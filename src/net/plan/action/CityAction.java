package net.plan.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.plan.db.PlanCountryBean;
import net.plan.db.PlanDAO;

public class CityAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		System.out.println("CityAction execute()");
		//한글처리 
		request.setCharacterEncoding("utf-8");
		
		ActionForward forward = new ActionForward();
		
		PlanDAO pdao =  new PlanDAO();
		
		List<PlanCountryBean> countryList =  pdao.getCountryList();
		
		request.setAttribute("countryList", countryList);
		
		forward = new ActionForward();
		forward.setPath("./plan/cityAdd.jsp");
		forward.setRedirect(false);
		return forward;
	}

}
