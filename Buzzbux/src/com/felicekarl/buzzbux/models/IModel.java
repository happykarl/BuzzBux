package com.felicekarl.buzzbux.models;

import java.util.ArrayList;

public interface IModel {
	public void setCurUser(User user);
	public User getCurUser();
	public void setCurAccount(Account account);
	public Account getCurAccount();
	public ArrayList<Account> getCurUserAccounts();
}