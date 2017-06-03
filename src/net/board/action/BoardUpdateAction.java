package net.board.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import net.board.db.BoardDAO;
import net.board.db.boardBean;


public class BoardUpdateAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("BoardUpdateAction execute()");
		
		request.setCharacterEncoding("utf-8");
		String realPath = request.getRealPath("/upload");
		int maxSize=5*1024*1024;	//5MB

		MultipartRequest multi = new MultipartRequest(request, realPath, maxSize, "utf-8", new DefaultFileRenamePolicy());

				
		BoardDAO bdao=new BoardDAO();
		boardBean bb=new boardBean();
		

		
		String pageNum=multi.getParameter("pageNum");
		bb.setNum(Integer.parseInt(multi.getParameter("num")));		
		bb.setNick(multi.getParameter("nick"));
		bb.setSubject(multi.getParameter("subject"));
		bb.setContent(multi.getParameter("content"));
		String image1=multi.getFilesystemName("image1");
		//image1이 빈칸이 아니다 = 파일을 추가하였다=> 추가한 파일을 사용한다.
		if(image1!=null){
			bb.setImage1(image1);	
			}
		//image1이 빈칸이다 = 추가한 파일이 없다 => 기존의 image2파일을 그대로 사용한다.
		else{
			bb.setImage1(multi.getParameter("image2"));	
		}	
	
		
		bdao.updateBoard(bb);


		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();		
		out.println("<script>");
		out.println("alert('수정성공')");
		out.println("location.href='./BoardList.bo?pageNum="+pageNum+"';");
		out.println("</script>");
		out.close(); 
		

		return null;
	}
}

