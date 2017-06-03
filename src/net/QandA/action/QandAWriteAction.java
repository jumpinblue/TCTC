package net.QandA.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import net.QandA.db.QandABean;
import net.QandA.db.QandADAO;


public class QandAWriteAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("QandAWriteAction execute()");

		request.setCharacterEncoding("utf-8");
		String realPath = request.getRealPath("/upload");
		int maxSize=5*1024*1024;	//5MB

		MultipartRequest multi = new MultipartRequest(request, realPath, maxSize, "utf-8", new DefaultFileRenamePolicy());

		
		QandADAO qdao=new QandADAO();
		QandABean qb=new QandABean();
		qb.setNick(multi.getParameter("nick"));
		qb.setSubject(multi.getParameter("subject"));
		qb.setContent(multi.getParameter("content"));
		qb.setImage1(multi.getFilesystemName("image1"));

		qdao.insertQandA(qb);
		

		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();		
		out.println("<script>");
		out.println("alert('Q&A가 작성되었숩니당.')");
		out.println("location.href='./QandAList.qna'");
		out.println("</script>");
		out.close(); 

		return null;
	}
	

}
