package com.felicekarl.buzzbux.views.fragments;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import com.felicekarl.buzzbux.R;
import com.felicekarl.buzzbux.listeners.ManageReportFragmentButtonListener;
import com.felicekarl.buzzbux.listeners.UpdateManageReportFragmentButtonListener;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.DatePicker.OnDateChangedListener;
/**
 * ManageReportFragment.
 * @author Karl
 *
 */
@SuppressLint("SimpleDateFormat") public class ManageReportFragment extends AbstractBaseFragment implements OnClickListener, UpdateManageReportFragmentButtonListener {
    /**
     * String "yyyy-MM-dd".
     */
    private static final String DATEFORMAT = "yyyy-MM-dd";
	/**
     * String "From -".
     */
    private static final String FROM = "▼ From -";
    /**
     * String "To -".
     */
    private static final String TO = "▼ To -";
	/**
	 * ListView of Account.
	 */
    private ListView lvListAccount;
    /**
     * DateFrom TextView.
     */
    private TextView tvDateFrom;
    /**
     * DateTo TextView.
     */
    private TextView tvDateTo;
    /**
     * Wrapper of DateFrom.
     */
    private View tvDateFromWrapper;
    /**
     * Wrapper of DateTo.
     */
    private View tvDateToWrapper;
    /**
     * DatePicker.
     */
    private DatePicker dpDateFrom;
    /**
     * DatePicker.
     */
    private DatePicker dpDateTo;
    /**
     * Show Report Button.
     */
    private Button btShowReport;
    /**
     * Background Layout.
     */
    private View bgLayout1;
    /**
     * Background Layout.
     */
    private View bgLayout2;
    /**
     * Background Layout.
     */
    private View bgLayout3;
	/**
	 * Status of DateFromPicker.
	 */
    private boolean isFromDatePickerOn = false;
    /**
	 * Status of DateToPicker.
	 */
    private boolean isToDatePickerOn = false;
	/**
	 * Dateholder.
	 */
    private Calendar dateFrom;
    /**
	 * Dateholder.
	 */
    private Calendar dateTo;
	/**
	 * Selected Account List.
	 */
    private List<Integer> selectedItemPosition;
	/**
	 * ManageReportFragmentButtonListener.
	 */
    private ManageReportFragmentButtonListener mManageReportFragmentButtonListener;
    /**
     * Error TextView.
     */
    private TextView tvError;
    /**
	 * Constructor.
	 */
    public ManageReportFragment() {
        super();
        selectedItemPosition = new ArrayList<Integer>();
    }
	/**
	 * Create ManageReportFragment instance.
	 * @return ManageReportFragment
	 */
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
		
        tvDateFrom = (TextView) view.findViewById(R.id.tv_date_from);
        tvDateFrom.setOnClickListener(this);
        tvDateTo = (TextView) view.findViewById(R.id.tv_date_to);
        tvDateTo.setOnClickListener(this);
        tvDateFromWrapper = view.findViewById(R.id.tv_date_from_wrapper);
        tvDateToWrapper = view.findViewById(R.id.tv_date_to_wrapper);
        dpDateFrom = (DatePicker) view.findViewById(R.id.dp_date_from);
        dpDateTo = (DatePicker) view.findViewById(R.id.dp_date_to);
        btShowReport = (Button) view.findViewById(R.id.bt_show_report);
        btShowReport.setOnClickListener(this);
        tvError = (TextView) view.findViewById(R.id.tv_error);
        dateFrom = Calendar.getInstance();
        dateTo = Calendar.getInstance();
		
        SimpleDateFormat sdf = new SimpleDateFormat(DATEFORMAT);
        tvDateFrom.setText(FROM + sdf.format(dateFrom.getTime()));
        tvDateTo.setText(TO + sdf.format(dateTo.getTime()));
		
        dpDateFrom.init(dateFrom.get(Calendar.YEAR), dateFrom.get(Calendar.MONTH), dateFrom.get(Calendar.DAY_OF_MONTH), new OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                dateFrom.set(year, monthOfYear, dayOfMonth);
                SimpleDateFormat sdf = new SimpleDateFormat(DATEFORMAT);
                tvDateFrom.setText(FROM + sdf.format(dateFrom.getTime()));
            }
        });
		
        dpDateTo.init(dateTo.get(Calendar.YEAR), dateTo.get(Calendar.MONTH), dateTo.get(Calendar.DAY_OF_MONTH), new OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                dateTo.set(year, monthOfYear, dayOfMonth);
                SimpleDateFormat sdf = new SimpleDateFormat(DATEFORMAT);
                tvDateTo.setText(TO + sdf.format(dateTo.getTime()));
            }
    	});
		
        viewList.add(btShowReport);
        viewList.add(tvDateFrom);
        viewList.add(tvDateTo);
		
				
        lvListAccount = (ListView) view.findViewById(R.id.lv_list_account);
		
        lvListAccount.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (selectedItemPosition.contains(Integer.valueOf(position))) {
                    selectedItemPosition.remove(Integer.valueOf(position));
                    view.setBackgroundColor(Color.parseColor("#eeeeee"));
                } else {
                    selectedItemPosition.add(Integer.valueOf(position));
                    view.setBackgroundColor(Color.parseColor("#222222"));
					
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
    
    /**
     * Set Error Message.
     * @param msg error message
     */
    public void setErrorMsg(final String msg) {
        if (getActivity() != null && getView() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tvError.setText(msg);
                }
            });
    	}
    }

    @Override
    public void onClick(View v) {
        if (iView != null) {
            iView.closeMenu();
        }
        closeVirtualKeyboard();
        switch(v.getId()) {
            case R.id.tv_date_from:
                if (isFromDatePickerOn) {
                    ViewGroup.LayoutParams params = tvDateFromWrapper.getLayoutParams();
                    params.height = 0;
                    tvDateFromWrapper.requestLayout();
                    params = dpDateFrom.getLayoutParams();
                    params.height = 0;
                    dpDateFrom.requestLayout();
                    isFromDatePickerOn = false;
                } else {
                    ViewGroup.LayoutParams params = tvDateFromWrapper.getLayoutParams();
                    params.height = 300;
                    tvDateFromWrapper.requestLayout();
                    params = dpDateFrom.getLayoutParams();
                    params.height = 300;
                    dpDateFrom.requestLayout();
                    isFromDatePickerOn = true;
                }
                if (isToDatePickerOn) {
                    ViewGroup.LayoutParams params = tvDateToWrapper.getLayoutParams();
                    params.height = 0;
                    tvDateToWrapper.requestLayout();
                    params = dpDateTo.getLayoutParams();
                    params.height = 0;
                    dpDateTo.requestLayout();
                    isToDatePickerOn = false;
                }
                break;
            case R.id.tv_date_to:
                if (isToDatePickerOn) {
                    ViewGroup.LayoutParams params = tvDateToWrapper.getLayoutParams();
                    params.height = 0;
                    tvDateToWrapper.requestLayout();
                    params = dpDateTo.getLayoutParams();
                    params.height = 0;
                    dpDateTo.requestLayout();
                    isToDatePickerOn = false;
                } else {
                    ViewGroup.LayoutParams params = tvDateToWrapper.getLayoutParams();
                    params.height = 300;
                    tvDateToWrapper.requestLayout();
                    params = dpDateTo.getLayoutParams();
                    params.height = 300;
                    dpDateTo.requestLayout();
                    isToDatePickerOn = true;
                }
                if (isFromDatePickerOn) {
                    ViewGroup.LayoutParams params = tvDateFromWrapper.getLayoutParams();
                    params.height = 0;
                    tvDateFromWrapper.requestLayout();
                    params = dpDateFrom.getLayoutParams();
                    params.height = 0;
                    dpDateFrom.requestLayout();
                    isFromDatePickerOn = false;
                }
                break;
            case R.id.bt_show_report:
                if (mManageReportFragmentButtonListener != null) {
                	setErrorMsg("");
                    if (selectedItemPosition.size() > 0) {
                        disableButtonListener();
                        Collections.sort(selectedItemPosition);
                        mManageReportFragmentButtonListener.submit(selectedItemPosition, dateFrom, dateTo);
                    } else {
                    	setErrorMsg("There is no trasaction to show on report.");
                    }
                }
                resetDatePicker();
                break;
            case R.id.lv_list_account:
                resetDatePicker();
                break;
            case R.id.bgLayout1:
                resetDatePicker();
                break;
            case R.id.bgLayout2:
                resetDatePicker();
                break;
            case R.id.bgLayout3:
            	resetDatePicker();
                break;
        }
    }
	
    /**
     * Hide DatePicker.
     */
    private void resetDatePicker() {
        if (isFromDatePickerOn) {
            ViewGroup.LayoutParams params = tvDateFromWrapper.getLayoutParams();
            params.height = 0;
            tvDateFromWrapper.requestLayout();
            params = dpDateFrom.getLayoutParams();
            params.height = 0;
            dpDateFrom.requestLayout();
            isFromDatePickerOn = false;
        }
        if (isToDatePickerOn) {
            ViewGroup.LayoutParams params = tvDateToWrapper.getLayoutParams();
            params.height = 0;
            tvDateToWrapper.requestLayout();
            params = dpDateTo.getLayoutParams();
            params.height = 0;
            dpDateTo.requestLayout();
            isToDatePickerOn = false;
        }
    }

    @Override
    public void resetFragment() {
        enableButtonListener();
        if (getActivity() != null && getView() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tvError.setText("");
                }
            });
    	}
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
     * Get ListView of Account.
     * @return lvListAccount
     */
    public ListView getLvListAccount() {
        return lvListAccount;
    }

    @Override
    public void updateManageReportFragmentButtonListener(
			ManageReportFragmentButtonListener pManageReportFragmentButtonListener) {
        mManageReportFragmentButtonListener = pManageReportFragmentButtonListener;
    }

    /**
     * Reset All selection of Accounts.
     */
    public void resetSelection() {
        selectedItemPosition = new ArrayList<Integer>();
    }
}
