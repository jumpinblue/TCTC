package net.plan.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PlanSearchAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("PlanSearchAction execute()");
		
		//한글처리
		request.setCharacterEncoding("utf-8");
		
		ActionForward forward = new ActionForward();
		Action action = null;
		
		// search => 검색어    /  check => 국가&도시 구분
		String search = request.getParameter("search");
		int check = Integer.parseInt(request.getParameter("check"));
		
		// 도시를 검색했을 때
		if(check==2){
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("location.href='./PlanRegion.pl?region="+search+"';");
			out.println("</script>");	
			out.close();
			return null;
			
		// 국가를 검색했을 때	
		}else{
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("location.href='./PlanNation.pl?nation="+search+"';");
			out.println("</script>");	
			out.close();
			return null;
		}

	}

}
