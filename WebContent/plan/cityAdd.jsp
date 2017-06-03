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
			
			div.cityAdd {
				width: 90%;
				margin: -60px auto 30px auto;
				
				text-align: center;
			}
			
			div.cityAdd .btn_div {
				margin-top: 20px;
			}
			
			div.cityAdd input[type="file"] {
				display: block;
				margin: 0 auto 10px auto;
				font-size: 1.1em;
			}
			div.cityAdd .cityt {
				font-weight: bold;
			}
			
		</style>
			
	</head>

<body>

<div class="clear"></div>
<%
	request.setCharacterEncoding("utf-8");
	
	List countryList =(List)request.getAttribute("countryList");

%>

<section>

<div class="cityAdd">
	<form action="./CityAdd.pl" name="city_fr" method="post" enctype="multipart/form-data">
		<span class="cityt">국가코드</span>
		<select name="country_code">
			<%
				for(int i=0;i<countryList.size();i++){
					PlanCountryBean pcb = (PlanCountryBean)countryList.get(i);
					%>
					<option value="<%=pcb.getCountry_code() %>"><%=pcb.getName() %></option>
					<%
				}
			%>
		</select>  
	
		<!-- 도시 코드  ==> 도시영어이름과 동일 -->
		
		<span class="cityt">도시 이름</span>
		<input type="text" name="name">
		
		<span class="cityt">도시 영어이름</span>
		<input type="text" name="en_name">
		
		<span class="cityt">도시 이미지</span>
		<input type="file" name="file">
		
		<span class="cityt">정보</span>
		<textarea rows="10" cols="20" name="info" maxlength="160"></textarea>
	
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