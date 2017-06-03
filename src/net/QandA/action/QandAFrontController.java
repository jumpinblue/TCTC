package net.QandA.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class QandAFrontController extends HttpServlet {
	protected void doProcess(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("doProcess()메서드 호출");

	
		String requestURI = request.getRequestURI();
		String contextPath = request.getContextPath();
		String command = requestURI.substring(contextPath.length());
		System.out.println("뽑아온가상주소:" + command);

		ActionForward forward = null;
		Action action = null;
	
		if (command.equals("/QandAWrite.qna")) {
			forward = new ActionForward();
			forward.setPath("./QandA/writeForm.jsp");
			forward.setRedirect(false);// forward방식으로 이동
		} 
		
		else if (command.equals("/QandAList.qna")) {
			action = new QandAListAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (command.equals("/QandAWriteAction.qna")) {
			action = new QandAWriteAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();}
		} else if (command.equals("/QandAContentAction.qna")) {
			action = new QandAContentAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (command.equals("/QandAUpdate.qna")) {
			action = new QandAUpdate();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (command.equals("/QandAUpdateAction.qna")) {
			action = new QandAUpdateAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (command.equals("/QandADeleteAction.qna")) {
			action = new QandADeleteAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/QandAReplyWrite.qna")) {
				forward = new ActionForward();
				forward.setPath("./QandA/rewriteForm.jsp");
				forward.setRedirect(false);// forward방식으로 이동

	} else if (command.equals("/QandAReplyWriteAction.qna")) {
		action = new QandAReplyWriteAction();
		try {
			forward = action.execute(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		if (command.equals("/QandACompanyIntro.qna")) {
			forward = new ActionForward();
			forward.setPath("./QandA/QandACompanyIntro.jsp");
			forward.setRedirect(false);// forward방식으로 이동
		} 
		if (command.equals("/QandAHowToUse.qna")) {
			forward = new ActionForward();
			forward.setPath("./QandA/QandAHowToUse.jsp");
			forward.setRedirect(false);// forward방식으로 이동
		} 
		if (command.equals("/QandAadvertise.qna")) {
			forward = new ActionForward();
			forward.setPath("./QandA/QandAadvertise.jsp");
			forward.setRedirect(false);// forward방식으로 이동
		} 		
		if (command.equals("/QandAcondition.qna")) {
			forward = new ActionForward();
			forward.setPath("./QandA/QandAcondition.jsp");
			forward.setRedirect(false);// forward방식으로 이동
		} 	
		if (command.equals("/QandAprivacy.qna")) {
			forward = new ActionForward();
			forward.setPath("./QandA/QandAprivacy.jsp");
			forward.setRedirect(false);// forward방식으로 이동
		} 		
		if (command.equals("/Memberintro.qna")) {
			forward = new ActionForward();
			forward.setPath("./QandA/Memberintro.jsp");
			forward.setRedirect(false);// forward방식으로 이동
		} 
		
		
		
		
		
		
			
		
		if(forward!=null){
			if(forward.isRedirect()){
				response.sendRedirect(forward.getPath());
			}else{
				RequestDispatcher dispatcher
				=request.getRequestDispatcher(forward.getPath());
				dispatcher.forward(request, response);
			}
		}

	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doGet()메서드 호출");
		doProcess(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doPost()메서드 호출");
		doProcess(request, response);
	}

}
