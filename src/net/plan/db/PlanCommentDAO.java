package net.plan.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

/* 국가페이지, 도시페이지 리뷰 커뮤니티관련 DB작업 */
public class PlanCommentDAO {

	Connection con = null;
	PreparedStatement ps = null;
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

	/* 국가 */
	// 리뷰 작성(국가)
	public void insertNationComment(PlanNationCommentBean pncb) {

		int num = 0;
		int nation_num = 0;

		String content = pncb.getContent().replaceAll("\n", "<br>");

		try {

			con = getConnection();

			// 전체 국가페이지 리뷰글의 num 파악
			sql = "select max(num) as num from nation_comment";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				num = rs.getInt("num") + 1; // 넣을 num 값
			}

			// 특정 국가의 num 파악
			sql = "select max(nation_num) as nation_num from nation_comment where nation=?";
			ps = con.prepareStatement(sql);
			ps.setString(1, pncb.getNation());
			rs = ps.executeQuery();
			if (rs.next()) {
				nation_num = rs.getInt("nation_num") + 1; // 넣을 nation_num 값
			}

			// DB에 넣기
			sql = "insert into nation_comment(num, nation, nation_num, nick, date, eval, content)"
					+ "values(?, ?, ?, ?, ?, ?, ?)";
			ps = con.prepareStatement(sql);
			ps.setInt(1, num);
			ps.setString(2, pncb.getNation());
			ps.setInt(3, nation_num);
			ps.setString(4, pncb.getNick());
			Timestamp date = new Timestamp(System.currentTimeMillis()); // 현재시간
			ps.setTimestamp(5, date);
			ps.setInt(6, pncb.getEval());
			ps.setString(7, pncb.getContent());

			ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (con != null)
					con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// 리뷰 갯수 구하기(국가)
	public int getCount(String nation) {

		int count = 0;

		try {
			con = getConnection();

			sql = "select count(*) as count from nation_comment where nation = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, nation);

			rs = ps.executeQuery();

			if (rs.next()) {
				count = rs.getInt("count");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (con != null)
					con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return count;
	}

	// 특정 리뷰 리스트 가져오기(국가)
	public List<PlanNationCommentBean> getNationListComment(String nation, int startRow, int pageSize) {

		List<PlanNationCommentBean> list = new ArrayList<PlanNationCommentBean>();

		try {

			con = getConnection();

			sql = "select * from nation_comment where nation = ? order by nation_num desc limit ?, ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, nation);
			ps.setInt(2, startRow - 1);
			ps.setInt(3, pageSize);
			rs = ps.executeQuery();

			while (rs.next()) {
				PlanNationCommentBean pcb = new PlanNationCommentBean();
				pcb.setContent(rs.getString("content"));
				pcb.setDate(rs.getTimestamp("date"));
				pcb.setEval(rs.getInt("eval"));
				pcb.setNation(rs.getString("nation"));
				pcb.setNation_num(rs.getInt("nation_num"));
				pcb.setNick(rs.getString("nick"));
				pcb.setNum(rs.getInt("num"));

				list.add(pcb);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (con != null)
					con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return list;
	}

	// 국가 리뷰 삭제하기
	public void deleteNationReview(int num) {

		try {

			con = getConnection();

			sql = "delete from nation_comment where num = ?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, num);

			ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (con != null)
					con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/* 도시 */
	// 리뷰 작성
	public void insertRegionComment(PlanRegionCommentBean prcb) {

		int num = 0;
		int region_num = 0;

		String content = prcb.getContent().replaceAll("\n", "<br>");

		try {

			con = getConnection();

			// 전체 국가페이지 리뷰글의 num 파악
			sql = "select max(num) as num from region_comment";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				num = rs.getInt("num") + 1; // 넣을 num 값
			}

			// 특정 도시의 num 파악
			sql = "select max(region_num) as region_num from region_comment where region=?";
			ps = con.prepareStatement(sql);
			ps.setString(1, prcb.getRegion());
			rs = ps.executeQuery();
			if (rs.next()) {
				region_num = rs.getInt("region_num") + 1; // 넣을 region_num 값
			}

			// DB에 넣기
			sql = "insert into region_comment(num, region, region_num, nick, date, eval, content)"
					+ "values(?, ?, ?, ?, ?, ?, ?)";
			ps = con.prepareStatement(sql);
			ps.setInt(1, num);
			ps.setString(2, prcb.getRegion());
			ps.setInt(3, region_num);
			ps.setString(4, prcb.getNick());
			Timestamp date = new Timestamp(System.currentTimeMillis()); // 현재시간
			ps.setTimestamp(5, date);
			ps.setInt(6, prcb.getEval());
			ps.setString(7, prcb.getContent());

			ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (con != null)
					con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	
	// 리뷰 갯수 구하기(도시)
		public int getRegionCount(String region) {

			int count = 0;

			try {
				con = getConnection();

				sql = "select count(*) as count from region_comment where region = ?";
				ps = con.prepareStatement(sql);
				ps.setString(1, region);

				rs = ps.executeQuery();

				if (rs.next()) {
					count = rs.getInt("count");
				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (rs != null)
						rs.close();
					if (ps != null)
						ps.close();
					if (con != null)
						con.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			return count;
		}

		// 특정 리뷰 리스트 가져오기(국가)
		public List<PlanRegionCommentBean> getRegionListComment(String region, int startRow, int pageSize) {

			List<PlanRegionCommentBean> list = new ArrayList<PlanRegionCommentBean>();

			try {

				con = getConnection();

				sql = "select * from region_comment where region = ? order by region_num desc limit ?, ?";
				ps = con.prepareStatement(sql);
				ps.setString(1, region);
				ps.setInt(2, startRow - 1);
				ps.setInt(3, pageSize);
				rs = ps.executeQuery();

				while (rs.next()) {
					PlanRegionCommentBean prcb = new PlanRegionCommentBean();
					prcb.setContent(rs.getString("content"));
					prcb.setDate(rs.getTimestamp("date"));
					prcb.setEval(rs.getInt("eval"));
					prcb.setRegion(rs.getString("region"));
					prcb.setRegion_num(rs.getInt("region_num"));
					prcb.setNick(rs.getString("nick"));
					prcb.setNum(rs.getInt("num"));

					list.add(prcb);
				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (rs != null)
						rs.close();
					if (ps != null)
						ps.close();
					if (con != null)
						con.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			return list;
		}

		// 국가 리뷰 삭제하기
		public void deleteRegionReview(int num) {

			try {

				con = getConnection();

				sql = "delete from region_comment where num = ?";
				ps = con.prepareStatement(sql);
				ps.setInt(1, num);

				ps.executeUpdate();

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (rs != null)
						rs.close();
					if (ps != null)
						ps.close();
					if (con != null)
						con.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	

}
