var myIndex = 0;
carousel();

function carousel() {
	var i;
	var x = document.getElementsByClassName("img_sld1");
	for (i = 0; i < x.length; i++) {
		x[i].style.display = "none";
	}
	myIndex++;
	if (myIndex > x.length) {
		myIndex = 1
	}
	x[myIndex - 1].style.display = "block";
	setTimeout(carousel, 2000); // Change image every 1 seconds
}// 이미지 슬라이드 스크립트 끝
