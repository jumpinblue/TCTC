<%@page import="net.plan.db.PlanCountryBean"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
		
		<!-- 추가한 스타일 -->		
		<style type="text/css">
			
			section {
				text-align: center;
			}
			
			div.countryAdd {
				width: 90%;
				margin: -60px auto 30px auto;
				
				text-align: center;
			}
			
			div.countryAdd .btn_div {
				margin-top: 20px;
			}
			
			div.countryAdd .countryt {
				font-weight: bold;
			}
			
		</style>
			
	</head>

<body>

<div class="clear"></div>
<section>
<div class="countryAdd">
	<form action="./CounAdd.pl" name="coun_fr" method="post">
		<span class="countryt">대륙</span>
		<select name="continent">
			<option value="asia">아시아</option>
			<option value="europe">유럽</option>
			<option value="north">북미</option>
			<option value="oceania">남태평양</option>
			<option value="south">남미</option>
		</select>
		
		<span class="countryt">국가 코드</span>
		<input type="text" name="country_code">
		
		<span class="countryt">국가 이름</span>
		<input type="text" name="name">
		
		<span class="countryt">국가 영어이름</span>
		<input type="text" name="en_name">
		
		<span class="countryt">정보</span>
		<textarea rows="10" cols="20" name="info"></textarea>
		
		<div class="btn_div">
		<input type="submit"  value="추가" >
		<input type="reset"  value="다시쓰기" >
		</div>
		
	</form>
</div>

<!-- 뒤로가기 버튼 -->
<input type="button" class="button special" value="뒤로가기" onclick="history.back()">

</section>

<div class="clear"></div>

<!-- footer -->
</body>
</html>