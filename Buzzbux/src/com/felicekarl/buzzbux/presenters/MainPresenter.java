package com.felicekarl.buzzbux.presenters;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import com.felicekarl.buzzbux.R;
import com.felicekarl.buzzbux.listeners.*;
import com.felicekarl.buzzbux.models.*;
import com.felicekarl.buzzbux.presenters.adapters.*;
import com.felicekarl.buzzbux.views.*;
import com.felicekarl.buzzbux.views.fragments.ShowReportFragment.TypeReport;
import com.jjoe64.graphview.CustomLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphViewDataInterface;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.widget.LinearLayout;

public class MainPresenter implements Runnable {
	private static final String TAG = MainPresenter.class.getSimpleName();
	private static final int SPLASH_TIME = 3500;
	private int timeElapsed = 0;
	private boolean isLoaded = false;
	private Thread thread;
	
	private Context context;
	private IView view;
	private IModel model;
	
	private SharedPreferences preferences;
	private SharedPreferences.Editor editor;
	
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
        
        /* shared preference */
		preferences = PreferenceManager.getDefaultSharedPreferences(context);
		editor = preferences.edit();
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
					// save preference
					editor.putString("username",username);
					editor.commit();
				} else {
					Log.d(TAG, "fail to log-In");
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
					// save preference
					editor.putString("username",username);
					editor.commit();
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
					// import transactions for each account
					for (Account a : model.getCurUserAccounts()) {
						if (JsonParser.parseTransactions(a, network.getTransactions(a.getId()), view, model)) {
							// update list
							Collections.sort(a.getTransactions());
						}
					}
					
					
					view.getManageAccountListView().setAdapter(new ArrayAdapterAccountGraphItem(
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
				if (JsonParser.parseAccounts(network.getAccounts(
						model.getCurUser().getUsername()), view, model)) {
					// update list
					Collections.sort(model.getCurUserAccounts());
					view.getManageReportListView().setAdapter(new ArrayAdapterAccountItem(
							context, R.layout.fragment_manage_report_item, 
							model.getCurUserAccounts()));
					view.setView(TypeView.MANAGEREPORT);
					view.setTitle("Manage Report");
				} else {
					view.enablButtonListener();
				}
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
					view.getManageAccountListView().setAdapter(new ArrayAdapterAccountGraphItem(
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
					view.getManageAccountListView().setAdapter(new ArrayAdapterAccountGraphItem(
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

			@Override
			public void delete() {
				view.startSpinner();
				String result = network.deleteAccount(model.getCurAccount().getId());
				if (result.toLowerCase(Locale.US).equals("succeed")) {
					model.deleteAccount(model.getCurUser(), model.getCurAccount());
					model.setCurAccount(null);
					// update account list
					Collections.sort(model.getCurUserAccounts());
					view.getManageAccountListView().setAdapter(new ArrayAdapterAccountGraphItem(
							context, R.layout.fragment_manage_account_item, 
							model.getCurUserAccounts()));
					view.setView(TypeView.MANAGEACCOUNT);
					view.setTitle("Manage Account");
				} else {
					view.enablButtonListener();
				}
				view.stopSpinner();
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
					view.getManageAccountListView().setAdapter(new ArrayAdapterAccountGraphItem(
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
						view.getManageAccountListView().setAdapter(new ArrayAdapterAccountGraphItem(
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
		
		/* add ManageReportFragment button listener */
		view.updateManageReportFragmentButtonListener(new ManageReportFragmentButtonListener() {
			@Override
			public void submit(List<Integer> selectedItemPosition, Calendar date_from, Calendar date_to) {
				view.startSpinner();
				List<Integer> idList = new ArrayList<Integer>(); 
				for (Integer i : selectedItemPosition) {
					idList.add(Integer.valueOf(model.getCurUser().getAccounts().get(i).getId()));
				}
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				if (JsonParser.parseReport(network.getTransactions(idList, sdf.format(date_from.getTime()),
						sdf.format(date_to.getTime())), view, model)) {
					// update transaction list
					Collections.sort(model.getCurReportTransactions());
					view.getShowReportListView().setAdapter(new ArrayAdapterTransactionItem(
							context, R.layout.fragment_manage_transaction_item, 
							model.getCurReportTransactions()));
					// calculate amount
					int total = 0;
					for (Transaction t : model.getCurReportTransactions()) {
						total += t.getSignedValue();
					}
					// TODO: Hard coded locale
					Money amount = new Money(Locale.US, total);
					view.setShowReportAmount(amount.toString());
					
					// update Graph
					LinearLayout graphFrame = (LinearLayout) view.getShowReportGraphView();
					graphFrame.removeAllViews();
					if (model.getCurReportTransactions().size() > 0) {
						GraphDataParser gParser = new GraphDataParser();
						GraphViewSeries graphData = gParser.parseTransactionData(model.getCurReportTransactions());
						LineGraphView graphView = new LineGraphView(context, "All Transaction");
						graphView.getGraphViewStyle().setTextSize(10);
						graphView.getGraphViewStyle().setNumHorizontalLabels(5);
						graphView.setDrawDataPoints(true);
						graphView.setDataPointsRadius(5f);
						graphView.addSeries(graphData);
						graphFrame.addView(graphView);
						
						graphView.setCustomLabelFormatter(new CustomLabelFormatter() {
							@Override
							public String formatLabel(double value, boolean isValueX) {
								if (isValueX) {
									Calendar calendar = Calendar.getInstance();
									calendar.setTimeInMillis((long) value);
									SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
									String date = sdf.format(calendar.getTime());
									return date;
								}
								return null;
							}
						});
					}
					
					// change view
					Log.d(TAG, "JsonParser.parseReport - succeed");
					view.setView(TypeView.SHOWREPORT);
					view.setTitle("Report");
				} else {
					Log.d(TAG, "JsonParser.parseReport - failed");
					view.enablButtonListener();
				}
				view.stopSpinner();
			}
		});
		
		/* add ShowReportFragment button click listener */
		view.updateShowReportFragmentButtonListener(new ShowReportFragmentButtonListener() {
			@Override
			public void setReportType(TypeReport reportType) {
				if (model.getCurReportTransactions() != null) {
					if (reportType.equals(TypeReport.ALL)) {
						Log.d(TAG, "reportType.equals(TypeReport.ALL)");
						view.getShowReportListView().setAdapter(new ArrayAdapterTransactionItem(
								context, R.layout.fragment_manage_transaction_item, 
								model.getCurReportTransactions()));
						// calculate amount
						int total = 0;
						for (Transaction t : model.getCurReportTransactions()) {
							total += t.getSignedValue();
						}
						// TODO: Hard coded locale
						Money amount = new Money(Locale.US, total);
						view.setShowReportAmount(amount.toString());
						
						// update Graph
						LinearLayout graphFrame = (LinearLayout) view.getShowReportGraphView();
						graphFrame.removeAllViews();
						if (model.getCurReportTransactions().size() > 0) {
							GraphDataParser gParser = new GraphDataParser();
							GraphViewSeries graphData = gParser.parseTransactionData(model.getCurReportTransactions());
							
							LineGraphView graphView = new LineGraphView(context, "All Transaction");
							graphView.getGraphViewStyle().setTextSize(10);
							graphView.getGraphViewStyle().setNumHorizontalLabels(5);
							graphView.setDrawDataPoints(true);
							graphView.setDataPointsRadius(5f);
							graphView.addSeries(graphData);
							graphFrame.addView(graphView);
							
							graphView.setCustomLabelFormatter(new CustomLabelFormatter() {
								@Override
								public String formatLabel(double value, boolean isValueX) {
									if (isValueX) {
										Calendar calendar = Calendar.getInstance();
										calendar.setTimeInMillis((long) value);
										SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
										String date = sdf.format(calendar.getTime());
										return date;
									}
									return null;
								}
							});
						}
					} else if (reportType.equals(TypeReport.INCOME)) {
						Log.d(TAG, "reportType.equals(TypeReport.INCOME)");
						List<Transaction> list = new ArrayList<Transaction>();
						for (Transaction t : model.getCurReportTransactions()) {
							TransType type = t.getType();
							if ( type.equals(TransType.DEPOSIT) || type.equals(TransType.REFUND) ) {
								list.add(t);
							}
						}
						view.getShowReportListView().setAdapter(new ArrayAdapterTransactionItem(
								context, R.layout.fragment_manage_transaction_item, list));
						// calculate amount
						int total = 0;
						for (Transaction t : list) {
							total += t.getSignedValue();
						}
						// TODO: Hard coded locale
						Money amount = new Money(Locale.US, total);
						view.setShowReportAmount(amount.toString());
						
						// update Graph
						LinearLayout graphFrame = (LinearLayout) view.getShowReportGraphView();
						graphFrame.removeAllViews();
						if (list.size() > 0) {
							GraphDataParser gParser = new GraphDataParser();
							GraphViewSeries graphData = gParser.parseTransactionData(list);
							LineGraphView graphView = new LineGraphView(context, "Income Transaction");
							graphView.getGraphViewStyle().setTextSize(10);
							graphView.getGraphViewStyle().setNumHorizontalLabels(5);
							graphView.setDrawDataPoints(true);
							graphView.setDataPointsRadius(5f);
							graphView.addSeries(graphData);
							graphFrame.addView(graphView);
							
							graphView.setCustomLabelFormatter(new CustomLabelFormatter() {
								@Override
								public String formatLabel(double value, boolean isValueX) {
									if (isValueX) {
										Calendar calendar = Calendar.getInstance();
										calendar.setTimeInMillis((long) value);
										SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
										String date = sdf.format(calendar.getTime());
										return date;
									}
									return null;
								}
							});
						}
					} else if (reportType.equals(TypeReport.EXPENSE)) {
						Log.d(TAG, "reportType.equals(TypeReport.EXPENSE)");
						List<Transaction> list = new ArrayList<Transaction>();
						for (Transaction t : model.getCurReportTransactions()) {
							TransType type = t.getType();
							if ( type.equals(TransType.WITHDRAWAL) || type.equals(TransType.DEBIT) ||
									type.equals(TransType.CREDIT) || type.equals(TransType.VOID) ) {
								list.add(t);
							}
						}
						view.getShowReportListView().setAdapter(new ArrayAdapterTransactionItem(
								context, R.layout.fragment_manage_transaction_item, list));
						// calculate amount
						int total = 0;
						for (Transaction t : list) {
							total += t.getSignedValue();
						}
						// TODO: Hard coded locale
						Money amount = new Money(Locale.US, total);
						view.setShowReportAmount(amount.toString());
						
						// update Graph
						LinearLayout graphFrame = (LinearLayout) view.getShowReportGraphView();
						graphFrame.removeAllViews();
						if (list.size() > 0) {
							GraphDataParser gParser = new GraphDataParser();
							GraphViewSeries graphData = gParser.parseTransactionData(list);
							
							LineGraphView graphView = new LineGraphView(context, "Outcome Transaction");
							graphView.getGraphViewStyle().setTextSize(10);
							graphView.getGraphViewStyle().setNumHorizontalLabels(5);
							graphView.setDrawDataPoints(true);
							graphView.setDataPointsRadius(5f);
							graphView.addSeries(graphData);
							graphFrame.addView(graphView);
							
							graphView.setCustomLabelFormatter(new CustomLabelFormatter() {
								@Override
								public String formatLabel(double value, boolean isValueX) {
									if (isValueX) {
										Calendar calendar = Calendar.getInstance();
										calendar.setTimeInMillis((long) value);
										SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
										String date = sdf.format(calendar.getTime());
										return date;
									}
									return null;
								}
							});
						}
					}
				}
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
