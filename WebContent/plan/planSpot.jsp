<%@page import="net.plan.db.PlanTravelBean"%>
<%@page import="net.plan.db.PlanCountryBean"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<link rel="stylesheet" href="./assets/css/plan/planSpot.css"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- 헤더 -->
<jsp:include page="../inc/header.jsp" />

<script type="text/javascript">
	$(document).ready(function(){
		$('input').click(function(){
			$('input').css('background-color','#323037');
			$(this).css('background-color','#f32853');
			
			
			
			var month=$(this).val();
			$('.month_img').empty();
			$('.month_img').append(function(){
				var con='<h1>'+month+'</h1>';
				return con;
			});	
		});
	});

</script>

<!-- include jQuery library -->
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.5/jquery.min.js"></script>
<!-- include Cycle plugin -->
<script type="text/javascript" src="http://malsup.github.com/jquery.cycle.all.js"></script>

<script type="text/javascript">
$(document).ready(function() {
    $('.div_1').cycle({
		fx: 'fade' // choose your transition type, ex: fade, scrollUp, shuffle, etc...
	});
});
</script>

<%
	PlanTravelBean ptb = (PlanTravelBean)request.getAttribute("ptb");
	
	//도시이름 가져오기

%>
<div class="clear"></div>
<!-- 본문 -->
<section class = "planSpot">
<h2><%=ptb.getName() %><span><%=ptb.getAddress() %></span></h2>


<!-- 찜하기 버튼 추가 -->

<!-- 장소 이미지(이미지 슬라이드) -->

	<!-- <!-- 다음검색 api 
	<script src="./assets/js/plan/planSpotSearch.js"></script>
	
	검색어 설정(검색어 바꾸기 => "도시이름+여행+장소" )
	<div id="daumForm">
    	<input id="daumSearch" type="hidden" value="부산 여행 자갈치" onkeydown="javascript:if(event.keyCode == 13) daumSearch.search();"/>
	</div>

	이미지 출력
	<div id="daumView">
        <div id="daumImage"></div>
	</div>

	<div id="daumScript">
 		<div id="daumImageScript"><img src="pic02.jpg" width="200" height="200"/></div>
	</div> -->

<!-- 추가정보(검색설명) -->
<div class="tr_info" style="">추가 정보들</div>
<div class="clear"></div>

<!-- 장소 설명(db에서 받아온 설명) -->
<h1><%=ptb.getInfo() %></h1>

<!-- 월별 옷차림(검색) -->

<h3>월별 옷차림</h3>
<div>
<input type="button" value="1월" class="month">
<input type="button" value="2월" class="month">
<input type="button" value="3월" class="month">
<input type="button" value="4월" class="month">
<input type="button" value="5월" class="month">
<input type="button" value="6월" class="month">
<input type="button" value="7월" class="month">
<input type="button" value="8월" class="month">
<input type="button" value="9월" class="month">
<input type="button" value="10월" class="month">
<input type="button" value="11월" class="month">
<input type="button" value="12월" class="month">
</div>
<div class="clear"></div>
<div class="month_img">
</div>
<div class="clear"></div>

<!-- 선물리스트(1위 2위 3위)(db에서 받아오기) -->

<h3>선물리스트</h3>
<table class="gift">
<tr>
<td>1위</td>
<td>2위</td>
<td>3위</td>
</tr>
</table>

<!-- 장소에 대한 후기 작성(시간나면) -->
<div class="blog_Spot">블로그 정보</div>
<div class="Spot_epilogue">여행 후기</div>






</section>
<div class="clear"></div>

<%-- <!-- footer -->
<jsp:include page="../inc/footer.jsp" /> --%>