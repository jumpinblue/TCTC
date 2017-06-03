<%@page import="net.plan.db.PlanCommentDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	// 한글처리
	request.setCharacterEncoding("UTF-8");
	
	// 파라미터 값 가져오기
	String num = request.getParameter("num");
	int inum = Integer.parseInt(num);
	
	// 삭제 작업
	PlanCommentDAO pcdao = new PlanCommentDAO();
	pcdao.deleteRegionReview(inum);
%>