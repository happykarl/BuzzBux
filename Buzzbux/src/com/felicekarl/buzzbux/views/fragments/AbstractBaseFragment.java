package com.felicekarl.buzzbux.views.fragments;

import java.util.ArrayList;
import java.util.List;

import com.felicekarl.buzzbux.views.IView;

import android.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
/**
 * Base Class for All Fragment.
 * @author Karl
 *
 */
public abstract class AbstractBaseFragment extends Fragment {
	/**
	 * View of MVP Model.
	 */
    protected IView iView;
    /**
     * ViewGroup.
     */
    protected ViewGroup view;
    /**
     * Width of Fragment.
     */
    protected int width;
    /**
     * Height of Fragment.
     */
    protected int height;
    /**
     * Slide Animation Time.
     */
    protected static final int ANIM_SLIDE_DURATION = 500;
    /**
     * Fragment Stage Status.
     */
    protected boolean isOnStage;
    /**
     * List of View.
     */
    protected List<View> viewList;
    /**
     * List of EditText View.
     */
    protected List<EditText> etList;
	/**
	 * Screen Animation Direction.
	 * @author Karl
	 *
	 */
    public enum DIRECTION {
    	/**
    	 * TOP.
    	 */
        TOP,
        /**
    	 * BOTTOM.
    	 */
        BOTTOM,
        /**
    	 * LEFT.
    	 */
        LEFT,
        /**
    	 * RIGHT.
    	 */
        RIGHT
    }
	/**
	 * Initialize viewList and etList.
	 */
    public AbstractBaseFragment() {
        viewList = new ArrayList<View>();
        etList = new ArrayList<EditText>();
    }
	/**
	 * Save View of MVP Model.
	 * @param pView View of MVP Model.
	 */
    public void setIView(IView pView) {
        iView = pView;
    }
	/**
	 * Slide Up Fragment not to shown on the Window.
	 */
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
	/**
	 * Toggle Fragment.
	 * @param pIsOnStage true if fragment want to be shown on the window.
	 * @param isAnimation true if want to animation
	 * @param direction direction of animation (not usable when isAnimation is false)
	 */
    public void toggle(boolean pIsOnStage, boolean isAnimation, DIRECTION direction) {
    	isOnStage = pIsOnStage;
        if (isOnStage) {
            if (isAnimation) {
                if (direction.equals(DIRECTION.TOP)) {
                    view.setTranslationX(0);
                    view.setTranslationY(-height);
                    view.animate().translationY(0).setDuration(ANIM_SLIDE_DURATION).withLayer();
                } else if (direction.equals(DIRECTION.BOTTOM)) {
                    if (getActivity() != null && getView() != null) {
                        getActivity().runOnUiThread(new Runnable() {
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
            if (isAnimation) {
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
	/**
	 * Close Virtual Keyboard.
	 */
    protected void closeVirtualKeyboard() {
		// close virtual keyboard
        InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE); 
        if ( inputManager != null && getActivity().getCurrentFocus() != null ) {
            inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    	}
    }
	/**
	 * Enable EditText Focus.
	 */
    protected void enableEditText() {
        if (etList != null) {
            if (getActivity() != null && getView() != null) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        for (EditText et : etList) {
                            et.setFocusable(true);
                            et.setFocusableInTouchMode(true);
                        }
                    }
                });
            }
        }
    }
	/**
	 * Disable EditText Focus.
	 */
    protected void disableEditText() {
        if (etList != null) {
            if (getActivity() != null && getView() != null) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        for (EditText et : etList) {
                            et.setFocusable(false);
                            et.setFocusableInTouchMode(false);
                        }
                    }
                });
            }
        }
    }
	/**
	 * Reset Fragment.
	 */
    public abstract void resetFragment();
	/**
	 * Enable Button Listener.
	 */
    public abstract void enableButtonListener();
	/**
	 * Disable Button Listener.
	 */
    public abstract void disableButtonListener();
}
