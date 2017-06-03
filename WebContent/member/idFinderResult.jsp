<%@page import="java.util.List"%>
<%@page import="net.member.db.MemberBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>아이디 찾기</title>
<!-- Scripts -->
		<script src="./assets/js/jquery.min.js"></script>
		<script src="./assets/js/jquery-3.2.0.js"></script>
		<script src="./assets/js/skel.min.js"></script>
		<script src="./assets/js/util.js"></script>
		<script src="./assets/js/main.js"></script>
        	
		<!-- 스타일 불러오기 -->
		<link rel="stylesheet" href="./assets/css/main.css" />
		
		<!-- 추가한 스크립트 -->
		<script type="text/javascript">
			
			function window_close() {
				
				window.close();
				
			}
		
		</script>
		
		<!-- 추가한 스타일 -->
		<style type="text/css">
			input[type="button"] {
				display: block;
				margin: 0 auto;
			}
		
		</style>
		
</head>
<body>
	<!-- 찾은 아이디 리스트를 출력하는 페이지 -->
	<%
		// 한글처리
		request.setCharacterEncoding("utf-8");
	
		// 파라미터 값 가져오기
		List<MemberBean> idList = (List<MemberBean>)request.getAttribute("idList");
		MemberBean mb;
	%>
	<div id="container">
		
		<table>
			<tr>
				<th>아이디</th><th>가입날짜</th>
			</tr>
			<%
			if(idList!=null) {
				for(int i=0; i<idList.size(); i++) {
					mb = idList.get(i);
					%>
					<tr>
						<td><%=mb.getId() %></td><td><%=mb.getReg_date() %></td>
					</tr>
					<%
				}
			}
			%>
		</table>
		
		<input type="button" value="확인" onclick="window_close()">
		
	</div>
	
</body>
</html>