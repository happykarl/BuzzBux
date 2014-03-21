package com.felicekarl.buzzbux.presenters;

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
				Log.d(TAG, "submitManageUser");
			}
			@Override
			public void submitManageAccount() {
				view.startSpinner();
				if (JsonParser.parseAccounts(network.getAccounts(
						model.getCurUser().getUsername()), view, model)) {
					view.getManageAccountListView().setAdapter(new ArrayAdapterAccountItem(
							context, R.layout.fragment_manage_account_item, 
							model.getCurUserAccounts()));
					view.setView(TypeView.MANAGEACCOUNT);
				} else {
					view.enablButtonListener();
				}
				view.stopSpinner();
			}
			@Override
			public void submitReportTransaction() {
				Log.d(TAG, "submitReportTransaction");
			}
			@Override
			public void submitSettings() {
				Log.d(TAG, "submitSettings");
			}
		});
		
		/* ManageAccountFragment button click listeners */
		view.updateManageAccountFragmentButtonListener(new ManageAccountFragmentButtonListener() {
			@Override
			public void selectAccount(int position) {
				view.startSpinner();
				Account account = model.getCurUser().getAccounts().get(position);
				model.setCurAccount(account);
				if (JsonParser.parseTransactions(network.getTransactions(account.getId()), view, model)) {
					view.getManageTransactionListView().setAdapter(new ArrayAdapterTransactionItem(
							context, R.layout.fragment_manage_transaction_item, 
							model.getCurAccount().getTransactions()));
					view.setView(TypeView.MANAGETRANSACTION);
					view.setTitle(model.getCurAccount().getName());
				} else {
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
