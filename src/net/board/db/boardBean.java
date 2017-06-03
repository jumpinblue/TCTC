package net.board.db;

import java.sql.Date;

public class boardBean {
	
	private int num;
	private String nick;
	private String subject;
	private String content;
	private String image1;	
	private Date date;
	//하기의 love는 해당하는 글 num에 대한 좋아요 갯수 구현하는 변수
	//love 테이블은 nick을 기준으로 누가 어떤글을 좋아하는지를 나타냄
	private int love;
	
	
	

	public int getLove() {
		return love;
	}
	public void setLove(int love) {
		this.love = love;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}

	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	public String getImage1() {
		return image1;
	}
	public void setImage1(String image1) {
		this.image1 = image1;
	}
}