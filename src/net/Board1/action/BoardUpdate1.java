package net.Board1.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class BoardUpdate1 implements Action1{

	@Override
	public ActionForward1 execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("boardupdate");
		int num = Integer.parseInt(request.getParameter("num"));
		String pageNum = request.getParameter("pageNum");
		
		net.Board1.db.BoardDAO bdao = new net.Board1.db.BoardDAO();
		
		net.Board1.db.BoardBean bb = bdao.getBoard(num);
		
		request.setAttribute("bb", bb);
		request.setAttribute("pageNum", pageNum);
		
		ActionForward1 forward = new ActionForward1();
		forward.setPath("./board/updateForm1.jsp");
		forward.setRedirect(false);
		return forward;
		
	}

}
