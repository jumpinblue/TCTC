package net.member.action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.PrivateKey;

import javax.crypto.Cipher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.member.db.MemberDAO;

// 자신의 아이디를 직접 탈퇴시키는 클래스
public class MemberDeleteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		int check = deleteMember(request);
		
		// 이동
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		if(check == 0) {
			out.print("<script>");
			out.print("alert('비밀번호가 맞지 않습니다.');");
			out.print("location.href='./MemberDelete.me';");
			out.print("</script>");
			out.close();
			
		}else if(check == -1) {
			
			HttpSession session = request.getSession();
			session.invalidate();
			
			out.print("<script>");
			out.print("alert('세션값이 없습니다.');");
			out.print("location.href='./Main.me';");
			out.print("</script>");
			out.close();
			
		}else {
			// 세션에 있는 값 삭제
			HttpSession session = request.getSession();
			session.invalidate();
			
			out.print("<script>");
			out.print("alert('회원탈퇴가 완료되었습니다.');");
			out.print("location.href='./Main.me';");
			out.print("</script>");
			out.close();	
		}
		
		return null;
	}

	/**
     * 암호화된 비밀번호를 복호화 한다.
     */
    private int deleteMember(HttpServletRequest request)
            throws ServletException, IOException {

    	int check = 0;	// 0: 비밀번호 틀림, 1: 탈퇴 완료
    	
    	// 한글처리
    	request.setCharacterEncoding("utf-8");
    	
    	// 파라미터 값 가져오기
        String securedPass = request.getParameter("pass");
        
        // 세션에 저장된 개인키 가져오기
        HttpSession session = request.getSession();
        PrivateKey privateKey = (PrivateKey) session.getAttribute("__rsaPrivateKey__");
        session.removeAttribute("__rsaPrivateKey__"); // 키의 재사용을 막는다. 항상 새로운 키를 받도록 강제.

        if (privateKey == null) {
            throw new RuntimeException("암호화 비밀키 정보를 찾을 수 없습니다.");
        }
        try {
        	// 암호화된 값 복호화
            String pass = decryptRsa(privateKey, securedPass);
            
            // 세션값 가져오기
            String id = (String)session.getAttribute("id");
            
            // DB작업전 프로필 사진 삭제 위한 설정
            String realPath = request.getRealPath("/upload/images/profileImg/");
            
            // DB작업
            MemberDAO mdao = new MemberDAO();
            check = mdao.deleteMember(id, pass, realPath);
            
        } catch (Exception ex) {
            throw new ServletException(ex.getMessage(), ex);
        }
        
        return check;
    }

    private String decryptRsa(PrivateKey privateKey, String securedValue) throws Exception {
        System.out.println("will decrypt : " + securedValue);
        Cipher cipher = Cipher.getInstance("RSA");
        byte[] encryptedBytes = hexToByteArray(securedValue);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        String decryptedValue = new String(decryptedBytes, "utf-8"); // 문자 인코딩 주의.
        return decryptedValue;
    }

    /**
     * 16진 문자열을 byte 배열로 변환한다.
     */
    public static byte[] hexToByteArray(String hex) {
        if (hex == null || hex.length() % 2 != 0) {
            return new byte[]{};
        }

        byte[] bytes = new byte[hex.length() / 2];
        for (int i = 0; i < hex.length(); i += 2) {
            byte value = (byte)Integer.parseInt(hex.substring(i, i + 2), 16);
            bytes[(int) Math.floor(i / 2)] = value;
        }
        return bytes;
    }
	
}
