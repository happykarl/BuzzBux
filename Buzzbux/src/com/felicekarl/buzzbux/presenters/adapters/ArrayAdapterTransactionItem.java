package com.felicekarl.buzzbux.presenters.adapters;

import java.text.SimpleDateFormat;
import java.util.List;
import com.felicekarl.buzzbux.R;
import com.felicekarl.buzzbux.models.Transaction;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * ArrayAdapter for List of Transaction.
 * @author Karl
 *
 */
@SuppressLint("SimpleDateFormat") public class ArrayAdapterTransactionItem extends ArrayAdapter<Transaction> {
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
    private List<Transaction> data;
    /**
     * Initiate Item with following info.
     * @param pContext Android context
     * @param pLayoutResourceId layout id
     * @param pData actual data
     */
    public ArrayAdapterTransactionItem(Context pContext, int pLayoutResourceId, List<Transaction> pData) {
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
        Transaction item = getItem(position);
        // get the TextView and then set the text (item name) and tag (item ID) values
        TextView tvTransType = (TextView) convertView.findViewById(R.id.tv_trnas_type);
        tvTransType.setText(item.getType().toString());
        TextView tvAmount = (TextView) convertView.findViewById(R.id.tv_amount);
        tvAmount.setText(item.getSignedAmount());
        TextView tvDescription = (TextView) convertView.findViewById(R.id.tv_description);
        tvDescription.setText(item.getDescription());
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        TextView tvDate = (TextView) convertView.findViewById(R.id.tv_date);
        tvDate.setText(sdf.format(item.getDate()));

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