<%@page import="java.util.ArrayList"%>
<%@page import="java.io.PrintWriter"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>
<%@page import="temp.MyPlanBean"%>
<%@page import="net.travel.admin.db.TravelBean"%>
<%@page import="net.myplanBasket.db.MyPlanBasketBean"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<!-- Header -->
<jsp:include page="../inc/header.jsp" />
<!-- 스타일 불러오기 -->
<link rel="stylesheet" href="assets/css/main.css" />
<link rel="stylesheet" href="assets/css/map/modifyNewFile7.css" />
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" href="/resources/demos/style.css">
<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script type="text/javascript" src="http://code.jquery.com/jquery-2.1.0.min.js" ></script>
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script src="http://code.jquery.com/jquery-1.11.0.min.js"></script>
<script src="http://code.jquery.com/ui/1.11.4/jquery-ui.min.js"></script>
</head>
<script >

$(function(){
	$("#left_box2").click(function(){
		var effect = 'slide';
		var options ='left';
		var duration = 500;
		$('#right_box').toggle(effect, options, duration);
	}); 
});



$( function() {
    $( "#left_box2_detail" ).sortable();
    $( "#left_box2_detail" ).disableSelection();
  } );
  
  
/* 
$(document).ready(function(datelist){
	
	var left_box1_detail li=$(this).val();
	$('date').empty();
	$('date').append(function(){
		
	});
}); */

if(document.test.planMaker.selectedIndex!=0){
	alert("과목을 선택하세요");
	document.test.se.focus();
	return false;
}//if

</script>
<script type="text/javascript">

	$(document).ready(function(){
		//name:홍길동 age:21파라미터 넘겨서
		//string2.jsp 결과처리 내용가져오기
		$('#testPlanner').click(function(){
			
		$.ajax('myplanModify_selectBox.jsp',{
			data:{name:'홍길동', age:21},
			success:function(data){
				//select 뒷부분 추가
				alert("testtest");
				$('#testsel').append(data);
			}
		});
		
		});
	});

</script>
<body>
	<%
		List basketList = (List) request.getAttribute("basketList");
		List goodsList = (List) request.getAttribute("goodsList");
		String id = (String) session.getAttribute("id");

		int plan_nr = Integer.parseInt(request.getParameter("plan_nr"));
		
		String fromDate = (String) request.getParameter("fromDate");
		String toDate = (String) request.getParameter("toDate");
		List datelist = (List)request.getAttribute("datelist");
		System.out.println(fromDate);
		
		
		
		String dep_lat =  (String) request.getParameter("dlat");
		String dep_lng =  (String) request.getParameter("dlng");
		String arr_lat =  (String) request.getParameter("alat");
		String arr_lng =  (String) request.getParameter("alng");
    
		%>

</body>
<div class="wrap">

	<%if(plan_nr==1){%>일정A<%} %>
    <%if(plan_nr==2){%>일정B<%} %>
    <%if(plan_nr==3){%>일정C<%} %> 
    
<table border="1" class="tg" name="test">
  <tr>
    <th id="testPlanner" ><%=fromDate%></th>
    <td>
    
  
	    <select name="planMaker" >
	    <option>---선택하세요---</option>
	    <%
	    	for (int i = 0; i < basketList.size(); i++) {
	    		TravelBean tb = (TravelBean) goodsList.get(i); /*  여행지(상품) DB Bean */
	    %>
	    	<option  value="<%=tb.getName()%>"><%=tb.getName()%></option>
		<%}%>	
		</select>
		
			
    </td>
    <td>	
    </td>
  </tr>
  <%for(int j=0;j<datelist.size();j++){ %>
  <tr>
  	<th><%=datelist.get(j) %></th>
    <td>
      <select name="planMaker" >
	    <option>---선택하세요---</option>
	    <%
	    	for (int i = 0; i < basketList.size(); i++) {
	    		TravelBean tb = (TravelBean) goodsList.get(i); /*  여행지(상품) DB Bean */
	    %>
	    	<option  value="<%=tb.getName()%>"><%=tb.getName()%></option>
		<%}%>	
		</select>
    </td>
    <td>
    </td>
  </tr>
    <% } %>
  <tr>
  	<th><%=toDate%></th>
    <td>
      <select name="planMaker" >
	    <option>---선택하세요---</option>
	    <%
	    	for (int i = 0; i < basketList.size(); i++) {
	    		TravelBean tb = (TravelBean) goodsList.get(i); /*  여행지(상품) DB Bean */
	    %>
	    	<option  value="<%=tb.getName()%>"><%=tb.getName()%></option>
		<%}%>	
		</select>
    </td>
    <td>
    </td>
  </tr>
  
  <tr><td colspan="7"><input type="submit" value="일정수정">
		<input type="reset" value="다시등록"></td>
	</tr>
</table>

<div id="testsel"></div>
</div>


<% 	
for(int i=0;i<basketList.size();i++){
	MyPlanBasketBean mpbb = (MyPlanBasketBean)basketList.get(i);
	TravelBean tb=(TravelBean)goodsList.get(i);
	if(mpbb.getId().equals(id)){
	if(mpbb.getPlan_nr()== plan_nr){
%>
	<%
}
}
}
 	%>



		
		<!-- <form action="./MyPlanModifyAction.pln" method="post" ></form> -->
		<%-- <input type="text" name="plan_nr" value="<%=plan_nr%>"> --%>
		
		
<%-- 		<div class="wrap">
			<div id="left_box1">
				<!-- box1 -->
				<ul id="left_box1_detail">
					<!-- myplanModify.css -->
					<li id="size"><%if (plan_nr == 1) {%>일정A<%}%><%if (plan_nr == 2) {%>일정B<%}%></li>
					<li>전체일정보기</li>
					<li><%=fromDate%></li>
					<%for(int i=0;i<datelist.size();i++){ %>
					<li>
						<%=datelist.get(i) %>
					</li>		
						<% } %>
					<li><%=toDate%></li>
				</ul>
			</div>
			
			
			
			
			<div id="left_box2">
				<!-- box2 찜 바구니, 날짜마다 바구니 다르게 할 예정 ajax 찾는중-->	
				<ul id="left_box2_head">
						<li><button style="border: 1px solid red;">경로최적화</button>
						<button style="border: 1px solid red;">장소검색하기</button></li>
				</ul>
				<ul id="left_box2_detail"><!-- 빈 공간으로 두고 right box에서 찜하기 버튼 눌러서 리스트 채울 예정 -->
					<li>장소를 추가해 보세요~</li>
					<li>Drag & Drop 으로 일정순서를 변경해 보세요~</li>
					 
				</ul>
			</div>	
	
			<!-- box3 도시 찜 버튼 -->
			<div id="right_box">
				 <ul id="right_box_detail">
						<%
						for (int i = 0; i < goodsList.size(); i++) {
							TravelBean tb = (TravelBean) goodsList.get(i);
						%>
						<li><%=tb.getName()%></li>
						<%
							}
						%>
			</ul>
				
			</div>
			<!-- <div id="map" class="f1" ></div> -->
		</div>
		

 --%>
	
	
	<%-- 		<table border="1" >
					<tr>
						<td>plan_nr</td>
						<td>item_nr</td>
						<td>name</td>
					</tr>
				
					<%
						for (int i = 0; i < basketList.size(); i++) {
							MyPlanBasketBean mpbb = (MyPlanBasketBean) basketList.get(i);
							TravelBean tb = (TravelBean) goodsList.get(i);
						/* 	if (plan_nr != mpbb.getPlan_nr() & plan_nr != 100)
								continue; */
					%>
					<tr>
						<td><%=mpbb.getPlan_nr()%></td>
						<td><%=mpbb.getItem_nr()%></td>
						<td><%=tb.getName()%></td>
					</tr>
					<%
						}
					%>
		</table> --%>
		
		
<div class="clear"></div>
<!-- Footer -->
<jsp:include page="../inc/footer.jsp" />
</html>