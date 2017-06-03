<%@page import="java.util.Random"%>
<%@page import="net.plan.db.PlanCityBean"%>
<%@page import="java.util.List"%>
<%@page import="net.plan.db.PlanDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

<%
	//파라미터 값 가져오기
	String continent = request.getParameter("continent"); //대륙가져오기
	if(continent==null){
		continent="All";
	}
	//객체 생성
	
	System.out.print(continent);
	PlanDAO pdao = new PlanDAO();
	
	int count=0;
	List<PlanCityBean> pcbList=null;
	PlanCityBean pcb = null;
	
	//DB에 등록된 대륙에 맞는 도시 개수
	if(continent.equals("All")){//도시전체
		count=pdao.getCityCount();
	
	}else{ //각 대륙에 맞는 도시
		count=pdao.getCityCount_con(continent);	
	}
	
	//도시 리스트 가져오기
	if(count>0){
		pcbList=pdao.getCityList_con(continent);

			
	//도시가 8개 이상이면 랜덤으로 뽑아오기
	int size=pcbList.size();
	int[] test = new int[size];
      
		int tmp=0;    
         int i=0;      
         Random rnd = new Random();
         for(i=0;i<size;i++){
             test[i]= i;
         }
         for(i=0;i<size;i++){
             int des =rnd.nextInt(size);
             tmp = test[i];
             test[i]=test[des];
             test[des]=tmp;
         }
       
       //랜덤으로 뽑아온 도시출력
       if(size>8){
     	for(int d=0;d<8;d++){
     		for(int z=0;z<pcbList.size();z++){
     			if(test[d]==z){
     				pcb=pcbList.get(z);
     				System.out.println(test[d]+pcb.getName());
     			}
     		}
     	}
       }
       else{
    	   for(int d=0;d<size;d++){
        		for(int z=0;z<pcbList.size();z++){
        			if(test[d]==z){
        				pcb=pcbList.get(z);
        				System.out.println(test[d]+pcb.getName());
        			}
        		}
        	}
       }
         
    
    
%>
		<!-- 이미지 출력 -->
	<table>
		<%
			int countnum = 0;
			/* if(pcbList.size()<=8){ */
			for (int z = 0; z < 2; z++) {
				
			if(size>8){
		%>
		<tr>
			<%
				for (int d = countnum; d < 8; d++) {
						countnum++;
						for (i = 0; i < pcbList.size(); i++) {

							if (test[d] == i) {
								pcb = pcbList.get(i);
			%>

			<td
				style="background-image: url('./images/plan/nation/<%=pcb.getCountry_code()%>/<%=pcb.getEn_name()%>.jpg'); 
					   background-size: cover; 
					   width:10em;
					   height:15em;"
					   >
					   
				<span><a href="./PlanSearch.pl?check=2&search=<%=pcb.getName()%>"><%=pcb.getName()%></a></span>
			
			</td>
			<%
				break;
							}
				}

						if (countnum == 4 || countnum == 8) {
			%>
		</tr>
		<%
			break;
					}

				}
			}else{
				%><tr><%
				
				
					for (int d = countnum; d < size; d++) {
							countnum++;
							for (i = 0; i < pcbList.size(); i++) {

								if (test[d] == i) {
									pcb = pcbList.get(i);
				%>

				<td
				style="background-image: url('./images/plan/nation/<%=pcb.getCountry_code()%>/<%=pcb.getEn_name()%>.jpg'); 
					   background-size: cover; 
					   width:10em;
					   height:15em;"
					   >
					<span><a href="./PlanSearch.pl?check=2&search='<%=pcb.getName()%>'"><%=pcb.getName()%></a></span>
				</td>
				<%
					break;
								}
					}
							if (countnum == 4 || countnum == 8) {
				%>
			</tr>
			<%
				break;
						}

					}
			}
				
			}
			
		%>
	</table><%} 
			else{
				%>
			
				<div style="width: 85em ; height: 20em; border: 1px dotted #999;">
					<span>현재 등록된 여행지가 없습니다.ㅠ ㅅ ㅠ</span>
				</div>
			<%	
				
			}
	%>
	
	


</body>
</html>