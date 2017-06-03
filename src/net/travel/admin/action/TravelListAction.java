package net.travel.admin.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.plan.db.PlanDAO;
import net.plan.db.PlanTravelBean;

public class TravelListAction implements Action{
	
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		System.out.println("DBTravelList Action execute()");
		//한글처리 
		request.setCharacterEncoding("utf-8");
		
		ActionForward forward = new ActionForward();
		
		PlanDAO pdao =  new PlanDAO();
		
		
		String search = request.getParameter("search");
		if(search==null) {
			search = "";
		}
		
		String sort = request.getParameter("sort");
		if(sort==null) {
			sort = "0";
		}
		int isort = Integer.parseInt(sort);
				
		//전체글의 개수 구하기
		int count = 0;
		if(search.equals("")) {
			count = pdao.getTravelCount();
		}else {
			count = pdao.getTravelSearchCount(search);
			System.out.println(search+": count: " + count);
		}
		
		//한페이지에 보여줄 글의 개수 설정
		int pageSize =10;
		
		//현재 페이지가 몇페이지인지 가져오기
		String pageNum = request.getParameter("pageNum");
		if(pageNum == null){
			pageNum ="1";
		}
		
		//시작행 구하기
		int currentPage = Integer.parseInt(pageNum);
		int startRow =(currentPage -1)*pageSize +1;
		
		//끝행 구하기
		int endRow = currentPage * pageSize;
		
		List<PlanTravelBean> travelList = null;
		if(count >= 0){
			travelList = pdao.getTravelList(startRow, pageSize, search, isort);
		}
		
		
		int pageCount = count / pageSize + (count % pageSize == 0? 0:1);
		
		int pageBlock = 10;
		
		int startPage = ((currentPage -1)/pageBlock)*pageBlock +1;
		
		int endPage = startPage + pageBlock - 1;
		if(endPage>pageCount){
			endPage = pageCount;
		}
		
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("count", count);
		request.setAttribute("pageCount", pageCount);
		request.setAttribute("pageBlock", pageBlock);
		request.setAttribute("startPage", startPage);
		request.setAttribute("endPage", endPage);		  
		
		request.setAttribute("travelList", travelList);
		
		forward = new ActionForward();
		forward.setPath("./plan/travelList.jsp");
		forward.setRedirect(false);
		return forward;
	}
	
}
