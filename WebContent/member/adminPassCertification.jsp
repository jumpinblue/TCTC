<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>아이디 찾기</title>

<!-- Scripts -->
		<script src="./assets/js/jquery.min.js"></script>
		<script src="./assets/js/jquery-3.2.0.js"></script>
		<script src="./assets/js/skel.min.js"></script>
		<script src="./assets/js/util.js"></script>
		<script src="./assets/js/main.js"></script>
        	
        	<!-- RSA -->
			<script type="text/javascript" src="./assets/js/rsa/jsbn.js"></script>
        	<script type="text/javascript" src="./assets/js/rsa/rsa.js"></script>
       		<script type="text/javascript" src="./assets/js/rsa/prng4.js"></script>
       		<script type="text/javascript" src="./assets/js/rsa/rng.js"></script>
        	
		<!-- 스타일 불러오기 -->
		<link rel="stylesheet" href="./assets/css/main.css" />

	<!-- 추가한 스크립트 -->
	<script type="text/javascript">
	function adminPassCertification() {

	    var pass = $('#admin_pass').val();

	    if (!pass) {
	        alert("비밀번호를 입력해주세요.");
	        return false;
	    }

	    try {
	    	// 공개키 가져오기
	        var rsaPublicKeyModulus = document.getElementById("adminPass_publicKeyModulus").value;
	        var rsaPublicKeyExponent = document.getElementById("adminPass_publicKeyExponent").value;

	        var rsa = new RSAKey();
		    rsa.setPublic(rsaPublicKeyModulus, rsaPublicKeyExponent);

		    // 비밀번호를 RSA로 암호화한다.
		    var securedPassword = rsa.encrypt(pass);

		    // 암호화 한 비밀번호를 다시 폼에 입력한다.
		    $('#admin_pass').val(securedPassword);
	        
	        return true;
	        
	    } catch(err) {
	        alert(err);
	    }
	    return false;
	}
	
	</script>

	<!-- 추가한 스타일 -->
	<style type="text/css">
		div#container {
			width: 550px;
			height: 300px;
			margin: 0 auto;
			padding: 10px 50px;
		}
		
		div#container label {
			margin-bottom: -7px;
		}
		
		div#container input[type=submit] {
			display: block;
			margin: 20px auto;
		}
	
	</style>

</head>
<body>
	<!-- 관리자가 회원 삭제하기전 비밀번호 인증 받는 페이지 -->
	<!-- 팝업 브라우저 창 띄워서 할거라 include 안함 -->
	
	<%
		// 파라미터 값 가져오기
		String id = request.getParameter("id");				// 삭제할 아이디
		String pageNum = request.getParameter("pageNum");	// 페이지 번호
	%>
	
	<div id="container">
		<h4>비밀번호 재입력</h4>
		<form action="./MemberDeleteManager.me" name="id_finder_form" method="post" onsubmit="return adminPassCertification();">
		
			<label for="pass">비밀번호</label>
				<input type="password" name="pass" id="admin_pass">
				
				<input type="hidden" value="<%=id %>" name="id" id="id"><!-- 비밀번호 확인을 위한 아이디 -->
				<input type="hidden" value="<%=pageNum %>" name="pageNum" id="pageNum"><!-- 비밀번호 확인을 위한 아이디 -->
				
			<!-- 공개키 -->
			<input type="hidden" id="adminPass_publicKeyModulus" value="<%=request.getAttribute("publicKeyModulus") %>">
			<input type="hidden" id="adminPass_publicKeyExponent" value="<%=request.getAttribute("publicKeyExponent") %>">
		
			<input type="submit" value="확인">
		</form>
	</div>
	
	
</body>
</html>