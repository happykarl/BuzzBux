package com.felicekarl.buzzbux.listeners;

import java.util.Calendar;
/**
 * Button Listener Interface for EditTransactionFragment.
 * @author Karl
 *
 */
public interface EditTransactionFragmentButtonListener {
	/**
	 * Pass updated transaction info to presenter.
	 * @param transType transaction type
	 * @param amount amount of money related to this transaction
	 * @param description transaction description
	 * @param calendar date of transaction
	 */
    void submit(String transType, String amount, String description, Calendar calendar);
    /**
     * Cancel editing transaction.
     */
    void cancel();
    /**
     * Delete selected transaction.
     */
    void delete();
}
