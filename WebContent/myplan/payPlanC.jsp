<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!-- Header -->
<jsp:include page="../inc/header.jsp" />

<div style="max-width: 1080px; margin: auto;">



<!-- Banner -->
<section id="banner">

	<!-- 입력란 들어갈 곳. -->

	<link rel="stylesheet" href="assets/css/myplan/payPlanC.css" />
	<link rel="stylesheet" href="assets/css/main.css" />
	<script src="assets/js/jquery.min.js"></script>

	<%
		String id = (String) session.getAttribute("id");
		String nick = (String) session.getAttribute("nick");
		String tel = (String) request.getAttribute("tel");
		String name = (String) request.getAttribute("name");

		int gold_price = 97000;
		int discount_price = 0;
		int send_fee = 3000;
	%>

	<!-- 결제 수단 선택 하면 동작할 함수 -->
	<script type="text/javascript">
		var i=0;
		var agr=0;
		
		function payway_change() {
								
			if(rdb1.checked){
				i=1;
			} else if(rdb2.checked){
				i=2;
			} else if(rdb3.checked){
				i=3;
			} else if(rdb4.checked){
				i=4;
			}
			
			$.ajax({
				type:'post',
				url:'./myplan/payWayList.jsp',
				data : {i:i},
				success : function(data){
					$('.pay_way_form').empty();
					$('.pay_way_form').append(data);
				}
			});
		}
						
		function agree_check(){
						
			if(agree.checked){
				agr=1;
			} else{
				agr=0;
			}
		}
		
		function pay(){
			if(agr==1 && i==4){
				
				location.href='./PayAction.pln?id=<%=id%>&approval=1';
			} else if (agr != 1) {
				alert("구매 동의버튼을 선택해주세요.");
			} else if (agr == 1 && i == 0) {
				alert("결제 방법을 선택해 주세요.")
			} else if (agr == 1 && i != 4) {
				alert("선택하신 결제 방법은 현재 사용하실 수 없습니다. 믿음을 선택해주세요.");
			}
		}
	</script>
	<!-- 결제 수단 선택 하면 동작할 함수 끝-->


	<h2>결제</h2>
	<form action="./PayAction.pln" method="post">
		<!-- 왼쪽 배송 장소 및 수단 선택 공간. -->

		<div>
			<h4>결제하실 정보</h4>
			<table>
				<tr>
					<th>선택사항</th>
					<th>상품금액</th>
					<th>수량</th>
					<th>할인</th>
					<th>할인금액</th>
					<th>주문금액</th>
					<th>배송정보</th>
				</tr>
				<tr>
					<td>일정C</td>
					<td><%=gold_price%>원</td>
					<td>1</td>
					<td>-</td>
					<td><%=discount_price%>원</td>
					<td><b><%=gold_price - discount_price%>원</b></td>
					<td><%=send_fee%>원</td>
				</tr>
			</table>
			<br>

			<h4>배송 정보</h4>
			<table>
				<tr>
					<th>주문하시는 분</th>
					<td>
						<table class="innerTable">
							<tr>
								<td>이름</td>
								<td><input type="text" name="name" value="<%=name%>"
									readonly="readonly"></td>
							</tr>
							<tr>
								<td>이메일</td>
								<td><input type="text" name="email" value="<%=id%>"
									readonly="readonly"></td>
							</tr>
							<tr>
								<td>휴대폰 번호</td>
								<td><input type="text" name="phone_number"
									value="<%=tel%>" readonly="readonly"></td>
							</tr>
						</table>
					</td>
				</tr>

				<tr>
					<th>받으시는 분</th>
					<td>
						<table class="innerTable">
							<tr>
								<td>이름</td>
								<td><input type="text" name="name"></td>
							</tr>
							<tr>
								<td>휴대폰 번호</td>
								<td><input type="text" name="email"></td>
							</tr>
							<tr>
								<td>받으실 주소</td>
								<td><input type="text" name="address"
									placeholder="배송을 받는 상품이 아닙니다."></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<th>배송메시지</th>
					<td><input type="text" name="message"
						placeholder="배송비가 이상하다면 글을 남겨주세요."></td>
				</tr>
				<tr>
					<th>총배송비</th>
					<td><%=send_fee%>원</td>
				</tr>
			</table>

			<h4>할인혜택</h4>
			현재 적용 가능한 할인혜택이 없습니다. <br> <br> <br>

			<h4>결제수단</h4>

			<div class="pay_way">
				<div class="pay_way_radio 4u 12u$(xsmall)"
					onchange="payway_change()">
					<!-- radio 버튼으로 구현하고 하는 결제수단 선택 -->

					<input id="rdb1" type="radio" name="p_w" /><label for="rdb1">신용카드</label><br>
					<input id="rdb2" type="radio" name="p_w" /><label for="rdb2">무통장</label><br>
					<input id="rdb3" type="radio" name="p_w" /><label for="rdb3">휴대폰
						결제</label><br> <input id="rdb4" type="radio" name="p_w" /><label
						for="rdb4">믿음</label>

				</div>
				<!-- radio 버튼으로 구현하고 하는 결제수단 선택 끝-->

				<!-- 결제수단에서 선택하면 나올 공간 -->
				<div class="pay_way_form" style="height: 300px;"></div>
				<!-- 결제수단에서 선택하면 나올 공간 끝 -->
			</div>
		</div>
	</form>
	<!-- 왼쪽 배송 장소 및 수단 선택 공간 끝.-->

	<div class="banner">
		<!-- fix 된 오른쪽 사이드 출력 -->
		<h4>최종 결제 정보</h4>

		<div class="result">

			<table>
				<tr>
					<th>상품금액</th>
					<td><%=gold_price%>원</td>
				</tr>
				<tr>
					<th>할인금액</th>
					<td><%=discount_price%>원</td>
				</tr>
				<tr>
					<th>배송비</th>
					<td><%=send_fee%>원</td>
				</tr>
				<tr>
					<th><span> 총 결제금액 </span></th>
					<td><span class="final_price"><%=gold_price - discount_price + send_fee%>원</span></td>
				</tr>

			</table>

		</div>

		<div class="agree_check">
			<input type="checkbox" name="agree" id="agree"
				onchange="agree_check()"><label for="agree">(필수)동의합니다.<br>
				주문할 상품의 상품명, 가격, 배송정보에 동의 하십니까?(전자상거래법 제8조 2항)
			</label>
		</div>

		<div class="popup_check">
			<span>확인해주세요!</span><br>
			설정에서 팝업차단을 해지했는지 확인해 주세요. 팝업창 차단이 설정되어
			있으면 결제가 진행되지 않습니다.
		</div>
		<a class="pay_button button spacial" onclick="pay()">구매하기</a>

	</div>
	<!-- fix 된 오른쪽 사이드 출력 끝-->

	<!-- 입력란 들어갈 곳. 끝. -->
</section>


</div>
<!-- Footer -->
<jsp:include page="../inc/footer.jsp" />