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
import android.widget.TextView;

public class LogInFragment extends BaseFragment implements OnClickListener, UpdateLogInFragmentButtonListener {
	@SuppressWarnings("unused")
	private static final String TAG = LogInFragment.class.getSimpleName();
	
	private static final String ARG_USERNAME = "username";
	private String username;
	
	private LogInFragmentButtonListener mLogInFragmentButtonListener;
	
	private EditText et_username;
	private EditText et_password;
	private Button bt_login;
	private TextView tv_error;
	private Button bt_register;
	private View bgLayout1;
	private View bgLayout2;
	private View bgLayout3;
	private View bgLayout4;
	
	public LogInFragment() {
		super();
	}
	
	public static LogInFragment create(String _username) {
		LogInFragment fragment = new LogInFragment();
		Bundle args = new Bundle();
		args.putString(ARG_USERNAME, _username);
		fragment.setArguments(args);
		return fragment;
    }
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		username = getArguments().getString(ARG_USERNAME);
		
    }
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = (ViewGroup) inflater.inflate(R.layout.fragment_login, container, false);
		
		et_username = (EditText) view.findViewById(R.id.et_username);
    	et_password = (EditText) view.findViewById(R.id.et_password);
    	bt_login = (Button) view.findViewById(R.id.bt_login);
    	bt_login.setOnClickListener(this);
    	tv_error = (TextView) view.findViewById(R.id.tv_error);
    	bt_register = (Button) view.findViewById(R.id.bt_register);
    	bt_register.setOnClickListener(this);
    	
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
    	et_username.setText(username);
		
    	view_list.add(bt_login);
    	view_list.add(bt_register);
    	et_list.add(et_username);
    	et_list.add(et_password);
		et_username.setFocusable(false);
    	et_password.setFocusable(false);
    	
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
		case R.id.bt_login:
			if (mLogInFragmentButtonListener != null) {
				// initialize the error msg text view
				if (getActivity() != null && getView() != null) {
		    		getActivity().runOnUiThread(new Runnable(){
		    			@Override
		    			public void run() {
		    				tv_error.setText("");
		    			}
		        	});
		    	}
				mLogInFragmentButtonListener.submitLogIn(et_username.getText().toString(), et_password.getText().toString());
				disableButtonListener();
			}
			break;
		case R.id.bt_register:
			if (mLogInFragmentButtonListener != null) {
				mLogInFragmentButtonListener.submitRegister();
				disableButtonListener();
			}
			break;
		}
	}

	@Override
	public void updateLogInFragmentButtonListener(LogInFragmentButtonListener mLogInFragmentButtonListener) {
		this.mLogInFragmentButtonListener = mLogInFragmentButtonListener;
	}

	@Override
	public void resetFragment() {
		// initialize the error msg text view
		if (getActivity() != null && getView() != null) {
			getActivity().runOnUiThread(new Runnable(){
				@Override
				public void run() {
					tv_error.setText("");
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
}
