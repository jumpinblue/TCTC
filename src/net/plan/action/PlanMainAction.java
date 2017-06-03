package net.plan.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.plan.db.PlanCountryBean;
import net.plan.db.PlanDAO;

public class PlanMainAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		
		ActionForward forward = new ActionForward();
		
		System.out.println("countryList execute()");
		
		PlanDAO pdao = new PlanDAO();
		
		List<PlanCountryBean> countryList = pdao.getCountryList();
		
		request.setAttribute("countL", countryList);
		
		
		forward = new ActionForward();
		forward.setPath("./plan/planMain.jsp");
		forward.setRedirect(false);
		return forward;
	
	}

}
