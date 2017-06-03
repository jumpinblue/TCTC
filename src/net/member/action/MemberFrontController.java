package net.member.action;
        
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

   
public class MemberFrontController extends HttpServlet {
      
	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String uriPath = request.getRequestURI();
		String contextPath = request.getContextPath();
		String command = uriPath.substring(contextPath.length());
		
		// 이동정보 담는 객체
		ActionForward forward = null;
		// 처리담당 객체
		Action action = null;
		// RSA암호화 키 생성
		RSAKeySetting rsa_key;
		
		if(command.equals("/Main.me")) {	// 메인 페이지
			action = new MainListAction();			
			try {
				forward = action.execute(request, response);
				
			}catch(Exception e) {
				e.printStackTrace();
			}
		
		}else if(command.equals("/MemberJoin.me")) {	// 회원가입 입력 페이지
			
			rsa_key = new RSAKeySetting();
			
			try {
				rsa_key.keySetting(request);
				
				forward = new ActionForward();
				forward.setPath("./member/join.jsp");
				forward.setRedirect(false);
				
			}catch(Exception e) {
				e.printStackTrace();
			}
			
		}else if(command.equals("/MemberJoinAction.me")) {		// 회원가입 DB작업
			action = new MemberJoinAction();
			
			try {
				forward = action.execute(request, response);
				
			}catch(Exception e) {
				e.printStackTrace();
			}
			
		}else if(command.equals("/MemberLoginAction.me")) {		// 로그인 처리
			
			/* 로그인 방식 변경해서 필요가 없어짐...ㅠㅠ */
			/* 그래도 나중에 필요할까봐 일단 남겨둠 */
			
		}else if(command.equals("/MemberIdFinder.me")) {	// 아이디 찾기 입력 페이지(팝업 브라우저)
			
			forward = new ActionForward();
			forward.setPath("./member/idFinder.jsp");
			forward.setRedirect(false);
			
		}else if(command.equals("/MemberIdFinderAction.me")) {	// 아이디 찾기 처리(팝업 브라우저)
			action = new MemberIdFinderAction();
			
			try {
				forward = action.execute(request, response);
				
			}catch(Exception e) {
				e.printStackTrace();
			}
			
		}else if(command.equals("/MemberPassFinder.me")) {	// 비밀번호 찾기 입력 페이지(팝업 브라우저)
			
			forward = new ActionForward();
			forward.setPath("./member/passFinder.jsp");
			forward.setRedirect(false);
			
		}else if(command.equals("/MemberPassFinderAction.me")) {	// 비밀번호 찾기 처리(팝업 브라우저)
			action = new MemberPassFinderAction();
			
			try {
				forward = action.execute(request, response);
				
			}catch(Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/MemberLogout.me")) {	// 로그아웃 처리
			action = new MemberLogoutAction();
			
			try {
				forward = action.execute(request, response);
				
			}catch(Exception e) {
				e.printStackTrace();
			}
			
		}else if(command.equals("/MemberInfo.me")) {		// 회원정보 페이지
			// 회원정보 가져오기
			action = new MemberInfoAction();
			
			try {
				forward = action.execute(request, response);
				
			}catch(Exception e) {
				System.out.println("MemberForntController MemberInfo.me 예외 발생");
				e.printStackTrace();
				
			}
		}else if(command.equals("/MemberUpdate.me")) {	// 회원정보 수정 처리
			// 회원정보 수정
			action = new MemberUpdateAction();
			
			try {
				forward = action.execute(request, response);
				
			}catch(Exception e) {
				e.printStackTrace();
			}
			
		}else if(command.equals("/MemberPassUpdate.me")) {	// 비밀번호 변경 페이지
			// 비밀번호 변경 페이지 이동 전 공개키, 개인키 셋팅(RSA 암호화)
			rsa_key = new RSAKeySetting();
			
			try {
				rsa_key.keySetting(request);
				
				forward = new ActionForward();
				forward.setPath("./member/memberPassUpdate.jsp");
				forward.setRedirect(false);
				
			}catch(Exception e) {
				e.printStackTrace();
			}			
			
		}else if(command.equals("/MemberPassUpdateAction.me")) {	// 비밀번호 변경 처리
			// 비밀번호 변경
			action = new MemberPassUpdateAction();
			
			try {
				forward = action.execute(request, response);
				
			}catch(Exception e) {
				System.out.println("MemberForntController MemberPassUpdateAction.me 예외 발생");
				e.printStackTrace();
			}
			
		}else if(command.equals("/MemberDelete.me")) {	// 회원탈퇴 페이지
			// 회원탈퇴 페이지 이동 전 공개키, 개인키 셋팅(RSA 암호화)
			rsa_key = new RSAKeySetting();
			
			try {
				rsa_key.keySetting(request);

				forward = new ActionForward();
				forward.setPath("./member/memberDelete.jsp");
				forward.setRedirect(false);
				
			}catch(Exception e) {
				System.out.println("MemberForntController MemberDelete.me 예외 발생");
				e.printStackTrace();
			}
			
		}else if(command.equals("/MemberDeleteAction.me")) {	// 회원탈퇴 처리
			
			action = new MemberDeleteAction();
			
			try {
				forward = action.execute(request, response);
				
			}catch(Exception e) {
				System.out.println("MemberForntController MemberDeleteAction.me 예외 발생");
				e.printStackTrace();
			}
				
		}else if(command.equals("/MemberManager.me")) {	// 회원관리 페이지(관리자 전용)
			
			action = new MemberManager();
			
			try {
				forward = action.execute(request, response); 
				
			}catch(Exception e) {
				e.printStackTrace();
			}
			
		}else if(command.equals("/AdminPassCertification.me")) {	// 회원관리페이지에서 탈퇴 눌렀을때 비밀번호 인증하는 팝업			
			// 관리자 비밀번호 인증 페이지 이동 전 공개키, 개인키 셋팅(RSA 암호화)
			rsa_key = new RSAKeySetting();
			
			try {
				rsa_key.keySetting(request);
				
				forward = new ActionForward();
				forward.setPath("./member/adminPassCertification.jsp");
				forward.setRedirect(false);
				
			}catch(Exception e) {
				System.out.println("MemberForntController AdminPassCertification.me 예외 발생");
				e.printStackTrace();
			}
			
		}else if(command.equals("/MemberDeleteManager.me")) {	// 회원관리 페이지에서 관리자가 회원 삭제하는 처리

			action = new MemberDeleteManager();
			
			try {
				forward = action.execute(request, response);
				
			}catch(Exception e) {
				e.printStackTrace();
			}	
			
		}else if(command.equals("/MemberAuthChange.me")) {	// 권한 설정 변경 처리
			
			action = new MemberAuthChangeAction();
			
			try {
				forward = action.execute(request, response);
				
			}catch(Exception e) {
				e.printStackTrace();
			}
			
		}else if(command.equals("/AdminDBManager.me")) {	// 관리자 DB 작성 관리 페이지(통합 관리)
			
			forward = new ActionForward();
			forward.setPath("./member/adminDBManager.jsp");
			forward.setRedirect(false);
			
		}else if(command.equals("/AdminMemberInfo.me")) { // 관리자 회원 정보보기
			
			action = new AdminMemberInfoAction();
			
			try {
				forward = action.execute(request, response);
				
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		
		// 이동
		if(forward!=null) {
			if(forward.isRedirect()) {	// response방식
				response.sendRedirect(forward.getPath());
			}else {	// forward방식
				RequestDispatcher dispatcher = request.getRequestDispatcher(forward.getPath());
				dispatcher.forward(request, response);
			}
		}
					
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}
}
