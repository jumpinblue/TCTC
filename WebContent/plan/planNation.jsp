<%@page import="java.util.Calendar"%>
<%@page import="net.images.db.ImagesBean"%>
<%@page import="net.plan.db.PlanCityBean"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
   	
   	<!-- Link Swiper's CSS 슬라이드 -->
    <link rel="stylesheet" href="./assets/dist/css/swiper.min.css">
    
</head>	
<!-- Header -->
<jsp:include page="../inc/header.jsp" />

<%
	String nation = request.getParameter("nation");	// 국가명
	String id = (String) session.getAttribute("id");
	
	//바뀌는 배경을 초 단위로 하기 위해서 현재 시간 불러오기.
		Calendar cal = Calendar.getInstance();
		int second = cal.get(Calendar.SECOND)%4;//배경 갯수에 따라서 나누는 값 바꾸기
%>
<!-- One 지역명 및 설명-->

<!-- 현재 초 값을 받아와서, 배경이 새로고침 할때마다 바뀌도록. -->
<%if(second==3) {%>
<section id="banner" class="nation_one b_back<%=second%>">
<%} else if(second==2) {%>
<section id="banner" class="nation_one b_back<%=second%>">
<%} else if(second==1) {%>
<section id="banner" class="nation_one b_back<%=second%>">
<%} else { %>
<section id="banner" class="nation_one b_back">
<%} %>
	<h2><%=nation%></h2>
	<div class="nation_info_content">
	<!-- 국가의 도시들의 이미지 슬라이드 -->
	<!-- Swiper -->
    <%
    // 도시 이미지 리스트
    List<ImagesBean> cityImgList = null;
    cityImgList = (List)request.getAttribute("cityImgList");
    %>
    <div class="swiper-container slideContainer imgSlide">
        <div class="swiper-wrapper">
    <%
    ImagesBean ib = null;
    if(cityImgList.size()>0) {
    	for(int i=0; i<cityImgList.size(); i++) {
    		ib = cityImgList.get(i);
    		%>
    		<div class="swiper-slide"><img src="./images/plan/nation/<%=ib.getFile()%>"></div>
    		<%
    	}
    }else {
    	%>
    		<div class="swiper-slide" style="color: #fff;">이미지 정보가 없습니다.</div>
    	<%
    }
    %>
     </div>	<!-- swiper-wrapper 끝 -->
        <!-- Add Pagination -->
        <div class="swiper-pagination" style="color: #fff; font-weight: 900;"></div>
         
         <!-- Add Arrows -->
        <div class="swiper-button-next"></div>
        <div class="swiper-button-prev"></div>     
    </div>	<!-- slideContainer -->
    
    	
    <!-- Swiper JS -->
    <script src="./assets/dist/js/swiper.min.js"></script>

    <!-- Initialize Swiper -->
     <script>
    var swiper = new Swiper('.swiper-container', {
        pagination: '.swiper-pagination',
        nextButton: '.swiper-button-next',
        prevButton: '.swiper-button-prev',
        effect: 'coverflow',
        grabCursor: true,
        centeredSlides: true,
        slidesPerView: 'auto',
        paginationType: 'fraction',
        coverflow: {
            rotate: 50,
            stretch: 0,
            depth: 100,
            modifier: 1,
            slideShadows : true
        },
        autoplay: 2500,
        autoplayDisableOnInteraction: false
    });
    </script>
    
	</div>
</section>

<section class="info">
		<!-- 국가 정보 -->
	<div class="nation_info_content">
	<div class="nation_info">
	<!-- 지도 -->
	<div class="map">
		<!-- 수현씨 지도 부분 -->
		<iframe width="100%" height="100%" frameborder="0" style="border: 0"
			src="https://www.google.com/maps/embed/v1/place?key=AIzaSyAwZMwcmxMBI0VQAUkusmqbMVHy-b4FuKQ&q=<%=nation%>"
			allowfullscreen> </iframe>
	</div> <!-- 수현씨 지도 부분 끝 -->
	<%
	// PlanNationAction에서 가져옴
	StringBuffer nation_info = (StringBuffer)request.getAttribute("nation_info");
	%>
	<%=nation_info.toString() %>
	</div>
	<div class="clear"></div>
</div>	<!-- nation_info_content -->
</section>

<!-- Two 국가의 지역 리스트 -->
<section class="two">
	<div class="cityList">
		<h2><%=nation%> 인기도시</h2><hr>
		<div class="cityListTable">	<!-- 도시 리스트 -->
			<!-- 도시리스트 테이블 오는 자리 -->
		</div>	<!-- "cityListTable" -->
	</div>	<!-- "cityList" -->
</section>

<!-- info, Two 섹션 스크립트 -->
<script type="text/javascript">
// 페이지에 처음 왔을때 도시 리스트를 불러옴, 페이지번호는 1로 시작
$(window).load(function() {
	$.ajax({
		type: 'post',
		url: './plan/planNationCityList.jsp',
		data : {nation:'<%=nation%>', pageNum:'1'},
		success: function(data) {
			$('.cityListTable').append(data);
		},
		error: function(xhr, status, error) {
	        alert(error);
	    }   
	});
});

// 페이지 번호를 눌렸을때 그에 맞는 게시글을 불러옴
function cityListChange(pageNum) {
	
		var search = $('#search').val();
	
		$.ajax({
			type: 'post',
			url: './plan/planNationCityList.jsp',
			data : {nation:'<%=nation%>', pageNum: pageNum, search: search},
			success: function(data) {
				$('.cityListTable').empty();
				$('.cityListTable').append(data);
			},
			error: function(xhr, status, error) {
				alert(error);
		    }   
		});
}

/* 맵의 세로 크기를 정보테이블의 크기에 따라 변경 */
$(document).ready(function(){
	var infoTableHeight = $('.nation_info table').css('height');
	$('.nation_info_content .map').css('height', infoTableHeight);
});

</script>


<!-- Three -->
<section id="three" class="wrapper style1">
	<div class="container">
		<header class="major special">
			<h2><%=nation %>
				여행 후기
			</h2>
			<!-- <p>Feugiat sed lorem ipsum magna</p> -->
		</header>

		<div class="feature-grid">

			<!-- 이미지 서치 시작.-->

			<script src="./assets/js/plan/daumSearch3.js"></script>

			<div id="daumForm">
				<input id="daumSearch" type="hidden" value="<%=nation %>+여행"
					onkeydown="javascript:if(event.keyCode == 13) daumSearch.search();" />
				<!-- 				<input id="daumSubmit" onclick="javascript:daumSearch.search()" -->
				<!-- 					type="submit" value="검색" /> -->
			</div>

			<div id="daumView">
				<div id="daumImage"></div>
			</div>

			<div id="daumScript">
				<div id="daumImageScript"></div>
			</div>
		</div>
		<!-- 이미지 서치 끝. -->


	</div>
	
</section>

<!-- Four 여행 후기, 리뷰 등 댓글 -->
<section class="four">
	
	<div class="comment">
	<h2><%=nation %> 커뮤니티</h2>
		<div class="review_list">
			<!-- 리뷰 리스트 오는 자리 -->
			<!-- 페이지 번호 오는 자리 -->
		</div>
		
		<div class="comment_right">
		<!-- 리뷰 작성 -->
		<%if(id!=null) {%>
		<input type="button" value="리뷰쓰기" id="writeBtn" class="button alt writeBtn">
		<%} %>
		<!-- 숨겨진 공간 -->
		<div class="reviewWriterDiv">
			
			<form action="javascript:writeComplete()" method="post">
			<select id="eval">
				<option value="-1" style="font-weight: 900; color: #6B66FF">평가하기</option>
					<option value="1" style="color: orange;">좋아요!</option>
					<option value="2" style="color: blue;">괜찮아요.</option>
					<option value="3" style="color: red;">별로에요!</option>
			</select>
			<textarea rows="5" cols="5" maxlength="1000" name="content"></textarea>
			<div class="formBtnDiv">
				<input type="submit" value="작성완료" class="submitBtn">
				<input type="reset" value="다시쓰기" class="resetBtn">
			</div>
			
			</form>
			
		</div>	<!-- 리뷰 작성 div(숨겨진 공간) -->
			
			<%
			if(id==null) {
			%>
			<div class="comment_member">	<!-- 로그인, 회원가입 DIV -->
				<span>도움이 필요하신가요?<br>로그인하여 커뮤니티에 참여해 보세요!</span><br>
				<div class="comment_member_btn">
					<input type="button" value="로그인" onclick="popupToggle()">
					<input type="button" value="회원가입" onclick="location.href='./MemberJoin.me';">
				</div>
			</div>	<!-- .comment_member -->
			<%
			}
			%>	
		</div>	<!-- .comment_right -->
		
	<div class="clear"></div>
	</div>	<!-- .comment -->
	
</section>
	<!-- Four 스크립트 -->
	<script type="text/javascript">
		
		var toggleBtn = 0;
		$(document).ready(function(data){
			/* 리뷰 작성 버튼 */
			$('#writeBtn').click(function(){
				if(toggleBtn==0) {
					$('.reviewWriterDiv').removeClass('animated fadeOutUp').addClass('animated fadeInDown');
					$('.reviewWriterDiv').css('display', 'inline-block');
					
					toggleBtn = 1;
				}else {
					$('.reviewWriterDiv').removeClass('animated fadeInDown').addClass('animated fadeOutUp');
					setTimeout(function(){
						$('.reviewWriterDiv').hide();
					}, 700)
					toggleBtn = 0;
				}
			});
			
			
			/* 최초 리뷰 리스트 가져오기 */
			$.ajax({
				type: 'post',
				url: './plan/planComment/planNationCommentList.jsp',
				data : {nation:'<%=nation%>'},
				success: function(data) {
					$('div.comment .review_list').empty();
					$('div.comment .review_list').append(data);
				},
				error: function(xhr, status, error) {
					alert(error);
			    } 
			});
		});
		
		/* 리뷰 작성완료 DB작업 */
		function writeComplete() {
			var con = $('textarea').val();
			var sel = $('#eval').val();
			if(con.length == 0) {
				alert("글을 입력해주세요.");
				return;
			}
			if(sel == -1) {
				alert("평가하기를 해주세요.");
				return;
			}
			
			$.ajax({
				type: 'post',
				url: './plan/planComment/planNationCommentWrite.jsp',
				data : {nation:'<%=nation%>', content : con, eval : sel},
				async: false,
				success: function(data) {
					$('textarea').val('');
					$('.reviewWriterDiv').css('display', 'none');
					toggleBtn = 0;
				},
				error: function(xhr, status, error) {
					alert(error);
			    } 
			});
			
			nationCommentList(1);
		}
		
		/* 페이징 변경이나 다른 작업 후 다시 리뷰 리스트를 로딩할때 */
		function nationCommentList(pageNum) {
			$.ajax({
				type: 'post',
				url: './plan/planComment/planNationCommentList.jsp',
				data : {nation:'<%=nation%>', pageNum : pageNum},
				success: function(data) {
					$('div.comment .review_list').empty();
					$('div.comment .review_list').append(data);
				},
				error: function(xhr, status, error) {
					alert(error);
			    }
			});
		}
		
		/* 리뷰 삭제하기 */
		function nationCommentDelete(num, pageNum) {
			
			var con = confirm("리뷰를 삭제하시겠습니까?");
			
			if(con == true) {
				$.ajax({
					type: 'post',
					url: './plan/planComment/planNationCommentDelete.jsp',
					data : {num : num},
					async: false,
					success: function(data) {
						alert("삭제 되었습니다.");
					},
					error: function(xhr, status, error) {
						alert(error);
				    }
				});
				nationCommentList(pageNum);
			}
		}
		
	</script>

<div class="clear"></div>

<!-- Footer -->
<jsp:include page="../inc/footer.jsp" />

