package com.felicekarl.buzzbux.presenters;

import java.util.concurrent.ExecutionException;

import com.felicekarl.buzzbux.R;
import com.felicekarl.buzzbux.presenters.tasks.*;

import android.content.Context;

public class Network {
	private static final String TAG = Network.class.getSimpleName();
	
	private LogInTask mLogInTask;
	private RegisterTask mRegisterTask;
	private GetAccountsTask mGetAccountsTask;
	private GetTransactionsTask mGetTransactionsTask;
	
	private String login_php;
	private String register_php;
	private String add_account_php;
	private String get_accounts_php;
	private String get_transactions_php;
	private String add_transaction_php;
	private String get_balance_php;
	private String get_transaction_from_username_php;
	
	public static final String TAG_OMA_USER = "oma_user";
	public static final String TAG_OMA_USER_USERNAME = "username";
	public static final String TAG_OMA_USER_PASSWORD = "password";
	public static final String TAG_OMA_USER_FIRSTNAME = "firstname";
	public static final String TAG_OMA_USER_LASTNAME = "lastname";
	
	public static final String TAG_OMA_ACCOUNT = "oma_account";
	public static final String TAG_OMA_ACCOUNT_INDEX = "index";
	public static final String TAG_OMA_ACCOUNT_NAME = "name";
	public static final String TAG_OMA_ACCOUNT_DESCRIPTION = "description";
	public static final String TAG_OMA_ACCOUNT_BALANCE = "balance";
	public static final String TAG_OMA_ACCOUNT_LOCALE = "locale";
	
	public static final String TAG_OMA_TRANSACTION = "oma_transaction";
	public static final String TAG_OMA_TRANSACTION_INDEX = "index";
	public static final String TAG_OMA_TRANSACTION_BANKACCOUNT_ID = "bankaccountid";
	public static final String TAG_OMA_TRANSACTION_TYPE = "type";
	public static final String TAG_OMA_TRANSACTION_AMOUNT = "amount";
	public static final String TAG_OMA_TRANSACTION_DESC = "description";
	public static final String TAG_OMA_TRANSACTION_DATE = "date";
	
	public Network(Context context) {
		login_php = context.getResources().getString(R.string.login_php);
		register_php = context.getResources().getString(R.string.register_php);
		add_account_php = context.getResources().getString(R.string.add_account_php);
		get_accounts_php = context.getResources().getString(R.string.get_accounts_php);
		get_transactions_php = context.getResources().getString(R.string.get_transactions_php);
		add_transaction_php = context.getResources().getString(R.string.add_transaction_php);
		get_balance_php = context.getResources().getString(R.string.get_balance_php);
		get_transaction_from_username_php = context.getResources().getString(R.string.get_transaction_from_username_php);
	}
	
	public String submitLogIn(String username, String password) {
		mLogInTask = new LogInTask();
		mLogInTask.execute(new String[] {login_php, username, password});
		try {
			//Log.d(TAG, "result: " + login_task.get());
			return mLogInTask.get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public String submitRegister(String username, String password, String firstname, String lastname) {
		mRegisterTask = new RegisterTask();
		mRegisterTask.execute(new String[] {register_php, username, password, firstname, lastname});
		try {
			//Log.d(TAG, "result: " + login_task.get());
			return mRegisterTask.get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public String getAccounts(String username) {
		mGetAccountsTask = new GetAccountsTask();
		mGetAccountsTask.execute(new String[] {get_accounts_php, username});
		try {
			//Log.d(TAG, "result: " + login_task.get());
			return mGetAccountsTask.get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public String getTransactions(String accountId) {
		mGetTransactionsTask = new GetTransactionsTask();
		mGetTransactionsTask.execute(new String[] {get_transactions_php, accountId});
		try {
			//Log.d(TAG, "result: " + login_task.get());
			return mGetTransactionsTask.get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}
