<%@page import="net.plan.db.PlanRegionCommentBean"%>
<%@page import="net.plan.db.PlanCommentDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	// 한글처리
	request.setCharacterEncoding("UTF-8");
	
	// 파라미터 값 가져오기
	String region = request.getParameter("region");
	String nick = (String)session.getAttribute("nick");
	String content = request.getParameter("content");
	int eval = Integer.parseInt(request.getParameter("eval"));
	
	// DB작업
	PlanCommentDAO pcdao = new PlanCommentDAO();
	PlanRegionCommentBean prcb = new PlanRegionCommentBean();
	
	prcb.setRegion(region);
	prcb.setNick(nick);
	prcb.setContent(content);
	prcb.setEval(eval);
	
	pcdao.insertRegionComment(prcb);
%>