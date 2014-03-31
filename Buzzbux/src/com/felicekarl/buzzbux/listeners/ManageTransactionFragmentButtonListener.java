package com.felicekarl.buzzbux.listeners;
/**
 * Button Listener Interface for ManageTransactionFragment.
 * @author Karl
 *
 */
public interface ManageTransactionFragmentButtonListener {
	/**
	 * send the position of transaction of currently selected account.
	 * @param position position of transaction of currently selected account
	 */
    void selectTransaction(int position);
    /**
     * Delete currently selected Account.
     */
    void delete();
}
