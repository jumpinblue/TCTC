
/* 회원정보 메뉴 팝업 */

$(document).ready(function(){
	$('.memberInfoMenuTxt').click(function(){	
		var dis = $('.memberManagerNavPopupToggle').css('display');
		
		if(dis == "none") {
			$('.memberManagerNav').removeClass('animated flipOutX').addClass('animated flipInX');
			$('.memberManagerNav').css('display', 'block');
			$('.memberManagerNavPopupToggle').css('display', 'block');
		}else {
			$('.memberManagerNav').removeClass('animated flipInX').addClass('animated flipOutX');
			$('.memberManagerNav').css('display', 'block');	// 애니메이트로 인해 block이여도 숨겨짐
			$('.memberManagerNavPopupToggle').css('display', 'none');
		}
	});
				
		$('section, footer, article').click(function(){
			var dis = $('.memberManagerNavPopupToggle').css('display');
			
			if(dis == "block") {
				$('.memberManagerNav').removeClass('animated flipInX').addClass('animated flipOutX');
				$('.memberManagerNav').css('display', 'block');
				$('.memberManagerNavPopupToggle').css('display', 'none');
			}
		});
});