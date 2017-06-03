package net.myplanBasket.action;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.mysql.fabric.xmlrpc.base.Array;

public class DateList {

	public List Date(String fromDate, String toDate) {
			System.out.println("가나요?");
		List datelist = new ArrayList();
		try {
			System.out.println(fromDate);
			int date_cnt = getDiffDay(fromDate, toDate);
			for (int i = 1; i <date_cnt; i++) {
				System.out.println(i);
				datelist.add(getAfterDate(fromDate, i));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println(datelist.get(2));
		return datelist;
	}

	/**
	 * 두 날짜의 차이를 리턴한다.
	 * 
	 * @param startDate
	 *            yyy-MM-dd
	 * @param endDate
	 *            yyy-MM-dd
	 * @return 두날짜의 차이
	 */
	public int getDiffDay(String startDate, String endDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date sDate;
		Date eDate;
		try {
			sDate = sdf.parse(startDate);
			eDate = sdf.parse(endDate);
			System.out.println((int) ((eDate.getTime() - sDate.getTime()) / 1000 / 60 / 60 / 24));
			return (int) ((eDate.getTime() - sDate.getTime()) / 1000 / 60 / 60 / 24);
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * 입력받은 날짜에 해달 일수를 증가한다.
	 * 
	 * @param str
	 *            yyyy-MM-dd 형식
	 * @param i
	 *            증가시킬 날
	 * @return yyyy-MM-dd 증가된 날짜
	 * @throws Exception
	 */
	public String getAfterDate(String str, int i) throws Exception {
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date sDate = (java.util.Date) formatter.parse(str);
		java.util.Calendar cal = java.util.Calendar.getInstance();
		
		
		cal.setTime(sDate);
		cal.add(Calendar.DATE, i);
		
		String year = Integer.toString(cal.get(Calendar.YEAR));
		String month = cal.get(Calendar.MONTH) < 9 ? "0" + Integer.toString(cal.get(Calendar.MONTH) + 1)
				: Integer.toString(cal.get(Calendar.MONTH) + 1);
		;
		String date = cal.get(Calendar.DAY_OF_MONTH) < 10 ? "0" + Integer.toString(cal.get(Calendar.DAY_OF_MONTH))
				: Integer.toString(cal.get(Calendar.DAY_OF_MONTH));
		
		
		return year + "-" + month + "-" + date;
	}
}