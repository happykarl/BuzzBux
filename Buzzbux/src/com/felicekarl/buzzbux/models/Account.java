package com.felicekarl.buzzbux.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
/**
 * Account.
 * @author Karl
 *
 */
public class Account implements Comparable {
	/**
	 * Account Id (unique).
	 */
    private String id;
    /**
     * Account name.
     */
    private String name;
    /**
     * Account Description.
     */
    private String description;
    /**
     * Account Balance.
     */
    private Money balance;
    /**
     * Account Locale.
     */
    private Locale locale;
    /**
     * Transaction List of Account.
     */
    private List<Transaction> list;
	
    /**
     * Initialize ArrayList of Transaction.
     */
    public Account() {
        this.list = new ArrayList<Transaction>();
    }
    /**
     * Intialize Account instance with following info.
     * @param pId Account Id
     * @param pName Account Name
     * @param pDescription Account Description
     * @param pLocale Account Locale
     * @param pValue Raw data of balance of Account
     */
    public Account(String pId, String pName, String pDescription, Locale pLocale, int pValue) {
	    this();
        id = pId;
        name = pName;
        description = pDescription;
        locale = pLocale;
        balance = new Money(locale, pValue);
    }
	/**
	 * Set Locale Of Account.
	 * @param pLocale Locale of Account
	 */
    public void setLocale(Locale pLocale) {
    	locale = pLocale;
    }
	/**
	 * Return current Locale of Account.
	 * @return current Lcale of Account
	 */
    public Locale getLocale() {
        return locale;
    }
	/**
	 * Add transaction item on the list.
	 * @param pItem Transaction instance.
	 */
    public void addItem(Transaction pItem) {
        list.add(pItem);
    }
	/**
	 * remove Transaction item from the list.	
	 * @param pItem Transaction item want to be deleted
	 * @return true if item is successfully deleted
	 */
    public boolean removeItem(Transaction pItem) {
        return list.remove(pItem);
    }
	/**
	 * Clear all the item in list of Transaction.
	 */
    public void removeAllItem() {
        this.list = new ArrayList<Transaction>();
    }
	/**
	 * Return name of Account.
	 * @return name of Account
	 */
    public String getName() {
        return name;
    }
	/**
	 * Return description of Account.
	 * @return description of Account
	 */
    public String getDescription() {
        return description;
    }
	/**
	 * Return Id (unique) of Account.
	 * @return Id (unique) of Account
	 */
    public String getId() {
        return id;
    }
	/**
	 * Return balance of Account.
	 * @return balance of Account
	 */
    public Money getBalance() {
        return balance;
    }
	/**
	 * Set the Balance of Account.
	 * @param pBalance balance of Account
	 */
    public void setBalance(Money pBalance) {
        balance = pBalance;
    }
	/**
	 * Return the List of Transaction.
	 * @return List of Transaction.
	 */
    public List<Transaction> getTransactions() {
        return list;
    }
    @Override
    public int compareTo(Object obj) {
        return this.name.compareTo(((Account) obj).name);
    }
}
