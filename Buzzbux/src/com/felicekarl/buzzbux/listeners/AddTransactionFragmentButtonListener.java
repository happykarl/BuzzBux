package com.felicekarl.buzzbux.listeners;

import java.util.Calendar;

public interface AddTransactionFragmentButtonListener {
	public void submit(String transType, String amount, String description, Calendar calendar);
	public void cancel();
}
