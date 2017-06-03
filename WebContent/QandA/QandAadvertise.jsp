<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- Header -->
<jsp:include page="../inc/header.jsp" />
<link rel="stylesheet" href="./assets/css/QandA/QandAadvertise.css"/>	

	
	<div id="combine">
		<h2>광고 및 제휴</h2>
	<h1>저희는 다양한 제휴·협력을 환영합니다.</h1>
		<form action="#" method="post" name="fr">
<br> 	
		회사명(성명):<input type="text" name="nick" value=""><br>		
		(이메일 주소):<input type="text" name="subject"><br>
		문의내용:<textarea rows="5" cols="20" name="content"></textarea><br>
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