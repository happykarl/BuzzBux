package com.felicekarl.buzzbux.views.fragments;

import com.felicekarl.buzzbux.R;
import com.felicekarl.buzzbux.listeners.DashboardFragmentButtonListener;
import com.felicekarl.buzzbux.listeners.UpdateDashboardFragmentButtonListener;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
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
     * ImageView for ManageUser
     */
    private TextureView ivManageUser;
    /**
     * Manage Account Button.
     */
    private LinearLayout llManagerAccount;
    /**
     * ImageView for ManageAccount
     */
    private ImageView ivManageAccount;
    /**
     * Report Transaction Button.
     */
    private LinearLayout llReportTransaction;
    /**
     * ImageView for ReportTransaction
     */
    private ImageView ivReportTransaction;
    /**
     * Setting Button.
     */
    private LinearLayout llSetting;
    /**
     * ImageView for Setting
     */
    private ImageView ivSetting;
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
        
        ivManageUser = (TextureView) view.findViewById(R.id.iv_manager_user);
        ivManageAccount = (ImageView) view.findViewById(R.id.iv_manager_account);
        ivReportTransaction = (ImageView) view.findViewById(R.id.iv_report_transaction);
        ivSetting = (ImageView) view.findViewById(R.id.iv_settings);
		
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
    
    /**
     * Draw User Info to Screen.
     * @param username
     * @param firstname
     * @param lastname
     */
    public void drawManageUser(String username, String firstname, String lastname) {
    	Canvas canvas = ivManageUser.lockCanvas(null);
    	Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_menu5);
    	canvas.drawBitmap(bitmap, 0, 0, null);
    	
    	Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    	paint.setColor(Color.parseColor("#ffffff"));
    	paint.setShadowLayer(2f, 0f, 2f, Color.parseColor("#888888"));
    	Rect bounds = new Rect();
    	paint.setTextSkewX(-0.15f);
    	
    	paint.setTextSize(30);
    	paint.getTextBounds(username, 0, username.length(), bounds);
    	canvas.drawText(username, (int) ((bitmap.getWidth() - bounds.width() * 2) / 2), 50, paint);
    	
    	paint.setTextSize(30);
    	paint.getTextBounds(firstname, 0, firstname.length(), bounds);
    	int x = (int) ((bitmap.getWidth()  - bounds.width()) / 2);
    	canvas.drawText(firstname, x, 100, paint);
    	
    	paint.setTextSize(30);
    	paint.getTextBounds(lastname, 0, lastname.length(), bounds);
    	canvas.drawText(lastname, x + firstname.length() * 20 + 5, 100, paint);
    	
    	ivManageUser.unlockCanvasAndPost(canvas);
    }
}
