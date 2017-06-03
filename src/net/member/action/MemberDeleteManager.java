package net.member.action;

import java.io.PrintWriter;
import java.security.PrivateKey;

import javax.crypto.Cipher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.member.db.MemberDAO;

// 관리자가 회원관리 페이지에서 다른 회원을 삭제하는 클래스
public class MemberDeleteManager implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// 한글처리
		request.setCharacterEncoding("UTF-8");
		
		// 파라미터 값 가져오기
		String id = request.getParameter("id");				// 삭제할 아이디
		String securedPass = request.getParameter("pass");	// 암호화 된 비밀번호
		
		String pageNum = request.getParameter("pageNum");	// 페이지 번호
		
		// 세션값 가져오기
		HttpSession session = request.getSession();
		String adminId = (String)session.getAttribute("id");	// 관리자 아이디
		
		PrivateKey privateKey = (PrivateKey)session.getAttribute("__rsaPrivateKey__");	// 개인키
		session.removeAttribute("__rsaPrivateKey__"); // 키의 재사용을 막는다. 항상 새로운 키를 받도록 강제.

        if (privateKey == null) {
            throw new RuntimeException("암호화 비밀키 정보를 찾을 수 없습니다.");
        }
		
        try {
        	// 암호화된 비밀번호 복호화
        	String pass = decryptRsa(privateKey, securedPass);
        	
    		// 디비 작업
    		MemberDAO mdao = new MemberDAO();
    		int check = mdao.memberLogin(adminId, pass);
    		
    		response.setContentType("text/html; charset=UTF-8");
    		PrintWriter out = response.getWriter();
    		if(check==0) {	// 비밀번호 틀림
    			
    			out.print("<script>");
    			out.print("alert('비밀번호가 일치하지 않습니다.');");
    			out.print("location.href='./AdminPassCertification.me?pageNum="+pageNum+"';");
    			out.print("</script>");
    			out.close();

    		}else if(check == -1) {	// 아이디 없음
    		
    			session.invalidate();
    			
    			out.print("<script>");
    			out.print("alert('세션값이 없습니다.');");
    			out.print("location.href='./Main.me';");
    			out.print("</script>");
    			out.close();
    			
    		}else {	// 비밀번호 맞음
    			
    			// DB작업전 프로필 사진 삭제 위한 설정
                String realPath = request.getRealPath("/upload/images/profileImg/");
    			
    			mdao.deleteMember(id, realPath);	// 회원 삭제
    			
    			out.print("<script>");
    			out.print("alert('회원을 삭제하였습니다.');");
    			out.print("window.opener.top.location.href='./MemberManager.me?pageNum="+pageNum+"';");
    			out.print("window.close();");
    			out.print("</script>");
    			out.close();
    		}
        	
        }catch(Exception e) {
        	e.printStackTrace();
        }
        
		return null;
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
