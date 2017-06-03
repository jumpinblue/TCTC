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
        	
		<!-- 스타일 불러오기 -->
		<link rel="stylesheet" href="./assets/css/main.css" />

	<!-- 추가한 스크립트 -->
	<script type="text/javascript">
		function idFind() {
			
 			// 자바스크립트가 먼저 실행되고 그 다음 jquery가 실행됨(이제알음...)
		
			var name = document.getElementById("name").value;
			var tel = document.getElementById("tel").value;
		
			if(!name || !tel) {
				alert("이름 또는 연락처를 입력해주세요.");
				return false;
			}else {
				return true;
			}

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
			margin: 20px 0 0 140px;
		}
	
	</style>

</head>
<body>
	<!-- 아이디 찾는 페이지 -->
	<!-- 팝업 브라우저 창 띄워서 할거라 include 안함 -->
	
	<div id="container">
		<h4>아이디 찾기</h4>
		<form action="./MemberIdFinderAction.me" name="id_finder_form" method="post" onsubmit="return idFind();">
		
			<label for="name">이름</label>
				<input type="text" name="name" id="name" placeholder="회원가입시 작성한 이름">
		
			<label for="tel">연락처</label>
				<input type="text" name="tel" id="tel" placeholder="회원가입시 작성한 연락처( '-' 문자생략)">
		
			<input type="submit" value="아이디 찾기">
		</form>
	</div>
	
	
</body>
</html>