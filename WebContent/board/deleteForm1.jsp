<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <!-- Header -->
<jsp:include page="../inc/header.jsp" />
<section class="wrapper" style="padding:0 ;">
<!-- Header ph -->
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
//int  num     String  pageNum 파라미터 가져오기
int num=Integer.parseInt(request.getParameter("num"));
String pageNum=request.getParameter("pageNum");


%>
<form action="./BoardDelete1.bb?pageNum=<%=pageNum %>" method="post" name="fr">
<input type="hidden" name="num" value="<%=num%>">
비밀번호:<input type="password" name="pass"><br>
<input type="submit" value="글삭제">
</form>
</body>
</html>
<!-- Footer --> <jsp:include page="../inc/footer.jsp" />
