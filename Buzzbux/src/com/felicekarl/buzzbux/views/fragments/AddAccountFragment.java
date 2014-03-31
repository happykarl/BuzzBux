package com.felicekarl.buzzbux.views.fragments;

import com.felicekarl.buzzbux.R;
import com.felicekarl.buzzbux.listeners.AddAccountFragmentButtonListener;
import com.felicekarl.buzzbux.listeners.UpdateAddAccountFragmentButtonListener;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
/**
 * AddAccountFragment.
 * @author Karl
 *
 */
public class AddAccountFragment extends AbstractBaseFragment implements OnClickListener, UpdateAddAccountFragmentButtonListener {
	/**
	 * AddAccountFragmentButtonListener.
	 */
    private AddAccountFragmentButtonListener mAddAccountFragmentButtonListener;
	/**
	 * AccountName EditText.
	 */
    private EditText etAccountName;
    /**
     * Description EditText.
     */
    private EditText etAccountDescription;
    /**
     * Drop Box menu.
     */
    private Spinner spLocale;
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
    public AddAccountFragment() {
        super();
    }
    /**
	 * Create AddAccountFragment instance.
	 * @return AddAccountFragment
	 */
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
		
        etAccountName = (EditText) view.findViewById(R.id.et_account_name);
        etAccountDescription = (EditText) view.findViewById(R.id.et_account_description);
        spLocale = (Spinner) view.findViewById(R.id.sp_locale);
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
		
    	etList.add(etAccountName);
    	etList.add(etAccountDescription);
    	viewList.add(btSubmit);
    	viewList.add(btCancel);
    	etAccountName.setFocusable(false);
    	etAccountDescription.setFocusable(false);
    	
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
                if ( (etAccountName.getText().toString().trim().length() == 0) 
                        || (etAccountDescription.getText().toString().trim().length() == 0)) {
                    setErrorMsg("Please fill all blanks.");
                } else if (mAddAccountFragmentButtonListener != null) {
                    disableButtonListener();
                    mAddAccountFragmentButtonListener.submit(
                            etAccountName.getText().toString().trim(), 
                            etAccountDescription.getText().toString().trim(), 
                            spLocale.getSelectedItem().toString());
                }
                break;
            case R.id.bt_cancel:
                if (mAddAccountFragmentButtonListener != null) {
                    disableButtonListener();
                    mAddAccountFragmentButtonListener.cancel();
                }
                break;
        }
    }

    @Override
    public void resetFragment() {
        if (getActivity() != null && getView() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    etAccountName.setText("");
                    etAccountDescription.setText("");
                    tvError.setText("");
                    spLocale.setSelection(0);
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
    
    @Override
    public void updateAddAccountFragmentButtonListener(
			AddAccountFragmentButtonListener pAddAccountFragmentButtonListener) {
        mAddAccountFragmentButtonListener = pAddAccountFragmentButtonListener;
    }
}
