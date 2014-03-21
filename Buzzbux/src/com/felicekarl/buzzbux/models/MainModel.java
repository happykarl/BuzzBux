package com.felicekarl.buzzbux.models;

import java.util.ArrayList;

import android.content.Context;

public class MainModel implements IModel {
	private static final String TAG = MainModel.class.getSimpleName();
	
	private Context context;
	private User curUser;
	private Account curAccount;

	public MainModel(Context context) {
		this.context = context;
	}
	@Override
	public void setCurUser(User user) {
		this.curUser = user;
	}
	@Override
	public ArrayList<Account> getCurUserAccounts() {
		return curUser.getAccounts();
	}
	@Override
	public User getCurUser() {
		return curUser;
	}
	@Override
	public void setCurAccount(Account account) {
		this.curAccount = account;
	}
	@Override
	public Account getCurAccount() {
		return curAccount;
	}
}
