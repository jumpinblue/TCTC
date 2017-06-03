<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- <link rel="stylesheet" href="assets/css/myplan/payPlanC.css" /> -->
<%
	int i = Integer.parseInt(request.getParameter("i"));
%>

<%
	if (i == 1) {
%>
<div class="pay_way_div">
	<div class="pay_way_title">신용카드</div>
	<table>
		<tr>
			<th>카드선택</th>
			<td><select name="card_choice" class="c_choice">
					<option value="">카드종류를 선택해주세요.</option>
					<option value="bc">비씨카드</option>
					<option value="kb">국민카드</option>
					<option value="nh">농협카드</option>
			</select></td>
		</tr>

		<tr>
			<th>할부선택</th>
			<td><select name="card_choice" class="c_choice">
					<option value="">일시불</option>
					<option value="month">3개월</option>
					<option value="year">3년</option>
					<option value="light-year">3광년</option>
			</select></td>
		</tr>
	</table>
</div>
<%
	} else if (i == 2) {
%>
<div class="pay_way_div">
	<div class="pay_way_title">무통장</div>
	<table>
		<tr>
			<th>은행선택</th>
			<td><select name="bank_choice" class="bank_choice">
					<option value="">카드종류를 선택해주세요.</option>
					<option value="bc">비씨카드</option>
					<option value="kb">국민카드</option>
					<option value="nh">농협카드</option>
			</select><br>(예금주 : 박성우 국민은행 9 5035 6077 66)
			
			</td>
		</tr>

		<tr>
			<th>입금자</th>
			<td>
				<input type="text" name="deposit_name">
			</td>
		</tr>
	</table>
</div>
<%
	} else if (i == 3) {
%>
<div class="pay_way_div">
	<div class="pay_way_title">휴대폰 결제</div>
	구매하기 버튼을 누르면 결제가 진행됩니다.
</div>
<%
	} else if (i == 4) {
%>
<div class="pay_way_div">
	<div class="pay_way_title">믿음</div>
	운영자에게 직접 현금으로 전달하였을 경우에만 선택해주세요.
</div>
<%
	}
%>