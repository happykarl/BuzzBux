package com.felicekarl.buzzbux.views.fragments;

import com.felicekarl.buzzbux.R;
import com.felicekarl.buzzbux.listeners.*;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class AddAccountFragment extends BaseFragment implements OnClickListener, UpdateAddAccountFragmentButtonListener {
	@SuppressWarnings("unused")
	private static final String TAG = AddAccountFragment.class.getSimpleName();
	
	private AddAccountFragmentButtonListener mAddAccountFragmentButtonListener;
	
	private EditText et_account_name;
	private EditText et_account_description;
	private Spinner sp_locale;
	private Button bt_submit;
	private TextView tv_error;
	private Button bt_cancel;
	private View bgLayout1;
	private View bgLayout2;
	private View bgLayout3;
	private View bgLayout4;
	
	public AddAccountFragment() {
		super();
	}
	
	public static AddAccountFragment create() {
		AddAccountFragment fragment = new AddAccountFragment();
		return fragment;
    }
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
    }
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = (ViewGroup) inflater.inflate(R.layout.fragment_add_account, container, false);
		
		et_account_name = (EditText) view.findViewById(R.id.et_account_name);
		et_account_description = (EditText) view.findViewById(R.id.et_account_description);
		sp_locale = (Spinner) view.findViewById(R.id.sp_locale);
		bt_submit = (Button) view.findViewById(R.id.bt_submit);
		bt_submit.setOnClickListener(this);
    	tv_error = (TextView) view.findViewById(R.id.tv_error);
    	bt_cancel = (Button) view.findViewById(R.id.bt_cancel);
    	bt_cancel.setOnClickListener(this);
    	
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
		
    	et_list.add(et_account_name);
    	et_list.add(et_account_description);
    	view_list.add(bt_submit);
    	view_list.add(bt_cancel);
    	et_account_name.setFocusable(false);
    	et_account_description.setFocusable(false);
    	
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

	@Override
	public void onClick(View v) {
		if (iView != null)	iView.closeMenu();
		closeVirtualKeyboard();
		switch(v.getId()) {
		case R.id.bt_submit:
			if ( (et_account_name.getText().toString().trim().length() == 0) 
		            || (et_account_description.getText().toString().trim().length() == 0)) {
				setErrorMsg("Please fill all blanks.");
			}else if (mAddAccountFragmentButtonListener != null) {
				disableButtonListener();
				mAddAccountFragmentButtonListener.submit(
						et_account_name.getText().toString().trim(), 
						et_account_description.getText().toString().trim(), 
						sp_locale.getSelectedItem().toString());
			}
			break;
		case R.id.bt_cancel:
			if (mAddAccountFragmentButtonListener != null) {
				disableButtonListener();
				mAddAccountFragmentButtonListener.cancel();
			}
			break;
//		case R.id.bt_login:
//			if (mLogInFragmentButtonListener != null) {
//				// initialize the error msg text view
//				if (getActivity() != null && getView() != null) {
//		    		getActivity().runOnUiThread(new Runnable(){
//		    			@Override
//		    			public void run() {
//		    				tv_error.setText("");
//		    			}
//		        	});
//		    	}
//				mLogInFragmentButtonListener.submitLogIn(et_username.getText().toString(), et_password.getText().toString());
//				disableButtonListener();
//			}
//			break;
//		case R.id.bt_register:
//			if (mLogInFragmentButtonListener != null) {
//				mLogInFragmentButtonListener.submitRegister();
//				disableButtonListener();
//			}
//			break;
		}
	}

	@Override
	public void resetFragment() {
		// initialize the error msg text view
		if (getActivity() != null && getView() != null) {
			getActivity().runOnUiThread(new Runnable(){
				@Override
				public void run() {
					et_account_name.setText("");
					et_account_description.setText("");
					tv_error.setText("");
					sp_locale.setSelection(0);
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
	public void updateAddAccountFragmentButtonListener(
			AddAccountFragmentButtonListener mAddAccountFragmentButtonListener) {
		this.mAddAccountFragmentButtonListener = mAddAccountFragmentButtonListener;
	}
}
