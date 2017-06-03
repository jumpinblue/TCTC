package net.plan.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.plan.action.ActionForward;
import net.plan.db.PlanCountryBean;
import net.plan.db.PlanDAO;

public class CountryUpdate implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("CountryUpdate execute()");
		
		String country_code = request.getParameter("country_code");
		String pageNum = request.getParameter("pageNum");
		
		PlanDAO pdao = new PlanDAO();
		PlanCountryBean pcb = pdao.getCountry(country_code);
		
		request.setAttribute("pcb", pcb);
		request.setAttribute("pageNum", pageNum);
		
		ActionForward forward = new ActionForward();
		forward.setRedirect(false);
		forward.setPath("./plan/countryUpdate.jsp");
		return forward;
	}

}
