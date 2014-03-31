package com.felicekarl.buzzbux.views.fragments;

import com.felicekarl.buzzbux.R;
import com.felicekarl.buzzbux.listeners.DashboardFragmentButtonListener;
import com.felicekarl.buzzbux.listeners.UpdateDashboardFragmentButtonListener;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
/**
 * DashboardFragment.
 * @author Karl
 *
 */
public class DashboardFragment extends AbstractBaseFragment implements OnClickListener, UpdateDashboardFragmentButtonListener {
	/**
	 * ManageUser Button.
	 */
    private LinearLayout llManagerUser;
    /**
     * Manage Account Button.
     */
    private LinearLayout llManagerAccount;
    /**
     * Report Transaction Button.
     */
    private LinearLayout llReportTransaction;
    /**
     * Setting Button.
     */
    private LinearLayout llSetting;
	/**
	 * DashboardFragmentButtonListener.
	 */
    private DashboardFragmentButtonListener mDashboardFragmentButtonListener;
    /**
	 * Create DashboardFragment instance.
	 * @return DashboardFragment
	 */
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
		
        llManagerUser = (LinearLayout) view.findViewById(R.id.ll_manager_user);
        llManagerUser.setOnClickListener(this);
        llManagerAccount = (LinearLayout) view.findViewById(R.id.ll_manager_account);
        llManagerAccount.setOnClickListener(this);
        llReportTransaction = (LinearLayout) view.findViewById(R.id.ll_report_transaction);
        llReportTransaction.setOnClickListener(this);
        llSetting = (LinearLayout) view.findViewById(R.id.ll_settings);
        llSetting.setOnClickListener(this);
		
        viewList.add(llManagerUser);
        viewList.add(llManagerAccount);
        viewList.add(llReportTransaction);
        viewList.add(llSetting);
		
        slideUpFragment();
    	
        return view;
    }
	
    @Override
    protected void enableEditText() {
    	
    }

    @Override
    protected void disableEditText() {
    	
    }

    @Override
    public void onClick(View v) {
        if (iView != null) {
            iView.closeMenu();
        }
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
    public void updateDashboardFragmentButtonListener(DashboardFragmentButtonListener pDashboardFragmentButtonListener) {
        mDashboardFragmentButtonListener = pDashboardFragmentButtonListener;
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
	
}
