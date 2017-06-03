<%-- <%@page import="java.sql.Statement"%> --%>

<%@page import="net.like.db.LikeDAO"%>
<%@page import="net.like.db.LikeBean"%>
<%@page import="net.member.db.MemberBean"%>
<%@page import="net.reply.db.ReplyDAO"%>
<%@page import="com.mysql.jdbc.jdbc2.optional.SuspendableXAConnection"%>
<%@page import="net.board.db.boardBean"%>
<%@page import="net.board.db.BoardDAO"%>
<%@page import="java.util.List"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    
<!-- Header -->
<jsp:include page="../inc/header.jsp" />
<link rel="stylesheet" href="./assets/css/instagram/list.css"/>	
<div id="top"></div>
<section class="wrapper">

<div id="combine">
	<%	
	
	String id = (String)session.getAttribute("id");
	String nick = (String)session.getAttribute("nick");		
	
	//BoardListAction에서 받아오는 값들
	List boardList=(List)request.getAttribute("bl");
	int count=((Integer)request.getAttribute("count")).intValue();
	int pageSize=((Integer)request.getAttribute("pageSize")).intValue();
	String pageNum=(String)request.getAttribute("pageNum");
	int currentPage=((Integer)request.getAttribute("currentPage")).intValue();
	int pageCount=((Integer)request.getAttribute("pageCount")).intValue();
	int startPage=((Integer)request.getAttribute("startPage")).intValue();
	int endPage=((Integer)request.getAttribute("endPage")).intValue();

	

	BoardDAO bdao = new BoardDAO();//인생샷그램 글들의 DAO객체 생성
	ReplyDAO rdao=new ReplyDAO();//댓글의 DAO생성 하는 이유는 게시판글 보여줄때 댓글 갯수가 몇개인지 제목옆에 표시해주기 위해!

	if(id==null){%>			
	<a onclick="popupToggle()" class="button">인생샷 공유해요</a>
 
	<%}else{%>
			<a href="./BoardWrite.bo" class="button" >인생샷 공유해요</a>
	<%} %>
	
	<div class="table-wrapper">
			<table class="alt" id="table1">
		<%
		//글의 갯수가 0이 아닐경우 하기실행(글 게시)	
		if(count!=0){
			//boardList(boardListAction에서 받아온 값)의 갯수만큼 for문 반복
			for(int i=0;i<boardList.size();i++){				
			//자바빈(boardBean) 변수=배열한칸 접근 배열변수.get()
			//boardList에서 List하나하나 순서대로 불러오는 작업
			boardBean bb=(boardBean)boardList.get(i);
			//좋아요를 구현하기 위해 LikeBean LikeDAO객체 생성			
			LikeBean lb=new LikeBean();
			LikeDAO ldao=new LikeDAO();
			//net.like.db.LikeDAO 의 좋아요 갯수 구하는 메소드 :likecountall
			int likecountall=ldao.getLikecountall(bb.getNum());
			//한사람당 한개만 좋아요갯수를 누르개 하기위해 하기의 getLikecount라는 메소드 생성
			//한사람이 likecount넘버가 짝수일경우 좋아요만 누를수 있게,홀수일경우에는 좋아요 취소만 누를 수 있게 구현
			int likecount=ldao.getLikecount(bb.getNum(), nick);
				
			%>
		<tr>
													<!-- 제목옆에 해당글에 댓글이 몇개 달렸는지 나타내줌 -->
			<td id="titlecolor" colspan="4"><%=bb.getSubject() %>(댓글:<%=rdao.replyCount(bb.getNum()) %>)</td>
			</tr>		
			<tr><td id="darkgrey">닉네임:</td><td ><%=bb.getNick()%></td>
			<td id="darkgrey">업로드날짜:</td><td><%=bb.getDate() %></td>
			</tr>	
			<tr>
			<td colspan="4">
			<a href="./BoardContent.bo?num=<%=bb.getNum()%>&pageNum=<%=pageNum%>">
			<img id="contentimg" src="./upload/<%=bb.getImage1()%>" width=300 height=300 onerror="this.src='./images/instagram/noimage.png'">
			</a>
			</td></tr>
		<%
	
		if(id==null){
		%>
		<tr><td colspan="4">
		<input type="button" value="좋아요" id="like" onclick="popupToggle()">
		</tr></td>
		<%		
		}
		
		else{
		%>			
			<tr>
			<td colspan="4">
			
<!-- 			====================좋아요================= -->
<%
//likecount(한사람이 좋아요를 누른 갯수)가 2로 나누었을때 0일떄, 즉 짝수일때는 좋아요를 누를 수 있게 구현
if(likecount%2==0){	
		%>
			<form action="./LikeaddAction.lk" method="post" name="fr" >				
			<input type="submit" id="like" value="좋아요: )">		
				<%
				//likecountall은 해당글에 해당하는 총 좋아요갯수, 화면상에 표기됨
				if(likecountall>0){ %>회원님 외<%=likecountall %>명<%} %>				
			<input type="hidden" name="num" value="<%=bb.getNum()%>">
			<input type="hidden" name="love" value="<%=bb.getLove()%>">
			<input type="hidden" name="nick" value="<%=nick%>">	
			<input type="hidden" name="pageNum" value="<%=pageNum%>">		
					
				
			</form>
			<%} %>			
						
<!-- 			====================좋아요취소=============== -->

		<%
		//likecount(한사람이 좋아요를 누른 갯수)가 2로 나누었을때 0이 아닐떄, 즉홀수일때는 좋아요취소를 누를 수 있게 구현
		if(likecount%2!=0){	
		%>		
			<form action="./DislikeaddAction.lk" method="post" name="fr">
			<input type="submit" id="dislike" value="좋아요취소 ">
			<%if(likecountall>0){ %>회원님 외<%=likecountall %>명<%} %>			
			<input type="hidden" name="num" value="<%=bb.getNum()%>">
			<input type="hidden" name="love" value="<%=bb.getLove()%>">
			<input type="hidden" name="nick" value="<%=nick%>">		
			<input type="hidden" name="pageNum" value="<%=pageNum%>">		
				
			
			</form>	
			<%} %>
			
			</td>
			</tr>
		<%
		}
			}//아이디가 not null이면
			%>
			
			<tr>
			<td colspan="4" id="listpage">
			
			<%
// 			=======================페이지출력===================
			
			if(count!=0){//글이 한개라도 있을때 하기 출력

				if(endPage>pageCount){
					endPage=pageCount;}
				//이전
				if(startPage>pageSize){
					%><a href="./BoardList.bo?pageNum=<%=startPage-pageSize%>">[이전]</a>
			<%
				}
				//1...10
				for(int i=startPage;i<=endPage;i++){
					%><a href="./BoardList.bo?pageNum=<%=i%>">[<%=i%>]
			</a>
			<%
				}
				//다음
				if(endPage< pageCount){
					%><a href="./BoardList.bo?pageNum=<%=startPage+pageSize %>">[다음]</a>
			<%}		
			}		
			%>			
			</td>
			</tr>
	</table>
	</div>
	<%
	
	}
	%>
	
<!-- 	리스트 배너에 있는 좋아요 갯수가 가장 많은 베스트 -->
	<%
	boardBean bb=new boardBean();
	bb=	bdao.Bestshot();//좋아요가 가장 많은 게시글을 선정해주는 메소드
	
	//베스트 게시글의 좋아요가 몇개인지 게시하기 위해 하기 실행
	LikeDAO ldao=new LikeDAO();
	int likecountall=ldao.getLikecountall(bb.getNum());
	%>

<!-- 인생샷그램 위에 움직이는 왕관모양-->
<img id="AlphbetB" src="./images/instagram/AlphbetB.png">
<img id="AlphbetE" src="./images/instagram/AlphbetE.png">	
<img id="AlphbetS" src="./images/instagram/AlphbetS.png">	
<img id="AlphbetT" src="./images/instagram/AlphbetT.png">	



<!-- 	베스트 게시글 테이블 -->
	<table class="banner">
	<tr><td id="paddingnone">
	<marquee>BEST샷<marquee width=300>←♡-&lt </marquee>
	
	
	
	</td></tr>
	<tr><td>	

<a href="./BoardContent.bo?num=<%=bb.getNum()%>&pageNum=1"><img src="./upload/<%=bb.getImage1()%>" width=180 height=200 onerror="this.src='./images/instagram/noimage.png'"></a>
	</td></tr>
	<tr><td id="paddingnone">좋아	요<%=likecountall %>개</td></tr>
	</table>
	
	
<!-- 	화면 상단/ 하단 이동 아이콘 -->
	<a href="#top"><img title="상단이동" alt="상단이동" id="up" src="./images/instagram/up.png"></a>
	<a href="#bottom"><img title="하단이동" alt="하단이동" id="down" class="down" src="./images/instagram/down.png"></a>
		</div>
	<div id="bottom"></div>
		
</section>
	
</body>
		
<!-- Footer -->
<jsp:include page="../inc/footer.jsp" />

