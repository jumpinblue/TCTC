<%@page import="net.plan.db.PlanTravelBean"%>
<%@page import="net.travel.admin.db.TravelBean"%>
<%@page import="net.plan.db.PlanCityBean"%>
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

		<!-- 스타일 -->
		<link rel="stylesheet" href="./assets/css/main.css?ver=3"/>
		<link rel="stylesheet" href="./assets/css/animate/animate.min.css"/>	<!-- 애니메이트 css -->
		
		<style type="text/css">
			div.clear {
				clear: both;
			}
		
			div.cityList {
				width: 90%;
				margin: -60px auto 30px auto;
				
				text-align: center;
			}
			
			div.cityList h3:hover {
				cursor: pointer;
			}
			
			/* 테이블 */
			div.cityList th {
				padding-top: 10px;
			}
			div.cityList th img.sort_img {
				width: 12px;
				height: 12px;
			}
			/* 테이블 크기 */
			div.cityList th:nth-of-type(1) {	/* 도시코드 */
				width: 10%;
				cursor: pointer;
			}
			div.cityList th:nth-of-type(2) {	/* 여행지 이름 */
				width: 15%;
				cursor: pointer;
			}
			div.cityList th:nth-of-type(3) {	/* 국가코드 */
				width: 10%;
				cursor: pointer;
			}
			div.cityList th:nth-of-type(4) {	/* 여행지 타입 */
				width: 10%;
				cursor: pointer;
			}
			div.cityList th:nth-of-type(5) {	/* 정보 */
				width: 35%;
			} 
			div.cityList th:nth-of-type(6) {	/* 수정 / 삭제 */
				width: 20%;
			}

			div.cityList .page a {
				border: 1px solid #ccc;
				padding: 4px 7px;

				font-size: 1.2em;
				font-weight: bold;
			}
			div.cityList .page a:hover {
				background-color: #f0f0f0;
			}
				
			div.cityList .addCityBtn {
				margin-bottom: 20px;
			}
			
			/* 검색폼 */
			div.cityList .search_div {
				position: relative;
				float: right;
				margin-bottom: 10px;

				width: 30em;
				height: 40px;
			}
			div.cityList .search_div .search_img {
				position: absolute;
				top: 2px;
				left: 2px;
				
				width: 1.5em;
				height: 1.5em;
			}
			div.cityList .search_div input[type="text"] {
				width: 25em;
				height: 2em;
				
				padding-left: 2.2em;
			}
			div.cityList .search_div input[type="submit"] {
				position: absolute;
				font-size: 0.5em;
				height: 3em;
				
				top: 0;
				left: 37em;
			}
		</style>
			
	</head>

<body>
<div class="clear"></div>

<section>
<%
	request.setCharacterEncoding("UTF-8");

	List travelList = (List)request.getAttribute("travelList"); 
	String pageNum=(String)request.getAttribute("pageNum");
	if(pageNum==null) {
		pageNum="1";
	}
	int currentPage = Integer.parseInt(pageNum);
	
	int pageCount=((Integer)request.getAttribute("pageCount")).intValue();
	int pageBlock=((Integer)request.getAttribute("pageBlock")).intValue();
	int startPage=((Integer)request.getAttribute("startPage")).intValue();
	int endPage=((Integer)request.getAttribute("endPage")).intValue();
	int count=((Integer)request.getAttribute("count")).intValue();
	
	String search = request.getParameter("search");
	if(search==null) {
		search = "";
	}
	
	String sort = request.getParameter("sort");
	if(sort==null) {
		sort = "0";
	}
	int isort = Integer.parseInt(sort);
%>

<script type="text/javascript">

// 삭제 버튼 눌렀을때 한번더 확인
function delConfirm(pageNum, city_code) {
	var result = confirm("정말 삭제하시겠습니까?");
	if(result==true) {
// 		location.href="./CityDelete.pl?pageNum="+pageNum+"&city_code="+city_code;				
		location.href="#";		
	}
}

// 여행지 정보 펼쳐보기 / 숨기기
function toggleInfo(i, t) {	// i는 td위치, t는 토글
	if(t==1) {	// 펼치기
		$('#hideInfo'+i).css('display', 'none');
		$('#showInfo'+i).css('display', 'inline');
	}else if(t==0) {	// 접기
		$('#hideInfo'+i).css('display', 'inline');
		$('#showInfo'+i).css('display', 'none');
	}
}

// 정렬값 가지고 도시 리스트 가져오기
function sortTravelList(i) {
	
	var sort = i;
	
	if(i==1) {	// 국가코드 정렬 1 2
		if(<%=isort%>==1) sort=2;	// 내림차순으로
		else sort=1;	// 오름차순으로
			
	}else if(i==3) {	// 국가이름 정렬 3 4
		if(<%=isort%>==3) sort=4;	// 내림차순으로
		else sort=3;	// 오름차순으로	
		
	}else if(i==5) {	// 대륙이름 정렬 5 6
		if(<%=isort%>==5) sort=6;	// 내림차순으로
		else sort=5;	// 오름차순으로
					
	}else if(i==7) {	// 영문이름 정렬 정렬 7 8
		if(<%=isort%>==7) sort=8;	// 내림차순으로
		else sort=7;	// 오름차순으로
	}
	
	location.href="./TravelAdminList.td?pageNum=<%=pageNum%>&search=<%=search%>&sort="+sort;
}

// 정렬 값에 따라 th의 화살표 이미지 변경
$(document).ready(function() {
	if(<%=isort%>==1) {
		$('.sort1_img').attr('src', './images/sort_top.png');
	}else if(<%=isort%>==2) {
		$('.sort1_img').attr('src', './images/sort_bottom.png');
	}else if(<%=isort%>==3) {
		$('.sort2_img').attr('src', './images/sort_top.png');
	}else if(<%=isort%>==4) {
		$('.sort2_img').attr('src', './images/sort_bottom.png');
	}else if(<%=isort%>==5) {
		$('.sort3_img').attr('src', './images/sort_top.png');
	}else if(<%=isort%>==6) {
		$('.sort3_img').attr('src', './images/sort_bottom.png');
	}else if(<%=isort%>==7) {
		$('.sort4_img').attr('src', './images/sort_top.png');
	}else if(<%=isort%>==8) {
		$('.sort4_img').attr('src', './images/sort_bottom.png');
	}
});

</script>

<div class="cityList">
<h3 onclick="location.href='./TravelAdminList.td'">DB 도시 개수 :<%=count %> </h3>

	<!-- 국가 리스트 출력 -->
	<table>
		<tr>
			<th onclick="sortTravelList(1)">도시 코드<img src="./images/sort_right.png" class="sort_img sort1_img"></th>
			<th onclick="sortTravelList(3)">여행지 이름<img src="./images/sort_right.png" class="sort_img sort2_img"></th>
			<th onclick="sortTravelList(5)">국가 코드<img src="./images/sort_right.png" class="sort_img sort3_img"></th>
			<th onclick="sortTravelList(7)">여행지 타입<img src="./images/sort_right.png" class="sort_img sort4_img"></th>
			<th>정보</th>
			<th>수정 /삭제</th>
		</tr>
		
		<%
		for (int i=0; i<travelList.size();i++){
			PlanTravelBean tb = (PlanTravelBean)travelList.get(i);
		%>
		<tr>
			<td><%=tb.getCity_code() %></td>
			<td style="font-weight: bold;"><%=tb.getName() %></td>
			<td><%=tb.getConuntry_code() %></td>
			<td><%=tb.getType() %></td>
			<td><span id="hideInfo<%=i%>" style="display: display"><a href="javascript:toggleInfo('<%=i%>', '1');">펼쳐보기</a></span>
			<span id="showInfo<%=i%>" style="display: none"><a href="javascript:toggleInfo('<%=i%>', '0');">접기</a><br><%=tb.getInfo() %></span></td>
			
			<td>
				<input type = "button" name="update" value="수정" onclick="location.href='#'">
<%-- 				<input type = "button" name="update" value="수정" onclick="location.href='./CityUpdate.pl?pageNum=<%=pageNum%>&city_code=<%=tb.getCity_code() %>'"> --%>
				<input type = "button" name="delete" value="삭제" onclick="">
<%-- 				<input type = "button" name="delete" value="삭제" onclick="delConfirm('<%=pageNum%>', '<%=tb.getCity_code()%>');"> --%>
			</td>
			
		</tr>
		<%}//for %>
	</table>
	
	<!-- 검색폼 -->
	<div class="search_div">
		<form action="./TravelAdminList.td" method="post">
			<img src="./images/member/search_l2.png" class="search_img">
			<input type="text" name="search" id="search" value="<%=search %>" placeholder="여행지 이름으로 검색">
			
			<input type="submit" value="검색" class="button alt">
		</form>
	</div>
	
	<div class="clear"></div>
	
	<!-- 국가 추가 버튼 -->
	<input type="button" onclick="location.href='./TravelAdminWriteAction.td'" value="여행지 추가하기" class="addCityBtn">
	
	<!-- 페이지 출력 -->
	<div class="page">
	<%	
	if(count != 0){
	if(startPage>pageBlock){
	%><a href="./TravelAdminList.td?pageNum=<%=startPage - pageBlock%>&search=<%=search%>&sort=<%=isort %>">[이전]</a>	
	<%}//if
	
	for (int i = startPage; i <= endPage; i++) {
	%><a href="./TravelAdminList.td?pageNum=<%=i%>&search=<%=search%>&sort=<%=isort %>"<%if(currentPage==i){ %>style="background-color: #ccc;"<%}%>><span style="color: #000;"><%=i%></span></a>
	<%}//for
	
	if (endPage < pageCount) {
	%><a href="./TravelAdminList.td?pageNum=<%=startPage + pageBlock%>&search=<%=search%>&sort=<%=isort %>">[다음]</a>
	<%
		}//if
	}//if
	%>
	</div>
		
</div>
</section>

</body>
</html>
