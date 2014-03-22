package com.felicekarl.buzzbux.presenters.adapters;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.felicekarl.buzzbux.R;
import com.felicekarl.buzzbux.models.Account;
import com.felicekarl.buzzbux.models.TransType;
import com.felicekarl.buzzbux.models.Transaction;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

// here's our beautiful adapter
public class ArrayAdapterTransactionItem extends ArrayAdapter<Transaction> {
	private static final String TAG = ArrayAdapterTransactionItem.class.getSimpleName();

    private Context mContext;
    private int layoutResourceId;
    private List<Transaction> data;

    public ArrayAdapterTransactionItem(Context mContext, int layoutResourceId, List<Transaction> data) {

        super(mContext, layoutResourceId, data);

        this.layoutResourceId = layoutResourceId;
        this.mContext = mContext;
        this.data = data;
    }

	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
		Log.d("Karl", "position: " + position);
        /*
         * The convertView argument is essentially a "ScrapView" as described is Lucas post 
         * http://lucasr.org/2012/04/05/performance-tips-for-androids-listview/
         * It will have a non-null value when ListView is asking you recycle the row layout. 
         * So, when convertView is not null, you should simply update its contents instead of inflating a new row layout.
         */
        if(convertView==null){
            // inflate the layout
        	LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            convertView = inflater.inflate(layoutResourceId, parent, false);
        }

        // object item based on the position
        Transaction item = getItem(position);

        // get the TextView and then set the text (item name) and tag (item ID) values
        TextView tv_trnas_type = (TextView) convertView.findViewById(R.id.tv_trnas_type);
        tv_trnas_type.setText(item.getType().toString());
        TextView tv_amount = (TextView) convertView.findViewById(R.id.tv_amount);
        tv_amount.setText(item.getSignedAmount());
        TextView tv_description = (TextView) convertView.findViewById(R.id.tv_description);
        tv_description.setText(item.getDescription());
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        TextView tv_date = (TextView) convertView.findViewById(R.id.tv_date);
        tv_date.setText(sdf.format(item.getDate()));

        return convertView;
    }
	
	public int getCount() {
		return data.size();
	}

}