package net.chat.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ChatFrontController extends HttpServlet {

	protected void doProcess(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String uriPath = request.getRequestURI();
		String contextPath = request.getContextPath();
		String command = uriPath.substring(contextPath.length());

		// 이동정보 담는 객체
		ActionForward forward = null;
		// 처리담당 객체
		Action action = null;

		if(command.equals("/Chat.ct")){	
			action = new ChatAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();}		
		}

		if (forward != null) {
			if (forward.isRedirect()) { // response방식
				response.sendRedirect(forward.getPath());
			} else { // forward방식
				RequestDispatcher dispatcher = request.getRequestDispatcher(forward.getPath());
				dispatcher.forward(request, response);
			}
		}

	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doProcess(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doProcess(request, response);
	}
}
