package com.felicekarl.buzzbux.views.fragments;

import com.felicekarl.buzzbux.R;
import com.felicekarl.buzzbux.views.TypeView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ActionBarFragment extends BaseFragment implements Runnable, OnClickListener {
	private static final String TAG = ActionBarFragment.class.getSimpleName();
	
	private AnimationDrawable mAnimationDrawable; 
	
	private TextView tv_title;
	private ImageView iv_menu;
	private Button bt_logoff;
	
	private static final int LOADING_TIME = 1000;
	private int timeElapsed = 0;
	private boolean isLoaded = false;
	private Thread thread;
	
	public ActionBarFragment() {
		super();
	}
	
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
		
		tv_title = (TextView) view.findViewById(R.id.tv_title);
		iv_menu = (ImageView) view.findViewById(R.id.iv_menu);
        
		iv_menu.setBackgroundResource(R.drawable.bt_menu);
		iv_menu.setOnClickListener(this);
		
		bt_logoff = (Button) view.findViewById(R.id.bt_logoff);
		bt_logoff.setOnClickListener(this);
		
		slideUpFragment();
    	
		return view;
	}
	
	public void setTitle(final String str) {
		if (getActivity() != null && getView() != null) {
    		getActivity().runOnUiThread(new Runnable(){
    			@Override
    			public void run() {
    				tv_title.setText(str);
    			}
        	});
    	}
	}
	
	public void startSpinner() {
		iv_menu.setBackgroundResource(R.drawable.pb_menu);
		mAnimationDrawable = (AnimationDrawable) iv_menu.getBackground();
		mAnimationDrawable.start();
	}
	
	public void stopSpinner() {
		isLoaded = false;
		timeElapsed = 0;
		thread = new Thread(this);
        thread.start();
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
	public void resetFragment() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enableButtonListener() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disableButtonListener() {
		// TODO Auto-generated method stub
		
	}
	
	private void animateSpinner() {
		
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
				getActivity().runOnUiThread(new Runnable(){
					@Override
					public void run() {
						iv_menu.setBackgroundResource(R.drawable.bt_menu);
					}
		    	});
			}
		}
	}

	@Override
	public void onClick(View v) {
		if (iView != null)	iView.closeMenu();
		closeVirtualKeyboard();
		switch(v.getId()) {
		case R.id.iv_menu:
			if (iView.getView().equals(TypeView.MANAGETRANSACTION)) {
				iView.setView(TypeView.MANAGEACCOUNT);
				iView.setTitle("Manage Account");
			} else if (iView.getView().equals(TypeView.MANAGEACCOUNT)) {
				iView.setView(TypeView.DASHBOARD);
				iView.setTitle("Dashboard");
			} else if (iView.getView().equals(TypeView.MANAGEREPORT)) {
				iView.setView(TypeView.DASHBOARD);
				iView.setTitle("Dashboard");
			} else if (iView.getView().equals(TypeView.SHOWREPORT)) {
				iView.setView(TypeView.MANAGEREPORT);
				iView.setTitle("Manage Report");
			}
			break;
		case R.id.bt_logoff:
			if (!iView.getView().equals(TypeView.LOGIN) && !iView.getView().equals(TypeView.REGISTER)) {
				confirmLogOffDialog();
			}
			break;
		}
	}
	
	private void confirmLogOffDialog(){
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
