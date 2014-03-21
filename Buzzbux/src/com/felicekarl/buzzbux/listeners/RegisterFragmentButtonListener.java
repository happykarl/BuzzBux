package com.felicekarl.buzzbux.listeners;

public interface RegisterFragmentButtonListener {
	public void submitRegister(String username, String password, String firstname, String lastname);
	public void cancel();
}
