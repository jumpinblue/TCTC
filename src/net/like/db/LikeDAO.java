package net.like.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import net.board.db.boardBean;

public class LikeDAO {
	Connection con = null;
	PreparedStatement pstmt = null;
	String sql = "";
	ResultSet rs = null;
	int num = 0;

	private Connection getConnection() throws Exception {
		Connection con = null;
		Context init = new InitialContext();
		DataSource ds = (DataSource) init.lookup("java:comp/env/jdbc/mySQL");
		con = ds.getConnection();
		return con;
	}
//love테이블에 누가 몇번째 글을 DB에 넣어주는 메소드
	public void addLike(LikeBean lb) {
		//like_num은 love테이블에 순서를 매기는 값(구현상에 필요한 칼럼은 아니나 primary key설정을 위해 생성)
		int like_num = 0;

		try {
			con = getConnection();
			//love테이블의 like_num 의 최대값 다음값에 좋아요 정보 입력
			sql = "select max(like_num)as max from love";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				like_num = rs.getInt("max") + 1;
			}
			sql = "insert into love(like_num,num,nick) values(?,?,?)";

			pstmt = con.prepareStatement(sql);

			pstmt.setInt(1, like_num);//행 순서매겨주는 변수 like_num
			pstmt.setInt(2, lb.getNum());//글번호
			pstmt.setString(3, lb.getNick());

			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (con != null)
				try {
					con.close();
				} catch (SQLException ex) {
				}
		} // finally

	}
//좋아요 취소, 좋아요를 눌렀던 정보가 삭제됨을 구현한 메소드
	public void adddislike(LikeBean lb) {
		System.out.println(lb.getLike_num());
		try {
			 con = getConnection();	 
			 //어떤 num글의 nick이 좋아요했던것을 삭제한다는 sql구문
				sql = "delete from love where num=? and nick=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1,lb.getNum());
				pstmt.setString(2, lb.getNick());
				pstmt.executeUpdate();				
		} catch (Exception e) {
			e.printStackTrace();
//		} finally {
			if(rs!=null)
				try{rs.close();}catch(SQLException ex){}
			if(pstmt!=null)
				try {pstmt.close();} catch (SQLException ex) {}
			if (con != null)
				try {con.close();} catch (SQLException ex) {}
		}//finally

	}

	
//한사람당 좋아요를 한번씩만 누르게 하기위해 필요한 메소드
//한 닉네임이 한 글에 좋아요를 몇개누르는지 count하여
//list 페이지에서 짝수일경우에 좋아요 버튼이 보이게, 홀수일경우 좋아요취소버튼이보이게제어 
	public int getLikecount(int num,String nick) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "";
		ResultSet rs = null;
		int count = 0;
		try {
			con = getConnection();
				sql = "select count(*) from love where num=? and nick=?";
	
				pstmt = con.prepareStatement(sql);
	
				pstmt.setInt(1,num);
				pstmt.setString(2,nick);
				rs = pstmt.executeQuery();
			// select에서 검색한 첫번째 열에 값이 있는지 없는지 확인하는 작업
			if (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (con != null)
				try {
					con.close();
				} catch (SQLException ex) {
				}
		} // finally
		return count;
	}// getBoardcount
	
	
	//좋아요 갯수 구하는 메소드  num값을 기준으로 해당글의 좋아갯수가 몇개인지 구해줌
	//그냥 gram 페이지에 love칼럼을 출력해줘도 됨....(그게 더 쉬움....)
	//한글에 좋아요갯수가 몇개인지 count하여 게시판 화면상에 좋아요가 몇개인지 나타내줌
	public int getLikecountall(int num) {		
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "";
		ResultSet rs = null;
		int count = 0;
		try {
			con = getConnection();
			//love의 어떤num에 해당하는 좋아요갯수가 몇개인지 count 해주는 sql구문
				sql = "select count(*) from love where num=? ";
	
				pstmt = con.prepareStatement(sql);	
				pstmt.setInt(1,num);
				rs = pstmt.executeQuery();
			// select에서 검색한 첫번째 열에 값이 있는지 없는지 확인하는 작업
			if (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (con != null)
				try {
					con.close();
				} catch (SQLException ex) {
				}
		} // finally
		return count;
	}//getLikecountall종료
	
	

}
