package com.felicekarl.buzzbux.models;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class Account implements Comparable {
	private String id;
	private String name;
	private String description;
	private Money balance;
	private Locale locale;
	private List<Transaction> list;
	
	public Account() {
		this.list = new ArrayList<Transaction>();
	}
	
	public Account(String id, String name, String description, Locale locale, int value) {
		this();
		this.id = id;
		this.name = name;
		this.description = description;
		this.locale = locale;
		this.balance = new Money(locale, value);
	}
	
	public void setLocale(Locale locale) {
		this.locale = locale;
	}
	
	public Locale getLocale() {
		return locale;
	}
	
	public void addItem(Transaction item) {
		list.add(item);
	}
	
	
	public boolean removeItem(Transaction item) {
		return list.remove(item);
	}
	
	public void removeAllItem() {
		this.list = new ArrayList<Transaction>();
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getId() {
		return id;
	}
	
	public Money getBalance() {
		return balance;
	}
	
	public void setBalance(Money balance) {
		this.balance = balance;
	}
	
	public List<Transaction> getTransactions() {
		return list;
	}
	
	@Override
	public int compareTo(Object obj) {
		return this.name.compareTo(((Account) obj).name);
	}
}
