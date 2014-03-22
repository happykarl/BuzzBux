package com.felicekarl.buzzbux.listeners;

public interface AddAccountFragmentButtonListener {
	public void submit(String accountName, String accountDescription, String currency);
	public void cancel();
}
