package com.felicekarl.buzzbux.listeners;

import java.util.Calendar;
import java.util.List;
/**
 * Button Listener Interface for ManageReportFragment.
 * @author Karl
 *
 */
public interface ManageReportFragmentButtonListener {
	/**
	 * Send list of positions of Accounts and Range of Date to search.
	 * @param selectedItemPosition list of selected Accounts.
	 * @param dateFrom starting date
	 * @param dateTo end date
	 */
    void submit(List<Integer> selectedItemPosition, Calendar dateFrom, Calendar dateTo);
}
