package com.felicekarl.buzzbux.presenters.adapters;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import com.felicekarl.buzzbux.R;
import com.felicekarl.buzzbux.models.Account;
import com.felicekarl.buzzbux.presenters.GraphDataParser;
import com.jjoe64.graphview.CustomLabelFormatter;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * ArrayAdapter for List of Account with Graph.
 * @author Karl
 *
 */
public class ArrayAdapterAccountGraphItem extends ArrayAdapter<Account> {
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
    public ArrayAdapterAccountGraphItem(Context pContext, int pLayoutResourceId, List<Account> pData) {
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
        
        LinearLayout graphFrame = (LinearLayout) convertView.findViewById(R.id.ll_graph);
        TextView tvAccountName = (TextView) convertView.findViewById(R.id.tv_account_name);
        TextView tvAccountBalance = (TextView) convertView.findViewById(R.id.tv_account_balance);
        TextView tvAccountDescription = (TextView) convertView.findViewById(R.id.tv_account_description);
        
        tvAccountName.setText(item.getName());
        tvAccountBalance.setText(item.getBalance().toString());
        tvAccountDescription.setText(item.getDescription());
        
        // update Graph
        graphFrame.removeAllViews();
        if (item.getTransactions().size() > 0) {
            GraphDataParser gParser = new GraphDataParser();
            GraphViewSeries graphData = gParser.parseTransactionData(item.getTransactions());
            LineGraphView graphView = new LineGraphView(mContext, item.getName());
            graphView.getGraphViewStyle().setTextSize(10);
            graphView.getGraphViewStyle().setNumHorizontalLabels(5);
            graphView.setDrawDataPoints(true);
            graphView.setDataPointsRadius(5f);
            graphView.addSeries(graphData);
            graphFrame.addView(graphView);
			
            graphView.setCustomLabelFormatter(new CustomLabelFormatter() {
                @Override
                public String formatLabel(double value, boolean isValueX) {
                    if (isValueX) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTimeInMillis((long) value);
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        String date = sdf.format(calendar.getTime());
                        return date;
                    }
                    return null;
                }
            });
        }
        return convertView;
    }
	/**
	 * Return size of data.
	 */
    public int getCount() {
        return data.size();
    }
}