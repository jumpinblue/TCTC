package net.QandA.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.QandA.db.QandADAO;



public class QandAListAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		System.out.println("QandAListAction execute()");

		request.setCharacterEncoding("utf-8");

		QandADAO qdao = new QandADAO();
		// =======================List 페이징 처리 해주는 부분=================
		int count = qdao.getQandAcount();// 게시한 글이 총 몇개인지 세어주는 메소드
		int pageSize = 5;// 한 페이지당 글 5개씩 게시

		// pageNum을 처음 선언해주는곳, 페이지번호이지만 String 값으로 처음생성
		String pageNum = request.getParameter("pageNum");
		if (pageNum == null) {// pageNum 이 없으면
			pageNum = "1";// 무조건 1페이지
		}

		int currentPage = Integer.parseInt(pageNum);// 현재페이지는 pageNum을 int형으로
													// 바꾼것
		int startrow = (currentPage - 1) * pageSize + 1;
		// 시작 줄 = (3-1) * 10 +1 =21 줄부터시작!

		int endRow = currentPage * pageSize;
		// 끝줄 = 3*10 =30줄까지!

		// 전체 페이지수 구하기 게시판 글 50개 한화면에 보여줄 글개수 10 =>5 전체페이지+0=>5
		// 게시판 글 56개 한화면에 보여줄 글개수 10 =>5 전체페이지+1(나머지)=>6
		int pageCount = count / pageSize + (count % pageSize == 0 ? 0 : 1);
		// 한 화면에 보여줄 페이지 번호 개수
		// 시작페이지 번호 구하기 1~10=>1 11~20=>11 21~30=>21
		int startPage = ((currentPage - 1) / pageSize) * pageSize + 1;
		// 끝페이지 번호 구하기
		int endPage = startPage + pageSize - 1;

		// =======================List 내용 뿌려주는 부분=================
		List QandAList = null;// list를 사용하여 list.jsp에 넘겨줄 값들 setAtrribute에 담기
		// 게시한글이 한개라도 있을경우에는 하기의 getBoardList를 통해 게시판에 글들을 게시해줌
		if (count != 0) {
			QandAList = qdao.getQandAList(startrow, pageSize);
		}

		// 상기의 getBoardList에서 받아온값들을 setAttribute 에 담아서 list.jsp로 보내줌
		request.setAttribute("ql", QandAList);
		request.setAttribute("count", count);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("pageCount", pageCount);
		request.setAttribute("startPage", startPage);
		request.setAttribute("endPage", endPage);
		


		ActionForward forward = new ActionForward();
		forward.setRedirect(false);// false일 경우 forward형식으로 이동
		forward.setPath("./QandA/list.jsp");
		return forward;		
	}


}
