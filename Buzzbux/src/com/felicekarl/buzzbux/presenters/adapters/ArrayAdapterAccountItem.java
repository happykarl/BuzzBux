package com.felicekarl.buzzbux.presenters.adapters;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import com.felicekarl.buzzbux.R;
import com.felicekarl.buzzbux.models.Account;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

// here's our beautiful adapter
public class ArrayAdapterAccountItem extends ArrayAdapter<Account> {
	private static final String TAG = ArrayAdapterAccountItem.class.getSimpleName();

    private Context mContext;
    private int layoutResourceId;
    private List<Account> data;

    public ArrayAdapterAccountItem(Context mContext, int layoutResourceId, List<Account> data) {

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
        Account item = getItem(position);
        Log.d(TAG, "item.getName(): " + item.getName() + "item.getBalance(): " + item.getBalance());
        
        TextView tv_account_name = (TextView) convertView.findViewById(R.id.tv_account_name);
        TextView tv_account_balance = (TextView) convertView.findViewById(R.id.tv_account_balance);
        TextView tv_account_description = (TextView) convertView.findViewById(R.id.tv_account_description);
        
        tv_account_name.setText(item.getName());
        tv_account_balance.setText(item.getBalance().toString());
        tv_account_description.setText(item.getDescription());

        return convertView;

    }
	
	public int getCount() {
		return data.size();
	}

}