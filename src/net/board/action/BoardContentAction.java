package net.board.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.board.db.BoardDAO;
import net.board.db.boardBean;
import net.reply.db.ReplyDAO;



public class BoardContentAction implements Action{
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("BoardContentAction");	
		request.setCharacterEncoding("utf-8");
		ActionForward forward=new ActionForward();

//============================게시글 Action=====================

		int num=Integer.parseInt(request.getParameter("num"));
		String pageNum=request.getParameter("pageNum");

		
		BoardDAO bdao=new BoardDAO();
				
		boardBean bb=bdao.getBoard(num);//이 메소드는 boardbean에 정의된 모든 변수를 불러옵니당	

		
		request.setAttribute("bb", bb);
		request.setAttribute("num", num);
		request.setAttribute("pageNum", pageNum);
		
//============================댓글 Action=====================

		ReplyDAO rdao = new ReplyDAO();
		int count = rdao.replyCount(num);//해당글에 댓글이 총 몇개인지 세어주는 메소드
		int pageSize=5;//페이지당 보여줄 댓글 수~
		String replypageNum=request.getParameter("replypageNum");//댓글에도 페이지넘버가필요하겠쬬?
		if (replypageNum == null) {//댓글의 페이지넘버가 없으면
			replypageNum = "1";//무조건 1페이지
		}		
		
		int currentPage = Integer.parseInt(replypageNum);//현재페이지는 replypageNum값을 int값으로 바꿔요
		int startrow = (currentPage - 1) * pageSize + 1;//시작줄 계산하는 연산
		 // 시작 줄 	=	(3-1)			* 10 +1 =21 줄부터시작!
		int endRow=currentPage*pageSize;
		//  끝줄 = 	3*10 =30줄까지!
		
		//list를 사용하여 list.jsp에 넘겨줄 값들 setAtrribute에 담기
		List replylist=null;
		if(count!=0){
			//하기는 댓글 모든 리스트를 가져와주는 메소드
			//기존 메소드랑 다른점은 해당 글의 댓글이여야하므로 num값도 인자로 같이 넘겨줘야되요~
			replylist=rdao.getReplyList(startrow, pageSize,num);
		}
		
		int replycount=rdao.replyCount(num);//특정글의 댓글이 몇개인지 세어주는 메소드
		
		
		//전체 페이지수 구하기 게시판 글 50개 한화면에 보여줄 글개수 10 =>5  전체페이지+0=>5
		//게시판 글 56개 한화면에 보여줄 글개수 10 =>5 전체페이지+1(나머지)=>6 
		int pageCount=replycount/pageSize+(replycount%pageSize==0?0:1);
		//한 화면에 보여줄 페이지 번호 개수
		
		//시작페이지 번호 구하기 1~10=>1  11~20=>11  21~30=>21
		int startPage=((currentPage-1)/pageSize)*pageSize+1;
		//끝페이지 번호 구하기
		int endPage=startPage+pageSize-1;

		request.setAttribute("rl", replylist);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("replycount", replycount);//해당글의 댓글이 몇개인지 알려주는변수
		request.setAttribute("pageCount", pageCount);
		request.setAttribute("startPage", startPage);
		request.setAttribute("endPage", endPage);
		
		forward.setPath("./instagram/content.jsp");
		forward.setRedirect(false);
		
		return forward;

	
	}
	

}

