package com.felicekarl.buzzbux.views.fragments;

import java.util.Calendar;
import java.util.Locale;

import com.felicekarl.buzzbux.R;
import com.felicekarl.buzzbux.listeners.EditTransactionFragmentButtonListener;
import com.felicekarl.buzzbux.listeners.UpdateEditTransactionFragmentButtonListener;
import com.felicekarl.buzzbux.models.LocaleParser;
import com.felicekarl.buzzbux.models.Money;
import com.felicekarl.buzzbux.models.TransType;
import com.felicekarl.buzzbux.models.Transaction;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
/**
 * EditTransactionFragment.
 * @author Karl
 *
 */
public class EditTransactionFragment extends AbstractBaseFragment implements OnClickListener, UpdateEditTransactionFragmentButtonListener {
    /**
     * EditTransactionFragmentButtonListener.
     */
    private EditTransactionFragmentButtonListener mEditTransactionFragmentButtonListener;
	/**
	 * Transaction Amount EditText.
	 */
    private EditText etTransAmount;
    /**
     * Description EditText.
     */
    private EditText etTransDescription;
    /**
     * Dropbox Menu for Transaction Type.
     */
    private Spinner spTransType;
    /**
     * Submit Button.
     */
    private Button btSubmit;
    /**
     * Error TextView.
     */
    private TextView tvError;
    /**
     * Currency TextView.
     */
    private TextView tvCurrency;
    /**
     * DatePicker for Transaction Date.
     */
    private DatePicker dpDate;
    /**
     * Calendar to hold date.
     */
    private Calendar calendar;
    /**
     * Cancel Button.
     */
    private Button btCancel;
    /**
     * Delete Button.
     */
    private Button btDelete;
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
	 * Locale.
	 */
    private Locale locale;
    /**
     * Amount of Money.
     */
    private Money amount;
    /**
	 * Constructor.
	 */
    public EditTransactionFragment() {
        super();
    }
    /**
	 * Create EditTransactionFragment instance.
	 * @return EditTransactionFragment
	 */
    public static EditTransactionFragment create() {
        EditTransactionFragment fragment = new EditTransactionFragment();
        return fragment;
    }
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    }
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = (ViewGroup) inflater.inflate(R.layout.fragment_edit_transaction, container, false);
		
        etTransAmount = (EditText) view.findViewById(R.id.et_trans_amount);
        etTransDescription = (EditText) view.findViewById(R.id.et_trans_description);
        spTransType = (Spinner) view.findViewById(R.id.sp_trans_type);
        btSubmit = (Button) view.findViewById(R.id.bt_submit);
        btSubmit.setOnClickListener(this);
        tvError = (TextView) view.findViewById(R.id.tv_error);
        tvCurrency = (TextView) view.findViewById(R.id.tv_currency);
        btCancel = (Button) view.findViewById(R.id.bt_cancel);
        btCancel.setOnClickListener(this);
        btDelete = (Button) view.findViewById(R.id.bt_delete);
        btDelete.setOnClickListener(this);
    	
    	dpDate = (DatePicker) view.findViewById(R.id.dp_date);
    	
    	calendar = Calendar.getInstance();
    	dpDate.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), new OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar = Calendar.getInstance();
                calendar.set(year, monthOfYear, dayOfMonth);
            }
        });
    	
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
		
    	etList.add(etTransAmount);
    	etList.add(etTransDescription);
    	viewList.add(btSubmit);
    	viewList.add(btCancel);
    	etTransAmount.setFocusable(false);
    	etTransDescription.setFocusable(false);
    	
        etTransAmount.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (locale != null && amount != null) {
                        etTransAmount.setText(String.valueOf(amount.getValue()));
                    }
                } else {
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
                        }
                    }
                }
            }
        });
    	
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
    /**
     * Set Currency.
     * @param currency currency
     */
    public void setCurrency(final String currency) {
        locale = LocaleParser.parseLocale(currency);
        amount = new Money(locale, 0);
        if (getActivity() != null && getView() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tvCurrency.setText("Amount (Currency - " + currency + ")");
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
                setErrorMsg("");
                if ( (etTransAmount.getText().toString().trim().length() == 0) 
                         || (etTransDescription.getText().toString().trim().length() == 0)) {
                    setErrorMsg("Please fill all blanks.");
                } else if (mEditTransactionFragmentButtonListener != null) {
                    disableButtonListener();
                    mEditTransactionFragmentButtonListener.submit(
                            spTransType.getSelectedItem().toString(), 
                            String.valueOf(amount.getValue()), 
                            etTransDescription.getText().toString().trim(),
                            calendar);
                }
                break;
            case R.id.bt_cancel:
                if (mEditTransactionFragmentButtonListener != null) {
                    disableButtonListener();
                    mEditTransactionFragmentButtonListener.cancel();
                }
                break;
            case R.id.bt_delete:
                confirmDeleteDialog();
                break;
        }
    }

    @Override
    public void resetFragment() {
        if (getActivity() != null && getView() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    etTransAmount.setText("");
                    etTransDescription.setText("");
                    tvError.setText("");
                    tvCurrency.setText("Amount");
                    spTransType.setSelection(0);
					
                    calendar = Calendar.getInstance();
                    dpDate.updateDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
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
    public void updateEditTransactionFragmentButtonListener(
			EditTransactionFragmentButtonListener pEditTransactionFragmentButtonListener) {
        mEditTransactionFragmentButtonListener = pEditTransactionFragmentButtonListener;
    }
    /**
     * Fill TextView & EditText with information of Transaction.
     * @param transaction Transaction info to be filled with
     */
    public void fillEditTransactionForm(final Transaction transaction) {
        if (getActivity() != null && getView() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    TransType type = transaction.getType();
                    int position = 0;
                    while (!spTransType.getItemAtPosition(position).toString().equals(type.toString())) {
                        position++;
                    }
                    spTransType.setSelection(position);
                    amount = transaction.getAmount();
                    etTransAmount.setText(transaction.getAmount().toString());
                    String description = transaction.getDescription();
                    etTransDescription.setText(description);
                    calendar = Calendar.getInstance();
                    calendar.setTime(transaction.getDate());
                    dpDate.updateDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                }
            });
        }
    }
	
    /**
     * Pop-Up Confirm Dialog for deletion.
     */
    private void confirmDeleteDialog() {
        AlertDialog.Builder dialoge = new AlertDialog.Builder(getActivity());
        dialoge.setTitle("Confirm to delete Transaction?");
        dialoge.setPositiveButton("Delete", new android.content.DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (mEditTransactionFragmentButtonListener != null) {
                    disableButtonListener();
                    mEditTransactionFragmentButtonListener.delete();
                }
            }
        });
        dialoge.setNegativeButton("Cancel", new android.content.DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                enableButtonListener();
            }
        });
        dialoge.show();
    }
}
