
// 닉네임 중복확인 했는지 여부 판단해주는 변수, 0이면 안한상태 또는 다시입력한 상태, 1이면 사용 가능한 닉네임인 상태
var check = 0;

// 아이디(이메일) 인증번호 인증
function emailCheckNumber() {
	
	// 메일 전송하고 인증번호 폼에 초기화
	$(document).ready(function(){
		
		var id = $('#id').val();	// 사용자가 입력한 이메일주소(아이디)
		if(id=="") {
			alert("아이디(이메일)을 입력해주세요.");
			return;
		}

		$.ajax('./member/joinCheck/mailAuthentication.jsp?id='+id, {
					success: function(data) {
						if(data==-1) {
							alert("이미 사용중인 아이디 입니다.\n다른 아이디를 사용해 주세요.");
						}else {
							$('#randomNum').val(data);	// 인증번호(hidden) 폼에 인증번호를 넣음
							alert("인증번호 전송완료\n서버 상태에 따라 인증번호 도착이 늦어질 수 있습니다.");
						}	
					}
			});
	});
	
}

// 아이디(이메일) 재입력 시 다시 인증번호 받게 설정
function re_requestEmailCheck() {
	$(document).ready(function(){
		$('#randomNum').val('');
	});
}

// 닉네임 중복확인
function nickOverlapCheck() {
	
	$(document).ready(function(){
		
		// 닉네임 폼 입력 값 가져오기
		var nick = $('#nick').val();
		if(nick=="") {
			alert("닉네임을 입력해주세요.");
			return false;
		}

		// DB에서 중복확인
		$.ajax('./member/joinCheck/nickOverlapCheck.jsp?nick='+nick, {
			success: function(data) {
				if(data==-1) {
					alert("닉네임을 형식에 맞게 입력해주세요.");
					return false;
				}else if(data==0) {
					alert("이미 있는 닉네임 입니다.");
					return false;
				}else if(data==1) {
					alert("사용가능한 닉네임 입니다.");
					check = 1;
				}else {
					alert("닉네임 중복확인 오류");
					return false;
				}
			}
		});
	});
	
}

// 닉네임 재입력 시 다시 중복확인 하게 설정
function check_change() {
	check = 0;
}

// RSA암호화 및 제약조건
function validateEncryptedForm() {

    var id = document.fr.id.value;
    var emailCheckNumber = document.fr.email_check.value;										// 입력한 인증번호
    var certificationNumber = document.getElementById("randomNum").value;		// 이메일로 보낸 인증번호
    var pass = document.fr.pass.value;
    var pass2 = document.fr.pass2.value;
    var name = document.fr.name.value;
    var nick = document.fr.nick.value;
    var gender = document.fr.pregender.value;
    var tel = document.fr.tel.value;
    
    // 제약조건(수정중)
    	// 입력여부, 비밀번호 일치여부
    if (!id) {
    	alert("아이디를 입력해주세요.");
    	document.fr.id.focus();
    	return false;
    }else if(!pass) {
    	alert("비밀번호를 입력해주세요.");
    	document.fr.pass.focus();
    	return false;
    }else if(pass != pass2) {
    	alert("비밀번호가 일치하지 않습니다.");
    	document.fr.pass2.focus();
    	return false;
    }else if(!name) {
    	alert("이름을 입력해주세요.");
    	document.fr.name.focus();
    	return false;
    }else if(!nick) {
    	alert("닉네임을 입력해주세요.")
    	document.fr.nick.focus();
    	return false;
    }else if(!tel) {
    	alert("연락처를 입력해주세요.");
    	document.fr.tel.focus();
    	return false;
    }
    	// 정규표현식
    	var regPass = /^[a-z][a-z|0-9|!@#$%^&*_-]{5,15}/i;	// 영문시작, 영문숫자특수문자 조합 가능 6~16자
    	var regNick = /^[a-z|가-힣][a-z|0-9|가-힣]{1,8}/i;			// 영문, 한글 시작 영문,숫자,한글 조합 가능 2~9자
    	var regTel = /^[0-9]{7,20}/;											// 숫자만 가능 7~20자
    	if(!regPass.test(pass)) {
    		alert("비밀번호를 형식에 맞게 입력해주세요.");
    		return false;
    	}else if(!regNick.test(nick)) {
    		alert("닉네임을 형식에 맞게 입력해주세요.");
    		return false;
    	}else if(!regTel.test(tel)) {
    		alert("연락처를 형식에 맞게 입력해주세요.");
    		return false;
    	}
    	
    	// 이메일 인증번호 일치 여부 확인
    	if(emailCheckNumber==""||emailCheckNumber!=certificationNumber) {
    		alert("이메일(아이디) 인증번호가 일치하지 않습니다.");
    		return false;
    	}
    	
    	// 닉네임 중복확인
    	if(check==0) {
    		alert("닉네임 중복확인을 해주세요.");
    		return false;
    	}
    	
    try {
    	// 공개키 가져오기
        var rsaPublicKeyModulus = document.getElementById("join_publicKeyModulus").value;
        var rsaPublicKeyExponent = document.getElementById("join_publicKeyExponent").value;
        
        var rsa = new RSAKey();
        rsa.setPublic(rsaPublicKeyModulus, rsaPublicKeyExponent);
        
        // 사용자ID와 비밀번호를 RSA로 암호화한다.(이름은 암호화 제외)
        var securedId = rsa.encrypt(id);
        var securedPass = rsa.encrypt(pass);
        var securedPass2 = rsa.encrypt(pass2);
        var securedNick = rsa.encrypt(nick);
        var securedGender = rsa.encrypt(gender);
        var securedTel = rsa.encrypt(tel);
        
        // 암호화된 값들을 다시 폼에 덮어쓴다.
        if(check==1) {
        	document.fr.id.value = securedId;
            document.fr.pass.value = securedPass;
            document.fr.pass2.value = securedPass2;
            document.fr.nick.value = securedNick;
            document.fr.gender.value = securedGender;
            document.fr.tel.value = securedTel;
            
            return true;
            
        }else {
        	return false;
        }
        
    } catch(err) {
        alert(err);
    }
}
