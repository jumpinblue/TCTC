package net.member.db;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

public class MemberDAO {
	
	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	String sql = "";
	
	// Connection pool
	private Connection getConnection() throws Exception {
		Context init = new InitialContext();
		DataSource ds = (DataSource)init.lookup("java:comp/env/jdbc/mySQL");
		con = ds.getConnection();
		
		return con;
	}
	
	// 아이디(이메일) 중복체크 및 존재여부(추가기능)
	public int idOverlapCheck(String id) {
		int check = 0;	// 0은 중복, 1은 사용가능
		
		try {
			con = getConnection();
			
			sql = "select count(id) as count from member where id=?";
			ps = con.prepareStatement(sql);
			ps.setString(1, id);
			
			rs = ps.executeQuery();
			
			if(rs.next()) {
				if(rs.getInt("count") > 0) {	// 아이디 중복, 아이디 존재
					check = 0;
				}else {										// 아이디 사용가능, 아이디 존재안함
					check = 1;
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try{
				if(rs!=null) rs.close();
				if(ps!=null) ps.close();
				if(con!=null) con.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		return check;
	}
	
	// 닉네임 중복체크
	public int nickOverlapCheck(String nick) {
		int check = -1;	// -1: 정규표현식 오류, 0: 중복, 1: 사용가능
		
		// 정규표현식 검사
		// 영문, 한글 시작 영문,숫자,한글 조합 가능 2~9자
		if(!Pattern.matches("^[a-z|A-Z|가-힣][a-z|A-Z|0-9|가-힣]{1,8}", nick)) {		// 정규표현식에 맞지 않으면
			return check;	// -1 반환
		}
		
		// 중복검사
		try{
			con = getConnection();
			
			sql = "select count(id) as count from member where nick=?";
			ps = con.prepareStatement(sql);
			ps.setString(1, nick);
			rs = ps.executeQuery();
			
			if(rs.next()) {
				if(rs.getInt("count") == 0) {
					check = 1;
				}else if(rs.getInt("count")>0) {
					check = 0;
				}
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try{
				if(rs!=null) rs.close();
				if(ps!=null) ps.close();
				if(con!=null) con.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		return check;
	}
	
	// 닉네임 중복체크2(같은 id의 닉네임은 중복되도 중복아님)
		public int nickOverlapCheck2(String id, String nick) {
			int check = -1;	// -1: 정규표현식 오류, 0: 중복, 1: 사용가능
			
			// 정규표현식 검사
			// 영문, 한글 시작 영문,숫자,한글 조합 가능 2~9자
			if(!Pattern.matches("^[a-z|A-Z|가-힣][a-z|A-Z|0-9|가-힣]{1,8}", nick)) {		// 정규표현식에 맞지 않으면
				return check;	// -1 반환
			}
			
			// 중복검사
			try{
				con = getConnection();
				
				sql = "select id count from member where nick=? && !(id=?)";
				ps = con.prepareStatement(sql);
				ps.setString(1, nick);
				ps.setString(2, id);
				rs = ps.executeQuery();
				
				if(rs.next()) {
					check = 0;
				}else {
					check = 1;
				}
				
				
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				try{
					if(rs!=null) rs.close();
					if(ps!=null) ps.close();
					if(con!=null) con.close();
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
			
			return check;
		}
	
	// 회원가입
	public void insertMember(MemberBean mb) {
		
		/* DB암호화 */
		// 비밀번호 단방향 암호화(SHA-256)
		// 전화번호 양방향 암호화(AES)
		
		try {
			con = getConnection();
			
			// 자바에서 비밀번호 SHA256 암호화(DB에도 함수가 있지만 작업할때 낮은 버전의 mySql을 쓰게 되서 자바에서 암호화 후 DB에 입력)
			String pass = mb.getPass();
			String shaPass = encSHA256(pass);	// 암호화 된 비밀번호(SHA-256)
			
			sql = "insert into member(id, pass, name, nick, gender, tel, reg_date, profile, auth, gold) "
					+ "values(?, ?, ?, ?, ?, HEX(AES_ENCRYPT(?, 'tel')), ?, ?, ?, ?)";
			ps = con.prepareStatement(sql);
			ps.setString(1, mb.getId());
			ps.setString(2, shaPass);
			ps.setString(3, mb.getName());
			ps.setString(4, mb.getNick());
			ps.setString(5, mb.getGender());
			ps.setString(6, mb.getTel());		// 암호화키 임시로 tel로 해놓음
			ps.setTimestamp(7, mb.getReg_date());
			ps.setString(8, mb.getProfile());
			ps.setInt(9, mb.getAuth());		// 처음 가입땐 1(일반사용자)
			ps.setInt(10, 0);
			
			ps.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try{
				if(rs!=null) rs.close();
				if(ps!=null) ps.close();
				if(con!=null) con.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
	}

	// 로그인 인증
	public int memberLogin(String id, String pass) {
		
		// -1 아이디 없음, 0 비밀번호 틀림, 1 비밀번호 맞음
		int check = 0;
		
		try {
			
			String shaPass = encSHA256(pass);	// SHA256로 암호화 된 비밀번호
			
			con = getConnection();
			
			sql = "select pass from member where id = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, id);
			
			rs = ps.executeQuery();
			
			if(rs.next()) {	// 아이디 존재
				if(rs.getString("pass").equals(shaPass)) {	// 비밀번호 일치
					check = 1;
				}else {	// 비밀번호 틀림
					check = 0;
				}
				
			}else {	// 아이디 없음
				check = -1;
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try{
				if(rs!=null) rs.close();
				if(ps!=null) ps.close();
				if(con!=null) con.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return check;
	}
	
	// 회원정보 가져오기
	public MemberBean getMember(String id) {
		
		MemberBean mb = new MemberBean();
		
		try {
			
			con = getConnection();
			
			sql = "select id, pass, name, nick, gender, AES_DECRYPT(UNHEX(tel), 'tel') as tel, reg_date, profile, auth, gold"
					+ " from member where id = ?";
			ps = con.prepareStatement(sql);
			
			ps.setString(1, id);
			
			rs = ps.executeQuery();
			
			if(rs.next()) {	// 아이디가 있으면
				
				mb.setId(rs.getString("id"));
				mb.setPass(rs.getString("pass"));
				mb.setName(rs.getString("name"));
				mb.setNick(rs.getString("nick"));
				mb.setGender(rs.getString("gender"));
				mb.setTel(rs.getString("tel"));
				mb.setReg_date(rs.getTimestamp("reg_date"));
				mb.setProfile(rs.getString("profile"));
				mb.setAuth(rs.getInt("auth"));
				mb.setGold(rs.getInt("gold"));
				
			}else {	// 아이디가 없으면
				return mb;
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try{
				if(rs!=null) rs.close();
				if(ps!=null) ps.close();
				if(con!=null) con.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		return mb;
	}
	
	// 닉네임으로 회원정보 가져오기
		public MemberBean getMemberNick(String nick) {
			
			MemberBean mb = new MemberBean();
			
			try {
				
				con = getConnection();
				
				sql = "select id, pass, name, nick, gender, AES_DECRYPT(UNHEX(tel), 'tel') as tel, reg_date, profile, auth, gold"
						+ " from member where nick = ?";
				ps = con.prepareStatement(sql);
				
				ps.setString(1, nick);
				
				rs = ps.executeQuery();
				
				if(rs.next()) {	// 닉네임을 가진 아이디가 있으면
					
					mb.setId(rs.getString("id"));
					mb.setPass(rs.getString("pass"));
					mb.setName(rs.getString("name"));
					mb.setNick(nick);
					mb.setGender(rs.getString("gender"));
					mb.setTel(rs.getString("tel"));
					mb.setReg_date(rs.getTimestamp("reg_date"));
					mb.setProfile(rs.getString("profile"));
					mb.setAuth(rs.getInt("auth"));
					mb.setGold(rs.getInt("gold"));
					
				}else {	// 아이디가 없으면
					return mb;
				}
				
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				try{
					if(rs!=null) rs.close();
					if(ps!=null) ps.close();
					if(con!=null) con.close();
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
			
			return mb;
		}
	
	// 읽어버린 아이디 찾기
	public List<MemberBean> getFinderMemberId(String name, String tel) {
	
		List<MemberBean> idList = new ArrayList<MemberBean>();
		
		try {			
			con = getConnection();
			
			sql = "select id, reg_date from member where name=? && AES_DECRYPT(UNHEX(tel), 'tel') = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, name);
			ps.setString(2, tel);
			
			rs = ps.executeQuery();
			
			MemberBean mb;
			while(rs.next()) {
				mb = new MemberBean();
				mb.setId(rs.getString("id"));
				mb.setReg_date(rs.getTimestamp("reg_date"));
				
				idList.add(mb);
			}
			
		}catch(Exception e) {
			System.out.println("MemberDAO->getFinderMemberId()에서 예외발생");
			e.printStackTrace();
		}finally {
			try{
				if(rs!=null) rs.close();
				if(ps!=null) ps.close();
				if(con!=null) con.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		return idList;
		
	}
	
	// 읽어버린 비밀번호 찾기
	public void updatePass(String id, String pass) {
		
		// 임시비밀번호를 메일로 보내고 DB에서도 비밀번호를 한다.
		try {
			
			String shaPass = encSHA256(pass);	// SHA256 암호화
			
			con = getConnection();
			
			// 임시비밀번호 SHA256 암호화
			sql = "update member set pass = ? where id = ? ";
			ps = con.prepareStatement(sql);
			ps.setString(1, shaPass);
			ps.setString(2, id);
			
			ps.executeUpdate();
			
		}catch(Exception e) {
			
			e.printStackTrace();
			
		}finally {
			try{
				if(rs!=null) rs.close();
				if(ps!=null) ps.close();
				if(con!=null) con.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	// 비밀번호 변경
	public int updatePass(String id, String cur_pass, String new_pass) {
			
		int check = 0;	// -1: 아이디 없음 0: 비밀번호 일치하지 않음, 1: 비밀번호 일치
		
		try {
				
			String shaCurPass = encSHA256(cur_pass);	// 현재 비밀번호 암호화
			String shaNewPass = encSHA256(new_pass);	// 새로운 비밀번호 암호화
			
			con = getConnection();
				
			sql = "select pass from member where id = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, id);
			
			rs = ps.executeQuery();
			
			if(rs.next()) {	// 아이디 있음
				if(rs.getString("pass").equals(shaCurPass)) {	// 현재 비밀번호 일치
					
					check = 1;
					
					sql = "update member set pass=? where id=?";
					ps = con.prepareStatement(sql);
					ps.setString(1, shaNewPass);
					ps.setString(2, id);
					
					ps.executeUpdate();
					
				}else {	// 현재 비밀번호 틀림
					check = 0;
				}
				
			}else {	// 아이디 없음
				check = -1;
			}
				
			}catch(Exception e) {
				System.out.println("MemberDAO->updatePass()에서 예외발생");
				e.printStackTrace();
			}finally {
				try{
					if(rs!=null) rs.close();
					if(ps!=null) ps.close();
					if(con!=null) con.close();
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
			
			return check;
		}
	
	// 회원정보 수정
	public void updateMember(MemberBean mb, String realPath) {
		
		try {
			con = getConnection();
			
			if(mb.getProfile()==null) {	// 프로필 사진 변경을 안했으면

				// 회원정보 수정
				sql = "update member set name=?, nick=?, gender=?, tel=HEX(AES_ENCRYPT(?, 'tel')) where id=?";
				ps = con.prepareStatement(sql);
				ps.setString(1, mb.getName());
				ps.setString(2, mb.getNick());
				ps.setString(3, mb.getGender());
				ps.setString(4, mb.getTel());
				ps.setString(5, mb.getId());
				
				ps.executeUpdate();
				
			}else {	// 프로필 사진 변경을 했으면
				
				// 기존의 프로필 이미지는 삭제(물리적 위치에 있는 이미지 파일)
				sql = "select profile from member where id=?";
				ps = con.prepareStatement(sql);
				ps.setString(1, mb.getId());
				rs = ps.executeQuery();
				if(rs.next()) {
					// 기본 프로필 사진일때는 삭제하지 않음
					if("basic/man.png".equals(rs.getString("profile")) || "basic/woman.png".equals(rs.getString("profile")) ) {
						System.out.println("기본 프로필이라 삭제 안함");
					}else {
						File f = new File(realPath+"/"+rs.getString("profile"));
						f.delete();
						System.out.println("프로필 이미지 삭제 성공");
					}
				}
				
				// 회원정보 수정
				sql = "update member set name=?, nick=?, gender=?, tel=HEX(AES_ENCRYPT(?, 'tel')), profile=? where id=?";
				ps = con.prepareStatement(sql);
				ps.setString(1, mb.getName());
				ps.setString(2, mb.getNick());
				ps.setString(3, mb.getGender());
				ps.setString(4, mb.getTel());
				ps.setString(5, mb.getProfile());
				ps.setString(6, mb.getId());
				
				ps.executeUpdate();
			}
			
		}catch(Exception e){
			System.out.println("MemberDAO->updateMember에서 예외발생");
			e.printStackTrace();
		}finally {
			try{
				if(rs!=null) rs.close();
				if(ps!=null) ps.close();
				if(con!=null) con.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	// 회원 탈퇴
	public int deleteMember(String id, String pass, String realPath) {
		
		int check = 0;	// -1: 아이디 없음 0: 비밀번호 틀림, 1: 탈퇴 완료
		
		try {
			
			String shaPass = encSHA256(pass);	// SHA256 암호화
			
			con = getConnection();
			
			sql = "select pass from member where id = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, id);
			
			rs = ps.executeQuery();
			
			if(rs.next()) { 	// 아이디 있음
				if(rs.getString("pass").equals(shaPass)) { 	// 비밀번호 일치
					check = 1;
					
					// 기존의 프로필 이미지는 삭제(물리적 위치에 있는 이미지 파일)
					sql = "select profile from member where id=?";
					ps = con.prepareStatement(sql);
					ps.setString(1, id);
					rs = ps.executeQuery();
					if(rs.next()){ 	// 아이디 존재
						// 기본 프로필 사진일때는 삭제하지 않음
						if("basic/man.png".equals(rs.getString("profile")) || "basic/woman.png".equals(rs.getString("profile")) ) {
							System.out.println("기본 프로필이라 삭제 안함");
						}else {
							File f = new File(realPath+rs.getString("profile"));
							f.delete();
							System.out.println("프로필 이미지 삭제 성공");
						}
					}

					sql="delete from member where id=?";
					ps = con.prepareStatement(sql);
					ps.setString(1, id);
						
					ps.executeUpdate();
					
				}else {	// 비밀번호 틀림
					check = 0;
				}
				
			}else {	// 아이디 없음
				check = -1;
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			
		}finally {
			try{
				if(rs!=null) rs.close();
				if(ps!=null) ps.close();
				if(con!=null) con.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return check;
	}
	
	// 회원 탈퇴(관리자가 회원관리 페이지에서 삭제시키기 때문에 id랑 프로필사진 경로만 필요)
		public void deleteMember(String id, String realPath) {

			try {
				
				con = getConnection();
				
				// 기존의 프로필 이미지는 삭제(물리적 위치에 있는 이미지 파일)
				sql = "select profile from member where id=?";
				ps = con.prepareStatement(sql);
				ps.setString(1, id);
				rs = ps.executeQuery();
				if(rs.next()) {
					// 기본 프로필 사진일때는 삭제하지 않음
					if("basic/man.png".equals(rs.getString("profile")) || "basic/woman.png".equals(rs.getString("profile")) ) {
						System.out.println("기본 프로필이라 삭제 안함");
					}else {
						File f = new File(realPath+rs.getString("profile"));
						f.delete();
						System.out.println("프로필 이미지 삭제 성공");
					}
				}
				
				sql = "delete from member where id=?";
				ps = con.prepareStatement(sql);
				ps.setString(1, id);
				
				ps.executeUpdate();
				
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				try{
					if(rs!=null) rs.close();
					if(ps!=null) ps.close();
					if(con!=null) con.close();
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
	
	// 가입된 회원수 구하기
	public int getCountMember() {
		
		int count = 0;
		try {
			
			con = getConnection();
			
			sql = "select count(id) as count from member";
			ps = con.prepareStatement(sql);
			
			rs = ps.executeQuery();
			
			if(rs.next()) {
				count = rs.getInt("count");
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try{
				if(rs!=null) rs.close();
				if(ps!=null) ps.close();
				if(con!=null) con.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return count;
	}
	
	// 가입된 회원수 구하기(검색값)
		public int getCountMember(String search, String search_sel) {
			
			int count = 0;
			try {
				
				con = getConnection();
				
				if("id_search".equals(search_sel)) {	// 아이디로 검색
					sql = "select count(id) as count from member where id like ?"; 
				}else {	// 닉네임으로 검색
					sql = "select count(id) as count from member where nick like ?"; 
				}
				
				ps = con.prepareStatement(sql);
				ps.setString(1, "%"+search+"%");
				
				rs = ps.executeQuery();
				
				if(rs.next()) {
					count = rs.getInt("count");
				}
				
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				try{
					if(rs!=null) rs.close();
					if(ps!=null) ps.close();
					if(con!=null) con.close();
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
			return count;
		}
	
	// 회원 리스트 가져오기
	public List<MemberBean> getMemberList(int startRow, int pageSize, String search, String search_sel, int sort) {
		
		List<MemberBean> memberList = new ArrayList<MemberBean>();
		MemberBean mb = null;
		
		try {
			
			con = getConnection();
			
			if("id_search".equals(search_sel)) {	// 아이디로 검색
				if(sort==1) {	// id오름차순
					sql = "select id, pass, name, nick, gender, tel, reg_date, profile, auth, gold from member "
							+ "where id like ? order by id limit ?, ?";
				}else if(sort==2) {	// id내림차순
					sql = "select id, pass, name, nick, gender, tel, reg_date, profile, auth, gold from member "
							+ "where id like ? order by id desc limit ?, ?";
				}else if(sort==3) {	// nick 오름차순
					sql = "select id, pass, name, nick, gender, tel, reg_date, profile, auth, gold from member "
							+ "where id like ? order by nick limit ?, ?";
				}else if(sort==4) {	// nick 내림차순
					sql = "select id, pass, name, nick, gender, tel, reg_date, profile, auth, gold from member "
							+ "where id like ? order by nick desc limit ?, ?";
				}else if(sort==5) {	// auth 오름차순
					sql = "select id, pass, name, nick, gender, tel, reg_date, profile, auth, gold from member "
							+ "where id like ? order by auth limit ?, ?";
				}else if(sort==6) {	// auth 내림차순
					sql = "select id, pass, name, nick, gender, tel, reg_date, profile, auth, gold from member "
							+ "where id like ? order by auth desc limit ?, ?";
				}else {
					sql = "select id, pass, name, nick, gender, tel, reg_date, profile, auth, gold from member "
							+ "where id like ? order by reg_date limit ?, ?";
				}
				

			}else {	// 닉네임으로 검색
				if(sort==1) {	// id오름차순
					sql = "select id, pass, name, nick, gender, tel, reg_date, profile, auth, gold from member "
							+ "where nick like ? order by id limit ?, ?";
				}else if(sort==2) {	// id내림차순
					sql = "select id, pass, name, nick, gender, tel, reg_date, profile, auth, gold from member "
							+ "where nick like ? order by id desc limit ?, ?";
				}else if(sort==3) {	// nick 오름차순
					sql = "select id, pass, name, nick, gender, tel, reg_date, profile, auth, gold from member "
							+ "where nick like ? order by nick limit ?, ?";
				}else if(sort==4) {	// nick 내림차순
					sql = "select id, pass, name, nick, gender, tel, reg_date, profile, auth, gold from member "
							+ "where nick like ? order by nick desc limit ?, ?";
				}else if(sort==5) {	// auth 오름차순
					sql = "select id, pass, name, nick, gender, tel, reg_date, profile, auth, gold from member "
							+ "where nick like ? order by auth limit ?, ?";
				}else if(sort==6) {	// auth 내림차순
					sql = "select id, pass, name, nick, gender, tel, reg_date, profile, auth, gold from member "
							+ "where nick like ? order by auth desc limit ?, ?";
				}else {
					sql = "select id, pass, name, nick, gender, tel, reg_date, profile, auth, gold from member "
							+ "where nick like ? order by reg_date limit ?, ?";
				}
			}
			
			ps = con.prepareStatement(sql);
			ps.setString(1, "%"+search+"%");
			ps.setInt(2, startRow-1);
			ps.setInt(3, pageSize);
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				mb = new MemberBean();
				
				mb.setId(rs.getString("id"));
				mb.setPass(rs.getString("pass"));
				mb.setName(rs.getString("name"));
				mb.setNick(rs.getString("nick"));
				mb.setGender(rs.getString("gender"));
				mb.setTel(rs.getString("tel"));
				mb.setReg_date(rs.getTimestamp("reg_date"));
				mb.setProfile(rs.getString("profile"));
				mb.setAuth(rs.getInt("auth"));
				mb.setGold(rs.getInt("gold"));
				
				memberList.add(mb);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try{
				if(rs!=null) rs.close();
				if(ps!=null) ps.close();
				if(con!=null) con.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		return memberList;
	}
	
	// 권한 변경
	public void authUpdate(String id, String auth) {
		
		try {
			
			con = getConnection();
			
			sql = "update member set auth=? where id=?";
			ps = con.prepareStatement(sql);
			if(auth.equals("admin")) {	// 관리자
				ps.setInt(1, 0);
			}else if(auth.equals("user")) {	// 사용자
				ps.setInt(1, 1);
			}
			ps.setString(2, id);
			ps.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try{
				if(rs!=null) rs.close();
				if(ps!=null) ps.close();
				if(con!=null) con.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	// 닉네임 변경
	public void updateNick(String id, String nick) {
		
		try {
			
			con = getConnection();
			
			sql = "update member set nick=? where id=?";
			ps = con.prepareStatement(sql);
			ps.setString(1, nick);
			ps.setString(2, id);
			
			ps.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try{
				if(rs!=null) rs.close();
				if(ps!=null) ps.close();
				if(con!=null) con.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	// 닉네임 반환
	public String getNick(String id) {
		
		String nick = "";
		
		try {
			
			con = getConnection();
			
			sql="select nick from member where id=?";
			ps = con.prepareStatement(sql);
			ps.setString(1, id);
			rs = ps.executeQuery();
			
			if(rs.next()) {
				nick = rs.getString("nick");
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try{
				if(rs!=null) rs.close();
				if(ps!=null) ps.close();
				if(con!=null) con.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return nick;
	}
	

	// SHA256 암호화(단방향)
	public String encSHA256(String str){
		String SHA = ""; 
		try{
			MessageDigest sh = MessageDigest.getInstance("SHA-256"); 
			sh.update(str.getBytes()); 
			byte byteData[] = sh.digest();
			StringBuffer sb = new StringBuffer(); 
			for(int i = 0 ; i < byteData.length ; i++){
				sb.append(Integer.toString((byteData[i]&0xff) + 0x100, 16).substring(1));
		}
			SHA = sb.toString();

		}catch(NoSuchAlgorithmException e){
			e.printStackTrace(); 
			SHA = null; 
		}
			return SHA;
	}
	
} // class








