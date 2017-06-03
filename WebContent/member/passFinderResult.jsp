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

function window_close() {
	
	window.close();
	
}

</script>

<!-- 추가한 스타일 -->
<style type="text/css">

	#container {
		border: 3px solid #f0f0f0;
		width: 360px;
		height: 200px;
		padding: 30px;
		margin: 30px auto;
		text-align: center;
	}	
	
	#container h3 {
		font-size: 1.1em;
		text-transform: none;
	}
	
	#container span {
		color: #BA942B;
	}

</style>

</head>
<body>
	
	<div id="container">
		<h3><span><%=request.getParameter("id") %></span> 주소로</h3>
		<h3>임시 비밀번호가 전송되었습니다.</h3>
		<input type="button" value="확인" onclick="window_close()">
	</div>
	
</body>
</html>