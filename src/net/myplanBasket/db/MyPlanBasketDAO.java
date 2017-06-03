package net.myplanBasket.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import net.member.db.MemberBean;
import net.travel.admin.db.TravelBean;

public class MyPlanBasketDAO {
	private static final MyPlanBasketBean MyPlanBasketBean = null;

	// 디비연결
	private Connection getConnection() throws Exception {
		Connection con = null;
		// String url="jdbc:mysql://localhost:3306/jspdb";
		// String dbuser="jspid";
		// String dbpass="jsppass";
		// 1단계 드라이버로더
		// Class.forName("com.mysql.jdbc.Driver");
		// 2단계 디비연결
		// con=DriverManager.getConnection(url,dbuser,dbpass);

		// 커넥션풀 : 데이터베이스와 연결된 Connection 객체를 미리 생성하여
		// 풀속에 저장해 두고 필요할때마다 이 풀에 접근하여 Connection객체 사용
		Context init = new InitialContext();
		DataSource ds = (DataSource) init.lookup("java:comp/env/jdbc/mySQL");
		con = ds.getConnection();
		return con;
	}

	// basketAdd(basketbean)
	public void basketAdd(MyPlanBasketBean basketbean) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";
		int myplans_id = 0;
		int item_nr = 0;

		try {

			// 1,2 디비연결
			con = getConnection();
			// num 구하기 max(num)+1
			sql = "select max(myplans_id) from myplans";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				myplans_id = rs.getInt(1) + 1;
			} else {
				myplans_id = 1;
			}

			sql = "select max(item_nr) from myplans where id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, basketbean.getId());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				item_nr = rs.getInt(1) + 1;
			}

			// 3 sql insert now()
			sql = "insert into myplans values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, myplans_id);
			pstmt.setString(2, basketbean.getId());
			pstmt.setInt(3, basketbean.getPlan_nr());
			pstmt.setInt(4, basketbean.getTravel_id());
			pstmt.setInt(5, item_nr);
			pstmt.setString(6, basketbean.getFirstday());
			pstmt.setString(7, basketbean.getLastday());
			pstmt.setInt(8, basketbean.getDay_nr());
			pstmt.setString(9, basketbean.getDay_night());
			pstmt.setFloat(10, basketbean.getUser_lat());
			pstmt.setFloat(11, basketbean.getUser_lng());
			pstmt.setString(12, basketbean.getDate());
			pstmt.setString(13, basketbean.getMemo());
			pstmt.setInt(14, basketbean.getPlan_done_nr());

			// 4 실행
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

	}

	// checkGoods(basketbean)
	public int checkBasket(MyPlanBasketBean basketbean) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";
		int check = 0;
		try {
			// 1,2 디비연결
			con = getConnection();
			// 3 select
			// 조건
			sql = "select * from myplans where id=? and plan_nr=? and travel_id=? "
					+ "and user_lat=? and user_lng=? and date=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, basketbean.getId());
			pstmt.setInt(2, basketbean.getPlan_nr());
			pstmt.setInt(3, basketbean.getTravel_id());
			pstmt.setFloat(4, basketbean.getUser_lat());
			pstmt.setFloat(5, basketbean.getUser_lng());
			pstmt.setString(6, basketbean.getDate());

			// 4 rs 실행저장
			rs = pstmt.executeQuery();
			// 5 rs 데이터 있으면 check=1
			// 3 sql update
			// 조건 b_m_id b_g_num b_g_size b_g_color
			// 수정 b_g_amount
			// 4 실행
			if (rs.next()) {
				check = 1;
				sql = "update myplans set myplans_id=myplans_id+? where travel_id=? and date=? ";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, basketbean.getTravel_id());
				pstmt.setString(2, basketbean.getDate());

				pstmt.executeUpdate();
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
		}

		return check;
	}

	public String getMemberGold(String id) {

		String gold = "무료회원";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";
		int check = 0;

		try {

			con = getConnection();

			sql = "select gold from member where id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			// 4 rs 실행저장
			rs = pstmt.executeQuery();

			if (rs.next()) {
				check = rs.getInt("gold");
			}

			if (check == 1) {
				gold = "유료회원";

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
		}

		return gold; // 실제로는 gold값을 반환한다.
	}

	public MemberBean getTelName(String id) {
		MemberBean mb = new MemberBean();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";
		try {

			con = getConnection();

			sql = "select AES_DECRYPT(UNHEX(tel), 'tel') as tel, name from member where id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			// 4 rs 실행저장
			rs = pstmt.executeQuery();

			if (rs.next()) {
				mb.setTel(rs.getString("tel"));
				mb.setName(rs.getString("name"));
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
		}

		return mb;
	}

	public void insertGoldMember(String id) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";

		try {

			con = getConnection();

			sql = "update member set gold=1 where id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			// 4 rs 실행저장
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
		}
	}

	// getBasketList(id)
	public Vector getBasketList(String id) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs2 = null;
		String sql = "";
		Vector vector = new Vector();
		List basketList = new ArrayList();
		List goodsList = new ArrayList();

		try {
			// 1,2 디비연결
			con = getConnection();
			// 3 sql id에 해당하는 장바구니 정보 가져오기
			sql = "select * from myplans where id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			// 4 rs 실행 저장
			rs = pstmt.executeQuery();
			// 5 rs 데이터 있으면 장바구니 자바빈 객체 생성
			// rs => 자바빈 저장 =>basketList 배열한칸 저장
			// rs => b_g_num
			// 3 sql b_g_num 해당하는 상품정보가져오기
			// 4 rs2 pstmt2 실행저장
			// 5 rs2데이터 있으면 상품 자바빈 객체 생성
			// rs2=>자바빈 저장 => goodsList한칸 저장
			while (rs.next()) {
				MyPlanBasketBean mpbb = new MyPlanBasketBean();
				mpbb.setId(rs.getString("id"));
				mpbb.setPlan_nr(rs.getInt("plan_nr"));
				mpbb.setTravel_id(rs.getInt("travel_id"));
				mpbb.setItem_nr(rs.getInt("item_nr"));
				mpbb.setFirstday(rs.getString("firstday"));
				mpbb.setLastday(rs.getString("lastday"));
				mpbb.setDay_nr(rs.getInt("day_nr"));
				mpbb.setDay_night(rs.getString("day_night"));
				mpbb.setUser_lat(rs.getFloat("user_lat"));
				mpbb.setUser_lng(rs.getFloat("user_lng"));
				mpbb.setDate(rs.getString("date"));
				mpbb.setMemo(rs.getString("memo"));
				mpbb.setPlan_done_nr(rs.getInt("plan_done_nr"));
				basketList.add(mpbb);

				sql = "select * from travel where travel_id=?";
				pstmt2 = con.prepareStatement(sql);
				pstmt2.setInt(1, mpbb.getTravel_id());
				rs2 = pstmt2.executeQuery();
				if (rs2.next()) {
					TravelBean tb = new TravelBean();
					tb.setType(rs2.getString("type"));
					tb.setCountry_code(rs2.getString("country_code"));
					tb.setCity_code(rs2.getString("city_code"));
					tb.setName(rs2.getString("name"));
					tb.setLatitude(rs2.getFloat("latitude"));
					tb.setLongitude(rs2.getFloat("longitude"));
					tb.setInfo(rs2.getString("info"));
					tb.setAddress(rs2.getString("address"));
					goodsList.add(tb);
				}

			}
			// vector 첫번째 칸 basketList 저장
			// vector 두번째 칸 goodsList 저장
			vector.add(basketList);
			vector.add(goodsList);
			System.out.println("basketList.size" + basketList.size());
			System.out.println("goodsList.size" + goodsList.size());
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
		}
		return vector;

	}

	// modifyGoods(GoodsBean goodsbean)
	public void modifyMyPlan(MyPlanBasketBean myplanbean) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";
		try {
			// 1,2 디비연결
			con = getConnection();
			// 3 sql num 에 해당하는
			sql = "update myplans set item_nr=? where plan_nr=?,,firstday=?,lastday=?"
					+ ",day_nr=?,day_night=?,user_lat=?,user_lng=?,date=?,memo=?,plan_done_nr=? ";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, myplanbean.getItem_nr());
			pstmt.setInt(2, myplanbean.getPlan_nr());
			pstmt.setString(3, myplanbean.getFirstday());
			pstmt.setString(4, myplanbean.getLastday());
			pstmt.setInt(5, myplanbean.getDay_nr());
			pstmt.setString(6, myplanbean.getDay_night());
			pstmt.setFloat(7, myplanbean.getUser_lat());
			pstmt.setFloat(8, myplanbean.getUser_lng());
			pstmt.setString(9, myplanbean.getDate());
			pstmt.setString(10, myplanbean.getMemo());
			pstmt.setInt(11, myplanbean.getPlan_done_nr());

			// 4실행
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
		}
	}

	public MyPlanBasketBean get(int myplans_id) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";
		MyPlanBasketBean mypalnbasketbean = null;
		try {
			// 1,2 디비연결
			con = getConnection();
			// 3 sql num에 해당하는 상품정보 가져오기
			sql = "select * from myplans where myplans_id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, myplans_id);
			// 4 rs 실행 저장
			rs = pstmt.executeQuery();
			// 5 데이터 있으면 자바빈객체 생성
			// rs => 자바빈 저장
			if (rs.next()) {
				mypalnbasketbean = new MyPlanBasketBean();
				mypalnbasketbean.setId(rs.getString("id"));
				mypalnbasketbean.setPlan_nr(rs.getInt("plan_nr"));
				mypalnbasketbean.setTravel_id(rs.getInt("travel_id"));
				mypalnbasketbean.setItem_nr(rs.getInt("item_nr"));
				mypalnbasketbean.setFirstday(rs.getString("firstday"));
				mypalnbasketbean.setLastday(rs.getString("lastday"));
				mypalnbasketbean.setDay_nr(rs.getInt("day_nr"));
				mypalnbasketbean.setDay_night(rs.getString("day_night"));
				mypalnbasketbean.setUser_lat(rs.getFloat("user_lat"));
				mypalnbasketbean.setUser_lng(rs.getFloat("user_lng"));
				mypalnbasketbean.setDate(rs.getString("date"));
				mypalnbasketbean.setMemo(rs.getString("memo"));
				mypalnbasketbean.setPlan_done_nr(rs.getInt("plan_done_nr"));
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
		}

		return MyPlanBasketBean;
	}

}
