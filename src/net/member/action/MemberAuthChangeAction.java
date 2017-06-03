package net.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.member.db.MemberDAO;

/* 관리자가 회원관리 페이지에서 회원권한을 변경했을때 처리하는데 클래스 */
public class MemberAuthChangeAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// 한글처리
		request.setCharacterEncoding("UTF-8");
		// 파라미터 값 가져오기
		String id = request.getParameter("id");
		String auth = request.getParameter("auth");

		// DB작업
		MemberDAO mdao = new MemberDAO();
		mdao.authUpdate(id, auth);
		
		// 이동(ajax라서 이동안함)	
		return null;
	}
}
