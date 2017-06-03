package net.travel.admin.db;

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
import javax.sql.DataSource;

public class TravelAdminDAO {
	// Connection pool
	private Connection getConnection() throws Exception {
		Connection con = null;
		Context init = new InitialContext();
		DataSource ds = (DataSource)init.lookup("java:comp/env/jdbc/mySQL");
		con = ds.getConnection();
		return con;
	}

	// 여행정보 저장
	public void insertTravel(TravelBean tBean) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "";

		/*int num=0;*/
		try {
			con = getConnection();
			
			/*sql="select max(num) from travel";
			ps=con.prepareStatement(sql);
			rs=ps.executeQuery();
			if(rs.next()){
				num=rs.getInt(1)+1;
			}else{
				num=1;
			}*/
			
			sql = "insert into travel(type,country_code,city_code,name,latitude,longitude,info,address)"
					+ "values(?,?,?,?,?,?,?,?)";
			ps = con.prepareStatement(sql);
			ps.setString(1, tBean.getType());
			ps.setString(2, tBean.getCountry_code());
			ps.setString(3, tBean.getCity_code());
			ps.setString(4, tBean.getName());
			ps.setFloat(5, tBean.getLatitude());
			ps.setFloat(6, tBean.getLongitude());
			ps.setString(7, tBean.getInfo());
			ps.setString(8, tBean.getAddress());
			
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
	
/*	// 아이디(이메일) 중복체크
	public int idOverlapCheck(String id) {
		int check = 0;	// 0은 중복, 1은 사용가능
		
		try {
			con = getConnection();
			
			sql = "select count(id) as count from member where id=?";
			ps = con.prepareStatement(sql);
			ps.setString(1, id);
			
			rs = ps.executeQuery();
			
			if(rs.next()) {
				if(rs.getInt("count") > 0) {	// 아이디 중복
					check = 0;
				}else {										// 아이디 사용가능
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
	}*/
	
/*
	// 로그인 인증
	public int memberLogin(String id, String pass) {
		
		// -1 아이디없음, 0 비밀번호 틀림, 1 비밀번호 맞음
		int check = -1;
		
		try {
			
			con = getConnection();
			
			// DB의 pass가 단방향 암호화 SHA-256으로 되어있기때문에 매개변수 pass를 SHA-256암호화 후 비교한다.
			sql = "select pass from member where id=?";
			ps = con.prepareStatement(sql);
			ps.setString(1, id);
			
			rs = ps.executeQuery();
			
			if(rs.next()) {
				String passSHA = txtSHA256(pass);	// SHA256 암호화
				if(rs.getString("pass").equals(passSHA)) {
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
	
	// SHA256 암호화(비밀번호 확인 시 필요)
	public String txtSHA256(String str){
	
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
			System.out.println("SHA256암호화 오류");
			e.printStackTrace(); 
			SHA = null; 
		}
			return SHA;
	}
	
	// 회원정보 가져오기
	public TravelDataBean getMember(String id) {
		
		TravelDataBean mb = new TravelDataBean();
		
		try {
			
			con = getConnection();
			
			sql = "select id, pass, name, nick, gender, AES_DECRYPT(UNHEX(tel), 'tel') as tel, reg_date, profile, auth"
					+ " from member where id = ?";
			ps = con.prepareStatement(sql);
			
			ps.setString(1, id);
			
			rs = ps.executeQuery();
			
			if(rs.next()) {	// 아이디가 있으면
				
				mb.setId(id);
				mb.setPass(rs.getString("pass"));
				mb.setName(rs.getString("name"));
				mb.setNick(rs.getString("nick"));
				mb.setGender(rs.getString("gender"));
				mb.setTel(rs.getString("tel"));
				mb.setReg_date(rs.getTimestamp("reg_date"));
				mb.setProfile(rs.getString("profile"));
				mb.setAuth(rs.getInt("auth"));
				
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
	public List<TravelDataBean> getFinderMemberId(String name, String tel) {
	
		List<TravelDataBean> idList = new ArrayList<TravelDataBean>();
		
		try {			
			con = getConnection();
			
			sql = "select id, reg_date from member where name=? && AES_DECRYPT(UNHEX(tel), 'tel') = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, name);
			ps.setString(2, tel);
			
			rs = ps.executeQuery();
			
			TravelDataBean mb;
			while(rs.next()) {
				mb = new TravelDataBean();
				mb.setId(rs.getString("id"));
				mb.setReg_date(rs.getTimestamp("reg_date"));
				
				idList.add(mb);
			}
			
		}catch(Exception e) {
			System.out.println("MemberDAO클래스 getFinderMemberId() 예외 오류");
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
*/	
}








