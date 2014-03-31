package com.felicekarl.buzzbux.views;

import com.felicekarl.buzzbux.R;
import com.felicekarl.buzzbux.activities.MainActivity;
import com.felicekarl.buzzbux.listeners.AddAccountFragmentButtonListener;
import com.felicekarl.buzzbux.listeners.AddTransactionFragmentButtonListener;
import com.felicekarl.buzzbux.listeners.DashboardFragmentButtonListener;
import com.felicekarl.buzzbux.listeners.EditTransactionFragmentButtonListener;
import com.felicekarl.buzzbux.listeners.FooterFragmentButtonListener;
import com.felicekarl.buzzbux.listeners.LogInFragmentButtonListener;
import com.felicekarl.buzzbux.listeners.ManageAccountFragmentButtonListener;
import com.felicekarl.buzzbux.listeners.ManageReportFragmentButtonListener;
import com.felicekarl.buzzbux.listeners.ManageTransactionFragmentButtonListener;
import com.felicekarl.buzzbux.listeners.RegisterFragmentButtonListener;
import com.felicekarl.buzzbux.listeners.ShowReportFragmentButtonListener;
import com.felicekarl.buzzbux.models.Transaction;
import com.felicekarl.buzzbux.views.fragments.AbstractBaseFragment.DIRECTION;
import com.felicekarl.buzzbux.views.fragments.ActionBarFragment;
import com.felicekarl.buzzbux.views.fragments.AddAccountFragment;
import com.felicekarl.buzzbux.views.fragments.AddTransactionFragment;
import com.felicekarl.buzzbux.views.fragments.DashboardFragment;
import com.felicekarl.buzzbux.views.fragments.EditTransactionFragment;
import com.felicekarl.buzzbux.views.fragments.FooterFragment;
import com.felicekarl.buzzbux.views.fragments.LogInFragment;
import com.felicekarl.buzzbux.views.fragments.ManageAccountFragment;
import com.felicekarl.buzzbux.views.fragments.ManageReportFragment;
import com.felicekarl.buzzbux.views.fragments.ManageTransactionFragment;
import com.felicekarl.buzzbux.views.fragments.RegisterFragment;
import com.felicekarl.buzzbux.views.fragments.ShowReportFragment;
import com.felicekarl.buzzbux.views.fragments.SplashFragment;

import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ListView;
/**
 * MainView.
 * @author Karl
 *
 */
public class MainView implements IView {
	/**
	 * SharedPreferences.
	 */
    private SharedPreferences preferences;
    /**
     * SharedPreferences.Editor.
     */
    private SharedPreferences.Editor editor;
	/**
	 * FragmentManager.
	 */
    private FragmentManager mFragmentManager;
    /**
     * Current View Type.
     */
    private TypeView curTypeView;
	/**
	 * SplashFragment.
	 */
    private SplashFragment mSplashFragment;
    /**
     * LogInFragment.
     */
    private LogInFragment mLogInFragment;
    /**
     * ActionBarFragment.
     */
    private ActionBarFragment mActionBarFragment;
    /**
     * FooterFragment.
     */
    private FooterFragment mFooterFragment;
    /**
     * DashboardFragment.
     */
    private DashboardFragment mDashboardFragment;
    /**
     * RegisterFragment.
     */
    private RegisterFragment mRegisterFragment;
    /**
     * ManageAccountFragment.
     */
    private ManageAccountFragment mManageAccountFragment;
    /**
     * ManageTransactionFragment.
     */
    private ManageTransactionFragment mManageTransactionFragment;
    /**
     * AddAccountFragment.
     */
    private AddAccountFragment mAddAccountFragment;
    /**
     * AddTransactionFragment.
     */
    private AddTransactionFragment mAddTransactionFragment;
    /**
     * EditTransactionFragment.
     */
    private EditTransactionFragment mEditTransactionFragment;
    /**
     * ManageReportFragment.
     */
    private ManageReportFragment mManageReportFragment;
    /**
     * ShowReportFragment.
     */
    private ShowReportFragment mShowReportFragment;
	/**
	 * Save Android context in MainView.
	 * @param context Android Context
	 */
    public MainView(final Context context) {
		/* shared preference */
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = preferences.edit();
		
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
		
		/* add add account fragment */
        mAddAccountFragment = AddAccountFragment.create();
        mAddAccountFragment.setIView(this);
        mFragmentManager.beginTransaction().add(R.id.main, mAddAccountFragment).commit();
		
		/* add manage report fragment */
        mManageReportFragment = ManageReportFragment.create();
        mManageReportFragment.setIView(this);
        mFragmentManager.beginTransaction().add(R.id.main, mManageReportFragment).commit();
		
		/* add show report fragment */
        mShowReportFragment = ShowReportFragment.create();
        mShowReportFragment.setIView(this);
        mFragmentManager.beginTransaction().add(R.id.main, mShowReportFragment).commit();
		
		/* add manage transaction fragment */
        mManageTransactionFragment = ManageTransactionFragment.create();
        mManageTransactionFragment.setIView(this);
        mFragmentManager.beginTransaction().add(R.id.main, mManageTransactionFragment).commit();
		
		/* add add transaction fragment */
        mAddTransactionFragment = AddTransactionFragment.create();
        mAddTransactionFragment.setIView(this);
        mFragmentManager.beginTransaction().add(R.id.main, mAddTransactionFragment).commit();
		
		/* add edit transaction fragment */
        mEditTransactionFragment = EditTransactionFragment.create();
        mEditTransactionFragment.setIView(this);
        mFragmentManager.beginTransaction().add(R.id.main, mEditTransactionFragment).commit();
		
		/* add login fragment */
        mLogInFragment = LogInFragment.create(preferences.getString("username", ""));
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
            mAddAccountFragment.disableButtonListener();
            mAddTransactionFragment.disableButtonListener();
            mEditTransactionFragment.disableButtonListener();
            mManageReportFragment.disableButtonListener();
            mShowReportFragment.disableButtonListener();
			
            closeMenu();
			
            if (curTypeView.equals(TypeView.SPLASH) && type.equals(TypeView.LOGIN)) {
                mSplashFragment.toggle(false, true, DIRECTION.BOTTOM);
                mLogInFragment.toggle(true, true, DIRECTION.TOP);
                mActionBarFragment.toggle(true, true, DIRECTION.TOP);
                mFooterFragment.toggle(false, false, DIRECTION.TOP);
                mDashboardFragment.toggle(false, false, DIRECTION.TOP);
                mRegisterFragment.toggle(false, false, DIRECTION.TOP);
                mManageAccountFragment.toggle(false, false, DIRECTION.TOP);
                mManageTransactionFragment.toggle(false, false, DIRECTION.TOP);
                mAddAccountFragment.toggle(false, false, DIRECTION.TOP);
                mAddTransactionFragment.toggle(false, false, DIRECTION.TOP);
                mEditTransactionFragment.toggle(false, false, DIRECTION.TOP);
                mManageReportFragment.toggle(false, false, DIRECTION.TOP);
                mShowReportFragment.toggle(false, false, DIRECTION.TOP);
				
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
                mAddAccountFragment.toggle(false, false, DIRECTION.TOP);
                mAddTransactionFragment.toggle(false, false, DIRECTION.TOP);
                mEditTransactionFragment.toggle(false, false, DIRECTION.TOP);
                mManageReportFragment.toggle(false, false, DIRECTION.TOP);
                mShowReportFragment.toggle(false, false, DIRECTION.TOP);
				
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
                mAddAccountFragment.toggle(false, false, DIRECTION.TOP);
                mAddTransactionFragment.toggle(false, false, DIRECTION.TOP);
                mEditTransactionFragment.toggle(false, false, DIRECTION.TOP);
                mManageReportFragment.toggle(false, false, DIRECTION.TOP);
                mShowReportFragment.toggle(false, false, DIRECTION.TOP);
				
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
                mAddAccountFragment.toggle(false, false, DIRECTION.TOP);
                mAddTransactionFragment.toggle(false, false, DIRECTION.TOP);
                mEditTransactionFragment.toggle(false, false, DIRECTION.TOP);
                mManageReportFragment.toggle(false, false, DIRECTION.TOP);
                mShowReportFragment.toggle(false, false, DIRECTION.TOP);
				
                mLogInFragment.resetFragment();
            } else if (type.equals(TypeView.LOGIN)) {
            	mLogInFragment.toggle(true, true, DIRECTION.TOP);
				
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
                mAddAccountFragment.toggle(false, false, DIRECTION.TOP);
                mAddTransactionFragment.toggle(false, false, DIRECTION.TOP);
                mEditTransactionFragment.toggle(false, false, DIRECTION.TOP);
                mManageReportFragment.toggle(false, false, DIRECTION.TOP);
                mShowReportFragment.toggle(false, false, DIRECTION.TOP);
				
                mDashboardFragment.resetFragment();
            } else if (curTypeView.equals(TypeView.DASHBOARD) && type.equals(TypeView.MANAGEACCOUNT)) {
                mSplashFragment.toggle(false, false, DIRECTION.BOTTOM);
                mLogInFragment.toggle(false, false, DIRECTION.TOP);
                mActionBarFragment.toggle(true, false, DIRECTION.TOP);
                mFooterFragment.toggle(true, true, DIRECTION.BOTTOM);
                mDashboardFragment.toggle(false, true, DIRECTION.LEFT);
                mRegisterFragment.toggle(false, false, DIRECTION.BOTTOM);
                mManageAccountFragment.toggle(true, true, DIRECTION.RIGHT);
				//mManageTransactionFragment.toggle(false, false, DIRECTION.TOP);
                mAddAccountFragment.toggle(false, false, DIRECTION.TOP);
                mAddTransactionFragment.toggle(false, false, DIRECTION.TOP);
                mEditTransactionFragment.toggle(false, false, DIRECTION.TOP);
                mManageReportFragment.toggle(false, false, DIRECTION.TOP);
                mShowReportFragment.toggle(false, false, DIRECTION.TOP);
				
                mManageAccountFragment.resetFragment();
            } else if (curTypeView.equals(TypeView.DASHBOARD) && type.equals(TypeView.MANAGEREPORT)) {
                mSplashFragment.toggle(false, false, DIRECTION.BOTTOM);
                mLogInFragment.toggle(false, false, DIRECTION.TOP);
                mActionBarFragment.toggle(true, false, DIRECTION.TOP);
                mFooterFragment.toggle(false, false, DIRECTION.BOTTOM);
                mDashboardFragment.toggle(false, true, DIRECTION.LEFT);
                mRegisterFragment.toggle(false, false, DIRECTION.BOTTOM);
                mManageAccountFragment.toggle(false, false, DIRECTION.RIGHT);
				//mManageTransactionFragment.toggle(false, false, DIRECTION.TOP);
                mAddAccountFragment.toggle(false, false, DIRECTION.TOP);
                mAddTransactionFragment.toggle(false, false, DIRECTION.TOP);
                mEditTransactionFragment.toggle(false, false, DIRECTION.TOP);
                mManageReportFragment.toggle(true, true, DIRECTION.RIGHT);
                mShowReportFragment.toggle(false, false, DIRECTION.TOP);
				
                mManageReportFragment.resetFragment();
                mManageReportFragment.resetSelection();
            } else if (curTypeView.equals(TypeView.MANAGEREPORT) && type.equals(TypeView.DASHBOARD)) {
                mSplashFragment.toggle(false, false, DIRECTION.BOTTOM);
                mLogInFragment.toggle(false, false, DIRECTION.TOP);
                mActionBarFragment.toggle(true, false, DIRECTION.TOP);
                mFooterFragment.toggle(false, false, DIRECTION.BOTTOM);
                mDashboardFragment.toggle(true, true, DIRECTION.LEFT);
                mRegisterFragment.toggle(false, false, DIRECTION.BOTTOM);
                mManageAccountFragment.toggle(false, false, DIRECTION.RIGHT);
				//mManageTransactionFragment.toggle(false, false, DIRECTION.TOP);
                mAddAccountFragment.toggle(false, false, DIRECTION.TOP);
                mAddTransactionFragment.toggle(false, false, DIRECTION.TOP);
                mEditTransactionFragment.toggle(false, false, DIRECTION.TOP);
                mManageReportFragment.toggle(false, true, DIRECTION.RIGHT);
                mShowReportFragment.toggle(false, false, DIRECTION.TOP);
				
                mDashboardFragment.resetFragment();
            } else if (curTypeView.equals(TypeView.MANAGEREPORT) && type.equals(TypeView.SHOWREPORT)) {
                mSplashFragment.toggle(false, false, DIRECTION.BOTTOM);
                mLogInFragment.toggle(false, false, DIRECTION.TOP);
                mActionBarFragment.toggle(true, false, DIRECTION.TOP);
                mFooterFragment.toggle(false, false, DIRECTION.BOTTOM);
                mDashboardFragment.toggle(false, false, DIRECTION.LEFT);
                mRegisterFragment.toggle(false, false, DIRECTION.BOTTOM);
                mManageAccountFragment.toggle(false, false, DIRECTION.RIGHT);
				//mManageTransactionFragment.toggle(false, false, DIRECTION.TOP);
                mAddAccountFragment.toggle(false, false, DIRECTION.TOP);
                mAddTransactionFragment.toggle(false, false, DIRECTION.TOP);
                mEditTransactionFragment.toggle(false, false, DIRECTION.TOP);
                mManageReportFragment.toggle(false, true, DIRECTION.LEFT);
                mShowReportFragment.toggle(true, true, DIRECTION.RIGHT);
				
                mShowReportFragment.resetFragment();
            } else if (curTypeView.equals(TypeView.SHOWREPORT) && type.equals(TypeView.MANAGEREPORT)) {
                mSplashFragment.toggle(false, false, DIRECTION.BOTTOM);
                mLogInFragment.toggle(false, false, DIRECTION.TOP);
                mActionBarFragment.toggle(true, false, DIRECTION.TOP);
                mFooterFragment.toggle(false, false, DIRECTION.BOTTOM);
                mDashboardFragment.toggle(false, false, DIRECTION.LEFT);
                mRegisterFragment.toggle(false, false, DIRECTION.BOTTOM);
                mManageAccountFragment.toggle(false, false, DIRECTION.RIGHT);
				//mManageTransactionFragment.toggle(false, false, DIRECTION.TOP);
                mAddAccountFragment.toggle(false, false, DIRECTION.TOP);
                mAddTransactionFragment.toggle(false, false, DIRECTION.TOP);
                mEditTransactionFragment.toggle(false, false, DIRECTION.TOP);
                mManageReportFragment.toggle(true, true, DIRECTION.LEFT);
                mShowReportFragment.toggle(false, true, DIRECTION.RIGHT);
				
                mManageReportFragment.resetFragment();
            } else if (curTypeView.equals(TypeView.MANAGEACCOUNT) && type.equals(TypeView.DASHBOARD)) {
                mSplashFragment.toggle(false, false, DIRECTION.BOTTOM);
                mLogInFragment.toggle(false, false, DIRECTION.TOP);
                mActionBarFragment.toggle(true, false, DIRECTION.TOP);
                mFooterFragment.toggle(false, true, DIRECTION.BOTTOM);
                mDashboardFragment.toggle(true, true, DIRECTION.LEFT);
                mRegisterFragment.toggle(false, false, DIRECTION.BOTTOM);
                mManageAccountFragment.toggle(false, true, DIRECTION.RIGHT);
				//mManageTransactionFragment.toggle(false, false, DIRECTION.RIGHT);
                mAddAccountFragment.toggle(false, false, DIRECTION.TOP);
                mAddTransactionFragment.toggle(false, false, DIRECTION.TOP);
                mEditTransactionFragment.toggle(false, false, DIRECTION.TOP);
                mManageReportFragment.toggle(false, false, DIRECTION.TOP);
                mShowReportFragment.toggle(false, false, DIRECTION.TOP);
				
                mDashboardFragment.resetFragment();
            } else if (curTypeView.equals(TypeView.MANAGEACCOUNT) && type.equals(TypeView.ADDACCOUNT)) {
                mSplashFragment.toggle(false, false, DIRECTION.BOTTOM);
                mLogInFragment.toggle(false, false, DIRECTION.TOP);
                mActionBarFragment.toggle(true, false, DIRECTION.TOP);
                mFooterFragment.toggle(false, true, DIRECTION.BOTTOM);
                mDashboardFragment.toggle(false, false, DIRECTION.LEFT);
                mRegisterFragment.toggle(false, false, DIRECTION.BOTTOM);
                mManageAccountFragment.toggle(true, false, DIRECTION.RIGHT);
				//mManageTransactionFragment.toggle(false, false, DIRECTION.RIGHT);
                mAddAccountFragment.toggle(true, true, DIRECTION.BOTTOM);
                mAddTransactionFragment.toggle(false, false, DIRECTION.TOP);
                mEditTransactionFragment.toggle(false, false, DIRECTION.TOP);
                mManageReportFragment.toggle(false, false, DIRECTION.TOP);
                mShowReportFragment.toggle(false, false, DIRECTION.TOP);
				
                mAddAccountFragment.resetFragment();
            } else if (curTypeView.equals(TypeView.ADDACCOUNT) && type.equals(TypeView.MANAGEACCOUNT)) {
                mSplashFragment.toggle(false, false, DIRECTION.BOTTOM);
                mLogInFragment.toggle(false, false, DIRECTION.TOP);
                mActionBarFragment.toggle(true, false, DIRECTION.TOP);
                mFooterFragment.toggle(true, true, DIRECTION.BOTTOM);
                mDashboardFragment.toggle(false, false, DIRECTION.LEFT);
                mRegisterFragment.toggle(false, false, DIRECTION.BOTTOM);
                mManageAccountFragment.toggle(true, false, DIRECTION.RIGHT);
				//mManageTransactionFragment.toggle(false, false, DIRECTION.RIGHT);
                mAddAccountFragment.toggle(false, true, DIRECTION.BOTTOM);
                mAddTransactionFragment.toggle(false, false, DIRECTION.TOP);
                mEditTransactionFragment.toggle(false, false, DIRECTION.TOP);
                mManageReportFragment.toggle(false, false, DIRECTION.TOP);
                mShowReportFragment.toggle(false, false, DIRECTION.TOP);
				
                mManageAccountFragment.resetFragment();
            } else if (curTypeView.equals(TypeView.MANAGEACCOUNT) && type.equals(TypeView.MANAGETRANSACTION)) {
                mSplashFragment.toggle(false, false, DIRECTION.BOTTOM);
                mLogInFragment.toggle(false, false, DIRECTION.TOP);
                mActionBarFragment.toggle(true, false, DIRECTION.TOP);
                mFooterFragment.toggle(true, false, DIRECTION.BOTTOM);
                mDashboardFragment.toggle(false, false, DIRECTION.LEFT);
                mRegisterFragment.toggle(false, false, DIRECTION.BOTTOM);
                mManageAccountFragment.toggle(false, true, DIRECTION.LEFT);
                mManageTransactionFragment.toggle(true, true, DIRECTION.RIGHT);
                mAddAccountFragment.toggle(false, false, DIRECTION.TOP);
                mAddTransactionFragment.toggle(false, false, DIRECTION.TOP);
                mEditTransactionFragment.toggle(false, false, DIRECTION.TOP);
                mManageReportFragment.toggle(false, false, DIRECTION.TOP);
                mShowReportFragment.toggle(false, false, DIRECTION.TOP);
				
                mManageTransactionFragment.resetFragment();
            } else if (curTypeView.equals(TypeView.MANAGETRANSACTION) && type.equals(TypeView.MANAGEACCOUNT)) {
                mSplashFragment.toggle(false, false, DIRECTION.BOTTOM);
                mLogInFragment.toggle(false, false, DIRECTION.TOP);
                mActionBarFragment.toggle(true, false, DIRECTION.TOP);
                mFooterFragment.toggle(true, false, DIRECTION.BOTTOM);
                mDashboardFragment.toggle(false, false, DIRECTION.LEFT);
                mRegisterFragment.toggle(false, false, DIRECTION.BOTTOM);
                mManageAccountFragment.toggle(true, true, DIRECTION.LEFT);
                mManageTransactionFragment.toggle(false, true, DIRECTION.RIGHT);
                mAddAccountFragment.toggle(false, false, DIRECTION.TOP);
                mAddTransactionFragment.toggle(false, false, DIRECTION.TOP);
                mEditTransactionFragment.toggle(false, false, DIRECTION.TOP);
                mManageReportFragment.toggle(false, false, DIRECTION.TOP);
                mShowReportFragment.toggle(false, false, DIRECTION.TOP);
				
                mManageAccountFragment.resetFragment();
            } else if (curTypeView.equals(TypeView.MANAGETRANSACTION) && type.equals(TypeView.ADDTRANSACTION)) {
                mSplashFragment.toggle(false, false, DIRECTION.BOTTOM);
                mLogInFragment.toggle(false, false, DIRECTION.TOP);
                mActionBarFragment.toggle(true, false, DIRECTION.TOP);
                mFooterFragment.toggle(false, true, DIRECTION.BOTTOM);
                mDashboardFragment.toggle(false, false, DIRECTION.LEFT);
                mRegisterFragment.toggle(false, false, DIRECTION.BOTTOM);
                mManageAccountFragment.toggle(false, false, DIRECTION.LEFT);
                mManageTransactionFragment.toggle(true, false, DIRECTION.RIGHT);
                mAddAccountFragment.toggle(false, false, DIRECTION.TOP);
                mAddTransactionFragment.toggle(true, true, DIRECTION.BOTTOM);
                mEditTransactionFragment.toggle(false, false, DIRECTION.TOP);
                mManageReportFragment.toggle(false, false, DIRECTION.TOP);
                mShowReportFragment.toggle(false, false, DIRECTION.TOP);
				
                mAddTransactionFragment.resetFragment();
            } else if (curTypeView.equals(TypeView.ADDTRANSACTION) && type.equals(TypeView.MANAGETRANSACTION)) {
                mSplashFragment.toggle(false, false, DIRECTION.BOTTOM);
                mLogInFragment.toggle(false, false, DIRECTION.TOP);
                mActionBarFragment.toggle(true, false, DIRECTION.TOP);
                mFooterFragment.toggle(true, true, DIRECTION.BOTTOM);
                mDashboardFragment.toggle(false, false, DIRECTION.LEFT);
                mRegisterFragment.toggle(false, false, DIRECTION.BOTTOM);
                mManageAccountFragment.toggle(false, false, DIRECTION.LEFT);
                mManageTransactionFragment.toggle(true, false, DIRECTION.RIGHT);
                mAddAccountFragment.toggle(false, false, DIRECTION.TOP);
                mAddTransactionFragment.toggle(false, true, DIRECTION.BOTTOM);
                mEditTransactionFragment.toggle(false, false, DIRECTION.TOP);
                mManageReportFragment.toggle(false, false, DIRECTION.TOP);
                mShowReportFragment.toggle(false, false, DIRECTION.TOP);
				
                mManageTransactionFragment.resetFragment();
            } else if (curTypeView.equals(TypeView.MANAGETRANSACTION) && type.equals(TypeView.EDITTRANSACTION)) {
                mSplashFragment.toggle(false, false, DIRECTION.BOTTOM);
                mLogInFragment.toggle(false, false, DIRECTION.TOP);
                mActionBarFragment.toggle(true, false, DIRECTION.TOP);
                mFooterFragment.toggle(false, true, DIRECTION.BOTTOM);
                mDashboardFragment.toggle(false, false, DIRECTION.LEFT);
                mRegisterFragment.toggle(false, false, DIRECTION.BOTTOM);
                mManageAccountFragment.toggle(false, false, DIRECTION.LEFT);
                mManageTransactionFragment.toggle(true, false, DIRECTION.RIGHT);
                mAddAccountFragment.toggle(false, false, DIRECTION.TOP);
                mAddTransactionFragment.toggle(false, false, DIRECTION.BOTTOM);
                mEditTransactionFragment.toggle(true, true, DIRECTION.BOTTOM);
                mManageReportFragment.toggle(false, false, DIRECTION.TOP);
                mShowReportFragment.toggle(false, false, DIRECTION.TOP);
				
                mEditTransactionFragment.resetFragment();
            } else if (curTypeView.equals(TypeView.EDITTRANSACTION) && type.equals(TypeView.MANAGETRANSACTION)) {
                mSplashFragment.toggle(false, false, DIRECTION.BOTTOM);
                mLogInFragment.toggle(false, false, DIRECTION.TOP);
                mActionBarFragment.toggle(true, false, DIRECTION.TOP);
                mFooterFragment.toggle(true, true, DIRECTION.BOTTOM);
                mDashboardFragment.toggle(false, false, DIRECTION.LEFT);
                mRegisterFragment.toggle(false, false, DIRECTION.BOTTOM);
                mManageAccountFragment.toggle(false, false, DIRECTION.LEFT);
                mManageTransactionFragment.toggle(true, false, DIRECTION.RIGHT);
                mAddAccountFragment.toggle(false, false, DIRECTION.TOP);
                mAddTransactionFragment.toggle(false, false, DIRECTION.BOTTOM);
                mEditTransactionFragment.toggle(false, true, DIRECTION.BOTTOM);
                mManageReportFragment.toggle(false, false, DIRECTION.TOP);
                mShowReportFragment.toggle(false, false, DIRECTION.TOP);
				
                mManageTransactionFragment.resetFragment();
            }
            curTypeView = type;
        }
    }

    @Override
    public TypeView getCurTypeView() {
        return curTypeView;
    }

    @Override
    public void closeMenu() {
    	mFooterFragment.closeMenu();
    }

    @Override
    public void updateLogInFragmentButtonListener(LogInFragmentButtonListener mLogInFragmentButtonListener) {
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
    public void updateRegisterFragmentButtonListener(RegisterFragmentButtonListener mRegisterFragmentButtonListener) {
        mRegisterFragment.updateRegisterFragmentButtonListener(mRegisterFragmentButtonListener);
    }

    @Override
    public void enablButtonListener() {
        if (curTypeView.equals(TypeView.LOGIN)) {
            mLogInFragment.enableButtonListener();
        } else if (curTypeView.equals(TypeView.REGISTER)) {
            mRegisterFragment.enableButtonListener();
        } else if (curTypeView.equals(TypeView.DASHBOARD)) {
            mDashboardFragment.enableButtonListener();
        } else if (curTypeView.equals(TypeView.MANAGEACCOUNT)) {
            mManageAccountFragment.enableButtonListener();
        } else if (curTypeView.equals(TypeView.MANAGETRANSACTION)) {
            mManageTransactionFragment.enableButtonListener();
        } else if (curTypeView.equals(TypeView.MANAGEREPORT)) {
            mManageReportFragment.enableButtonListener();
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
    public void openMenu() {
        mFooterFragment.openMenu();
    }

    @Override
    public void updateDashboardFragmentButtonListener(DashboardFragmentButtonListener mDashboardFragmentButtonListener) {
        mDashboardFragment.updateDashboardFragmentButtonListener(mDashboardFragmentButtonListener);
    }

    @Override
    public void updateManageAccountFragmentButtonListener(
			ManageAccountFragmentButtonListener mManageAccountFragmentButtonListener) {
        mManageAccountFragment.updateManageAccountFragmentButtonListener(mManageAccountFragmentButtonListener);
    }

    @Override
    public ListView getManageAccountListView() {
        return mManageAccountFragment.getLvListAccount();
    }

    @Override
    public ListView getManageTransactionListView() {
        return mManageTransactionFragment.getLvListTransaction();
    }

    @Override
    public void updateFooterFragmentButtonListener(
			FooterFragmentButtonListener mFooterFragmentButtonListener) {
        mFooterFragment.updateFooterFragmentButtonListener(mFooterFragmentButtonListener);
    }

    @Override
    public void updateAddAccountFragmentButtonListener(
			AddAccountFragmentButtonListener mAddAccountFragmentButtonListener) {
        mAddAccountFragment.updateAddAccountFragmentButtonListener(mAddAccountFragmentButtonListener);
    }

    @Override
    public void setAddAccountErrorMsg(String msg) {
        mAddAccountFragment.setErrorMsg(msg);
    }

    @Override
    public void updateAddTransactionFragmentButtonListener(
			AddTransactionFragmentButtonListener mAddTransactionFragmentButtonListener) {
        mAddTransactionFragment.updateAddTransactionFragmentButtonListener(mAddTransactionFragmentButtonListener);
    }

    @Override
    public void setAddTransactionErrorMsg(String msg) {
        mAddTransactionFragment.setErrorMsg(msg);
    }

    @Override
    public void setAddTransactionCurrency(String currency) {
        mAddTransactionFragment.setCurrency(currency);
    }

    @Override
    public void updateManageTransactionFragmentButtonListener(
			ManageTransactionFragmentButtonListener mManageTransactionFragmentButtonListener) {
        mManageTransactionFragment.updateManageTransactionFragmentButtonListener(mManageTransactionFragmentButtonListener);
    }

    @Override
    public void updateEditTransactionFragmentButtonListener(
			EditTransactionFragmentButtonListener mEditTransactionFragmentButtonListener) {
        mEditTransactionFragment.updateEditTransactionFragmentButtonListener(mEditTransactionFragmentButtonListener);
    }

    @Override
    public void fillEditTransactionForm(Transaction transaction) {
        mEditTransactionFragment.fillEditTransactionForm(transaction);
    }

    @Override
    public void setEditTransactionCurrency(String currency) {
        mEditTransactionFragment.setCurrency(currency);
    }

    @Override
    public void setManageTransactionBalance(String balance) {
        mManageTransactionFragment.setBalance(balance);
    }

    @Override
    public ListView getManageReportListView() {
        return mManageReportFragment.getLvListAccount();
    }

    @Override
    public void updateManageReportFragmentButtonListener(
			ManageReportFragmentButtonListener mManageReportFragmentButtonListener) {
        mManageReportFragment.updateManageReportFragmentButtonListener(mManageReportFragmentButtonListener);
    }

    @Override
    public ListView getShowReportListView() {
        return mShowReportFragment.getLvListTransaction();
    }

    @Override
    public void setShowReportAmount(String amount) {
        mShowReportFragment.setAmount(amount);
    }

    @Override
    public void updateShowReportFragmentButtonListener(
			ShowReportFragmentButtonListener mShowReportFragmentButtonListener) {
        mShowReportFragment.updateShowReportFragmentButtonListener(mShowReportFragmentButtonListener);
    }

    @Override
    public View getShowReportGraphView() {
        return mShowReportFragment.getLlGraph();
    }

}
