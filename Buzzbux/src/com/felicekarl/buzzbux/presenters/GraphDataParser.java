package com.felicekarl.buzzbux.presenters;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import android.util.Log;

import com.felicekarl.buzzbux.models.TransType;
import com.felicekarl.buzzbux.models.Transaction;
import com.jjoe64.graphview.GraphViewDataInterface;
import com.jjoe64.graphview.GraphViewSeries;

// init example series data
//GraphViewSeries exampleSeries = new GraphViewSeries(new GraphViewData[] {
//      new GraphViewData(1, 2.0d)
//      , new GraphViewData(2, 1.5d)
//      , new GraphViewData(3, 2.5d)
//      , new GraphViewData(4, 1.0d)
//});

public class GraphDataParser {
	public GraphViewSeries parseTransactionData(List<Transaction> list) {
		int yValue = 0;
		
		Iterator<Transaction> iterator = list.iterator();
		Transaction t = iterator.next();	// first data
		
		if ( t.getType().equals(TransType.DEPOSIT) || t.getType().equals(TransType.REFUND) ) {
			yValue += t.getAmount().getValue();
		// substract
		} else if ( t.getType().equals(TransType.WITHDRAWAL) || 
				t.getType().equals(TransType.DEBIT) ||
				t.getType().equals(TransType.CREDIT) || 
				t.getType().equals(TransType.VOID) ) {
			yValue += -1 * t.getAmount().getValue();
		}
		GraphViewSeries result = new GraphViewSeries(new GraphViewDataInterface[]{
				new GraphViewData(t.getDate().getTime(), yValue/100)});
		
		while (iterator.hasNext()) {
			t = iterator.next();
			if ( t.getType().equals(TransType.DEPOSIT) || t.getType().equals(TransType.REFUND) ) {
				yValue += t.getAmount().getValue();
			// substract
			} else if ( t.getType().equals(TransType.WITHDRAWAL) || 
					t.getType().equals(TransType.DEBIT) ||
					t.getType().equals(TransType.CREDIT) || 
					t.getType().equals(TransType.VOID) ) {
				yValue += -1 * t.getAmount().getValue();
			}
			Log.d("Karl", "yValue/100d: " + yValue/100d);
			result.appendData(new GraphViewData(t.getDate().getTime(), yValue/100d), true, 100);
		}
		result.appendData(new GraphViewData(t.getDate().getTime(), yValue/100d), true, 100);
		return result;
	}
	
	private class GraphViewData implements GraphViewDataInterface {
		private double x;
		private double y;
		public GraphViewData(double x, double y) {
			this.x = x;
			this.y = y;
		}
		@Override
		public double getX() {
			return x;
		}

		@Override
		public double getY() {
			return y;
		}
		
	}
}


