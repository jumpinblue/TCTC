<%@page import="net.reply.db.ReplyBean"%>
<%@page import="java.util.List"%>
<%@page import="net.reply.db.ReplyDAO"%>
<%@page import="net.board.db.boardBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="./js/jquery-3.2.0.js"></script>
<style type="text/css">
/*하기는 대댓글창(처음에는 숨겨놓고 func1함수실행되면 보이게한당 */
#replytextarea{
display:none;
}
/* 하기는 수정창(처음에는 숨겨놓고 func3()이 실행되면 보이게한당) */
#replymodify{
display:none;
}
</style>

<script type="text/javascript">
// 하기에 대댓글창을 보면 아이디는 replytextarea로 주고 클래스는 replyreply로 아이디, 클래스 두개 다 부여함
// 그 이유는 아이디는 기존에 display:none값을 display로 해제하기위함이고
// replyreply클래스는 뒤에 변수를 넘겨서 클릭한 해당댓글에만 창이 뜨게 하기 위해서이다.
// (처음 css에서 none속성을 주고싶은데 변수까지 제어가 안되어 두개준것임)
function func1(re_num){
	$(document).ready(function() {	
		var x=document.getElementById('replytextarea');		
		x.style.display='display';		
		$('.replyreply'+re_num).toggle('slow',function(){				
			});	
	});	
	}	
function func2(){alert("정말 삭제하시겠습니까?");}


function func3(i){
	// 댓글 수정 버튼을 누를 때 뜨는 창도
	//func1()과 같은 로직으로 id="replymodify" class="replymodifyclass" 이렇게 두개 부여	
	$(document).ready(function() {
		var x=document.getElementById('originalreply'+i);		
		x.style.display='none';		
			$('.replymodifyclass'+i).toggle('slow',function(){				
			});	
	});
	
}


</script>

<!-- Header -->
<jsp:include page="../inc/header.jsp" />
<link rel="stylesheet" href="./assets/css/instagram/content.css"/>	
	<%	
	
	String id = (String)session.getAttribute("id");			// 아이디
	String nick = (String)session.getAttribute("nick");	// 닉네임
	
	
// 	하기는 BoardContentAction에서 받아오는 아이들
		boardBean bb = (boardBean) request.getAttribute("bb");
		ReplyBean rb=new ReplyBean();
		int num=((Integer)request.getAttribute("num")).intValue();
		String pageNum = request.getParameter("pageNum");//이거는 해당 글의 페이지넘버!(댓글 페이지넘은 따로있음)

		//엔터키 \r\n => <br>로 바꾸기
		String content=bb.getContent();
		if(content!=null){
		content=bb.getContent().replace("\r\n","<br>");
		}

	%>
	<section class="wrapper">
	<div id="combine">
	<marquee behavior="scroll" width="500" scrollamount="2" scrolldelay="50"><h1><%=bb.getNick() %>님의 멋진 인생샷♡</h1></marquee>

	<table class="table1">
		<tr>
			<td id="titlecolor" colspan="2"><%=bb.getSubject()%></td>
			<td ><%=nick%></td>
			<td id="datecolor"><%=bb.getDate()%></td>
		</tr>
		<tr>
			<td colspan="4"><a href="./upload/<%=bb.getImage1()%>"><%=bb.getImage1()%></a></td>
		</tr>
		<tr>
			<td colspan="4">
<!-- 			onerror의 쓰임새는 이미지 에러가 뜰때는 noimage를 출력시켜라...  -->
			<img src="./upload/<%=bb.getImage1()%>"
				width=400 height=400 onerror="this.src='./images/instagram/noimage.png'"></td>
		</tr>
		<tr>
			<td colspan="4"><%=bb.getContent()%></td>
		</tr>
		<tr>
		<td>
		<%
		//로그인한 닉네임(세션값 닉네임)이랑 글쓴닉네임(boardContentAction 에서 받아온닉네임)이 같으면 글 수정및 삭제가가능해용
		//BoardContentAction에서 받아온 닉네임.equals(세션값 닉네임)		
		if(bb.getNick().equals(nick)){%>	
	<input id="opacitynone" type="button" style="margin-left:50px;"
	 value="글수정" title="글수정" onclick="location.href='./BoardUpdate.bo?num=<%=bb.getNum()%>&pageNum=<%=pageNum%>'">
		</td>
		<td colspan="2">
				<form action="./BoardDeleteAction.bo" method="post" name="fr" onclick="func2()">					
					<input type="hidden" value="<%=num%>" name="num"> 															
					<input type="hidden" value="<%=pageNum%>" name="pageNum">					
					<input id="opacitynone" type="submit" value="글삭제" title="글삭제">
				</form>
				</td>	

		<%} %>
<td colspan="4">
	<input id="listbutton" type="button" value="글목록 " title="글목록"
		onclick="location.href='BoardList.bo?pageNum=<%=pageNum%>'">		
	</td>		
		</tr>		
	</table>

<!-- ==========================여기서부터는 댓글입니당 ~~~~========================= -->
	<%
	

	
		//밑에 아이들은 net.board.action.BoardContentActio에서 가져오는 아이들
		//(위의 게시글이랑 댓글 부분의 action분리하지않고 같은페이지로 불러 왔어용)
		//코드가 짧아서 같은페이지에서  불러오고 같은 jsp페이지에 출력도록 했는데 이러면 ajax가 잘 안됩니다... 
		ReplyDAO rdao = new ReplyDAO();	
		int replycount=((Integer)request.getAttribute("replycount")).intValue();		
		List replylist = (List) request.getAttribute("rl");	
		int pageSize = ((Integer) request.getAttribute("pageSize")).intValue();
		int currentPage = ((Integer) request.getAttribute("currentPage")).intValue();		
		int pageCount = ((Integer) request.getAttribute("pageCount")).intValue();
		int startPage = ((Integer) request.getAttribute("startPage")).intValue();
		int endPage = ((Integer) request.getAttribute("endPage")).intValue();
		//하기의 댓글 페이지넘버는 Attribute가 아닌 parameter 값으로 받아왔습니다~~ 주소창에 parameter표기를 위해!
		String replypageNum = request.getParameter("replypageNum");
		
			if(replycount!=0){//댓글이 하나라도 있을때 실행

	%>
	<table class="table2">
		<tr>
			<td colspan="2">닉네임</td>		
			<td id="wordkbreak" colspan="2">답 글</td>
			<td>작성일</td>
		</tr>
		<%for(int i=0;i<replylist.size();i++){
			rb = (ReplyBean)replylist.get(i);
			%>

<!-- =================댓글 수정시 뜨는창 맨 위에 보면 처음에는 display:none으로 숨겨놨어요=============== -->
		<tr id="replymodify" class="replymodifyclass<%=i%>">
			<td colspan="2"><%=rb.getNick() %></td>
			<td colspan="2">	
					
			<form action="RreplyUpdateAction.re" method="post" name="fr">
					<input type="hidden" value="<%=nick %>" name="nick"> 
					<input type="hidden" value="<%=rb.getNum()%>" name="num"> 
					<input type="hidden" value="<%=rb.getRe_num() %>" name="re_num">									
					<input type="hidden" value="<%=pageNum%>" name="pageNum"> 
					<input type="text" value="<%=rb.getContent() %>" name="content">			
					<input type="submit" id="txt" value="수정">			
			</form>	
					
			</td>			
			<td><%=rb.getDate()%></td>
			</tr>
<!-- 		======================================================================== -->
<!-- 		밑에 originalreply라는 아이디를 준 이유는 댓글 수정을 눌렀을때 하기 tr은 숨기고 수정 tr이 나타나게 하기 위함 -->
		<tr id="originalreply<%=i%>">
		<td colspan="2">
		<%
		int wid=0;
		if(rb.getRe_lev()>0){
			wid=40*rb.getRe_lev();
			%>
			<img src="./images/instagram/level.gif" width="<%=wid%>" height="15">
			<img src="./images/instagram/re.png" width="18" height="16">
			
			
	<%	} %>
		<%=rb.getNick() %>
		</td>	
	<td id="wordkbreak" colspan="2">	

	<%=rb.getContent()%>

<%
//로그인닉넴이랑 댓글 쓴사람이 동일할때만 삭제, 수정 보이게 제어
if(rb.getNick().equals(nick)){%>
<input title="댓글삭제" type="button" id="txt" value="삭제" onclick="location.href='./ReplyDelete.re?num=<%=rb.getNum()%>&pageNum=<%=pageNum%>&re_num=<%=rb.getRe_num()%>'">
<%int re_num=rb.getRe_num(); %>	
<input title="댓글수정" type="button" id="txt" value="수정" onclick="func3(<%=i%>)">
<%}
//로그인 되 있으면 대댓글 달수있게 제어, 대댓글 func1()누르면 맨 상단의 jQuery작동
if(	id!=null){%>
<input title="댓글의댓글" type="button" id="txt" value="댓글" onclick="func1(<%=rb.getRe_num()%>)">
<%} %>

		</td>
		
		<td><%=rb.getDate() %></td>
		</tr>	
		
<!-- 		==========func1()이 동작하면 대댓글창, 초기화면에는 display:none으로 숨겨져 있습니다========== -->
				<tr id="replytextarea" class="replyreply<%=rb.getRe_num() %>"><td colspan="5">
				<form action="ReplyReplyWriteAction.re" method="post" name="fr">
					<input type="hidden" value="<%=nick %>" name="nick"> 
					<input type="hidden" value="<%=rb.getNum()%>" name="num"> 
					<input type="hidden" value="<%=rb.getRe_ref() %>" name="re_ref">					
					<input type="hidden" value="<%=rb.getRe_lev() %>" name="re_lev">
					<input type="hidden" value="<%=rb.getRe_seq() %>" name="re_seq">
					<input type="hidden" value="<%=rb.getRe_num() %>" name="re_num">
									
					<input type="hidden" value="<%=pageNum%>" name="pageNum"> 
					<textarea rows="2" cols="100" name="content"></textarea>
					<input type="submit" id="txt2" value="입력">
				</form>		
		</td></tr>		
<!-- 		============================================================================== -->
		<%} %>
		
		
<!-- 		댓글입력창 -->

		<tr id="replytextarea2">
			<td colspan="6">

				<form action="./ReplyWriteAction.re" method="post" name="fr">
					<input type="hidden" value="<%=nick%>" name="nick"> 
					<input type="hidden" value="<%=num%>" name="num"> 
					<input type="hidden" value="<%=rb.getRe_ref() %>" name="re_ref">								
					<input type="hidden" value="<%=rb.getRe_lev() %>" name="re_lev">
					<input type="hidden" value="<%=rb.getRe_seq() %>" name="re_seq">
					<input type="hidden" value="<%=rb.getRe_num() %>" name="re_num">										
					<input type="hidden" value="<%=pageNum%>" name="pageNum"> 
					
					<textarea rows="2" cols="80" name="content"></textarea>
					<input type="submit" id="txt2"  value="입력">
				</form>
				
				<%
				//댓글 페이지 출력
				if(replycount!=0){
	
					if(endPage>pageCount){
						endPage=pageCount;}
					//이전
					if(startPage>pageSize){
						%><a href="./BoardContent.bo?num=<%=num %>&pageNum=<%=pageNum%>&replypageNum=<%=startPage-pageSize%>">[이전]</a>
				<%
					}
					//1...10
					for(int i=startPage;i<=endPage;i++){
						%><a href="./BoardContent.bo?num=<%=num %>&pageNum=<%=pageNum%>&replypageNum=<%=i%>">[<%=i%>]</a>
				<%
					}
					//다음
					if(endPage< pageCount){
						%><a href="./BoardContent.bo?num=<%=num %>&pageNum=<%=pageNum%>&replypageNum=<%=startPage+pageSize %>">[다음]</a>						
						
						
				<%
							}		
						}
				
				%>
				
				

			</td>
		</tr>

		
	</table>

<!-- 리플 페이징 -->
		<% 		
		
		}
		if(replycount==0){
// 			댓글 누르면 뜨는 대댓글창
			%>
					<form action="./ReplyWriteAction.re" method="post" name="fr">
					<input type="hidden" value="<%=bb.getSubject()%>" name="nick">							
					<input type="hidden" value="<%=rb.getNick() %>" name="nick"> 
					<input type="hidden" value="<%=num%>" name="num"> 
					<input type="hidden" value="<%=rb.getRe_ref() %>" name="re_ref">								
					<input type="hidden" value="<%=rb.getRe_lev() %>" name="re_lev">
					<input type="hidden" value="<%=rb.getRe_seq() %>" name="re_seq">
					<input type="hidden" value="<%=rb.getRe_num() %>" name="re_num">										
					<input type="hidden" value="<%=pageNum%>" name="pageNum"> 
					
					
					<textarea rows="2" cols="80" name="content"></textarea>
					<input type="submit" value="입력">
				</form>
				<%}%>

<!-- ====================================리플끗~========================================= -->
</div>


</section>

</body>
</html>
		
<!-- Footer -->
<jsp:include page="../inc/footer.jsp" />


