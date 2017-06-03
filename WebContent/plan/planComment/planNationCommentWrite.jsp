<%@page import="net.plan.db.PlanNationCommentBean"%>
<%@page import="net.plan.db.PlanCommentDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	// 한글처리
	request.setCharacterEncoding("UTF-8");
	
	// 파라미터 값 가져오기
	String nation = request.getParameter("nation");
	String nick = (String)session.getAttribute("nick");
	String content = request.getParameter("content");
	int eval = Integer.parseInt(request.getParameter("eval"));
	
	// DB작업
	PlanCommentDAO pcdao = new PlanCommentDAO();
	PlanNationCommentBean pncb = new PlanNationCommentBean();
	
	pncb.setNation(nation);
	pncb.setNick(nick);
	pncb.setContent(content);
	pncb.setEval(eval);
	
	pcdao.insertNationComment(pncb);
%>