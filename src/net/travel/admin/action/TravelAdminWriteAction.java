package net.travel.admin.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.plan.db.PlanCityBean;
import net.plan.db.PlanCountryBean;
import net.plan.db.PlanDAO;
import net.plan.db.PlanTravelBean;
import temp.MyPlanDAO;

public class TravelAdminWriteAction implements Action {
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("excute TravelAdminWriteAction");

		// 한글처리
		request.setCharacterEncoding("utf-8");

		ActionForward forward = new ActionForward();

		PlanDAO pdao = new PlanDAO();

		List<PlanCountryBean> countryList =  pdao.getCountryList();
		
		request.setAttribute("countryList", countryList);
		
		
		List<PlanCityBean> cityList = pdao.getCityList();
		
		request.setAttribute("cityList", cityList);

		forward = new ActionForward();
		forward.setPath("/TravelAdmin/TravelAdmin.jsp");
		forward.setRedirect(false);
		return forward;
	}
}
