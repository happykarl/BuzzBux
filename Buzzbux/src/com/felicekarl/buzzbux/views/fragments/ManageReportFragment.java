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
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.DatePicker.OnDateChangedListener;

public class ManageReportFragment extends BaseFragment implements OnClickListener, UpdateManageReportFragmentButtonListener {
	@SuppressWarnings("unused")
	private static final String TAG = ManageReportFragment.class.getSimpleName();
	
	private ListView lv_list_account;
	private TextView tv_date_from;
	private TextView tv_date_to;
	private View tv_date_from_wrapper;
	private View tv_date_to_wrapper;
	private DatePicker dp_date_from;
	private DatePicker dp_date_to;
	private Button bt_show_report;
	
	private View bgLayout1;
	private View bgLayout2;
	private View bgLayout3;
	
	private boolean isFromDatePickerOn = false;
	private boolean isToDatePickerOn = false;
	
	private Calendar date_from;
	private Calendar date_to;
	
	private List<Integer> selectedItemPosition;
	
	private ManageReportFragmentButtonListener mManageReportFragmentButtonListener;
	
	public ManageReportFragment() {
		super();
		selectedItemPosition = new ArrayList<Integer>();
	}
	
	public static ManageReportFragment create() {
		ManageReportFragment fragment = new ManageReportFragment();
		return fragment;
    }
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
    }
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = (ViewGroup) inflater.inflate(R.layout.fragment_manage_report, container, false);
		
		tv_date_from = (TextView) view.findViewById(R.id.tv_date_from);
		tv_date_from.setOnClickListener(this);
		tv_date_to = (TextView) view.findViewById(R.id.tv_date_to);
		tv_date_to.setOnClickListener(this);
		tv_date_from_wrapper = view.findViewById(R.id.tv_date_from_wrapper);
		tv_date_to_wrapper = view.findViewById(R.id.tv_date_to_wrapper);
		dp_date_from = (DatePicker) view.findViewById(R.id.dp_date_from);
		dp_date_to = (DatePicker) view.findViewById(R.id.dp_date_to);
		bt_show_report = (Button) view.findViewById(R.id.bt_show_report);
		bt_show_report.setOnClickListener(this);
		
		date_from = Calendar.getInstance();
		date_to = Calendar.getInstance();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		tv_date_from.setText("From - " + sdf.format(date_from.getTime()));
		tv_date_to.setText("To - " + sdf.format(date_to.getTime()));
		
		dp_date_from.init(date_from.get(Calendar.YEAR), 
				date_from.get(Calendar.MONTH), 
				date_from.get(Calendar.DAY_OF_MONTH), new OnDateChangedListener() {
					@Override
					public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
						date_from.set(year, monthOfYear, dayOfMonth);
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						tv_date_from.setText("From - " + sdf.format(date_from.getTime()));
		                //int dayOfMonth = cPickerDate.get(Calendar.DAY_OF_MONTH);
						
						
					}
    	});
		
		dp_date_to.init(date_to.get(Calendar.YEAR), 
				date_to.get(Calendar.MONTH), 
				date_to.get(Calendar.DAY_OF_MONTH), new OnDateChangedListener() {
					@Override
					public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
						date_to.set(year, monthOfYear, dayOfMonth);
		                //int dayOfMonth = cPickerDate.get(Calendar.DAY_OF_MONTH);
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						tv_date_to.setText("To - " + sdf.format(date_to.getTime()));
					}
    	});
		
		view_list.add(bt_show_report);
		view_list.add(tv_date_from);
		view_list.add(tv_date_to);
		
				
		lv_list_account = (ListView) view.findViewById(R.id.lv_list_account);
		
		lv_list_account.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (!selectedItemPosition.contains(Integer.valueOf(position))) {
					selectedItemPosition.add(Integer.valueOf(position));
					view.setBackgroundColor(Color.parseColor("#222222"));
					for(Integer i : selectedItemPosition) {
						Log.d(TAG, "position: " + i.toString());
					}
				} else {
					selectedItemPosition.remove(Integer.valueOf(position));
					view.setBackgroundColor(Color.parseColor("#eeeeee"));
					for(Integer i : selectedItemPosition) {
						Log.d(TAG, "position: " + i.toString());
					}
				}
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
	
	public void setBalance(final String balance) {
		if (getActivity() != null && getView() != null) {
    		getActivity().runOnUiThread(new Runnable(){
    			@Override
    			public void run() {
//    				tv_balance.setText(balance);
    			}
        	});
    	}
	}

	@Override
	public void onClick(View v) {
		if (iView != null)	iView.closeMenu();
		closeVirtualKeyboard();
		switch(v.getId()) {
		case R.id.tv_date_from:
			if (!isFromDatePickerOn) {
				ViewGroup.LayoutParams params = tv_date_from_wrapper.getLayoutParams();
				params.height = 300;
				tv_date_from_wrapper.requestLayout();
				params = dp_date_from.getLayoutParams();
				params.height = 300;
				dp_date_from.requestLayout();
				isFromDatePickerOn = true;
			} else {
				ViewGroup.LayoutParams params = tv_date_from_wrapper.getLayoutParams();
				params.height = 0;
				tv_date_from_wrapper.requestLayout();
				params = dp_date_from.getLayoutParams();
				params.height = 0;
				dp_date_from.requestLayout();
				isFromDatePickerOn = false;
			}
			if (isToDatePickerOn) {
				ViewGroup.LayoutParams params = tv_date_to_wrapper.getLayoutParams();
				params.height = 0;
				tv_date_to_wrapper.requestLayout();
				params = dp_date_to.getLayoutParams();
				params.height = 0;
				dp_date_to.requestLayout();
				isToDatePickerOn = false;
			}
			break;
		case R.id.tv_date_to:
			if (!isToDatePickerOn) {
				ViewGroup.LayoutParams params = tv_date_to_wrapper.getLayoutParams();
				params.height = 300;
				tv_date_to_wrapper.requestLayout();
				params = dp_date_to.getLayoutParams();
				params.height = 300;
				dp_date_to.requestLayout();
				isToDatePickerOn = true;
			} else {
				ViewGroup.LayoutParams params = tv_date_to_wrapper.getLayoutParams();
				params.height = 0;
				tv_date_to_wrapper.requestLayout();
				params = dp_date_to.getLayoutParams();
				params.height = 0;
				dp_date_to.requestLayout();
				isToDatePickerOn = false;
			}
			if (isFromDatePickerOn) {
				ViewGroup.LayoutParams params = tv_date_from_wrapper.getLayoutParams();
				params.height = 0;
				tv_date_from_wrapper.requestLayout();
				params = dp_date_from.getLayoutParams();
				params.height = 0;
				dp_date_from.requestLayout();
				isFromDatePickerOn = false;
			}
			break;
		case R.id.bt_show_report:
			if (mManageReportFragmentButtonListener != null) {
				if (selectedItemPosition.size() > 0) {
					disableButtonListener();
					Collections.sort(selectedItemPosition);
					mManageReportFragmentButtonListener.submit(selectedItemPosition, date_from, date_to);
				}
			}
		case R.id.lv_list_account:
		case R.id.bgLayout1:
		case R.id.bgLayout2:
		case R.id.bgLayout3:
			if (isFromDatePickerOn) {
				ViewGroup.LayoutParams params = tv_date_from_wrapper.getLayoutParams();
				params.height = 0;
				tv_date_from_wrapper.requestLayout();
				params = dp_date_from.getLayoutParams();
				params.height = 0;
				dp_date_from.requestLayout();
				isFromDatePickerOn = false;
			}
			if (isToDatePickerOn) {
				ViewGroup.LayoutParams params = tv_date_to_wrapper.getLayoutParams();
				params.height = 0;
				tv_date_to_wrapper.requestLayout();
				params = dp_date_to.getLayoutParams();
				params.height = 0;
				dp_date_to.requestLayout();
				isToDatePickerOn = false;
			}
			break;
		}
	}

	@Override
	public void resetFragment() {
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
		return lv_list_account;
	}

	@Override
	public void updateManageReportFragmentButtonListener(
			ManageReportFragmentButtonListener mManageReportFragmentButtonListener) {
		this.mManageReportFragmentButtonListener = mManageReportFragmentButtonListener;
	}

	public void resetSelection() {
		selectedItemPosition = new ArrayList<Integer>();
	}
}
