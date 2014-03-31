package com.felicekarl.buzzbux.presenters;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import com.felicekarl.buzzbux.R;
import com.felicekarl.buzzbux.listeners.AddAccountFragmentButtonListener;
import com.felicekarl.buzzbux.listeners.AddTransactionFragmentButtonListener;
import com.felicekarl.buzzbux.listeners.DashboardFragmentButtonListener;
import com.felicekarl.buzzbux.listeners.EditTransactionFragmentButtonListener;
import com.felicekarl.buzzbux.listeners.FooterFragmentButtonListener;
import com.felicekarl.buzzbux.listeners.LogInFragmentButtonListener;
import com.felicekarl.buzzbux.listeners.ManageAccountFragmentButtonListener;
import com.felicekarl.buzzbux.listeners.ManageReportFragmentButtonListener;
import com.felicekarl.buzzbux.listeners.ManageTransactionFragmentButtonListener;
import com.felicekarl.buzzbux.listeners.RegisterFragmentButtonListener;
import com.felicekarl.buzzbux.listeners.ShowReportFragmentButtonListener;
import com.felicekarl.buzzbux.models.Account;
import com.felicekarl.buzzbux.models.IModel;
import com.felicekarl.buzzbux.models.Money;
import com.felicekarl.buzzbux.models.TransType;
import com.felicekarl.buzzbux.models.Transaction;
import com.felicekarl.buzzbux.models.LocaleParser;
import com.felicekarl.buzzbux.models.TransTypeParser;
import com.felicekarl.buzzbux.models.User;
import com.felicekarl.buzzbux.presenters.adapters.ArrayAdapterTransactionItem;
import com.felicekarl.buzzbux.presenters.adapters.ArrayAdapterAccountGraphItem;
import com.felicekarl.buzzbux.presenters.adapters.ArrayAdapterAccountItem;
import com.felicekarl.buzzbux.views.IView;
import com.felicekarl.buzzbux.views.TypeView;
import com.felicekarl.buzzbux.views.fragments.ShowReportFragment.TypeReport;
import com.jjoe64.graphview.CustomLabelFormatter;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.LinearLayout;
/**
 * MainPresenter.
 * @author Karl
 *
 */
@SuppressLint("SimpleDateFormat") public class MainPresenter implements Runnable {
	/**
	 * String "Dashboard".
	 */
    private static final String DASHBOARD = "Dashboard";
    /**
	 * String "username".
	 */
    private static final String USERNAME = "username";
    /**
	 * String "Log-In".
	 */
    private static final String LOGIN = "Log-In";
    /**
	 * String "Manage Account".
	 */
    private static final String MANAGEACCOUNT = "Manage Account";
    /**
	 * String "All Transaction".
	 */
    private static final String ALLTRANSACTIONS = "All Transaction";
    /**
	 * String "1".
	 */
    private static final String ONE = "1";
    /**
	 * String "yyyy-MM-dd".
	 */
    private static final String DATEFORMAT = "yyyy-MM-dd";
    /**
	 * Splash Screen Time.
	 */
    private static final int SPLASH_TIME = 3500;
    /**
     * Timer for Splash Screen.
     */
    private int timeElapsed = 0;
    /**
     * Check isLogInScreen is loaded.
     */
    private boolean isLoaded = false;
    /**
     * Thread for timing Splash Screen.
     */
    private Thread thread;
	/**
	 * Android context.
	 */
    private Context context;
    /**
     * View of MVP Model.
     */
    private IView view;
    /**
     * Model of MVP Model.
     */
    private IModel model;
	/**
	 * SharedPreferences.
	 */
    private SharedPreferences preferences;
    /**
	 * SharedPreferences.Editor.
	 */
    private SharedPreferences.Editor editor;
    /**
     * Network for communicating with server.
     */
    private Network network;
	/**
	 * Intiate instance with following info.
	 * @param pContext Android Context
	 * @param pView View of MVP Model
	 * @param pModel Model of MVP Model
	 */
    public MainPresenter(Context pContext, IView pView, IModel pModel) {
        context = pContext;
        view = pView;
        model = pModel;
        view.setView(TypeView.SPLASH);
		
        network = new Network(context);
		
        initFooterFragmentButtonListener();
        initLogInFragmentButtonListener();
        initRegisterFragmentButtonListener();
        initDashboardFragmentButtonListener();
        initManageAccountFragmentButtonListener();
        initAddAccountFragmentButtonListener();
        initAddTransactionFragmentButtonListener();
        initManageTransactionFragmentButtonListener();
        initEditTransactionFragmentButtonListener();
        initManageReportFragmentButtonListener();
        initShowReportFragmentButtonListener();
		
        /* splash fragment timer */
        thread = new Thread(this);
        thread.start();
        
        /* shared preference */
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = preferences.edit();
    }
	/**
	 * initFooterFragmentButtonListener.
	 */
    private void initFooterFragmentButtonListener() {
        /* FooterFragment button click listeners */
        view.updateFooterFragmentButtonListener(new FooterFragmentButtonListener() {
    	    @Override
            public void addItem() {
                if (view.getCurTypeView().equals(TypeView.MANAGEACCOUNT)) {
                    view.setView(TypeView.ADDACCOUNT);
                    view.setTitle("Add Account");
                } else if (view.getCurTypeView().equals(TypeView.MANAGETRANSACTION)) {
                    view.setView(TypeView.ADDTRANSACTION);
                    view.setAddTransactionCurrency(model.getCurAccount().getLocale().toString());
                    view.setTitle("Add Transaction");
                }
            }

            @Override
            public void editItem() {
                if (view.getCurTypeView().equals(TypeView.MANAGEACCOUNT)) {
                    // TODO: Edit Account Mode
                } else if (view.getCurTypeView().equals(TypeView.MANAGETRANSACTION)) {
                    if (model.getCurTransaction() != null) {
                        view.setView(TypeView.EDITTRANSACTION);
                        view.setEditTransactionCurrency(model.getCurAccount().getLocale().toString());
                        view.fillEditTransactionForm(model.getCurTransaction());
                        view.setTitle("Edit Transaction");
                    }
                }
            }
        });
    }
    /**
	 * initLogInFragmentButtonListener.
	 */
    private void initLogInFragmentButtonListener() {
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
                    view.setTitle(DASHBOARD);
                    // save preference
                    editor.putString(USERNAME, username);
                    editor.commit();
                } else {
                    view.enablButtonListener();
                }
                view.stopSpinner();
            }
        });
    }
    /**
	 * initRegisterFragmentButtonListener.
	 */
    private void initRegisterFragmentButtonListener() {
        /* RegisterFragment button click listeners */
        view.updateRegisterFragmentButtonListener(new RegisterFragmentButtonListener() {
            @Override
            public void submitRegister(User pUser, String pPassword) {
            	view.startSpinner();
                if (JsonParser.parseRegister(network.submitRegister(pUser, pPassword), view, model)) {
                    view.setView(TypeView.DASHBOARD);
                    view.setTitle(DASHBOARD);
                    // save preference
                    editor.putString(USERNAME, pUser.getUsername());
                    editor.commit();
                } else {
                    view.enablButtonListener();
                }
                view.stopSpinner();
            }
            @Override
            public void cancel() {
                view.setView(TypeView.LOGIN);
                view.setTitle(LOGIN);
            }
			
        });
    }
    /**
	 * initDashboardFragmentButtonListener.
	 */
    private void initDashboardFragmentButtonListener() {
        /* Dashboard button click listeners */
        view.updateDashboardFragmentButtonListener(new DashboardFragmentButtonListener() {
            @Override
            public void submitManageUser() {
                view.startSpinner();
                view.enablButtonListener();
                view.stopSpinner();
            }
            @SuppressWarnings("unchecked")
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
                            Collections.sort(a.getList());
                        }
                    }
                    view.getManageAccountListView().setAdapter(new ArrayAdapterAccountGraphItem(
							context, R.layout.fragment_manage_account_item, 
							model.getCurUserAccounts()));
                    view.setView(TypeView.MANAGEACCOUNT);
                    view.setTitle(MANAGEACCOUNT);
                } else {
                    view.enablButtonListener();
                }
                view.stopSpinner();
            }
            @SuppressWarnings("unchecked")
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
    }
    /**
	 * initManageAccountFragmentButtonListener.
	 */
    private void initManageAccountFragmentButtonListener() {
		/* ManageAccountFragment button click listeners */
        view.updateManageAccountFragmentButtonListener(new ManageAccountFragmentButtonListener() {
            @SuppressWarnings("unchecked")
            @Override
            public void selectAccount(int position) {
                view.startSpinner();
				// set cur account as selected one
                Account account = model.getCurUser().getList().get(position);
                model.setCurAccount(account);
				// reset cur transaction as null
                model.setCurTransaction(null);
                if (JsonParser.parseTransactions(network.getTransactions(account.getId()), view, model)) {
					// update list
                    Collections.sort(model.getCurAccount().getList());
                    view.getManageTransactionListView().setAdapter(new ArrayAdapterTransactionItem(
							context, R.layout.fragment_manage_transaction_item, 
							model.getCurAccount().getList()));
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
    }
    /**
	 * initAddAccountFragmentButtonListener.
	 */
    private void initAddAccountFragmentButtonListener() {
        /* AddAccountFragment button click listeners */
        view.updateAddAccountFragmentButtonListener(new AddAccountFragmentButtonListener() {
            @SuppressWarnings("unchecked")
            @Override
            public void submit(String accountName, String accountDescription, String currency) {
                view.startSpinner();
                Account account = new Account(null, accountName, accountDescription, LocaleParser.parseLocale(currency), 0);
                if (JsonParser.parseAddAccount(network.addAccount(model.getCurUser().getUsername(), account), view, model)) {
					// update list
                    Collections.sort(model.getCurUserAccounts());
                    view.getManageAccountListView().setAdapter(new ArrayAdapterAccountGraphItem(
							context, R.layout.fragment_manage_account_item, 
							model.getCurUserAccounts()));
                    view.setView(TypeView.MANAGEACCOUNT);
                    view.setTitle(MANAGEACCOUNT);
                } else {
                    view.enablButtonListener();
                }
                view.stopSpinner();
            }
            @Override
            public void cancel() {
                view.setView(TypeView.MANAGEACCOUNT);
                view.setTitle(MANAGEACCOUNT);
            }
        });
    }
    /**
	 * initAddTransactionFragmentButtonListener.
	 */
    private void initAddTransactionFragmentButtonListener() {
		/* AddTransactionFragment button click listeners */
        view.updateAddTransactionFragmentButtonListener(new AddTransactionFragmentButtonListener() {
            @SuppressWarnings("unchecked")
            @Override
            public void submit(String transType, String amount, String description, Calendar calendar) {
                view.startSpinner();
                TransType type = TransTypeParser.parseTransType(transType);
                Money money = new Money(model.getCurAccount().getLocale(), (int) Integer.valueOf(amount));
                Transaction transaction = new Transaction(null, type, description, money, calendar.getTime());
                if (JsonParser.parseAddTransaction(network.addTransaction(
						model.getCurAccount().getId(), TransTypeParser.parseSign(type), transaction), view, model)) {
                    Collections.sort(model.getCurAccount().getList());
                    view.getManageTransactionListView().setAdapter(new ArrayAdapterTransactionItem(
							context, R.layout.fragment_manage_transaction_item, 
							model.getCurAccount().getList()));
					
                    Money balance = new Money(model.getCurAccount().getLocale(), 
							(int) Integer.valueOf(network.getBalance(model.getCurAccount().getId())));
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
    }
    /**
	 * initManageTransactionFragmentButtonListener.
	 */
    private void initManageTransactionFragmentButtonListener() {
		/* ManageTransaction button click listener */
        view.updateManageTransactionFragmentButtonListener(new ManageTransactionFragmentButtonListener() {
            @Override
            public void selectTransaction(int position) {
                Transaction transaction = model.getCurAccount().getList().get(position);
                model.setCurTransaction(transaction);
            }
            @SuppressWarnings("unchecked")
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
                    view.setTitle(MANAGEACCOUNT);
                } else {
                    view.enablButtonListener();
                }
                view.stopSpinner();
            }
        });
    }
    /**
	 * initEditTransactionFragmentButtonListener.
	 */
    private void initEditTransactionFragmentButtonListener() {
		/* EditTransaction button click listener */
        view.updateEditTransactionFragmentButtonListener(new EditTransactionFragmentButtonListener() {
            @SuppressWarnings("unchecked")
            @Override
            public void submit(String transType, String amount, String description, Calendar calendar) {
                view.startSpinner();
                TransType type = TransTypeParser.parseTransType(transType);
                SimpleDateFormat sdf = new SimpleDateFormat(DATEFORMAT);
                String date = sdf.format(calendar.getTime());

                String prevType = TransTypeParser.parseSign(model.getCurTransaction().getType());
                int prevAmount = model.getCurTransaction().getAmount().getValue();
                if (prevType.equals(ONE)) {
                    prevAmount *= -1;
                }
                String newType = TransTypeParser.parseSign(type);
                int newAmount = (int) Integer.valueOf(amount);
                if (newType.equals(ONE)) {
                    newAmount *= -1;
                }
				
                String diff = String.valueOf(newAmount - prevAmount);
                Money money = new Money(model.getCurAccount().getLocale(), (int) Integer.valueOf(amount));
            	Transaction transaction = new Transaction(model.getCurTransaction().getIndex(), type, description, money, calendar.getTime());
                if (JsonParser.parseEditTransaction(network.editTransaction(model.getCurAccount().getId(), diff, transaction), view, model)) {
					// update transaction list
                    Collections.sort(model.getCurAccount().getList());
                    view.getManageTransactionListView().setAdapter(new ArrayAdapterTransactionItem(
							context, R.layout.fragment_manage_transaction_item, 
							model.getCurAccount().getList()));
					// update balance
                    Money balance = new Money(model.getCurAccount().getLocale(), 
							(int) Integer.valueOf(network.getBalance(model.getCurAccount().getId())));
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
            @SuppressWarnings("unchecked")
            @Override
            public void delete() {
                view.startSpinner();
                String prevType = TransTypeParser.parseSign(model.getCurTransaction().getType());
                int prevAmount = model.getCurTransaction().getAmount().getValue();
                if (prevType.equals(ONE)) {
                    prevAmount *= -1;
                }
                String diff = String.valueOf(0 - prevAmount);
                try {
                    int value = (int) Integer.valueOf(network.deleteTransaction(model.getCurTransaction().getIndex(), 
							model.getCurAccount().getId(), diff));
                    if ( model.deleteTransaction(model.getCurAccount(), model.getCurTransaction()) ) {
						// update transaction list
                        Collections.sort(model.getCurAccount().getList());
                        view.getManageTransactionListView().setAdapter(new ArrayAdapterTransactionItem(
								context, R.layout.fragment_manage_transaction_item, 
								model.getCurAccount().getList()));
						// update balance
                        Money balance = new Money(model.getCurAccount().getLocale(), value);
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
    }
    /**
	 * initManageReportFragmentButtonListener.
	 */
    private void initManageReportFragmentButtonListener() {
		/* add ManageReportFragment button listener */
        view.updateManageReportFragmentButtonListener(new ManageReportFragmentButtonListener() {
            @SuppressWarnings("unchecked")
            @Override
            public void submit(List<Integer> selectedItemPosition, Calendar dateFrom, Calendar dateTo) {
                view.startSpinner();
                List<Integer> idList = new ArrayList<Integer>(); 
                for (Integer i : selectedItemPosition) {
                    idList.add(Integer.valueOf(model.getCurUser().getList().get(i).getId()));
                }
                SimpleDateFormat sdf = new SimpleDateFormat(DATEFORMAT);
                if (JsonParser.parseReport(network.getTransactions(idList, sdf.format(dateFrom.getTime()), sdf.format(dateTo.getTime())), view, model)) {
                    // update transaction list
                    Collections.sort(model.getReportTransactions());
                    view.getShowReportListView().setAdapter(new ArrayAdapterTransactionItem(
							context, R.layout.fragment_manage_transaction_item, 
							model.getReportTransactions()));
					// calculate amount
                    int total = 0;
                    for (Transaction t : model.getReportTransactions()) {
                        total += t.getSignedValue();
                    }
					// TODO: Hard coded locale
                    Money amount = new Money(Locale.US, total);
                    view.setShowReportAmount(amount.toString());
					
					// update Graph
                    LinearLayout graphFrame = (LinearLayout) view.getShowReportGraphView();
                    graphFrame.removeAllViews();
                    if (model.getReportTransactions().size() > 0) {
                        GraphDataParser gParser = new GraphDataParser();
                        GraphViewSeries graphData = gParser.parseTransactionData(model.getReportTransactions());
                        LineGraphView graphView = new LineGraphView(context, ALLTRANSACTIONS);
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
                                    SimpleDateFormat sdf = new SimpleDateFormat(DATEFORMAT);
                                    String date = sdf.format(calendar.getTime());
                                    return date;
                                }
                                return null;
                            }
                        });
                    }
					// change view
                    view.setView(TypeView.SHOWREPORT);
                    view.setTitle("Report");
                } else {
                    view.enablButtonListener();
                }
                view.stopSpinner();
            }
        });
    }
    /**
   	 * initShowReportFragmentButtonListener.
   	 */
    private void initShowReportFragmentButtonListener() {
		/* add ShowReportFragment button click listener */
        view.updateShowReportFragmentButtonListener(new ShowReportFragmentButtonListener() {
            @Override
            public void setReportType(TypeReport reportType) {
                if (model.getReportTransactions() != null) {
                    if (reportType.equals(TypeReport.ALL)) {
                        view.getShowReportListView().setAdapter(new ArrayAdapterTransactionItem(
								context, R.layout.fragment_manage_transaction_item, 
								model.getReportTransactions()));
						// calculate amount
                        int total = 0;
                        for (Transaction t : model.getReportTransactions()) {
                            total += t.getSignedValue();
                        }
						// TODO: Hard coded locale
                        Money amount = new Money(Locale.US, total);
                        view.setShowReportAmount(amount.toString());
						
						// update Graph
                        LinearLayout graphFrame = (LinearLayout) view.getShowReportGraphView();
                        graphFrame.removeAllViews();
                        if (model.getReportTransactions().size() > 0) {
                            GraphDataParser gParser = new GraphDataParser();
                            GraphViewSeries graphData = gParser.parseTransactionData(model.getReportTransactions());
							
                            LineGraphView graphView = new LineGraphView(context, ALLTRANSACTIONS);
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
                                        SimpleDateFormat sdf = new SimpleDateFormat(DATEFORMAT);
                                        String date = sdf.format(calendar.getTime());
                                        return date;
                                    }
                                    return null;
                                }
                            });
                        }
                    } else if (reportType.equals(TypeReport.INCOME)) {
                        List<Transaction> list = new ArrayList<Transaction>();
                        for (Transaction t : model.getReportTransactions()) {
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
                                        SimpleDateFormat sdf = new SimpleDateFormat(DATEFORMAT);
                                        String date = sdf.format(calendar.getTime());
                                        return date;
                                    }
                                    return null;
                                }
                            });
                        }
                    } else if (reportType.equals(TypeReport.EXPENSE)) {
                        List<Transaction> list = new ArrayList<Transaction>();
                        for (Transaction t : model.getReportTransactions()) {
                            TransType type = t.getType();
                            if ( type.equals(TransType.WITHDRAWAL) || type.equals(TransType.DEBIT) || type.equals(TransType.CREDIT) || type.equals(TransType.VOID) ) {
                                list.add(t);
                            }
                        }
                        view.getShowReportListView().setAdapter(new ArrayAdapterTransactionItem(context, R.layout.fragment_manage_transaction_item, list));
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
                                        SimpleDateFormat sdf = new SimpleDateFormat(DATEFORMAT);
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
            view.setTitle(LOGIN);
        }
    }
}
