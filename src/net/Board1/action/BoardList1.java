package net.Board1.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class BoardList1 implements Action1{
	@Override
	public ActionForward1 execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("BoardListAction execute()");
		//디비객체 생성 BoardDAO bdao
		net.Board1.db.BoardDAO bdao=new net.Board1.db.BoardDAO();
		// 게시판 전체글 개수 
		// int count=   getBoardCount() 메서드호출 
		int count=bdao.getBoardCount();
		//한페이지에 보여줄 글의 개수 설정
		int pageSize=10;
		//현페이지가  몇페이지 인지 가져오기 없으면 1페이지 설정
		String pageNum=request.getParameter("pageNum");
		if(pageNum==null){
			pageNum="1";
		}
		
		//시작행구하기   1    11    21   31 ..  <= pageNum, pageSize 조합
		int currentPage=Integer.parseInt(pageNum);
		int startRow=(currentPage-1)*pageSize+1;
		//끝행구하기
		int endRow=currentPage*pageSize;
		//List boardList =    메서드호출 getBoardList(시작행,몇개)
		List boardList=bdao.getBoardList(startRow, pageSize);
		// 전체 페이지수 구하기   게시판 글 50개 한화면에 보여줄 글개수 10 => 5 전체페이지 +0=>5
					//  게시판 글 56개 한화면에 보여줄 글개수 10 => 5 전체페이지 +1(나머지)=>6 
		int pageCount=count/pageSize+(count%pageSize==0?0:1);
		// 한 화면에 보여줄 페이지 번호 개수 
		int pageBlock=10;
		// 시작페이지 번호구하기   1~10=>1   11~20=>11  21 ~30 => 21
		int startPage=((currentPage-1)/pageBlock)*pageBlock+1;
		// 끝페이지 번호 구하기  
		int endPage=startPage+pageBlock-1;
		if(endPage>pageCount){
			endPage=pageCount;
		}
		//데이터 저장 pageCount boardList pageCount
		//			pageBlock startPage endPage
		request.setAttribute("boardList", boardList);
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("count", count);
		request.setAttribute("pageCount", pageCount);
		request.setAttribute("pageBlock", pageBlock);
		request.setAttribute("startPage", startPage);
		request.setAttribute("endPage", endPage);
		
		
		// 이동  ./board/list.jsp
		ActionForward1 forward=new ActionForward1();
		forward.setPath("./board/list1.jsp");
		forward.setRedirect(false);
		return forward;
	}
}
