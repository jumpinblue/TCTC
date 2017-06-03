package net.Board1.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class BoardDAO {
	//디비연결메서드 getConnection()
	private Connection getConnection() throws Exception{
//		String dbUrl="jdbc:mysql://localhost:3306/jspdb2";
//		String dbId="jspid";
//		String dbPass="jsppass";
//		Connection con=null;
//		//1단계 드라이버로더
//		Class.forName("com.mysql.jdbc.Driver");
//		//2단계 디비연결
//		con=DriverManager.getConnection(dbUrl,dbId,dbPass);
//		return con;
		
		//커넥션 풀(Connection Pool) 
		// 데이터베이스와 연결된 Connection 객체를 미리 생성하여 
		// 풀(Pool)속에 저장해 두고 필요할때마다 이 풀을 접근하여 Connection객체 사용
		// 작업이 끝나면 다시 반환
		
		//자카르타 DBCP API 이용한 커텍션 풀
		// http://commons.apache.org/   다운
		// WebContent - WEB_INF - lib
		//  commons-collections-3.2.1.jar
		//  commons-dbcp-1.4.jar
		//  commons-pool-1.6.jar
		
		//1. WebContent - META-INF - context.xml 만들기
		//   1단계, 2단계 기술 -> 디비연동 이름정의
		//2. WebContent - WEB_INF - web.xml 수정
		//   context.xml 에  디비연동 해놓은 이름을 모든페이지에 알려줌
		//3. DB작업(DAO) -  이름을 불러서 사용
		
		Connection con=null;
		// Context 객체 생성
		Context init=new InitialContext();
		// DataSource =디비연동 이름 불러오기
		DataSource ds=(DataSource)init.lookup("java:comp/env/jdbc/mySQL");
		// con = DataSource
		con=ds.getConnection();
		return con;
	}
	
	public void insertBoard(BoardBean bb){
		Connection con=null;
		PreparedStatement pstmt=null;
		String sql="";
		ResultSet rs=null;
		int num=0;
		try {
			//1,2디비연결 메서드호출
			con=getConnection();
			// num 게시판 글번호 구하기
			sql="select max(num) from board";
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			if(rs.next()){
				num=rs.getInt(1)+1;
			}
			System.out.println("num="+num);
			//3 sql insert  디비날짜  now()
			sql="insert into board(num,nick_name,subject,content,readcount,re_ref,date,file,location) values(?,?,?,?,?,?,now(),?,?)";
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, num);
			pstmt.setString(2, bb.getNick_name());
			pstmt.setString(3, bb.getSubject()); //readcount 조회수 0
			pstmt.setString(4, bb.getContent()); //re_ref 답변글 그룹==일반글의 글번호 동일
			pstmt.setInt(5, bb.getReadcount()); //re_lev 답변글 들여쓰기 일반글 들여쓰기 없음
			pstmt.setInt(6, bb.getRe_ref()); //re_seq 답변글 순서   일반글 순서 맨위
			pstmt.setString(7, bb.getFile()); 
			pstmt.setString(8, bb.getLocation());
			//4  실행
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(rs!=null){try {rs.close();}catch(SQLException ex){}}
			if(pstmt!=null){try {pstmt.close();}catch(SQLException ex){}}
			if(con!=null){try {con.close();}catch(SQLException ex) {}}
		}
	}//insertBoard(bb)
	
	public int getBoardCount(){
		Connection con=null;
		PreparedStatement pstmt=null;
		String sql="";
		ResultSet rs=null;
		int count=0;
		try {
			//1,2 디비연결 메서드호출
			con=getConnection();
			//3 sql  함수  count(*) 이용
			sql="select count(*) from board";
			pstmt=con.prepareStatement(sql);
			//4 rs 실행 저장
			rs=pstmt.executeQuery();
			//5 rs 데이터 있으면  count 저장
			if(rs.next()){
				count=rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(rs!=null){try {rs.close();}catch(SQLException ex){}}
			if(pstmt!=null){try {pstmt.close();}catch(SQLException ex){}}
			if(con!=null){try {con.close();}catch(SQLException ex) {}}
		}
		return count;
	}//getBoardCount()
	
	public List getBoardList(int startRow,int pageSize){
		Connection con=null;
		PreparedStatement pstmt=null; 
		String sql="";
		ResultSet rs=null;
		List boardList=new ArrayList();
		try {
			//1,2 디비연결 메서드호출
			con=getConnection();
			//3 sql 객체 생성
			// sql  select * from board
			// 최글글위로 re_ref 그룹별내림차순 정렬  re_seq오름차순
			//      re_ref desc,re_seq asc
			// 글잘라오기    limit  시작행-1,개수 
			sql="select * from board order by num desc limit ?,?";
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, startRow-1);//시작행-1
			pstmt.setInt(2, pageSize);//몇개글
			//4 rs 실행 저장
			rs=pstmt.executeQuery();
			//5 rs while 데이터 있으면 
			// 자바빈 객체 생성 BoardBean bb
			// bb 멤버변수 <= rs열데이터 가져와서 저장
			// bb게시판 글 하나 => boardList 한칸 저장
			while(rs.next()){
				BoardBean bb=new BoardBean();
				bb.setNum(rs.getInt("num"));
				bb.setNick_name(rs.getString("nick_name"));
				bb.setSubject(rs.getString("subject"));
				bb.setContent(rs.getString("content"));
				bb.setDate(rs.getTimestamp("date"));
				bb.setFile(rs.getString("file"));
		
				bb.setRe_ref(rs.getInt("re_ref"));
			
				bb.setReadcount(rs.getInt("readcount"));
				bb.setLocation(rs.getString("location"));
				
				//boardList  한칸 저장
				boardList.add(bb);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(rs!=null){try {rs.close();}catch(SQLException ex){}}
			if(pstmt!=null){try {pstmt.close();}catch(SQLException ex){}}
			if(con!=null){try {con.close();}catch(SQLException ex) {}}
		}
		return boardList;
	}//getBoardList(startRow,pageSize)
	
	
	public BoardBean getBoard(int num){
		Connection con=null;
		PreparedStatement pstmt=null;
		String sql="";
		ResultSet rs=null;
		BoardBean bb=null;
		try {
			//1,2 디비연결 메서드호출
			con=getConnection();
			//3 sql 객체 생성 조건num값에 해당하는 게시판글 전체 가져오기
			sql="select * from board where num=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, num);
			//4  rs = 실행 저장
			rs=pstmt.executeQuery();
			//5 rs 데이터 있으면  자바빈 bb 객체 생성
			//  bb set메서드 멤버변수 저장  <= rs 열내용
			if(rs.next()){
				bb=new BoardBean();
				bb.setNum(rs.getInt("num"));
				bb.setNick_name(rs.getString("nick_name"));
				bb.setSubject(rs.getString("subject"));
				bb.setContent(rs.getString("content"));
				bb.setDate(rs.getTimestamp("date"));
				bb.setFile(rs.getString("file"));
				bb.setIp(rs.getString("ip"));
			
				bb.setRe_ref(rs.getInt("re_ref"));

				bb.setReadcount(rs.getInt("readcount"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(rs!=null){try {rs.close();}catch(SQLException ex){}}
			if(pstmt!=null){try {pstmt.close();}catch(SQLException ex){}}
			if(con!=null){try {con.close();}catch(SQLException ex) {}}
		}
		return bb;
	}//getBoard(int num)
	
	public void updateReadcount(int num){
		Connection con=null;
		PreparedStatement pstmt=null;
		String sql="";
		try {
			//1,2 드라이버로더
			con=getConnection();
			//3 sql 객체 생성
			// readcount 1증가    update   readcount=readcount+1
			sql="update board set readcount=readcount+1  where num=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, num);
			//4  실행
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(pstmt!=null){try {pstmt.close();}catch(SQLException ex){}}
			if(con!=null){try {con.close();}catch(SQLException ex) {}}
		}
	}//updateReadcount(int num)
	
	public int updateBoard(BoardBean bb){
		Connection con=null;
		PreparedStatement pstmt=null;
		String sql="";
		ResultSet rs=null;
		int check=-1;
		try { 
			//1,2 디비연결 메서드호출
			con=getConnection();
			//3 sql num에 해당하는 pass 가져오기
			sql="select pass from board where num=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, bb.getNum());
			//4 rs 실행 저장
			rs=pstmt.executeQuery();
			//5 rs 데이터 있으면 
			//            비밀번호 비교 맞으면 check=1
			//      3 num에 해당하는 name,subject,content수정
			//      4  실행
			//                    틀리면 check=0
			//         없으면 check=-1
			if(rs.next()){
				if(bb.getPass().equals(rs.getString("pass"))){
					//3 sql update
					sql="update board set name=?,subject=?,content=? where num=?";
					pstmt=con.prepareStatement(sql);
				
					pstmt.setString(2, bb.getSubject());
					pstmt.setString(3, bb.getContent());
					pstmt.setInt(4, bb.getNum());
					//4 실행
					pstmt.executeUpdate();
					check=1;
				}else{
					check=0;
				}
			}else{
				check=-1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(rs!=null){try {rs.close();}catch(SQLException ex){}}
			if(pstmt!=null){try {pstmt.close();}catch(SQLException ex){}}
			if(con!=null){try {con.close();}catch(SQLException ex) {}}
		}
		return check;
	}//updateBoard(BoardBean bb)
	
	public int deleteBoard(int num,String pass){
		Connection con=null;
		PreparedStatement pstmt=null;
		String sql="";
		ResultSet rs=null;
		int check=-1;
		try {
			//1,2 디비연결 메서드호출
			con=getConnection();
			//3 sql num에 해당하는 pass 가져오기
			sql="select pass from board where num=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, num);
			//4 rs 실행 저장
			rs=pstmt.executeQuery();
			//5 rs 데이터 있으면 
			//            비밀번호 비교 맞으면 check=1
			//      3 num에 해당하는 글삭제
			//      4  실행
			//                    틀리면 check=0
			//         없으면 check=-1
			if(rs.next()){
				if(pass.equals(rs.getString("pass"))){
					//3 sql delete
					sql="delete from board where num=?";
					pstmt=con.prepareStatement(sql);
					pstmt.setInt(1, num);
					//4 실행
					pstmt.executeUpdate();
					check=1;
				}else{
					check=0;
				}
			}else{
				check=-1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(rs!=null){try {rs.close();}catch(SQLException ex){}}
			if(pstmt!=null){try {pstmt.close();}catch(SQLException ex){}}
			if(con!=null){try {con.close();}catch(SQLException ex) {}}
		}
		return check;
	}//deleteBoard(num,pass)
	
	public void reInsertBoard(BoardBean bb){
		Connection con=null;
		PreparedStatement pstmt=null;
		String sql="";
		ResultSet rs=null;
		int num=0;
		try {
			// 1,2 디비연결
			con=getConnection();
			// 3sql select  최대num구하기
			// 4 rs=실행 저장
			// 5 rs데이터 있으면   num = 1번째열을 가져와서 +1
			sql="select max(num) from board";
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			if(rs.next()){
				num=rs.getInt(1)+1;
			}
			
			// 답글순서 재배치 
	// 3 update 조건 re_ref 같은그룹   re_seq 기존값보다 큰값이 있으면
			//  순서바꾸기 re_seq 1증가
			sql="update board set re_seq=re_seq+1  where re_ref=? and re_seq>?";
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, bb.getRe_ref());
		
			//4실행
			pstmt.executeUpdate();
			
			
			//3 sql insert   num구한값        re_ref 그대로  
			//               re_lev+1  re_seq+1   
			sql="insert into board(num,name,pass,subject,content,readcount,re_ref,re_lev,re_seq,date,ip,file) values(?,?,?,?,?,?,?,?,?,now(),?,?)";
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, num);
		
			pstmt.setString(4, bb.getSubject());
			pstmt.setString(5, bb.getContent());
			pstmt.setInt(6, 0); //readcount 조회수 0
			pstmt.setInt(7, bb.getRe_ref()); //re_ref 기존글 그룹번호 같게함
		
			pstmt.setString(10, bb.getIp());
			pstmt.setString(11, bb.getFile());
			//4  실행
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(rs!=null){try {rs.close();}catch(SQLException ex){}}
			if(pstmt!=null){try {pstmt.close();}catch(SQLException ex){}}
			if(con!=null){try {con.close();}catch(SQLException ex) {}}
		}
	}//reInsertBoard(bb)
	
}//클래스
