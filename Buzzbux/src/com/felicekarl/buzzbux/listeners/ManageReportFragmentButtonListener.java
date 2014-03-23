package com.felicekarl.buzzbux.listeners;

import java.util.Calendar;
import java.util.List;

public interface ManageReportFragmentButtonListener {
	public void submit(List<Integer> selectedItemPosition, Calendar date_from,
			Calendar date_to);
}
