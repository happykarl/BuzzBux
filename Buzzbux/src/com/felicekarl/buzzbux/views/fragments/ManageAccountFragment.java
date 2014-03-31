package com.felicekarl.buzzbux.views.fragments;

import com.felicekarl.buzzbux.R;
import com.felicekarl.buzzbux.listeners.ManageAccountFragmentButtonListener;
import com.felicekarl.buzzbux.listeners.UpdateManageAccountFragmentButtonListener;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
/**
 * ManageAccountFragment.
 * @author Karl
 *
 */
public class ManageAccountFragment extends AbstractBaseFragment implements OnClickListener, UpdateManageAccountFragmentButtonListener {
	/**
	 * Account ListView.
	 */
    private ListView lvListAccount;
	/**
	 * ManageAccountFragmentButtonListener.
	 */
    private ManageAccountFragmentButtonListener mManageAccountFragmentButtonListener;
    /**
	 * Constructor.
	 */
    public ManageAccountFragment() {
        super();
    }
    /**
	 * Create ManageAccountFragment instance.
	 * @return ManageAccountFragment
	 */
    public static ManageAccountFragment create() {
        ManageAccountFragment fragment = new ManageAccountFragment();
        return fragment;
    }
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = (ViewGroup) inflater.inflate(R.layout.fragment_manage_account, container, false);
		
        lvListAccount = (ListView) view.findViewById(R.id.lv_list_account);
		
        lvListAccount.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mManageAccountFragmentButtonListener != null) {
                    mManageAccountFragmentButtonListener.selectAccount(position);
                }
            }
        });
		
    	slideUpFragment();
    	
        return view;
    }

    @Override
    public void onClick(View v) {
    	
    }

    @Override
    public void resetFragment() {
        enableButtonListener();
    }

    @Override
    public void enableButtonListener() {
    	
    }

    @Override
    public void disableButtonListener() {
    	
    }
	
    /**
     * Get ListView of Account.
     * @return ListView of Account
     */
    public ListView getLvListAccount() {
        return lvListAccount;
    }

    @Override
    public void updateManageAccountFragmentButtonListener(
			ManageAccountFragmentButtonListener pManageAccountFragmentButtonListener) {
        mManageAccountFragmentButtonListener = pManageAccountFragmentButtonListener;
    }
}
