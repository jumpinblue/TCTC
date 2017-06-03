package net.QandA.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import net.QandA.db.QandABean;
import net.QandA.db.QandADAO;


public class QandAUpdateAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
System.out.println("QandAUpdateAction execute()");
		
		request.setCharacterEncoding("utf-8");
		String realPath = request.getRealPath("/upload");
		int maxSize=5*1024*1024;	//5MB

		MultipartRequest multi = new MultipartRequest(request, realPath, maxSize, "utf-8", new DefaultFileRenamePolicy());

		QandADAO qdao=new QandADAO();
		QandABean qb=new QandABean();
		

		
		String pageNum=multi.getParameter("pageNum");
		
		
		qb.setNum(Integer.parseInt(multi.getParameter("num")));		
		qb.setNick(multi.getParameter("nick"));
		qb.setSubject(multi.getParameter("subject"));
		qb.setContent(multi.getParameter("content"));
		String image1=multi.getFilesystemName("image1");
		//image1이 빈칸이 아니다 = 파일을 추가하였다=> 추가한 파일을 사용한다.
		if(image1!=null){
			qb.setImage1(image1);	
			}
		//image1이 빈칸이다 = 추가한 파일이 없다 => 기존의 image2파일을 그대로 사용한다.
		else{
			qb.setImage1(multi.getParameter("image2"));	
		}	
	
		qdao.updateQandA(qb);


		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();		
		out.println("<script>");
		out.println("alert('수정성공')");
		out.println("location.href='./QandAList.qna?pageNum="+pageNum+"';");
		out.println("</script>");
		out.close(); 
		

		return null;
	}
	
	

}
