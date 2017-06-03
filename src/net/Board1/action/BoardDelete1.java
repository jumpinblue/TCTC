package net.Board1.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class BoardDelete1 implements Action1 {

	@Override
	public ActionForward1 execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");

		net.Board1.db.BoardDAO bdao = new net.Board1.db.BoardDAO();

		int num = Integer.parseInt(request.getParameter("num"));

		String pass = request.getParameter("pass");

		int check = bdao.deleteBoard(num, pass);

		if (check == 0) {
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('비밀번호 틀림');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
			return null;
			
		} else if(check== 1) {
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('삭제성공');");
			out.println("location.href='./BoardList1.bb'");
			out.println("</script>");
			out.close();
			return null;
		}

		ActionForward1 forward = new ActionForward1();
		forward.setPath("./board/deleteForm1.jsp");
		forward.setRedirect(false);
		return forward;
	}
}
