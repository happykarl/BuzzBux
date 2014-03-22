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

public class FooterFragment extends BaseFragment implements OnClickListener,
		UpdateFooterFragmentButtonListener {
	private static final String TAG = FooterFragment.class.getSimpleName();
	
	protected static int ANIM_SLIDE_DURATION = 300;
	private boolean isOpen = false;
	private Button bt_footer_popup;
	private Button bt_footer_add;
	private Button bt_footer_edit;
	private Button bt_footer_delete;
	
	private FooterFragmentButtonListener mFooterFragmentButtonListener;
	
	public FooterFragment() {
		
	}
	
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
		
		bt_footer_popup = (Button) view.findViewById(R.id.bt_footer_popup);
		bt_footer_popup.setOnClickListener(this);
		bt_footer_add = (Button) view.findViewById(R.id.bt_footer_add);
		bt_footer_add.setOnClickListener(this);
		bt_footer_edit = (Button) view.findViewById(R.id.bt_footer_edit);
		bt_footer_edit.setOnClickListener(this);
		bt_footer_delete = (Button) view.findViewById(R.id.bt_footer_delete);
		bt_footer_delete.setOnClickListener(this);
		
		bt_footer_delete.setVisibility(View.INVISIBLE);
		
		view_list.add(bt_footer_popup);
		view_list.add(bt_footer_add);
		
		slideUpFragment();
    	
		return view;
	}
	
	@Override
	public void toggle(boolean isOnStage, boolean isAnimation, DIRECTION direction) {
		this.isOnStage = isOnStage;
		if (isOnStage) {
			if(isAnimation) {
				if (direction.equals(DIRECTION.TOP)) {
					view.setTranslationY(-height);
					view.animate().translationY(0).setDuration(ANIM_SLIDE_DURATION).withLayer();
				} else if (direction.equals(DIRECTION.BOTTOM)) {
					if (getActivity() != null && getView() != null) {
			    		getActivity().runOnUiThread(new Runnable(){
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
	
	private void toogleMenu() {
		if (isOnStage) {
			if (!isOpen) {
				if (getActivity() != null && getView() != null) {
		    		getActivity().runOnUiThread(new Runnable(){
		    			@Override
		    			public void run() {
							view.animate().translationY(0).setDuration(ANIM_SLIDE_DURATION).withLayer();
							isOpen = true;
		    			}
		    		});
				}
			} else {
				if (getActivity() != null && getView() != null) {
		    		getActivity().runOnUiThread(new Runnable(){
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
	
	public void openMenu() {
		if (!isOpen) {
			if (getActivity() != null && getView() != null) {
	    		getActivity().runOnUiThread(new Runnable(){
	    			@Override
	    			public void run() {
						view.animate().translationY(0).setDuration(ANIM_SLIDE_DURATION).withLayer();
						isOpen = true;
	    			}
	    		});
			}
		}
	}
	
	public void closeMenu() {
		if (isOpen) {
			if (getActivity() != null && getView() != null) {
	    		getActivity().runOnUiThread(new Runnable(){
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disableButtonListener() {
		// TODO Auto-generated method stub
		
	}

	

}
