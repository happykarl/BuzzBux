package com.felicekarl.buzzbux.views;

import android.widget.ListView;

import com.felicekarl.buzzbux.listeners.*;

public interface IView  extends UpdateLogInFragmentButtonListener, 
		UpdateRegisterFragmentButtonListener, UpdateDashboardFragmentButtonListener,
		UpdateManageAccountFragmentButtonListener {
	public void setView(TypeView type);
	public TypeView getView();
	public void closeMenu();
	public void setTitle(String str);
	public void startSpinner();
	public void stopSpinner();
	
	public void setLogInErrorMsg(String msg);
	public void setRegisterErrorMsg(String msg);
	public void enablButtonListener();
	
	public ListView getManageAccountListView();
	public ListView getManageTransactionListView();
	
}