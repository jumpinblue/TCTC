
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

<%
//한글처리
request.setCharacterEncoding("utf-8");
// 파라미터 가져오기
// 문자열 => 정수형  
// int age =  Integer.parseInt(파라미터 가져오기)

String lat=request.getParameter("latitude");
String lng=request.getParameter("longitude");


// p: 명소(place)  r:맛집(restaurent), h: 호텔(hotel)
	 String type =request.getParameter("type");	 
	 String country_code=request.getParameter("country_code");
	 String city_code=request.getParameter("city_code");
	 String name=request.getParameter("name");
	 float latitude=Float.parseFloat(lat.trim());
	 float longitude=Float.parseFloat(lng.trim());
	 String info=request.getParameter("info");
	 String address=request.getParameter("address");
	
	 out.println(request.getParameter("latitude"));
	 out.println(latitude);
	 out.println(lat);

//자바빈 파일에 담아서 디비작업파일로  파라미터 넘김
//자바파일  패키지  member 자바파일 MemberBean

//자바빈 객체생성=> 생성자호출 => 기억공간할당,초기값 할당  mb
/* TravelDataBean td=new TravelDataBean();
td.setType(type);
td.setCountry_code(country_code);
td.setCity_code(city_code);
td.setName(name);
td.setLatitude(latitude);
td.setLongitude(longitude); 
td.setInfo(info);
td.setAddress(address); */

%>
<%-- <jsp:useBean id="tdb" class="TravelData.TravelDataBean"/> --%>
<jsp:setProperty property="*" name="tdb"/>
<%
//디비작업
//자바파일  패키지  member 자바파일 MemberDAO

//디비 객체생성=> 생성자호출 => 기억공간할당,초기값 할당  mdao
//TravelDataDAO tdao=new TravelDataDAO();

// 회원가입하는 insertMember(자바빈의 주소값) 메서드 호출
//tdao.insertTravel(tdb);
%>
<script type="text/javascript">
	alert("DB저장 성공");
/* 	location.href="TravelDataFrom.jsp"; */
</script>
</body>
</html>



