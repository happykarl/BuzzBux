package com.felicekarl.buzzbux.views.fragments;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import com.felicekarl.buzzbux.R;
import com.felicekarl.buzzbux.listeners.*;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.TextureView;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.DatePicker.OnDateChangedListener;

public class ShowReportFragment extends BaseFragment implements OnClickListener, UpdateShowReportFragmentButtonListener {
	@SuppressWarnings("unused")
	private static final String TAG = ShowReportFragment.class.getSimpleName();
	
	private Spinner sp_report_type;
	private TextView tv_amount;
	private ListView lv_list_transaction;
	private View ll_graph;
	
	private View bgLayout1;
	private View bgLayout2;
	private View bgLayout3;
	
	private ShowReportFragmentButtonListener mShowReportFragmentButtonListener;
	
	public enum TypeReport {
		ALL, INCOME, EXPENSE
	}
	
	public ShowReportFragment() {
		super();
	}
	
	public static ShowReportFragment create() {
		ShowReportFragment fragment = new ShowReportFragment();
		return fragment;
    }
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
    }
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = (ViewGroup) inflater.inflate(R.layout.fragment_show_report, container, false);
		
		sp_report_type = (Spinner) view.findViewById(R.id.sp_report_type);
		tv_amount = (TextView) view.findViewById(R.id.tv_amount);
		lv_list_transaction = (ListView) view.findViewById(R.id.lv_list_transaction);
		ll_graph = view.findViewById(R.id.ll_graph);
		
		sp_report_type.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parentView, View selectedItemView, 
					int position, long id) {
				if (mShowReportFragmentButtonListener != null) {
					if (((TextView) selectedItemView).getText().toString().equals(TypeReport.ALL.toString())) {
						mShowReportFragmentButtonListener.setReportType(TypeReport.ALL);
					} else if (((TextView) selectedItemView).getText().toString().equals(TypeReport.INCOME.toString())) {
						mShowReportFragmentButtonListener.setReportType(TypeReport.INCOME);
					} else if (((TextView) selectedItemView).getText().toString().equals(TypeReport.EXPENSE.toString())) {
						mShowReportFragmentButtonListener.setReportType(TypeReport.EXPENSE);
					}
				}
			}
			@Override
			public void onNothingSelected(AdapterView<?> parentView) {
				// TODO Auto-generated method stub
			}
		});
		
		// add bg listener
    	bgLayout1 = view.findViewById(R.id.bgLayout1);
    	bgLayout1.setOnClickListener(this);
    	bgLayout2 = view.findViewById(R.id.bgLayout2);
    	bgLayout2.setOnClickListener(this);
    	bgLayout3 = view.findViewById(R.id.bgLayout3);
    	bgLayout3.setOnClickListener(this);
		
    	slideUpFragment();
    	
		return view;
	}
	
	public void setAmount(final String amount) {
		if (getActivity() != null && getView() != null) {
    		getActivity().runOnUiThread(new Runnable(){
    			@Override
    			public void run() {
    				tv_amount.setText(amount);
    			}
        	});
    	}
	}

	@Override
	public void onClick(View v) {
		if (iView != null)	iView.closeMenu();
		closeVirtualKeyboard();
		switch(v.getId()) {
		
		}
	}

	@Override
	public void resetFragment() {
		sp_report_type.setSelection(0);
		//enable button listener
		enableButtonListener();
	}

	@Override
	public void enableButtonListener() {
		if (view_list != null) {
			for (View v : view_list) {
				v.setOnClickListener(this);
			}
		}
	}

	@Override
	public void disableButtonListener() {
		if (view_list != null) {
			for (View v : view_list) {
				v.setOnClickListener(null);
			}
		}
	}
	
	public ListView getListView() {
		return lv_list_transaction;
	}
	
	public View getGraphVirew() {
		return ll_graph;
	}

	@Override
	public void updateShowReportFragmentButtonListener(
			ShowReportFragmentButtonListener mShowReportFragmentButtonListener) {
		this.mShowReportFragmentButtonListener = mShowReportFragmentButtonListener;
	}
}
