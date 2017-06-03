package net.plan.action;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import jdk.nashorn.internal.ir.RuntimeNode.Request;
import net.images.db.ImagesBean;
import net.images.db.ImagesDAO;
import net.plan.db.PlanCityBean;
import net.plan.db.PlanCountryBean;
import net.plan.db.PlanDAO;

// 다음에서 국가를 검색했을때 나오는 국가 정보를 가져옴
public class PlanNationAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// 한글처리
		request.setCharacterEncoding("UTF-8");
		
		// 파라미터 값 가져오기
		String nation = request.getParameter("nation");	// 국가페이지
		
		// html 가져오기
		Document doc;
		doc = Jsoup.connect("http://search.daum.net/search?w=tot&DA=YZR&t__nil_searchbox=btn&sug=&sugo=&q="+nation).get();
		
		// 다음 국가정보에서 가져옴
		Elements p = doc.select("div.cont_main div.wrap_cont dl.dl_comm dt");
		Elements p2 = doc.select("div.cont_main div.wrap_cont dl.dl_comm dd");

		StringBuffer nation_info = new StringBuffer();
		
		// 테이블 형태로 나오게 문자열 저장
		nation_info.append("<table>");
		for(int i=0; i<p.size(); i++) {
			System.out.print(p.get(i).text()+": "+p2.get(i).text()+"\n");	// 콘솔 확인용
			
			nation_info.append("<tr>");
			
			// dd에 요약정보라는 숨겨져 있는 문자열이 있는데 이걸 다른 문자열로 바꿈
			if(p2.get(i).text().contains("요약정보")) {	
				String txt = p2.get(i).text().replaceAll("요약정보", ", ");
				nation_info.append("<th>"+p.get(i).text()+ "</th>"+"<td>"+txt+"</td>");
				continue;
			}
			
			nation_info.append("<th>"+p.get(i).text()+ "</th>"+"<td>"+p2.get(i).text()+"</td>");
			nation_info.append("</tr>");
		}
		nation_info.append("</table>");
		
/*		// 도시 리스트 가져오기
		List<PlanCityBean> cityList = null;
		cityList = getCityList(nation);*/
		
		// 도시 이미지 리스트 가져오기
		List<ImagesBean> cityImgList = null;
		ImagesDAO idao = new ImagesDAO();
		PlanDAO pdao = new PlanDAO();
		String countryCode = pdao.getCountyCode(nation);	// country_code 를 구하기 위해 국가 정보를 가져옴
		cityImgList = idao.getCityImages(countryCode);
		
		// request에 담기
		request.setAttribute("nation_info", nation_info);
		//request.setAttribute("cityList", cityList);
		request.setAttribute("cityImgList", cityImgList);	// 도시 이미지들 담음
		
		// 이동정보
		ActionForward forward = new ActionForward();
		forward.setPath("./plan/planNation.jsp");
		forward.setRedirect(false);
		
		return forward;
	}
	
	// 도시 리스트 전체 가져오기
	public List<PlanCityBean> getCityList(String nation) {
		
		PlanDAO pdao = new PlanDAO();
		
		List<PlanCityBean> cityList = null;
		cityList = pdao.getCityList(nation);
		
		return cityList;
	}
}
