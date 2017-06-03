<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">
.planSpot_tb{
	border:5px solid blue;
	float: left;
}
div.planSpot_tb img{
	width: 100%;
	height: 100%;
}

</style>
</head>
<body>


<%
	//한글처리
	request.setCharacterEncoding("utf-8");
	
	
	//파라미터 값 가져오기(월, 도시)
	String month = request.getParameter("month");
	
	//도시 파라미터 가져오기
	
	if(month==null){
		month="1월";
	}
%>

<!-- 다음 이미지 검색 -->
<script src="../assets/js/plan/planSpotSearch.js"></script>
	
	<div id="daumForm">
    	<input id="daumSearch" type="hidden" value="부산 여행 +<%=month %>+" onkeydown="javascript:if(event.keyCode == 13) daumSearch.search();"/>
	</div>

	<div id="daumView">
        <div id="daumImage"></div>
	</div>

	<div id="daumScript">
 		<div id="daumImageScript"></div>
	</div>


</body>
</html>