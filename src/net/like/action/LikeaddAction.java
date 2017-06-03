package net.like.action;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.board.db.BoardDAO;
import net.board.db.boardBean;
import net.like.action.ActionForward;
import net.like.db.LikeBean;
import net.like.db.LikeDAO;

public class LikeaddAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		request.setCharacterEncoding("utf-8");		
		System.out.println("LikeaddAction execute()");
		
		//love테이블로 넘어가는 값들(love테이블은 누가 어떤글을 좋아하는지 알수 있는 테이블)
		LikeBean lb=new LikeBean();
		LikeDAO ldao=new LikeDAO();
		
		String nick=request.getParameter("nick");		
		lb.setNick(nick);
		lb.setNum(Integer.parseInt(request.getParameter("num")));
		int pageNum=Integer.parseInt(request.getParameter("pageNum"));

		
		ldao.addLike(lb);//net.like.db.LikeDAO의 addLike에 lb에 담긴값들을 넘겨줌
		
		
		//gram테이블로 넘어가는 값들(gram테이블의 love칼럼은 해당글에 좋아요가 몇개인지 표시해줌)	
		boardBean bb=new boardBean();
		BoardDAO bdao=new BoardDAO();
		bb.setNum(Integer.parseInt(request.getParameter("num")));
		bb.setLove(Integer.parseInt(request.getParameter("love")));		
		
		bdao.GramAddLike(bb);//net.board.db.BoardDAO의 GramAddLike메소드에 bb에 담긴값들을 넘겨줌
		

		ActionForward forward = new ActionForward();
		forward.setPath("./BoardList.bo?pageNum="+pageNum);
		forward.setRedirect(true);
		return forward;	
		

	}


}
