<%@page import="net.member.db.MemberDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

    <%
    // joinCheck 폴더의 nickOverlapCheck.jsp와 비슷하지만 id에서 쓰는 기존의 닉네임과 같을때는 중복으로 보지 않는다.
    MemberDAO mdao = new MemberDAO();
    
    String id = request.getParameter("id");
    String nick = request.getParameter("nick");
    int check = mdao.nickOverlapCheck2(id, nick);
    
 	// 관리자가 다른 사용자의 닉네임 변경을 하기위해 중복검사를 했을때, 1인 경우
    String admin = request.getParameter("admin");
 	if(admin==null) {
 		admin = "1";
 	}
    if(check==1 && admin.equals("1")) {
    	mdao.updateNick(id, nick);
    
    	String adminId = request.getParameter("adminId");
    	String adminNick = mdao.getNick(adminId);
    	
    	if(adminNick.equals(nick)) {
    		session.setAttribute("nick", nick);
    	}	
    }
    %>
    
    <%=check%>