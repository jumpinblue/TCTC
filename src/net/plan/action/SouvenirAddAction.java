package net.plan.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import net.plan.db.PlanDAO;

public class SouvenirAddAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		System.out.println("SouvenirAddAction execute()");
		
		//한글 처리
		request.setCharacterEncoding("utf-8");
		String realPath=request.getRealPath("/upload");
		int maxSize=5*1024*1024;

		MultipartRequest multi = new MultipartRequest(request, realPath, maxSize, "utf-8", new DefaultFileRenamePolicy());
		
		
		PlanDAO pdao = new PlanDAO();
		
		
		
		return null;
	}

}
