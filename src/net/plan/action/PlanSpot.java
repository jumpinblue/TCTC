package net.plan.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.plan.db.PlanDAO;
import net.plan.db.PlanSouvenirBean;
import net.plan.db.PlanTravelBean;

public class PlanSpot implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("PlanSpot execute()");
		
		request.setCharacterEncoding("utf-8");
		
		ActionForward forward = new ActionForward();
		
		String travel = request.getParameter("travel");
	
		PlanDAO pdao = new PlanDAO();
		
		/*해당 장소 가져오기*/
		PlanTravelBean ptb = pdao.getTravel(travel);
		
		/*해당장소에 맞는 이미지 가져오기  
		List spotimagelist = pdao.getSpotImages(ptb.getTravel_id());*/
		
		/*해당 장소 선물 리스트*/
		List<PlanSouvenirBean> souvenirList = pdao.getSouvenirList(ptb.getCity_code());
		
		

		request.setAttribute("ptb", ptb);
		
		forward = new ActionForward();
		forward.setRedirect(false);
		forward.setPath("./plan/planSpot.jsp");
		
		return forward;
		
	}

}
