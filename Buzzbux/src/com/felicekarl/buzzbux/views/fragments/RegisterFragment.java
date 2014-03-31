package com.felicekarl.buzzbux.views.fragments;

import com.felicekarl.buzzbux.R;
import com.felicekarl.buzzbux.listeners.RegisterFragmentButtonListener;
import com.felicekarl.buzzbux.listeners.UpdateRegisterFragmentButtonListener;
import com.felicekarl.buzzbux.models.User;

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
 * RegisterFragment.
 * @author Karl
 *
 */
public class RegisterFragment extends AbstractBaseFragment implements OnClickListener, UpdateRegisterFragmentButtonListener {
    /**
     * RegisterFragmentButtonListener.
     */
	private RegisterFragmentButtonListener mRegisterFragmentButtonListener;
    /**
	 * Username EditText.
	 */
    private EditText etUsername;
    /**
     * Password EditText.
     */
    private EditText etPassword;
    /**
     * FirstName EditText.
     */
    private EditText etFirstName;
    /**
     * LastName EditText.
     */
    private EditText etLastName;
    /**
     * Submit Button.
     */
    private Button btSubmit;
    /**
     * Error TextView.
     */
    private TextView tvError;
    /**
     * Cancel Button.
     */
    private Button btCancel;
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
    public RegisterFragment() {
        super();
    }
    /**
   	 * Create RegisterFragment instance.
   	 * @return RegisterFragment
   	 */
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
		
        etUsername = (EditText) view.findViewById(R.id.et_username);
        etPassword = (EditText) view.findViewById(R.id.et_password);
        etFirstName = (EditText) view.findViewById(R.id.et_firstname);
        etLastName = (EditText) view.findViewById(R.id.et_lastname);
        btSubmit = (Button) view.findViewById(R.id.bt_submit);
        btSubmit.setOnClickListener(this);
        tvError = (TextView) view.findViewById(R.id.tv_error);
        btCancel = (Button) view.findViewById(R.id.bt_cancel);
        btCancel.setOnClickListener(this);
    	
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
    	viewList.add(btSubmit);
    	viewList.add(btCancel);
    	// unfocus editable text
    	etList.add(etUsername);
    	etList.add(etPassword);
    	etList.add(etFirstName);
    	etList.add(etLastName);
    	etUsername.setFocusable(false);
    	etPassword.setFocusable(false);
    	etFirstName.setFocusable(false);
    	etLastName.setFocusable(false);
    	
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
            case R.id.bt_submit:
                if ( (etUsername.getText().toString().trim().length() == 0) 
		            || (etPassword.getText().toString().trim().length() == 0)
		            || (etFirstName.getText().toString().trim().length() == 0)
		            || (etLastName.getText().toString().trim().length() == 0)) {
                    setErrorMsg("Please fill all blanks.");
                } else {
                    setErrorMsg("");
                    if (mRegisterFragmentButtonListener != null) {
                        disableButtonListener();
                        User user = new User(etUsername.getText().toString().trim(), etFirstName.getText().toString().trim(), etLastName.getText().toString().trim());
                        mRegisterFragmentButtonListener.submitRegister(user, etPassword.getText().toString().trim());
					
                    }
                }
                break;
            case R.id.bt_cancel:
                if (mRegisterFragmentButtonListener != null) {
                    disableButtonListener();
                    mRegisterFragmentButtonListener.cancel();
                }
                break;
        }
    }

    @Override
    public void resetFragment() {
		// initialize the error msg text view
        if (getActivity() != null && getView() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    setErrorMsg("");
                    etUsername.setText("");
                    etPassword.setText("");
                    etFirstName.setText("");
                    etLastName.setText("");
                }
            });
        }
        enableButtonListener();
    }

    @Override
    public void updateRegisterFragmentButtonListener(RegisterFragmentButtonListener pRegisterFragmentButtonListener) {
        mRegisterFragmentButtonListener = pRegisterFragmentButtonListener;
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
