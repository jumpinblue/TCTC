package net.reply.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.reply.action.ActionForward;
import net.reply.db.ReplyBean;
import net.reply.db.ReplyDAO;

public class ReplyDelete implements Action {
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("ReplyDelete execute()");

		
		ReplyDAO rdao=new ReplyDAO();
		ReplyBean rb=new ReplyBean();
		
		int num=Integer.parseInt(request.getParameter("num"));		
		String pageNum = request.getParameter("pageNum");
		int re_num=Integer.parseInt(request.getParameter("re_num"));
			
		rdao.deleteReply(re_num);
				
		
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();		
			out.println("<script>");
//			out.println("alert('삭제성공')");
			out.println("location.href='./BoardContent.bo?num="+num+"&pageNum=" + pageNum + "';");
			out.println("</script>");
			out.close();				
	
				return null;

				
	}
}
