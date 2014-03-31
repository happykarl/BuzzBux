package com.felicekarl.buzzbux.models;
/**
 * Transaction Type.
 * @author Karl
 *
 */
public enum TransType {
	/**
	 * DEPOSIT.
	 */
	DEPOSIT, 
	/**
	 * WITHDRAWAL.
	 */
	WITHDRAWAL,
	/**
	 * DEBIT.
	 */
	DEBIT,
	/**
	 * CREDIT.
	 */
	CREDIT,
	/**
	 * REFUND.
	 */
	REFUND,
	/**
	 * VOID (Not Implemented Yet).
	 */
	VOID
}
