package net.travel.admin.action;

import java.io.File;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.sun.mail.handlers.multipart_mixed;

import net.images.db.ImagesBean;
import net.images.db.ImagesDAO;
import net.plan.db.PlanDAO;
import net.plan.db.PlanTravelBean;
import net.travel.admin.db.TravelAdminDAO;
import net.travel.admin.db.TravelBean;

public class TravelAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub

		System.out.println("execute TravelAction");

		request.setCharacterEncoding("utf-8");

		String realPath = request.getSession().getServletContext().getRealPath("/images/plan/nation/");
		int maxSize = 10 * 1024 * 1024;
		MultipartRequest multi = new MultipartRequest(request, realPath, maxSize, "UTF-8",
				new DefaultFileRenamePolicy());

		String lat = multi.getParameter("latitude");
		String lng = multi.getParameter("longitude");

		float latitude = Float.parseFloat(lat.trim());
		float longitude = Float.parseFloat(lng.trim());

		// 자바빈 파일 만들기
		// 자바빈 파일 생성
		TravelBean tBean = new TravelBean();
		// 폼 => 자바빈 저장

		tBean.setType(multi.getParameter("type"));
		tBean.setCountry_code(multi.getParameter("country_code"));
		tBean.setCity_code(multi.getParameter("city_code"));
		tBean.setName(multi.getParameter("name"));
		tBean.setLatitude(latitude);
		tBean.setLongitude(longitude);
		tBean.setInfo(multi.getParameter("info"));
		tBean.setAddress(multi.getParameter("address"));

		// 국가 폴더 내에서 도시 폴더가 없을 경우, 폴더 생성
		realPath = request.getSession().getServletContext()
				.getRealPath("/images/plan/nation/" + tBean.getCountry_code() + "/" + tBean.getCity_code());
		System.out.println("realPath: " + realPath);
		File dir = new File(realPath);
		if (!dir.exists()) { // 존재하지 않으면
			System.out.println("새 디렉토리 생성");
			dir.mkdirs();
			// 가상경로에 생성됨 이클립스 아파치톰캣 서버에서는
		}
		// 파일이 최초에 저장되는 경로를 설정.(이동시키기 위해서.)
		realPath = request.getSession().getServletContext().getRealPath("/images/plan/nation/");

		// 디비 파일 만들기
		// 객체 생성
		TravelAdminDAO mpl = new TravelAdminDAO();
		// 객체 생성 메서드호출
		mpl.insertTravel(tBean);

		System.out.println(realPath);
		System.out.println(tBean.getCountry_code());
		System.out.println(tBean.getCity_code());

		// 넣은 도시 이미지 다른 폴더에 넣기
		// travel 테이블에 들어간 후, travel_id값을 받아서, images 테이블에 넣는다.
		PlanDAO pdao = new PlanDAO();
		PlanTravelBean ptb = pdao.getTravel(tBean.getName());
		// travel 테이블에 들어간 후, travel_id값을 받아서, images 테이블에 넣는다. 끝.

		String fileName = multi.getFilesystemName("file");
		File curFile = new File(realPath + "\\" + fileName);
		String curFileType = fileName.substring(fileName.lastIndexOf("."), fileName.length()); // 확장자
																								// 가져오기
		File newFile = new File(realPath + "\\" + multi.getParameter("country_code") + "\\"
				+ multi.getParameter("city_code") + "\\" + ptb.getTravel_id() + curFileType);
		if (curFile.exists()) {
			curFile.renameTo(newFile);
		}
		;
		// 넣은 도시 이미지 다른 폴더에 넣기 끝.

		// 이미지 관리 테이블에 넣기
		ImagesDAO idao = new ImagesDAO();
		ImagesBean ib = new ImagesBean();
		ib.setCountry_code(multi.getParameter("country_code"));
		ib.setCity_code(multi.getParameter("city_code"));
		ib.setTravel_id(ptb.getTravel_id());
		ib.setType(multi.getParameter("type"));
		ib.setName(multi.getParameter("name"));
		ib.setInfo(multi.getParameter("name") + "의 사진");
		ib.setFile(
				multi.getParameter("country_code") + "\\" + multi.getParameter("city_code") + "\\" + newFile.getName());
		idao.insertImage(ib);

		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println("<script>");
		out.println("alert('여행지가 추가되었습니다.')");
		out.println("location.href='./TravelAdminList.td'");
		out.println("</script>");
		out.close();

		return null;
	}

}