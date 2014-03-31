package com.felicekarl.buzzbux.listeners;

import com.felicekarl.buzzbux.models.User;

/**
 * Button Listener Interface for RegisterFragment.
 * @author Karl
 *
 */
public interface RegisterFragmentButtonListener {
	/**
	 * Send registration info to presenter.
	 * @param pUser user
	 * @param pPassword password
	 */
    void submitRegister(User pUser, String pPassword);
    /**
     * User request to cancel registration.
     */
    void cancel();
}
