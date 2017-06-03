package net.myplanBasket.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.myplanBasket.db.MyPlanBasketDAO;

public class MyPlanBasketDeleteAction implements Action{
	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		System.out.println("BasketDeleteAction");
		//세션값 가져오기
		HttpSession session=request.getSession();
		String id=(String)session.getAttribute("id");
		//세션값 없으면 ./MemberLogin.me 이동
		ActionForward forward=new ActionForward();
		if(id==null){
			forward.setRedirect(true);
			forward.setPath("./MemberLogin.me");
			return forward;
		}
		// b_num 가져오기
		int b_num=Integer.parseInt(request.getParameter("b_num"));
		// 디비객체 생성 basketdao
		MyPlanBasketDAO basketdao=new MyPlanBasketDAO();
		//메서드호출 basketDelete(b_num)
		//basketdao.basketDelete(b_num);
		//이동 ./BasketList.ba
		forward.setRedirect(true);
		forward.setPath("./BasketList.ba");
		return forward;
	}
}
