package com.felicekarl.buzzbux.presenters;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.felicekarl.buzzbux.R;
import com.felicekarl.buzzbux.presenters.tasks.AddAccountsTask;
import com.felicekarl.buzzbux.presenters.tasks.AddTransactionTask;
import com.felicekarl.buzzbux.presenters.tasks.DeleteAccountTask;
import com.felicekarl.buzzbux.presenters.tasks.DeleteTransactionTask;
import com.felicekarl.buzzbux.presenters.tasks.EditTransactionTask;
import com.felicekarl.buzzbux.presenters.tasks.GetAccountsTask;
import com.felicekarl.buzzbux.presenters.tasks.GetBalanceTask;
import com.felicekarl.buzzbux.presenters.tasks.GetTransactionsFromAccountsTask;
import com.felicekarl.buzzbux.presenters.tasks.GetTransactionsTask;
import com.felicekarl.buzzbux.presenters.tasks.LogInTask;
import com.felicekarl.buzzbux.presenters.tasks.RegisterTask;

import android.content.Context;
/**
 * Network.
 * @author Karl
 *
 */
public class Network {
	/**
	 * LogInTask.
	 */
    private LogInTask mLogInTask;
    /**
	 * RegisterTask.
	 */
    private RegisterTask mRegisterTask;
    /**
	 * GetAccountsTask.
	 */
    private GetAccountsTask mGetAccountsTask;
    /**
	 * GetTransactionsTask.
	 */
    private GetTransactionsTask mGetTransactionsTask;
    /**
	 * AddAccountsTask.
	 */
    private AddAccountsTask mAddAccountsTask;
    /**
	 * AddTransactionTask.
	 */
    private AddTransactionTask mAddTransactionTask;
    /**
	 * GetBalanceTask.
	 */
    private GetBalanceTask mGetBalanceTask;
    /**
	 * EditTransactionTask.
	 */
    private EditTransactionTask mEditTransactionTask;
    /**
	 * DeleteTransactionTask.
	 */
    private DeleteTransactionTask mDeleteTransactionTask;
    /**
	 * DeleteAccountTask.
	 */
    private DeleteAccountTask mDeleteAccountTask;
    /**
	 * GetTransactionsFromAccountsTask.
	 */
    private GetTransactionsFromAccountsTask mGetTransactionsFromAccountsTask;
	/**
	 * String loginPhp.
	 */
    private String loginPhp;
    /**
	 * String registerPhp.
	 */
    private String registerPhp;
    /**
	 * String addAccountPhp.
	 */
    private String addAccountPhp;
    /**
	 * String getAccountsPhp.
	 */
    private String getAccountsPhp;
    /**
	 * String getTransactionsPhp.
	 */
    private String getTransactionsPhp;
    /**
	 * String addTransactionPhp.
	 */
    private String addTransactionPhp;
    /**
	 * String getBalancePhp.
	 */
    private String getBalancePhp;
    /**
	 * String editTransactionPhp.
	 */
    private String editTransactionPhp;
    /**
	 * String deleteTransactionPhp.
	 */
    private String deleteTransactionPhp;
    /**
	 * String deleteAccountPhp.
	 */
    private String deleteAccountPhp;
    /**
	 * String getTransactionsFromAccountsPhp.
	 */
    private String getTransactionsFromAccountsPhp;
    /**
	 * String "oma_user".
	 */
    public static final String TAG_OMA_USER = "oma_user";
    /**
	 * String "username".
	 */
    public static final String TAG_OMA_USER_USERNAME = "username";
    /**
	 * String "password".
	 */
    public static final String TAG_OMA_USER_PASSWORD = "password";
    /**
	 * String "firstname".
	 */
    public static final String TAG_OMA_USER_FIRSTNAME = "firstname";
    /**
	 * String "lastname".
	 */
    public static final String TAG_OMA_USER_LASTNAME = "lastname";
    
    /**
	 * String "oma_account".
	 */
    public static final String TAG_OMA_ACCOUNT = "oma_account";
    /**
	 * String "index".
	 */
    public static final String TAG_OMA_ACCOUNT_INDEX = "index";
    /**
	 * String "name".
	 */
    public static final String TAG_OMA_ACCOUNT_NAME = "name";
    /**
	 * String "description".
	 */
    public static final String TAG_OMA_ACCOUNT_DESCRIPTION = "description";
    /**
	 * String "balance".
	 */
    public static final String TAG_OMA_ACCOUNT_BALANCE = "balance";
    /**
	 * String "locale".
	 */
    public static final String TAG_OMA_ACCOUNT_LOCALE = "locale";
	
    /**
	 * String "oma_transaction".
	 */
    public static final String TAG_OMA_TRANSACTION = "oma_transaction";
    /**
	 * String "index".
	 */
    public static final String TAG_OMA_TRANSACTION_INDEX = "index";
    /**
	 * String "parentaccount".
	 */
    public static final String TAG_OMA_TRANSACTION_ACCOUNT_ID = "parentaccount";
    /**
	 * String "type".
	 */
    public static final String TAG_OMA_TRANSACTION_TYPE = "type";
    /**
	 * String "sign".
	 */
    public static final String TAG_OMA_TRANSACTION_SIGN = "sign";
    /**
	 * String "amount".
	 */
    public static final String TAG_OMA_TRANSACTION_AMOUNT = "amount";
    /**
	 * String "diff".
	 */
    public static final String TAG_OMA_TRANSACTION_DIFF = "diff";
    /**
	 * String "description".
	 */
    public static final String TAG_OMA_TRANSACTION_DESC = "description";
    /**
	 * String "date".
	 */
    public static final String TAG_OMA_TRANSACTION_DATE = "date";
    /**
	 * String "datefrom".
	 */
    public static final String TAG_OMA_TRANSACTION_DATE_FROM = "datefrom";
    /**
	 * String "dateto".
	 */
    public static final String TAG_OMA_TRANSACTION_DATE_TO = "dateto";
    /**
	 * String "indeces[]".
	 */
    public static final String TAG_OMA_TRANSACTION_INDECES = "indeces[]";
	/**
	 * Initialize PHP file addresses.
	 * @param context Android Context
	 */
    public Network(Context context) {
        loginPhp = context.getResources().getString(R.string.login_php);
        registerPhp = context.getResources().getString(R.string.register_php);
        addAccountPhp = context.getResources().getString(R.string.add_account_php);
        getAccountsPhp = context.getResources().getString(R.string.get_accounts_php);
        getTransactionsPhp = context.getResources().getString(R.string.get_transactions_php);
        addTransactionPhp = context.getResources().getString(R.string.add_transaction_php);
        getBalancePhp = context.getResources().getString(R.string.get_balance_php);
        editTransactionPhp = context.getResources().getString(R.string.edit_transaction_php);
        deleteTransactionPhp = context.getResources().getString(R.string.delete_transaction_php);
        deleteAccountPhp = context.getResources().getString(R.string.delete_account_php);
        getTransactionsFromAccountsPhp = context.getResources().getString(R.string.get_transactions_from_accounts_php);
    }
	
	/**
	 * Send user's input (username and password) to AsyncTask.
	 * In case of something wrong happens, it returns null and JSonParser will handle error.
	 * @param username username for User Account
	 * @param password password for User Account
	 * @return return the result of LogIn AsyncTask. If succeed, it will return the JSon data for user account.
	 */
    public String submitLogIn(String username, String password) {
        mLogInTask = new LogInTask();
        mLogInTask.execute(new String[] {loginPhp, username, password});
        try {
            return mLogInTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        } catch (ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
	 * Send user's input (username and password) to AsyncTask.
	 * In case of something wrong happens, it returns null and JSonParser will handle error.
	 * @param username username for User Account
	 * @param password password for User Account
	 * @param firstname first name
	 * @param lastname last name
	 * @return return the result of Register AsyncTask. If succeed, it will return the JSon data for user account.
	 */
    public String submitRegister(String username, String password, String firstname, String lastname) {
        mRegisterTask = new RegisterTask();
        mRegisterTask.execute(new String[] {registerPhp, username, password, firstname, lastname});
        try {
            return mRegisterTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
	 * Send user's input (username and password) to AsyncTask.
	 * In case of something wrong happens, it returns null and JSonParser will handle error.
	 * @param username username for User Account
	 * @return return the result of GetAccounts AsyncTask. If succeed, it will return the JSon data for user account.
	 */
    public String getAccounts(String username) {
        mGetAccountsTask = new GetAccountsTask();
        mGetAccountsTask.execute(new String[] {getAccountsPhp, username});
        try {
            return mGetAccountsTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
	 * Send account id to AsyncTask.
	 * In case of something wrong happens, it returns null and JSonParser will handle error.
	 * @param accountId id of Account
	 * @return return the result of GetTransactions AsyncTask. If succeed, it will return the JSon data for user account.
	 */
    public String getTransactions(String accountId) {
        mGetTransactionsTask = new GetTransactionsTask();
        mGetTransactionsTask.execute(new String[] {getTransactionsPhp, accountId});
        try {
            return mGetTransactionsTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * Send infos to add Account.
     * @param username username want to add account
     * @param accountName Account Name
     * @param accountDescription Description of Account
     * @param locale Currency Info of Account
     * @return the result of AddAccounts AsyncTask. If succeed, it will return the JSon data for user account.
     */
    public String addAccount(String username, String accountName, String accountDescription, String locale) {
        mAddAccountsTask = new AddAccountsTask();
        mAddAccountsTask.execute(new String[] {addAccountPhp, username, accountName, accountDescription, locale});
        try {
            return mAddAccountsTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }
	/**
	 * Send infos to add Transaction.
	 * @param accountId Account Id want to add the transaction.
	 * @param transType Transaction Type
	 * @param sign + if transaction is income, - if trnasaction is expense
	 * @param amount amount of money
	 * @param description description of Transaction
	 * @param date date of Transaction
	 * @return result of server of AddTransactionTask
	 */
    public String addTransaction(String accountId, String transType, String sign, String amount, String description, String date) {
        mAddTransactionTask = new AddTransactionTask();
        mAddTransactionTask.execute(new String[] {addTransactionPhp, accountId, transType, sign, amount, description, date});
        try {
            return mAddTransactionTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }
	/**
	 * Send Account Id to get Balance of that Account.
	 * @param accountId Account Id
	 * @return value of balance if GetBalanceTask successfully get data from server.
	 */
    public String getBalance(String accountId) {
        mGetBalanceTask = new GetBalanceTask(); 
        mGetBalanceTask.execute(new String[] {getBalancePhp, accountId});
        try {
            return mGetBalanceTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }
	/**
	 * Send following info to edit Transaction.
	 * @param transactionId Transaction Id want to edit
	 * @param transType new Transaction type
	 * @param accountId account id to change balance
	 * @param amount new amount of money
	 * @param diff difference between previous money and new money
	 * @param description new description of transaction
	 * @param date new date of transaction
	 * @return result of server of EditTransactionTask
	 */
    public String editTransaction(String transactionId, String transType, String accountId, String amount, String diff, String description, String date) {
        mEditTransactionTask = new EditTransactionTask();
        mEditTransactionTask.execute(new String[] {editTransactionPhp, transactionId, transType, accountId, amount, diff, description, date});
        try {
            return mEditTransactionTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }
	/**
	 * Send following info to DeleteTransactionTask.
	 * @param transactionId Transaction Id
	 * @param accountId Account Id
	 * @param diff difference of Money to change the balance of Account
	 * @return result of DeleteTransactionTask
	 */
    public String deleteTransaction(String transactionId, String accountId, String diff) {
        mDeleteTransactionTask = new DeleteTransactionTask();
        mDeleteTransactionTask.execute(new String[] {deleteTransactionPhp, transactionId, accountId, diff});
        try {
            return mDeleteTransactionTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }
	/**
	 * Send Account Id to delete Account.
	 * @param accountId Account Id
	 * @return result of DeleteAccountTask
	 */
    public String deleteAccount(String accountId) {
        mDeleteAccountTask = new DeleteAccountTask();
        mDeleteAccountTask.execute(new String[] {deleteAccountPhp, accountId});
        try {
            return mDeleteAccountTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * Send following info to get Transactions.
     * @param list list of Account to get all transaction
     * @param dateFrom Starting date
     * @param dateTo End Date
     * @return result of GetTransactionsFromAccountsTask
     */
    public String getTransactions(List<Integer> list, String dateFrom, String dateTo) {
        String[] params = new String[list.size() + 3];
        params[0] = getTransactionsFromAccountsPhp;
        params[1] = dateFrom;
        params[2] = dateTo;
        for (int i = 0; i < list.size(); i++) {
            params[3 + i] = String.valueOf(list.get(i));
        }
        mGetTransactionsFromAccountsTask = new GetTransactionsFromAccountsTask();
        mGetTransactionsFromAccountsTask.execute(params);
        try {
            return mGetTransactionsFromAccountsTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }
}
