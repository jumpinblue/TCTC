
/* 아시아  */
    jQuery.noConflict();
    jQuery(function(){
      var $ = jQuery;

      $('#map1').vectorMap({
        map: 'asia_mill',
        panOnDrag: true,
        
        onRegionClick : function(event, code){
        	var map = $('#map1').vectorMap('get', 'mapObject');			
			location.href="./PlanSearch.pl?check=1&search="+map.getRegionName(code);
		},
    
        series: {
          regions: [{
            scale: ['#47d147', '#001a00'],
            normalizeFunction: 'polynomial',
            values: {
              "BD": 16.63,
              "MN": 11.58,
              "BN": 158.97,
              "BH": 85.81,
              "BT": 1.1,
              "HK": 351.02,
              "JO": 8.83,
              "PS": 1219.72,
              "LB": 366.26,
              "LA": 52.17,
              "TW": 7.54,
              "TR": 21.73,
              "LK": 105.4,
              "TL": 3.96,
              "TM": 52.89,
              "TJ": 461.33,
              "TH": 1.43,
              "XC": 6.49,
              "NP": 1.4,
              "PK": 19.18,
              "PH": 16.2,
              "-99": 12.5,
              "AE": 2023.53,
              "CN": 11.96,
              "AF": 44.84,
              "IQ": 8.67,
              "JP": 1.47,
              "IR": 11.36,
              "AM": 21.88,
              "SY": 1563.66,
              "VN": 1.57,
              "GE": 2.11,
              "IL": 7.59,
              "IN": 199.18,
              "AZ": 5745.13,
              "ID": 283.11,
              "OM": 0.56,
              "KG": 12.6,
              "UZ": 11.88,
              "MM": 35.02,
              "SG": 22.38,
              "KH": 59.92,
              "CY": 22.75,
              "QA": 195.23,
              "KR": 304.56,
              "KP": 1.14,
              "KW": 0.38,
              "KZ": 50.87,
              "SA": 61.49,
              "MY": 216.83,
              "YE": 21.8
            }
          }]
        }
      });
    })

/* 유럽  */
    jQuery.noConflict();
    jQuery(function(){
      var $ = jQuery;

      $('#map2').vectorMap({
        map: 'europe_mill',
        panOnDrag: true,
        
        onRegionClick : function(event, code){
        	var map = $('#map2').vectorMap('get', 'mapObject');			
			location.href="./PlanSearch.pl?check=1&search="+map.getRegionName(code);
		},
		
        series: {
          regions: [{
            scale: ['#C8EEFF', '#0071A4'],
            normalizeFunction: 'polynomial',
            values: {
              "BE": 16.63,
              "FR": 11.58,
              "BG": 158.97,
              "DK": 85.81,
              "HR": 1.1,
              "DE": 351.02,
              "BA": 8.83,
              "HU": 1219.72,
              "JE": 366.26,
              "FI": 52.17,
              "BY": 7.54,
              "GR": 21.73,
              "RU": 105.4,
              "NL": 3.96,
              "PT": 52.89,
              "NO": 461.33,
              "LI": 1.43,
              "LV": 6.49,
              "LT": 1.4,
              "LU": 19.18,
              "FO": 16.2,
              "PL": 12.5,
              "XK": 2023.53,
              "CH": 11.96,
              "AD": 44.84,
              "EE": 8.67,
              "IS": 1.47,
              "AL": 11.36,
              "IT": 21.88,
              "GG": 1563.66,
              "CZ": 1.57,
              "IM": 2.11,
              "GB": 7.59,
              "AX": 199.18,
              "IE": 5745.13,
              "ES": 283.11,
              "ME": 0.56,
              "MD": 12.6,
              "RO": 11.88,
              "RS": 35.02,
              "MK": 22.38,
              "SK": 59.92,
              "MT": 22.75,
              "SI": 195.23,
              "SM": 304.56,
              "UA": 1.14,
              "SE": 0.38,
              "AT": 50.87
            }
          }]
        }
      });
    })

   
/* 남태평양  */
    jQuery.noConflict();
    jQuery(function(){
      var $ = jQuery;

      $('#map3').vectorMap({
        map: 'oceania_mill',
        
        onRegionClick : function(event, code){
        	var map = $('#map3').vectorMap('get', 'mapObject');			
			location.href="./PlanSearch.pl?check=1&search="+map.getRegionName(code);
		},
		
        panOnDrag: true,
        series: {
          regions: [{
            scale: ['#C8EEFF', '#0071A4'],
            normalizeFunction: 'polynomial',
            values: {
              "GU": 16.63,
              "PW": 11.58,
              "KI": 158.97,
              "NC": 85.81,
              "NU": 1.1,
              "NZ": 351.02,
              "AU": 8.83,
              "PG": 1219.72,
              "SB": 366.26,
              "PF": 52.17,
              "FJ": 7.54,
              "FM": 21.73,
              "WS": 105.4,
              "VU": 3.96
            }
          }]
        }
      });
    })

/* 남미  */
    jQuery.noConflict();
    jQuery(function(){
      var $ = jQuery;

      $('#map4').vectorMap({
        map: 'south_america_mill',
        panOnDrag: true,
        
        onRegionClick : function(event, code){
        	var map = $('#map4').vectorMap('get', 'mapObject');			
			location.href="./PlanSearch.pl?check=1&search="+map.getRegionName(code);
		},
		
        series: {
          regions: [{
            scale: ['#C8EEFF', '#0071A4'],
            normalizeFunction: 'polynomial',
            values: {
              "PY": 16.63,
              "CO": 11.58,
              "VE": 158.97,
              "CL": 85.81,
              "SR": 1.1,
              "BO": 351.02,
              "EC": 8.83,
              "AR": 1219.72,
              "GY": 366.26,
              "BR": 52.17,
              "PE": 7.54,
              "UY": 21.73,
              "FK": 105.4
            }
          }]
        }
      });
    })

/* 북미  */
    jQuery.noConflict();
    jQuery(function(){
      var $ = jQuery;

      $('#map5').vectorMap({
        map: 'north_america_mill',
        panOnDrag: true,
        
        onRegionClick : function(event, code){
        	var map = $('#map5').vectorMap('get', 'mapObject');			
			location.href="./PlanSearch.pl?check=1&search="+map.getRegionName(code);
		},
		
		
        series: {
          regions: [{
            scale: ['#C8EEFF', '#0071A4'],
            normalizeFunction: 'polynomial',
            values: {
              "PR": 16.63,
              "DO": 11.58,
              "DM": 158.97,
              "LC": 85.81,
              "NI": 1.1,
              "PA": 351.02,
              "CA": 8.83,
              "SV": 1219.72,
              "HT": 366.26,
              "TT": 52.17,
              "JM": 7.54,
              "GT": 21.73,
              "HN": 105.4,
              "BZ": 3.96,
              "BS": 52.89,
              "CR": 461.33,
              "US": 1.43,
              "GL": 6.49,
              "MX": 1.4,
              "CU": 19.18
            }
          }]
        }
      });
    })




