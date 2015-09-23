package com.example.cash;


public class Model {
	String Email;
	String Name;
	String Password;
	String Pinno;
	String Mobile;
	String pinno;
	String Sno;
	String tofrom;
	String credit;
	String Otherpinno;
	String debit;
	String Nbalance;
	String Success;
	String Date;
	String amttopay;
	String balaftertrans;
	String Balance;

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}
	
	public String getBalance() {
		return Balance;
	}
	
	public void setBalance(String bal) {
		Balance = bal;
	}
	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}
	
	public String getMobile() {
		return Mobile;
	}

	public void setMobile(String mobile) {
		Mobile = mobile;
	}
	
	public String getPinno() {
		
		return (pinno);
	}

	public void setPinno(String pinno) {
		this.pinno =pinno;
	}
	
	public String getToFrom() {
		return tofrom;
	}

	public void setToFrom(String tf) {
		this.tofrom = tf;
	}
	
	public String getDebit() {
		return debit;
	}

	public void setDebit(String db) {
		this.debit = db;
	}

	public String getNbalance() {
		return Nbalance;
	}

	public void setNbalance(String pbal) {
		this.Nbalance = pbal;
	}

	public String getAmttopay() {
		return amttopay;
	}

	public void setAmttopay(String pay) {
		this.amttopay = pay;
	}

	public String getSuccess() {
		return Success;
	}

	public void setSuccess(String sucval) {
		this.Success = sucval;
	}

	public String getBalaftertrans() {
		return balaftertrans;
	}

	public void setBalaftertrans(String balval) {
		this.balaftertrans = balval;
	}

	public String getDate() {
		return Date;
	}

	public void setDate(String date) {
		this.Date = date;
	}

	public String getCredit() {
		return credit;
	}

	public void setCredit(String cd) {
		this.credit = cd;
	}
	
	public String getSno() {
		return Sno;
	}

	public void setSno(String sn) {
		this.Sno = sn;
	}

	public String getPinno1() {
		return Otherpinno;
	}

	public void setPinno1(String ttid) {
		this.Otherpinno = ttid;
	}
	}
