package net.travel.admin.action;

// 이동정보 담는 클래스
public class ActionForward {
	private String path;		// 경로
	private boolean isRedirect;	// 이동방식
	
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public boolean isRedirect() {
		return isRedirect;
	}
	public void setRedirect(boolean isRedirect) {
		this.isRedirect = isRedirect;
	}
}
