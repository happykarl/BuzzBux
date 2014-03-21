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

public class ManageAccountFragment extends BaseFragment implements OnClickListener, UpdateManageAccountFragmentButtonListener {
	@SuppressWarnings("unused")
	private static final String TAG = ManageAccountFragment.class.getSimpleName();
	
	private ListView lv_list_account;
	
	private ManageAccountFragmentButtonListener mManageAccountFragmentButtonListener;
	
	public ManageAccountFragment() {
		super();
	}
	
	public static ManageAccountFragment create() {
		ManageAccountFragment fragment = new ManageAccountFragment();
		return fragment;
    }
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
    }
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = (ViewGroup) inflater.inflate(R.layout.fragment_manage_account, container, false);
		
		lv_list_account = (ListView) view.findViewById(R.id.lv_list_account);
		
		lv_list_account.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (mManageAccountFragmentButtonListener != null) {
					mManageAccountFragmentButtonListener.selectAccount(position);
				}
			}
		});
		
    	slideUpFragment();
    	
		return view;
	}

	@Override
	public void onClick(View v) {
//		if (iView != null)	iView.closeMenu();
//		closeVirtualKeyboard();
//		switch(v.getId()) {
//		
//		}
	}

	@Override
	public void resetFragment() {
		//enable button listener
		enableButtonListener();
	}

	@Override
	public void enableButtonListener() {
//		if (bt_list != null) {
//			for (Button bt : bt_list) {
//				bt.setOnClickListener(this);
//			}
//		}
	}

	@Override
	public void disableButtonListener() {
//		if (bt_list != null) {
//			for (Button bt : bt_list) {
//				bt.setOnClickListener(null);
//			}
//		}
	}
	
	public ListView getListView() {
		return lv_list_account;
	}

	@Override
	public void updateManageAccountFragmentButtonListener(
			ManageAccountFragmentButtonListener mManageAccountFragmentButtonListener) {
		this.mManageAccountFragmentButtonListener = mManageAccountFragmentButtonListener;
	}
}
