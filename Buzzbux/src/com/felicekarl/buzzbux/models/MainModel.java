package com.felicekarl.buzzbux.models;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
/**
 * MainModel of MVP Model.
 * @author Karl
 *
 */
public class MainModel implements IModel {
	/**
	 * Android Context.
	 */
    @SuppressWarnings("unused")
    private Context context;
    /**
     * current User of Application.
     */
    private User curUser;
    /**
     * current Account of Application.
     */
    private Account curAccount;
    /**
     * current Transaction of Application.
     */
    private Transaction curTransaction;
    /**
     * List of Transaction for making report.
     */
    private List<Transaction> reportTransactions;
    /**
     * save Android Context as instance variable of MainModel.
     * @param pContext Android Context
     */
    public MainModel(Context pContext) {
        context = pContext;
    }
	
    @Override
    public void setCurUser(User user) {
        this.curUser = user;
    }
	
    @Override
    public ArrayList<Account> getCurUserAccounts() {
        return curUser.getList();
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
        ArrayList<Transaction> list = (ArrayList<Transaction>) account.getList();
        return list.remove(transaction);
    }
	
    @Override
    public boolean deleteAccount(User user, Account account) {
        ArrayList<Account> list = user.getList();
        return list.remove(account);
    }
	
    @Override
    public void setReportTransactions(List<Transaction> list) {
        this.reportTransactions = list;
    }
    
    @Override
    public List<Transaction> getReportTransactions() {
        return reportTransactions;
    }
}
