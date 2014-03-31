package com.felicekarl.buzzbux.views.fragments;

import com.felicekarl.buzzbux.R;
import com.felicekarl.buzzbux.listeners.ShowReportFragmentButtonListener;
import com.felicekarl.buzzbux.listeners.UpdateShowReportFragmentButtonListener;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
/**
 * ShowReportFragment.
 * @author Karl
 *
 */
public class ShowReportFragment extends AbstractBaseFragment implements OnClickListener, UpdateShowReportFragmentButtonListener {
	/**
	 * DropBox Menu for Report Type.
	 */
    private Spinner spReportType;
	/**
	 * Amount TextView.
	 */
    private TextView tvAmount;
    /**
     * List of Transaction.
     */
    private ListView lvListTransaction;
    /**
     * Graph View.
     */
    private View llGraph;
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
	 * ShowReportFragmentButtonListener.
	 */
    private ShowReportFragmentButtonListener mShowReportFragmentButtonListener;
	/**
	 * Report Type.
	 * @author Karl
	 *
	 */
    public enum TypeReport {
    	/**
    	 * ALL.
    	 */
        ALL,
        /**
    	 * INCOME.
    	 */
        INCOME,
        /**
    	 * EXPENSE.
    	 */
        EXPENSE
    }
    /**
     * Constructor.
     */
    public ShowReportFragment() {
    	super();
    }
    /**
   	 * Create ShowReportFragment instance.
   	 * @return ShowReportFragment
   	 */
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
	
        spReportType = (Spinner) view.findViewById(R.id.sp_report_type);
        tvAmount = (TextView) view.findViewById(R.id.tv_amount);
        lvListTransaction = (ListView) view.findViewById(R.id.lv_list_transaction);
        llGraph = view.findViewById(R.id.ll_graph);
	
        spReportType.setOnItemSelectedListener(new OnItemSelectedListener() {
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
	
    /**
     * Set Amount.
     * @param amount amount
     */
    public void setAmount(final String amount) {
        if (getActivity() != null && getView() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tvAmount.setText(amount);
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
    }

    @Override
    public void resetFragment() {
        spReportType.setSelection(0);
        enableButtonListener();
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
     * Get ListView of Transaction.
     * @return ListView of Transaction
     */
    public ListView getListView() {
        return lvListTransaction;
    }
	
    /**
     * Get GraphView.
     * @return GraphView
     */
    public View getGraphVirew() {
        return llGraph;
    }

    @Override
    public void updateShowReportFragmentButtonListener(ShowReportFragmentButtonListener pShowReportFragmentButtonListener) {
        mShowReportFragmentButtonListener = pShowReportFragmentButtonListener;
    }
    
    /**
     * Reset Selection.
     */
    public void resetSelection() {
		// TODO Auto-generated method stub
    }
}
