package net.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.member.db.MemberBean;
import net.member.db.MemberDAO;

/* 회원정보 가져오는 클래스 */
public class MemberInfoAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");
		if(id==null) {
			response.sendRedirect("./Main.me");
			return null;
		}
		
		// DB작업 객체
		MemberDAO mdao = new MemberDAO();
		MemberBean mb = mdao.getMember(id);
		
		request.setAttribute("mb", mb);
		
		// 이동정보 설정
		ActionForward forward = new ActionForward();
		forward.setPath("./member/memberInfo.jsp?mb="+mb);
		forward.setRedirect(false);
		
		return forward;
	}
	
}
