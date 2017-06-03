
<%@page import="com.sun.xml.internal.bind.v2.runtime.Location"%>
<%@page import="net.travel.admin.db.TravelBean"%>
<%@page import="net.myplanBasket.db.MyPlanBasketBean"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<!-- Header -->
<jsp:include page="../inc/header.jsp" />
<!-- 스타일 불러오기 -->
<link rel="stylesheet" href="assets/css/main.css" />
<link rel="stylesheet" href="assets/css/map/myplanNew.css" />
<link rel="stylesheet" href="assets/css/myplan/pay_button.css" />
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" href="/resources/demos/style.css">
<!-- 드래그 삽입 시작 -->		
<!--   <link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css"> -->
<!--   <script src="//code.jquery.com/jquery-1.10.2.js"></script> -->
  <!-- <script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
  <link rel="stylesheet" href="/resources/demos/style.css"> -->
  <!-- 드래그 삽입 종료 -->		
<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script type="text/javascript" src="http://code.jquery.com/jquery-2.1.0.min.js" ></script>
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script src="http://code.jquery.com/jquery-1.11.0.min.js"></script>
<script src="http://code.jquery.com/ui/1.11.4/jquery-ui.min.js"></script>
<!-- datepicker 한국어로 -->
<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/i18n/datepicker-ko.js"></script>
<script>
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
    // 시작일(fromDate)은 종료일(toDate) 이후 날짜 선택 불가
    // 종료일(toDate)은 시작일(fromDate) 이전 날짜 선택 불가

	//시작일
	$('#fromDate').datepicker({
		showOn: "focus",
		dateFormat:"yy-mm-dd",
		changeMonth:true,
		onClose: function(selectedDate){
            // 시작일(fromDate) datepicker가 닫힐때
            // 종료일(toDate)의 선택할수있는 최소 날짜(minDate)를 선택한 시작일로 지정
            $("#toDate").datepicker( "option", "minDate", selectedDate );
		}
	});

    
    //종료일
    $('#toDate').datepicker({
   	 	 showOn: "focus", 
         dateFormat: "yy-mm-dd",
         changeMonth: true,
         onClose: function( selectedDate ) {
             // 종료일(toDate) datepicker가 닫힐때
             // 시작일(fromDate)의 선택할수있는 최대 날짜(maxDate)를 선택한 종료일로 지정 
             $("#fromDate").datepicker( "option", "maxDate", selectedDate );
         } 
   	});
	/* $( '.datepicker').datepicker({
		altField: ".selecter"
	}); */
});  
//날짜 선택하기 
</script>


<!-- 드래그 삽입 시작 -->		
 

<!--   <style type="text/css" >
  	 body { font-size:12px; }
  
  #bestseller_books tr { cursor:pointer; }
  #bestseller_books tr:hover { background:rgba(244,251,17,0.45); }
  #bestseller_books th { height:30px; }
  #bestseller_books td { height:30px; }
  </style>
  
  
  
  <Script language="javascript" type="application/javascript">
	
	$(document).ready(function() {     //Helper function to keep table row from collapsing when being sorted     
		var fixHelperModified = function(e, tr) {         
			var $originals = tr.children();         
			var $helper = tr.clone(); 
			$helper.children().each(function(index) {
				$(this).width($originals.eq(index).width()) 
		});
		return $helper;
	};
	
	
	//Make diagnosis table sortable
	$("#bestseller_books tbody").sortable({
		helper: fixHelperModified, stop: function(event,ui) {
			renumber_table('#diagnosis_list')}
   }).disableSelection(); 
   
	
	//Delete button in table rows     
   $('table').on('click','.btn-delete',function() {  
      tableID = '#' + $(this).closest('table').attr('id'); 
	  r = confirm('Delete this item?');
	  if(r) { 
	    $(this).closest('tr').remove();
		renumber_table(tableID); 
	  } 
	});
 }); 
	
	
	
 //Renumber table rows 
 	
	function renumber_table(tableID) {     
		$(tableID + " tr").each(function() {
			count = $(this).parent().children().index($(this)) + 1;
			$(this).find('.priority').html(count);
	     });
	 }
  </script>
		
	 -->	
<!-- 드래그 삽입 종료-->
		
		
		
		
		

</head>
<body>
	<%
		String gold = (String) request.getAttribute("gold");
		String id = (String) session.getAttribute("id");

		List basketList = (List) request.getAttribute("basketList"); //여행지찜리스트 myplans List
		List goodsList = (List) request.getAttribute("goodsList"); //상품 travel List
		int plan_nr = Integer.parseInt(request.getParameter("plan_nr")); //일정 종류
		

		String dep_lat =  (String) request.getParameter("dlat"); //경로 표시를 위한 출발장소의 lat 값
		String dep_lng =  (String) request.getParameter("dlng"); //경로 표시를 위한 출발장소의 lnt 값
		String arr_lat =  (String) request.getParameter("alat"); //경로 표시를 위한 도착장소의 lat 값
		String arr_lng =  (String) request.getParameter("alng"); //경로 표시를 위한 도착장소의 lnt 값

 		int plan_item_nr=0;  // 일정종류별 방문지 갯수 계산을 위함
 		int j=0;  //일정목록 넘버링을 위함
	%>

	<div class="container" >
		<div class="myplan-list" >


				&nbsp;&nbsp;&nbsp;&nbsp;<a href='./MyPlan.pln?plan_nr=100'"><img src="./images/myplans/zzim.png" width="50px" height="50px"style="vertical-align:bottom"></a>&nbsp;&nbsp; <!-- 여행지찜리스트 전체 목록 -->
				<a href='./MyPlan.pln?plan_nr=1'"><img src="./images/myplans/plan_a.png" width="50px" height="50px" style="vertical-align:bottom"></a> <!--  일정1 -->
				<a href='./MyPlan.pln?plan_nr=2'"><img src="./images/myplans/plan_b.png" width="50px" height="50px" style="vertical-align:bottom"></a> <!--  일정2 -->
				<a href='./MyPlan.pln?plan_nr=3'"><img src="./images/myplans/plan_c.png" width="50px" height="50px" style="vertical-align:bottom"></a> &nbsp;&nbsp;<!--  일정3 -->

				<!-- onclick = "location.href ='./MyPlan.pln?plan_nr=100'" modify해결하고 jqeury로 펼치기 설정 -->
				<img class="btn"  src="./images/myplans/make.png" width="50px" height="50px" style="vertical-align:bottom"> <!-- 일정만들기버튼 -->
				<!-- <button class="btn" >일정만들기</button> -->
				<hr>

				<table border="1"  class="table" id="bestseller_books">  <!-- 여행지찜리스트 목록 및 일정별 목록 -->
					<thead>
					<tr>
						<!-- <th>plan</th>
						<th>item</th> -->
						<th width="100px" align="center">
							<% // 전체 목록표시와 세부 일정일때 테이블 제목 각기 다르게 표시
							if(plan_nr==100)%>찜갯수 <%
							else %> 순서</th>
						<th width="400px" align="center">
							<%// 전체 목록표시와 세부 일정일때 테이블 제목 각기 다르게 표시
							if(plan_nr==100)%>찜목록 <%
							else %> 방문지</th>
						<% /* 전체목록 표시(plan_nr=100)할 때에는 경로 column 숨기기 */
						if(plan_nr!=100){
							%>	
						<th width="100px">경로</th>
						<%
						}
						%>
					</tr>
					<thead>
					<%
					/* 일정별 방문지 갯수 계산을 위한 for문 */ 
 					for (int i = 0; i < basketList.size(); i++) {  
						MyPlanBasketBean mpbb = (MyPlanBasketBean) basketList.get(i);
						/* 일정종류(plan_nr) 가 여행지찜리스트의 일정종류와 다를 때에는 갯수 계산 안함. 
						일정종류(plan_nr) 가 목록표시일 경우에도 갯수 계산 안함 */
						if (plan_nr != mpbb.getPlan_nr() & plan_nr != 100)    
							continue;
						plan_item_nr=plan_item_nr+1;    	 						
					} /* 선택한 일정의 방문지 갯수 계산 완료 */
					
					
					int button_nr=0; //경로 표시 버튼의 각  id 값 생성을 위한 초기 값(for문)
					float[][] route=new float[plan_item_nr][2];  // 두 지점간의 경로표시를 위해 for 문내에서(출발지의 lat, lng 도착지의 lat, lng 값)을 2차원 배열에 저장
					
					
					/* 전체 방문지 찜목록, 일정별 방문지 목록 생성을 위한 for문 */
					for (int i = 0; i < basketList.size(); i++) {
							MyPlanBasketBean mpbb = (MyPlanBasketBean) basketList.get(i); /* 찜목록 DB Bean */ 
							TravelBean tb = (TravelBean) goodsList.get(i); /*  여행지(상품) DB Bean */
					
							/* 여행지찜리스트에서 선택한 일정종류(plan_nr) 와 일정종류가 다를 땐, 목록테이블 만들지 않고 pass. 
							일정종류(plan_nr) 가 목록표시(100)일 경우에도  목록테이블 만들지 않고 pass????????? */
							if (plan_nr != mpbb.getPlan_nr() & plan_nr != 100)
								continue;
					%>
					 <tbody>
					<tr>
<%-- 						<td><%=mpbb.getPlan_nr()%></td>  <!-- 일정 종류 표시, 전체 목록표시때에만 표시 -->
						<td class='priority'><%=mpbb.getItem_nr()%></td> <!--  1~ 값( for문의 i 값 으로 변경 예정 --> --%>
						<td align="center"><%=++j%></td>   <!-- 일정 목록 넘버링 -->
						<td><%=tb.getName()%></td> <!-- 일정별 찜한 여행지명 출력-->
						
						<%
						if(plan_nr!=100){ /* 전체목록표시 아닐때에만 경로표시 버튼 생성 */
							%>
						<td>

								<% /* 일정별 방문지 목록에서 각 여행지의 lat, lnt 값을  이차원 배열의 각 행에 저장 */
									route[button_nr][0] = tb.getLatitude();  
									route[button_nr][1] = tb.getLongitude();
									if(button_nr!=0){  /* 첫번째 방문지(button_nr=0)에는 경로버튼 표시하지 않음 */
									%>	
									
										<!-- 경로표시 버튼 눌렀을 때, plan_nr 과, 출발지 lat, lng, 도착지 lat, lng 값을 다음페이지(경로표시페이지)에 넘겨줌 -->
										<a href='./MyPlan.pln?plan_nr=<%=plan_nr%>&dlat=<%=route[button_nr-1][0]%>&dlng=<%=route[button_nr-1][1]%>&alat=<%=route[button_nr][0]%>&alng=<%=route[button_nr][1]%>'>
				  						<!-- 경로표시 버튼 이미지 -->
				  						<img src="./images/myplans/bus_trans.png" width="30px" height="30px" style="vertical-align:bottom"> </a> 
		  						<%
		  							}
									button_nr=button_nr+1; /* 경로표시 버튼 넘버링 */			
		  						%>

						</td>
						<%
						}
						%>
					</tr>	
						<%
						}

					%>
					</tbody>

				</table>

			
			<% /* 찜한 방문지가 없을 때 안내멘트 표시 */
				if (basketList.size() == 0) {
					%>				
			아직 일정을 추가하지 않으셨군요! <br> 여행지를 검색해서 찜해보세요. <br> <br>
			여행지가 추가되면서,<br> 일정별로 방문순서를 계획할 수 있어요<br> <br> 또한 여행경로도
			확인 가능하다는 사실!<br> 그 놀라운 서비스를 확인하러 Go~ Go~<br>
			<%
				}
			%>
		</div>
		<div id="map" class="f1" ></div><!-- map -->		
		<div id="pln_list"><!-- 일정수정 버튼 시 오른쪽 슬라이드 시작 -->
			<form id="pln_form" action="./MyPlanModify.pln" method="post" name="fr">
				<input type="text" name="fromDate" id="fromDate" value="시작일"
					style="	background-image: url('myplan/pn_cal_btn.png');
							background-repeat: no-repeat;
							background-position: 110px 13px;"> 
				<input type="text" name="toDate" id="toDate" value="종료일"
					style="	background-image: url('myplan/pn_cal_btn.png');
							background-repeat: no-repeat;
							background-position: 110px 13px;">
				<select name="plan_nr">
					<option value="choice">여행 타이틀을 선택하세요</option>
					<option value="1" >일정A</option>
					<option value="2">일정B</option>
					<%if(gold.equals("유료회원")){ %>
						<option value="일정C">일정C</option>
					<%}%>
				</select>
				
					<%if(gold.equals("무료회원")){ %>
						<input type="submit" value="상세일정만들기" class="pln_sub_free">
						<input type="button" class="pln_sub_btn_free" onclick="location.href='./PayAction.pln?approval=0&id=<%=id %>'" value="일정C 사용하기">
					<%} else {%>
						<input type="submit" value="상세일정만들기" class="pln_sub">	
					<%} %>
				
			</form>  		
		</div><!-- 일정수정 버튼 시 오른쪽 슬라이드 시작-->

	<%if(plan_nr!=100) { %>
   	<div id="right-panel">    </div> <!-- 방문지간 대중교통 이동 정보 표시 패널 -->
<%
}%>
	</div><!-- container 끝 -->



	<script>
      var map;

      // Create a new blank array for all the listing markers.
      var markers = [];     //찜목록 마커들 표시를 위한 배열
      var TotalPath = []; // 전체 경로 표시를 위한 위치값 저장 배열 
     
      
      function initMap() {
       
        // 초기 셋팅
        map = new google.maps.Map(document.getElementById('map'), {

            center: {lat: 35.096706, lng: 129.03049},  //초기 임의의 센터값 입력 

          	zoom: 13,
         	mapTypeControl: false
        });

        
		var largeInfowindow = new google.maps.InfoWindow();
		var highlightedIcon = makeMarkerIcon('FFFF24');
		    
      					       

             <%
			String MarkerColor;

			String TitlePlan;
			String typeIcon;  // 여행지 type 별 아이콘 hotel, place, restaurant
			
			if (basketList.size() != 0) { //여행찜 목록이 존재할 때

				for (int i = 0; i < basketList.size(); i++) {  //여행찜 목록 만큼 for 문 실행
					MyPlanBasketBean mpbb = (MyPlanBasketBean) basketList.get(i);
					TravelBean tb = (TravelBean) goodsList.get(i);
						//목록보기 일때에는 마커생성 안함. 찜목록중에 일정별로 해당 목록표시
						if (plan_nr != mpbb.getPlan_nr() & plan_nr != 100) 
						continue;

						
						
					/* 일정별 마커 색깔 정의*/
/* 					switch (mpbb.getPlan_nr()) {
						case 1 :
							MarkerColor = "6799FF"; //light blue
							TitlePlan = "A";
							break;
						case 2 :
							MarkerColor = "F361DC"; //pink
							TitlePlan = "B";
							break;
						case 3 :
							MarkerColor = "8041D9"; //purple
							TitlePlan = "C";
							break;
						case 4 :
							MarkerColor = "47C83E"; //green
							TitlePlan = "D";
							break;
						default :
							MarkerColor = "F15F5F"; //red
							TitlePlan = "E";
							break;
					} */   /* switch 문 종료 */
					
					/* 일정별 마커 모양 정의*/
					switch (tb.getType()) {
						case "r" :
							typeIcon = "./images/myplans/restaurant.png";
							 TitlePlan = "A"; 
							break;
						case "h" :
							typeIcon = "./images/myplans/hotel.png";
							TitlePlan = "B"; 
							break;
						case "p" :
							typeIcon = "./images/myplans/place.png"; 
							TitlePlan = "C"; 
							break;
						default :
							typeIcon = "F15F5F"; //red
							TitlePlan = "D"; 
							break;
					}   /* switch 문 종료 */%> 

	      <%--   var defaultIcon = makeMarkerIcon('<%=MarkerColor%>'); --%>
	    

         	var lat = <%=tb.getLatitude()%>;
	    	var lng = <%=tb.getLongitude()%>;
	    	var position = new google.maps.LatLng(lat,lng); 

	    	var title = '일정<%=TitlePlan%>-'+'<%=i + 1%>'+'번째 방문지: '+'<%=tb.getName()%>';
			// Create a marker per location, and put into markers array.
			var marker = new google.maps.Marker({
				position : position,
				title : title,
				animation : google.maps.Animation.DROP,
				icon : {
            			url: '<%=typeIcon%>',   //full path of your image
            			//scaledSize: new google.maps.Size(40,60)//this control the size of your icon
        		},
				id :<%=i%>
				});

			// 마커를 마커 배열에 Push
			markers.push(marker);
			
			
			//일정별 초기 화면에서만 전체 경로 표시해줌
			<%
			    if(plan_nr!=100 & dep_lat==null & dep_lng==null & arr_lat==null & arr_lng==null){
			%>
						TotalPath.push(position);
			<%
		    			}
		     %>
			
			// onclick 이벤트 생성 (각 마커에서 large infowindow 를 열기 위함)
			marker.addListener('click', function() {
				populateInfoWindow(this, largeInfowindow);
			});
	<%} //for문 종료 (여행찜 목록 만큼 for 문 실행하여, 표시할 마커들 정보 생성)  %>
	
			showListings();  //마커가 저장된 배열을 실제로 표출시키고 bound 로 묶어서 적당한 zoom 을 발생하기 위함
			flightPath();
			 <%
			 //전체목록보기가 아니고, 출발지 도착지 lat lng 값 모두 null 이 아닐때 => 경로 표시
		      if(plan_nr!=100 & dep_lat!=null & dep_lng!=null & arr_lat!=null & arr_lng!=null){

						 	String route_dep = dep_lat + ", " + dep_lng;   // 출발지 lat lng 값 변수 하나로 저장
						 	String route_arr= arr_lat + ", " + arr_lng;  //도착지 lat lng 값 변수 하나로 저장
						 	%>
						 	
						 var directionsService = new google.maps.DirectionsService;  // 경로 표시 서비스
					     var directionsDisplay = new google.maps.DirectionsRenderer({   //경로 표시
										           draggable: true,
										           map: map,
										           panel: document.getElementById('right-panel')
										         });
								 
					       directionsDisplay.addListener('directions_changed', function() {
					           computeTotalDistance(directionsDisplay.getDirections());
					         });
				   			
					       displayRoute('<%=route_dep%>', '<%=route_arr%>', directionsService, directionsDisplay);
				
					       function displayRoute(origin, destination, service, display) {
					           service.route({
					             origin: origin,
					             destination: destination,
					            // waypoints: [{location: '35.158408, 129.062038'}]/*  국가별로 경유지 지원여부 달라서 구현하지 않음 */
					             travelMode: 'TRANSIT',  /* 대중교통표시 mode */
					             avoidTolls: true
					           }, function(response, status) {
					             if (status === 'OK') {
					               display.setDirections(response);
					             } else {
					               alert('다음의 이유로 경로를 표시할 수 없습니다: ' + status);
					             }
					           });
					         }
			
		<%} //경로표시 if문 
		      
		      
			}//if 문 (여행찜 목록이 존재할 때) 닫기 %>  
		}	//*******function initMap() 닫기*****
		
		
		
		
		function flightPath (){  //좌표 배열을 활용한 비행경로 함수 사용 
			var flightPath = new google.maps.Polyline({
		          path: TotalPath,  // 일정별 전체 이동경로
		          geodesic: true, 	//측지선?....음....
		          strokeColor: '#8041D9',   //경로 선 색상 초기값 '#FF0000'  
		          strokeOpacity: 0.7,   //불투명도
		          strokeWeight: 4    //선두께
		        });			
			flightPath.setMap(map);  //맵에 함수 결과 표시
		}
		
		// 마커 배열의 모든 아이템 표시하고 bound로 묶어서 zoom
		function showListings() {
			var bounds = new google.maps.LatLngBounds();  //LatLngBounds() 객체를 사용한 bounds 변수선언
			for (var i = 0; i < markers.length; i++) {  // 마커배열에 모든 정보를 지도에 하나씩 출력
				markers[i].setMap(map);			//맵에 배열i당 결과 표시
				bounds.extend(markers[i].position);  //bound로 확장
			}
			map.fitBounds(bounds);  //bound로 묶음 zoom
		}

		//populateInfoWindow 함수
		function populateInfoWindow(marker, infowindow) {
			
			// info윈도우가 해당 마커에 이미 열려있는지 확인
			if (infowindow.marker != marker) {
			
				// street 뷰 출력을 위해 info window 를 Clear
				infowindow.setContent('');
				infowindow.marker = marker;
				// 만약 info창이 닫혀져 있다면, 마커 특성이 clear 되었는지 확인.
				infowindow.addListener('closeclick', function() {
					infowindow.marker = null;
				});
				var streetViewService = new google.maps.StreetViewService();
				var radius = 50;
				// 상태가 OK 라면, 파노라마 뷰가 발견되었음을 의미. 
				// streetview 이미지의 위치를 산정하고, 방향을 계산하고, 그것으로 부터 파노라마를 얻은후 옵션값을 셋팅한다.
				
				function getStreetView(data, status) {
					if (status == google.maps.StreetViewStatus.OK) {
						var nearStreetViewLocation = data.location.latLng;
						var heading = google.maps.geometry.spherical
								.computeHeading(nearStreetViewLocation,
										marker.position);
						infowindow.setContent('<div>' + marker.title
								+ '</div><div id="pano"></div>');
						var panoramaOptions = {
							position : nearStreetViewLocation,
							pov : {
								heading : heading,
								pitch : 30
							}
						};
						var panorama = new google.maps.StreetViewPanorama(
								document.getElementById('pano'),
								panoramaOptions);
					} else {
						infowindow.setContent('<div>' + marker.title + '</div>'
								+ '<div>No Street View Found</div>');
					}
				}
				// 마커 위치에서 50 m 이내의 가장 가까운 streetview 이미지를 갖기 위해 streetview 서비스를 사용
				streetViewService.getPanoramaByLocation(marker.position,
						radius, getStreetView);
				// 마커에 infowindow 를 open 한다
				infowindow.open(map, marker);
			}
		}

		
		// 이 함수는 마커색상을 취하고 그 색상의 새로운 마커를 생성한다.  
		// 아이콘은 21px 폭, 34 높이가 될것임
		function makeMarkerIcon(markerColor) {
			var markerImage = new google.maps.MarkerImage(
					'http://chart.googleapis.com/chart?chst=d_map_spin&chld=1.15|0|'
							+ markerColor + '|40|_|%E2%80%A2',
					new google.maps.Size(21, 34), new google.maps.Point(0, 0),
					new google.maps.Point(10, 34), new google.maps.Size(21, 34));
			return markerImage;
		}
	</script>
	<script async defer
		src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAHAu8kwLgLcIk1oWIKpJhyOQQTK6RBLNI&v=3&callback=initMap">
	</script>

</body>


<div class="clear"></div>
<!-- Footer -->
<jsp:include page="../inc/footer.jsp" />
</html>