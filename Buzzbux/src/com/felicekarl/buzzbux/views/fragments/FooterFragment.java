package com.felicekarl.buzzbux.views.fragments;

import com.felicekarl.buzzbux.R;
import com.felicekarl.buzzbux.listeners.FooterFragmentButtonListener;
import com.felicekarl.buzzbux.listeners.UpdateFooterFragmentButtonListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
/**
 * FooterFragment.
 * @author Karl
 *
 */
public class FooterFragment extends AbstractBaseFragment implements OnClickListener, UpdateFooterFragmentButtonListener {
	/**
	 * Animation Duration.
	 */
    protected static final int ANIM_SLIDE_DURATION = 300;
    /**
     * Status of Footer.
     */
    private boolean isOpen = false;
    /**
     * Pop-Up Button.
     */
    private Button btFooterPopup;
    /**
     * Add Button.
     */
    private Button btFooterAdd;
    /**
     * Edit Button.
     */
    private Button btFooterEdit;
    /**
     * Delete Button.
     */
    private Button btFooterDelete;
	/**
	 * FooterFragmentButtonListener.
	 */
    private FooterFragmentButtonListener mFooterFragmentButtonListener;
	/**
	 * Constructor.
	 */
    public FooterFragment() {
		
    }
    /**
	 * Create FooterFragment instance.
	 * @return FooterFragment
	 */
    public static FooterFragment create() {
        FooterFragment fragment = new FooterFragment();
        return fragment;
    }
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = (ViewGroup) inflater.inflate(R.layout.fragment_footer, container, false);
		
        btFooterPopup = (Button) view.findViewById(R.id.bt_footer_popup);
        btFooterPopup.setOnClickListener(this);
        btFooterAdd = (Button) view.findViewById(R.id.bt_footer_add);
        btFooterAdd.setOnClickListener(this);
        btFooterEdit = (Button) view.findViewById(R.id.bt_footer_edit);
        btFooterEdit.setOnClickListener(this);
        btFooterDelete = (Button) view.findViewById(R.id.bt_footer_delete);
        btFooterDelete.setOnClickListener(this);
		
        btFooterDelete.setVisibility(View.INVISIBLE);
		
        viewList.add(btFooterPopup);
        viewList.add(btFooterAdd);
		
        slideUpFragment();
    	
        return view;
    }
	
    @Override
    public void toggle(boolean isOnStage, boolean isAnimation, DIRECTION direction) {
        this.isOnStage = isOnStage;
        if (isOnStage) {
            if (isAnimation) {
                if (direction.equals(DIRECTION.TOP)) {
                    view.setTranslationY(-height);
                    view.animate().translationY(0).setDuration(ANIM_SLIDE_DURATION).withLayer();
                } else if (direction.equals(DIRECTION.BOTTOM)) {
                    if (getActivity() != null && getView() != null) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                view.setTranslationY(height);
                                view.animate().translationY(70).setDuration(ANIM_SLIDE_DURATION).withLayer();
                            }
                        });
                    }
                } else if (direction.equals(DIRECTION.LEFT)) {
                    view.setTranslationX(-width);
                    view.animate().translationX(0).setDuration(ANIM_SLIDE_DURATION).withLayer();
                } else if (direction.equals(DIRECTION.RIGHT)) {
                    view.setTranslationX(width);
                    view.animate().translationX(0).setDuration(ANIM_SLIDE_DURATION).withLayer();
                }
            } else {
                view.animate().translationX(0).setDuration(0).withLayer();
                view.animate().translationY(70).setDuration(ANIM_SLIDE_DURATION).withLayer();
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
    public void onClick(View v) {
        closeVirtualKeyboard();
        switch(v.getId()) {
            case R.id.bt_footer_popup:
                toogleMenu();
                break;
            case R.id.bt_footer_add:
                if (mFooterFragmentButtonListener != null) {
                    mFooterFragmentButtonListener.addItem();
                }
                closeMenu();
                break;
            case R.id.bt_footer_edit:
                if (mFooterFragmentButtonListener != null) {
                    mFooterFragmentButtonListener.editItem();
                }
                closeMenu();
        }
    }
	
    /**
     * Toggle Footer Menu.
     */
    private void toogleMenu() {
        if (isOnStage) {
            if (!isOpen) {
                if (getActivity() != null && getView() != null) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            view.animate().translationY(0).setDuration(ANIM_SLIDE_DURATION).withLayer();
                            isOpen = true;
                        }
                    });
                }
            } else {
                if (getActivity() != null && getView() != null) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            view.animate().translationY(70).setDuration(ANIM_SLIDE_DURATION).withLayer();
                            isOpen = false;
                        }
                    });
                }
            }
        }
    }
	
    /**
     * Open Menu.
     */
    public void openMenu() {
        if (!isOpen) {
            if (getActivity() != null && getView() != null) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        view.animate().translationY(0).setDuration(ANIM_SLIDE_DURATION).withLayer();
                        isOpen = true;
    	            }
                });
            }
        }
    }
	
    /**
     * Close Menu.
     */
    public void closeMenu() {
        if (isOpen) {
            if (getActivity() != null && getView() != null) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        view.animate().translationY(70).setDuration(ANIM_SLIDE_DURATION).withLayer();
                        isOpen = false;
                    }
                });
            }
        }
    }

    @Override
    public void updateFooterFragmentButtonListener(
			FooterFragmentButtonListener mFooterFragmentButtonListener) {
        this.mFooterFragmentButtonListener = mFooterFragmentButtonListener;
    }

    @Override
    public void enableButtonListener() {
    	
    }

    @Override
    public void disableButtonListener() {
    	
    }
}
