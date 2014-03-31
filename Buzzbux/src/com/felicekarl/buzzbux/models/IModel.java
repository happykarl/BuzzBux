package com.felicekarl.buzzbux.models;

import java.util.ArrayList;
import java.util.List;
/**
 * Interface for MainModel.
 * @author Karl
 *
 */
public interface IModel {
	/**
	 * Set current User of Application.
	 * @param user current User
	 */
    void setCurUser(User user);
    /**
     * Get current User of Application.
     * @return current User
     */
    User getCurUser();
    /**
     * Set current Account of Application.
     * @param account current Account of Application
     */
    void setCurAccount(Account account);
    /**
     * Get current Account of Application.
     * @return current Account of Application
     */
    Account getCurAccount();
    /**
     * Get List of Current User's Accounts.
     * @return List of Current User's Accounts
     */
    ArrayList<Account> getCurUserAccounts();
    /**
     * Set current Transaction of Application.
     * @param transaction current Transaction of Application
     */
    void setCurTransaction(Transaction transaction);
    /**
     * Get current Transaction of Application.
     * @return Get current Transaction of Application
     */
    Transaction getCurTransaction();
    /**
     * Delete Transaction from Account.
     * @param account Account which holds Transaction that want to be deleted
     * @param transaction Transaction item to be deleted
     * @return true if item is successfully deleted
     */
    boolean deleteTransaction(Account account, Transaction transaction);
    /**
     * Delete Account from User.
     * @param user User which holds Account that want to be deleted
     * @param account Account item to be deleted
     * @return true if item is successfully deleted
     */
    boolean deleteAccount(User user, Account account);
    /**
     * Set the list of Transaction for making report.
     * @param list list of Transaction to be shown on the report.
     */
    void setReportTransactions(List<Transaction> list);
    /**
     * Get the list of Transaction for making report.
     * @return list of Transaction for making report.
     */
    List<Transaction> getReportTransactions();
}