<%@page import="net.plan.db.PlanCountryBean"%>
<%@page import="net.plan.db.PlanCityBean"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>

<!-- 스타일 불러오기 -->
<link rel="stylesheet" href="assets/css/main.css" />

<body style="max-width: 1080px; margin: auto;">
	<h1>여행장소 db 입력용</h1>
<%String city_code = null; %>


	<form action="./TravelAdminAction.td?city_code=<%=city_code %>" method="post" name="fr"
		enctype="multipart/form-data">
		<select id="type" name="type">
			<option value="p">명소(place)</option>
			<option value="r">맛집(restaurant)</option>
			<option value="h">호텔(hotel)</option>
		</select> &nbsp;&nbsp;&nbsp;&nbsp; <select id="country_code"
			name="country_code" onChange="country(this.value)">

			<%
				List countryList = (List) request.getAttribute("countryList");

				for (int i = 0; i < countryList.size(); i++) {
					PlanCountryBean pcb = (PlanCountryBean) countryList.get(i);
			%>
			<option id="country" value="<%=pcb.getCountry_code()%>"><%=pcb.getName()%></option>

			<%
				}
			%>
		</select> &nbsp;&nbsp;&nbsp;&nbsp; <select id="city_code" name="city_code"></select>

		<script language="javascript">
			//상위 셀렉트로 하위 셀렉트 제어하기
			function country(country) {
				
				document.getElementById('city_code').length=0;
				
		<%List cityList = (List) request.getAttribute("cityList");
			for (int i = 0; i < cityList.size(); i++) {

				PlanCityBean cb = (PlanCityBean) cityList.get(i);%>
				
			if (country == "<%=cb.getCountry_code()%>") {
				
					var option = document.createElement('option');
					
					option.text = "<%=cb.getName()%>";
					option.value = "<%=cb.getCity_code()%>";

					document.getElementById('city_code').appendChild(option);
				}
		<%}%>
			}
			
		</script>
		<br> <br> 여행지 이미지<br> <input type="file" name="file">

		<br> <br>



		<!-- 타입 - p(명소),r(맛집),h(호텔) 3개중에(p,r,h 중) 하나만 넣으세요 :<input type="text" name="type"><br>
국가코드(kr, jp, ph) 3개중에 하나만 넣으세요:<input type="text" name="country_code"><br>
도시코드(seoul, busan, tokyo, osaca, manila, cebu) 6개중에 하나만 넣으세요 :<input type="text" name="city_code"><br> -->
		명칭(ex 조선비치호텔) : <input type="text" name="name"><br> 주소 :
		<input type="text" name="address"><br> 장소소개 : <input
			type="text" name="info"><br> lat : <input type="text"
			name="latitude"><br> lng : <input type="text"
			name="longitude"> <br> lat,lng 값은 <a
			href="https://www.google.co.kr/maps" target="blank">구글맵</a>에서 특정 장소
		검색후 <br> marker 가 뜨면 마우스 우클릭하여 "이곳이 궁금한가요?" 클릭하면 lat, lng 정보 확인가능<br>
		화면 좌측에 해당장소의 주소 복사해서 아래 주소칸에 넣으세요<br> <input type="submit"
			value="정보저장">

	</form>
</body>

<div class="clear"></div>

</html>