package com.felicekarl.buzzbux.presenters;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import com.felicekarl.buzzbux.R;
import com.felicekarl.buzzbux.listeners.*;
import com.felicekarl.buzzbux.models.*;
import com.felicekarl.buzzbux.presenters.adapters.*;
import com.felicekarl.buzzbux.views.*;

import android.content.Context;
import android.util.Log;

public class MainPresenter implements Runnable {
	private static final String TAG = MainPresenter.class.getSimpleName();
	private static final int SPLASH_TIME = 2500;
	private int timeElapsed = 0;
	private boolean isLoaded = false;
	private Thread thread;
	
	private Context context;
	private IView view;
	private IModel model;
	
	private Network network;
	
	public MainPresenter(Context context, IView view, IModel model){
		this.context = context;
		this.view = view;
		this.model = model;
		this.view.setView(TypeView.SPLASH);
		
		this.network = new Network(context);
		
		initListeners();
		
		/* splash fragment timer */
		thread = new Thread(this);
        thread.start();
	}
	
	private void initListeners() {
		/* FooterFragment button click listeners */
		view.updateFooterFragmentButtonListener(new FooterFragmentButtonListener() {
			@Override
			public void addItem() {
				if (view.getView().equals(TypeView.MANAGEACCOUNT)) {
					view.setView(TypeView.ADDACCOUNT);
					view.setTitle("Add Account");
				} else if (view.getView().equals(TypeView.MANAGETRANSACTION)) {
					view.setView(TypeView.ADDTRANSACTION);
					view.setAddTransactionCurrency(model.getCurAccount().getLocale().toString());
					view.setTitle("Add Transaction");
				}
			}

			@Override
			public void editItem() {
				if (view.getView().equals(TypeView.MANAGEACCOUNT)) {
					
				} else if (view.getView().equals(TypeView.MANAGETRANSACTION)) {
					if (model.getCurTransaction() != null) {
						view.setView(TypeView.EDITTRANSACTION);
						view.setEditTransactionCurrency(model.getCurAccount().getLocale().toString());
						view.fillEditTransactionForm(model.getCurTransaction());
						view.setTitle("Edit Transaction");
					}
				}
				
			}
		});
		
		/* LogInFragment button click listeners */
		view.updateLogInFragmentButtonListener(new LogInFragmentButtonListener() {
			@Override
			public void submitRegister() {
				view.setView(TypeView.REGISTER);
				view.setTitle("Register");
			}
			@Override
			public void submitLogIn(String username, String password) {
				view.startSpinner();
				if (JsonParser.parseLogIn(network.submitLogIn(username, password), view, model)) {
					view.setView(TypeView.DASHBOARD);
					view.setTitle("Dashboard");
				} else {
					view.enablButtonListener();
				}
				view.stopSpinner();
			}
		});
		
		/* RegisterFragment button click listeners */
		view.updateRegisterFragmentButtonListener(new RegisterFragmentButtonListener() {
			@Override
			public void submitRegister(String username, String password, String firstname, String lastname) {
				view.startSpinner();
				if (JsonParser.parseRegister(network.submitRegister(username, password, firstname, lastname), view, model)) {
					view.setView(TypeView.DASHBOARD);
					view.setTitle("Dashboard");
				} else {
					view.enablButtonListener();
				}
				view.stopSpinner();
			}
			@Override
			public void cancel() {
				view.setView(TypeView.LOGIN);
				view.setTitle("Log-In");
			}
		});
		
		/* Dashboard button click listeners */
		view.updateDashboardFragmentButtonListener(new DashboardFragmentButtonListener() {
			@Override
			public void submitManageUser() {
				view.startSpinner();
				view.enablButtonListener();
				view.stopSpinner();
			}
			@Override
			public void submitManageAccount() {
				view.startSpinner();
				if (JsonParser.parseAccounts(network.getAccounts(
						model.getCurUser().getUsername()), view, model)) {
					// update list
					Collections.sort(model.getCurUserAccounts());
					view.getManageAccountListView().setAdapter(new ArrayAdapterAccountItem(
							context, R.layout.fragment_manage_account_item, 
							model.getCurUserAccounts()));
					view.setView(TypeView.MANAGEACCOUNT);
					view.setTitle("Manage Account");
				} else {
					view.enablButtonListener();
				}
				view.stopSpinner();
			}
			@Override
			public void submitReportTransaction() {
				view.startSpinner();
				view.enablButtonListener();
				view.stopSpinner();
			}
			@Override
			public void submitSettings() {
				view.enablButtonListener();
			}
		});
		
		/* ManageAccountFragment button click listeners */
		view.updateManageAccountFragmentButtonListener(new ManageAccountFragmentButtonListener() {
			@Override
			public void selectAccount(int position) {
				view.startSpinner();
				// set cur account as selected one
				Account account = model.getCurUser().getAccounts().get(position);
				model.setCurAccount(account);
				// reset cur transaction as null
				model.setCurTransaction(null);
				if (JsonParser.parseTransactions(network.getTransactions(account.getId()), view, model)) {
					// update list
					Collections.sort(model.getCurAccount().getTransactions());
					view.getManageTransactionListView().setAdapter(new ArrayAdapterTransactionItem(
							context, R.layout.fragment_manage_transaction_item, 
							model.getCurAccount().getTransactions()));
					// update balance in ManageTransaction view
					view.setManageTransactionBalance(model.getCurAccount().getBalance().toString());
					// change view
					view.setView(TypeView.MANAGETRANSACTION);
					view.setTitle(model.getCurAccount().getName());
				} else {
					view.enablButtonListener();
				}
				view.stopSpinner();
			}
		});
		
		/* AddAccountFragment button click listeners */
		view.updateAddAccountFragmentButtonListener(new AddAccountFragmentButtonListener() {
			@Override
			public void submit(String accountName, String accountDescription, String currency) {
				//Log.d(TAG, "accountName: " + accountName + " | accountDescription: " + accountDescription + " | currency: " + currency);
				view.startSpinner();
				if (JsonParser.parseAddAccount(network.addAccount(model.getCurUser().getUsername(), 
						accountName, accountDescription, LocaleParser.parseLocale(currency).toString()), 
						view, model)) {
					// update list
					Collections.sort(model.getCurUserAccounts());
					view.getManageAccountListView().setAdapter(new ArrayAdapterAccountItem(
							context, R.layout.fragment_manage_account_item, 
							model.getCurUserAccounts()));
					view.setView(TypeView.MANAGEACCOUNT);
					view.setTitle("Manage Account");
				} else {
					view.enablButtonListener();
				}
				view.stopSpinner();
			}
			@Override
			public void cancel() {
				view.setView(TypeView.MANAGEACCOUNT);
				view.setTitle("Manage Account");
			}
		});
		
		/* AddTransactionFragment button click listeners */
		view.updateAddTransactionFragmentButtonListener(new AddTransactionFragmentButtonListener() {
			@Override
			public void submit(String transType, String amount, String description, Calendar calendar) {
				view.startSpinner();
				TransType type = TransTypeParser.parseTransType(transType);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String date = sdf.format(calendar.getTime());
				if (JsonParser.parseAddTransaction(network.addTransaction(
						model.getCurAccount().getId(), type.toString(), 
						TransTypeParser.parseSign(type), amount, description, date), 
						view, model)) {
					Collections.sort(model.getCurAccount().getTransactions());
					view.getManageTransactionListView().setAdapter(new ArrayAdapterTransactionItem(
							context, R.layout.fragment_manage_transaction_item, 
							model.getCurAccount().getTransactions()));
					
					Money balance = new Money(model.getCurAccount().getLocale(), 
							(int) Integer.valueOf(network.getBalance(model.getCurAccount().getId())));
					Log.d(TAG, "money: " + balance.toString());
					model.getCurAccount().setBalance(balance);
					view.getManageAccountListView().setAdapter(new ArrayAdapterAccountItem(
							context, R.layout.fragment_manage_account_item, 
							model.getCurUserAccounts()));
					// update balance in ManageTransaction view
					view.setManageTransactionBalance(balance.toString());
					// change view
					view.setView(TypeView.MANAGETRANSACTION);
					view.setTitle(model.getCurAccount().getName());
				} else {
					view.enablButtonListener();
				}
				view.stopSpinner();
			}
			@Override
			public void cancel() {
				view.setView(TypeView.MANAGETRANSACTION);
				view.setTitle(model.getCurAccount().getName());
			}
		});
		
		/* ManageTransaction button click listener */
		view.updateManageTransactionFragmentButtonListener(new ManageTransactionFragmentButtonListener() {
			@Override
			public void selectTransaction(int position) {
				Transaction transaction = model.getCurAccount().getTransactions().get(position);
				Log.d(TAG, "transaction.getDescription(): " + transaction.getDescription());
				model.setCurTransaction(transaction);
			}
		});
		
		/* EditTransaction button click listener */
		view.updateEditTransactionFragmentButtonListener(new EditTransactionFragmentButtonListener() {
			@Override
			public void submit(String transType, String amount, String description, Calendar calendar) {
				view.startSpinner();
				TransType type = TransTypeParser.parseTransType(transType);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String date = sdf.format(calendar.getTime());
				
				String prevType = TransTypeParser.parseSign(model.getCurTransaction().getType());
				int prevAmount = model.getCurTransaction().getAmount().getValue();
				if (prevType.equals("1")) {
					prevAmount *= -1;
				}
				String newType = TransTypeParser.parseSign(type);
				int newAmount = (int) Integer.valueOf(amount);
				if (newType.equals("1")) {
					newAmount *= -1;
				}
				
				String diff = String.valueOf(newAmount - prevAmount);
				
				if (JsonParser.parseEditTransaction(network.editTransaction(
						model.getCurTransaction().getId(), type.toString(), 
						model.getCurAccount().getId(), amount, diff, description, date), view, model)){
					// update transaction list
					Collections.sort(model.getCurAccount().getTransactions());
					view.getManageTransactionListView().setAdapter(new ArrayAdapterTransactionItem(
							context, R.layout.fragment_manage_transaction_item, 
							model.getCurAccount().getTransactions()));
					// update balance
					Money balance = new Money(model.getCurAccount().getLocale(), 
							(int) Integer.valueOf(network.getBalance(model.getCurAccount().getId())));
					Log.d(TAG, "money: " + balance.toString());
					model.getCurAccount().setBalance(balance);
					view.getManageAccountListView().setAdapter(new ArrayAdapterAccountItem(
							context, R.layout.fragment_manage_account_item, 
							model.getCurUserAccounts()));
					// update balance in ManageTransaction view
					view.setManageTransactionBalance(balance.toString());
					// change view
					view.setView(TypeView.MANAGETRANSACTION);
					view.setTitle(model.getCurAccount().getName());
				} else {
					view.enablButtonListener();
				}
				view.stopSpinner();
			}
			
			@Override
			public void cancel() {
				Log.d(TAG, "EditTransactionFragmentButtonListener - cancel()");
				view.setView(TypeView.MANAGETRANSACTION);
				view.setTitle(model.getCurAccount().getName());
			}

			@Override
			public void delete() {
				view.startSpinner();
				String prevType = TransTypeParser.parseSign(model.getCurTransaction().getType());
				int prevAmount = model.getCurTransaction().getAmount().getValue();
				if (prevType.equals("1")) {
					prevAmount *= -1;
				}
				String diff = String.valueOf(0 - prevAmount);
				try {
					int value = (int) Integer.valueOf(network.deleteTransaction(model.getCurTransaction().getId(), 
							model.getCurAccount().getId(), diff));
					if ( model.deleteTransaction(model.getCurAccount(), model.getCurTransaction()) ) {
						// update transaction list
						Collections.sort(model.getCurAccount().getTransactions());
						view.getManageTransactionListView().setAdapter(new ArrayAdapterTransactionItem(
								context, R.layout.fragment_manage_transaction_item, 
								model.getCurAccount().getTransactions()));
						// update balance
						Money balance = new Money(model.getCurAccount().getLocale(), value);
						Log.d(TAG, "money: " + balance.toString());
						model.getCurAccount().setBalance(balance);
						view.getManageAccountListView().setAdapter(new ArrayAdapterAccountItem(
								context, R.layout.fragment_manage_account_item, 
								model.getCurUserAccounts()));
						// update balance in ManageTransaction view
						view.setManageTransactionBalance(balance.toString());
						
						// reset model as null
						model.setCurTransaction(null);
						// change view
						view.setView(TypeView.MANAGETRANSACTION);
						view.setTitle(model.getCurAccount().getName());
						
					}
				} catch (NumberFormatException e) {
					view.enablButtonListener();
				}
				view.stopSpinner();
			}
		});
	}
	
	@Override
	public void run() {
		try {
			while (!isLoaded && (timeElapsed < SPLASH_TIME)) {
				Thread.sleep(100);
				if (!isLoaded) {
		            timeElapsed += 100;
		        }
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			isLoaded = true;
			view.setView(TypeView.LOGIN);
			view.setTitle("Log-In");
		}
	}
}
