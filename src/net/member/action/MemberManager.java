package net.member.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.member.db.MemberBean;
import net.member.db.MemberDAO;

/* memberManager.jsp 에 회원정보를 출력하기위해 회원정보를 가져오는 클래스 */
public class MemberManager implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// 한글처리
		request.setCharacterEncoding("UTF-8");
		
		// MemberDAO 객체 생성
		MemberDAO mdao = new MemberDAO();
		
		// 파라미터 값 가져오기
		String search = request.getParameter("search");
		String search_sel = request.getParameter("search_sel");	// 검색 종류(id_search, nick_search)
		
		if(search_sel==null) {
			search_sel = "id_search";
		}
		String sort = request.getParameter("sort");	// 정렬값, 기본값 0
		int isort = 0;
		if(sort!=null) {
			isort = Integer.parseInt(sort);
		}
		
		/* 회원 리스트 가져오기 */
		// 현제 페이지 번호 구하기
		String pageNum = request.getParameter("pageNum");
		if(pageNum==null) {
			pageNum = "1";
		}
		
		// 전체 회원의 수 구하기
		int count;
			// 검색값이 있으면 검색값에 맞는 회원수만 계산
		if(search==null) {
			count = mdao.getCountMember();
			if(search==null) {
				search="";
			}
		}else {
			count = mdao.getCountMember(search, search_sel);
		}
		request.setAttribute("count", count);	// request에 회원 수 담기
		
		int currentPage = Integer.parseInt(pageNum);
		
		// 한 페이지에 출력할 게시글의 갯수 설정
		int pageSize = 10;
		
		// 시작 행 구하기
		int startRow = (currentPage - 1) * pageSize + 1;
		
		// 회원 리스트 가져오기
		List<MemberBean> memberList = null;
		if(count != 0) {
			memberList = mdao.getMemberList(startRow, pageSize, search, search_sel, isort);
		}
		
		request.setAttribute("memberList", memberList);
		
		
		/* 페이징 처리하기 */
		// 한 페이지에 보여줄 페이지 링크 갯수
		int pageBlock = 10;
		
		// 필요한 페이지 수 구하기
		int pageCount = (count/pageSize)+(count%pageSize==0?0:1);
		
		// 시작 페이지 구하기
		int startPage = ((currentPage-1)/pageBlock) * pageBlock + 1;
		
		// 끝 페이지 구하기
		int endPage = startPage + pageBlock - 1;
		if(endPage > pageCount) {
			endPage = pageCount;
		};
		
		// request에 페이지 값들 담기
		request.setAttribute("pageBlock", pageBlock);	// 한페이지에 보여줄 페이지 수
		request.setAttribute("pageCount", pageCount);	// 총 필요한 페이지 수
		request.setAttribute("startPage", startPage);	// 시작 페이지
		request.setAttribute("endPage", endPage);	// 끝 페이지
		
		// 이동정보
		ActionForward forward = new ActionForward();
		forward.setPath("./member/memberManager.jsp?pageNum="+pageNum);
		forward.setRedirect(false);
		
		return forward;
	}
}
