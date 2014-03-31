package com.felicekarl.buzzbux.views.fragments;

import com.felicekarl.buzzbux.R;
import com.felicekarl.buzzbux.listeners.ManageTransactionFragmentButtonListener;
import com.felicekarl.buzzbux.listeners.UpdateManageTransactionFragmentButtonListener;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
/**
 * ManageTransactionFragment.
 * @author Karl
 *
 */
public class ManageTransactionFragment extends AbstractBaseFragment implements OnClickListener, UpdateManageTransactionFragmentButtonListener {
	/**
	 * Transaction ListView.
	 */
    private ListView lvListTransaction;
    /**
     * Balance TextView.
     */
    private TextView tvBalance;
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
     * Delete Transaction Button.
     */
    private Button btDelete;
	/**
	 * preView.
	 */
    private View prevView;
	/**
	 * ManageTransactionFragmentButtonListener.
	 */
    private ManageTransactionFragmentButtonListener mManageTransactionFragmentButtonListener;
    /**
	 * Constructor.
	 */
    public ManageTransactionFragment() {
		super();
    }
    /**
	 * Create ManageTransactionFragment instance.
	 * @return ManageTransactionFragment
	 */
    public static ManageTransactionFragment create() {
        ManageTransactionFragment fragment = new ManageTransactionFragment();
        return fragment;
    }
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = (ViewGroup) inflater.inflate(R.layout.fragment_manage_transaction, container, false);
		
        lvListTransaction = (ListView) view.findViewById(R.id.lv_list_transaction);
		
        lvListTransaction.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (prevView != null) {
                    prevView.setBackgroundColor(Color.parseColor("#eeeeee"));
                }
                view.setBackgroundColor(Color.parseColor("#222222"));
                prevView = view;
                if (mManageTransactionFragmentButtonListener != null) {
                    iView.openMenu();
                    mManageTransactionFragmentButtonListener.selectTransaction(position);
                }
            }
        });
		
        tvBalance = (TextView) view.findViewById(R.id.tv_balance);
        btDelete = (Button) view.findViewById(R.id.bt_delete);
        btDelete.setOnClickListener(this);
		
		// add bg listener
    	bgLayout1 = view.findViewById(R.id.bgLayout1);
    	bgLayout1.setOnClickListener(this);
    	bgLayout2 = view.findViewById(R.id.bgLayout2);
    	bgLayout2.setOnClickListener(this);
    	bgLayout3 = view.findViewById(R.id.bgLayout3);
    	bgLayout3.setOnClickListener(this);
		
    	slideUpFragment();
    	
        return view;
    }
	/**
	 * Set Balance.
	 * @param balance balance of Account.
	 */
    public void setBalance(final String balance) {
        if (getActivity() != null && getView() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tvBalance.setText(balance);
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
            case R.id.bt_delete:
                disableButtonListener();
                confirmDeleteAccountDialog();
                break;
        }
    }
	
    /**
     * Pop-Up Dialogue.
     */
    private void confirmDeleteAccountDialog() {
        AlertDialog.Builder quitDialog = new AlertDialog.Builder(getActivity());
        quitDialog.setTitle("Confirm to Delete Account?");
        quitDialog.setPositiveButton("Yes", new android.content.DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (mManageTransactionFragmentButtonListener != null) {
                    mManageTransactionFragmentButtonListener.delete();
                }
            }
        });
        quitDialog.setNegativeButton("Cancel", new android.content.DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                enableButtonListener();
            }
        });
        quitDialog.show();
    }

    @Override
    public void resetFragment() {
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
	
    /**
     * Get ListView of Transaction.
     * @return lvListTransaction
     */
    public ListView getLvListTransaction() {
        return lvListTransaction;
    }

    @Override
    public void updateManageTransactionFragmentButtonListener(
			ManageTransactionFragmentButtonListener pManageTransactionFragmentButtonListener) {
        mManageTransactionFragmentButtonListener = pManageTransactionFragmentButtonListener;
    }
}
