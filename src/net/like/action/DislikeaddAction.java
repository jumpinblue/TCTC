package net.like.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.board.db.BoardDAO;
import net.board.db.boardBean;
import net.like.db.LikeBean;
import net.like.db.LikeDAO;

public class DislikeaddAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");

		System.out.println("DislikeaddAction execute()");
		
		
		//love테이블로 넘어가는 값들(love테이블은 누가 어떤글에 좋아요 취소를하는지 알수 있는 테이블)
		LikeBean lb=new LikeBean();
		LikeDAO ldao=new LikeDAO();
		
		lb.setNick(request.getParameter("nick"));
		lb.setNum(Integer.parseInt(request.getParameter("num")));
		int pageNum=Integer.parseInt(request.getParameter("pageNum"));

		
		ldao.adddislike(lb);//net.like.db.LikeDAO
		
		
		//gram테이블로 넘어가는 값들(어떤 해당글에 좋아요 취소를 했는지 구현,총좋아요갯수(love의 최대값)에서 1을뺌)			
				boardBean bb=new boardBean();
				BoardDAO bdao=new BoardDAO();
				bb.setNum(Integer.parseInt(request.getParameter("num")));
				bb.setLove(Integer.parseInt(request.getParameter("love")));				
				
				bdao.GramDisLike(bb);//net.board.db.BoardDAO
		
		

		ActionForward forward = new ActionForward();
		forward.setPath("./BoardList.bo?pageNum="+pageNum);
		forward.setRedirect(true);
		return forward;	
		
		
	
	}

}
