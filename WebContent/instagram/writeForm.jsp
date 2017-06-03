<%@page import="net.board.db.boardBean"%>
<%@page import="net.board.db.BoardDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<meta name="viewport" content="width=device-width, initial-scale=1" />
<link rel="stylesheet" href="assets/css/main.css" />

<script type="text/javascript">

function func1(){
if(document.fr.image1.value==""){
	alert("당신의 인생샷을 첨부해주세요");
	document.fr.image1.focus();
	return false;}
	
document.fr.submit();
}



</script>
<!-- Header -->
<jsp:include page="../inc/header.jsp" />
<link rel="stylesheet" href="./assets/css/instagram/writeForm.css"/>	


<section class="wrapper">
	<%
// 	인생샷그램 글쓰는곳
	String nick = (String)session.getAttribute("nick");	// 닉네임
	%>
	
		<form id="form" action="./BoardWriteAction.bo" method="post" name="fr" onsubmit="return func1()" enctype="multipart/form-data">
<!-- 		닉네임 세션값 생성해서 readonly로 설정하기 -->
<br> 

		글쓴이<input type="text" name="nick" value=<%=nick %> readonly><br>		
		제목<input type="text" name="subject" required><br>
		내용<textarea rows="5" cols="10" name="content" required></textarea><br>
		<input type="file" name="image1"><br><br>	
		<input id="submit" type="submit" value="글쓰기"><br> 
	</form>
</section>


<img id="girl" src="./images/instagram/girl.png">
<img id="boy" src="./images/instagram/boy.png">

<!-- Footer -->
<jsp:include page="../inc/footer.jsp" />

