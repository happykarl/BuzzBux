package com.felicekarl.buzzbux.listeners;
/**
 * Button Listener Interface for LogInFragment.
 * @author Karl
 *
 */
public interface LogInFragmentButtonListener {
	/**
	 * Send username and password for logging-in to presenter.
	 * @param username username
	 * @param password password
	 */
    void submitLogIn(String username, String password);
    /**
     * Send message to presenter that user wants the registration page.
     */
    void submitRegister();
}
