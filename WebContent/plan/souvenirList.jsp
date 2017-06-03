<%@page import="net.plan.db.PlanSouvenirBean"%>
<%@page import="java.util.List"%>
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

		<!-- 스타일 -->
		<link rel="stylesheet" href="./assets/css/main.css?ver=3"/>
		<link rel="stylesheet" href="./assets/css/animate/animate.min.css"/>
</head>
<body>

<div class="clear"></div>
<section>
	<%
		String city_code="";
		List souvenirList = (List)request.getAttribute("souvenirList");
		String pageNum=(String)request.getAttribute("pageNum");
		if(pageNum==null){
			pageNum="1";
		}
	%>
	
<div>
	<table>
		<tr>
		<td>도시코드</td>
		<td>이름</td>
		<td>이미지</td>
		<td>순위</td>
		<td>정보</td>
		</tr>
		
		<%
		if(souvenirList.size()==0){
			%>
			</table>
			<div>기념품 정보가 없습니다.</div>	
			<%		
		}else{
			for(int i=0;i<souvenirList.size();i++){
				PlanSouvenirBean psb=(PlanSouvenirBean)souvenirList.get(i);
				city_code=psb.getCity_code();
		%>
			<tr>
				<td><%=psb.getCity_code() %></td>
				<td><%=psb.getName() %></td>
				<td><%=psb.getImg() %></td>
				<td><%=psb.getRanking() %>위</td>
				<td><%=psb.getInfo() %></td>
			</tr>
		<%
			}
			%>
			</table>
			<%
		}
		%>
		
		<input type="button" value="기념품 추가" onclick="location.href='./SouvenirAdd.pl?city_code=<%=city_code%>'">
	
	

</div>

</section>

</body>
</html>