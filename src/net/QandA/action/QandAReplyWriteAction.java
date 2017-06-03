package net.QandA.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import net.QandA.db.QandABean;
import net.QandA.db.QandADAO;


public class QandAReplyWriteAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("QandAReplyWriteAction execute()");
		
		
		request.setCharacterEncoding("utf-8");
		String realPath = request.getRealPath("/upload");
		int maxSize=5*1024*1024;	//5MB

		MultipartRequest multi = new MultipartRequest(request, realPath, maxSize, "utf-8", new DefaultFileRenamePolicy());
		
		
		QandADAO qdao=new QandADAO();
		QandABean qb = new QandABean();		
		
		String pageNum = multi.getParameter("pageNum");		
		int num=Integer.parseInt(multi.getParameter("num"));
		
	
		

		qb.setNick(multi.getParameter("nick"));
		qb.setSubject(multi.getParameter("subject"));
		qb.setContent(multi.getParameter("content"));
		qb.setImage1(multi.getFilesystemName("image1"));
		qb.setRe_ref(Integer.parseInt(multi.getParameter("re_ref")));
		qb.setRe_lev(Integer.parseInt(multi.getParameter("re_lev")));		
		qb.setRe_seq(Integer.parseInt(multi.getParameter("re_seq")));


		
		qdao.insertQandAReply(qb);

		ActionForward forward = new ActionForward();
		forward.setPath("./QandAList.qna?num="+num+"&pageNum="+pageNum);
		
		forward.setRedirect(true);
		return forward;
	}
}
	

