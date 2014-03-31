package com.felicekarl.buzzbux.listeners;
/**
 * Button Listener Interface for FooterFragment.
 * @author Karl
 *
 */
public interface FooterFragmentButtonListener {
	/**
	 * Send message to presenter that user clicked addItem button.
	 */
    void addItem();
    /**
     * Send message to presetner that user clicked editItem button.
     */
    void editItem();
}
