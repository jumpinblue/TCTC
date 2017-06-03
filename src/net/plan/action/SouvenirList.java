package net.plan.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.plan.db.PlanDAO;
import net.plan.db.PlanSouvenirBean;

public class SouvenirList implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("SouvenirList execute()");
		
		//한글처리 
		request.setCharacterEncoding("utf-8");
		
		//파라메타 값 가져오기
		String pageNum=request.getParameter("pageNum");
		String city_code=request.getParameter("city_code");
		
		ActionForward forward = new ActionForward();
		
		PlanDAO pdao = new PlanDAO();
		
		//도시에 해당하는 기념품 리스트 가져오기
		List<PlanSouvenirBean> souvenirList=pdao.getSouvenirList(city_code);
		
		System.out.println(souvenirList.size());
		
		request.setAttribute("souvenirList", souvenirList);
		request.setAttribute("pageNum", pageNum);
		
		forward= new ActionForward();
		forward.setPath("./plan/souvenirList.jsp");
		forward.setRedirect(false);
		
		return forward;
	}

}
