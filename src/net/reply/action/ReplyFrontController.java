package net.reply.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.reply.action.Action;
import net.reply.action.ActionForward;

public class ReplyFrontController extends HttpServlet {
	protected void doProcess(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("doProcess()메서드 호출");

		// 가상주소 뽑아오기
		// http://localhost:8080/Model2/BoardWrite.bo
		// 가상주소 비교
		// 이동하기
		String requestURI = request.getRequestURI();
		String contextPath = request.getContextPath();
		String command = requestURI.substring(contextPath.length());
		System.out.println("뽑아온가상주소:" + command);

		ActionForward forward = null;
		Action action = null;
		
		if (command.equals("/ReplyWriteAction.re")) {
			action = new ReplyWriteAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();}	
			
		} else if (command.equals("/RreplyUpdateAction.re")) {
			action= new RreplyUpdateAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();}
		} else if (command.equals("/ReplyDelete.re")) {
			action= new ReplyDelete();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();}
		} else if (command.equals("/ReplyReplyWriteAction.re")) {
			action= new ReplyReplyWriteAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();}
			
			
		} else if (command.equals("/ReplyReply.re")) {
			forward = new ActionForward();
			forward.setPath("./instagram/replyreplyform.jsp");
			forward.setRedirect(false);
		}
			



	if(forward!=null)

	{
		if (forward.isRedirect()) {
			response.sendRedirect(forward.getPath());
		} else {

			RequestDispatcher dispatcher = request.getRequestDispatcher(forward.getPath());
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("doPost()메서드 호출");
		doProcess(request, response);
	}

}
