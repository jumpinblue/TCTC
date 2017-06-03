package net.Board1.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



public class BoardreWrite1 implements Action1{

	@Override
	public ActionForward1 execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
	
		request.setCharacterEncoding("utf-8");
		
		net.Board1.db.BoardDAO bdao = new net.Board1.db.BoardDAO();
		
		net.Board1.db.BoardBean bb = new net.Board1.db.BoardBean();
		
		bb.setIp(request.getRemoteAddr());
		bb.setNum(Integer.parseInt(request.getParameter("num")));
		bb.setRe_ref(Integer.parseInt(request.getParameter("re_ref")));
		bb.setPass(request.getParameter("pass"));
		bb.setSubject(request.getParameter("subject"));
		bb.setContent(request.getParameter("content"));
		
		bdao.reInsertBoard(bb);
		
		
		ActionForward1 forward = new ActionForward1();
		forward.setPath("./BoardList1.bb");
		forward.setRedirect(false);
		return forward;
	}

}
