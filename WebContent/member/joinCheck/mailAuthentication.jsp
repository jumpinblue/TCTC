<%@page import="net.member.db.MemberDAO"%>
<%@page import="net.member.action.MailAuthentication"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
String id = request.getParameter("id");

// 아이디(이메일) 중복확인
MemberDAO mdao = new MemberDAO();
int check = mdao.idOverlapCheck(id);

MailAuthentication mail = null;
int certificationNumber;					// 인증번호

if(check == 1) {
	// 인증번호 보내기
	mail = new MailAuthentication();	
	certificationNumber = mail.sendMail(id);
}else {	// 인증번호 안보냄
	certificationNumber = -1;
}
%>

<%=certificationNumber%>