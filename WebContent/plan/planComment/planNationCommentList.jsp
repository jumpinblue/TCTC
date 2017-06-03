<%@page import="net.member.db.MemberBean"%>
<%@page import="net.member.db.MemberDAO"%>
<%@page import="net.plan.db.PlanNationCommentBean"%>
<%@page import="java.util.List"%>
<%@page import="net.plan.db.PlanCommentDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	// 한글처리
	request.setCharacterEncoding("UTF-8");

	// 세션값 가져오기
	String id = (String)session.getAttribute("id");

	// 파라미터 값 가져오기
	String nation = request.getParameter("nation");
	
	// 페이지 번호 구하기
	String pageNum = request.getParameter("pageNum");
	if(pageNum==null) {
		pageNum="1";
	}
	int currentPage = Integer.parseInt(pageNum);

	// 한 페이지에 출력할 리스트 갯수
	int pageSize = 6;
	
	// 시작 행 구하기
	int startRow = (currentPage - 1) * pageSize + 1;
	
	// 리스트 가져오기
    PlanCommentDAO pcdao = new PlanCommentDAO();
	List<PlanNationCommentBean> list = pcdao.getNationListComment(nation, startRow, pageSize);
%>

<%if(list.size()==0) {
	%>
		<table><tr><td>아직 리뷰가 없습니다.</td></tr></table>
	<%
}else {
	%>
	<table>
	<%
	PlanNationCommentBean pncb = null;
	for(int i=0; i<list.size(); i++) {
		pncb = list.get(i);
		%>
		<tr>
			<td>
			<%
			// 닉네임을 이용해 프로필 사진 찾기
			MemberDAO mdao = new MemberDAO();
			MemberBean mb = mdao.getMemberNick(pncb.getNick());
			MemberBean sessionIdMb = mdao.getMember(id);
			%>
			<img src="./upload/images/profileImg/<%=mb.getProfile() %>" onerror="this.src='./images/error/noImage.png'"><!-- 프로필 이미지 -->
			<span class="c_nick"><%=pncb.getNick() %></span><span class="c_date"><%=pncb.getDate() %></span>
			<span class="c_eval"><%if(pncb.getEval()==1) {%>좋아요!<%}%>
								<%if(pncb.getEval()==2) {%>괜찮아요.<%}%>
								<%if(pncb.getEval()==3) {%>별로에요!<%}%></span>
			<span class="c_content"><%=pncb.getContent() %></span>
			</td>
			<td>
				<%
				if(id!=null && mb.getId().equals(id) || id!=null && sessionIdMb.getAuth()==0) {
					%>
					<a href="javascript:nationCommentDelete('<%=pncb.getNum()%>', '<%=pageNum%>');">삭제</a>
					<%
				}
				%>
			</td>
		</tr>
	<%
	}
	%>
	</table>
	<%
	// 전체 글의 갯수 구하기
	int count = pcdao.getCount(nation);
	
	// 필요한 페이지 갯수
	int pageCount = count/pageSize + (count%pageSize==0 ? 0:1);
	
	// 페이지 크기 지정
	int pageBlock = 3;
	
	// 시작 페이지
	int startPage = ((currentPage-1)/pageBlock) * pageBlock + 1;
	
	// 끝 페이지
	int endPage = startPage + pageBlock - 1;
	if(endPage > pageCount) {
		endPage = pageCount;
	}
	%>
	<div class="page">
		<%
		if(currentPage > pageBlock) {
			%>
			<a href="javascript:nationCommentList('<%=startPage-pageBlock%>');">
				<img src="./images/etc/backward_icon.png">
			</a>
			<%
		}
		for(int i=startPage; i<=endPage; i++) {
			%><a href="javascript:nationCommentList('<%=i%>');"
			<%if(currentPage==i){%>style="border: 1px solid #FF00DD;"<%}%>><%=i %></a><%
		}
		if(endPage < pageCount) {
			%><a href="javascript:nationCommentList('<%=startPage+pageBlock%>');">
				<img src="./images/etc/next_icon.png">
			</a><%
		}
		%>
		
	</div>
	<%
}
%>
	
















