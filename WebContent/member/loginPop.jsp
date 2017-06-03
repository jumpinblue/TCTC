<%@page import="net.member.action.RSAKeySetting"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

    <%
    // 로그인 팝업 띄우기 전에 RSA암호화를 위해 키를 생성, 공개키는 request에 개인키는 session에 저장됨
    RSAKeySetting rsa_key = new RSAKeySetting();
    rsa_key.keySetting(request);
    %>
<div id="publicKey_div">
<input type="hidden" id="login_publicKeyModulus" value="<%=request.getAttribute("publicKeyModulus")%>" />
<input type="hidden" id="login_publicKeyExponent" value="<%=request.getAttribute("publicKeyExponent")%>" />
</div>