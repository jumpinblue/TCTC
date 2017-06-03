<%@page import="net.member.db.MemberBean"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<!-- 회원관리 페이지(관리자) -->
<!-- Header -->
<jsp:include page="../inc/header.jsp" />

<!-- 프로필 사진 확대해서 미리보기 -->
<div class="profileImg_enl"><img src="">
	<script type="text/javascript">
		$(document).ready(function() {
			$('.img_td').mouseenter(function(){
				var browserWidth = window.innerWidth;
				var browserHeight = window.innerHeight;
				var imgWid = browserWidth * 0.25;
				var imgHeight = browserHeight * 0.35;
				
				var profileImgPath = $(this).children('img').attr('src');
				
				$('.profileImg_enl').css('display', 'block');
				$('.profileImg_enl img').css({
					'width' : imgWid,
					'height' : imgHeight
				});
				$('.profileImg_enl img').attr('src', profileImgPath);
			});
			$('.img_td').mouseleave(function(){
				$('.profileImg_enl').css('display', 'none');
			})
		});
	</script>
</div>

<!-- Main -->
<section id="main" class="wrapper memberManager">
	<div class="container">
		<!-- 서브메뉴 -->
		<jsp:include page="subMenu/memberManager.jsp"/>
		
		<!-- 컨텐츠 -->
		<%
			// 세션값 가져오기
			String id = (String)session.getAttribute("id");	// 관리자 아이디
			if(id==null) {
				response.sendRedirect("./Main.me");
				return;
			}
		
			// 한글처리
			request.setCharacterEncoding("UTF-8");

			// 현제 페이지 번호
			String pageNum = request.getParameter("pageNum");
			if(pageNum==null) {
				pageNum = "1";
			}
			int currentPage = Integer.parseInt(pageNum);
		
			// 검색값 가져오기
			String search = request.getParameter("search");
			if(search==null) {
				search="";
			}
			String search_sel = request.getParameter("search_sel");	// 검색 방법
			if(search_sel==null) {
				search_sel = "id_search";
			}
			
			// 정렬방법
			String sort = request.getParameter("sort");
			int isort = 0;
			if(sort!=null) {	// 정렬값이 있을대, 없으면 기본값 0(가입날짜 오름차순)
				isort = Integer.parseInt(sort);
			}
			
			// 파라미터값 가져오기
			int count = 0;
			if(request.getAttribute("count")!=null) {
				 count = Integer.parseInt(String.valueOf(request.getAttribute("count")));	// 전체 회원 수
			}
			
			// 회원리스트 가져오기
			List<MemberBean> memberList = (List)request.getAttribute("memberList");
			MemberBean mb = null;
			
		%>
		<div class="content">
			<div class="content_member_memberManager">
				<h1>회원 관리(<%if(search.length()>0) {%> &lt;<%=search%>&gt;로 검색된<%} %> 회원수:<%=count %> )
				<%if(isort==1){%> : 아이디 오름차순 정렬<%}else if(isort==2){%> : 아이디 내림차순 정렬<%}%>
				<%if(isort==3){%> : 닉네임 오름차순 정렬<%}else if(isort==4){%> : 닉네임 내림차순 정렬<%}%>
				<%if(isort==5){%> : 관리자 우선정렬<%}else if(isort==6){%> : 사용자 우선정렬<%}else if(isort==0){%> : 가입날짜 오름차순<%}%>
				</h1>
					
					<table>
						<tr>
							<th class="th_img">
								<div style="width: 160px; margin: 0 auto;">프로필 이미지</div>
							</th>
							<th id="th_id" class="a th_id" title="아이디<%if(isort==1){%>오름차순<%}else if(isort==2){%>내림차순<%}%>">
								<div style="width: 80px; margin: 0 auto;">아이디<img src="./images/sort_right.png" style="width: 12px; height: 12px; float: right; margin-top: 10px;" class="sortId_img"></div>
							</th>
							<th id="th_nick" class="a th_nick" title="닉네임<%if(isort==3){%>오름차순<%}else if(isort==4){%>내림차순<%}%>">
								<div style="width: 80px; margin: 0 auto;">닉네임<img src="./images/sort_right.png" style="width: 12px; height: 12px; float: right; margin-top: 10px;" class="sortNick_img"></div>
							</th>
							<th id="th_auth" class="a th_auth" title="권한<%if(isort==5){%>:관리자우선<%}else if(isort==6){%>:사용자우선<%}%>">
								<div style="width: 99px; margin: 0 auto;">권한설정<img src="./images/sort_right.png" style="width: 12px; height: 12px; float: right; margin-top: 10px;" class="sortAuth_img"></div>
							</th>
							<th class="th_delete">
								회원탈퇴
							</th>
						</tr>
						<%
						if(count>0) {
							for(int i=0; i<memberList.size(); i++) {
								mb = memberList.get(i);
								%>
								<tr title="가입날짜: <%=mb.getReg_date() %>">
									<td class="img_td"><img src="./upload/images/profileImg/<%=mb.getProfile() %>" onerror="this.src='./images/error/noImage.png'"></td>
									<td class="id_td" onclick="location.href='./AdminMemberInfo.me?memberId=<%=mb.getId()%>&pageNum=<%=pageNum%>&search=<%=search%>&sort=<%=isort%>';">
									<span <%if(mb.getGold()==1){%>style="color: gold; text-shadow: 1px 1px 1px black;"<%} %>><%=mb.getId() %></span></td>
									<td class="nick_td">
									<span <%if(mb.getGold()==1){%>style="color: gold; text-shadow: 1px 1px 1px black;"<%} %>><%=mb.getNick() %></span>
									</td>	
									<td class="auth_select_box">
										<div class="select-wrapper">
										<select name="auth" id="category" onchange="auth_change('<%=mb.getId()%>', this.options[this.selectedIndex].value);">
											<option value="admin" <%if(mb.getAuth()==0){%>selected style="font-weight: bold;"<%}%>>관리자</option>
											<option value="user" <%if(mb.getAuth()==1){%>selected style="font-weight: bold;"<%}%>>사용자</option>
										</select>
										</div>
									</td>
									<td class="delete_td">
										<%
										if(id.equals(mb.getId())) {	// 현재 로그인한 관리자 아이디와 가져온 관리자 아이디와 같다면
										%>
											<span><a href="./MemberDelete.me">탈퇴</a></span>	<!-- 회원탈퇴 페이지로 -->
										<%	
										}else {
											%>
												<span><a href="javascript:deleteMemberPass('<%=mb.getId() %>')">탈퇴</a></span>
											<%
										}
										%>
									</td>	
								</tr>
								<%
							}
						}
						
						%>
					</table>
					
					<div class="search_div">
						<form action="./MemberManager.me?pageNum=1&sort=<%=isort%>" method="post">
							<select name="search_sel" id="category">
								<option value="id_search" <%if("id_search".equals(search_sel)){%>selected<%} %>>아이디 검색</option>
								<option value="nick_search" <%if("nick_search".equals(search_sel)){%>selected<%} %>>닉네임 검색</option>
							</select>
							<img src="./images/member/search_l2.png" class="search_img">
							<input type="text" name="search" value="<%=search %>" placeholder="search...">
							<input type="submit" value="검색" class="button alt">
						</form>
					</div>
					
					<div class="clear"></div>
					
					<%
					// 페이징
					int pageBlock = Integer.parseInt(String.valueOf(request.getAttribute("pageBlock")));
					int pageCount = Integer.parseInt(String.valueOf(request.getAttribute("pageCount")));
					int startPage = Integer.parseInt(String.valueOf(request.getAttribute("startPage")));
					int endPage = Integer.parseInt(String.valueOf(request.getAttribute("endPage")));
					%>
					<div class="page">
						<%
						if(currentPage > pageBlock) {
							%><a href="./MemberManager.me?pageNum=<%=startPage-pageBlock %>&search=<%=search%>&sort=<%=isort%>&search_sel=<%=search_sel%>">[이전]</a><%	
						}
						for(int i=startPage; i<=endPage; i++) {
							%><%if(currentPage==i){%>
							<a href="./MemberManager.me?pageNum=<%=i %>&search=<%=search%>&sort=<%=isort%>&search_sel=<%=search_sel%>" style="background-color: #f0f0f0;"><%=i %></a>
							<%}else {%><a href="./MemberManager.me?pageNum=<%=i %>&search=<%=search%>&sort=<%=isort%>&search_sel=<%=search_sel%>"><%=i %></a><%}%><%
						}
						if(pageCount > endPage) {
							%><a href="./MemberManager.me?pageNum=<%=startPage+pageBlock %>&search=<%=search%>&sort=<%=isort%>&search_sel=<%=search_sel%>">[다음]</a><%
						}
						%>
					</div>
					
					<script type="text/javascript">
					/* 관리자가 회원을 탈퇴시킬때 비밀번호 한번더 확인 */
					function deleteMemberPass(id) {
						var screenWidth = screen.availWidth;
						var screenHeight = screen.availHeight;
						var left = screenWidth/2 - 600/2;
						var top = screenHeight/2 - 350/2;
						
						window.open('./AdminPassCertification.me?id='+id+'&pageNum=<%=pageNum %>', '_blank', 
						'toolbar=no, location=no, status=no, menubar=no, scrollbars=no, resizable=no, directories=no, width=600, height=350, top='+top+', left='+left);
					}
					
					/* 권한 설정이 변경되었을때 */
					function auth_change(id, auth) {
						
						// DB 권한을 변경함
						$.ajax({
							type: 'POST',
							url: './MemberAuthChange.me',
							data: {'id' : id, 'auth' : auth},
							dataType: 'text',
							async: false,
							success: function(data){
								console.log("권한변경");
							},
							error: function(error){
								alert(error);
							}
						});
					}
					
					/* 정렬 */
					$('#th_id').click(function(){
						var sort = <%=isort%>;	// 현재 정렬모드
						if(sort==1) {	// 오름차순
							location.href='./MemberManager.me?pageNum=<%=pageNum%>&search=<%=search%>&sort=2&search_sel=<%=search_sel%>';
						}else {	// 내림차순
							location.href='./MemberManager.me?pageNum=<%=pageNum%>&search=<%=search%>&sort=1&search_sel=<%=search_sel%>';
						}
					});
					
					$('#th_nick').click(function(){
						var sort = <%=isort%>;	// 현재 정렬모드
						if(sort==3) {	// 오름차순
							location.href='./MemberManager.me?pageNum=<%=pageNum%>&search=<%=search%>&sort=4&search_sel=<%=search_sel%>';
						}else {	// 내림차순
							location.href='./MemberManager.me?pageNum=<%=pageNum%>&search=<%=search%>&sort=3&search_sel=<%=search_sel%>';
						}
					});
					
					$('#th_auth').click(function(){
						var sort = <%=isort%>;	// 현재 정렬모드
						if(sort==5) {	// 오름차순
							location.href='./MemberManager.me?pageNum=<%=pageNum%>&search=<%=search%>&sort=6&search_sel=<%=search_sel%>';
						}else {	// 내림차순
							location.href='./MemberManager.me?pageNum=<%=pageNum%>&search=<%=search%>&sort=5&search_sel=<%=search_sel%>';
						}
					});
					/* 정렬 이미지 */
					if(<%=isort%>==1) {
						$('.sortId_img').attr('src', './images/sort_top.png');
					}else if(<%=isort%>==2) {
						$('.sortId_img').attr('src', './images/sort_bottom.png');
					}else if(<%=isort%>==3) {
						$('.sortNick_img').attr('src', './images/sort_top.png');
					}else if(<%=isort%>==4) {
						$('.sortNick_img').attr('src', './images/sort_bottom.png');
					}else if(<%=isort%>==5) {
						$('.sortAuth_img').attr('src', './images/sort_top.png');
					}else if(<%=isort%>==6) {
						$('.sortAuth_img').attr('src', './images/sort_bottom.png');
					}
					
					</script>
					
			</div> <!-- content_member_memberManager -->
		</div>	<!-- content -->
	
	</div>
</section>

<div class="clear"></div>

<!-- Footer -->
<jsp:include page="../inc/footer.jsp" />