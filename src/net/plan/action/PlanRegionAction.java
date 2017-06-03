package net.plan.action;

import java.io.IOException;
import java.security.PrivateKey;
import java.sql.Timestamp;
import java.util.List;

import javax.crypto.Cipher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import net.images.db.ImagesBean;
import net.images.db.ImagesDAO;
import net.plan.db.PlanCityBean;
import net.plan.db.PlanDAO;
import net.plan.db.PlanTravelBean;

public class PlanRegionAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		System.out.println("PlanRegionAction execute()");
		request.setCharacterEncoding("UTF-8");

		//지역값 받아오기.
		String region = request.getParameter("region");
		String city_code = request.getParameter("city_code");
		
		//객체 생성
		PlanDAO pdao = new PlanDAO();
		
			/*** 지역설명(정보) 스크랩핑 시작 ***/
			// 지역값을 이용해 지역의 값을 빈에 담기
			PlanCityBean pcb = pdao.getCity(region);
			
			CityScraping cs = new CityScraping(pcb);
			StringBuffer region_info;
			if("kr".equals(pcb.getCountry_code())) {// 국내
				region_info = cs.getDomesticCity();
			}else {	// 해외 또는 없는 값
				region_info = cs.getForeignCity();
			}
			request.setAttribute("region_info", region_info);
			/*** 지역설명(정보) 스크랩핑 끝 ***/
		
		/* 지역의 각종 관광지, 맛집, 숙소 가져오는 작업 */
		//지역값 받아와서 글 갯수에 조건 걸어서, 지역에 맞는 글만 가져오는 과정 필요.
		//타입값 받아와서 글 타입에 조건 걸어서, 맛집, 관광지, 숙소 골라서 보기 가능하도록 할 필요.
			
		// 관광지 이미지 리스트 가져오기
		List<ImagesBean> travelImgList = null;
		ImagesDAO idao = new ImagesDAO();
		String cityCode = pdao.getCityCode(region);	// region_code 를 구하기 위해 국가 정보를 가져옴
		travelImgList = idao.getTravelImages(cityCode);
		
		request.setAttribute("travelImgList", travelImgList);	// 관광지 이미지들 담음
		
		//DB에 등록된 글의 개수.
		int count = pdao.getTravelCount(region, city_code);
		
		// 한페이지에 보여줄 글의 개수
		int pageSize = 4;

		// 현페이지가 몇페이지인지 가져오기(기본 1페이지)
		String pageNum = request.getParameter("pageNum");
		if (pageNum == null)
			pageNum = "1"; // pageNum없으면 무조건 1페이지

		// 시작글 구하기 1 11 21 31 ... <= pageNum, pageSize 조합
		int currentPage = Integer.parseInt(pageNum);
		int startRow = (currentPage - 1) * pageSize + 1;

		// 끝행구하기
		int endRow = currentPage * pageSize;

		List<PlanTravelBean> planTravelList = null;
		if (count != 0)
			planTravelList = pdao.getTravelList(startRow, pageSize, region, city_code);
		
		request.setAttribute("planTravelList", planTravelList);
		request.setAttribute("count", count);
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("city_code", pcb.getCity_code());

		ActionForward forward = new ActionForward();
		
		return forward;
	}

}

// 도시 정보 스크랩핑
class CityScraping {
	
	PlanCityBean pcb;	// 지역(도시)
	StringBuffer sb;	// 여기에 출력할 html 형식의 text를 담음
	
	// 생성자
	CityScraping(PlanCityBean pcb) {
		this.pcb = pcb;
		this.sb = new StringBuffer();
	}
	
	// 국내도시 정보 스크랩핑
		// 국내도시는 다음백과에서 가져오는데 일일이 도시마다 다른 값을 가져와야함, 해외는 다음검색 해외지 미리보기에서 바로 가져올 수 있음
	public StringBuffer getDomesticCity() {
		// 국내도시 목록
		final String BUSAN = "b10b0860b";
		final String ANDONG = "b14a3029b";
		final String JEJU = "b19j1942b";
		final String SEOUL = "b11s3796b";
		final String DAEGU = "b04d2569b";
		
		String city = "";
		
		switch(pcb.getEn_name()) {
			case "busan" : city = BUSAN;
						 break;
			case "andong" : city = ANDONG;
			 			 break;
			case "jeju" : city = JEJU;
			 			 break;
			case "seoul" : city = SEOUL;
			 			 break;
			case "daegu" : city = DAEGU;
						 break;
		}
		
		// html 가져오기
		Document doc;
		try {
			// 다음 검색하면 다음백과 미리보기에서 가져오는데 해외도시는 미리보기가 뜨는데 국내도시는 안떠서 국내는 다음백과에서 따로 가져와야함, 검색값이 암호화됫는지 이상하게 나와서
			// 그 값을 따로 저장해두고 따로 가져와야함.
			doc = Jsoup.connect("http://100.daum.net/encyclopedia/view/"+city).get();
							
			// 특정 값 가져오기
			Elements p = doc.select(".info_details tbody th");
			Elements p2 = doc.select(".info_details tbody td");
			
			sb.append("<table>");
			for(int i=0; i<p.size()-1; i++) {	// 소개 부분은 빼기 위해
				Element e = p.get(i);
				sb.append("<tr><th>"+e.text()+"</th>");
				Element e2 = p2.get(i);
				sb.append("<td>"+e2.text()+"</td></tr>");
			}
					
			Elements p3 = doc.select(".info_details .desc_summary");
			Element e3 = p3.get(0);
			String e3_txt = e3.text().replace("요약", "");	// 앞의 요약 이라는 단어를 지운다.
			sb.append("<tr><td colspan='2' style='text-align: left;'>"+e3_txt+"</td></tr>");
			sb.append("</table>");
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			System.out.println("가져올 정보가 없음.");
			return sb;
		}
		
		return sb;
	}
		
	// 해외도시 정보 스크랩핑
	public StringBuffer getForeignCity() {
		
		Document doc;
		try {
			String city = pcb.getName();
			
			// 다음 검색하면 다음백과 미리보기에서 가져오는데 해외도시는 미리보기가 뜨는데 국내도시는 안떠서 국내는 다음백과에서 따로 가져와야함, 검색값이 암호화됫는지 이상하게 나와서
			// 그 값을 따로 저장해두고 따로 가져와야함.
			doc = Jsoup.connect("http://search.daum.net/search?w=tot&DA=YZR&t__nil_searchbox=btn&sug=&sugo=&q="+city).get();
			
			// 특정 값 가져오기
			Elements p = doc.select("#cityNColl .wrap_cont .dl_comm dt");
			Elements p2 = doc.select("#cityNColl .wrap_cont .dl_comm dd");
			
			sb.append("<table>");
			for(int i=0; i<p.size()-1; i++) {	// 소개 부분은 빼기 위해
				Element e = p.get(i);
				sb.append("<tr><th>"+e.text()+"</th>");
				Element e2 = p2.get(i);
				sb.append("<td>"+e2.text()+"</td></tr>");
			}
			sb.append("</table>");

		} catch (IOException e1) {
			System.out.println("가져올 정보가 없음.");
			return sb;
		}		
		
		return sb;
	}
}

