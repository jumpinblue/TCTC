/*국가리스트 출력
function CountryList(){ 
	country = new Array("아시아", "유럽", "남태평양", "북미", "중남미");
	var i=0;
	$(document).ready(function(){
		$.getJSON('./plan/countryData.jsp', function(data){
			$.each(data, function(index,item){
				for(i=1;i<=5;i++){
				if(item.continent==i){
					$('.'+country[i-1]).append( function(){
						var con = '<div><a href=./PlanSearch.pl?check=1&search='+item.country+'>'+item.country+'</a><span>'+item.EnName+'</span></div>';

						return con;
					}); 	
				}//if
				}//for
			});
		});
	});
}*/

/*검색 폼제어*/
function checkSearch(){
	if(document.fr.check.value==0){
		alert("도시명/국가명을 선택해 주세요");
		document.fr.check.focus();
		return false;
	}
	if(document.fr.search.value==""){
		alert("검색어를 작성해주세요");
		document.fr.search.focus();
		return false;
	}
}

/* 대륙지도 팝업없애기 */
function popupToggle_map() {
	$(document).ready(function(){	
		if($('#countryMap_back').css('display')=="block") {	// 팝업 숨김
			$('#map1').fadeOut(200, function(){	// 페이드 아웃 애니메이션
				$('#map1').css('display', 'none');
			});
			$('#map2').fadeOut(200, function(){	// 페이드 아웃 애니메이션
				$('#map2').css('display', 'none');
			});
			$('#map3').fadeOut(200, function(){	// 페이드 아웃 애니메이션
				$('#map3').css('display', 'none');
			});
			$('#map4').fadeOut(200, function(){	// 페이드 아웃 애니메이션
				$('#map4').css('display', 'none');
			});
			$('#map5').fadeOut(200, function(){	// 페이드 아웃 애니메이션
				$('#map5').css('display', 'none');
			});
			$('#countryMap_back').css('display', 'none');
		}
	});	
}


/* 대륙지도 팝업(아시아) */
function popupToggle_asia() {	
	$(document).ready(function(){
		
		// 팝업 띄움
		if($('#countryMap_back').css('display')=="none") {
			$('#map1').fadeIn(200, function(){	// 페이드 인 애니메이션
				$('#map1').css('display', 'block');
			});
			$('#countryMap_back').css('display', 'block');
			
		}
	});	
}

/* 대륙지도 팝업(유럽) */
function popupToggle_europe() {
	
	$(document).ready(function(){	
		// 팝업 띄움
		if($('#countryMap_back').css('display')=="none") {
			$('#map2').fadeIn(200, function(){	// 페이드 인 애니메이션
				$('#map2').css('display', 'block');
			});
			$('#countryMap_back').css('display', 'block');
			
		}
	});	
}

/* 대륙지도 팝업(남태평양) */
function popupToggle_oceania() {
	
	$(document).ready(function(){	
		// 팝업 띄움
		if($('#countryMap_back').css('display')=="none") {
			$('#map3').fadeIn(200, function(){	// 페이드 인 애니메이션
				$('#map3').css('display', 'block');
			});
			$('#countryMap_back').css('display', 'block');
			
		}
	});	
}

/* 대륙지도 팝업(북미) */
function popupToggle_north() {
	
	$(document).ready(function(){	
		// 팝업 띄움
		if($('#countryMap_back').css('display')=="none") {
			$('#map5').fadeIn(200, function(){	// 페이드 인 애니메이션
				$('#map5').css('display', 'block');
			});
			$('#countryMap_back').css('display', 'block');
			
		}
	});	
}

/* 대륙지도 팝업(남미) */
function popupToggle_south() {
	
	$(document).ready(function(){	
		// 팝업 띄움
		if($('#countryMap_back').css('display')=="none") {
			$('#map4').fadeIn(200, function(){	// 페이드 인 애니메이션
				$('#map4').css('display', 'block');
			});
			$('#countryMap_back').css('display', 'block');
			
		}
	});	
}

