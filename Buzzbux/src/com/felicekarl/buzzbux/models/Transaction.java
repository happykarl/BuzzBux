package com.felicekarl.buzzbux.models;

import java.util.Date;

public class Transaction {
	private String index;
	private TransType type;
	private String description;
	private Money amount;
	private Date date;
	
	public Transaction(String index, TransType type, String description, Money amount, Date date) {
		this.index = index;
		this.type = type;
		this.description = description;
		this.amount = amount;
		this.date = date;
	}
	
	public void setType(TransType type) {
		this.type = type;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setAmount(Money amount) {
		this.amount = amount;
	}
	
	public TransType getType() {
		return this.type;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public Money getAmount() {
		return amount;
	}
	
	public String getSignedAmount() {
		if (type.equals(TransType.DEPOSIT)) {
			return "+" + amount.toString();
		} else if (type.equals(TransType.WITHDRAWAL)) {
			return "-" + amount.toString();
		}
		return null;
	}
	
	public Date getDate() {
		return this.date;
	}

	
}
