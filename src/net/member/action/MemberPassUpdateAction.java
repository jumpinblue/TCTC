package net.member.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.PrivateKey;

import javax.crypto.Cipher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.member.db.MemberDAO;

public class MemberPassUpdateAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		int check = updatePass(request, response);
		
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		if(check == 0) {	// 비밀번호 틀림
			out.print("<script>");
			out.print("alert('현재 비밀번호가 맞지 않습니다.');");
			out.print("location.href='./MemberPassUpdate.me';");
			out.print("</script>");
			out.close();
		}else if(check == -1){	// 아이디 없음
			
			HttpSession session = request.getSession();
			session.invalidate();
			
			out.print("<script>");
			out.print("alert('세션값이 없어졌습니다.');");
			out.print("location.href='./Main.me';");
			out.print("</script>");
			out.close();
		}else {
			out.print("<script>");
			out.print("alert('비밀번호 변경 완료');");
			out.print("location.href='./MemberInfo.me';");
			out.print("</script>");
			out.close();
		}
		
		return null;
	}
	
	/**
     * 암호화된 비밀번호를 복호화 한다.
     */
    private int updatePass(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    	int check = 0;	// 0: 비밀번호 틀림, 1: 수정 성공
    	
    	// 한글처리
    	request.setCharacterEncoding("utf-8");
    	
    	// 파라미터 값 가져오기
        String securedCurPass = request.getParameter("cur_pass");
		String securedNewPass = request.getParameter("new_pass");
        
        // 세션에 저장된 개인키 가져오기
        HttpSession session = request.getSession();
        PrivateKey privateKey = (PrivateKey) session.getAttribute("__rsaPrivateKey__");
        session.removeAttribute("__rsaPrivateKey__"); // 키의 재사용을 막는다. 항상 새로운 키를 받도록 강제.

        if (privateKey == null) {
            throw new RuntimeException("암호화 비밀키 정보를 찾을 수 없습니다.");
        }
        try {
        	// 암호화된 값들 복호화
            String cur_pass = decryptRsa(privateKey, securedCurPass);
            String new_pass = decryptRsa(privateKey, securedNewPass);
            
            // 세션값 가져오기
            String id = (String)session.getAttribute("id");
            
            // DB작업
            MemberDAO mdao = new MemberDAO();
            check = mdao.updatePass(id, cur_pass, new_pass);
            
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
