<%@page import="net.images.db.ImagesDAO"%>
<%@page import="net.plan.db.PlanTravelBean"%>
<%@page import="net.plan.db.PlanDAO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	//파라미터 값 가져오기.
	String region = request.getParameter("region"); // 리스트의 링크 값
	String search = request.getParameter("search"); // 검색값
	String city_code = request.getParameter("city_code"); // city_code 값
	String id = (String) session.getAttribute("id");

	//객체 생성
	PlanDAO pdao = new PlanDAO();

	/* 게시글 가져오기 */
	//DB에 등록된 글의 개수.
	int count = 0;

	if (search == null) { // 검색값이 없으면
		search = "";//null값 방지를 위해서.
		count = pdao.getTravelCount(region, city_code);
	} else { // 검색값이 있으면
		count = pdao.getTravelCount(region, city_code, search);
	}

	// 한페이지에 보여줄 글의 개수
	int pageSize = 5;

	// 현페이지가 몇페이지인지 가져오기(기본 1페이지)
	String pageNum = request.getParameter("pageNum");
	if (pageNum == null)
		pageNum = "1"; // pageNum없으면 무조건 1페이지
	int currentPage = Integer.parseInt(pageNum);

	// 시작글 구하기 1 11 21 31 ... <= pageNum, pageSize 조합
	int startRow = (currentPage - 1) * pageSize + 1;

	// 끝행구하기
	int endRow = currentPage * pageSize;

	// 도시 리스트 가져오기
	List<PlanTravelBean> ptbList = null;
	if (count > 0) {
		if ("".equals(search)) { // 검색값 없으면
			ptbList = pdao.getTravelList(startRow, pageSize, region, city_code);
		} else { // 검색값 있으면
			ptbList = pdao.getTravelList(startRow, pageSize, region, search, city_code);
		}
	}

	/* 페이징 */
	// 필요한 전체 페이지 수
	int pageCount = count / pageSize + (count % pageSize == 0 ? 0 : 1);
	// 한페이지에 출력할 페이지 수
	int pageBlock = 5;
	// 시작 페이지
	int startPage = ((currentPage - 1) / pageBlock) * pageBlock + 1;
	;
	// 끝 페이지
	int endPage = startPage + pageBlock - 1;
	if (endPage > pageCount) {
		endPage = pageCount;
	}
	
	//조건부 검색된 값이면 c_b가 true로.
	boolean c_b = false;
	if (search.equals("rph") || search.equals("rp") || search.equals("rh") || search.equals("ph")
			|| search.equals("r") || search.equals("p") || search.equals("h")) {
		c_b = true;
	}
	//조건부 검색된 값이면 c_b가 true로. 끝.
%>

<style>

#zzim{
	padding: 0 1.5em;
	margin-left: 2em;
	max-height: 2em;
	vertical-align: middle;
	line-height: 0;
}

#travel_name{
	font-size: 1.2em;
	font-weight: bold;
	color: black;
	margin-bottom: 0.7em;

}

table td{
	padding: 1em;
	vertical-align: middle;
}

</style>


<!-- 검색폼 -->

<div class="search_div">
	<form action="javascript:regionListChange('<%=pageNum%>');"
		method="post">
		<img src="./images/member/search_l2.png" class="search_img">

		<!-- 조건부 검색 조건값을 보여주지 않음. -->
		<%
			if (c_b) {
		%>
		<input type="text" name="search" id="search" value=""
			placeholder="이름으로 찾기">
		<%
			} else {
		%>
		<input type="text" name="search" id="search" value="<%=search%>"
			placeholder="이름으로 찾기">
		<%
			}
		%>
		<!-- 조건부 검색 조건값을 보여주지 않음. 끝.-->

		<input type="submit" value="검색" class="button alt">
	</form>
</div>

<div class="clear"></div>
<!-- 검색폼 끝. -->

<table>
	<%
	// 
	ImagesDAO idao = new ImagesDAO();	// 이미지 정보 들고오기
	String regionPath = "";
	
		PlanTravelBean ptb = null;
		if (count > 0) {
			for (int i = 0; i < ptbList.size(); i++) {
				ptb = ptbList.get(i);
				
				regionPath = idao.getTravelImgPath(ptb.getTravel_id());
				regionPath = regionPath.replace("\\", "/");	// css는 \아닌 /로 인식되서 바꿔줌
	%>
	<tr>
		<td class="img_td">
			<img src="./images/plan/nation/<%=regionPath %>" alt="<%=ptb.getName()%>">
		</td>
		<td class="txt_td" style="text-align: left; padding-left: 1em;">
			<p id="travel_name"><%=ptb.getName()%> 
				<%
					if (id != null) {
				%>
				<input type="button" name="zzim"
					onclick="zzim_add('<%=ptb.getTravel_id()%>')" value="찜"
					class="button special icon fa-download" id="zzim"/>
				<%
					} else if (id == null) {
				%>
				<input type="button" name="zzim_noId" onclick="zzim_noId()"
					value="찜" class="button special icon fa-download" id="zzim" />
				<%
					}
				%>
			</p>
			
			<p
				style="font-size: 1.0em; line-height: 20px; letter-spacing: -1px; word-spacing: 4px;"><%=ptb.getInfo()%></p>
		</td>
	</tr>
	<%
		}
		} else {
	%>
	<tr>
		<td colspan="2"><p>찾으시는 정보가 없습니다.</p></td>
	</tr>
	<%
		}
	%>
</table>

<%
	// 페이징
%>
<div class="page">
	<%
		if (currentPage > pageBlock) {
	%><a
		href="javascript:regionListChange('<%=startPage - pageBlock%>', '<%=search%>');">[이전]</a>
	<%
		}
		for (int i = startPage; i <= endPage; i++) {
	%><a href="javascript:regionListChange('<%=i%>', '<%=search%>');"
		<%if (currentPage == i) {%> style="background-color: #ccc;" <%}%>><%=i%></a>
	<%
		}
		if (pageCount > endPage) {
	%><a
		href="javascript:regionListChange('<%=startPage + pageBlock%>', '<%=search%>');">[다음]</a>
	<%
		}
	%>
</div>