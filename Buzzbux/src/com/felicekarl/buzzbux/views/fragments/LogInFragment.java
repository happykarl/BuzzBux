package com.felicekarl.buzzbux.views.fragments;

import com.felicekarl.buzzbux.R;
import com.felicekarl.buzzbux.listeners.LogInFragmentButtonListener;
import com.felicekarl.buzzbux.listeners.UpdateLogInFragmentButtonListener;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
/**
 * LogInFragment.
 * @author Karl
 *
 */
public class LogInFragment extends AbstractBaseFragment implements OnClickListener, UpdateLogInFragmentButtonListener {
	/**
	 * String "username".
	 */
    private static final String ARG_USERNAME = "username";
	/**
	 * Username holder.
	 */
    private String username;
    /**
     * LogInFragmentButtonListener.
     */
    private LogInFragmentButtonListener mLogInFragmentButtonListener;
	/**
	 * Username EditText.
	 */
    private EditText etUsername;
    /**
     * Password EditText.
     */
    private EditText etPassword;
    /**
     * LogIn Button.
     */
    private Button btLogIn;
    /**
     * Error TextView.
     */
    private TextView tvError;
    /**
     * Register Button.
     */
    private Button btRegister;
    /**
     * Background Layout.
     */
    private View bgLayout1;
    /**
     * Background Layout.
     */
    private View bgLayout2;
    /**
     * Background Layout.
     */
    private View bgLayout3;
    /**
     * Background Layout.
     */
    private View bgLayout4;
    /**
     * Constructor.
     */
    public LogInFragment() {
        super();
    }
    /**
	 * Create LogInFragment instance.
	 * @param username username to be shown on the TextView.
	 * @return LogInFragment
	 */
    public static LogInFragment create(String username) {
        LogInFragment fragment = new LogInFragment();
        Bundle args = new Bundle();
        args.putString(ARG_USERNAME, username);
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
		
        etUsername = (EditText) view.findViewById(R.id.et_username);
        etPassword = (EditText) view.findViewById(R.id.et_password);
        btLogIn = (Button) view.findViewById(R.id.bt_login);
        btLogIn.setOnClickListener(this);
        tvError = (TextView) view.findViewById(R.id.tv_error);
        btRegister = (Button) view.findViewById(R.id.bt_register);
    	btRegister.setOnClickListener(this);
    	
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
    	etUsername.setText(username);
		
    	viewList.add(btLogIn);
    	viewList.add(btRegister);
    	etList.add(etUsername);
    	etList.add(etPassword);
    	etUsername.setFocusable(false);
    	etPassword.setFocusable(false);
    	
    	getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    	
    	slideUpFragment();
    	
        return view;
    }
    /**
     * Set Error Message.
     * @param msg error message
     */
    public void setErrorMsg(final String msg) {
        if (getActivity() != null && getView() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tvError.setText(msg);
                }
            });
    	}
    }

    @Override
    public void onClick(View v) {
        if (iView != null) {
            iView.closeMenu();
        }
        closeVirtualKeyboard();
        switch(v.getId()) {
            case R.id.bt_login:
                if (mLogInFragmentButtonListener != null) {
                    disableButtonListener();
                    if (getActivity() != null && getView() != null) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tvError.setText("");
                            }
                        });
                    }
                    mLogInFragmentButtonListener.submitLogIn(etUsername.getText().toString(), etPassword.getText().toString());
                }
                break;
            case R.id.bt_register:
                if (mLogInFragmentButtonListener != null) {
                    disableButtonListener();
                    mLogInFragmentButtonListener.submitRegister();
                }
                break;
        }
    }

    @Override
    public void updateLogInFragmentButtonListener(LogInFragmentButtonListener pLogInFragmentButtonListener) {
        mLogInFragmentButtonListener = pLogInFragmentButtonListener;
    }

    @Override
    public void resetFragment() {
        if (getActivity() != null && getView() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tvError.setText("");
                    etPassword.setText("");
                }
            });
        }
        enableButtonListener();
    }
	
    @Override
    public void enableButtonListener() {
        if (viewList != null) {
            for (View v : viewList) {
                v.setOnClickListener(this);
            }
        }
    }
	
    @Override
    public void disableButtonListener() {
        if (viewList != null) {
            for (View v : viewList) {
                v.setOnClickListener(null);
            }
        }
    }
}
