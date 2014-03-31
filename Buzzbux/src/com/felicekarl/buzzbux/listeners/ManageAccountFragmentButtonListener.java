package com.felicekarl.buzzbux.listeners;
/**
 * Button Listener Interface for ManageAccountFragment.
 * @author Karl
 *
 */
public interface ManageAccountFragmentButtonListener {
	/**
	 * Send the position of ListView of Accounts to presenter.
	 * @param position position of ListView of Accounts
	 */
    void selectAccount(int position);
}
