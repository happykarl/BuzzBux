package com.felicekarl.buzzbux.presenters;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.annotation.SuppressLint;
import com.felicekarl.buzzbux.models.Account;
import com.felicekarl.buzzbux.models.IModel;
import com.felicekarl.buzzbux.models.LocaleParser;
import com.felicekarl.buzzbux.models.Money;
import com.felicekarl.buzzbux.models.TransType;
import com.felicekarl.buzzbux.models.TransTypeParser;
import com.felicekarl.buzzbux.models.Transaction;
import com.felicekarl.buzzbux.models.User;
import com.felicekarl.buzzbux.views.IView;
/**
 * ServiceHolder to Parse Data from Server.
 * @author Karl
 *
 */
@SuppressLint("SimpleDateFormat") public class JsonParser {
	/**
	 * String "null".
	 */
    private static final String NULL = "null";
    /**
	 * String "Wrong username or password. Please try again.".
	 */
    private static final String ERROR_WRONG_USERNAME_PASS = "Wrong username or password. Please try again.";
    /**
	 * String "Network error. Check the connection please.".
	 */
    private static final String ERROR_NETWORK = "Network error. Check the connection please.";
    /**
	 * String "Username is already exists.".
	 */
    private static final String ERROR_USERNAME_EXIST = "Username is already exists.";
    /**
	 * String "Account name is already exists.".
	 */
    private static final String ERROR_ACCOUNTNAME_EXIST = "Account name is already exists.";
    /**
	 * String "Failed to make a transaction. Try again.".
	 */
    private static final String ERROR_MAKE_TRANSACTION = "Failed to make a transaction. Try again.";
    /**
	 * String "exist".
	 */
    private static final String EXIST = "exist";
    /**
	 * Date Format "yyyy-MM-dd".
	 */
    private static final String DATEFORMAT = "yyyy-MM-dd";
	/**
	 * Parse LogIn Info.
	 * @param str String to be parsed
	 * @param view View of MVP Model
	 * @param model Model of MVP Model
	 * @return true if successfully parsed.
	 */
    public static boolean parseLogIn(String str, IView view, IModel model) {
        if (isStringNull(str)) {
            return false;
        }
        try {
            if (str.toLowerCase(Locale.US).contains(NULL)) {
                view.setLogInErrorMsg(ERROR_WRONG_USERNAME_PASS);
                return false;
            } else {
                JSONObject jsonObj = new JSONObject(str);
                JSONArray usernames = jsonObj.getJSONArray(Network.TAG_OMA_USER);
                if (usernames.length() < 1) {
                    view.setLogInErrorMsg(ERROR_WRONG_USERNAME_PASS);
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
            view.setLogInErrorMsg(ERROR_NETWORK);
        }
        return false;
    }
    /**
	 * Parse Register Info.
	 * @param str String to be parsed
	 * @param view View of MVP Model
	 * @param model Model of MVP Model
	 * @return true if successfully parsed.
	 */
    public static boolean parseRegister(String str, IView view, IModel model) {
        if (isStringNull(str)) {
            return false;
        }
        try {
            if (str.toLowerCase(Locale.US).contains(EXIST)) {
                view.setRegisterErrorMsg(ERROR_USERNAME_EXIST);
                return false;
            } else {
                JSONObject jsonObj = new JSONObject(str);
                JSONArray usernames = jsonObj.getJSONArray(Network.TAG_OMA_USER);
                if (usernames.length() < 1) {
                    view.setRegisterErrorMsg(ERROR_USERNAME_EXIST);
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
            view.setRegisterErrorMsg(ERROR_NETWORK);
        }
        return false;
    }
    /**
	 * Parse Accounts Info.
	 * @param str String to be parsed
	 * @param view View of MVP Model
	 * @param model Model of MVP Model
	 * @return true if successfully parsed.
	 */
    public static boolean parseAccounts(String str, IView view, IModel model) {
        if (isStringNull(str)) {
            return false;
        }
        try {
            if (str.toLowerCase(Locale.US).contains(NULL)) {
                return true;
            } else {
                JSONObject jsonObj = new JSONObject(str);
                JSONArray accounts = jsonObj.getJSONArray(Network.TAG_OMA_ACCOUNT);
                if (accounts.length() < 1) {
                    return true;
                } else {
                    model.getCurUser().removeAllAccounts();
                    for (int i = 0; i < accounts.length(); i++) {
                        JSONObject account = accounts.getJSONObject(i);
                        String id = account.getString(Network.TAG_OMA_ACCOUNT_INDEX);
                        String name = account.getString(Network.TAG_OMA_ACCOUNT_NAME);
                        String description = account.getString(Network.TAG_OMA_ACCOUNT_DESCRIPTION);
                        String locale = account.getString(Network.TAG_OMA_ACCOUNT_LOCALE);
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
    /**
	 * Parse Transactions Info.
	 * @param account Account that want to add transaction
	 * @param str String to be parsed
	 * @param view View of MVP Model
	 * @param model Model of MVP Model
	 * @return true if successfully parsed.
	 */
    public static boolean parseTransactions(Account account, String str, IView view, IModel model) {
        if (isStringNull(str)) {
            return false;
        }
        try {
            if (str.toLowerCase(Locale.US).contains(NULL)) {
                return true;
            } else {
                JSONObject jsonObj = new JSONObject(str);
                JSONArray transactions = jsonObj.getJSONArray(Network.TAG_OMA_TRANSACTION);
                if (transactions.length() < 1) {
                    return true;
                } else {
                    DateFormat df = new SimpleDateFormat(DATEFORMAT);
                    account.removeAllItem();
                    for (int i = 0; i < transactions.length(); i++) {
                        JSONObject user = transactions.getJSONObject(i);
                        String index = user.getString(Network.TAG_OMA_TRANSACTION_INDEX);
                        //String accountId = user.getString(Network.TAG_OMA_TRANSACTION_ACCOUNT_ID);
                        TransType type = TransTypeParser.parseTransType(user.getString(Network.TAG_OMA_TRANSACTION_TYPE));
                        String description = user.getString(Network.TAG_OMA_TRANSACTION_DESC);
                        String dateStr = user.getString(Network.TAG_OMA_TRANSACTION_DATE);
                        Date date = null;
                        try {
                            date = df.parse(dateStr);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        int value = (int) Integer.valueOf(user.getString(Network.TAG_OMA_TRANSACTION_AMOUNT));
						// TODO: Hard coded locale
                        Money amount = new Money(Locale.US, value);
                        Transaction item = new Transaction(index, type, description, amount, date);
                        account.addItem(item);
                    }
                    return true;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }
    /**
	 * Parse Transactions Info.
	 * @param str String to be parsed
	 * @param view View of MVP Model
	 * @param model Model of MVP Model
	 * @return true if successfully parsed.
	 */
    public static boolean parseTransactions(String str, IView view, IModel model) {
        if (isStringNull(str)) {
            return false;
        }
        try {
            if (str.toLowerCase(Locale.US).contains(NULL)) {
                return true;
            } else {
                JSONObject jsonObj = new JSONObject(str);
                JSONArray transactions = jsonObj.getJSONArray(Network.TAG_OMA_TRANSACTION);
                if (transactions.length() < 1) {
                    return true;
                } else {
                    DateFormat df = new SimpleDateFormat(DATEFORMAT);
                    model.getCurAccount().removeAllItem();
                    for (int i = 0; i < transactions.length(); i++) {
                        JSONObject user = transactions.getJSONObject(i);
                        String index = user.getString(Network.TAG_OMA_TRANSACTION_INDEX);
                        //String accountId = user.getString(Network.TAG_OMA_TRANSACTION_ACCOUNT_ID);
                        TransType type = TransTypeParser.parseTransType(user.getString(Network.TAG_OMA_TRANSACTION_TYPE));
                        String description = user.getString(Network.TAG_OMA_TRANSACTION_DESC);
                        String dateStr = user.getString(Network.TAG_OMA_TRANSACTION_DATE);
                        Date date = null;
                        try {
                            date = df.parse(dateStr);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        int value = (int) Integer.valueOf(user.getString(Network.TAG_OMA_TRANSACTION_AMOUNT));
                        Money amount = new Money(model.getCurAccount().getLocale(), value);
                        Transaction item = new Transaction(index, type, description, amount, date);
                        model.getCurAccount().addItem(item);
                    }
                    return true;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }
    /**
	 * Parse AddAccount Info.
	 * @param str String to be parsed
	 * @param view View of MVP Model
	 * @param model Model of MVP Model
	 * @return true if successfully parsed.
	 */
    public static boolean parseAddAccount(String str, IView view, IModel model) {
        if (isStringNull(str)) {
            return false;
        }
        try {
            if ( str.toLowerCase(Locale.US).contains(EXIST) || str.toLowerCase(Locale.US).contains(NULL) ) {
                view.setAddAccountErrorMsg(ERROR_ACCOUNTNAME_EXIST);
                return false;
            } else {
                JSONObject jsonObj = new JSONObject(str);
                JSONArray accounts = jsonObj.getJSONArray(Network.TAG_OMA_ACCOUNT);
                if (accounts.length() < 1) {
                    view.setAddAccountErrorMsg(ERROR_ACCOUNTNAME_EXIST);
                    return false;
                } else {
                    for (int i = 0; i < accounts.length(); i++) {
                        JSONObject account = accounts.getJSONObject(i);
                        String id = account.getString(Network.TAG_OMA_ACCOUNT_INDEX);
                        String name = account.getString(Network.TAG_OMA_ACCOUNT_NAME);
                        String description = account.getString(Network.TAG_OMA_ACCOUNT_DESCRIPTION);
                        String locale = account.getString(Network.TAG_OMA_ACCOUNT_LOCALE);
                        int balance = (int) Integer.valueOf(account.getString(Network.TAG_OMA_ACCOUNT_BALANCE));
                        Account item = new Account(id, name, description, LocaleParser.parseLocale(locale), balance);
                        model.getCurUser().addAccount(item);
                    }
                    return true;
            	}
            } 
        } catch (JSONException e) {
            view.setAddAccountErrorMsg(ERROR_NETWORK);
        }
        return false;
    }
    /**
	 * Parse AddTransaction Info.
	 * @param str String to be parsed
	 * @param view View of MVP Model
	 * @param model Model of MVP Model
	 * @return true if successfully parsed.
	 */
    public static boolean parseAddTransaction(String str, IView view, IModel model) {
        if (isStringNull(str)) {
            return false;
        }
        try {
            if (str.toLowerCase(Locale.US).contains(NULL)) {
                view.setAddTransactionErrorMsg(ERROR_MAKE_TRANSACTION);
                return false;
            } else {
                JSONObject jsonObj = new JSONObject(str);
                JSONArray transactions = jsonObj.getJSONArray(Network.TAG_OMA_TRANSACTION);
                if (transactions.length() < 1) {
                    view.setAddTransactionErrorMsg(ERROR_MAKE_TRANSACTION);
                    return false;
                } else {
                    DateFormat df = new SimpleDateFormat(DATEFORMAT);
                    for (int i = 0; i < transactions.length(); i++) {
                        JSONObject user = transactions.getJSONObject(i);
                        String index = user.getString(Network.TAG_OMA_TRANSACTION_INDEX);
                        //String accountId = user.getString(Network.TAG_OMA_TRANSACTION_ACCOUNT_ID);
                        String type = user.getString(Network.TAG_OMA_TRANSACTION_TYPE);
                        String description = user.getString(Network.TAG_OMA_TRANSACTION_DESC);
                        int value = (int) Integer.valueOf(user.getString(Network.TAG_OMA_TRANSACTION_AMOUNT));
                        Money amount = new Money(model.getCurAccount().getLocale(), value);
                        String dateStr = user.getString(Network.TAG_OMA_TRANSACTION_DATE);
                        Date date = null;
                        try {
                            date = df.parse(dateStr);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        Transaction item = new Transaction(index, TransTypeParser.parseTransType(type), description, amount, date);
                        model.getCurAccount().addItem(item);
                    }
                    return true;
                }
            }
        } catch (JSONException e) {
            view.setAddTransactionErrorMsg(ERROR_NETWORK);
        }
        return false;
    }
    /**
	 * Parse EditTransaction Info.
	 * @param str String to be parsed
	 * @param view View of MVP Model
	 * @param model Model of MVP Model
	 * @return true if successfully parsed.
	 */
    public static boolean parseEditTransaction(String str, IView view, IModel model) {
        if (isStringNull(str)) {
            return false;
        }
        try {
            if (str.toLowerCase(Locale.US).contains(NULL)) {
                view.setAddTransactionErrorMsg(ERROR_MAKE_TRANSACTION);
                return false;
            } else {
                JSONObject jsonObj = new JSONObject(str);
                JSONArray transactions = jsonObj.getJSONArray(Network.TAG_OMA_TRANSACTION);
                if (transactions.length() < 1) {
                    view.setAddTransactionErrorMsg(ERROR_MAKE_TRANSACTION);
                    return false;
                } else {
                    DateFormat df = new SimpleDateFormat(DATEFORMAT);
                    for (int i = 0; i < transactions.length(); i++) {
                        JSONObject user = transactions.getJSONObject(i);
                        //String index = user.getString(Network.TAG_OMA_TRANSACTION_INDEX);
                        //String accountId = user.getString(Network.TAG_OMA_TRANSACTION_ACCOUNT_ID);
                        String type = user.getString(Network.TAG_OMA_TRANSACTION_TYPE);
                        String description = user.getString(Network.TAG_OMA_TRANSACTION_DESC);
                        int value = (int) Integer.valueOf(user.getString(Network.TAG_OMA_TRANSACTION_AMOUNT));
                        Money amount = new Money(model.getCurAccount().getLocale(), value);
                        String dateStr = user.getString(Network.TAG_OMA_TRANSACTION_DATE);
                        Date date = null;
                        try {
                            date = df.parse(dateStr);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        model.getCurTransaction().setType(TransTypeParser.parseTransType(type));
                        model.getCurTransaction().setDescription(description);
                        model.getCurTransaction().setAmount(amount);
                        model.getCurTransaction().setDate(date);
                    }
                    return true;
                }
            }
        } catch (JSONException e) {
            view.setAddTransactionErrorMsg(ERROR_NETWORK);
        }
        return false;
    }
    /**
	 * Parse Report Info.
	 * @param str String to be parsed
	 * @param view View of MVP Model
	 * @param model Model of MVP Model
	 * @return true if successfully parsed.
	 */
    public static boolean parseReport(String str, IView view, IModel model) {
        if (isStringNull(str)) {
            return false;
        }
        try {
            if (str.toLowerCase(Locale.US).contains(NULL)) {
                return false;
            } else {
                JSONObject jsonObj = new JSONObject(str);
                JSONArray transactions = jsonObj.getJSONArray(Network.TAG_OMA_TRANSACTION);
                if (transactions.length() < 1) {
                    view.setAddTransactionErrorMsg(ERROR_MAKE_TRANSACTION);
                    return false;
                } else {
                    List<Transaction> list = new ArrayList<Transaction>();
                    DateFormat df = new SimpleDateFormat(DATEFORMAT);
                    for (int i = 0; i < transactions.length(); i++) {
                        JSONObject user = transactions.getJSONObject(i);
                        String index = user.getString(Network.TAG_OMA_TRANSACTION_INDEX);
                        //String accountId = user.getString(Network.TAG_OMA_TRANSACTION_ACCOUNT_ID);
                        String type = user.getString(Network.TAG_OMA_TRANSACTION_TYPE);
                        String description = user.getString(Network.TAG_OMA_TRANSACTION_DESC);
                        int value = (int) Integer.valueOf(user.getString(Network.TAG_OMA_TRANSACTION_AMOUNT));
						// TODO: Hard coded locale
                        Money amount = new Money(Locale.US, value);
                        String dateStr = user.getString(Network.TAG_OMA_TRANSACTION_DATE);
                        Date date = null;
                        try {
                            date = df.parse(dateStr);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        Transaction item = new Transaction(index, TransTypeParser.parseTransType(type), 
								description, amount, date);
                        list.add(item);
                    }
                    model.setCurReportTransactions(list);
                    return true;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }
    /**
     * Check String is null.
     * @param str String to be checked
     * @return true if string is null
     */
    private static boolean isStringNull(String str) {
        if (str == null) {
            return true;
        }
        return false;
    }
}
