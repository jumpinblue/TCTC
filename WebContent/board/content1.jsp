
<%@page import="net.Board1.db.BoardBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>글내용보기</h1>
<%
BoardBean bb = (BoardBean)request.getAttribute("bb");
String pageNum = request.getParameter("pageNum");
%>
<table>
<tr><td>글쓴이</td><td><%=bb.getNick_name()%></td></tr>
<!-- 닉네임 가져오기  -->

<tr><td>날짜</td><td><%=bb.getDate() %></td></tr>
<!-- 글쓴 시간 time stamp -->
<tr><td>글내용</td><td colspan="3"><%=bb.getContent()%></td></tr>

<%-- <a href="../upload/<%=bb.getFile()%>"><%=bb.getFile()%></a></td></tr> --%>

<tr><td>글제목</td><td colspan="3"><%=bb.getSubject()%></td></tr>
<tr><td colspan="4">



<input type="button" value="글수정"
onclick="location.href='./BoardUpdate1.bb?num=<%=bb.getNum()%>&pageNum=<%=pageNum%>'">
<input type="button" value="글삭제"
onclick="location.href='./BoardDelete1.bb?num=<%=bb.getNum()%>&pageNum=<%=pageNum%>'">

<input type="button" value="글목록"
onclick="location.href='list1.jsp'"></td></tr>

</table>
</body>
</html>