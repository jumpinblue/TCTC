<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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

		<!-- 스타일 불러오기 -->
		<link rel="stylesheet" href="./assets/css/main.css?ver=3"/>
		<link rel="stylesheet" href="./assets/css/animate/animate.min.css"/>	<!-- 애니메이트 css -->
			
	</head>

<body>
<div class="clear"></div>
<!-- 도시 기념품 추가하기 -->
<section>
	<div class="souvenirAdd">
		<form action="./SouvenirAdd.pl" name="sou_fr" method="post" enctype="multipart/form-data">
			
			<label>순위</label>
			<input type="text" name="ranking">
			
			<label>이름</label>
			<input type="text" name="name">
			
			<label>이미지</label>
			<input type="file" name="file">
			
			<label>설명</label>
			<textarea rows="10" cols="20" name="info"></textarea>
			
			<div class="clear"></div>	
		</form>
	
	
	</div>

</section>
<!-- footer -->
</body>
</html>