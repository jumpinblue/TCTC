
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
	} else {
		nick = "NULL";
	}
	
	String gold = (String)request.getAttribute("gold");
%>

<!-- 함께해요 게시판에 붙일예정. -->

<!-- (완)귓속말 : /닉네임 내용 -->

<!-- (완)textarea가 아닌 div 사용해서 카카오톡처럼 만들기. -->

<!-- 현재 접속자 알기. -->

<!-- 이모티콘 유료기능 구현하기. - action으로 갔다가, db에서 골드회원인지 검증하기. -->



<!-- onkeydown을 통해서 엔터키로도 입력되도록 설정. -->

<div id="messageWindow2" style="max-width:20em; padding:10px 0; height:35em; overflow: auto; background-color: rgba(255,215,247,0.89)"></div>

<%if(gold.equals("유료회원")){ %>
<div style="background-color: white; max-width: 20em;">
	<%for(int i=1; i<=9; i++){ %><!-- i값의 범위를 이모티콘의 개수로 한다. -->
			<img src='./images/chat/emoticon/emoticons_<%=i %>.png' onclick="emtc(<%=i %>)" style="width:2em; vertical-align: middle;">
	<%} %>
</div>
<%} else { %>
<div style="background-color: white; max-width: 20em;">
	<%for(int i=1; i<=9; i++){ %><!-- i값의 범위를 이모티콘의 개수로 한다. -->
			<img src='./images/chat/emoticon/emoticons_<%=i %>.png' onclick="emtc_none()" style="width:2em; vertical-align: middle;">
	<%} %>
</div>
<%} %>


<input id="inputMessage" type="text"
	onkeydown="if(event.keyCode==13){send();}" style="width:20em; background-color: white;" placeholder="Enter로 전송"/>
<!-- <input type="submit" value="send" onclick="send();"/> -->

<script type="text/javascript">
	
	//웹소켓 설정
	var webSocket = new WebSocket('ws://192.168.2.17:8080/TomCat/broadcasting');
	//var webSocket = new WebSocket('ws://localhost:8080/프로젝트명/broadcasting');
	
	//inputMessage를 선언.
	var inputMessage = document.getElementById('inputMessage');
	
	//같은 이가 여러번 보낼때 이름 판별할 변수
	var re_send = "";
	
	var gold = <%=gold%>;

	webSocket.onerror = function(event) {
		onError(event)
	};
	webSocket.onopen = function(event) {
		onOpen(event)
	};
	webSocket.onmessage = function(event) {
		onMessage(event)
	};

	//	OnClose는 웹 소켓이 끊겼을 때 동작하는 함수.
	function onClose(event){
		var div=document.createElement('div');
		
		//접속했을 때 접속자들에게 알릴 내용.
		webSocket.send("<%=nick%> is DisConnected\n");
		alert("test");
	}

	//	OnMessage는 클라이언트에서 서버 측으로 메시지를 보내면 호출되는 함수.
	function onMessage(event) {

		//클라이언트에서 날아온 메시지를 |\| 단위로 분리한다
		// id|\| 내용 |\|img
		var message = event.data.split("|\|");
		
		//message의 내용(message[1]을 말한다.)을 확인해서,
		//처음이 / 이면, 귓속말로 분류한다.
		var secret_chat = message[1].split(" ");
		if(secret_chat[0].substring(1,0) == "/"){
			
			//귓속말 조건 시작
			if(secret_chat[0] == "/<%=nick%>"){
				//귓속말을 받는 이가 본인일 경우
				
				//금방 보낸 이를 re_send에 저장하고,
				//금방 보낸 이가 연속해서 메시지를 보낼경우 보낸 이 출력 없도록 함.
				if(message[0] != re_send & message[2] != "입장"){
					//messageWindow2에 붙이기
					var who = document.createElement('div');

					who.style["padding"]="3px";
					who.style["margin-left"]="3px";

					who.innerHTML = message[0];
					document.getElementById('messageWindow2').appendChild(who);

					//	금방 보낸 사람을 임시 저장한다.
					re_send = message[0];
				}
				
				//div는 받은 메시지 출력할 공간.
				var div=document.createElement('div');
				
				div.style["max-width"]="15em";
				div.style["width"]="auto";
				div.style["word-wrap"]="break-word";
				div.style["display"]="inline-block";
				div.style["color"]="blue";

				//3번째 구분자가 존재 할 경우, background 제거
				if(message[2] == null){
					div.style["background-color"]="rgba(95,132,242,0.3)";
				}

				div.style["border-radius"]="3px";
				div.style["padding"]="3px";
				div.style["margin-left"]="3px";
				
				//secret_chat의 /nick부분을 제하고 출력.
				for(var i=1; i < secret_chat.length; i++){
					div.innerHTML += secret_chat[i] + " ";
				}//secret_chat의 /nick부분을 제하고 출력 끝.
				document.getElementById('messageWindow2').appendChild(div);

				//clear div 추가. 줄바꿈용.		
				var clear=document.createElement('div');
				clear.style["clear"]="both";
				document.getElementById('messageWindow2').appendChild(clear);
				
			} else {
				//귓속말을 받는 이가 아닐 경우 출력없음.
			}//귓속말 조건 끝.
			
			// 받은 문자의 처음이 /가 아닐 경우 일반 메시지로 처리.
		} else {
		
			//금방 보낸 이를 re_send에 저장하고,
			//금방 보낸 이가 연속해서 메시지를 보낼경우 보낸 이 출력 없도록 함.
			if(message[0] != re_send & message[2] != "입장"){
				//messageWindow2에 붙이기
				var who = document.createElement('div');

				who.style["padding"]="3px";
				who.style["margin-left"]="3px";

				who.innerHTML = message[0];
				document.getElementById('messageWindow2').appendChild(who);

				//	금방 보낸 사람을 임시 저장한다.
				re_send = message[0];
			}
			
			//div는 받은 메시지 출력할 공간.
			var div=document.createElement('div');
			
			//세번째 구분자에 입장이 있을경우 입장했다 문구 출력
			if(message[2] == "입장"){
				message[1] = message[0] + "님이 입장하셨습니다.";
				div.style["text-align"]="center";
				
			//세번째 구분자가 없을 경우 메시지 출력.
			} else {

				div.style["max-width"]="15em";
				div.style["width"]="auto";
				div.style["word-wrap"]="break-word";
				div.style["display"]="inline-block";

				//3번째 구분자가 존재 할 경우, background 제거
				if(message[2] == null){
					div.style["background-color"]="rgba(95,132,242,0.3)";
				}

				div.style["border-radius"]="3px";
				div.style["padding"]="3px";
				div.style["margin-left"]="3px";

			}//세번째 옵션 구분 끝.
			
			div.innerHTML = message[1];
			document.getElementById('messageWindow2').appendChild(div);

			//clear div 추가. 줄바꿈용.		
			var clear=document.createElement('div');
			clear.style["clear"]="both";
			document.getElementById('messageWindow2').appendChild(clear);
		
		}
		
		//div 스크롤 아래로.
		messageWindow2.scrollTop = messageWindow2.scrollHeight;
		
	}

	//	OnOpen은 서버 측에서 클라이언트와 웹 소켓 연결이 되었을 때 호출되는 함수.
	function onOpen(event) {
		
		//접속했을 때, 내 영역에 보이는 글.
		var div=document.createElement('div');
		
		div.style["text-align"]="center";
		
		div.innerHTML = "반갑습니다.";
		document.getElementById('messageWindow2').appendChild(div);
		
		var clear=document.createElement('div');
		clear.style["clear"]="both";
		document.getElementById('messageWindow2').appendChild(clear);
		
		//접속했을 때 접속자들에게 알릴 내용.
		webSocket.send("<%=nick%>|\| |\|입장");
	}

	//	OnError는 웹 소켓이 에러가 나면 발생을 하는 함수.
	function onError(event) {
		alert("chat_server connecting error " + event.data);
	}
	
	// send 함수를 통해서 웹소켓으로 메시지를 보낸다.
	function send() {

		//inputMessage가 있을때만 전송가능
		if(inputMessage.value!=""){
			
			//	서버에 보낼때 날아가는 값.
			webSocket.send("<%=nick%>|\|" + inputMessage.value);
			
			// 채팅화면 div에 붙일 내용
			var div=document.createElement('div');
			
			div.style["max-width"]="15em";
			div.style["width"]="auto";
			div.style["word-wrap"]="break-word";
			div.style["float"]="right";
			div.style["display"]="inline-block";
			div.style["background-color"]="rgba(255, 0, 0, 0.15)";
			div.style["padding"]="3px";
			div.style["border-radius"]="3px";
			div.style["margin-right"]="3px";

			//input message를 secret_msg에 넣어서 귓속말인지 판별하도록 한다.
			var secret_msg = (inputMessage.value).split(" ");
			//secret_msg 의 가장 앞에 /가 있다면, 귓속말.
			if(secret_msg[0].substring(1,0) == "/"){
				
				div.style["color"]="blue";
				
				//귓속말을 누구에게 보냈는지 확인가능하도록 한다.
				div.innerHTML = secret_msg[0].substring(1) + "님에게 "
				
				//secret_chat의 /nick부분을 제하고 출력.
				for(var i=1; i < secret_msg.length; i++){
					div.innerHTML += secret_msg[i] + " ";
				}//secret_chat의 /nick부분을 제하고 출력 끝.
				
				//귓속말을 보낼 경우에 inputMessage 대상에 연속성을 부여한다.
				inputMessage.value = secret_msg[0]+" ";
			} else { 
				//div에 innerHTML로 문자 넣기
				div.innerHTML = inputMessage.value;
				
				//	inputMessage의 value값을 지운다.
				inputMessage.value = '';
			}
			
			document.getElementById('messageWindow2').appendChild(div);

			//clear div 추가
			var clear = document.createElement('div');
			clear.style["clear"] = "both";
			document.getElementById('messageWindow2').appendChild(clear);
			
			//	textarea의 스크롤을 맨 밑으로 내린다.
			messageWindow2.scrollTop = messageWindow2.scrollHeight;
			
			//	금방 보낸 사람을 임시 저장한다.
			//	re_send의 값을 바꾸어 주어야, 자연스럽게 nick 출력.
			//	(주석처리하고 2인끼리 대화하면 알 수 있다.)
			re_send = "<%=nick%>";
			
		}//inputMessage가 있을때만 전송가능 끝.
		
	}
	
	//이모티콘 함수
	function emtc(i){
		
			var div = document.createElement('div');
		
			div.style["float"]="right";
			div.innerHTML = "<img src='./images/chat/emoticon/emoticons_"+i+ ".png' style='height:5em; margin-right:1em;'>";
			document.getElementById('messageWindow2').appendChild(div);
		
			//clear div 추가
			var clear = document.createElement('div');
			clear.style["clear"] = "both";
			document.getElementById('messageWindow2').appendChild(clear);
		
			//2번째 구분자 뒤에 img 단어를 넣는다.
			webSocket.send("<%=nick%>|\|<img src='./images/chat/emoticon/emoticons_"+i+ ".png' style='height:5em; margin-left:1em;'>|\|img");
			
			//div 스크롤 아래로.
			messageWindow2.scrollTop = messageWindow2.scrollHeight;

	}
	
	function emtc_none(){
		if (confirm("\n Gold 회원만 사용 가능한 기능입니다. \n\n 결제 화면으로 이동하시겠습니까?\n\n") == true){    //확인
			location.href="./PayAction.pln?approval=0&id=<%=id %>&url='chat'";
		}else{   //취소
		    return;
		}
	}
	
</script>

