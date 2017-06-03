package net.member.action;

import java.io.File;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.oreilly.servlet.multipart.FileRenamePolicy;

import net.member.db.MemberBean;
import net.member.db.MemberDAO;

public class MemberUpdateAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// 한글처리
		request.setCharacterEncoding("UTF-8");
		
		// 파라미터 값 가져오기(MultipartRequest)
		String realPath = request.getRealPath("/upload/images/profileImg");
		System.out.println("가상 프로필사진 저장 공간: " + realPath);
		int maxSize = 5 * 1024 * 1024;	// 5MB
		MultipartRequest multi = new MultipartRequest(request, realPath, maxSize, "UTF-8", new DefaultFileRenamePolicy());
		
		String id = multi.getParameter("id");
		String name = multi.getParameter("name");
		String nick = multi.getParameter("nick");
		String gender = multi.getParameter("gender");
		String tel = multi.getParameter("tel");
		String profile = multi.getFilesystemName("profile");
		
		// 파라미터 값 빈에 담기
		MemberBean mb = new MemberBean();
		mb.setId(id);
		mb.setName(name);
		mb.setNick(nick);;
		mb.setGender(gender);
		mb.setTel(tel);
		mb.setProfile(profile);
		
		// DB수정 작업
		MemberDAO mdao = new MemberDAO();
		try {
			mdao.updateMember(mb, realPath);
		}catch(Exception e) {
			e.printStackTrace();
		}
		// 세션값 변경
		HttpSession session = request.getSession();
		session.setAttribute("nick", mb.getNick());
		
		// 이동
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println("<script>");
		out.println("alert('회원정보 수정이 완료되었습니다.');");
		out.println("location.href='./MemberInfo.me';");
		out.println("</script>");
		out.close();
		
		return null;
	}
	
}
