package net.reply.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.reply.db.ReplyBean;
import net.reply.db.ReplyDAO;

public class ReplyReplyWriteAction implements Action{
	

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("ReplyReplyWriteAction execute()");
		
		request.setCharacterEncoding("UTF-8");
		

		ReplyDAO rdao = new ReplyDAO();
		ReplyBean rb = new ReplyBean();
		System.out.println("ReplyReplyWriteAction의 re_ref의 값:"+rb.getRe_ref());
		
		String pageNum = request.getParameter("pageNum");		
		int num=Integer.parseInt(request.getParameter("num"));
		
		rb.setNum(num);
		rb.setContent(request.getParameter("content"));
		rb.setNick(request.getParameter("nick"));

		rb.setRe_ref(Integer.parseInt(request.getParameter("re_ref")));
		rb.setRe_lev(Integer.parseInt(request.getParameter("re_lev")));
		rb.setRe_num(Integer.parseInt(request.getParameter("re_num")));
		rb.setRe_seq(Integer.parseInt(request.getParameter("re_seq")));

		rdao.insertRepleyReply(rb);

		ActionForward forward = new ActionForward();
		forward.setPath("./BoardContent.bo?num="+num+"&pageNum="+pageNum);
		
		forward.setRedirect(true);
		return forward;
	}

}


