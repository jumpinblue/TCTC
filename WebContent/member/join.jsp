<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%-- <!-- Header -->
<jsp:include page="../inc/header.jsp" /> --%>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Before you go</title>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1" />
		
		<!-- Scripts -->
		<script src="./assets/js/jquery.min.js"></script>
		<script src="./assets/js/skel.min.js"></script>
		<script src="./assets/js/util.js"></script>
		<script src="./assets/js/main.js"></script>
		
			<!-- RSA -->
			<script type="text/javascript" src="./assets/js/rsa/jsbn.js"></script>
        	<script type="text/javascript" src="./assets/js/rsa/rsa.js"></script>
       		<script type="text/javascript" src="./assets/js/rsa/prng4.js"></script>
       		<script type="text/javascript" src="./assets/js/rsa/rng.js"></script>
       		
       	<!-- 스타일 불러오기 -->
		<link rel="stylesheet" href="./assets/css/main.css?ver=2"/>
		<link rel="stylesheet" href="./assets/css/animate/animate.min.css"/>	<!-- 애니메이트 css -->

		<!-- 추가한 스타일 -->
			<!-- member -->
			<link rel="stylesheet" href="./assets/css/member/join.css?ver=10"/>

		<!-- 추가한 스크립트 -->
			<!-- plan -->
			<script type="text/javascript" src="./assets/js/plan/planMain.js"></script>
			<script type="text/javascript" src="./assets/js/member/join.js?ver=10"></script>	<!-- 회원가입 제약조건 및 암호화 -->

</head>
	
<body>

<!-- Header -->
<header id="header">
	<h1>
		<a href="./Main.me"><strong>Before</strong> you go...</a>
	</h1>
	
	<nav id="nav" style="font-family: '나눔고딕' , '맑은고딕', sans-serif; " >
		<ul>
			<li><a href="#">함께해요</a></li>
			<li><a href="./PlanMain.pl">여행일정플래너</a></li>
			<li><a href="#">여행일정Q&A</a></li>
			<li><a href="./BoardList.bo">인생샷그램</a></li>
		</ul>
	</nav> 
</header>

<a href="#menu" class="navPanelToggle"><span class="fa fa-bars"></span></a>

		<!-- Main -->
			<section id="main" class="wrapper joinSection">
				<div class="container">
				
					<!-- 회원가입 -->
					<div class="join_div">
						<h1>회원가입</h1>
							<h4 style="color: #ccc;">★ 모든 입력란에 입력해주세요.</h4>
						<form action="./MemberJoinAction.me" method="post" name="fr" onsubmit="return validateEncryptedForm()" >
							
							<label for="id">아이디</label> 
								<input type="email" name="id" id="id" placeholder="이메일을 입력해주세요." onchange="re_requestEmailCheck()">
							<input type="button" value="인증번호 전송" onclick="emailCheckNumber()" class="button alt small email_btn" >	<!-- 이메일 인증하기 -->
							<input type="text" id="randomNum" value="" >	<!-- 입력해야할 인증번호 -->
							<input type="text" name="email_check" class="email_check_input" placeholder="인증번호 입력" >	
								
							<div class="clear"></div>
								
							<label for="pass">비밀번호</label> 
								<input type="password" name="pass" id="pass" maxlength="16" placeholder="6~16자 영문 대 소문자로 시작하고, 숫자, 특수문자를 사용" >
							<label for="">비밀번호 확인</label> 
								<input type="password" name="pass2" id="pass2" maxlength="16">
							<label for="name">이름</label> 
								<input type="text" name="name" id="name" maxlength="30">
							<label for="nick">닉네임</label> 
								<input type="text" name="nick" id="nick" maxlength="9" placeholder="2~9자 영문 대 소문자, 한글로 시작하고 숫자 사용"  onchange="check_change()">
								<input type="button" name="nick_check" value="닉네임 중복확인" onclick="nickOverlapCheck()" class="button alt small" >
							
							<label for="gender">성별</label> 
							<div class="radio_box">
								<input type="radio" id="priority-normal man" name="pregender" value="남"  checked="checked">
									<label for="priority-normal man">남</label>
								<input type="radio" id="priority-normal woman" name="pregender" value="여" >
									<label for="priority-normal woman">여</label><br>
								<input type="hidden" name="gender" id="gender" value="" >	<!-- 암호화 후 넘길때 여기로 덮어쓰고 넘김 -->
							</div>
								
							<div class="clear"></div>
							
							<label for="tel" >연락처</label> 
								<input type="text" name="tel" id="tel" maxlength="20" placeholder=" ' - '문자 생략, 예)01000000000" >
							
							<!-- 공개키 -->
							<input type="hidden" id="join_publicKeyModulus" value="<%=request.getAttribute("publicKeyModulus") %>">
							<input type="hidden" id="join_publicKeyExponent" value="<%=request.getAttribute("publicKeyExponent") %>">
							
							<!-- 버튼 -->
							<div class="join_btn">	
								<input type="submit" class="button special" value="가입" >
								<input type="reset" class="button" value="다시쓰기" >
							</div>
							
						</form> 
					
					</div>	<!-- join_div -->
					
				</div>	<!-- container -->
		</section>
		
<!-- Footer -->
<jsp:include page="../inc/footer.jsp" />
		
		