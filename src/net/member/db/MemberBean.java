package net.member.db;

import java.sql.Timestamp;

public class MemberBean {
	private String id;
	private String pass;
	private String name;
	private String nick;
	private String gender;
	private String tel;
	private Timestamp reg_date;
	private String profile;
	private int auth;	// 0: 관리자, 1: 일반사용자, 2: 제휴업체(?)
	private int gold;	// 유료회원: 1, 무료회원: 0
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public Timestamp getReg_date() {
		return reg_date;
	}
	public void setReg_date(Timestamp reg_date) {
		this.reg_date = reg_date;
	}
	public String getProfile() {
		return profile;
	}
	public void setProfile(String profile) {
		this.profile = profile;
	}
	public void setAuth(int auth) {
		this.auth = auth;
	}
	public int getAuth() {
		return auth;
	}
	public int getGold() {
		return gold;
	}
	public void setGold(int gold) {
		this.gold = gold;
	}
	
}
