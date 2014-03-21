package com.felicekarl.buzzbux.models;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class User {
	private String username;
	private String firstname;
	private String lastname;
	private ArrayList<Account> list;
	
	public User() {
		this.list = new ArrayList<Account>();
	}
	
	public User(String username, String firstname, String lastname) {
		this();
		this.username = username;
		this.firstname = firstname;
		this.lastname = lastname;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public String getFirstname() {
		return this.firstname;
	}
	
	public String getLastName() {
		return this.lastname;
	}
	
	public void addAccount(Account item) {
		this.list.add(item);
	}
	
	public boolean removeAccount(Account item) {
		return this.list.remove(item);
	}
	
	public ArrayList<Account> getAccounts() {
		return list;
	}
	 
}
