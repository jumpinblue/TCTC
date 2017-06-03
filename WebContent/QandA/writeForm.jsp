<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- Header -->
<jsp:include page="../inc/header.jsp" />
<link rel="stylesheet" href="./assets/css/QandA/writeForm.css"/>	


	<%
// 	인생샷그램 글쓰는곳
	String nick = (String)session.getAttribute("nick");	// 닉네임
	%>	
	<div id="combine">
	
		<form action="QandAWriteAction.qna" method="post" name="fr" onsubmit="return func1()" enctype="multipart/form-data">
<!-- 		닉네임 세션값 생성해서 readonly로 설정하기 -->
<br> 
		글쓴이:<input type="text" name="nick" value=<%=nick %> readonly><br>		
		제목:<input type="text" name="subject"><br>
		내용:<textarea rows="5" cols="20" name="content"></textarea><br>
		<input type="file" name="image1"><br><br>	
		<input id="submit" type="submit" value="글쓰기"><br> 
	</form>

<table id="banner">
<tr><td><a href="./QandACompanyIntro.qna">회사소개</a></td></tr>
<tr><td id="darker"><a href="./QandAHowToUse.qna">이용방법</a></td></tr>
<tr><td><a href="./QandAadvertise.qna">광고 및 제휴</a></td></tr>
<tr><td id="darker"><a href="./QandAcondition.qna">이용약관</a></td></tr>
<tr><td><a href="./QandAprivacy.qna">개인정보 취급 방침</a></td></tr>
<tr><td id="darker"><a href="./QandAWrite.qna">문의하기</a></td></tr>
<tr><td><a href="./Memberintro.qna">운영진소개</a></td></tr>
<tr><td id="darkerlast"><a href="./QandAList.qna">목록으로</a></td></tr>

</table>
	</div>



<!-- Footer -->
<jsp:include page="../inc/footer.jsp" />