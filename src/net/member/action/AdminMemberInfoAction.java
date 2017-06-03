package net.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.member.db.MemberBean;
import net.member.db.MemberDAO;

public class AdminMemberInfoAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// 한글처리
		request.setCharacterEncoding("UTF-8");
		
		// 파라미터 값 가져오기
		String memberId = request.getParameter("memberId");
		
		// DB에서 가져오기
		MemberDAO mdao = new MemberDAO();
		MemberBean mb = mdao.getMember(memberId);
		
		// 파라미터 담기
		request.setAttribute("memberInfo", mb);
		
		// 이동정보 반환
		ActionForward forward = new ActionForward();
		forward.setPath("./member/adminMemberInfo.jsp");
		forward.setRedirect(false);
		
		return forward;
	}
	
}
