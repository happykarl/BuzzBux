package com.felicekarl.buzzbux.models;

import java.util.Calendar;
import java.util.Date;

public class Transaction implements Comparable {
	private String index;
	private TransType type;
	private String description;
	private Money amount;
	private Date date;
	//private Calendar calendar;
	
	public Transaction(String index, TransType type, String description, Money amount, Date date) {
		this.index = index;
		this.type = type;
		this.description = description;
		this.amount = amount;
		this.date = date;
		//this.calendar = Calendar.getInstance();
		//calendar.setTime(date);
	}
	
	public String getId() {
		return index;
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
	
	public int getSignedValue() {
		if ( type.equals(TransType.DEPOSIT) || type.equals(TransType.REFUND) ) {
			return amount.getValue();
		} else if ( type.equals(TransType.WITHDRAWAL) || 
				type.equals(TransType.DEBIT) ||
				type.equals(TransType.CREDIT) || 
				type.equals(TransType.VOID) ) {
			return -1 * amount.getValue();
		}
		return 0;
	}
	
	public String getSignedAmount() {
		if ( type.equals(TransType.DEPOSIT) || type.equals(TransType.REFUND) ) {
			return "+" + amount.toString();
		} else if ( type.equals(TransType.WITHDRAWAL) || 
				type.equals(TransType.DEBIT) ||
				type.equals(TransType.CREDIT) || 
				type.equals(TransType.VOID) ) {
			return "-" + amount.toString();
		}
		return null;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public Date getDate() {
		return this.date;
	}

	@Override
	public int compareTo(Object obj) {
		Transaction o = (Transaction) obj;
		if (date.compareTo(o.date) > 0) {
			return 1;
		} else if (date.compareTo(o.date) < 0) {
			return -1;
		} else {
			if (index.compareTo(o.index) > 0) {
				return 1;
			} else if (index.compareTo(o.index) < 0) {
				return -1;
			}
		}
		return 0;
	}
}
