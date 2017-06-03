  
var daumSearch = {
        /** 초기화. **/
        init : function(){
            this.apikey = "5aad815bb793e165b8de68b1d3e26d3467e96367";
            this.q = document.getElementById('daumSearch');
            
            //검색 객체들 초기화.
            daumImage.init(5);

        },
        /** 검색 **/
        search : function(){
            this.query = '?apikey=' + this.apikey 
                + '&output=json&q=' + encodeURI(this.q.value);
            
            //검색어에 맞게 각각 첫페이지를 띄움.
            daumImage.pingSearch(1);
        },
        /** callback 함수 호출. **/
        pingSearch : function(ds, api, pgno, callback, result){
            ds.innerHTML = "";
            
            var s = document.createElement('script');
            s.type = 'text/javascript';
            s.charset = 'utf-8';
            s.src = api + this.query + '&pageno=' + pgno 
                + '&callback=' + callback + '&result='+result ; 
            
            ds.appendChild(s);
        },
        /** 결과를 뿌려줌. **/
        pongSearch : function(search, z){
            var ul = document.createElement('div');
            ul.className="div_1";
/*           ul.style["border"]="5px solid blue";*/
            for(var i=0; i<z.channel.item.length; i++){
                //title 정보를 얻음.
                var title = document.createElement('h6');
                var a = document.createElement('a');
                a.href = z.channel.item[i].link;
                a.target = '_blank';
                a.innerHTML = this.escapeHtml(z.channel.item[i].title) 
                    + '<br'+'>';
                //세부 내용을 얻음.
                var content = search.getContent(z.channel.item[i]);
                
                //리스트 화.
                ul.appendChild(search.getSearch(title,content));
            }
            return ul;
        },
        /** HTML태그 안 먹게 하는 함수 **/
        escapeHtml: function (str) {
            str = str.replace(/&amp;/g, "&");
            str = str.replace(/&lt;/g, "<");
            str = str.replace(/&gt;/g, ">");
            return str;
        }
    };

    

    /** 이미지 검색. **/
    var daumImage = {
        /** 초기화. **/
        init : function(r){
            daumImage.api = 'http://apis.daum.net/search/image';
            daumImage.pgno = 1;
            daumImage.result = r;
        },
        /** callback 함수 호출. **/
        pingSearch : function(pgno){
            daumImage.pgno = pgno;
            
            var ds = document.getElementById('daumImageScript');
            var callback = 'daumImage.pongSearch';
            
            daumSearch.pingSearch(ds,daumImage.api, 
                daumImage.pgno, callback, daumImage.result);  
        },
        /** 결과를 뿌려줌. **/
        pongSearch : function(z){
            var dv = document.getElementById('daumImage');
            dv.innerHTML ="";
            dv.appendChild(daumSearch.pongSearch(this, z));
            dv.appendChild(daumSearch.pongPgno(daumImage.pgno, 
                z.channel.totalCount/daumImage.result,daumImage.pingSearch));
        },
        /** li setting **/
        getSearch : function(title,content){
            var li = document.createElement('div');
            /*li.style["border"]="5px solid blue";*/
            li.style.height = '18em';
            li.style.width = '18em';
            
            li.className='planSpot_tb';
  
           /* content.appendChild(document.createElement('br'));*/
           li.appendChild(content);
            li.appendChild(title);
            
            return li;
        },
        /** 설명 return **/
       getContent : function(z){
           var a = document.createElement('a');
           var img = document.createElement('img');
           
           a.target = '_blank';
           a.href = z.image;
           
          /* img.style.height = '20em';
           img.style.width = '30em';*/
           
           img.src = z.thumbnail;
           
           a.appendChild(img);
           
           return a;
       }
    };
    
    window.onload = function () {
        daumSearch.init();
        daumSearch.search();
    };
