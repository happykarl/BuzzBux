package com.felicekarl.buzzbux.listeners;

/**
 * Button Listener Interface for AddAccountFragment.
 * @author Karl
 *
 */
public interface AddAccountFragmentButtonListener {
	/**
	 * Submit Account info to presenter.
	 * @param accountName account name (unique)
	 * @param accountDescription account description
	 * @param currency currency (ex> Locale.US)
	 */
    void submit(String accountName, String accountDescription, String currency);
    /**
     * Cancel adding account.
     */
    void cancel();
}
