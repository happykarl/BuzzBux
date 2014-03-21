package com.felicekarl.buzzbux.views.fragments;

import com.felicekarl.buzzbux.R;
import com.felicekarl.buzzbux.listeners.*;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

public class RegisterFragment extends BaseFragment implements OnClickListener, UpdateRegisterFragmentButtonListener {
	@SuppressWarnings("unused")
	private static final String TAG = RegisterFragment.class.getSimpleName();
	
	private RegisterFragmentButtonListener mRegisterFragmentButtonListener;
	
	private EditText et_username;
	private EditText et_password;
	private EditText et_firstname;
	private EditText et_lastname;
	private Button bt_submit;
	private TextView tv_error;
	private Button bt_cancel;
	private View bgLayout1;
	private View bgLayout2;
	private View bgLayout3;
	private View bgLayout4;
	
	public RegisterFragment() {
		super();
	}
	
	public static RegisterFragment create() {
		RegisterFragment fragment = new RegisterFragment();
		return fragment;
    }
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
    }
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = (ViewGroup) inflater.inflate(R.layout.fragment_register, container, false);
		
		et_username = (EditText) view.findViewById(R.id.et_username);
    	et_password = (EditText) view.findViewById(R.id.et_password);
    	et_firstname = (EditText) view.findViewById(R.id.et_firstname);
    	et_lastname = (EditText) view.findViewById(R.id.et_lastname);
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
    	bt_list.add(bt_submit);
    	bt_list.add(bt_cancel);
    	// unfocus editable text
    	et_list.add(et_username);
    	et_list.add(et_password);
    	et_list.add(et_firstname);
    	et_list.add(et_lastname);
		et_username.setFocusable(false);
    	et_password.setFocusable(false);
    	et_firstname.setFocusable(false);
    	et_lastname.setFocusable(false);
    	
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
			if ( (et_username.getText().toString().trim().length() == 0) 
		            || (et_password.getText().toString().trim().length() == 0)
		            || (et_firstname.getText().toString().trim().length() == 0)
		            || (et_lastname.getText().toString().trim().length() == 0)) {
				tv_error.setText("Please fill all blanks.");
		    } else {
		    	tv_error.setText("");
		    	if (mRegisterFragmentButtonListener != null) {
					mRegisterFragmentButtonListener.submitRegister(
							et_username.getText().toString().trim(), 
							et_password.getText().toString().trim(), 
							et_firstname.getText().toString().trim(), 
							et_lastname.getText().toString().trim());
					disableButtonListener();
				}
		    }
			break;
		case R.id.bt_cancel:
			if (mRegisterFragmentButtonListener != null) {
				mRegisterFragmentButtonListener.cancel();
				disableButtonListener();
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
					tv_error.setText("");
					et_username.setText("");
			    	et_password.setText("");
			    	et_firstname.setText("");
			    	et_lastname.setText("");
				}
	    	});
		}
		
		//enable button listener
		enableButtonListener();
	}

	@Override
	public void updateRegisterFragmentButtonListener(
			RegisterFragmentButtonListener mRegisterFragmentButtonListener) {
		this.mRegisterFragmentButtonListener = mRegisterFragmentButtonListener;
	}

	@Override
	public void enableButtonListener() {
		if (bt_list != null) {
			for (Button bt : bt_list) {
				bt.setOnClickListener(this);
			}
		}
	}

	@Override
	public void disableButtonListener() {
		if (bt_list != null) {
			for (Button bt : bt_list) {
				bt.setOnClickListener(null);
			}
		}
	}
}
