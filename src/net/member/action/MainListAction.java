package net.member.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.board.db.BoardDAO;

public class MainListAction implements Action{
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("MainListAction execute()");

		request.setCharacterEncoding("utf-8");		
		
		BoardDAO bdao = new BoardDAO();
		
		int count = bdao.getBoardcount();
		int pageSize = 10;

		String pageNum = request.getParameter("pageNum");
		if (pageNum == null) {//pageNum 이 없으면
			pageNum = "1";//무조건 1페이지
		}
		int currentPage = Integer.parseInt(pageNum);
		int startrow = (currentPage - 1) * pageSize + 1;
		 // 시작 줄 	=	(3-1)			* 10 +1 =21 줄부터시작!
		int endRow=currentPage*pageSize;
		//  끝줄 = 	3*10 =30줄까지!
		
		//list를 사용하여 list.jsp에 넘겨줄 값들 setAtrribute에 담기
		List boardList=null;
		if(count!=0){
			boardList=bdao.getBoardList(startrow, pageSize);
		}
		
		request.setAttribute("bl", boardList);
		request.setAttribute("count", count);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("startrow", startrow);
		request.setAttribute("endRow", endRow);		
		
				
			
		ActionForward forward = new ActionForward();
		forward.setRedirect(false);
		forward.setPath("./main/main.jsp");
		return forward;
	}

}
