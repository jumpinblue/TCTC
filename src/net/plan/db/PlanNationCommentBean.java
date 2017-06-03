package net.plan.db;

import java.sql.Timestamp;

public class PlanNationCommentBean {

	private int num;
	private String nation;
	private int nation_num;
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
	public String getNation() {
		return nation;
	}
	public void setNation(String nation) {
		this.nation = nation;
	}
	public int getNation_num() {
		return nation_num;
	}
	public void setNation_num(int nation_num) {
		this.nation_num = nation_num;
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
