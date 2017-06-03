package net.QandA.db;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import net.board.db.boardBean;
import net.like.db.LikeBean;
import net.reply.db.ReplyBean;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QandADAO {
	Connection con = null;
	PreparedStatement pstmt = null;
	String sql = "";
	ResultSet rs = null;

	// 커넥션 풀(디비연결작업)
	private Connection getConnection() throws Exception {
		Connection con = null;
		Context init = new InitialContext();
		DataSource ds = (DataSource) init.lookup("java:comp/env/jdbc/mySQL");
		con = ds.getConnection();
		return con;
	}

	public void insertQandA(QandABean qb) {
		int num = 0;

		try {
			con = getConnection();
			sql = "select max(num) from qanda";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				num = rs.getInt(1) + 1;
			}
			sql = "insert into qanda(num,nick,subject,content,image1,re_ref,re_lev,re_seq,readcount,date) values(?,?,?,?,?,?,?,?,?,now())";

			pstmt = con.prepareStatement(sql);

			pstmt.setInt(1, num);
			pstmt.setString(2, qb.getNick());
			pstmt.setString(3, qb.getSubject());
			pstmt.setString(4, qb.getContent());
			pstmt.setString(5, qb.getImage1());
			pstmt.setInt(6,num);//re_ref 답변글 그룹==일반글의 글번호
			pstmt.setInt(7, 0);//re_lev 답변글 들여쓰기,0:맨위, 일반글 들여쓰기 없음
			pstmt.setInt(8, 0);//re_seq 답변글 순서 일반글순서 맨위
			pstmt.setInt(9, 0);//처음조회수는0


			// date는 위에서 now()로 생성됨

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

	}// insertBoard 메소드

	// 게시한글이 몇개인지 세주는 메소드(BoardListAction에서 호출됨)
	public int getQandAcount() {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "";
		ResultSet rs = null;
		int count = 0;
		try {
			// 1,2 디비연결 메서드 호출
			con = getConnection();
			// 3 sql 함수 count(*) 이용
			sql = "select count(*) from qanda";

			pstmt = con.prepareStatement(sql);
			// 4 rs실행저장
			rs = pstmt.executeQuery();
			// 5 데이터있으면 count저장

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

	// 게시판에 글들을 게시할때 필요한 메소드
	// (BoardListAction에서 호출됨, 게시판에 게시할변수가 많으므로 list형으로 return)
	public List getQandAList(int startrow, int pageSize) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "";
		ResultSet rs = null;

		// 배열(컬렉션)객체 생성- 여러개의 기억공간 사용+기억공간 추가해서 사용
		List<QandABean> QandAList = new ArrayList<QandABean>();
		try {
			// 1,2 디비연결 메서드 호출
			con = getConnection();
			// 3 sql gram의모든 데이터 가져오기(select*from gram)
			// num에 의한 내림차순 정렬(order by num)
			// 글 잘라 오기 limit(시작행-1, 개수) startrow을 기준으로 pageSize만큼 자른다!
			sql = "select *from qanda order by re_ref desc, re_seq asc limit ?, ? ";

			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, startrow - 1);
			pstmt.setInt(2, pageSize);
			// 4 결과 rs에 저장
			rs = pstmt.executeQuery();

			while (rs.next()) {
				QandABean qb = new QandABean();
				
				System.out.println(rs.getInt("num"));

				
				qb.setNum(rs.getInt("num"));
				qb.setNick(rs.getString("nick"));
				qb.setSubject(rs.getString("subject"));
				qb.setContent(rs.getString("content"));
				qb.setImage1(rs.getString("image1"));
				qb.setRe_lev(rs.getInt("re_lev"));
				qb.setRe_seq(rs.getInt("re_seq"));
				qb.setRe_ref(rs.getInt("re_ref"));
				qb.setReadcount(rs.getInt("readcount"));
				qb.setDate(rs.getDate("date"));

				// boardList 한칸 저장
				QandAList.add(qb);			
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
		return QandAList;
	}

	//컨텐트 내용을 불러옴
	 public QandABean getQandA(int num) {
		 QandABean qb=null;
	 try{
	 con = getConnection();
	 sql = "select *from qanda where num=?";
	 pstmt = con.prepareStatement(sql);
	 pstmt.setInt(1,num);
	
	 rs = pstmt.executeQuery();
	
	 if (rs.next()) {
	 qb=new QandABean();
	
		qb.setNum(rs.getInt("num"));
		qb.setNick(rs.getString("nick"));
		qb.setSubject(rs.getString("subject"));
		qb.setContent(rs.getString("content"));
		qb.setImage1(rs.getString("image1"));
		qb.setRe_lev(rs.getInt("re_lev"));
		qb.setRe_seq(rs.getInt("re_seq"));
		qb.setRe_ref(rs.getInt("re_ref"));
		qb.setReadcount(rs.getInt("readcount"));
		qb.setDate(rs.getDate("date")); 

	 }
	
	
	 } catch (Exception e) {
	 e.printStackTrace();
	 } finally {
	 if (rs != null)
	 try {rs.close();} catch (SQLException ex) { }
	 if (pstmt != null)
	 try {pstmt.close();} catch (SQLException ex) {}
	 if (con != null)
	 try {con.close();} catch (SQLException ex) {}
	
	 }//finally
	 return qb;
	 }
	 
	
	 
	 
	
	 //게시글 수정하는 메소드
	 public void updateQandA(QandABean qb){
	
	 try{
	 con = getConnection();	
	 sql = "update qanda set nick=?, subject=?, content=?, image1=? where num=?";
	 pstmt=con.prepareStatement(sql);
	
	 pstmt.setString(1, qb.getNick());
	 pstmt.setString(2, qb.getSubject());
	 pstmt.setString(3, qb.getContent());
	 pstmt.setString(4, qb.getImage1());
	 pstmt.setInt(5,qb.getNum());
	
	 pstmt.executeUpdate();
	
	 } catch (Exception e) {
	 e.printStackTrace();
	 } finally {
	 if (rs != null)
	 try {rs.close();} catch (SQLException ex) { }
	 if (pstmt != null)
	 try {pstmt.close();} catch (SQLException ex) {}
	 if (con != null)
	 try {con.close();} catch (SQLException ex) {}
	 }//finally
	 }

	 
	 
	// 게시글 삭제하는 메소드
	 public void deleteqna(int num){
	
	
	 Connection con = null;
	 PreparedStatement pstmt = null;
	 String sql = "";
	 ResultSet rs = null;
	 try {
	 con = getConnection();
	
	 sql = "delete from qanda where num=?";
	 pstmt = con.prepareStatement(sql);
	 pstmt.setInt(1, num);
	 pstmt.executeUpdate();
	
	 } catch (Exception e) {
	 e.printStackTrace();
	 } finally {
	 if (rs != null)
	 try {rs.close();} catch (SQLException ex) { }
	 if (pstmt != null)
	 try {pstmt.close();} catch (SQLException ex) {}
	 if (con != null)
	 try {con.close();} catch (SQLException ex) {}
	 }	
	 }
	 
	 
	//
	//
	//// gram 테이블에 해당글의 좋아요수를 증가시켜주는 메소드
	// public void GramAddLike(boardBean bb) {
	// int love = 0;
	// try {
	// con = getConnection();
	// //좋아요를 누르면: 해당 num의 글에서 최대값을 구하고 거기 +1이 되어 총 좋아요갯수에서 1이 증가함
	// sql = "select max(love)as max from gram where num=?";
	// pstmt = con.prepareStatement(sql);
	// pstmt.setInt(1,bb.getNum());
	// rs = pstmt.executeQuery();
	//
	// if (rs.next()) {love = rs.getInt("max") + 1;}
	// //증가한 1값을 DB에 update하는 sql구문
	// sql = "update gram set love=? where num=?";
	// pstmt = con.prepareStatement(sql);
	// pstmt.setInt(1, love);
	// pstmt.setInt(2, bb.getNum());
	//
	//
	// pstmt.executeUpdate();
	//
	// } catch (Exception e) {
	// e.printStackTrace();
	// } finally {
	// if (rs != null)
	// try {
	// rs.close();
	// } catch (SQLException ex) {
	// }
	// if (pstmt != null)
	// try {
	// pstmt.close();
	// } catch (SQLException ex) {
	// }
	// if (con != null)
	// try {
	// con.close();
	// } catch (SQLException ex) {
	// }
	// } // finally
	//
	// }
	//
	//
	//// 좋아요 취소 구현, gram 테이블에서 총 좋아요갯수에서 -1를 구현
	// public void GramDisLike(boardBean bb) {
	// int love = bb.getLove();
	//
	//
	// try {
	// con = getConnection();
	// sql = "select max(love) from gram where num=?";
	// pstmt = con.prepareStatement(sql);
	// pstmt.setInt(1,bb.getNum());
	//
	// rs = pstmt.executeQuery();
	// if (rs.next()) {love = rs.getInt(1)- 1;}
	//
	// sql = "update gram set love=? where num=?";
	//
	// pstmt = con.prepareStatement(sql);
	// pstmt.setInt(1, love);
	// pstmt.setInt(2, bb.getNum());
	//
	//
	//
	//
	// pstmt.executeUpdate();
	//
	// } catch (Exception e) {
	// e.printStackTrace();
	// } finally {
	// if (rs != null)
	// try {
	// rs.close();
	// } catch (SQLException ex) {
	// }
	// if (pstmt != null)
	// try {
	// pstmt.close();
	// } catch (SQLException ex) {
	// }
	// if (con != null)
	// try {
	// con.close();
	// } catch (SQLException ex) {
	// }
	// } // finally
	//
	// }
	//
	// //인생샷그램 게시판 베너에 있는 best인생샷(좋아요가 가장 많은 게시글 게시)
	// public boardBean Bestshot() {
	// boardBean bb=null;
	// try {
	// con = getConnection();
	// //gram 테이블에서 좋아요갯수가 가장많은 게시글 선정 select max(love)from gram
	// sql = "select * from gram where love=(select max(love) from gram)";
	// pstmt = con.prepareStatement(sql);
	// rs = pstmt.executeQuery();
	//
	// if (rs.next()) {
	// bb=new boardBean();
	// bb.setNum(rs.getInt("num"));
	// bb.setNick(rs.getString("nick"));
	// bb.setSubject(rs.getString("subject"));
	// bb.setContent(rs.getString("content"));
	// bb.setImage1(rs.getString("image1"));
	// bb.setLove(rs.getInt("love"));
	// bb.setDate(rs.getDate("date"));
	// }
	//
	// } catch (Exception e) {
	// e.printStackTrace();
	// } finally {
	// if (rs != null)
	// try {
	// rs.close();
	// } catch (SQLException ex) {
	// }
	// if (pstmt != null)
	// try {
	// pstmt.close();
	// } catch (SQLException ex) {
	// }
	// if (con != null)
	// try {
	// con.close();
	// } catch (SQLException ex) {
	// }
	// } // finally
	// return bb;
	// }

	 //content.jsp를 클릭했을때 바로 실행되는 메소드! 조회수 1을 증가시킨다!
		public void updateReadCount(int num){
			try{
				//1,2디비연결 메서드 호출
				con = getConnection();			
				//3 sql 객체 생성 조건 num값에 해당하는 게시판글 전체 가져오기
				sql = "update qanda set readcount=readcount+1 where num=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1,num);

				//4 rs=실행저장			
				pstmt.executeUpdate();

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (rs != null)
					try {rs.close();} catch (SQLException ex) {	}
				if (pstmt != null)
					try {pstmt.close();} catch (SQLException ex) {}
				if (con != null)
					try {con.close();} catch (SQLException ex) {}

			}//finally	
			
		}
		
	 
	 

		public void insertQandAReply(QandABean qb) {	
			System.out.println("insertQandAReply까지오나요?");
				int num=0;
			try {			
				con = getConnection();
				sql = "select max(num) from qanda";
				pstmt = con.prepareStatement(sql);
				rs = pstmt.executeQuery();
				if (rs.next()) {
					num = rs.getInt(1) + 1;
				}
				 
					 sql="update qanda set re_seq=re_seq+1 where re_ref=? and re_seq>?";			
				 pstmt = con.prepareStatement(sql);
				 pstmt.setInt(1,qb.getRe_ref());
				 pstmt.setInt(2,qb.getRe_seq());
				 
				 pstmt.executeUpdate();
		

					sql = "insert into qanda(num,nick,subject,content,image1,readcount,re_ref,re_lev,re_seq,date) values(?,?,?,?,?,?,?,?,?,now())";

					pstmt = con.prepareStatement(sql);

					pstmt.setInt(1, num);
					pstmt.setString(2, qb.getNick());
					pstmt.setString(3, qb.getSubject());
					pstmt.setString(4, qb.getContent());
					pstmt.setString(5, qb.getImage1());
					pstmt.setInt(6,0);//readcount , 처음조회수는0
//					===================================================
					pstmt.setInt(7, qb.getRe_ref());//re_ref 기존글 그룹번호 같게함
					pstmt.setInt(8, qb.getRe_lev()+1);//re_lev 답변글 들여쓰기,0:맨위, 일반글 들여쓰기 없음// 기존글+1
					pstmt.setInt(9, qb.getRe_seq()+1);//re_seq 답변글 순서 일반글순서 맨위// 기존글 +1
		//	
				 

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
		}// insertRepley() end
		
	 
	 
	 
	 
}// 클래스 end