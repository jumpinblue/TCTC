package net.reply.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.board.db.boardBean;
import net.reply.action.Action;
import net.reply.action.ActionForward;

import net.reply.db.ReplyBean;
import net.reply.db.ReplyDAO;

public class ReplyWriteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("ReplyWriteAction execute()");
		request.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession();
		String nick = (String) session.getAttribute("nick");

		ReplyDAO rdao = new ReplyDAO();
		ReplyBean rb = new ReplyBean();

		boardBean bb = new boardBean();
		bb.setSubject(request.getParameter("subject"));

		String pageNum = request.getParameter("pageNum");
		int num = Integer.parseInt(request.getParameter("num"));
		rb.setNum(num);

		System.out.println("ReplyWrite액션에서num의 값" + num);
		System.out.println("ReplyWrite액션에서nick의 값" + nick);

		rb.setContent(request.getParameter("content"));
		rb.setNick(nick);

		rb.setRe_lev(Integer.parseInt(request.getParameter("re_ref")));
		rb.setRe_lev(Integer.parseInt(request.getParameter("re_lev")));
		rb.setRe_num(Integer.parseInt(request.getParameter("re_num")));
		rb.setRe_seq(Integer.parseInt(request.getParameter("re_seq")));

		rdao.insertRepley(rb);

		ActionForward forward = new ActionForward();
		forward.setPath("./BoardContent.bo?num=" + num + "&pageNum=" + pageNum);

		forward.setRedirect(true);
		return forward;
	}

}
