package net.board.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.board.db.BoardDAO;
import net.board.db.boardBean;



public class BoardDeleteAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("BoardDeleteAction execute()");

		
		BoardDAO bdao=new BoardDAO();
		boardBean bb=new boardBean();
		//bb.setNum(Integer.parseInt(request.getParameter("num")));
		int num=Integer.parseInt(request.getParameter("num"));
		
		
		System.out.println("딜릿액션에서의 num의 값"+num);
		bdao.deleteboard(num);
				
		
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();		
			out.println("<script>");
			out.println("alert('삭제성공')");
			out.println("location.href='./BoardList.bo'");
			out.println("</script>");
			out.close();				
	
				return null;

				
	}
}
		
	
