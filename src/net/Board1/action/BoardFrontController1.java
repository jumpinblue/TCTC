package net.Board1.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BoardFrontController1 extends HttpServlet{
	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//가상주소 뽑아오기
		//  http://localhost:8080/Model2/BoardWrite.bo
		//     /Model2/BoardWrite.bo
		String requestURI=request.getRequestURI();
		//     /Model2
		String contextPath=request.getContextPath();
		//           /BoardWrite.bo
		String command=requestURI.substring(contextPath.length());
		//가상주소 비교
		ActionForward1 forward=null;
		Action1 action=null;
		if(command.equals("/BoardWrite1.bb")){
			//  ./board/writeForm.jsp
			// 이동정보 저장  net.board.action.ActionForward
			forward=new ActionForward1();
			forward.setPath("./board/writeForm1.jsp");
			forward.setRedirect(false);
			
		}else if(command.equals("/BoardWriteAction1.bb")){
			// 처리할 파일 틀제시  net.board.action.Action
			// 파일 BoardWriteAction execute()
			action=new BoardWriteAction1();
			try {
				forward=action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}else if(command.equals("/BoardList1.bb")){
			// BoardListAction execute() 
			action=new BoardList1();
			try{
				forward=action.execute(request, response);
				
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}else if(command.equals("/BoardContent1.bb")){
			action=new BoardContent1();
			try{
				forward=action.execute(request, response);
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}else if(command.equals("/BoardDelete1.bb")){
			action=new BoardDelete1();
			try{
				forward=action.execute(request, response);
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}else if(command.equals("/BoardUpdate1.bb")){
			action=new BoardUpdate1();
			try{
				forward=action.execute(request, response);
			}catch(Exception e){
				e.printStackTrace();
			}
		
			
		}else if(command.equals("/BoardUpdateAction1.bb")){
			action=new BoardUpdateAction1();
			try{
				forward=action.execute(request, response);
			}catch(Exception e){
				e.printStackTrace();
			}
		
		}else if(command.equals("/BoardreWrite1.bb")){
			forward=new ActionForward1();
			forward.setPath("./board/reWriteForm1.jsp");
			forward.setRedirect(false);
			
		}else if(command.equals("/BoardreWriteAction1.bb")){
			action=new BoardreWrite1();
			try{
				forward = action.execute(request, response);
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		
		
		//이동하기
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
		doProcess(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

}
