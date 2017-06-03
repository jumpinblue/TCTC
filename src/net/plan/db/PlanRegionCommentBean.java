package net.plan.db;

import java.sql.Timestamp;

public class PlanRegionCommentBean {
	
	private int num;
	private String region;
	private int region_num;
	private String nick;
	private Timestamp date;
	private int eval;
	private String content;
	
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public int getRegion_num() {
		return region_num;
	}
	public void setRegion_num(int region_num) {
		this.region_num = region_num;
	}
	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
	public Timestamp getDate() {
		return date;
	}
	public void setDate(Timestamp date) {
		this.date = date;
	}
	public int getEval() {
		return eval;
	}
	public void setEval(int eval) {
		this.eval = eval;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}
