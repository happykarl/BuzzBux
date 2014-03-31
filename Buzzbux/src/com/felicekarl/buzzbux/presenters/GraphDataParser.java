package com.felicekarl.buzzbux.presenters;

import java.util.Iterator;
import java.util.List;

import com.felicekarl.buzzbux.models.TransType;
import com.felicekarl.buzzbux.models.Transaction;
import com.jjoe64.graphview.GraphViewDataInterface;
import com.jjoe64.graphview.GraphViewSeries;
/**
 * ServiceHolder To draw Graph.
 * @author Karl
 *
 */
public class GraphDataParser {
	/**
	 * Parse transaction date to make GraphViewSeries.
	 * @param list list of Transaction want to be parsed
	 * @return GraphViewSeries
	 */
    public GraphViewSeries parseTransactionData(List<Transaction> list) {
        int yValue = 0;
		
        Iterator<Transaction> iterator = list.iterator();
        Transaction t = iterator.next();	// first data
		
        if ( t.getType().equals(TransType.DEPOSIT) || t.getType().equals(TransType.REFUND) ) {
            yValue += t.getAmount().getValue();
		// substract
        } else if ( t.getType().equals(TransType.WITHDRAWAL) || t.getType().equals(TransType.DEBIT) || t.getType().equals(TransType.CREDIT) ||  t.getType().equals(TransType.VOID) ) {
            yValue += -1 * t.getAmount().getValue();
        }
        GraphViewSeries result = new GraphViewSeries(new GraphViewDataInterface[]{new GraphViewData(t.getDate().getTime(), yValue / 100)});
		
        while (iterator.hasNext()) {
            t = iterator.next();
            if ( t.getType().equals(TransType.DEPOSIT) || t.getType().equals(TransType.REFUND) ) {
                yValue += t.getAmount().getValue();
			// substract
            } else if ( t.getType().equals(TransType.WITHDRAWAL) || t.getType().equals(TransType.DEBIT) || t.getType().equals(TransType.CREDIT) || t.getType().equals(TransType.VOID) ) {
                yValue += -1 * t.getAmount().getValue();
            }
            result.appendData(new GraphViewData(t.getDate().getTime(), yValue / 100d), true, 100);
        }
        result.appendData(new GraphViewData(t.getDate().getTime(), yValue / 100d), true, 100);
        return result;
    }
	/**
	 * Basic class for holding Graph Data.
	 * @author Karl
	 *
	 */
    private class GraphViewData implements GraphViewDataInterface {
    	/**
    	 * X Axis.
    	 */
        private double x;
        /**
         * Y Axis.
         */
        private double y;
        /**
         * Initiate instance with following info.
         * @param pX X Axis.
         * @param pY Y Axis.
         */
        public GraphViewData(double pX, double pY) {
            x = pX;
            y = pY;
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


