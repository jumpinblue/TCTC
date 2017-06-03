<%@page import="java.util.Calendar"%>
<%@page import="net.Board1.db.BoardBean"%>
<%@page import="net.member.db.MemberBean"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!-- Header -->
<jsp:include page="../inc/header.jsp" />
<section class="wrapper" style="padding:0 ;">
<!-- Header ph -->

<head>

<script src="http://code.jquery.com/jquery-1.10.2.js"></script>
<link href="assets/css/list.css?ver=121" rel="stylesheet" type="text/css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
</head>

<body class="fadein">

	<!-- 버튼 : 위로 -->
	<button type="button" class="btn_up_layer">
		<img src="./board/rrr6.png">
	</button>

	<script type="text/javascript">
		/* 위로
		 스크롤이 특정 위치로 이동하면 위로버튼이 나타난다.
		 위로버튼을 클릭하면 상단으로 이동
		 */
		function btn_mv_up(oj) {
			if (!oj)
				return false;
			var o = $(oj);
			var p = $(window).scrollTop();
			if (p > 300) {
				o.fadeIn('slow');
			} // 위로버튼이 나타나는 위치 지정
			else if (p < 300) {
				o.fadeOut('slow');
			} // 위로버튼을 숨기는 위치 지정
		}

		// 위로 버튼
		$(document).scroll(function() {
			btn_mv_up('.btn_up_layer');
		}).on('click', '.btn_up_layer', function() {
			$("html, body").animate({
				scrollTop : 0
			}, 'slow');
		});
	</script>



<!-- giolocation 위치값 받기. 모바일환경에서도 작동. -->
<script type="text/javascript">  
  if (!!navigator.geolocation) 
  {
    navigator.geolocation.getCurrentPosition(successCallback,errorCallback);  
  }
  else
  {
    alert("이 브라우저는 Geolocation를 지원하지 않습니다");
  }
    
  function successCallback(position)
  {
    var lat = position.coords.latitude;
    var lng = position.coords.longitude;
    
    document.getElementById("location").value = lat + ", "+lng;
  }
  
  function errorCallback(error)
  {
    alert(error.message);
  }
</script>
<!-- giolocation 위치값 받기 끝-->
	

	<div class="q">
		<%
			// request.setAttribute("boardList", boardList);
			// request.setAttribute("pageNum", pageNum);
			// request.setAttribute("count", count);
			// request.setAttribute("pageCount", pageCount);
			// request.setAttribute("pageBlock", pageBlock);
			// request.setAttribute("startPage", startPage);
			// request.setAttribute("endPage", endPage);
			String id = (String) session.getAttribute("id");
			List boardList = (List) request.getAttribute("boardList");
			String pageNum = (String) request.getAttribute("pageNum");
			int count = ((Integer) request.getAttribute("count")).intValue();
			int pageCount = ((Integer) request.getAttribute("pageCount")).intValue();
			int pageBlock = ((Integer) request.getAttribute("pageBlock")).intValue();
			int startPage = ((Integer) request.getAttribute("startPage")).intValue();
			int endPage = ((Integer) request.getAttribute("endPage")).intValue();

			//jsp 날짜 구하기
			Calendar cal = Calendar.getInstance();
			//jsp 날짜 구하기 끝.
			
			
			
			if(id!=null){
		%>
		
		<form method="post" action="./BoardWrite1.bb" class="write">
			<input type="hidden" id="location" name="location" value="aaa">
			<input type="submit" value="글쓰기"  id="mit">
		</form>
		<%} %>
		
		<div class="w">
			<%
				//  boardList 

				MemberBean mb = new MemberBean();

				for (int i = 0; i < boardList.size(); i++) {
					//자바빈(BoardBean) 변수 =배열한칸 접근   배열변수.get()
					BoardBean bb = (BoardBean) boardList.get(i);
					
					String content=bb.getContent();
					if(content!=null){
					content=bb.getContent().replace("\r\n", "<br>");
					}
					
			%>


			<div class="e" id="<%=i%>">

				<!--  제목 -->
				<div id="sub">
					<%=bb.getSubject()%>
				</div>

				<!-- 내용 -->
				<div id="con">
					<%=content%><br>
<%-- 					현재 위치 <%=bb.getLocation() %> --%>
				</div>

<!-- 					<div class="clear"> -->
					
				<div id="2_inner_left" style="width: 30%;">
					<!-- 프로필 -->
					<div id="file">
						<img src="./upload/images/profileImg/basic/man.png">
						<%-- 					<img src="./upload/images/profileImg/<%=mb.getProfile()%>"> --%>
					</div>

					<!-- 닉네임,날짜 -->
					<div id="nick">
						<%=bb.getNick_name()%>
					</div>

					<!--  날짜 -->
					<!-- 현재 날짜와 글이 작성된 날짜를 비교해서, 언제 쓴 글인지 알기 쉽도록 한다. -->
					<div id="date">
						<%
							if (cal.get(Calendar.YEAR) == bb.getDate().getYear() + 1900) {
								if (cal.get(Calendar.MONTH) == bb.getDate().getMonth()) {
									if (cal.get(Calendar.DATE) == bb.getDate().getDate()) {
											
						%>
										<%=cal.get(Calendar.HOUR) - bb.getDate().getHours()%>시간전
						<%
									} else {
						%>
										<%=cal.get(Calendar.DATE) - bb.getDate().getDate()%>일전
						<%
									}
								} else {
						%>
									<%=cal.get(Calendar.MONTH) - bb.getDate().getMonth()%>달전
						<%
								}				
							} else {
						%>
								<%=bb.getDate().getYear() + 1900%>년 <br>
								<%=bb.getDate().getMonth()%>월
								<%=bb.getDate().getDate()%>일
								<%=bb.getDate().getHours()%>시
																
						<%
							}
						%>
					</div>
					<!-- 날짜 끝. -->
					
					<div class="if">
					<!-- 수현씨 지도 부분 -->
					<%if(bb.getLocation() != null && !bb.getLocation().equals("null")) {%>
					
				<iframe width="100%" height="200px" frameborder="0" style="border: 0; margin:auto;"
					src="https://www.google.com/maps/embed/v1/place?key=AIzaSyAwZMwcmxMBI0VQAUkusmqbMVHy-b4FuKQ&q=<%=bb.getLocation()%>" allowfullscreen>
				</iframe>
				<%} %>
				<!-- 수현씨 지도 부분 끝 -->
			</div>
			
					
				</div>
<div class="upde">				
<input type="button" value="글수정"
onclick="location.href='./BoardUpdate1.bb?num=<%=bb.getNum()%>&pageNum=<%=pageNum%>'" class="up">
<input type="button" value="글삭제"
onclick="location.href='./BoardDelete1.bb?num=<%=bb.getNum()%>&pageNum=<%=pageNum%>'" class="de">
</div>
				
			<div class="t">
			qe
			</div>

			</div>

			
	<script type="text/javascript"> 
// 		댓글 기능(->슬라이드)
		 $(function(){
			$(".btn").click(function(){
				var effect = 'slide';
				var options ='left';
				var duration = 500;
			$('#pln_list').toggle(effect, options, duration);
			}); 
		});  
		//일정수정 버튼 클릭시 오른쪽으로 슬라이드  
 		 $(function(){
			 //datepicker 한국어로 사용하기 위한 언어설정
		   	$.datepicker.setDefaults($.datepicker.regional['ko']); 
		 });
		</script>
 
			<%
				}
				
					
				
			%>

		</div>
		
		

	</div>
		
<script type="text/javascript">
$(document).ready(function (event){
	$(window).scroll(function (){

		var scrollHeight = $(window).scrollTop() + $(window).height();
		var documentHeight = $(document).height();

		// 스크롤이 맨 아래로 갔는지 아닌지 확인
		if(scrollHeight == documentHeight){
			for(var i=0; i<5; i++){
				$('<h1>scroll2_'+i+'_</h1>').appendTo('body');
			}
		}
	});
});

</script>

</body>
<%if(id!=null){ %>

	<div id="chat"></div>
 
		<script type="text/javascript">
		// 채팅을 불러온다.
		$(window).load(function() {
			$.ajax({
				type: 'post',
				url: './Chat.ct',
				success: function(data) {
					$('#chat').append(data);
				},
				error: function(xhr, status, error) {
        			alert(error);
    			}   
			});
		});

 		</script>
		<%} %>
