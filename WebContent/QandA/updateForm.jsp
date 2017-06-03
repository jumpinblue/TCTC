<%@page import="net.QandA.db.QandABean"%>
<%@page import="net.QandA.db.QandADAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<!-- Header -->
<jsp:include page="../inc/header.jsp" />

<!-- 추가한 스크립트 -->
<script type="text/javascript" src="./assets/js/member/join.js"></script>	<!-- 회원가입 제약조건 및 암호화 -->
<link rel="stylesheet" href="./assets/css/QandA/updateForm.css"/>	

<section class="wrapper">
<%
//int num,String pageNum 파라미터 가져오기
String pageNum = request.getParameter("pageNum");
int num=Integer.parseInt(request.getParameter("num"));
QandABean qb = (QandABean) request.getAttribute("qb");
%>

	<h1>QandA글수정</h1>
		<form id="form" action="./QandAUpdateAction.qna?num=<%=num %>&pageNum=<%=pageNum%>" method="post" name="fr" enctype="multipart/form-data">
<!-- 		hidden으로 숨겨서 num값 넘겨주기 -->
		<input type="hidden" name="num" value="<%=num%>">
		
		글쓴이:<input type="text" name="nick" value="<%=qb.getNick()%>"><br> 
		제목:<input type="text" name="subject" value="<%=qb.getSubject()%>"><br>
<!-- 		textarea는 value값이 없움 -->
		내용:<textarea rows="10" cols="20" name="content"><%=qb.getContent()%></textarea><br>
		
		<img src="./upload/<%=qb.getImage1()%>" width=200 height=200><br>
		
<!-- 		파일 첨부할때 type2개 만들어 놓기(하나는  hidden) -->
		파일첨부:<input type="file" name="image1">
		<input type="hidden" name="image2" value="<%=qb.getImage1()%>">
		
		
		<br>
				<input type="submit" value="글수정"><br> 
		
	</form>

</section>
</body>
<jsp:include page="../inc/footer.jsp" />
