package net.Board1.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BoardContent1 implements Action1{

	@Override
	public ActionForward1 execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//BoardBean bb = request 저장된 bb값 가져오기
		int num= Integer.parseInt(request.getParameter("num"));
		
		net.Board1.db.BoardDAO bdao = new net.Board1.db.BoardDAO();
		
		bdao.updateReadcount(num);
		
		net.Board1.db.BoardBean bb = bdao.getBoard(num);
		
		request.setAttribute("bb", bb );
		
		String pageNum = request.getParameter("pageNum");
		request.setAttribute("pageNum", pageNum );
		//String pageNum = request 저장된 pageNum 가져오기
		
		ActionForward1 forward = new ActionForward1();
		forward.setPath("./board/content1.jsp");
		forward.setRedirect(false);
		return forward;
	}

}
