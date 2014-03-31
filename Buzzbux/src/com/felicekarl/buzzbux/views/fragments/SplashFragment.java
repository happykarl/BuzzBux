package com.felicekarl.buzzbux.views.fragments;

import com.felicekarl.buzzbux.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
/**
 * SplashFragment.
 * @author Karl
 *
 */
public class SplashFragment extends AbstractBaseFragment {
	/**
   	 * Create SplashFragment instance.
   	 * @return SplashFragment
   	 */
    public static SplashFragment create() {
        SplashFragment fragment = new SplashFragment();
        return fragment;
    }
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	view = (ViewGroup) inflater.inflate(R.layout.fragment_splash, container, false);
    	
    	slideUpFragment();
    	toggle(true, false, DIRECTION.TOP);
    	
    	return view;
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
}
