package com.felicekarl.buzzbux.presenters;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.felicekarl.buzzbux.models.Account;
import com.felicekarl.buzzbux.models.IModel;
import com.felicekarl.buzzbux.models.LocaleParser;
import com.felicekarl.buzzbux.models.Money;
import com.felicekarl.buzzbux.models.TransType;
import com.felicekarl.buzzbux.models.TransTypeParser;
import com.felicekarl.buzzbux.models.Transaction;
import com.felicekarl.buzzbux.models.User;
import com.felicekarl.buzzbux.views.IView;

public class JsonParser {
	private static final String TAG = JsonParser.class.getSimpleName();
	
	public static boolean parseLogIn(String str, IView view, IModel model) {
		if (str == null)	return false;
		try {
			if (str.toLowerCase(Locale.US).contains("null")) {
				view.setLogInErrorMsg("Wrong username or password. Please try again.");
				return false;
			} else {
				JSONObject jsonObj = new JSONObject(str);
				JSONArray usernames = jsonObj.getJSONArray(Network.TAG_OMA_USER);
				if (usernames.length() < 1) {
					view.setLogInErrorMsg("Wrong username or password. Please try again.");
					return false;
				} else {
					JSONObject user = usernames.getJSONObject(0);
					String username = user.getString(Network.TAG_OMA_USER_USERNAME);
					String firstname = user.getString(Network.TAG_OMA_USER_FIRSTNAME);
					String lastname = user.getString(Network.TAG_OMA_USER_LASTNAME);
					User mUser = new User(username, firstname, lastname);
					model.setCurUser(mUser);
					return true;
				}
			}
		} catch (JSONException e) {
			view.setLogInErrorMsg("Network error. Check the connection please.");
		}
		return false;
	}
	
	public static boolean parseRegister(String str, IView view, IModel model) {
		if (str == null)	return false;
		try {
			if (str.toLowerCase(Locale.US).contains("exist")) {
				view.setRegisterErrorMsg("Username is already exists.");
				return false;
			} else {
				JSONObject jsonObj = new JSONObject(str);
				JSONArray usernames = jsonObj.getJSONArray(Network.TAG_OMA_USER);
				if (usernames.length() < 1) {
					view.setRegisterErrorMsg("Username is already exists.");
					return false;
				} else {
					JSONObject user = usernames.getJSONObject(0);
					String username = user.getString(Network.TAG_OMA_USER_USERNAME);
					String firstname = user.getString(Network.TAG_OMA_USER_FIRSTNAME);
					String lastname = user.getString(Network.TAG_OMA_USER_LASTNAME);
					User mUser = new User(username, firstname, lastname);
					model.setCurUser(mUser);
					Log.d(TAG, mUser.toString());
					return true;
				}
			}
		} catch (JSONException e) {
			view.setRegisterErrorMsg("Network error. Check the connection please.");
		}
		return false;
	}
	
	public static boolean parseAccounts(String str, IView view, IModel model) {
		if (str == null)	return false;
		try {
			if (str.toLowerCase(Locale.US).contains("null")) {
				return true;
			} else {
				JSONObject jsonObj = new JSONObject(str);
				JSONArray accounts = jsonObj.getJSONArray(Network.TAG_OMA_ACCOUNT);
				if (accounts.length() < 1) {
					return true;
				} else {
					model.getCurUser().removeAllAccounts();
					for (int i=0; i<accounts.length(); i++) {
						JSONObject account = accounts.getJSONObject(i);
						String id = account.getString(Network.TAG_OMA_ACCOUNT_INDEX);
						String name = account.getString(Network.TAG_OMA_ACCOUNT_NAME);
						String description = account.getString(Network.TAG_OMA_ACCOUNT_DESCRIPTION);
						String locale = account.getString(Network.TAG_OMA_ACCOUNT_LOCALE);
						Log.d(TAG, "locale: " + locale.toString());
						Log.d(TAG, "account.getString(TAG_OMA_BANK_BALANCE): " + account.getString(Network.TAG_OMA_ACCOUNT_BALANCE));
						int balance = (int) Integer.valueOf(account.getString(Network.TAG_OMA_ACCOUNT_BALANCE));
						Account item = new Account(id, name, description, LocaleParser.parseLocale(locale), balance);
						model.getCurUser().addAccount(item);
					}
					return true;
				}
			}
		} catch (JSONException e) {
			
		}
		return false;
	}
	
	public static boolean parseTransactions(String str, IView view, IModel model) {
		if (str == null)	return false;
		try {
			if (str.toLowerCase(Locale.US).contains("null")) {
				return true;
			} else {
				JSONObject jsonObj = new JSONObject(str);
				JSONArray transactions = jsonObj.getJSONArray(Network.TAG_OMA_TRANSACTION);
				if (transactions.length() < 1) {
					return true;
				} else {
					DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
					model.getCurAccount().removeAllItem();
					for (int i=0; i<transactions.length(); i++) {
						JSONObject user = transactions.getJSONObject(i);
						String index = user.getString(Network.TAG_OMA_TRANSACTION_INDEX);
						String accountId = user.getString(Network.TAG_OMA_TRANSACTION_ACCOUNT_ID);
						TransType type = TransTypeParser.parseTransType(user.getString(Network.TAG_OMA_TRANSACTION_TYPE));
						String description = user.getString(Network.TAG_OMA_TRANSACTION_DESC);
						String dateStr = user.getString(Network.TAG_OMA_TRANSACTION_DATE);
						Date date = null;
						try {
							date = df.parse(dateStr);
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						int value = (int) Integer.valueOf(user.getString(Network.TAG_OMA_TRANSACTION_AMOUNT));
						Money amount = new Money(model.getCurAccount().getLocale(), value);
						Log.d(TAG, "amount: " + amount);
						Transaction item = new Transaction(index, type, description, amount, date);
						//Log.d(TAG, "name: " + name);
						model.getCurAccount().addItem(item);
					}
					return true;
				}
			}
		} catch (JSONException e) {
			
		}
		return false;
	}
	
	public static boolean parseAddAccount(String str, IView view, IModel model) {
		if (str == null)	return false;
		try {
			if ( str.toLowerCase(Locale.US).contains("exist") ||
					str.toLowerCase(Locale.US).contains("null") ) {
				view.setAddAccountErrorMsg("Account name is already exists.");
				return false;
			} else {
				JSONObject jsonObj = new JSONObject(str);
				JSONArray accounts = jsonObj.getJSONArray(Network.TAG_OMA_ACCOUNT);
				if (accounts.length() < 1) {
					view.setAddAccountErrorMsg("Account name is already exists.");
					return false;
				} else {
					for (int i=0; i<accounts.length(); i++) {
						JSONObject account = accounts.getJSONObject(i);
						String id = account.getString(Network.TAG_OMA_ACCOUNT_INDEX);
						String name = account.getString(Network.TAG_OMA_ACCOUNT_NAME);
						String description = account.getString(Network.TAG_OMA_ACCOUNT_DESCRIPTION);
						String locale = account.getString(Network.TAG_OMA_ACCOUNT_LOCALE);
						Log.d(TAG, "locale: " + locale.toString());
						Log.d(TAG, "account.getString(TAG_OMA_BANK_BALANCE): " + account.getString(Network.TAG_OMA_ACCOUNT_BALANCE));
						int balance = (int) Integer.valueOf(account.getString(Network.TAG_OMA_ACCOUNT_BALANCE));
						Account item = new Account(id, name, description, LocaleParser.parseLocale(locale), balance);
						model.getCurUser().addAccount(item);
					}
					return true;
				}
			} 
		} catch (JSONException e) {
			view.setAddAccountErrorMsg("Network error. Check the connection please.");
		}
		return false;
	}
	
	public static boolean parseAddTransaction(String str, IView view, IModel model) {
		if (str == null)	return false;
		try {
			if (str.toLowerCase(Locale.US).contains("null")) {
				view.setAddTransactionErrorMsg("Failed to make a transaction. Try again.");
				return false;
			} else {
				JSONObject jsonObj = new JSONObject(str);
				JSONArray transactions = jsonObj.getJSONArray(Network.TAG_OMA_TRANSACTION);
				if (transactions.length() < 1) {
					view.setAddTransactionErrorMsg("Failed to make a transaction. Try again.");
					return false;
				} else {
					DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
					for (int i=0; i<transactions.length(); i++) {
						JSONObject user = transactions.getJSONObject(i);
						String index = user.getString(Network.TAG_OMA_TRANSACTION_INDEX);
						String accountId = user.getString(Network.TAG_OMA_TRANSACTION_ACCOUNT_ID);
						String type = user.getString(Network.TAG_OMA_TRANSACTION_TYPE);
						String description = user.getString(Network.TAG_OMA_TRANSACTION_DESC);
						int value = (int) Integer.valueOf(user.getString(Network.TAG_OMA_TRANSACTION_AMOUNT));
						Money amount = new Money(model.getCurAccount().getLocale(), value);
						String dateStr = user.getString(Network.TAG_OMA_TRANSACTION_DATE);
						Date date = null;
						try {
							date = df.parse(dateStr);
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						Log.d(TAG, "amount: " + amount.toString());
						Transaction item = new Transaction(index, TransTypeParser.parseTransType(type), 
								description, amount, date);
						//Log.d(TAG, "name: " + name);
						model.getCurAccount().addItem(item);
					}
					return true;
				}
			}
		} catch (JSONException e) {
			view.setAddTransactionErrorMsg("Network error. Check the connection please.");
		}
		return false;
	}
	
	public static boolean parseEditTransaction(String str, IView view, IModel model) {
		if (str == null)	return false;
		try {
			if (str.toLowerCase(Locale.US).contains("null")) {
				view.setAddTransactionErrorMsg("Failed to make a transaction. Try again.");
				return false;
			} else {
				JSONObject jsonObj = new JSONObject(str);
				JSONArray transactions = jsonObj.getJSONArray(Network.TAG_OMA_TRANSACTION);
				if (transactions.length() < 1) {
					view.setAddTransactionErrorMsg("Failed to make a transaction. Try again.");
					return false;
				} else {
					DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
					for (int i=0; i<transactions.length(); i++) {
						JSONObject user = transactions.getJSONObject(i);
						String index = user.getString(Network.TAG_OMA_TRANSACTION_INDEX);
						String accountId = user.getString(Network.TAG_OMA_TRANSACTION_ACCOUNT_ID);
						String type = user.getString(Network.TAG_OMA_TRANSACTION_TYPE);
						String description = user.getString(Network.TAG_OMA_TRANSACTION_DESC);
						int value = (int) Integer.valueOf(user.getString(Network.TAG_OMA_TRANSACTION_AMOUNT));
						Money amount = new Money(model.getCurAccount().getLocale(), value);
						String dateStr = user.getString(Network.TAG_OMA_TRANSACTION_DATE);
						Date date = null;
						try {
							date = df.parse(dateStr);
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
//						Log.d(TAG, "amount: " + amount.toString());
//						Transaction item = new Transaction(index, TransTypeParser.parseTransType(type), 
//								description, amount, date);
//						//Log.d(TAG, "name: " + name);
						model.getCurTransaction().setType(TransTypeParser.parseTransType(type));
						model.getCurTransaction().setDescription(description);
						model.getCurTransaction().setAmount(amount);
						model.getCurTransaction().setDate(date);
						//model.getCurAccount().addItem(item);
					}
					return true;
				}
			}
		} catch (JSONException e) {
			view.setAddTransactionErrorMsg("Network error. Check the connection please.");
		}
		return false;
	}
}
