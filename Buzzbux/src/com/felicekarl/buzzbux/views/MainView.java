package com.felicekarl.buzzbux.views;

import com.felicekarl.buzzbux.R;
import com.felicekarl.buzzbux.activities.MainActivity;
import com.felicekarl.buzzbux.listeners.DashboardFragmentButtonListener;
import com.felicekarl.buzzbux.listeners.LogInFragmentButtonListener;
import com.felicekarl.buzzbux.listeners.ManageAccountFragmentButtonListener;
import com.felicekarl.buzzbux.listeners.RegisterFragmentButtonListener;
import com.felicekarl.buzzbux.views.fragments.*;
import com.felicekarl.buzzbux.views.fragments.BaseFragment.DIRECTION;

import android.app.FragmentManager;
import android.content.Context;
import android.util.Log;
import android.widget.ListView;

public class MainView implements IView {
	private static final String TAG = MainView.class.getSimpleName();
	private FragmentManager mFragmentManager;
	private TypeView curTypeView;
	
	private SplashFragment mSplashFragment;
	private LogInFragment mLogInFragment;
	private ActionBarFragment mActionBarFragment;
	private FooterFragment mFooterFragment;
	private DashboardFragment mDashboardFragment;
	private RegisterFragment mRegisterFragment;
	private ManageAccountFragment mManageAccountFragment;
	private ManageTransactionFragment mManageTransactionFragment;
	
	public MainView(Context context) {
		this.mFragmentManager = ((MainActivity) context).getFragmentManager();
		/* Initialize curTypeView as Splash Screen */
		curTypeView = TypeView.SPLASH;
		
		/* add dashboard fragment */
		mDashboardFragment = DashboardFragment.create();
		mDashboardFragment.setIView(this);
		mFragmentManager.beginTransaction().add(R.id.main, mDashboardFragment).commit();
		
		/* add manage account fragment */
		mManageAccountFragment = ManageAccountFragment.create();
		mManageAccountFragment.setIView(this);
		mFragmentManager.beginTransaction().add(R.id.main, mManageAccountFragment).commit();
		
		/* add manage transaction fragment */
		mManageTransactionFragment = ManageTransactionFragment.create();
		mManageTransactionFragment.setIView(this);
		mFragmentManager.beginTransaction().add(R.id.main, mManageTransactionFragment).commit();
		
		/* add login fragment */
		mLogInFragment = LogInFragment.create("admin");
		mLogInFragment.setIView(this);
		mFragmentManager.beginTransaction().add(R.id.main, mLogInFragment).commit();
		
		/* add register fragment */
		mRegisterFragment = RegisterFragment.create();
		mRegisterFragment.setIView(this);
		mFragmentManager.beginTransaction().add(R.id.main, mRegisterFragment).commit();
		
		/* add splash screen fragment */
		mSplashFragment = SplashFragment.create();
		mSplashFragment.setIView(this);
		mFragmentManager.beginTransaction().add(R.id.main, mSplashFragment).commit();
		
		/* add actionbar fragment */
		mActionBarFragment = ActionBarFragment.create();
		mActionBarFragment.setIView(this);
		mFragmentManager.beginTransaction().add(R.id.main, mActionBarFragment).commit();
		
		/* add footer fragment */
		mFooterFragment = FooterFragment.create();
		mFooterFragment.setIView(this);
		mFragmentManager.beginTransaction().add(R.id.main, mFooterFragment).commit();
		
	}

	@Override
	public void setView(TypeView type) {
		if (!curTypeView.equals(type)) {
			mSplashFragment.disableButtonListener();
			mLogInFragment.disableButtonListener();
			mActionBarFragment.disableButtonListener();
			mFooterFragment.disableButtonListener();
			mDashboardFragment.disableButtonListener();
			mRegisterFragment.disableButtonListener();
			mManageAccountFragment.disableButtonListener();
			mManageTransactionFragment.disableButtonListener();
			
			if (curTypeView.equals(TypeView.SPLASH) && type.equals(TypeView.LOGIN)) {
				mSplashFragment.toggle(false, true, DIRECTION.BOTTOM);
				mLogInFragment.toggle(true, true, DIRECTION.TOP);
				mActionBarFragment.toggle(true, true, DIRECTION.TOP);
				mFooterFragment.toggle(false, false, DIRECTION.TOP);
				mDashboardFragment.toggle(false, false, DIRECTION.TOP);
				mRegisterFragment.toggle(false, false, DIRECTION.TOP);
				mManageAccountFragment.toggle(false, false, DIRECTION.TOP);
				mManageTransactionFragment.toggle(false, false, DIRECTION.TOP);
				
				mLogInFragment.resetFragment();
			} else if (curTypeView.equals(TypeView.LOGIN) && type.equals(TypeView.DASHBOARD)) {
				mSplashFragment.toggle(false, false, DIRECTION.BOTTOM);
				mLogInFragment.toggle(false, true, DIRECTION.BOTTOM);
				mActionBarFragment.toggle(true, false, DIRECTION.TOP);
				mFooterFragment.toggle(false, false, DIRECTION.BOTTOM);
				mDashboardFragment.toggle(true, true, DIRECTION.TOP);
				mRegisterFragment.toggle(false, false, DIRECTION.TOP);
				mManageAccountFragment.toggle(false, false, DIRECTION.TOP);
				mManageTransactionFragment.toggle(false, false, DIRECTION.TOP);
				
				mDashboardFragment.resetFragment();
			} else if (curTypeView.equals(TypeView.LOGIN) && type.equals(TypeView.REGISTER)) {
				mSplashFragment.toggle(false, false, DIRECTION.BOTTOM);
				mLogInFragment.toggle(true, false, DIRECTION.TOP);
				mActionBarFragment.toggle(true, false, DIRECTION.TOP);
				mFooterFragment.toggle(false, false, DIRECTION.BOTTOM);
				mDashboardFragment.toggle(false, false, DIRECTION.TOP);
				mRegisterFragment.toggle(true, true, DIRECTION.BOTTOM);
				mManageAccountFragment.toggle(false, false, DIRECTION.TOP);
				mManageTransactionFragment.toggle(false, false, DIRECTION.TOP);
				
				mRegisterFragment.resetFragment();
			} else if (curTypeView.equals(TypeView.REGISTER) && type.equals(TypeView.LOGIN)) {
				mSplashFragment.toggle(false, false, DIRECTION.BOTTOM);
				mLogInFragment.toggle(true, false, DIRECTION.TOP);
				mActionBarFragment.toggle(true, false, DIRECTION.TOP);
				mFooterFragment.toggle(false, false, DIRECTION.BOTTOM);
				mDashboardFragment.toggle(false, false, DIRECTION.TOP);
				mRegisterFragment.toggle(false, true, DIRECTION.BOTTOM);
				mManageAccountFragment.toggle(false, false, DIRECTION.TOP);
				mManageTransactionFragment.toggle(false, false, DIRECTION.TOP);
				
				mLogInFragment.resetFragment();
			} else if (curTypeView.equals(TypeView.REGISTER) && type.equals(TypeView.DASHBOARD)) {
				mSplashFragment.toggle(false, false, DIRECTION.BOTTOM);
				mLogInFragment.toggle(false, false, DIRECTION.TOP);
				mActionBarFragment.toggle(true, false, DIRECTION.TOP);
				mFooterFragment.toggle(false, false, DIRECTION.BOTTOM);
				mDashboardFragment.toggle(true, true, DIRECTION.TOP);
				mRegisterFragment.toggle(false, true, DIRECTION.BOTTOM);
				mManageAccountFragment.toggle(false, false, DIRECTION.TOP);
				mManageTransactionFragment.toggle(false, false, DIRECTION.TOP);
				
				mDashboardFragment.resetFragment();
			} else if (curTypeView.equals(TypeView.DASHBOARD) && type.equals(TypeView.MANAGEACCOUNT)) {
				mSplashFragment.toggle(false, false, DIRECTION.BOTTOM);
				mLogInFragment.toggle(false, false, DIRECTION.TOP);
				mActionBarFragment.toggle(true, false, DIRECTION.TOP);
				mFooterFragment.toggle(true, true, DIRECTION.BOTTOM);
				mDashboardFragment.toggle(false, true, DIRECTION.LEFT);
				mRegisterFragment.toggle(false, false, DIRECTION.BOTTOM);
				mManageAccountFragment.toggle(true, true, DIRECTION.RIGHT);
				mManageTransactionFragment.toggle(false, false, DIRECTION.TOP);
				
				mManageAccountFragment.resetFragment();
			} else if (curTypeView.equals(TypeView.MANAGEACCOUNT) && type.equals(TypeView.MANAGETRANSACTION)) {
				Log.d(TAG, "MANAGEACCOUNT -> MANAGETRANSACTION");
				mSplashFragment.toggle(false, false, DIRECTION.BOTTOM);
				mLogInFragment.toggle(false, false, DIRECTION.TOP);
				mActionBarFragment.toggle(true, false, DIRECTION.TOP);
				mFooterFragment.toggle(true, false, DIRECTION.BOTTOM);
				mDashboardFragment.toggle(false, false, DIRECTION.LEFT);
				mRegisterFragment.toggle(false, false, DIRECTION.BOTTOM);
				mManageAccountFragment.toggle(false, true, DIRECTION.LEFT);
				mManageTransactionFragment.toggle(true, true, DIRECTION.RIGHT);
				
				mManageTransactionFragment.resetFragment();
			}
			curTypeView = type;
		}
	}

	@Override
	public TypeView getView() {
		return curTypeView;
	}

	@Override
	public void closeMenu() {
		mFooterFragment.closeMenu();
	}

	@Override
	public void updateLogInFragmentButtonListener(
			LogInFragmentButtonListener mLogInFragmentButtonListener) {
		mLogInFragment.updateLogInFragmentButtonListener(mLogInFragmentButtonListener);
	}

	@Override
	public void setLogInErrorMsg(String msg) {
		mLogInFragment.setErrorMsg(msg);
	}
	
	@Override
	public void setRegisterErrorMsg(String msg) {
		mRegisterFragment.setErrorMsg(msg);
	}

	@Override
	public void updateRegisterFragmentButtonListener(
			RegisterFragmentButtonListener mRegisterFragmentButtonListener) {
		mRegisterFragment.updateRegisterFragmentButtonListener(mRegisterFragmentButtonListener);
	}

	@Override
	public void enablButtonListener() {
		if (curTypeView.equals(TypeView.LOGIN)) {
			mLogInFragment.enableButtonListener();
		} else if (curTypeView.equals(TypeView.REGISTER)) {
			mRegisterFragment.enableButtonListener();
		}
	}

	@Override
	public void setTitle(String str) {
		mActionBarFragment.setTitle(str);
	}

	@Override
	public void startSpinner() {
		mActionBarFragment.startSpinner();
	}

	@Override
	public void stopSpinner() {
		mActionBarFragment.stopSpinner();
	}

	@Override
	public void updateDashboardFragmentButtonListener(
			DashboardFragmentButtonListener mDashboardFragmentButtonListener) {
		mDashboardFragment.updateDashboardFragmentButtonListener(mDashboardFragmentButtonListener);
	}

	@Override
	public void updateManageAccountFragmentButtonListener(
			ManageAccountFragmentButtonListener mManageAccountFragmentButtonListener) {
		mManageAccountFragment.updateManageAccountFragmentButtonListener(mManageAccountFragmentButtonListener);
	}

	@Override
	public ListView getManageAccountListView() {
		return mManageAccountFragment.getListView();
	}

	@Override
	public ListView getManageTransactionListView() {
		return mManageTransactionFragment.getListView();
	}

	

}
