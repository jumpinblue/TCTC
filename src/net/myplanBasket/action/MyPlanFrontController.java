package net.myplanBasket.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



public class MyPlanFrontController extends HttpServlet{
	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//가상주소 가져오기
		//  http://localhost:8080/Model2/BasketAdd.ba
		//  /Model2/BasketAdd.ba
		String requestURI=request.getRequestURI();
		//  /Model2
		String contextPath=request.getContextPath();
		//  /BasketAdd.ba
		String command=requestURI.substring(contextPath.length());
		//주소비교하기
		Action action = null;
		ActionForward forward = null;
		if(command.equals("/MyPlan.pln")){
			//  BasketAddAction
			action=new MyPlanBasketListAction();
			try {
				forward=action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/MyPlanModify.pln")){
			//  AdminGoodsModifyForm
			action=new MyPlanModifyForm();
			try {
				forward=action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/MyPlanModifyAction.pln")){
			// GoodsModifyAction
			action=new MyPlanModifyAction();
			try {
				forward=action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/MyPlanBasketAdd.pln")){
			//  AdminGoodsModifyForm
			action=new MyPlanBasketAddAction();
			try {
				forward=action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/PayAction.pln")){
			
			action=new Pay();
			try {
				forward=action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/Pay.pln")){
			forward = new ActionForward();
			forward.setPath("./myplan/payPlanC.jsp");
			forward.setRedirect(false);
		}
		
		//이동
		if(forward!=null){
			if(forward.isRedirect()){
				response.sendRedirect(forward.getPath());
			}else{
				RequestDispatcher dispatcher=request.getRequestDispatcher(forward.getPath());
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
