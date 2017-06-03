package net.Board1.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Action1 {
	//추상메서드
	public ActionForward1 execute(HttpServletRequest request, HttpServletResponse response) throws Exception;
	
}
