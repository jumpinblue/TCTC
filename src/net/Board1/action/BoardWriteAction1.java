package net.Board1.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.Board1.db.BoardDAO;


public class BoardWriteAction1 implements Action1{

	@Override
	public ActionForward1 execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("BoardWriteAction execute()");
		// net.board.db.BoardBean net.board.db.BoardDAO
		//한글처리
		request.setCharacterEncoding("utf-8");
		// BoardBean bb 객체생성
		net.Board1.db.BoardBean bb = new net.Board1.db.BoardBean();
		// 자바빈 멤버변수 <= 파라미터 저장
		bb.setNick_name(request.getParameter("nick_name"));
		bb.setPass(request.getParameter("pass"));
		bb.setContent(request.getParameter("content"));
		bb.setSubject(request.getParameter("subject"));
		bb.setFile(request.getParameter("file"));
		bb.setIp(request.getRemoteAddr());
		bb.setLocation(request.getParameter("location"));
		
		System.out.println("location test : "+request.getParameter("location"));
		System.out.println("location test : "+bb.getLocation());
		
		// BoardDAO 객체생성
			BoardDAO bdao = new BoardDAO();
		// 메서드 호출 insertBoard(bb)
			bdao.insertBoard(bb);
			// 이동 ./BoardList.bo
			ActionForward1 forward = new ActionForward1();
			forward.setPath("./BoardList1.bb");
			forward.setRedirect(true);
		return forward;
	}
	
	
	
}
