package com.felicekarl.buzzbux.views.fragments;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import com.felicekarl.buzzbux.R;
import com.felicekarl.buzzbux.listeners.*;
import com.felicekarl.buzzbux.models.LocaleParser;
import com.felicekarl.buzzbux.models.Money;
import com.felicekarl.buzzbux.models.TransType;

import android.opengl.Visibility;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class AddTransactionFragment extends BaseFragment implements OnClickListener,
		UpdateAddTransactionFragmentButtonListener {
	@SuppressWarnings("unused")
	private static final String TAG = AddTransactionFragment.class.getSimpleName();
	
	private AddTransactionFragmentButtonListener mAddTransactionFragmentButtonListener;
	
	private EditText et_trans_amount;
	private EditText et_trans_description;
	private Spinner sp_trans_type;
	private Button bt_submit;
	private TextView tv_error;
	private TextView tv_currency;
	private TextView tv_month_back;
	private TextView tv_month_next;
	private DatePicker dp_date;
	private Calendar calendar;
	private Button bt_cancel;
	private View bgLayout1;
	private View bgLayout2;
	private View bgLayout3;
	private View bgLayout4;
	
	private Locale locale;
	private Money amount;
	
	public AddTransactionFragment() {
		super();
	}
	
	public static AddTransactionFragment create() {
		AddTransactionFragment fragment = new AddTransactionFragment();
		return fragment;
    }
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
    }
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = (ViewGroup) inflater.inflate(R.layout.fragment_add_transaction, container, false);
		
		et_trans_amount = (EditText) view.findViewById(R.id.et_trans_amount);
		et_trans_description = (EditText) view.findViewById(R.id.et_trans_description);
		sp_trans_type = (Spinner) view.findViewById(R.id.sp_trans_type);
		bt_submit = (Button) view.findViewById(R.id.bt_submit);
		bt_submit.setOnClickListener(this);
    	tv_error = (TextView) view.findViewById(R.id.tv_error);
    	tv_currency = (TextView) view.findViewById(R.id.tv_currency);
    	bt_cancel = (Button) view.findViewById(R.id.bt_cancel);
    	bt_cancel.setOnClickListener(this);
    	
    	dp_date = (DatePicker) view.findViewById(R.id.dp_date);
    	
    	calendar = Calendar.getInstance();
    	dp_date.init(calendar.get(Calendar.YEAR), 
    			calendar.get(Calendar.MONTH), 
    			calendar.get(Calendar.DAY_OF_MONTH), new OnDateChangedListener() {
					@Override
					public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
						calendar = Calendar.getInstance();
						calendar.set(year, monthOfYear, dayOfMonth);
		                //int dayOfMonth = cPickerDate.get(Calendar.DAY_OF_MONTH);
					}
    	});
    	
//    	long currentTimeUtc = System.currentTimeMillis();
//		long currentTimeLocal = currentTimeUtc - TimeZone.getDefault().getOffset(currentTimeUtc);
//		// today is Jan 14, 2013. if set daysAgo to a value larger than 36, there will be no issue.
//		int daysAgo = 36;
//		long minDateInMs = currentTimeLocal - 3600000L * 24 * daysAgo;
//		
//		DateUtils.formatDateRange(getActivity(), minDateInMs, currentTimeLocal, DateUtils.FORMAT_SHOW_DATE);
    	
    	// add bg listener
    	bgLayout1 = view.findViewById(R.id.bgLayout1);
    	bgLayout1.setOnClickListener(this);
    	bgLayout2 = view.findViewById(R.id.bgLayout2);
    	bgLayout2.setOnClickListener(this);
    	bgLayout3 = view.findViewById(R.id.bgLayout3);
    	bgLayout3.setOnClickListener(this);
    	bgLayout4 = view.findViewById(R.id.bgLayout4);
    	bgLayout4.setOnClickListener(this);
    	
		//view.setTranslationY(-height);
		
    	et_list.add(et_trans_amount);
    	et_list.add(et_trans_description);
    	view_list.add(bt_submit);
    	view_list.add(bt_cancel);
    	et_trans_amount.setFocusable(false);
    	et_trans_description.setFocusable(false);
    	
    	et_trans_amount.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(hasFocus){
					Log.d(TAG, "hasFocus");
					if (locale != null && amount != null) {
						Log.d(TAG, "String.valueOf(amount.getValue()): " + String.valueOf(amount.getValue()));
						et_trans_amount.setText(String.valueOf(amount.getValue()));
					}
				} else {
					Log.d(TAG, "!hasFocus");
					if (locale != null) {
						if ( !((EditText) v).getText().toString().equals("") ) {
							int value = 0;
							try {
								value = (int) Integer.valueOf(((EditText) v).getText().toString());
							} catch (NumberFormatException e) {
								setErrorMsg("Please input valid range of Integer value.");
								value = Integer.MAX_VALUE;
							} finally {
								amount = new Money(locale, value);
								((EditText) v).setText(amount.toString());
							}
							
						} else {
							amount = new Money(locale, 0);
							((EditText) v).setText(amount.toString());
							Log.d(TAG, "amount.toString(): " + amount.toString());
						}
						
					}
				}
			}
		});
    	
    	getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    	
    	slideUpFragment();
    	
		return view;
	}
	
	public void setErrorMsg(final String msg) {
		if (getActivity() != null && getView() != null) {
    		getActivity().runOnUiThread(new Runnable(){
    			@Override
    			public void run() {
    				tv_error.setText(msg);
    			}
        	});
    	}
	}
	
	public void setCurrency(final String currency) {
		locale = LocaleParser.parseLocale(currency);
		amount = new Money(locale, 0);
		if (getActivity() != null && getView() != null) {
    		getActivity().runOnUiThread(new Runnable(){
    			@Override
    			public void run() {
    				tv_currency.setText("Amount (Currency - " + currency + ")");
    			}
        	});
    	}
	}

	@Override
	public void onClick(View v) {
		if (iView != null)	iView.closeMenu();
		closeVirtualKeyboard();
		switch(v.getId()) {
		case R.id.bt_submit:
			setErrorMsg("");
			if ( (et_trans_amount.getText().toString().trim().length() == 0) 
		            || (et_trans_description.getText().toString().trim().length() == 0)) {
				setErrorMsg("Please fill all blanks.");
			}else if (mAddTransactionFragmentButtonListener != null) {
				disableButtonListener();
				mAddTransactionFragmentButtonListener.submit(
						sp_trans_type.getSelectedItem().toString(), 
						String.valueOf(amount.getValue()), 
						et_trans_description.getText().toString().trim(),
						calendar);
			}
			break;
		case R.id.bt_cancel:
			if (mAddTransactionFragmentButtonListener != null) {
				disableButtonListener();
				mAddTransactionFragmentButtonListener.cancel();
			}
			break;
		}
	}

	@Override
	public void resetFragment() {
		// initialize the error msg text view
		if (getActivity() != null && getView() != null) {
			getActivity().runOnUiThread(new Runnable(){
				@Override
				public void run() {
					et_trans_amount.setText("");
					et_trans_description.setText("");
					tv_error.setText("");
					tv_currency.setText("Amount");
					sp_trans_type.setSelection(0);
					
					calendar = Calendar.getInstance();
			    	dp_date.updateDate(calendar.get(Calendar.YEAR), 
			    			calendar.get(Calendar.MONTH), 
			    			calendar.get(Calendar.DAY_OF_MONTH));
				}
	    	});
		}
		
		//enable button listener
		enableButtonListener();
	}
	
	@Override
	public void enableButtonListener() {
		if (view_list != null) {
			for (View v : view_list) {
				v.setOnClickListener(this);
			}
		}
	}
	
	@Override
	public void disableButtonListener() {
		if (view_list != null) {
			for (View v : view_list) {
				v.setOnClickListener(null);
			}
		}
	}
	
	@Override
	public void updateAddTransactionFragmentButtonListener(
			AddTransactionFragmentButtonListener mAddTransactionFragmentButtonListener) {
		this.mAddTransactionFragmentButtonListener = mAddTransactionFragmentButtonListener;
	}
}
