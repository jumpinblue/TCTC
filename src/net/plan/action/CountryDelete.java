package net.plan.action;

import java.io.File;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.images.db.ImagesDAO;
import net.plan.db.PlanCountryBean;
import net.plan.db.PlanDAO;

public class CountryDelete implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		System.out.println("CountryDelete execute()");
		
		request.setCharacterEncoding("utf-8");
		
		String pageNum = request.getParameter("pageNum");
		String country_code = request.getParameter("country_code");
		
		// DB의 국가 이미지 정보 삭제
		File file = new File(request.getSession().getServletContext().getRealPath("/images/plan/nation/"+country_code));	// 국가 폴더
		if(file.exists()) {	
			// 내부 파일들 삭제
			File[] inFile = file.listFiles();
			for(int i=0; i<inFile.length; i++) {
				inFile[i].delete();
			}
			file.delete();	// 디렉토리 삭제
		}
		ImagesDAO idao = new ImagesDAO();
		idao.deleteCountryImages(country_code);
		
		// DB의 국가 삭제
		PlanDAO pdao = new PlanDAO();
		int check =  pdao.deleteCountry(country_code);
		
		if(check==1){
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out=response.getWriter();
			out.println("<script>");
			out.println("alert('삭제되었습니다');");
			out.println("location.href='./CountryList.pl?pageNum="+pageNum+"'");
			out.println("</script>");
			out.close();
			return null;
		}
		
		return null;
	}

}
