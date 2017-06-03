package net.reply.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.reply.action.ActionForward;
import net.reply.db.ReplyBean;
import net.reply.db.ReplyDAO;

public class RreplyUpdateAction implements Action {
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("BoardUpdateAction execute()");
		request.setCharacterEncoding("utf-8");
		
		int num=Integer.parseInt(request.getParameter("num"));

		ActionForward forward = new ActionForward();

		ReplyDAO rdao = new ReplyDAO();
		ReplyBean rb = new ReplyBean();

		String pageNum = request.getParameter("pageNum");
		
		rb.setContent(request.getParameter("content"));
		rb.setNick(request.getParameter("nick"));
		rb.setRe_num(Integer.parseInt(request.getParameter("re_num")));
		
		rdao.updateReply(rb);

		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println("<script>");
//		out.println("alert('수정성공')");
		out.println("location.href='./BoardContent.bo?num="+num+"&pageNum=" + pageNum + "';");
		out.println("</script>");
		out.close();

		return null;

	}
}
