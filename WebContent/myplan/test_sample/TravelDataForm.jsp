<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

</head>
<body>

<h1>여행장소 db 입력용 임시 페이지</h1>

<form action="TravelDataPro.jsp" method="post" name="fr">

<select id="type"  name="type">
  <option value="">장소타입 선택</option>
  <option value="">------------</option>
  <option value="p">명소(place)</option>
  <option value="r">맛집(restaurant)</option>
  <option value="h">호텔(hotel)</option>
</select>
&nbsp;&nbsp;&nbsp;&nbsp;

<select id="country_code" name="country_code">

  <option value="">국가 선택</option>
  <option value="">------------</option>
  <option value="kr">한국(kr)</option>
  <option value="jp">일본(jp)</option>
  <option value="ph">필리핀(ph)</option>
</select>
&nbsp;&nbsp;&nbsp;&nbsp;

<select id="city_code" name="city_code">

  <option value="">도시 선택</option>
  <option value="">------------</option>
  <option value="seoul" class="kr">서울(seoul)</option>
  <option value="busan" class="kr">부산(busan)</option>
  <option value="jeju" class="kr">제주(jeju)</option>

  <option value="tokyo" class="jp">도쿄</option>
  <option value="osaca" class="jp">오사카</option>
  <option value="okinawa" class="jp">오키나와</option>

  <option value="manila" class="ph">마닐라</option>
  <option value="cebu" class="ph">세부</option>
  <option value="palawan" class="ph">팔라완</option>
</select>
<br><br>

 <script type="text/javascript" src="http://code.jquery.com/jquery-latest.min.js"></script>
 <script type="text/javascript">

(function($){$.fn.chained=function(parent_selector,options){return this.each(function(){var self=this;var backup=$(self).clone();$(parent_selector).each(function(){$(this).bind("change",function(){$(self).html(backup.html());var selected="";$(parent_selector).each(function(){selected+="\\"+$(":selected",this).val();});selected=selected.substr(1);var first=$(parent_selector).first();var selected_first=$(":selected",first).val();$("option",self).each(function(){if(!$(this).hasClass(selected)&&!$(this).hasClass(selected_first)&&$(this).val()!==""){$(this).remove();}});if(1==$("option",self).size()&&$(self).val()===""){$(self).attr("disabled","disabled");}else{$(self).removeAttr("disabled");}

$(self).trigger("change");});if(!$("option:selected",this).length){$("option",this).first().attr("selected","selected");}

$(this).trigger("change");});});};$.fn.chainedTo=$.fn.chained;})(jQuery);

$(function(){
$("#city_code").chained("#country_code");
});
</script>

<!-- 타입 - p(명소),r(맛집),h(호텔) 3개중에(p,r,h 중) 하나만 넣으세요 :<input type="text" name="type"><br>
국가코드(kr, jp, ph) 3개중에 하나만 넣으세요:<input type="text" name="country_code"><br>
도시코드(seoul, busan, tokyo, osaca, manila, cebu) 6개중에 하나만 넣으세요 :<input type="text" name="city_code"><br> -->
명칭(ex 조선비치호텔) : <input type="text" name="name"><br>

lat : <input type="text" name="latitude"><br>
lng : <input type="text" name="longitude"><br> 
lat,lng 값은 <a href="https://www.google.co.kr/maps" target="blank">구글맵</a>에서 특정 장소 검색후 <br>
marker 가 뜨면 마우스 우클릭하여 "이곳이 궁금한가요?" 클릭하면 lat, lng 정보 확인가능<br>
화면 좌측에 해당장소의 주소 복사해서 아래 주소칸에 넣으세요<br>
 
장소소개 : <input type="text" name="info"><br>
주소 : <input type="text" name="address"><br>






<input type="submit" value="정보저장">
</form>
</body>
</html>
