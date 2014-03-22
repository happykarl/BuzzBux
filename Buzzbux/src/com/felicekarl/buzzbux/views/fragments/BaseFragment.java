package com.felicekarl.buzzbux.views.fragments;

import java.util.ArrayList;
import java.util.List;

import com.felicekarl.buzzbux.views.IView;

import android.app.Fragment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public abstract class BaseFragment extends Fragment {
	@SuppressWarnings("unused")
	private static final String TAG = BaseFragment.class.getSimpleName();
	
	protected IView iView;
	protected ViewGroup view;
	protected int width;
	protected int height;
	protected static int ANIM_SLIDE_DURATION = 500;
	protected boolean isOnStage;
	protected List<View> view_list;
	protected List<EditText> et_list;
	
	public enum DIRECTION {
		TOP, BOTTOM, LEFT, RIGHT
	}
	
	public BaseFragment() {
		view_list = new ArrayList<View>();
		et_list = new ArrayList<EditText>();
	}
	
	public void setIView(IView iView) {
		this.iView = iView;
	}
	
	protected void slideUpFragment() {
		ViewTreeObserver vto = view.getViewTreeObserver();
    	vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				width = view.getWidth();
				height = view.getHeight();
				ViewTreeObserver obs = view.getViewTreeObserver();
				obs.removeOnGlobalLayoutListener(this);
				view.setTranslationY(-height);
			}
		});
	}
	
	public void toggle(boolean isOnStage, boolean isAnimation, DIRECTION direction) {
		if (isOnStage) {
			if(isAnimation) {
				if (direction.equals(DIRECTION.TOP)) {
					view.setTranslationX(0);
					view.setTranslationY(-height);
					view.animate().translationY(0).setDuration(ANIM_SLIDE_DURATION).withLayer();
				} else if (direction.equals(DIRECTION.BOTTOM)) {
					if (getActivity() != null && getView() != null) {
			    		getActivity().runOnUiThread(new Runnable(){
			    			@Override
			    			public void run() {
			    				view.setTranslationX(0);
			    				view.setTranslationY(height);
								view.animate().translationY(0).setDuration(ANIM_SLIDE_DURATION).withLayer();
			    			}
			    		});
					}
				} else if (direction.equals(DIRECTION.LEFT)) {
					view.setTranslationX(-width);
					view.setTranslationY(0);
					view.animate().translationX(0).setDuration(ANIM_SLIDE_DURATION).withLayer();
				} else if (direction.equals(DIRECTION.RIGHT)) {
					view.setTranslationX(width);
					view.setTranslationY(0);
					view.animate().translationX(0).setDuration(ANIM_SLIDE_DURATION).withLayer();
				}
			} else {
				view.animate().translationX(0).setDuration(0).withLayer();
				view.animate().translationY(0).setDuration(0).withLayer();
			}
    		enableEditText();
    	} else {
    		if(isAnimation) {
    			if (direction.equals(DIRECTION.TOP)) {
					view.animate().translationY(-height).setDuration(ANIM_SLIDE_DURATION).withLayer();
				} else if (direction.equals(DIRECTION.BOTTOM)) {
					view.animate().translationY(height).setDuration(ANIM_SLIDE_DURATION).withLayer();
				} else if (direction.equals(DIRECTION.LEFT)) {
					view.animate().translationX(-width).setDuration(ANIM_SLIDE_DURATION).withLayer();
				} else if (direction.equals(DIRECTION.RIGHT)) {
					view.animate().translationX(width).setDuration(ANIM_SLIDE_DURATION).withLayer();
				}
    		} else {
    			if (direction.equals(DIRECTION.TOP)) {
    				view.setTranslationX(0);
    				view.animate().translationY(-height).setDuration(0).withLayer();
				} else if (direction.equals(DIRECTION.BOTTOM)) {
					view.setTranslationX(0);
    				view.animate().translationY(height).setDuration(0).withLayer();
				} else if (direction.equals(DIRECTION.LEFT)) {
					view.animate().translationX(-width).setDuration(0).withLayer();
					view.setTranslationY(0);
				} else if (direction.equals(DIRECTION.RIGHT)) {
					view.animate().translationX(width).setDuration(0).withLayer();
					view.setTranslationY(0);
				}
    		}
    		disableEditText();
    	}
    }
	
	protected void closeVirtualKeyboard() {
		// close virtual keyboard
		InputMethodManager inputManager = (InputMethodManager) getActivity()
				.getSystemService(getActivity().INPUT_METHOD_SERVICE); 
		if ( inputManager != null && getActivity().getCurrentFocus() != null ) {
			inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus()
					.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}
	
	protected void enableEditText() {
		if (et_list != null) {
			if (getActivity() != null && getView() != null) {
	    		getActivity().runOnUiThread(new Runnable(){
	    			@Override
	    			public void run() {
	    				for (EditText et : et_list) {
	    					et.setFocusable(true);
		    				et.setFocusableInTouchMode(true);
	    				}
	    			}
	        	});
	    	}
		}
	}
	
	protected void disableEditText() {
		if (et_list != null) {
			if (getActivity() != null && getView() != null) {
	    		getActivity().runOnUiThread(new Runnable(){
	    			@Override
	    			public void run() {
	    				for (EditText et : et_list) {
	    					et.setFocusable(false);
		    				et.setFocusableInTouchMode(false);
	    				}
	    			}
	        	});
	    	}
		}
	}
	
	public abstract void resetFragment();
	
	public abstract void enableButtonListener();
	
	public abstract void disableButtonListener();
}
