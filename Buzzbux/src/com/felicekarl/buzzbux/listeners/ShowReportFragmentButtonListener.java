package com.felicekarl.buzzbux.listeners;

import com.felicekarl.buzzbux.views.fragments.ShowReportFragment.TypeReport;
/**
 * Button Listener Interface for ShowReportFragment.
 * @author Karl
 *
 */
public interface ShowReportFragmentButtonListener {
	/**
	 * Send the report type to presenter.
	 * @param reportType type of Report. (ALL, INCOME, EXPENSE)
	 */
    void setReportType(TypeReport reportType);
}
