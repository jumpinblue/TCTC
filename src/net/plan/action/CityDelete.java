package net.plan.action;

import java.io.File;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.images.db.ImagesDAO;
import net.plan.db.PlanCityBean;
import net.plan.db.PlanDAO;

public class CityDelete implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		System.out.println("CityDelete execute()");
		
		request.setCharacterEncoding("utf-8");
		
		String pageNum = request.getParameter("pageNum");
		String city_code = request.getParameter("city_code");
		
		PlanDAO pdao = new PlanDAO();
		PlanCityBean pcb = pdao.getCityContent(city_code);
		
		ImagesDAO idao = new ImagesDAO();
		String imgPath = idao.getCityImgPath(city_code);
		idao.deleteCityImages(city_code);
		
		// DB의 도시 이미지 정보 삭제
		File file = new File(request.getSession().getServletContext().getRealPath("/images/plan/nation/"+imgPath));	// 이미지 위치 정보
		if(file.exists()) {
			file.delete();
		}
		int check =  pdao.deleteCity(city_code);

		if(check==1){
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out=response.getWriter();
			out.println("<script>");
			out.println("alert('삭제되었습니다');");
			out.println("location.href='./CityList.pl?pageNum="+pageNum+"'");
			out.println("</script>");
			out.close();
			return null;
		}
		return null;
	}

}
