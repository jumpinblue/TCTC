package net.board.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.board.action.Action;
import net.board.action.ActionForward;

public class BoardFrontController extends HttpServlet {
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

		if (command.equals("/BoardWrite.bo")) {
			forward = new ActionForward();
			forward.setPath("./instagram/writeForm.jsp");
			forward.setRedirect(false);// forward방식으로 이동

		} else if (command.equals("/BoardWriteAction.bo")) {
			action = new BoardWriteAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (command.equals("/BoardList.bo")) {
			action = new BoardListAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (command.equals("/BoardContent.bo")) {
			action = new BoardContentAction();

			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}

			// 수정

		} else if (command.equals("/BoardUpdate.bo")) {
			action = new BoardUpdate();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else if (command.equals("/BoardUpdateAction.bo")) {
			action = new BoardUpdateAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}

			// 삭제
		} else if (command.equals("/BoardDelete.bo")) {
			forward = new ActionForward();
			forward.setPath("./instagram/deleteForm.jsp");
			forward.setRedirect(false);// forward방식으로 이동

		} else if (command.equals("/BoardDeleteAction.bo")) {
			action = new BoardDeleteAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (forward != null) {
			if (forward.isRedirect()) {
				response.sendRedirect(forward.getPath());
			} else {
				RequestDispatcher dispatcher = request.getRequestDispatcher(forward.getPath());
				dispatcher.forward(request, response);
			}
		}

	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
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
