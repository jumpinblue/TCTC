package net.plan.action;

import java.io.File;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.oreilly.servlet.multipart.FileRenamePolicy;

import net.images.db.ImagesBean;
import net.images.db.ImagesDAO;
import net.plan.db.PlanCityBean;
import net.plan.db.PlanCountryBean;
import net.plan.db.PlanDAO;

public class CityAddAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("CountryAddAction execute()");
		
		request.setCharacterEncoding("utf-8");
		
		PlanCityBean pcb = new PlanCityBean();

		String realPath = request.getSession().getServletContext().getRealPath("/images/plan/nation");
		System.out.println("realPath: " + realPath);
		int maxSize = 10 * 1024 * 1024;
		MultipartRequest multi = new MultipartRequest(request, realPath, maxSize, "UTF-8", new DefaultFileRenamePolicy());
		
		pcb.setCountry_code(multi.getParameter("country_code"));
		pcb.setName(multi.getParameter("name"));
		pcb.setEn_name(multi.getParameter("en_name"));
		pcb.setInfo(multi.getParameter("info"));
		pcb.setCity_code(multi.getParameter("en_name"));
		
		PlanDAO pdao = new PlanDAO();
		
		pdao.insertCity(pcb);
		
		// 넣은 도시 이미지 다른 폴더에 넣기
		String fileName = multi.getFilesystemName("file");
		File curFile = new File(realPath+"\\"+fileName);
		String curFileType = fileName.substring(fileName.lastIndexOf("."), fileName.length());	// 확장자 가져오기
		File newFile = new File(realPath+"\\"+multi.getParameter("country_code")+"\\"+multi.getParameter("en_name")+curFileType);
		if(curFile.exists()){curFile.renameTo(newFile);};
		
		// 이미지 관리 테이블에 넣기
		ImagesDAO idao = new ImagesDAO();
		ImagesBean ib = new ImagesBean();
		ib.setCountry_code(multi.getParameter("country_code"));
		ib.setCity_code(multi.getParameter("en_name"));
		ib.setName(multi.getParameter("name"));
		ib.setInfo(multi.getParameter("name")+"의 사진");
		ib.setFile(multi.getParameter("country_code")+"\\"+newFile.getName());
		idao.insertImage(ib);
		
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();		
		out.println("<script>");
		out.println("alert('도시가 추가되었습니다.')");
		out.println("location.href='./CityList.pl'");
		out.println("</script>");
		out.close();
				
		return null;
	}
}


