package com.felicekarl.buzzbux.presenters.adapters;

import java.util.List;

import com.felicekarl.buzzbux.R;
import com.felicekarl.buzzbux.models.Account;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * ArrayAdapter for Account.
 * @author Karl
 *
 */
public class ArrayAdapterAccountItem extends ArrayAdapter<Account> {
	/**
	 * Android context.
	 */
    private Context mContext;
    /**
     * Layout Id.
     */
    private int layoutResourceId;
    /**
     * Data to be filled in ListView.
     */
    private List<Account> data;
    /**
     * Initiate Item with following info.
     * @param pContext Android context
     * @param pLayoutResourceId layout id
     * @param pData actual data
     */
    public ArrayAdapterAccountItem(Context pContext, int pLayoutResourceId, List<Account> pData) {
        super(pContext, pLayoutResourceId, pData);
        layoutResourceId = pLayoutResourceId;
        mContext = pContext;
        data = pData;
    }
    @Override
    public View getView(int position, View cView, ViewGroup parent) {
        View convertView = cView;
        if (convertView == null) {
            // inflate the layout
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layoutResourceId, parent, false);
        }
        // object item based on the position
        Account item = getItem(position);
        TextView tvAccountName = (TextView) convertView.findViewById(R.id.tv_account_name);
        TextView tvAccountBalance = (TextView) convertView.findViewById(R.id.tv_account_balance);
        TextView tvAccountDescription = (TextView) convertView.findViewById(R.id.tv_account_description);
        tvAccountName.setText(item.getName());
        tvAccountBalance.setText(item.getBalance().toString());
        tvAccountDescription.setText(item.getDescription());

        return convertView;
    }
    /**
	 * Return size of data.
	 * @return size of data
	 */
    public int getCount() {
        return data.size();
    }

}