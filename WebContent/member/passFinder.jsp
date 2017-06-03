<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>비밀번호 찾기</title>

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
		function passFind() {
		
			var id = document.getElementById("id").value;
		
			if(!id) {
				alert("아이디(이메일)를 입력해주세요.");
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
	
	<div id="container">
		<h4>비밀번호 찾기</h4>
		<form action="./MemberPassFinderAction.me" name="pass_finder_form" method="post" onsubmit="return passFind();">
		
			<label for="id">아이디</label>
				<input type="email" name="id" id="id" placeholder="아아디(이메일)을 입력">
		
			<input type="submit" value="비밀번호 찾기">
		</form>
	</div>
	
</body>
</html>