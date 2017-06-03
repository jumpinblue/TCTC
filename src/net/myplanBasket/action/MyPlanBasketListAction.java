package net.myplanBasket.action;

import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.myplanBasket.db.MyPlanBasketDAO;

public class MyPlanBasketListAction implements Action{
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//세션 가져오기
		HttpSession session=request.getSession();
		String id=(String)session.getAttribute("id");
		//세션값 없으면  ./MemberLogin.me
		ActionForward forward=new ActionForward();
		
		
		
		if(id==null){
			forward.setRedirect(true);
			forward.setPath("./MemberLoginAction.me");
			return forward;
		}
		//BasketDAO 객체 생성 basketdao
		MyPlanBasketDAO basketdao=new MyPlanBasketDAO();
		
		//Vector vector= 메서드호출  getBasketList(String id)
		//  => Vector vector=new Vector();
		
		Vector vector=basketdao.getBasketList(id);
		String gold = basketdao.getMemberGold(id);
		
		//List basketList = vector 첫번째데이터
		List basketList=(List)vector.get(0);
		
		//List goodsList = vector 두번째데이터
		List goodsList=(List)vector.get(1);
		
		// 저장 basketList goodsList
		request.setAttribute("basketList", basketList);
		request.setAttribute("goodsList", goodsList);
		request.setAttribute("gold", gold);
		
		//이동   ./goods_order/goods_basket.jsp
		
		System.out.println("myplanbasketlistaction 진입");
		forward.setRedirect(false);
		forward.setPath("./myplan/myplan.jsp");
		return forward;
	}
}
