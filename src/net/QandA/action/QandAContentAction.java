package net.QandA.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.QandA.db.QandABean;
import net.QandA.db.QandADAO;



public class QandAContentAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("QandAContentAction");
		request.setCharacterEncoding("utf-8");
		ActionForward forward = new ActionForward();

		int num = Integer.parseInt(request.getParameter("num"));
		String pageNum = request.getParameter("pageNum");

		QandADAO qdao = new QandADAO();

		QandABean qb = qdao.getQandA(num);// 이 메소드는 boardbean에 정의된 모든 변수를 불러옵니당

		request.setAttribute("qb", qb);
		request.setAttribute("num", num);
		request.setAttribute("pageNum", pageNum);
		
	System.out.println("re_Ref의값"+qb.getRe_ref());

		forward.setPath("./QandA/content.jsp");
		forward.setRedirect(false);

		return forward;
	}

}
