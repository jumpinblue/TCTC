package net.QandA.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.QandA.db.QandADAO;

public class QandADeleteAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("QandADeleteAction execute()");

		
		QandADAO qdao=new QandADAO();		
		int num=Integer.parseInt(request.getParameter("num"));

		qdao.deleteqna(num);
				
		
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();		
			out.println("<script>");
			out.println("alert('삭제성공')");
			out.println("location.href='./QandAList.qna'");
			out.println("</script>");
			out.close();				
	
				return null;

	}
	

}
