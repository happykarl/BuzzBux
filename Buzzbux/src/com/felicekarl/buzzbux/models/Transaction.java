package com.felicekarl.buzzbux.models;

import java.util.Date;
/**
 * Transaction.
 * @author Karl
 *
 */
public class Transaction implements Comparable {
	/**
	 * Index of Transaction (unique).
	 */
    private String index;
    /**
     * Type of Transaction.
     */
    private TransType type;
    /**
     * Description of Transaction.
     */
    private String description;
    /**
     * Amount of Money related to this Transaction.
     */
    private Money amount;
    /**
     * Date of Transaction.
     */
    private Date date;
	/**
	 * Initiate instace with following info.
	 * @param pIndex Index of Transaction (unique)
	 * @param pType Type of Transaction
	 * @param pDescription Description of Transaction
	 * @param pAmount Amount of Money related to this Transaction
	 * @param pDate Date of Transaction
	 */
    public Transaction(String pIndex, TransType pType, String pDescription, Money pAmount, Date pDate) {
        index = pIndex;
        type = pType;
        description = pDescription;
        amount = pAmount;
        date = pDate;
    }
	/**
	 * Get Index of Transaction (unique).
	 * @return Index of Transaction (unique)
	 */
    public String getId() {
        return index;
    }
	/**
	 * Set Type of Transaction.
	 * @param pType Type of Transaction
	 */
    public void setType(TransType pType) {
        type = pType;
    }
	/**
	 * Set Description of Transaction.
	 * @param pDescription Description of Transaction
	 */
    public void setDescription(String pDescription) {
        description = pDescription;
    }
	/**
	 * Set Amount of Money related to this Transaction.
	 * @param pAmount Amount of Money related to this Transaction
	 */
    public void setAmount(Money pAmount) {
        amount = pAmount;
    }
	/**
	 * Get Type of Transaction.
	 * @return Type of Transaction
	 */
    public TransType getType() {
        return this.type;
    }
	/**
	 * Get Description of Transaction.
	 * @return Description of Transaction
	 */
    public String getDescription() {
        return this.description;
    }
	/**
	 * Get Amount of Money related to this Transaction.
	 * @return Amount of Money related to this Transaction
	 */
    public Money getAmount() {
        return amount;
    }
	/**
	 * Get Signed Value of Money.
	 * @return + if Transaction is related to adding money on the Account, vise versa
	 */
    public int getSignedValue() {
        if ( type.equals(TransType.DEPOSIT) || type.equals(TransType.REFUND) ) {
            return	amount.getValue();
        } else if ( type.equals(TransType.WITHDRAWAL) || type.equals(TransType.DEBIT) || type.equals(TransType.CREDIT) || type.equals(TransType.VOID) ) {
            return	-1 * amount.getValue();
        }
        return 0;
    }
	/**
	 * Get Signed String Value of Money with Currency Info .
	 * @return + if Transaction is related to adding money on the Account, vise versa
	 */
    public String getSignedAmount() {
        if ( type.equals(TransType.DEPOSIT) || type.equals(TransType.REFUND) ) {
            return "+" + amount.toString();
        } else if ( type.equals(TransType.WITHDRAWAL) || type.equals(TransType.DEBIT) || type.equals(TransType.CREDIT) || type.equals(TransType.VOID) ) {
            return "-" + amount.toString();
        }
        return null;
    }
	/**
	 * Set Date of Transaction.
	 * @param pDate Date of Transaction
	 */
    public void setDate(Date pDate) {
        date = pDate;
    }
	/**
	 * Get Date of Transaction.
	 * @return Date of Transaction
	 */
    public Date getDate() {
        return this.date;
    }
    @Override
    public int compareTo(Object obj) {
        Transaction o = (Transaction) obj;
        if (date.compareTo(o.date) > 0) {
            return	1;
        } else if (date.compareTo(o.date) < 0) {
            return -1;
        } else {
            if (index.compareTo(o.index) > 0) {
                return	1;
            } else if (index.compareTo(o.index) < 0) {
            	return	-1;
            }
        }
        return 0;
    }
    
}
