package net.plan.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class PlanDAO {

	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String sql = null;

	private Connection getConnection() throws Exception {

		Connection con = null;
		// Context 객체 생성
		Context init = new InitialContext();
		// DateSource = 디비연동 이름 불러오기
		DataSource ds = (DataSource) init.lookup("java:comp/env/jdbc/mySQL");
		// con = DataSource
		con = ds.getConnection();
		return con;
	}

	/* 국가 페이지 */
	// 국가의 도시 리스트 갯수
	public int getCityCount(String str) {
		int count = 0;
		String country_code;

		String nation = str;
		if ("한국".equals(nation)) {
			nation = "대한민국";
		}

		System.out.println("DAO getCityCount()의 nation: " + nation);

		try {
			con = getConnection();

			// country_code 값 가져오기
			sql = "select country_code from country where name=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, nation);
			rs = pstmt.executeQuery();

			if (rs.next()) { // DB에 국가가 있으면
				country_code = rs.getString("country_code");

				sql = "select count(*) as count from city where country_code=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, country_code);

				rs = pstmt.executeQuery();

				if (rs.next()) {
					count = rs.getInt("count");
				}

			} else { // 검색된 국가가 없으면
				return count;
			}

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return count;
	}

	// 국가의 도시 리스트 갯수(검색값)
	public int getCityCount(String str, String search) {
		int count = 0;
		String country_code;

		String nation = str;
		if ("한국".equals(nation)) {
			nation = "대한민국";
		}

		System.out.println("DAO getCityCount()의 nation: " + nation);

		try {
			con = getConnection();

			// country_code 값 가져오기
			sql = "select country_code from country where name=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, nation);
			rs = pstmt.executeQuery();

			if (rs.next()) { // DB에 국가가 있으면
				country_code = rs.getString("country_code");

				sql = "select count(*) as count from city where country_code=? " + "&& (name like ? || en_name like ?)";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, country_code);
				pstmt.setString(2, "%" + search + "%");
				pstmt.setString(3, "%" + search + "%");

				rs = pstmt.executeQuery();

				if (rs.next()) {
					count = rs.getInt("count");
				}

			} else { // 검색된 국가가 없으면
				return count;
			}

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return count;
	}

	/* DB 도시 개수(운영자 페이지) */
	public int getCityCount() {
		int count = 0;

		try {
			con = getConnection();
			sql = "select count(*) as count from city";
			pstmt = con.prepareStatement(sql);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				count = rs.getInt("count");
			}
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException ex) {
				}
			}
		}
		return count;
	}

	public int getTravelCount() {
		int count = 0;

		try {
			con = getConnection();
			sql = "select count(*) as count from travel";
			pstmt = con.prepareStatement(sql);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				count = rs.getInt("count");
			}
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException ex) {
				}
			}
		}
		return count;
	}

	/* DB 도시 개수(운영자 페이지) : 검색값 있을때 */
	public int getCitySearchCount(String search) {
		int count = 0;

		try {
			con = getConnection();
			sql = "select count(*) as count from city where name like ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%" + search + "%");

			rs = pstmt.executeQuery();

			if (rs.next()) {
				count = rs.getInt("count");
			}
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException ex) {
				}
			}
		}
		return count;
	}

	/* DB 도시 개수(운영자 페이지) : 검색값 있을때 */
	public int getTravelSearchCount(String search) {
		int count = 0;

		try {
			con = getConnection();
			sql = "select count(*) as count from travel where name like ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%" + search + "%");

			rs = pstmt.executeQuery();

			if (rs.next()) {
				count = rs.getInt("count");
			}
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException ex) {
				}
			}
		}
		return count;
	}

	// 도시리스트 전체 가져오기
	public List<PlanCityBean> getCityList() {

		List<PlanCityBean> cityList = new ArrayList<PlanCityBean>();
		PlanCityBean pcb = null;
		Statement pstmt = null;
		
		try {
			con = getConnection();

			// country_code 값 가져오기
			sql = "select * from city";
			pstmt = con.createStatement();
			rs = pstmt.executeQuery(sql);

			
			
			while (rs.next()) {
				pcb = new PlanCityBean();

				pcb.setCity_code(rs.getString("city_code"));
				pcb.setName(rs.getString("name"));
				pcb.setEn_name(rs.getString("en_name"));
				pcb.setInfo(rs.getString("info"));
				pcb.setCountry_code(rs.getString("country_code"));

				cityList.add(pcb);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return cityList;
	}

	// 국가페이지의 도시리스트 전체 가져오기
	public List<PlanCityBean> getCityList(String str) {

		String country_code = "";
		List<PlanCityBean> list = new ArrayList<PlanCityBean>();

		String nation = str;
		if ("한국".equals(nation)) {
			nation = "대한민국";
		}

		try {
			con = getConnection();

			// country_code 값 가져오기
			sql = "select country_code from country where name=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, nation);
			rs = pstmt.executeQuery();

			if (rs.next()) { // 검색된 국가가 있으면
				country_code = rs.getString("country_code");

				sql = "select city_code, name, en_name, info, country_code from city where country_code = ?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, country_code);

				rs = pstmt.executeQuery();

				PlanCityBean pcb = null;
				while (rs.next()) {
					pcb = new PlanCityBean();

					pcb.setCity_code(rs.getString("city_code"));
					pcb.setName(rs.getString("name"));
					pcb.setEn_name(rs.getString("en_name"));
					pcb.setInfo(rs.getString("info"));
					pcb.setCountry_code(rs.getString("country_code"));

					list.add(pcb);
				}

			} else {
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return list;
	}

	// 국가페이지의 도시리스트 가져오기
	public List<PlanCityBean> getCityList(String str, int startRow, int pageSize) {

		String country_code = "";
		List<PlanCityBean> list = new ArrayList<PlanCityBean>();

		String nation = str;
		if ("한국".equals(nation)) {
			nation = "대한민국";
		}

		try {
			con = getConnection();

			// country_code 값 가져오기
			sql = "select country_code from country where name=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, nation);
			rs = pstmt.executeQuery();

			if (rs.next()) { // 검색된 국가가 있으면
				country_code = rs.getString("country_code");

				sql = "select city_code, name, en_name, info, country_code from city where country_code = ? limit ?, ?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, country_code);
				pstmt.setInt(2, startRow - 1);
				pstmt.setInt(3, pageSize);

				rs = pstmt.executeQuery();

				PlanCityBean pcb = null;
				while (rs.next()) {
					pcb = new PlanCityBean();

					pcb.setCity_code(rs.getString("city_code"));
					pcb.setName(rs.getString("name"));
					pcb.setEn_name(rs.getString("en_name"));
					pcb.setInfo(rs.getString("info"));
					pcb.setCountry_code(rs.getString("country_code"));

					list.add(pcb);
				}

			} else {
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return list;
	}

	// 국가페이지의 도시리스트 가져오기(검색값)
	public List<PlanCityBean> getCityList(String str, int startRow, int pageSize, String search) {

		String country_code = "";
		List<PlanCityBean> list = new ArrayList<PlanCityBean>();

		String nation = str;
		if ("한국".equals(nation)) {
			nation = "대한민국";
		}

		try {
			con = getConnection();

			// country_code 값 가져오기
			sql = "select country_code from country where name=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, nation);
			rs = pstmt.executeQuery();

			if (rs.next()) { // 검색된 국가가 있으면
				country_code = rs.getString("country_code");

				sql = "select city_code, name, en_name, info, country_code from city where country_code = ? "
						+ "&& (name like ? || en_name like ? ) limit ?, ?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, country_code);
				pstmt.setString(2, "%" + search + "%");
				pstmt.setString(3, "%" + search + "%");
				pstmt.setInt(4, startRow - 1);
				pstmt.setInt(5, pageSize);

				rs = pstmt.executeQuery();

				PlanCityBean pcb = null;
				while (rs.next()) {
					pcb = new PlanCityBean();

					pcb.setCity_code(rs.getString("city_code"));
					pcb.setName(rs.getString("name"));
					pcb.setEn_name(rs.getString("en_name"));
					pcb.setInfo(rs.getString("info"));
					pcb.setCountry_code(rs.getString("country_code"));

					list.add(pcb);
				}

			} else {
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return list;
	}

	/* DB 도시리스트 뽑아오기 (운영자 페이지) : 검색값 */
	public List<PlanCityBean> getCitySearchList(int startRow, int pageSize, String search, int sort) {
		List<PlanCityBean> cityList = new ArrayList();
		PlanCityBean cb = null;
		String sql = "";

		try {
			con = getConnection();

			if (sort == 1)
				sql = "select * from city where name like ? order by city_code asc limit ?,?";
			else if (sort == 2)
				sql = "select * from city where name like ? order by city_code desc limit ?,?";
			else if (sort == 3)
				sql = "select * from city where name like ? order by name asc limit ?,?";
			else if (sort == 4)
				sql = "select * from city where name like ? order by name desc limit ?,?";
			else if (sort == 5)
				sql = "select * from city where name like ? order by country_code asc limit ?,?";
			else if (sort == 6)
				sql = "select * from city where name like ? order by country_code desc limit ?,?";
			else if (sort == 7)
				sql = "select * from city where name like ? order by en_name asc limit ?,?";
			else if (sort == 8)
				sql = "select * from city where name like ? order by en_name desc limit ?,?";
			else
				sql = "select * from city where name like ? limit ?,?"; // 정렬 값
																		// 없을때

			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%" + search + "%");
			pstmt.setInt(2, startRow - 1);// 시작행-1
			pstmt.setInt(3, pageSize);// 몇개글

			rs = pstmt.executeQuery();

			while (rs.next()) {
				cb = new PlanCityBean();

				cb.setCountry_code(rs.getString("country_code"));
				cb.setName(rs.getString("name"));
				cb.setInfo(rs.getString("info"));
				cb.setCity_code(rs.getString("city_code"));
				cb.setEn_name(rs.getString("en_name"));

				cityList.add(cb);
			}

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException ex) {
				}
			}
		}
		return cityList;

	}

	/* 메인페이지 국가리스트 뽑아오기(메인페이지) */
	public List<PlanCountryBean> getCountryList() {
		List<PlanCountryBean> countryList = new ArrayList();
		PlanCountryBean cb = null;
		Statement stmt = null;

		try {
			con = getConnection();

			sql = "select * from country order by name asc";

			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				cb = new PlanCountryBean();

				cb.setCountry_code(rs.getString("country_code"));
				cb.setName(rs.getString("name"));
				cb.setInfo(rs.getString("info"));
				cb.setContinent(rs.getString("continent"));
				cb.setEn_name(rs.getString("en_name"));

				countryList.add(cb);
			}

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException ex) {
				}
			}
		}
		return countryList;

	}

	/* DB 도시리스트 뽑아오기 (운영자 페이지) : 검색값 */
	public List<PlanTravelBean> getTravelList(int startRow, int pageSize, String search, int sort) {
		List<PlanTravelBean> travelList = new ArrayList();
		PlanTravelBean tb = null;
		String sql = "";

		try {
			con = getConnection();

			if (sort == 1)
				sql = "select * from travel where name like ? order by city_code asc limit ?,?";
			else if (sort == 2)
				sql = "select * from travel where name like ? order by city_code desc limit ?,?";
			else if (sort == 3)
				sql = "select * from travel where name like ? order by name asc limit ?,?";
			else if (sort == 4)
				sql = "select * from travel where name like ? order by name desc limit ?,?";
			else if (sort == 5)
				sql = "select * from travel where name like ? order by country_code asc limit ?,?";
			else if (sort == 6)
				sql = "select * from travel where name like ? order by country_code desc limit ?,?";
			else if (sort == 7)
				sql = "select * from travel where name like ? order by type asc limit ?,?";
			else if (sort == 8)
				sql = "select * from travel where name like ? order by type desc limit ?,?";
			else
				sql = "select * from travel where name like ? limit ?,?"; // 정렬
																			// 값
																			// 없을때

			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%" + search + "%");
			pstmt.setInt(2, startRow - 1);// 시작행-1
			pstmt.setInt(3, pageSize);// 몇개글

			rs = pstmt.executeQuery();

			while (rs.next()) {
				tb = new PlanTravelBean();

				tb.setConuntry_code(rs.getString("country_code"));
				tb.setName(rs.getString("name"));
				tb.setInfo(rs.getString("info"));
				tb.setCity_code(rs.getString("city_code"));
				tb.setType(rs.getString("type"));

				travelList.add(tb);
			}

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException ex) {
				}
			}
		}
		return travelList;

	}

	/* DB 국가 개수(운영자 페이지) */
	public int getCountryCount() {
		int count = 0;

		try {
			con = getConnection();
			sql = "select count(*) as count from country";
			pstmt = con.prepareStatement(sql);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				count = rs.getInt("count");
			}
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException ex) {
				}
			}
		}
		return count;
	}

	/* DB 국가 개수(운영자 페이지 : 검색값 포함) */
	public int getCountryCount(String search) {
		int count = 0;

		try {
			con = getConnection();
			sql = "select count(*) as count from country where name like ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%" + search + "%");

			rs = pstmt.executeQuery();

			if (rs.next()) {
				count = rs.getInt("count");
			}
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException ex) {
				}
			}
		}
		return count;
	}

	/* DB 국가리스트 뽑아오기 (운영자 페이지) */
	public List<PlanCountryBean> getCountryList(int startRow, int pageSize) {
		List<PlanCountryBean> countryList = new ArrayList();
		PlanCountryBean cb = null;
		String sql = "";

		try {
			con = getConnection();

			sql = "select * from country order by name asc limit ?,?";

			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, startRow - 1);// 시작행-1
			pstmt.setInt(2, pageSize);// 몇개글

			rs = pstmt.executeQuery();

			while (rs.next()) {
				cb = new PlanCountryBean();

				cb.setCountry_code(rs.getString("country_code"));
				cb.setName(rs.getString("name"));
				cb.setInfo(rs.getString("info"));
				cb.setContinent(rs.getString("continent"));
				cb.setEn_name(rs.getString("en_name"));

				countryList.add(cb);
			}

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException ex) {
				}
			}
		}
		return countryList;

	}

	/* DB 국가리스트 뽑아오기 (운영자 페이지 : 검색값) */
	public List<PlanCountryBean> getCountryList(int startRow, int pageSize, String search, int sort) {
		List<PlanCountryBean> countryList = new ArrayList();
		PlanCountryBean cb = null;
		String sql = "";

		try {
			con = getConnection();

			// sort-> 0:정렬x 기본값, 1:국가코드 오름차순 ,2:국가코드 내림차순, 3:국가이름 오름차순, 4:국가이름
			// 내림차순, 5:대륙별 오름차순, 6:대륙별 내림차순
			// 7:영문이름 오름차순, 8:영문이름 내림차순
			if (sort == 1)
				sql = "select * from country where name like ? order by country_code asc limit ?,?";
			else if (sort == 2)
				sql = "select * from country where name like ? order by country_code desc limit ?,?";
			else if (sort == 3)
				sql = "select * from country where name like ? order by name asc limit ?,?";
			else if (sort == 4)
				sql = "select * from country where name like ? order by name desc limit ?,?";
			else if (sort == 5)
				sql = "select * from country where name like ? order by continent asc limit ?,?";
			else if (sort == 6)
				sql = "select * from country where name like ? order by continent desc limit ?,?";
			else if (sort == 7)
				sql = "select * from country where name like ? order by en_name asc limit ?,?";
			else if (sort == 8)
				sql = "select * from country where name like ? order by en_name desc limit ?,?";
			else
				sql = "select * from country where name like ? limit ?,?"; // 정렬
																			// 값
																			// 없을때

			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%" + search + "%"); // 검색값
			pstmt.setInt(2, startRow - 1);// 시작행-1
			pstmt.setInt(3, pageSize);// 몇개글

			rs = pstmt.executeQuery();

			while (rs.next()) {
				cb = new PlanCountryBean();

				cb.setCountry_code(rs.getString("country_code"));
				cb.setName(rs.getString("name"));
				cb.setInfo(rs.getString("info"));
				cb.setContinent(rs.getString("continent"));
				cb.setEn_name(rs.getString("en_name"));

				countryList.add(cb);
			}

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException ex) {
				}
			}
		}
		return countryList;

	}

	/* 국가 추가하기(운영자페이지) */
	public void insertCountry(PlanCountryBean pcb) {
		Connection con = null;

		try {

			con = getConnection();
			sql = "insert into country(country_code, name, info, continent, en_name) values(?,?,?,?,?)";
			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, pcb.getCountry_code());
			pstmt.setString(2, pcb.getName());
			pstmt.setString(3, pcb.getInfo());
			pstmt.setString(4, pcb.getContinent());
			pstmt.setString(5, pcb.getEn_name());

			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException ex) {
				}
			}

		}

	}

	// 국가코드 구하기
	public String getCountyCode(String nation) {
		PlanCountryBean pcb = null;
		Connection con = null;

		String country_code = "";

		try {
			con = getConnection();
			sql = "select country_code from country where name=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, nation);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				country_code = rs.getString("country_code");
			}

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return country_code;
	}
	
	// 도시코드 구하기
	public String getCityCode(String region) {
		PlanCountryBean pcb = null;
		Connection con = null;
	
		String city_code = "";

		try {
			con = getConnection();
			sql = "select city_code from city where name=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, region);

			rs = pstmt.executeQuery();
	
			if (rs.next()) {
				city_code = rs.getString("city_code");
			}

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();

		} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return city_code;
	}


	/* 수정할 국가 가져오기(운영자 페이지) */
	public PlanCountryBean getCountry(String country_code) {
		PlanCountryBean pcb = null;
		Connection con = null;

		try {
			con = getConnection();
			sql = "select * from country where country_code=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, country_code);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				pcb = new PlanCountryBean();
				pcb.setContinent(rs.getString("continent"));
				pcb.setCountry_code(rs.getString("country_code"));
				pcb.setEn_name(rs.getString("en_name"));
				pcb.setInfo(rs.getString("info"));
				pcb.setName(rs.getString("name"));
			}

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException ex) {
				}
			}
		}
		return pcb;
	}

	/* 국가 DB수정(운영자 페이지) */
	public int updateCountry(PlanCountryBean pcb, String beforeCountryCode) {
		int check = 0;

		System.out.println("country_code: " + pcb.getCountry_code());

		try {
			con = getConnection();
			sql = "select * from country where country_code=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, beforeCountryCode);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				check = 1;
				sql = "update country set country_code=?, name=?, info=?, continent=?, en_name=? where country_code=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, pcb.getCountry_code());
				pstmt.setString(2, pcb.getName());
				pstmt.setString(3, pcb.getInfo());
				pstmt.setString(4, pcb.getContinent());
				pstmt.setString(5, pcb.getEn_name());
				pstmt.setString(6, beforeCountryCode);

				pstmt.executeUpdate();
			}

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException ex) {
				}
			}
		}
		return check;

	}

	/* DB 국가 삭제 (운영자 페이지) */
	public int deleteCountry(String country_code) {
		int check = 0;
		try {
			con = getConnection();

			sql = "delete from country where country_code=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, country_code);

			pstmt.executeUpdate();
			check = 1;
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException ex) {
				}
			}
		}
		return check;
	}

	/* 지역페이지 */
	public int getTravelCount(String region, String city_code) {

		ResultSet rs = null;
		int count = 0;
		try {
			// 1,2디비연결 메서드호출
			con = getConnection();
			// num 게시판 글번호 구하기
			// sql 함수 최대값 구하기 max()

			sql = "select count(travel_id) from travel where address like ? or city_code like ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%" + region + "%");
			pstmt.setString(2, city_code);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				count = rs.getInt(1);
			}

			// 3. sql insert 디비날짜 now()
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException ex) {
				}
			}
		}
		return count;
	}// getTravelCount() end

	public int getTravelCount(String region, String city_code, String search) {
		int count = 0;

		if ("관광지".equals(search) || "관광".equals(search)) {
			search = "p";
		} else if ("호텔".equals(search) || "숙소".equals(search)) {
			search = "h";
		} else if ("맛집".equals(search)) {
			search = "r";
		}

		try {
			con = getConnection();
			if (search.equals("rph")) {
				sql = "select count(*) as count from travel where city_code like ?";

				pstmt = con.prepareStatement(sql);

				pstmt.setString(1, city_code);

			} else if (search.equals("rp") || search.equals("rh") || search.equals("ph")) {
				sql = "select count(*) as count from travel where city_code like ? and (type like ? or type like ?)";

				pstmt = con.prepareStatement(sql);

				pstmt.setString(1, city_code);
				pstmt.setString(2, search.substring(0, 1));
				pstmt.setString(3, search.substring(1, 2));
			} else {
				sql = "select count(*) as count from travel where city_code like ? and (name like ? or type like ?)";

				pstmt = con.prepareStatement(sql);

				pstmt.setString(1, city_code);
				pstmt.setString(2, "%" + search + "%");
				pstmt.setString(3, "%" + search + "%");
			}

			rs = pstmt.executeQuery();

			if (rs.next()) {
				count = rs.getInt("count");
			}

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return count;
	}

	public List<PlanTravelBean> getTravelList(int startRow, int pageSize, String region, String city_code) {
		ResultSet rs = null;
		List<PlanTravelBean> planTravelList = new ArrayList<PlanTravelBean>();
		try {

			// 1,2디비연결 메서드호출
			con = getConnection();
			// num 게시판 글번호 구하기
			// sql 함수 최대값 구하기 max()
			sql = "select * from travel where address like ? or city_code like ? order by travel_id desc limit ?, ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%" + region + "%");
			pstmt.setString(2, city_code);
			pstmt.setInt(3, startRow - 1);
			pstmt.setInt(4, pageSize);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				PlanTravelBean ptb = new PlanTravelBean();
				ptb.setTravel_id(rs.getInt(1));
				ptb.setType(rs.getString(2));
				ptb.setConuntry_code(rs.getString(3));
				ptb.setCity_code(rs.getString(4));
				ptb.setName(rs.getString(5));
				ptb.setLatitude(rs.getFloat(6));
				ptb.setLongitude(rs.getFloat(7));
				ptb.setInfo(rs.getString(8));
				ptb.setAddress(rs.getString(9));
				ptb.setFile(rs.getString(10));
				planTravelList.add(ptb);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException ex) {
				}
			}
		}
		return planTravelList;
	} // getBoardList() end

	public List<PlanTravelBean> getTravelList(int startRow, int pageSize, String region, String search,
			String city_code) {
		ResultSet rs = null;
		List<PlanTravelBean> planTravelList = new ArrayList<PlanTravelBean>();

		if ("관광지".equals(search) || "관광".equals(search)) {
			search = "p";
		} else if ("호텔".equals(search) || "숙소".equals(search)) {
			search = "h";
		} else if ("맛집".equals(search)) {
			search = "r";
		}

		try {

			// 1,2디비연결 메서드호출
			con = getConnection();
			if (search.equals("rph")) {
				sql = "select * from travel where city_code like ? order by travel_id desc limit ?, ?";

				pstmt = con.prepareStatement(sql);

				pstmt.setString(1, city_code);
				pstmt.setInt(2, startRow - 1);
				pstmt.setInt(3, pageSize);

			} else if (search.equals("rp") || search.equals("rh") || search.equals("ph")) {
				sql = "select * from travel where city_code like ? and (type like ? or type like ?) order by travel_id desc limit ?, ?";

				pstmt = con.prepareStatement(sql);

				pstmt.setString(1, city_code);
				pstmt.setString(2, search.substring(0, 1));
				pstmt.setString(3, search.substring(1, 2));

				pstmt.setInt(4, startRow - 1);
				pstmt.setInt(5, pageSize);
			} else {
				sql = "select * from travel where city_code like ? and (name like ? or type like ?) order by travel_id desc limit ?, ?";
				pstmt = con.prepareStatement(sql);

				pstmt.setString(1, city_code);
				pstmt.setString(2, "%" + search + "%");
				pstmt.setString(3, "%" + search + "%");

				pstmt.setInt(4, startRow - 1);
				pstmt.setInt(5, pageSize);
			}

			rs = pstmt.executeQuery();

			while (rs.next()) {
				PlanTravelBean ptb = new PlanTravelBean();
				ptb.setTravel_id(rs.getInt(1));
				ptb.setType(rs.getString(2));
				ptb.setConuntry_code(rs.getString(3));
				ptb.setCity_code(rs.getString(4));
				ptb.setName(rs.getString(5));
				ptb.setLatitude(rs.getFloat(6));
				ptb.setLongitude(rs.getFloat(7));
				ptb.setInfo(rs.getString(8));
				ptb.setAddress(rs.getString(9));
				ptb.setFile(rs.getString(10));
				planTravelList.add(ptb);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException ex) {
				}
			}
		}
		return planTravelList;
	} // getBoardList() end

	// 도시 추가하기
	public void insertCity(PlanCityBean pcb) {
		PlanCityBean cb = null;
		Connection con = null;
		String sql = "";
		ResultSet rs = null;
		Statement stmt = null;

		try {

			con = getConnection();
			sql = "insert into city(country_code, name, info, city_code, en_name) values(?,?,?,?,?)";
			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, pcb.getCountry_code());
			pstmt.setString(2, pcb.getName());
			pstmt.setString(3, pcb.getInfo());
			pstmt.setString(4, pcb.getCity_code());
			pstmt.setString(5, pcb.getEn_name());

			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException ex) {
				}
			}
		}

	}

	/* 수정할 도시 가져오기(운영자 페이지) */
	public PlanCityBean getCityContent(String city_code) {
		PlanCityBean pcb = null;
		Connection con = null;

		try {
			con = getConnection();
			sql = "select * from city where city_code=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, city_code);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				pcb = new PlanCityBean();
				pcb.setCity_code(rs.getString("city_code"));
				pcb.setCountry_code(rs.getString("country_code"));
				pcb.setEn_name(rs.getString("en_name"));
				pcb.setInfo(rs.getString("info"));
				pcb.setName(rs.getString("name"));
			}

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException ex) {
				}
			}
		}
		return pcb;
	}

	/* 국가 DB수정(운영자 페이지) */
	public int updateCity(PlanCityBean pcb, String beforeCityCode) {
		int check = 0;

		try {
			con = getConnection();
			sql = "select * from city where city_code=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, beforeCityCode);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				check = 1;
				sql = "update city set country_code=?, name=?, info=?, city_code=?, en_name=? where city_code=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, pcb.getCountry_code());
				pstmt.setString(2, pcb.getName());
				pstmt.setString(3, pcb.getInfo());
				pstmt.setString(4, pcb.getCity_code());
				pstmt.setString(5, pcb.getEn_name());
				pstmt.setString(6, beforeCityCode);

				pstmt.executeUpdate();
			}

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException ex) {
				}
			}
		}
		return check;

	}

	/* 추천장소 상세히 보여주기 */
	public PlanTravelBean getTravel(String travel) {
		PlanTravelBean ptb = new PlanTravelBean();

		try {
			con = getConnection();

			sql = "select * from travel where name=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, travel);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				ptb.setTravel_id(rs.getInt("travel_id"));
				ptb.setType(rs.getString("type"));
				ptb.setConuntry_code(rs.getString("country_code"));
				ptb.setCity_code(rs.getString("city_code"));
				ptb.setName(rs.getString("name"));
				ptb.setLatitude(rs.getFloat("latitude"));
				ptb.setLongitude(rs.getFloat("longitude"));
				ptb.setInfo(rs.getString("info"));
				ptb.setAddress(rs.getString("address"));
				ptb.setFile(rs.getString("file"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException ex) {
				}
			}
		}

		return ptb;
	}

	/* 추천장소 이미지 */
	public List getSpotImages(int travel_id) {
		List spotimagelist = new ArrayList();

		try {
			con = getConnection();

			sql = "select * from images where travel_id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, travel_id);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				/* 이미지 리스트 만들기 */
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException ex) {
				}
			}
		}
		return spotimagelist;
	}

	/* DB 도시 삭제 (운영자 페이지) */
	public int deleteCity(String city_code) {
		int check = 0;
		try {
			con = getConnection();

			sql = "delete from city where city_code=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, city_code);

			pstmt.executeUpdate();
			check = 1;
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException ex) {
				}
			}
		}
		return check;
	}

	// 도시 정보 가져오기
	public PlanCityBean getCity(String region) {

		PlanCityBean pcb = new PlanCityBean();
		;

		try {
			con = getConnection();

			sql = "select city_code, name, info, country_code, en_name from city where name = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, region);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				pcb.setCity_code(rs.getString("city_code"));
				pcb.setCountry_code(rs.getString("country_code"));
				pcb.setEn_name(rs.getString("en_name"));
				pcb.setInfo(rs.getString("info"));
				pcb.setName(rs.getString("name"));
			} else {
				pcb.setCity_code("");
				pcb.setCountry_code("");
				pcb.setEn_name("");
				pcb.setInfo("");
				pcb.setName("");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException ex) {
				}
			}
		}

		return pcb;
	}

	/* 각 대륙에 맞는 도시 개수 */
	public int getCityCount_con(String continent) {
		int count = 0;
		String country_code = "";
		try {
			con = getConnection();

			// country_code 값 가져오기
			sql = "select country_code from country where continent=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, continent);
			rs = pstmt.executeQuery();

			while (rs.next()) { // DB에 국가가 있으면
				country_code = rs.getString("country_code");

				sql = "select count(*) as count from city where country_code=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, country_code);

				rs = pstmt.executeQuery();

				if (rs.next()) {
					count = count + rs.getInt("count");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return count;
	}

	/* 각대륙에 맞는 도시리스트 뽑아오기 */
	public List<PlanCityBean> getCityList_con(String continent) {
		List<PlanCityBean> cityList = new ArrayList();
		PlanCityBean cb = null;
		String sql = "";
		String country_code = "";
		ResultSet rs2 = null;

		try {
			con = getConnection();
			if (continent.equals("All")) {// 도시 전체
				sql = "select * from city";
				pstmt = con.prepareStatement(sql);
				rs = pstmt.executeQuery();

				while (rs.next()) {
					cb = new PlanCityBean();

					cb.setCountry_code(rs.getString("country_code"));
					cb.setName(rs.getString("name"));
					cb.setInfo(rs.getString("info"));
					cb.setCity_code(rs.getString("city_code"));
					cb.setEn_name(rs.getString("en_name"));

					cityList.add(cb);
				}
			} else {// 대륙에 맞는 도시 리스트
				sql = "select country_code from country where continent=?";

				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, continent);
				rs = pstmt.executeQuery();

				while (rs.next()) { // DB에 국가가 있으면
					country_code = rs.getString("country_code");

					sql = "select * from city where country_code=?";
					pstmt = con.prepareStatement(sql);
					pstmt.setString(1, country_code);

					rs2 = pstmt.executeQuery();

					while (rs2.next()) {
						cb = new PlanCityBean();
						cb.setCountry_code(rs2.getString("country_code"));
						cb.setName(rs2.getString("name"));
						cb.setInfo(rs2.getString("info"));
						cb.setCity_code(rs2.getString("city_code"));
						cb.setEn_name(rs2.getString("en_name"));

						cityList.add(cb);
					}
				}

			}

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			}
			if (rs2 != null) {
				try {
					rs2.close();
				} catch (SQLException ex) {
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException ex) {
				}
			}
		}
		return cityList;

	}

	/* 기념품 리스트 뽑아 오기 */
	public List<PlanSouvenirBean> getSouvenirList(String city_code) {
		List<PlanSouvenirBean> souvenirList = new ArrayList<PlanSouvenirBean>();
		PlanSouvenirBean psb = null;

		try {
			con = getConnection();

			// 도시에 맞는 기념품 리스트 가져오기
			sql = "select * from souvenir where city_code=? order by ranking asc";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, city_code);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				psb = new PlanSouvenirBean();

				psb.setCity_code(city_code);
				psb.setImg(rs.getString("sou_img"));
				psb.setName(rs.getString("sou_name"));
				psb.setNum(rs.getInt("num"));
				psb.setRanking(rs.getInt("ranking"));
				psb.setInfo(rs.getString("info"));

				souvenirList.add(psb);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException ex) {
				}
			}
		}

		return souvenirList;
	}

	/* 기념품 리스트 추가하기 */
	public void addSouvenir() {

	}

}
