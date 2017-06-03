
var daumSearch = { 
        /** 초기화. **/ 
        init : function(){ 
            this.apikey = "5aad815bb793e165b8de68b1d3e26d3467e96367"//"645154cac2a46c0d3275a8026e3746e77a7266da"; //DAUM_SEARCH_DEMO_APIKEY 
            this.q = document.getElementById('daumSearch'); 
             
            daumImage.init(10); //한번에 출력할 그림의 갯수. 20까지 가능한듯.
        }, 
        /**1. 검색 **/ 
        search : function(){ //search버튼을 눌렀을 때 최초 시작되는 부분.
            this.query = '?apikey=' + this.apikey + '&output=json&q=' + encodeURI(this.q.value); 
            daumImage.pingSearch(1); //daumImage의 pingSearch로 이동한다. 인수는 첫번째 표시할 페이지. 1이면 1페이지.
        }, 
        /**6. callback 함수 호출. **/ 
        pingSearch : function(ds, api, pgno, callback, result){ 
            ds.innerHTML = ""; 
             
            var s = document.createElement('script'); 
            s.type = 'text/javascript'; 
            s.charset = 'utf-8'; 
            s.src = api + this.query + '&pageno=' + pgno + '&callback=' + callback + '&result='+result ;  
             
            ds.appendChild(s); 
        }, 
        /**4. 결과를 뿌려줌. **/ 
        pongSearch : function(search, z){ 
            var ul = document.createElement('div');	//id가 daumImage인 부분에 div추가.
            //기존 수정 전 코드에서는 div 대신에 ul을 사용했다. 변수명은 그대로 사용.
            
            for(var i=0; i<z.channel.item.length; i++){
                var title = document.createElement('h4'); //id가 daumImage인 부분에 추가. h1~h6까지 사진 설명 글자 크기.
                title.className = 'content';
                
                //사진 설명 글의 하이퍼링크 시작.
                var a = document.createElement('a'); 
                a.href = z.channel.item[i].link; 
                a.target = '_blank'; 
                a.innerHTML = this.escapeHtml(z.channel.item[i].title); 
                title.appendChild(a); //이 부분이 없으면 아예 사진 설명 출력 없음.
               //사진 설명 글의 하이퍼링크 끝.
                
                var content = search.getContent(z.channel.item[i]); 
                ul.appendChild(search.getSearch(title,content)); 
            }
            return ul; 
        }, 
        /**5. PageNumber를 그려줌. **/ 
        pongPgno : function(pgno,max,func){
            var maxpg = (pgno+6<max)?4:max; 
            //pgno+6이 max값보다 작으면 앞의 값, 크면 뒤의 값....
             
            var div = document.createElement('div'); 
            div.align = 'center';
            div.style.clear = 'left';
            //div.style["border"]="5px solid red";	//페이지넘버 박스
             
            //좌측 화살표.
            var left = document.createElement('a'); 
            left.innerHTML = "<< "; 
            if(pgno-5>1){
                this.onMouseDown(left,pgno-6,func);} 
            else{ 
                left.style.color = "gray"; 
                left.style.cursor = "default"; 
            } 
            //div.appendChild(left); //좌측 화살표 출력
             
            //페이지 번호. 
            for(var i=(pgno-5>1)?pgno-5:1; i<maxpg; i++){ 
                var a = document.createElement('a'); 
                a.innerHTML = " " + i + " "; 

                if(i==pgno){ 
                    a.style.color = 'blue';
                    a.style.cursor = "default"; 
                } 
                else 
                    this.onMouseDown(a,i,func); 
                 
                //div.appendChild(a); //페이지 번호 출력
            } 
             
            //우측 화살표. 
            var right = document.createElement('a'); 
            right.innerHTML = " >>"; 
            if(pgno+6<max) 
                this.onMouseDown(right,pgno+7,func); 
            else{ 
                right.style.color = "gray"; 
                right.style.cursor = "default"; 
            } 
            //div.appendChild(right); //우측 화살표 출력
             
            return div; 
        }, 
        /** 마우스 이벤트. **/ 
        onMouseDown: function(a, i, func){ 
            a.style.cursor = 'pointer'; 
            a.onmousedown = function(){ 
                func(i); 
            } 
        }, 
        /** HTML태그 안 먹게 하는 함수 **/ 
        escapeHtml: function (str) { 
            str = str.replace(/&amp;/g, "&"); 
            str = str.replace(/&lt;/g, "<"); 
            str = str.replace(/&gt;/g, ">"); 
            return str; 
        } 
    };/* end daum search */ 
     
    
    
    
     
    /** 이미지 검색. **/ 
    var daumImage = { 
        /** 초기화. **/ 
        init : function(r){ 
            daumImage.api = 'http://apis.daum.net/search/image'; 
            daumImage.pgno = 1; 
            daumImage.result = r; 
        }, 
        /**2. callback 함수 호출. **/ 
        pingSearch : function(pgno){
            daumImage.pgno = pgno; 
             
            var ds = document.getElementById('daumImageScript'); //ds에 html의 id가 daumIageScript를 가져옴.
            var callback = 'daumImage.pongSearch'; //세번째 함수로 이동.
             
            daumSearch.pingSearch(ds,daumImage.api, daumImage.pgno, callback, daumImage.result);   
        }, 
        /**3. 결과를 뿌려줌. **/ 
        pongSearch : function(z){
            var dv = document.getElementById('daumImage'); //dv에 id가 daumImage인 부분을 가져옴.
            dv.innerHTML ="";
            dv.appendChild(daumSearch.pongSearch(this, z)); //appendChild로, dv안에 자식요소를 추가합니다.
            //dv.style["border"]="5px solid red"; //이미지 모두를 포함하는 상자 표시하기.
            dv.appendChild(daumSearch.pongPgno(daumImage.pgno, z.channel.totalCount/daumImage.result, daumImage.pingSearch)); 
        }, 
        /** li setting **/ 
        getSearch : function(title,content){ 
            var li = document.createElement('div');
            li.className = 'feature';
            //수정 전 코드에서는 div 대신에 li를 만들었다. 변수명은 li계속 사용.
            
            //선택자.style[css명령어]='css value값';
            //li.style["border"]="5px solid red"; //낱개 이미지 박스.
            
             
            //content.appendChild(document.createElement('br')); 
            li.appendChild(content); //리스트에 content출력
            li.appendChild(title); //리스트에 사진 설명 출력
             
            return li; 
        }, 
        /** 설명 return **/ 
       getContent : function(z){ 
           var a = document.createElement("div");
           a.className = 'image rounded';
           var img = document.createElement('img'); 
            
           a.target = '_blank'; //img 하이퍼링크 타겟
           a.href = z.image; //img 하이퍼링크 대상 경로
           //a.href = 'http://naver.com';	//img 하이퍼링크 테스트
           
           //img.height = 100; //이미지 높이
           img.style.width = '11.5em';  //이미지 폭
           //img.style["border-radius"]="100px";
           img.src = z.thumbnail; 
            
           a.appendChild(img); 
            
           return a;
       } 
    }; 
     
     
    window.onload = function () { 
        daumSearch.init(); 
        daumSearch.search(); 

    };