
<%@page import="java.util.Calendar"%>
<%@page import="net.board.db.boardBean"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	String id = "";
	if (session.getAttribute("id") != null) {
		id = (String) session.getAttribute("id");
	}

	String nick = "";
	if (session.getAttribute("nick") != null) {
		nick = (String) session.getAttribute("nick");
	}
	
	//바뀌는 배경을 초 단위로 하기 위해서 현재 시간 불러오기.
	Calendar cal = Calendar.getInstance();
	int second = cal.get(Calendar.SECOND)%4;//배경 갯수에 따라서 나누는 값 바꾸기
%>
<!-- Header -->
<jsp:include page="../inc/header.jsp" />

<!-- Banner -->
<!-- 현재 초 값을 받아와서, 배경이 새로고침 할때마다 바뀌도록. -->
<%if(second==3) {%>
<section id="banner" class="b_back<%=second%>">
<%} else if(second==2) {%>
<section id="banner" class="b_back<%=second%>">
<%} else if(second==1) {%>
<section id="banner" class="b_back<%=second%>">
<%} else { %>
<section id="banner" class="b_back">
<%} %>


	<div style="max-width: 1080px; margin: auto; padding: 1.5em 0;">
		<span class="small">나만의 여행 플래너</span><span class="head1"> <b>BEFORE</b>
			YOU GO...
		</span>
		<!-- 검색폼 -->
		<div class="Main_Search">
			<form name="fr" action="./PlanSearch.pl" class="main_plan_search"
				method="post" onsubmit="return checkSearch();">
				<h2>어디로 여행을 가시나요?</h2>
				<div class="clear"></div>
				<div style="max-width: 950px; margin: auto;">
					<select name="check" class="check_search">
						<option value="0">선택해주십시오</option>
						<option value="1">국가명</option>
						<option value="2">도시명</option>
					</select> <input type="text" name="search" value="" class="search_text"
						placeholder="임시 버튼은 아래에 있습니당"> <input type="submit"
						value="검색" class="main_serch_button">
				</div>
			</form>
		</div>
	</div>





</section>


<!-- Two -->
<section id="two" class="wrapper style2 special">
	<div class="main_container" style="max-width: 1080px;">
		<header class="major">
			<p class="pa">BEFORE YOU GO 에서</p>
			<h2>성공적인 여행을 시작하세요</h2>

		</header>
		
<!-- 		https://hyeonseok.com/soojung/css/2013/03/12/730.html -->
<!-- 마우스를 올렸을 때만 동작하도록 설정하자. -->
		
		<div class="main_explain">

			<a href="./PlanMain.pl">
				<div class="explain_gif">
					<img src="./images/main/gif01.gif" alt="" />
					<h4>
						여행 일정 플래너에서 <br>가고 싶은 여행지를 선택하세요!
					</h4>
				</div>
			</a>
			
			<%if(id.equals("")){ %>
			<a onclick="popupToggle()">
			<%} else {%>
			<a href="./MyPlan.pln?plan_nr=100">
			<%} %>
				<div class="explain_gif">
					<img src="./images/main/gif02.gif" alt="" />
					<h4>
						나의 일정 관리에서 <br> 여행 날짜와 교통을 알수있습니다!
					</h4>
				</div>
			</a>
			<a href='./BoardList1.bb'>
				<div class="explain_gif">
					<img src="./images/main/gif03.gif" alt="" />
					<h4>
						함께해요와 인생샷그램에서 <br> 여행을 공유하세요!
					</h4>
				</div>
			</a>
		</div>
		<div class="clear"></div>

	</div>
</section>

<!-- Carousel -->

<script src="assets/js/main/jquery.min.js"></script>
<script src="assets/js/main/jquery.dropotron.min.js"></script>
<script src="assets/js/main/jquery.scrolly.min.js"></script>
<script src="assets/js/main/jquery.onvisible.min.js"></script>
<script src="assets/js/main/skel.min.js"></script>
<script src="assets/js/main/util.js"></script>
<script src="assets/js/main/main.js"></script>

<section class="carousel">
	<header class="major" style="text-align: center;">
		<p>인생샷그램에서</p>
		<h2>당신의 여행을 공유하세요!</h2>

	</header>

	<div class="reel">

		<%
			List boardList = (List) request.getAttribute("bl");

			if (boardList != null) {
				for (int i = 0; i < boardList.size(); i++) {

					//자바빈(boardBean) 변수=배열한칸 접근 배열변수.get()
					boardBean bb = (boardBean) boardList.get(i);
		%>

		<article>
			<a href="#" class="image featured"> <img
				src="./upload/<%=bb.getImage1()%>" width=300 height=300
				onerror="this.src='./images/instagram/noimage.png'">
			</a>
			<header>
				<h3>
					<a href="#"><%=bb.getSubject()%></a>
				</h3>
			</header>
			<p><%=bb.getNick()%></p>
		</article>


		<%
			}
			}
		%>
	</div>
</section>
<!-- Carousel end-->



<!-- Three -->
<!-- <section id="three" class="wrapper style1"> -->
<!-- 	<div class="container"> -->
<!-- 		<header class="major special"> -->
<!-- 			<h2>Mauris vulputate dolor</h2> -->
<!-- 			<p>Feugiat sed lorem ipsum magna</p> -->
<!-- 		</header> -->
<!-- 		<div class="feature-grid"> -->
<!-- 			<div class="feature"> -->
<!-- 				<div class="image rounded"> -->
<!-- 					<img src="./images/pic04.jpg" alt="" /> -->
<!-- 				</div> -->
<!-- 				<div class="content"> -->
<!-- 					<header> -->
<!-- 						<h4>Lorem ipsum</h4> -->
<!-- 						<p>Lorem ipsum dolor sit</p> -->
<!-- 					</header> -->
<!-- 					<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. -->
<!-- 						Labore esse tenetur accusantium porro omnis, unde mollitia totam -->
<!-- 						sit nesciunt consectetur.</p> -->
<!-- 				</div> -->
<!-- 			</div> -->
<!-- 			<div class="feature"> -->
<!-- 				<div class="image rounded"> -->
<!-- 					<img src="./images/pic05.jpg" alt="" /> -->
<!-- 				</div> -->
<!-- 				<div class="content"> -->
<!-- 					<header> -->
<!-- 						<h4>Recusandae nemo</h4> -->
<!-- 						<p>Ratione maiores a, commodi</p> -->
<!-- 					</header> -->
<!-- 					<p>Animi mollitia optio culpa expedita. Dolorem alias minima -->
<!-- 						culpa repellat. Dolores, fuga maiores ut obcaecati blanditiis, at -->
<!-- 						aperiam doloremque.</p> -->
<!-- 				</div> -->
<!-- 			</div> -->
<!-- 			<div class="feature"> -->
<!-- 				<div class="image rounded"> -->
<!-- 					<img src="./images/pic06.jpg" alt="" /> -->
<!-- 				</div> -->
<!-- 				<div class="content"> -->
<!-- 					<header> -->
<!-- 						<h4>Laudantium fugit</h4> -->
<!-- 						<p>Possimus ex reprehenderit eaque</p> -->
<!-- 					</header> -->
<!-- 					<p>Maiores iusto inventore fugit architecto est iste expedita -->
<!-- 						commodi sed, quasi feugiat nam neque mollitia vitae omnis, ea -->
<!-- 						natus facere.</p> -->
<!-- 				</div> -->
<!-- 			</div> -->
<!-- 			<div class="feature"> -->
<!-- 				<div class="image rounded"> -->
<!-- 					<img src="./images/pic07.jpg" alt="" /> -->
<!-- 				</div> -->
<!-- 				<div class="content"> -->
<!-- 					<header> -->
<!-- 						<h4>Porro aliquam</h4> -->
<!-- 						<p>Quaerat, excepturi eveniet laboriosam</p> -->
<!-- 					</header> -->
<!-- 					<p>Vitae earum unde, autem labore voluptas ex, iste dolorum -->
<!-- 						inventore natus consequatur iure similique obcaecati aut corporis -->
<!-- 						hic in! Porro sed.</p> -->
<!-- 				</div> -->
<!-- 			</div> -->
<!-- 		</div> -->
<!-- 	</div> -->
<!-- </section> -->

<!-- Four -->
<!-- <section id="four" class="wrapper style3 special"> -->
<!-- 	<div class="container"> -->
<!-- 		<header class="major"> -->
<!-- 			<h2>Aenean elementum ligula</h2> -->
<!-- 			<p>Feugiat sed lorem ipsum magna</p> -->
<!-- 		</header> -->
<!-- 		<ul class="actions"> -->
<!-- 			<li><a href="#" class="button special big">Get in touch</a></li> -->
<!-- 		</ul> -->
<!-- 	</div> -->
<!-- </section> -->

<!-- 테스트용 버튼들. -->
<div style="max-width: 1080px; margin: auto; border:5px solid red;">



	<p>
		임시 메인화면 입니다. <br /> 현재 session의 id는
		<%=id%>, nick은
		<%=nick%>입니다.
	</p>
	<ul class="actions">
		<li><a href="./BoardList1.bb" class="button special big">함께해요</a></li>
		<li><a href="./PlanMain.pl" class="button special big">여행 일정
				플래너</a></li>
		<li><a href="./BoardList.bo" class="button special big">인생샷그램</a></li>
		<li><a href="#" class="button special big">Q & A</a></li>
	</ul>

	<br> 아래는 연습용 버튼입니다.
	<ul class="actions">

		<li><a href="./MemberJoin.me" class="button special big">회원가입</a></li>


		<%
			if (!id.equals("")) {
		%>
		<li><a href="./MyPlan.pln?plan_nr=100" class="button special big">나의일정관리</a></li>
		<%
			} else if (id.equals("")) {
		%>
		<li><a onclick="popupToggle()" class="button special big">나의일정관리</a></li>

		<%
			}
		%>

		<li><form action="./PlanRegion.pl" method="get">
				<input type="text" name="region" style="background-color: white"
					placeholder="암거나 검색"> <input type="submit" value="검색"
					class="button special">
			</form></li>

		<li><a href="./CountryList.pl" class="button special big">국가
				DB</a></li>
		<li><a href="./CityList.pl" class="button special big">도시 DB</a></li>
		<li><a href="./PlanSpot.pl?travel=태종대" class="button special big">추천장소
				상세보기</a></li>
		<li><a href="./Chat.ct" class="button special big">채팅 테스트</a></li>
	</ul>



</div>
<!-- 테스트용 버튼들 끝. -->

<!-- Footer -->
<jsp:include page="../inc/footer.jsp" />
