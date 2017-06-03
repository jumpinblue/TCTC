
<%@page import="net.board.db.boardBean"%>
<%@page import="net.board.db.BoardDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!-- Header -->
<jsp:include page="../inc/header.jsp" />

<!-- 추가한 스크립트 -->
<script type="text/javascript" src="./assets/js/member/join.js"></script>	<!-- 회원가입 제약조건 및 암호화 -->
<link rel="stylesheet" href="./assets/css/instagram/updateForm.css"/>	

<section class="wrapper">
<%
//int num,String pageNum 파라미터 가져오기
String pageNum = request.getParameter("pageNum");
int num=Integer.parseInt(request.getParameter("num"));
//BoardDAO bdao 객체 생성
BoardDAO bdao=new BoardDAO();
//BoardBean bb=메서드호출 getBoard(num)
boardBean bb=bdao.getBoard(num);
%>
<form action="./BoardUpdateAction.bo?num=<%=num %>&pageNum=<%=pageNum%>" method="post" name="fr" enctype="multipart/form-data">
<!-- 		hidden으로 숨겨서 num값 넘겨주기 -->
<div id="combine">
<div id="form">
		<input type="hidden" name="num" value="<%=num%>">
		<br>	
		글쓴이:<input type="text" name="nick" value="<%=bb.getNick()%>"><br> 
		제목:<input type="text" name="subject" value="<%=bb.getSubject()%>"><br>
<!-- 		textarea는 value값이 없움 -->
		내용:<textarea rows="5" cols="8" name="content"><%=bb.getContent()%></textarea><br>
		<input type="submit" value="글수정"><br> 
		
		</div>
	
		<div id="upload">
		<img src="./upload/<%=bb.getImage1()%>" width=200 height=200><br>
<!-- 		파일 첨부할때 type2개 만들어 놓기(하나는  hidden) -->
		<input type="file" name="image1">
		<input type="hidden" name="image2" value="<%=bb.getImage1()%>">
		</div>
		</div>
	</form>
	

</section>
</body>
<jsp:include page="../inc/footer.jsp" />
