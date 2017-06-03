package net.plan.action;

import java.io.File;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.images.db.ImagesDAO;
import net.plan.db.PlanCountryBean;
import net.plan.db.PlanDAO;

public class CountryUpdateAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("CountryUpdateAction execute()");
		
		request.setCharacterEncoding("utf-8");
		
		PlanCountryBean pcb = new PlanCountryBean();
		
		pcb.setContinent(request.getParameter("continent"));
		pcb.setCountry_code(request.getParameter("country_code"));
		pcb.setName(request.getParameter("name"));
		pcb.setEn_name(request.getParameter("en_name"));
		pcb.setInfo(request.getParameter("info"));
		
				// 이미지 폴더 교체 작업
				String beforeCountryCode = request.getParameter("before_country_code");
				// 폴더 이름이 변경 되었으면(country_code가 변경되었으면)
				if(!beforeCountryCode.equals(request.getParameter("country_code"))) {
					ImagesDAO idao = new ImagesDAO();

					String newCountry_code = request.getParameter("country_code");
					String curFilePath = request.getSession().getServletContext().getRealPath("/images/plan/nation/"+beforeCountryCode);
					String newFilePath = request.getSession().getServletContext().getRealPath("/images/plan/nation/"+newCountry_code);
					
					File curFile = new File(curFilePath);
					File newFile = new File(newFilePath);
					
					// 폴더 이름 변경
					if(curFile.exists()) {
						curFile.renameTo(newFile);
					}
				}
				
		// 새 정보로 업데이트
		PlanDAO pdao = new PlanDAO();
		int check=pdao.updateCountry(pcb, beforeCountryCode);
				
		if(check==1){
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();		
		out.println("<script>");
		out.println("alert('수정되었습니다.')");
		out.println("location.href='./CountryList.pl'");
		out.println("</script>");
		out.close(); 
		}
		
		return null;
	}
}


