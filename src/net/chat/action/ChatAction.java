package net.chat.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import net.member.db.MemberBean;
import net.member.db.MemberDAO;
import net.myplanBasket.db.MyPlanBasketDAO;
import net.plan.db.PlanDAO;

public class ChatAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		request.setCharacterEncoding("utf-8");
		System.out.println("ChatAction execute()");

		// 골드 회원인지 판별하기
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("id");

		MyPlanBasketDAO bdao = new MyPlanBasketDAO();
		String gold = bdao.getMemberGold(id);

		System.out.println("gold");
		System.out.println(gold);
		
		request.setAttribute("gold", gold);
		// 골드 회원인지 판별하기 끝.

		ActionForward forward = new ActionForward();
		forward.setRedirect(false);
		forward.setPath("./chat/broadcast.jsp");
		
		
		return forward;

	}

}