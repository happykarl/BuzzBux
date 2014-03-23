package com.felicekarl.buzzbux.views;

import android.widget.ListView;

import com.felicekarl.buzzbux.listeners.*;
import com.felicekarl.buzzbux.models.Transaction;

public interface IView  extends UpdateLogInFragmentButtonListener, 
		UpdateRegisterFragmentButtonListener, UpdateDashboardFragmentButtonListener,
		UpdateManageAccountFragmentButtonListener, UpdateFooterFragmentButtonListener,
		UpdateAddAccountFragmentButtonListener, UpdateAddTransactionFragmentButtonListener,
		UpdateManageTransactionFragmentButtonListener, UpdateEditTransactionFragmentButtonListener,
		UpdateManageReportFragmentButtonListener, UpdateShowReportFragmentButtonListener {
	public void setView(TypeView type);
	public TypeView getView();
	public void closeMenu();
	public void openMenu();
	public void setTitle(String str);
	public void startSpinner();
	public void stopSpinner();
	
	public void setLogInErrorMsg(String msg);
	public void setRegisterErrorMsg(String msg);
	public void setAddAccountErrorMsg(String msg);
	public void setAddTransactionErrorMsg(String msg);
	public void setAddTransactionCurrency(String currency);
	public void setManageTransactionBalance(String balance);
	public void setShowReportAmount(String amount);
	public void enablButtonListener();
	
	
	
	public ListView getManageAccountListView();
	public ListView getManageTransactionListView();
	public ListView getManageReportListView();
	public ListView getShowReportListView();
	public void fillEditTransactionForm(Transaction transaction);
	public void setEditTransactionCurrency(String currency);
	
}