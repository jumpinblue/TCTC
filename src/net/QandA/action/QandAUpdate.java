package net.QandA.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.QandA.db.QandABean;
import net.QandA.db.QandADAO;


public class QandAUpdate implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("QandAUpdate()");
		ActionForward forward=new ActionForward();
		request.setCharacterEncoding("utf-8");
		
		QandADAO qdao=new QandADAO();
		
		int num=Integer.parseInt(request.getParameter("num"));
		QandABean qb=qdao.getQandA(num);
		
		request.setAttribute("qb", qb);
		forward.setRedirect(false);
		forward.setPath("./QandA/updateForm.jsp");
		return forward;		
	}
	

}
