<%@page import="net.images.db.ImagesBean"%>
<%@page import="net.images.db.ImagesDAO"%>
<%@page import="net.plan.db.PlanDAO"%>
<%@page import="net.plan.db.PlanCityBean"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	//파라미터 값 가져오기.
	String nation = request.getParameter("nation"); // 리스트의 링크 값
	String search = request.getParameter("search");	// 검색값
	if(search==null) {
		search="";
	}
	
	//객체 생성
	PlanDAO pdao = new PlanDAO();

	/* 게시글 가져오기 */
	//DB에 등록된 글의 개수.
	int count = 0;
	if("".equals(search)) {	// 검색값이 없으면
		count = pdao.getCityCount(nation);
	}else {	// 검색값이 있으면
		count = pdao.getCityCount(nation, search); 
	}

	// 한페이지에 보여줄 글의 개수
	int pageSize = 6;

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
	List<PlanCityBean> pcbList = null;
	if (count > 0) {
		if("".equals(search)) {	// 검색값 없으면
			pcbList = pdao.getCityList(nation, startRow, pageSize);
		}else {	// 검색값 있으면
			pcbList = pdao.getCityList(nation, startRow, pageSize, search);
		}	
	}
	
	/* 페이징 */
	// 필요한 전체 페이지 수
	int pageCount = count / pageSize + (count % pageSize == 0 ? 0 : 1);
	// 한페이지에 출력할 페이지 수
	int pageBlock = 3;
	// 시작 페이지
	int startPage = ((currentPage - 1) / pageBlock) * pageBlock + 1;
	;
	// 끝 페이지
	int endPage = startPage + pageBlock - 1;
	if (endPage > pageCount) {
		endPage = pageCount;
	}
%>

<!-- 검색폼 -->
<div class="search_div">
	<form action="javascript:cityListChange('<%=pageNum %>');" method="post">
		<img src="./images/member/search_l2.png" class="search_img">
		<input type="text" name="search" id="search" value="<%=search %>" placeholder="search...">
		<input type="submit" value="검색" class="button alt">
	</form>
</div>
					
<div class="clear"></div>

<table>
	<%
		ImagesDAO idao = new ImagesDAO();	// 이미지 정보 들고오기
		PlanCityBean pcb = null;
		String cityPath = "";
		if (count > 0) {
			for (int i = 0; i < pcbList.size(); i++) {
				pcb = pcbList.get(i);
				cityPath = idao.getCityImgPath(pcb.getCity_code());
				cityPath = cityPath.replace("\\", "/");	// css는 \아닌 /로 인식되서 바꿔줌
	%>
	<tr onclick="location.href='./PlanRegion.pl?region=<%=pcb.getName()%>';">
		<td class="img_td"
			style="background-image: url('./images/plan/nation/<%=cityPath %>'); background-size: cover;"></td>
		<td class="txt_td">
			<span class="city_name"><%=pcb.getName()%></span><br>
			<span class="city_enName"><%=pcb.getEn_name()%></span><br>
			<span class="city_info"><%=pcb.getInfo()%></span>
		</td>
	</tr>
	<%
		}
		} else {
	%>
	<tr>
		<td colspan="2" style="display: block; text-align: center; width: 1080px;">찾으시는 정보가 없습니다.</td>
	</tr>
	<%
		}
	%>
</table>

<!-- 페이징 -->
<div class="page">
	<%
		if (currentPage > pageBlock) {
	%><a href="javascript:cityListChange('<%=startPage - pageBlock%>', '<%=search%>');">
	<img src="./images/etc/backward_icon.png"></a>
	<%
		}
		for (int i = startPage; i <= endPage; i++) {
	%><a href="javascript:cityListChange('<%=i%>', '<%=search%>');"
		<%if (currentPage == i) {%>
		style="color: #0054FF; font-weight: 900;" <%}%>><%=i%></a>
	<%
		}
		if (pageCount > endPage) {
	%><a href="javascript:cityListChange('<%=startPage + pageBlock%>', '<%=search%>');">
	<img src="./images/etc/next_icon.png"></a>
	<%
		}
	%>
</div>