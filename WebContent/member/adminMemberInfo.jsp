<%@page import="net.member.db.MemberBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>

	<script type="text/javascript">
	
	// 닉네임 수정
	function nickUpdate(memberId, adminId) {
		
		var nick = $('.nick').val();
		
		var regNick = /^[a-z|가-힣][a-z|0-9|가-힣]{1,8}/i; // 영문, 한글 시작 영문,숫자,한글 조합 가능
		
		if(!regNick.test(nick)) {
			alert("닉네임을 형식에 맞게 입력해주세요.");
			return;
		}
		
		// 닉네임 중복확인
		$.ajax({
			type: 'POST',
			url: './member/updateCheck/nickOverlapCheck.jsp',
			data: {
				'id' : memberId,
				'nick' : nick,
				'adminId': adminId,
				'admin' : '1'
			},
			dataType: 'text',
			async: false,
			success: function(data) {
				if(data==1) {	// 중복없음
					alert(memberId+" 사용자의 닉네임이 변경되었습니다.");
					location.reload();	// 페이지 새로고침
				}else {			// 사용할 수 없는 닉네임
					alert("이미 사용중인 닉네임 입니다.");
				}
			},
			error: function(status, err) {
				alert(status + " : " + err);	
			}
		});
		
	}
	
	</script>
</head>

<!-- 회원정보 보기(관리자) -->
<!-- Header -->
<jsp:include page="../inc/header.jsp" />

<%
	// 세션값 확인
	String id = (String)session.getAttribute("id");
	if(id==null || id=="") {
		response.sendRedirect("./Main.me");
		return;
	}
	
	// 파라미터 값 가져오기
	String pageNum = request.getParameter("pageNum");
	String search = request.getParameter("search");
	if(search==null) {
		search="";
	}
	String sort = request.getParameter("sort");	
	if(sort==null) {
		sort="0";
	}
	int isort = Integer.parseInt(sort);
	
	MemberBean mb = (MemberBean)request.getAttribute("memberInfo");
	if(mb==null) {
		response.sendRedirect("./Main.me");
		return;
	}

%>

<!-- Main -->
<section id="main" class="wrapper memberManager">
		
		<div class = "container adminMemberInfo">
			<!-- 서브메뉴 -->
			<jsp:include page="subMenu/memberManager.jsp" />
			
			<!-- 주내용 -->
			<div class="content">

				<h1>회원 정보관리</h1>
				
				<!-- 제목 뺀 내용 -->
				<div class="inner_content">
				
				<!-- 프로필 사진 -->
				<img src="./upload/images/profileImg/<%=mb.getProfile() %>" onerror="this.src='./images/error/noImage.png'">	
				
				<!-- 닉네임만 수정가능하게(불건전 닉네임 방지) -->
				<table>
					<tr>
						<th>회원등급</th>
						<td><%if(mb.getAuth()==0) { %>
						<span style="color: red; font-weight: bold; text-shadow: 1px 1px 1px black;">관리자</span>
							<%}else if(mb.getGold()==1){%>
						<span style="color: gold; font-weight: bold; text-shadow: 1px 1px 1px black;">골드회원</span>
							<%}else{%>일반회원<%}%>					
						</td>
					</tr>
					<tr>
						<th>아이디</th>
						<td><%=mb.getId() %></td>
					</tr>
					<tr>
						<th>닉네임</th>
						<td>
							<input type="text" name="nick" class="nick" value="<%=mb.getNick()%>" maxlength="9">
							<input type="button" class="update_btn" value="수정" class="button alt" 
							onclick="nickUpdate('<%=mb.getId()%>', '<%=id%>');">
						</td>
					</tr>
					<tr>
						<th>이름</th>
						<td><%=mb.getName() %></td>
					</tr>
					<tr>
						<th>성별</th>
						<td><%=mb.getGender() %></td>
					</tr>
					<tr>
						<th>전화번호</th>
						<td><%=mb.getTel() %></td>
					</tr>
					<tr>
						<th>가입날짜</th>
						<td><%=mb.getReg_date() %></td>
					</tr>
				</table>
				
				</div><!-- inner_content -->
				
				<input type="button" value="뒤로가기" 
				onclick="location.href='./MemberManager.me?pageNum=<%=pageNum %>&search=<%=search %>&sort=<%=isort %>';" 
				class="button special">
				
			</div><!-- content -->
		</div>	<!-- adminMemberInfo -->
		
</section>

<!-- Footer -->
<div class="clear"></div>
<jsp:include page="../inc/footer.jsp" />

</html>
