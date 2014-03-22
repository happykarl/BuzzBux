package com.felicekarl.buzzbux.views.fragments;

import com.felicekarl.buzzbux.R;
import com.felicekarl.buzzbux.listeners.*;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class DashboardFragment extends BaseFragment implements OnClickListener,
		UpdateDashboardFragmentButtonListener {
	private static final String TAG = DashboardFragment.class.getSimpleName();
	
	private LinearLayout ll_manager_user;
	private LinearLayout ll_manager_account;
	private LinearLayout ll_report_transaction;
	private LinearLayout ll_settings;
	
	private DashboardFragmentButtonListener mDashboardFragmentButtonListener;
	
	
	public static DashboardFragment create() {
		DashboardFragment fragment = new DashboardFragment();
		return fragment;
    }
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
    }
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = (ViewGroup) inflater.inflate(R.layout.fragment_dashboard, container, false);
		
		ll_manager_user = (LinearLayout) view.findViewById(R.id.ll_manager_user);
		ll_manager_user.setOnClickListener(this);
		ll_manager_account = (LinearLayout) view.findViewById(R.id.ll_manager_account);
		ll_manager_account.setOnClickListener(this);
		ll_report_transaction = (LinearLayout) view.findViewById(R.id.ll_report_transaction);
		ll_report_transaction.setOnClickListener(this);
		ll_settings = (LinearLayout) view.findViewById(R.id.ll_settings);
		ll_settings.setOnClickListener(this);
		
		view_list.add(ll_manager_user);
		view_list.add(ll_manager_account);
		view_list.add(ll_report_transaction);
		view_list.add(ll_settings);
		
		slideUpFragment();
    	
		return view;
	}
	
	@Override
	protected void enableEditText() {
		// TODO Auto-generated method stub
	}

	@Override
	protected void disableEditText() {
		// TODO Auto-generated method stub
	}

	@Override
	public void onClick(View v) {
		if (iView != null)	iView.closeMenu();
		disableButtonListener();
		closeVirtualKeyboard();
		switch(v.getId()) {
		case R.id.ll_manager_user:
			if (mDashboardFragmentButtonListener != null) {
				mDashboardFragmentButtonListener.submitManageUser();
			}
			break;
		case R.id.ll_manager_account:
			if (mDashboardFragmentButtonListener != null) {
				mDashboardFragmentButtonListener.submitManageAccount();
			}
			break;
		case R.id.ll_report_transaction:
			if (mDashboardFragmentButtonListener != null) {
				mDashboardFragmentButtonListener.submitReportTransaction();
			}
			break;
		case R.id.ll_settings:
			if (mDashboardFragmentButtonListener != null) {
				mDashboardFragmentButtonListener.submitSettings();
			}
			break;
		}	
	}

	@Override
	public void updateDashboardFragmentButtonListener(
			DashboardFragmentButtonListener mDashboardFragmentButtonListener) {
		this.mDashboardFragmentButtonListener = mDashboardFragmentButtonListener;
	}

	@Override
	public void resetFragment() {
		enableButtonListener();
	}

	@Override
	public void enableButtonListener() {
		Log.d(TAG, "enableButtonListener");
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
