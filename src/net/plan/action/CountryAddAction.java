package net.plan.action;

import java.io.File;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;

import net.plan.db.PlanCountryBean;
import net.plan.db.PlanDAO;

public class CountryAddAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("CountryAddAction execute()");

		request.setCharacterEncoding("utf-8");

		PlanCountryBean pcb = new PlanCountryBean();

		pcb.setContinent(request.getParameter("continent"));
		pcb.setCountry_code(request.getParameter("country_code"));
		pcb.setName(request.getParameter("name"));
		pcb.setEn_name(request.getParameter("en_name"));
		pcb.setInfo(request.getParameter("info"));

		PlanDAO pdao = new PlanDAO();

		pdao.insertCountry(pcb);

		// 이미지 담는 폴더 생성
		String realPath = request.getSession().getServletContext()
				.getRealPath("/images/plan/nation/" + pcb.getCountry_code());
		System.out.println("realPath: " + realPath);
		File dir = new File(realPath);
		if (!dir.exists()) { // 존재하지 않으면
			System.out.println("새 디렉토리 생성");
			dir.mkdirs();
			// 가상경로에 생성됨 이클립스 아파치톰캣 서버에서는
		}

		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println("<script>");
		out.println("alert('국가가 추가되었습니다.')");
		out.println("location.href='./CountryList.pl'");
		out.println("</script>");
		out.close();

		return null;
	}

}
