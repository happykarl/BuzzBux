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
				Log.d(TAG, "position: " + position);
//				if (mManageAccountFragmentButtonListener != null) {
//					mManageAccountFragmentButtonListener.selectAccount(position);
//				}
			}
		});
		
    	slideUpFragment();
    	
		return view;
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
	
	public ListView getListView() {
		return lv_list_transaction;
	}

	@Override
	public void updateManageTransactionFragmentButtonListener(
			ManageTransactionFragmentButtonListener mManageTransactionFragmentButtonListener) {
		this.mManageTransactionFragmentButtonListener = mManageTransactionFragmentButtonListener;
	}
}
