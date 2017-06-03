<%@page import="java.util.Calendar"%>
<%@page import="net.plan.db.PlanCountryBean"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!-- 헤더 -->
<jsp:include page="../inc/header.jsp" />

<!-- 대륙지도js -->
<link rel="stylesheet" media="all"
	href="./assets/css/plan/jquery-jvectormap.css" />

<script src="./assets/js/plan/jquery-1.8.2.js"></script>
<script src="./assets/js/plan/jquery-jvectormap.js"></script>
<script src="./assets/js/plan/jquery-mousewheel.js"></script>

<script src="./assets/js/plan/src/jvectormap.js"></script>

<script src="./assets/js/plan/src/abstract-element.js"></script>
<script src="./assets/js/plan/src/abstract-canvas-element.js"></script>
<script src="./assets/js/plan/src/abstract-shape-element.js"></script>

<script src="./assets/js/plan/src/svg-element.js"></script>
<script src="./assets/js/plan/src/svg-group-element.js"></script>
<script src="./assets/js/plan/src/svg-canvas-element.js"></script>
<script src="./assets/js/plan/src/svg-shape-element.js"></script>
<script src="./assets/js/plan/src/svg-path-element.js"></script>
<script src="./assets/js/plan/src/svg-circle-element.js"></script>
<script src="./assets/js/plan/src/svg-image-element.js"></script>
<script src="./assets/js/plan/src/svg-text-element.js"></script>

<script src="./assets/js/plan/src/vml-element.js"></script>
<script src="./assets/js/plan/src/vml-group-element.js"></script>
<script src="./assets/js/plan/src/vml-canvas-element.js"></script>
<script src="./assets/js/plan/src/vml-shape-element.js"></script>
<script src="./assets/js/plan/src/vml-path-element.js"></script>
<script src="./assets/js/plan/src/vml-circle-element.js"></script>
<script src="./assets/js/plan/src/vml-image-element.js"></script>

<script src="./assets/js/plan/src/map-object.js"></script>
<script src="./assets/js/plan/src/region.js"></script>
<script src="./assets/js/plan/src/marker.js"></script>

<script src="./assets/js/plan/src/vector-canvas.js"></script>
<script src="./assets/js/plan/src/simple-scale.js"></script>
<script src="./assets/js/plan/src/ordinal-scale.js"></script>
<script src="./assets/js/plan/src/numeric-scale.js"></script>
<script src="./assets/js/plan/src/color-scale.js"></script>
<script src="./assets/js/plan/src/legend.js"></script>
<script src="./assets/js/plan/src/data-series.js"></script>
<script src="./assets/js/plan/src/proj.js"></script>
<script src="./assets/js/plan/src/map.js"></script>

<script src="./assets/js/plan/world/jquery-jvectormap-asia-mill.js"></script>
<script src="./assets/js/plan/world/jquery-jvectormap-europe-mill.js"></script>
<script
	src="./assets/js/plan/world/jquery-jvectormap-north_america-mill.js"></script>
<script src="./assets/js/plan/world/jquery-jvectormap-oceania-mill.js"></script>
<script
	src="./assets/js/plan/world/jquery-jvectormap-south_america-mill.js"></script>

<script src="./assets/js/plan/planMain_Map.js"></script>

<!-- 메인 -->


<!-- 아시아 -->

<div id="map1" style="width: 60em; height: 30em;"></div>

<!-- 유럽 -->

<div id="map2" style="width: 60em; height: 30em;"></div>

<!-- 남태평양 -->

<div id="map3" style="width: 60em; height: 30em;"></div>

<!-- 남미 -->

<div id="map4" style="width: 60em; height: 30em;"></div>

<!-- 북미 -->

<div id="map5" style="width: 60em; height: 30em;"></div>

<!-- 대륙별 국가리스트 출력 -->
<!-- <script>
	window.onload=CountryList();
</script> -->

<!-- 추천 도시 -->
<script type="text/javascript">
	$(window).load(function(){
		$.ajax({
			type:'post',
			url:'./plan/planMainCityList.jsp',
			data:{continent:'All'},
			success:function(data){
				$('div.bestTripimg').append(data);
			},
			error:function(xhr, status, error){
				alert(error);
			}
		});
	});
	
	function cityListchange(continent) {
		$.ajax({
			type:'post',
			url:'./plan/planMainCityList.jsp',
			data:{continent : continent},
			success:function(data){
				$('div.bestTripimg').empty();
				$('div.bestTripimg').append(data);
			},
			error:function(xhr, status, error){
				alert(error);
			}
		});
	
	}
</script>

<%
	/* 대륙 리스트 */
	String[] cont = { "아시아", "유럽", "남태평양", "중남미", "북미" };
	List countryList = (List) request.getAttribute("countL");
	
	//바뀌는 배경을 초 단위로 하기 위해서 현재 시간 불러오기.
			Calendar cal = Calendar.getInstance();
			int second = cal.get(Calendar.SECOND)%4;//배경 갯수에 따라서 나누는 값 바꾸기
%>

<div class="clear"></div>

<section class="planMain">
	<div>
	
	<!-- 현재 초 값을 받아와서, 배경이 새로고침 할때마다 바뀌도록. -->
<%if(second==3) {%>
<section id="banner" class="b_back<%=second%>">
<%} else if(second==2) {%>
<section id="banner" class="b_back<%=second%>">
<%} else if(second==1) {%>
<section id="banner" class="b_back<%=second%>">
<%} else { %>
<section id="banner" class="b_back">
<%} %>


	<div style="max-width: 1080px; margin: auto; padding: 1.5em 0;">
		<!-- 검색폼 -->
		<div class="Main_Search">
			<form name="fr" action="./PlanSearch.pl" class="main_plan_search"
				method="post" onsubmit="return checkSearch();">
				<h2>어디로 여행을 가시나요?</h2>
				<div class="clear"></div>
				<div style="max-width: 950px; margin: auto;">
					<select name="check" class="check_search">
						<option value="0">선택해주십시오</option>
						<option value="1">국가명</option>
						<option value="2">도시명</option>
					</select> <input type="text" name="search" value="" class="search_text"
						placeholder="임시 버튼은 아래에 있습니당"> <input type="submit"
						value="검색" class="main_serch_button">
				</div>
			</form>
		</div>
	</div>





</section>

		<hr>
		<div class="clear"></div>

		<!-- 인기여행지 리스트(대륙별 도시추천) -->
		<div class="bestTrip">
			<h2>인기여행지</h2>
			<div class="bestTripMenu">
				<ul class="bestTrip_cont">
					<li><a href="javascript:cityListchange('All');">추천</a></li>
					<li><a href="javascript:cityListchange('asia');">아시아</a></li>
					<li><a href="javascript:cityListchange('europe');">유럽</a></li>
					<li><a href="javascript:cityListchange('oceania');">남태평양</a></li>
					<li><a href="javascript:cityListchange('south');">중남미</a></li>
					<li><a href="javascript:cityListchange('north');">북미</a></li>
				</ul>
			</div>

			<div class="clear"></div>

			<!-- 인기여행지 이미지리스트(대륙별 도시추천) -->
			<div class="bestTripimg">
				
			</div>
		</div>

		<div class="clear"></div>

		<!-- 국가 리스트  -->
		<div class="countryList">
			<h2>국가 리스트</h2>
			<%
				String[] asia = { "asia", "europe", "oceania", "south", "north" };
				for (int j = 0; j < 5; j++) {
			%>

			<h3><%=cont[j] + "  "%><a
					onclick="popupToggle_<%=asia[j]%>()">>>지도로보기</a>
			</h3>
			<div>
				<%
					for (int i = 0; i < countryList.size(); i++) {
							PlanCountryBean cb = (PlanCountryBean) countryList.get(i);
							if (cb.getContinent().equals(asia[j])) {
				%>


				<div style="border: 1px soild red;">
					<a href=./PlanSearch.pl?check=1&search=<%=cb.getName()%>><%=cb.getName()%></a><span><%=cb.getEn_name()%></span>
				</div>
				<%
				}
					}
			%>
			</div>
			<div class="clear"></div>
			




			<%
				}
			%>


		</div>
		<div id="countryMap_back" onclick="popupToggle_map()"></div>
	</div>
</section>

<!-- footer -->
<jsp:include page="../inc/footer.jsp" />