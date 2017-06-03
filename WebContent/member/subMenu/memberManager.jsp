<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	String id = (String)session.getAttribute("id");
	if(id==null) {
		response.sendRedirect("./Main.me");
		return;
	}

	String auth_str = (String)session.getAttribute("auth");
	if(auth_str==null) {
		response.sendRedirect("./Main.me");
		return;
	}
	int auth = Integer.parseInt(auth_str);
%>
<!-- 서브메뉴 -->
<div id="submenu">
	<ul>
		<li><a href="./MemberInfo.me">회원정보</a></li>
		<li><a href="./MemberPassUpdate.me">비밀번호 변경</a></li>
		<li><a href="#">내가 쓴 게시글 관리</a></li>
		<li><a href="#">내가 쓴 댓글 관리</a></li>
		<li><a href="./MemberDelete.me">회원탈퇴</a></li>

		<%
		if(auth==0) {	// 관리자 전용
		%>
			<li style="margin-top: 160px; border-bottom: 1px solid #ccc;">관리자 전용</li>
			<li><a href="./MemberManager.me?pageNum=1">회원관리</a></li>
			<li><a href="./AdminDBManager.me">관리자 DB작성</a></li>
		<%
		}
		%>

	</ul>
</div>