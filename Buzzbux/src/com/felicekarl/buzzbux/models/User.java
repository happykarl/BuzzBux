package com.felicekarl.buzzbux.models;

import java.util.ArrayList;
/**
 * User.
 * @author Karl
 *
 */
public class User {
	/**
	 * Username.
	 */
    private String username;
    /**
     * Fisrt Name.
     */
    private String firstname;
    /**
     * Last Name.
     */
    private String lastname;
    /**
     * List of Account that User holding.
     */
    private ArrayList<Account> list;
	/**
	 * Initiate List of Account.
	 */
    public User() {
        this.list = new ArrayList<Account>();
    }
	/**
	 * Initiate instance with following info.
	 * @param pUsername username
	 * @param pFirstname fisrt name
	 * @param pLastname last name
	 */
    public User(String pUsername, String pFirstname, String pLastname) {
        this();
        username = pUsername;
        firstname = pFirstname;
        lastname = pLastname;
    }
	/**
	 * Set Username.
	 * @param pUsername username.
	 */
    public void setUsername(String pUsername) {
        username = pUsername;
    }
	/**
	 * Set First Name.
	 * @param pFirstname fisrt name
	 */
    public void setFirstname(String pFirstname) {
        firstname = pFirstname;
    }
	/**
	 * Set Last Name.
	 * @param pLastname last name
	 */
    public void setLastname(String pLastname) {
        lastname = pLastname;
    }
	/**
	 * Get username.
	 * @return username
	 */
    public String getUsername() {
        return this.username;
    }
	/**
	 * Get First Name.
	 * @return first name
	 */
    public String getFirstname() {
        return this.firstname;
    }
	/**
	 * Get Last Name.
	 * @return last name
	 */
    public String getLastName() {
        return this.lastname;
    }
	/**
	 * Add Account item into the list.
	 * @param item Account item
	 */
    public void addAccount(Account item) {
        this.list.add(item);
    }
	/**
	 * Delete Account from the List.
	 * @param item Account item want to be deleted.
	 * @return true is item is successfully deleted
	 */
    public boolean removeAccount(Account item) {
        return this.list.remove(item);
    }
	/**
	 * Remove all Account items in the list.
	 */
    public void removeAllAccounts() {
        list = new ArrayList<Account>();
    }
	/**
	 * Get All the items of Account.
	 * @return list of Accounts
	 */
    public ArrayList<Account> getAccounts() {
        return list;
    }
	 
}
