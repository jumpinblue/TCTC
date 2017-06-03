package net.images.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;




/* 이미지파일을 관리하는 테이블 */
public class ImagesDAO {
	
	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	String sql = "";
	
	private Connection getConnection() throws Exception {
		
		Connection con = null;
		Context init = new InitialContext();
		DataSource ds = (DataSource)init.lookup("java:comp/env/jdbc/mySQL");
		con = ds.getConnection();
		
		return con;
	}
	
	// 이미지정보 넣기
	public void insertImage(ImagesBean ib) {
		
		try {
			con = getConnection();
			
			// num의 최댓값 구하기
			int num = 1;
			sql = "select MAX(num) as max from images";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			
			if(rs.next()) {
				num = rs.getInt("max")+1;
			}
			
			// images 테이블에 값 넣기
			sql = "insert into images(num, country_code, city_code, travel_id, type, name, info, file)"
					+ "value(?, ?, ?, ?, ?, ?, ?, ?)";
			ps = con.prepareStatement(sql);
			ps.setInt(1, num);
			ps.setString(2, ib.getCountry_code());
			ps.setString(3, ib.getCity_code());
			ps.setInt(4, ib.getTravel_id());
			ps.setString(5, ib.getType());
			ps.setString(6, ib.getName());
			ps.setString(7, ib.getInfo());
			ps.setString(8, ib.getFile());
			
			ps.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs!=null) rs.close();
				if(ps!=null) ps.close();
				if(con!=null) con.close();
				
			}catch(Exception e) {
				e.printStackTrace();
			}
		}		
	}

	// 도시 이미지 정보 있는지 확인하기
	// 한 국가의 도시의 이미지는 한개
	public boolean getIsCityImage(String city_code) {
		
		boolean b = false;
		try {
			con = getConnection();
			
			sql = "select count(city_code) as count from images where city_code = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, city_code);
			
			rs = ps.executeQuery();
			
			if(rs.next()) {
				if(rs.getInt("count")!=0) {
					b = true;
				}
			}
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs!=null) rs.close();
				if(ps!=null) ps.close();
				if(con!=null) con.close();
				
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		return b;
		
	}
	
	// 도시 이미지전체 가져오기(국가페이지에서 필요)
	public List<ImagesBean> getCityImages(String country_code) {
		
		List<ImagesBean> cityImgList = new ArrayList<ImagesBean>();
		
		try {
			
			con = getConnection();
			
			sql = "select * from images where country_code = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, country_code);
			
			rs = ps.executeQuery();
			
			ImagesBean ib = null;
			while(rs.next()) {
				ib = new ImagesBean();
				ib.setNum(rs.getInt("num"));
				ib.setCountry_code(rs.getString("country_code"));
				ib.setCity_code(rs.getString("city_code"));
				ib.setTravel_id(rs.getInt("travel_id"));
				ib.setType(rs.getString("type"));
				ib.setName(rs.getString("name"));
				ib.setInfo(rs.getString("info"));
				ib.setFile(rs.getString("file"));
				
				cityImgList.add(ib);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs!=null) rs.close();
				if(ps!=null) ps.close();
				if(con!=null) con.close();
				
			}catch(Exception e) {
				e.printStackTrace();
			}	
		}
		
		return cityImgList;
	}
	
	// 관광지 이미지전체 가져오기(도시페이지에서 필요)
	public List<ImagesBean> getTravelImages(String city_code) {
			
		List<ImagesBean> travelImgList = new ArrayList<ImagesBean>();
			
		try {
				
			con = getConnection();
				
			sql = "select * from images where city_code = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, city_code);
				
			rs = ps.executeQuery();
			
			ImagesBean ib = null;
			while(rs.next()) {
				ib = new ImagesBean();
				ib.setNum(rs.getInt("num"));
				ib.setCountry_code(rs.getString("city_code"));
				ib.setCity_code(rs.getString("city_code"));
				ib.setTravel_id(rs.getInt("travel_id"));
				ib.setType(rs.getString("type"));
				ib.setName(rs.getString("name"));
				ib.setInfo(rs.getString("info"));
				ib.setFile(rs.getString("file"));
				
				travelImgList.add(ib);
			}
				
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs!=null) rs.close();
				if(ps!=null) ps.close();
				if(con!=null) con.close();
				
			}catch(Exception e) {
				e.printStackTrace();
			}	
		}
		return travelImgList;
	}
	
	// 도시 이미지 경로 구하기
	public String getCityImgPath(String city_code) {
		
		String path = "";
		
		try {
			
			con = getConnection();
			
			sql = "select file from images where city_code = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, city_code);
			
			rs = ps.executeQuery();
			
			if(rs.next()) {
				path = rs.getString("file");
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs!=null) rs.close();
				if(ps!=null) ps.close();
				if(con!=null) con.close();
				
			}catch(Exception e) {
				e.printStackTrace();
			}	
		}
		
		return path;
	}
	
	// 여행지 이미지 경로 구하기
		public String getTravelImgPath(int travel_id) {
			
			String path = "";
			
			try {
				
				con = getConnection();
				
				sql = "select file from images where travel_id = ?";
				ps = con.prepareStatement(sql);
				ps.setInt(1, travel_id);
				
				rs = ps.executeQuery();
				
				if(rs.next()) {
					path = rs.getString("file");
				}
				
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				try {
					if(rs!=null) rs.close();
					if(ps!=null) ps.close();
					if(con!=null) con.close();
					
				}catch(Exception e) {
					e.printStackTrace();
				}	
			}
			
			return path;
		}
	
	// 국가 삭제시 이미지도 같이 삭제
	public void deleteCountryImages(String country_code) {
		
		try {
			con = getConnection();
			
			sql = "delete from images where country_code = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, country_code);
			ps.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs!=null) rs.close();
				if(ps!=null) ps.close();
				if(con!=null) con.close();
				
			}catch(Exception e) {
				e.printStackTrace();
			}	
		}
	}
	
	// 도시 삭제시 이미지도 같이 삭제
	public void deleteCityImages(String city_code) {
		
		try {
			con = getConnection();
			
			sql = "delete from images where city_code = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, city_code);
			ps.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs!=null) rs.close();
				if(ps!=null) ps.close();
				if(con!=null) con.close();
				
			}catch(Exception e) {
				e.printStackTrace();
			}	
		}	
	}
	
	// 국가코드 변경
	public void updateCityCode(String beforeCityCode, String newCountryCode, String newPath) {
		
		try {
			
			con = getConnection();
			
			sql = "update images set country_code = ?, file = ? where city_code = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, newCountryCode);
			ps.setString(2, newPath);
			ps.setString(3, beforeCityCode);
			
			ps.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs!=null) rs.close();
				if(ps!=null) ps.close();
				if(con!=null) con.close();
				
			}catch(Exception e) {
				e.printStackTrace();
			}	
		}	
		
	}
	
	// 도시코드 변경
	public void updateFile(String beforEnName, String newEnName, String newFileName) {
		
		try {
			
			con = getConnection();
			
			sql = "update images set file = ?, city_code = ? where city_code = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, newFileName);
			ps.setString(2, newEnName);
			ps.setString(3, beforEnName);
			
			ps.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs!=null) rs.close();
				if(ps!=null) ps.close();
				if(con!=null) con.close();
				
			}catch(Exception e) {
				e.printStackTrace();
			}	
		}	
		
	}
	
}
