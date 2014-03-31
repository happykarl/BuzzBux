package com.felicekarl.buzzbux.views.fragments;

import com.felicekarl.buzzbux.R;
import com.felicekarl.buzzbux.views.TypeView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
/**
 * ActionBarFragment.
 * @author Karl
 *
 */
public class ActionBarFragment extends AbstractBaseFragment implements Runnable, OnClickListener {
	/**
	 * String "Dashboard".
	 */
    private static final String DASHBOARD = "Dashboard";
	/**
	 * Loader Animation Drawable.
	 */
    private AnimationDrawable mAnimationDrawable; 
	/**
	 * Title TextView.
	 */
    private TextView tvTitle;
    /**
     * Menu ImageView.
     */
    private ImageView ivMenu;
    /**
     * LogOff Button.
     */
    private Button btLogOff;
	/**
	 * Time of Loading Animation.
	 */
    private static final int LOADING_TIME = 1000;
    /**
     * Timer of Loading Animation.
     */
    private int timeElapsed = 0;
    /**
     * Status of Animation.
     */
    private boolean isLoaded = false;
    /**
     * Animation Loading Thread.
     */
    private Thread thread;
	/**
	 * Contructor.
	 */
    public ActionBarFragment() {
        super();
    }
	/**
	 * Create ActionBarFragment instance.
	 * @return ActionBarFragment
	 */
    public static ActionBarFragment create() {
        ActionBarFragment fragment = new ActionBarFragment();
        return fragment;
    }
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = (ViewGroup) inflater.inflate(R.layout.fragment_actionbar, container, false);
		
        tvTitle = (TextView) view.findViewById(R.id.tv_title);
        ivMenu = (ImageView) view.findViewById(R.id.iv_menu);
        
        ivMenu.setBackgroundResource(R.drawable.bt_menu);
        ivMenu.setOnClickListener(this);
		
        btLogOff = (Button) view.findViewById(R.id.bt_logoff);
        btLogOff.setOnClickListener(this);
		
        slideUpFragment();
    	
        return view;
    }
	
    /**
     * Set title of Screen.
     * @param str title
     */
    public void setTitle(final String str) {
        if (getActivity() != null && getView() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tvTitle.setText(str);
                }
            });
    	}
    }
	/**
	 * Start Spinner Animation.
	 */
    public void startSpinner() {
        ivMenu.setBackgroundResource(R.drawable.pb_menu);
        mAnimationDrawable = (AnimationDrawable) ivMenu.getBackground();
        mAnimationDrawable.start();
    }
	/**
	 * Stop Spinner Animation.
	 */
    public void stopSpinner() {
        isLoaded = false;
        timeElapsed = 0;
        thread = new Thread(this);
        thread.start();
    }
	
    @Override
    protected void enableEditText() {
    	
    }

    @Override
    protected void disableEditText() {
    	
    }

    @Override
    public void resetFragment() {
		
    }

    @Override
    public void enableButtonListener() {
    	
    }

    @Override
    public void disableButtonListener() {
    	
    }

    @Override
    public void run() {
        try {
            while (!isLoaded && (timeElapsed < LOADING_TIME)) {
                Thread.sleep(100);
                if (!isLoaded) {
                    timeElapsed += 100;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            isLoaded = true;
            if (getActivity() != null && getView() != null) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ivMenu.setBackgroundResource(R.drawable.bt_menu);
                    }
                });
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (iView != null) {
            iView.closeMenu();
        }
        closeVirtualKeyboard();
        switch(v.getId()) {
            case R.id.iv_menu:
                if (iView.getCurTypeView().equals(TypeView.MANAGETRANSACTION)) {
                    iView.setView(TypeView.MANAGEACCOUNT);
                    iView.setTitle("Manage Account");
                } else if (iView.getCurTypeView().equals(TypeView.MANAGEACCOUNT)) {
                    iView.setView(TypeView.DASHBOARD);
                    iView.setTitle(DASHBOARD);
                } else if (iView.getCurTypeView().equals(TypeView.MANAGEREPORT)) {
                    iView.setView(TypeView.DASHBOARD);
                    iView.setTitle(DASHBOARD);
                } else if (iView.getCurTypeView().equals(TypeView.SHOWREPORT)) {
                    iView.setView(TypeView.MANAGEREPORT);
                    iView.setTitle("Manage Report");
                }
                break;
            case R.id.bt_logoff:
            	if (!iView.getCurTypeView().equals(TypeView.LOGIN) && !iView.getCurTypeView().equals(TypeView.REGISTER)) {
                    confirmLogOffDialog();
                }
            	break;
        }
    }
	
    /**
     * Pop-Up Log-Off Dialog.
     */
    private void confirmLogOffDialog() {
        AlertDialog.Builder dialoge = new AlertDialog.Builder(getActivity());
        dialoge.setTitle("Confirm to Log-Off?");
        dialoge.setPositiveButton("Log-Off", new android.content.DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                iView.setView(TypeView.LOGIN);
                iView.setTitle("Log-In");
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
