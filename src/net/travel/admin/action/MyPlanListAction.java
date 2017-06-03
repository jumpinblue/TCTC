package net.travel.admin.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import temp.MyPlanDAO;


public class MyPlanListAction implements Action{
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//디비객체 생성 
		MyPlanDAO mpl=new MyPlanDAO();
		//List myplanList = 메서드호출 getGoodsList()
		List myplanList = mpl.getMyPlanList();
		// myplanList 저장
		request.setAttribute("myplanList", myplanList);
		//이동 ./myplan/myplan.jsp
		ActionForward forward=new ActionForward();
		forward.setRedirect(false);
		forward.setPath("./TravelAdmin/TravelAdmin.jsp");
		return forward;
	}
}
