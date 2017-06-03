<%@page import="net.member.db.MemberDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

    <%
    MemberDAO mdao = new MemberDAO();
    
    String nick = request.getParameter("nick");
    int check = mdao.nickOverlapCheck(nick);
    %>
    
    <%=check%>
    