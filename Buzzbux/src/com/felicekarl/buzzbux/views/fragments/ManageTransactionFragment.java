package com.felicekarl.buzzbux.views.fragments;

import com.felicekarl.buzzbux.R;
import com.felicekarl.buzzbux.listeners.*;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class ManageTransactionFragment extends BaseFragment implements OnClickListener, UpdateManageTransactionFragmentButtonListener {
	@SuppressWarnings("unused")
	private static final String TAG = ManageTransactionFragment.class.getSimpleName();
	
	private ListView lv_list_transaction;
	private TextView tv_balance;
	
	private View bgLayout1;
	private View bgLayout2;
	private View bgLayout3;
	
	private View prevView;
	
	private ManageTransactionFragmentButtonListener mManageTransactionFragmentButtonListener;
	
	public ManageTransactionFragment() {
		super();
	}
	
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
		
		lv_list_transaction = (ListView) view.findViewById(R.id.lv_list_transaction);
		
		lv_list_transaction.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (prevView != null) {
					prevView.setBackgroundColor(Color.parseColor("#eeeeee"));
				}
				view.setBackgroundColor(Color.parseColor("#222222"));
				prevView = view;
				Log.d(TAG, "position: " + position);
				if (mManageTransactionFragmentButtonListener != null) {
					iView.openMenu();
					mManageTransactionFragmentButtonListener.selectTransaction(position);
				}
			}
		});
		
		tv_balance = (TextView) view.findViewById(R.id.tv_balance);
		
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
	
	public void setBalance(final String balance) {
		if (getActivity() != null && getView() != null) {
    		getActivity().runOnUiThread(new Runnable(){
    			@Override
    			public void run() {
    				tv_balance.setText(balance);
    			}
        	});
    	}
	}

	@Override
	public void onClick(View v) {
		if (iView != null)	iView.closeMenu();
		closeVirtualKeyboard();
		switch(v.getId()) {
		
		}
	}

	@Override
	public void resetFragment() {
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
	
	public ListView getListView() {
		return lv_list_transaction;
	}

	@Override
	public void updateManageTransactionFragmentButtonListener(
			ManageTransactionFragmentButtonListener mManageTransactionFragmentButtonListener) {
		this.mManageTransactionFragmentButtonListener = mManageTransactionFragmentButtonListener;
	}
}
