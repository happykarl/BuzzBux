package com.felicekarl.buzzbux.views;

import android.view.View;
import android.widget.ListView;

import com.felicekarl.buzzbux.listeners.UpdateAddAccountFragmentButtonListener;
import com.felicekarl.buzzbux.listeners.UpdateAddTransactionFragmentButtonListener;
import com.felicekarl.buzzbux.listeners.UpdateDashboardFragmentButtonListener;
import com.felicekarl.buzzbux.listeners.UpdateEditTransactionFragmentButtonListener;
import com.felicekarl.buzzbux.listeners.UpdateFooterFragmentButtonListener;
import com.felicekarl.buzzbux.listeners.UpdateLogInFragmentButtonListener;
import com.felicekarl.buzzbux.listeners.UpdateManageAccountFragmentButtonListener;
import com.felicekarl.buzzbux.listeners.UpdateManageReportFragmentButtonListener;
import com.felicekarl.buzzbux.listeners.UpdateManageTransactionFragmentButtonListener;
import com.felicekarl.buzzbux.listeners.UpdateRegisterFragmentButtonListener;
import com.felicekarl.buzzbux.listeners.UpdateShowReportFragmentButtonListener;
import com.felicekarl.buzzbux.models.Transaction;
/**
 * Interface for MainView.
 * @author Karl
 *
 */
public interface IView  extends UpdateLogInFragmentButtonListener, 
		UpdateRegisterFragmentButtonListener, UpdateDashboardFragmentButtonListener,
		UpdateManageAccountFragmentButtonListener, UpdateFooterFragmentButtonListener,
		UpdateAddAccountFragmentButtonListener, UpdateAddTransactionFragmentButtonListener,
		UpdateManageTransactionFragmentButtonListener, UpdateEditTransactionFragmentButtonListener,
		UpdateManageReportFragmentButtonListener, UpdateShowReportFragmentButtonListener {
	/**
	 * Set CurrentView.
	 * @param type TypeView
	 */
    void setView(TypeView type);
    /**
     * Get CurrentView.
     * @return CurrentView
     */
    TypeView getCurTypeView();
    /**
     * Close Menu.
     */
    void closeMenu();
    /**
     * Open Menu.
     */
    void openMenu();
    /**
     * Set Title TextBox.
     * @param str title
     */
    void setTitle(String str);
    /**
     * Start Animating Loader.
     */
    void startSpinner();
    /**
     * Stop Animating Loader.
     */
    void stopSpinner();
	/**
	 * Set Error Message on LogInScrren.
	 * @param msg error message
	 */
    void setLogInErrorMsg(String msg);
    /**
	 * Set Error Message on RegisterScreen.
	 * @param msg error message
	 */
    void setRegisterErrorMsg(String msg);
    /**
	 * Set Error Message on AddAccountScreen.
	 * @param msg error message
	 */
    void setAddAccountErrorMsg(String msg);
    /**
	 * Set Error Message on AddTransactionScreen.
	 * @param msg error message
	 */
    void setAddTransactionErrorMsg(String msg);
    /**
     * Set Current of Transaction.
     * @param currency currency
     */
    void setAddTransactionCurrency(String currency);
    /**
     * Set Balance TextBox as balance String.
     * @param balance balance of Account
     */
    void setManageTransactionBalance(String balance);
    /**
     * Set Total Amount of Money on ReportScreen.
     * @param amount Total Amount of Money
     */
    void setShowReportAmount(String amount);
    /**
     * Enable Button when action is finished.
     */
    void enablButtonListener();
    /**
     * Get ListView of Account.
     * @return ListView of Account
     */
    ListView getManageAccountListView();
    /**
     * Get ListView of Transaction.
     * @return ListView of Transaction
     */
    ListView getManageTransactionListView();
    /**
     * Get ListView of Transaction.
     * @return ListView of Transaction
     */
    ListView getManageReportListView();
    /**
     * Get ListView of Transaction.
     * @return ListView of Transaction
     */
    ListView getShowReportListView();
    /**
     * Get GraphView of Transaction.
     * @return GraphView of Transaction
     */
    View getShowReportGraphView();
	/**
	 * fill the information of edit mode of transaction.
	 * @param transaction Transaction date to be filled in
	 */
    void fillEditTransactionForm(Transaction transaction);
    /**
     * Set currency of edit mode of transaction.
     * @param currency currency of account
     */
    void setEditTransactionCurrency(String currency);
	
}