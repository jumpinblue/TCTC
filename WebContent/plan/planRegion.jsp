<%@page import="net.images.db.ImagesBean"%>
<%@page import="java.util.Calendar"%>
<%@page import="net.plan.db.PlanTravelBean"%>

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
<!-- Banner -->
<!-- <section id="banner"> -->
<%
	String region = request.getParameter("region");
	String id = (String) session.getAttribute("id");
	String city_code = (String) request.getAttribute("city_code");
	
	//바뀌는 배경을 초 단위로 하기 위해서 현재 시간 불러오기.
	Calendar cal = Calendar.getInstance();
	int second = cal.get(Calendar.SECOND)%4;//배경 갯수에 따라서 나누는 값 바꾸기
%>

<!-- One 지역명 및 설명-->
<%if(second==3) {%>
<section id="banner" class="region_one b_back<%=second%>">
<%} else if(second==2) {%>
<section id="banner" class="region_one b_back<%=second%>">
<%} else if(second==1) {%>
<section id="banner" class="region_one b_back<%=second%>">
<%} else { %>
<section id="banner" class="region_one b_back">
<%} %>

	<h2><%=region%></h2>

	<div class="region_info_content">
	<!-- 국가의 도시들의 이미지 슬라이드 -->
	<!-- Swiper -->
    <%
    // 도시 이미지 리스트
    List<ImagesBean> travelImgList = null;
    travelImgList = (List)request.getAttribute("travelImgList");
    %>
    <div class="swiper-container slideContainer imgSlide">
        <div class="swiper-wrapper">
    <%
    ImagesBean ib = null;
    if(travelImgList.size()>0) {
    	for(int i=0; i<travelImgList.size(); i++) {
    		ib = travelImgList.get(i);
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
     
     $(document).ready(function(){
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
     });
    </script>
       
	</div>
		
</section>

<section class="info">
		<!-- 국가 정보 -->
	<div class="region_info_content">
	<div class="region_info">
	<!-- 지도 -->
	<div class="map">
			<!-- 수현씨 지도 부분 -->
			<iframe width="100%" height="450" frameborder="0" style="border: 0; min-width: 280px;"
				src="https://www.google.com/maps/embed/v1/place?key=AIzaSyAwZMwcmxMBI0VQAUkusmqbMVHy-b4FuKQ&q=<%=region%>" allowfullscreen>
			</iframe>
		</div>
		<!-- 수현씨 지도 부분 끝 -->
	<!-- 지역 정보 -->
	<%
		StringBuffer region_info = (StringBuffer) request.getAttribute("region_info");
	%>
	<%=region_info.toString()%>
	</div>
	<div class="clear"></div>
</div>	<!-- region_info_content -->
</section>

<script type="text/javascript">
	/* 맵의 세로 크기를 정보테이블의 크기에 따라 변경 */
	$(document).ready(function(){
		var infoTableHeight = $('.region_info table').css('height');
		$('.region_info .map').css('height', infoTableHeight);
	});
</script>

<!-- Two -->
<!-- 지역 리스트 -->
<section class="two">
	<!-- container -->
	<div class="travelList">
		<h2><%=region%> 주요 지역 </h2>
		
		<hr style="max-width: 1080px;">

		<!-- checkbox -->
		<div class="checkbox" onchange="checkbox_change()">
			<input type="checkbox" name="r" id="r" checked="checked"><label for="r">Restaurant</label>
			<input type="checkbox" name="p" id="p" checked="checked"><label for="p">Place</label>
			<input type="checkbox" name="h" id="h" checked="checked"><label for="h">Hotel</label>
		</div>
		<!--end checkbox -->

		<!-- 도시리스트 테이블 오는 자리 -->
		<div class="region_list_div"></div>
		<!-- region_list_div -->
	</div>
	<!-- container end-->


	<!-- Two 섹션 스크립트 -->
	<script type="text/javascript">
		// 페이지에 처음 왔을때 도시 리스트를 불러옴, 페이지번호는 1로 시작
		$(window).load(function() {
			$.ajax({
				type: 'post',
				url: './plan/planRegionList.jsp',
				data : {region:'<%=region%>', pageNum:'1', city_code:'<%=city_code%>'},
				success: function(data) {
					$('.region_list_div').append(data);
				},
				error: function(xhr, status, error) {
        			alert(error);
    			}   
			});
		});

		//checkbox 버튼을 통한 조건부 검색.
		function checkbox_change(){
		
			var $checkbox;

			//checkbox에서 받아온 true / false를 문자 형태로 저장
			if(r.checked && p.checked && h.checked) {$checkbox="rph"}
			else if(!r.checked && !p.checked && !h.checked) {$checkbox="rph"}
	
			else if(r.checked && p.checked && !h.checked) {$checkbox="rp"}
			else if(r.checked && !p.checked && h.checked) {$checkbox="rh"}
			else if(!r.checked && p.checked && h.checked) {$checkbox="ph"}
	
			else if(r.checked && !p.checked && !h.checked) {$checkbox="r"}
			else if(!r.checked && p.checked && !h.checked) {$checkbox="p"}
			else if(!r.checked && !p.checked && h.checked) {$checkbox="h"}
			//checkbox에서 받아온 true / false를 문자 형태로 저장. 끝
	
			$.ajax({
				type: 'post',
				url: './plan/planRegionList.jsp',
				data : {region:'<%=region%>', pageNum: '1', search: $checkbox, city_code:'<%=city_code%>'},
				success: function(data) {
					$('.region_list_div').empty();
					$('.region_list_div').append(data);
				},
				error: function(xhr, status, error) {
					alert(error);
			    }
			});
	
		}

		// 페이지 번호를 눌렸을때 그에 맞는 게시글을 불러옴
		function regionListChange(pageNum) {
	
			var search = $('#search').val();
	
			$.ajax({
				type: 'post',
				url: './plan/planRegionList.jsp',
				data : {region:'<%=region%>', pageNum: pageNum, search: search, city_code:'<%=city_code%>'},
				success : function(data) {
					$('.region_list_div').empty();
					$('.region_list_div').append(data);
				},
				error : function(xhr, status, error) {
					alert(error);
				}
			});
		}

		//찜 버튼 누르면 내 일정에 담김.
		function zzim_add(travel_id) {

			$.ajax({
				type : 'POST',
				url : './MyPlanBasketAdd.pln',
				data : {
					'travel_id' : travel_id
				},
				dataType : 'text',
				async : false,
				success : function(data) {
					console.log(data)

				}
			});
			
			//찜 버튼이 동작한 후, 페이지 이동을 물어본다.
			if (confirm("\n나의 일정에 추가되었습니다.\n\n나의 일정 페이지로 이동하시겠습니까?") == true){    //확인
				location.href="./MyPlan.pln?plan_nr=100";
			}else{   //취소
			    return;
			}//찜 버튼이 동작한 후, 페이지 이동을 물어본다. 끝
			
		}//찜 버튼 끝.

		//비로그인 상태에서 찜버튼을 누르면 로그인 창 활성화.
		function zzim_noId() {
			popupToggle()
		}
	</script>

</section>
<!-- 지역 리스트 끝-->

<div class="clear" style="clear: both;"></div>


<!-- Three -->
<section id="three" class="wrapper style1">
	<div class="container">
		<header class="major special">
			<h2><%=region%>
				여행 후기
			</h2>

		</header>

		<!-- 이미지 서치 시작.-->
		<div class="feature-grid">
			<script src="./assets/js/plan/daumSearch3.js"></script>

			<div id="daumForm">
				<input id="daumSearch" type="hidden" value="<%=region%>+여행" onkeydown="javascript:if(event.keyCode == 13) daumSearch.search();" />
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
	<h2><%=region %> 커뮤니티</h2>
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
				url: './plan/planComment/planRegionCommentList.jsp',
				data : {region:'<%=region%>'},
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
				url: './plan/planComment/planRegionCommentWrite.jsp',
				data : {region:'<%=region%>', content : con, eval : sel},
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
			
			regionCommentList(1);
		}
		
		/* 페이징 변경이나 다른 작업 후 다시 리뷰 리스트를 로딩할때 */
		function regionCommentList(pageNum) {
			$.ajax({
				type: 'post',
				url: './plan/planComment/planRegionCommentList.jsp',
				data : {region:'<%=region%>', pageNum : pageNum},
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
		function regionCommentDelete(num, pageNum) {
			
			var con = confirm("리뷰를 삭제하시겠습니까?");
			
			if(con == true) {
				$.ajax({
					type: 'post',
					url: './plan/planComment/planRegionCommentDelete.jsp',
					data : {num : num},
					success: function(data) {
						alert("삭제 되었습니다.");
						regionCommentList(pageNum);
					},
					error: function(xhr, status, error) {
						alert(error);
				    }
				});
			}
		}
		
	</script>

<div class="clear"></div>

<!-- Footer -->
<jsp:include page="../inc/footer.jsp" />
