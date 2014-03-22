package com.felicekarl.buzzbux.models;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

public class MainModel implements IModel {
	private static final String TAG = MainModel.class.getSimpleName();
	
	private Context context;
	private User curUser;
	private Account curAccount;
	private Transaction curTransaction;

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
	@Override
	public void setCurTransaction(Transaction transaction) {
		curTransaction = transaction;
	}
	@Override
	public Transaction getCurTransaction() {
		return curTransaction;
	}
	@Override
	public boolean deleteTransaction(Account account, Transaction transaction) {
		ArrayList<Transaction> list = (ArrayList<Transaction>) account.getTransactions();
		return list.remove(transaction);
	}
}
