<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    
<!-- Header -->
<jsp:include page="../inc/header.jsp" />

<!-- Main -->
<section id="main" class="wrapper memberManager">
	<div class="container">

		<!-- 서브메뉴 -->
		<jsp:include page="subMenu/memberManager.jsp"/>
		
		<%
			// 세션값 확인
			String id = (String)session.getAttribute("id");
			if(id==null) {
				response.sendRedirect("./Main.me");
				return;
			}
		%>
		
		<!-- 컨텐츠 -->
		<div class="content">
			<div class="content_member_passUpdate">
				<h1>비밀번호 변경</h1>
				
				<div class="pass_form">
					
					<form action="./MemberPassUpdateAction.me" method="post" onsubmit="return updatePassCheck()">
						<table>	
							<tr>
								<th>현재 비밀번호</th>
								<td><input type="password" name="cur_pass" id="cur_pass" maxlength="16" placeholder="6~16자 영문 대 소문자로 시작하고, 숫자, 특수문자를 사용"></td>
							</tr>
							<tr>
								<th colspan="2"><hr></th>	<!-- 구분 행 -->
							</tr>
							<tr>
								<th>새로운 비밀번호</th>
								<td><input type="password" name="new_pass" id="new_pass" maxlength="16" placeholder="6~16자 영문 대 소문자로 시작하고, 숫자, 특수문자를 사용"></td>
							</tr>
							<tr>
								<th>비밀번호 재입력</th>
								<td><input type="password" name="new_pass2" id="new_pass2" maxlength="16"></td>
							</tr>
						</table>
						
						<!-- RSA암호화 공개키 -->
						<input type="hidden" id="updatePass_publicKeyModulus" value="<%=request.getAttribute("publicKeyModulus") %>">
						<input type="hidden" id="updatePass_publicKeyExponent" value="<%=request.getAttribute("publicKeyExponent") %>">
						
						<div class="form_btn">
							<input type="submit" value="수정">
							<input type="reset" value="되돌리기">
						</div>
					</form>
					
					<script type="text/javascript">
					
						function updatePassCheck() {
							var cur_pass = $('#cur_pass').val();	// 아이디의 현재 비밀번호
							var new_pass = $('#new_pass').val();	// 새로운 비밀번호
							var new_pass2 = $('#new_pass2').val();
							
							// 입력 여부
							if(cur_pass.length == 0) {
								alert("현재 비밀번호를 입력해주세요.");
								$('#cur_pass').focus();
								return false;
							}else if(new_pass.length == 0) {
								alert("새로운 비밀번호를 입력해주세요.");
								$('#new_pass').focus();
								return false;
							}
							
							// 비밀번호 형식 확인
							var regPass = /^[a-z][a-z|0-9|!@#$%^&*_-]{5,15}/i;	// 영문시작, 영문숫자특수문자 조합 가능 6~16자
							if(!regPass.test(new_pass)) {
								alert("비밀번호를 형식에 맞게 입력해주세요.");
								$('#new_pass').val('');
								$('#new_pass').focus();
								return false;
							}
							
							// 비밀번호 일치 여부 확인
							if(new_pass != new_pass2) {
								alert("새로운 비밀번호가 일치하지 않습니다.");
								$('#new_pass2').focus();
								return false;
							}
							
							// RSA암호화
							 try {
    							// 공개키 가져오기
        						var updatePass_publicKeyModulus = document.getElementById("updatePass_publicKeyModulus").value;
       							var updatePass_publicKeyExponent = document.getElementById("updatePass_publicKeyExponent").value;
        
        						var rsa = new RSAKey();
       							rsa.setPublic(updatePass_publicKeyModulus, updatePass_publicKeyExponent);
        
        						// 사용자ID와 비밀번호를 RSA로 암호화한다.(이름은 암호화 제외)
        						var securedCurPass = rsa.encrypt(cur_pass);
       							var securedNewPass = rsa.encrypt(new_pass);
        						var securedNewPass2 = rsa.encrypt(new_pass2);
								
        						// 기존 폼에 암호화 된 값을 재입력
        						$('#cur_pass').val(securedCurPass);		// 아이디의 현재 비밀번호
    							$('#new_pass').val(securedNewPass);		// 새로운 비밀번호
    							$('#new_pass2').val(securedNewPass2);
        						
    							return true;
    							
							 }catch(err) {
							 	alert(err);
						  	 }
						}
					
					</script>
					
				</div>	<!-- info_form -->
				
			</div> <!-- content_member_info -->
		</div>	<!-- content -->
	
	</div>
</section>

<div class="clear"></div>

<!-- Footer -->
<jsp:include page="../inc/footer.jsp" />