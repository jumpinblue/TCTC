<%-- <%@page import="java.util.ArrayList"%>
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
	
<%
	List basketList = (List) request.getAttribute("basketList");
	List goodsList = (List) request.getAttribute("goodsList");
	String id = (String) session.getAttribute("id");

%>
	    
<select name="planMaker">
<option>---선택하세요---</option>
<%
	for (int i = 0; i < basketList.size(); i++) {
		TravelBean tb = (TravelBean) goodsList.get(i); /*  여행지(상품) DB Bean */
%>
	<option value="<%=tb.getName()%>"><%=tb.getName()%></option>
<%}%>	
</select>	
	    
	
		 --%>
	
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- <h1>WebContent/j3/string2.jsp</h1> -->
<%
String name=request.getParameter("name");
String age=request.getParameter("age");
%>
<h1><%=name %>:<%=age %></h1>
	
	
