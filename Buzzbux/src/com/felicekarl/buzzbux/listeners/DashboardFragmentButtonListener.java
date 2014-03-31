package com.felicekarl.buzzbux.listeners;
/**
 * Button Listener Interface for DashboardFragmen.
 * @author Karl
 *
 */
public interface DashboardFragmentButtonListener {
	/**
	 * Send message to presenter that user request User Management Page.
	 */
    void submitManageUser();
    /**
     * Send message to presenter that user request Account Management Page.
     */
    void submitManageAccount();
    /**
     * Send message to presenter that user request Transaction Management Page.
     */
    void submitReportTransaction();
    /**
     * Send message to presenter that user request Setting Page.
     */
    void submitSettings();
}
