<%@page import="net.member.action.MemberLoginAction"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	MemberLoginAction loginAction = new MemberLoginAction();
	
	String securedId = request.getParameter("securedId");
	String securedPass = request.getParameter("securedPass");

	int check = -1;	// -1: 아이디 없음, 0: 비밀번호 틀림, 1: 인증 성공
	try {
		check = loginAction.execute(request, response);
		
	}catch(Exception e) {
		e.printStackTrace();
	}
%>
<%=check %>