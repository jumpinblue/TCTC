package net.like.db;

public class LikeBean {
	private int like_num;
	private int num;
	private String nick;
	
	
	
	public int getLike_num() {
		return like_num;
	}
	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
	public void setLike_num(int like_num) {
		this.like_num = like_num;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}


}
