package temp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class MyPlanDAO {
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

	public void insertMyplan(MyPlanBean mplBean) {
		// TODO Auto-generated method stub
		Connection con=null;
		PreparedStatement pstmt=null;
		String sql="";
		ResultSet rs=null;
		int num=0;
		try {
			//1,2 디비연결
			con=getConnection();
			//num구하기
			sql="select max(myplans_id) from myplans";
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			if(rs.next()){
				num=rs.getInt(1)+1;
			}else{
				num=1;
			}
			//3 sql insert
			sql="insert into travel(myplans_id, id, plan_nr, travel_id, item_nr, "
					+ "firstday, lastday, day_nr, day_night, user_lat, user_lng,"
					+ "date, memo, plan_done_nr) "
					+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, mplBean.getMyplans_id());
			pstmt.setString(2, mplBean.getId());
			pstmt.setInt(3, mplBean.getPlan_nr());
			pstmt.setInt(4, mplBean.getTravel_id());
			pstmt.setInt(5, mplBean.getItem_nr());
			pstmt.setString(6, mplBean.getFirstday());
			pstmt.setString(7, mplBean.getLastday());
			pstmt.setInt(8, mplBean.getDay_nr());
			pstmt.setString(9, mplBean.getDay_night());
			pstmt.setFloat(10, mplBean.getUser_lat());
			pstmt.setFloat(11, mplBean.getUser_lng());
			pstmt.setString(12, mplBean.getDate());
			pstmt.setString(13, mplBean.getMemo());
			pstmt.setInt(14, mplBean.getPlan_done_nr());
			
		
		
			//4 실행
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(rs!=null)try{rs.close();}catch(SQLException ex){}
			if(pstmt!=null)try{pstmt.close();}catch(SQLException ex){}
			if(con!=null)try{con.close();}catch(SQLException ex){}
		}
	}

	public List getMyPlanList() {
		// TODO Auto-generated method stub
		List myplanList =new ArrayList();
		Connection con=null;
		PreparedStatement pstmt=null;
		String sql="";
		ResultSet rs=null;
		try {
			//1,2 디비연결
			con=getConnection();
			//3 sql
			sql="select * from myplans where id=?";
			pstmt=con.prepareStatement(sql);
			//4 rs 실행 저장
			rs=pstmt.executeQuery();
			//5 rs데이터 있으면 자바빈 객체 생성 mplBean
			//  rs => 자바빈 멤버변수 저장 => goodsList 한칸 저장
			while(rs.next()){
				MyPlanBean mpl =new MyPlanBean();
				mpl.setMyplans_id(rs.getInt("myplans_id"));
				mpl.setId(rs.getString("id"));
				mpl.setPlan_nr(rs.getInt("plan_nr"));
				mpl.setTravel_id(rs.getInt("travel_id"));
				mpl.setItem_nr(rs.getInt("item_nr"));
				mpl.setFirstday(rs.getString("firstday"));
				mpl.setLastday(rs.getString("lastday"));
				mpl.setDay_nr(rs.getInt("day_nr"));
				mpl.setDay_night(rs.getString("day_night"));
				mpl.setUser_lat(rs.getFloat("user_lat"));
				mpl.setUser_lng(rs.getFloat("user_lng"));
				mpl.setDate(rs.getString("date"));
				mpl.setMemo(rs.getString("memo"));
				mpl.setPlan_done_nr(rs.getInt("plan_done_nr"));
				//자바빈 => 배열 한칸 저장
				myplanList.add(mpl);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(rs!=null)try{rs.close();}catch(SQLException ex){}
			if(pstmt!=null)try{pstmt.close();}catch(SQLException ex){}
			if(con!=null)try{con.close();}catch(SQLException ex){}
		}
		return myplanList;
	}


			
		/* map DAO 추후사용예정
		 * 
		 * 	public List<MyPlanBean> MyPlan(){

		List<MyPlanBean> MyPlan = new ArrayList<MyPlanBean>();
		
		try {			
			con = getConnection();
			
			sql = "select name, latitude, longitude from travel";
			ps = con.prepareStatement(sql);
		
			
			rs = ps.executeQuery();
			
			MyPlanBean mb;
			while(rs.next()) {
				mb = new MyPlanBean();
				mb.setName(rs.getString("name"));
				mb.setLatitude(rs.getString("latitude"));
				mb.setLongitude(rs.getString("longitude"));
				
				MyPlan.add(mb);
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
		
		return MyPlan;
		
		}*/

	
	

}
