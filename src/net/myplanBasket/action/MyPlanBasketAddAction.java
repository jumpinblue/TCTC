package net.myplanBasket.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.myplanBasket.db.MyPlanBasketBean;
import net.myplanBasket.db.MyPlanBasketDAO;

public class MyPlanBasketAddAction implements Action {
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		System.out.println("excuted MyPlanBasketAddAction");
		
		// 세션값 가져오기
		// 세션값 없으면 ./MemberLogin.me
		HttpSession session = request.getSession();

		String id = (String) session.getAttribute("id");
		int travel_id = Integer.parseInt(request.getParameter("travel_id"));

		ActionForward forward = new ActionForward();
		if (id == null) {
			forward.setRedirect(true);
			forward.setPath("./MemberLogin.me");
			return forward;
		}
		// 한글처리
		request.setCharacterEncoding("utf-8");
		// 자바빈 파일 만들기 net.basket.db BasketBean
		// 객체생성 BasketBean basketbean
		MyPlanBasketBean basketbean = new MyPlanBasketBean();
		// 폼 => 자바빈 저장 num amount size color id

		basketbean.setId(id);
		basketbean.setTravel_id(travel_id);
		
		

		if (request.getParameter("plan_nr") != null) {
			basketbean.setPlan_nr(Integer.parseInt(request.getParameter("plan_nr")));
			basketbean.setItem_nr(Integer.parseInt(request.getParameter("item_nr")));
			basketbean.setFirstday(request.getParameter("firstday"));
			basketbean.setLastday(request.getParameter("lastday"));
			basketbean.setDay_nr(Integer.parseInt(request.getParameter("day_nr")));
			basketbean.setDay_night(request.getParameter("day_night"));
			basketbean.setUser_lat(Float.parseFloat(request.getParameter("user_lat")));
			basketbean.setUser_lng(Float.parseFloat(request.getParameter("user_lng")));
			basketbean.setDate(request.getParameter("date"));
			basketbean.setMemo(request.getParameter("memo"));
			basketbean.setPlan_done_nr(Integer.parseInt(request.getParameter("plan_done_nr")));
		}		
		
		// 디비 파일 만들기 net.basket.db BasketDAO
		// 객체 생성 basketdao
		MyPlanBasketDAO basketdao = new MyPlanBasketDAO();
		// int check=상품 중복체크 중복이면 수량 update <= 1
		// checkGoods(BasketBean basketbean)
		int check = basketdao.checkBasket(basketbean);
		if (check != 1) {
			// 메서드호출 basketAdd(basketbean)
			basketdao.basketAdd(basketbean);
		}
		
		// 이동 ./BasketList.ba
		forward.setRedirect(true);
		forward.setPath("./MyPlan.pln");
		return forward;
	}
}
