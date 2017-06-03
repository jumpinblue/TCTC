package net.plan.action;

import java.io.File;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import net.images.db.ImagesBean;
import net.images.db.ImagesDAO;
import net.plan.db.PlanCityBean;
import net.plan.db.PlanDAO;

public class CityUpdateAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("CityUpdateAction execute()");
		
		request.setCharacterEncoding("utf-8");
		
		String realPath = request.getSession().getServletContext().getRealPath("/images/plan/nation");	// 국가폴더 전까지만
		int maxSize = 10 * 1024 * 1024;
		
		MultipartRequest multi = new MultipartRequest(request, realPath, maxSize, "UTF-8", new DefaultFileRenamePolicy());
		
		String beforeCountryCode = multi.getParameter("beforeCountryCode");	// 이전 국가코드
		String beforeCityCode = multi.getParameter("city_code");	// 이전 도시코드
		String beforeEnName = multi.getParameter("before_en_name");	// 이전 도시영어이름
		
		PlanCityBean pcb =  new PlanCityBean();
		pcb.setCity_code(multi.getParameter("en_name"));
		pcb.setCountry_code(multi.getParameter("country_code"));
		pcb.setName(multi.getParameter("name"));
		pcb.setEn_name(multi.getParameter("en_name"));
		pcb.setInfo(multi.getParameter("info"));
		
		// 이미지 작업
		ImagesDAO idao = new ImagesDAO();
		String newImg = multi.getFilesystemName("file");
		
		// 이미지가 애초에 없으면 DB에 정보가 없기 때문에  DB에 이미지정보를 미리 넣어줌
		if(!idao.getIsCityImage(beforeCityCode)) {
			
			String newImgType = newImg.substring(newImg.lastIndexOf('.'), newImg.length());	// 이미지 파일 확장자
			String newImgName = beforeCityCode+newImgType;	// 이미지 이름.확장자 이름
			
			ImagesBean ib = new ImagesBean();
			ib.setCountry_code(beforeCountryCode);
			ib.setCity_code(beforeCityCode);
			ib.setFile(beforeCountryCode+"\\"+newImgName);
			ib.setInfo(pcb.getName());
			ib.setName(pcb.getName());
			
			idao.insertImage(ib);
		}
		
		String cityImgPath = idao.getCityImgPath(beforeCityCode);	// DB의 파일(경로)정보 가져옴
		
		// 수정할 이미지를 작성한 경우 이미지 변경 작업
		if(newImg!=null) {
			// 디렉토리가 없으면 디렉토리 만듬
			File countryDir = new File(realPath + "\\" + beforeCountryCode);
			if(!countryDir.exists()) {
				countryDir.mkdirs();
			}
			
			// 기존의 이미지 삭제
			File curFile = new File(realPath+"\\"+cityImgPath);
			if(curFile.exists()) {
				curFile.delete();
			}
			// 새로 넣은 이미지 새로운 이미지 명으로 국가 폴더로 옮기기
			File newFile1 = new File(realPath+"\\"+newImg);
			String newImgType = newImg.substring(newImg.lastIndexOf('.'), newImg.length());	// 이미지 파일 확장자
			String newImgName = beforeCityCode+newImgType;
			File newFile2 = new File(realPath+"\\"+beforeCountryCode+"\\"+newImgName);
			if(newFile1.exists()) {
				newFile1.renameTo(newFile2);
			}
			
			// 이미지 DB에서 예전 city_code를 찾아서 현재 city_code로 변경, 다른건 같으나 확장자가 변경될 수 있기 때문에 작업해줘야 함
			idao.updateFile(beforeEnName, beforeCityCode, beforeCountryCode+"\\"+newImgName);
		}
		
		// 국가코드가 바뀐 경우 이미지를 바뀐 폴더로 이동
		if(!beforeCountryCode.equals(pcb.getCountry_code())) {
			cityImgPath = idao.getCityImgPath(beforeCityCode);
			String cityImgType = cityImgPath.substring(cityImgPath.lastIndexOf('.'), cityImgPath.length());	// 이미지 파일 확장자
			File curFile = new File(realPath + "\\" + cityImgPath);
			File newFile = new File(realPath + "\\" + pcb.getCountry_code() + "\\" +beforeCityCode+cityImgType);
			if(curFile.exists()) {
				curFile.renameTo(newFile);
			}
			
			// 이미지 DB에서 city_code 찾아서 국가코드 변경
			String newFileName = pcb.getCountry_code()+"\\"+newFile.getName();
			idao.updateCityCode(beforeCityCode, pcb.getCountry_code(), newFileName);
		}
		
		// 도시 영어이름이 바꼈을때 이미지 파일 이름을 변경
		if(!beforeEnName.equals(pcb.getEn_name())) {
			cityImgPath = idao.getCityImgPath(beforeCityCode);
			String cityImgType = cityImgPath.substring(cityImgPath.lastIndexOf('.'), cityImgPath.length());	// 이미지 파일 확장자
			File curFile = new File(realPath + "\\" + cityImgPath);
			File newFile = new File(realPath + "\\" + pcb.getCountry_code() + "\\" + pcb.getEn_name()+cityImgType);
			if(curFile.exists()) {
				curFile.renameTo(newFile);
			}
			
			// 이미지 DB에서 예전 city_code를 찾아서 현재 city_code로 변경, city_code == en_name
			String newFileName = pcb.getCountry_code() + "\\" + newFile.getName();
			idao.updateFile(beforeEnName, pcb.getEn_name(), newFileName);
		}
		
		PlanDAO pdao = new PlanDAO();
		
		int check = pdao.updateCity(pcb, beforeCityCode);
		
		if(check==1){
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();		
			out.println("<script>");
			out.println("alert('수정되었습니다.')");
			out.println("location.href='./CityList.pl'");
			out.println("</script>");
			out.close(); 
			}
		return null;
	}

}
