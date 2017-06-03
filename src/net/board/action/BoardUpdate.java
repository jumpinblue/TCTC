package net.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import net.board.db.boardBean;
import net.board.db.BoardDAO;



public class BoardUpdate implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("BoardUpdate execute()");
		ActionForward forward=new ActionForward();
		request.setCharacterEncoding("utf-8");

		
		BoardDAO bdao=new BoardDAO();
		
		int num=Integer.parseInt(request.getParameter("num"));
		boardBean bb=bdao.getBoard(num);
		
		request.setAttribute("bb", bb);
		forward.setRedirect(false);
		forward.setPath("./instagram/updateForm.jsp");
		return forward;				
	
	}

}
