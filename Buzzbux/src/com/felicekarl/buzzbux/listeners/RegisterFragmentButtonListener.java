package com.felicekarl.buzzbux.listeners;
/**
 * Button Listener Interface for RegisterFragment.
 * @author Karl
 *
 */
public interface RegisterFragmentButtonListener {
	/**
	 * Send registration info to presenter.
	 * @param username username (unique)
	 * @param password password
	 * @param firstname first name
	 * @param lastname last name
	 */
    void submitRegister(String username, String password, String firstname, String lastname);
    /**
     * User request to cancel registration.
     */
    void cancel();
}
