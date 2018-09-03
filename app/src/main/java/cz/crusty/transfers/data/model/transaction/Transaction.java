package cz.crusty.transfers.data.model.transaction;

/**
 * Created by Crusty. 28.08.2018
 */
public class Transaction {

    public final long mId;
    public final long mAmountInAccountCurrency;
    public final Type mDirection;

    private TransactionDetail mDetail = null;

    public Transaction(long id, long amountInAccountCurrency, Type direction) {
        mId = id;
        mAmountInAccountCurrency = amountInAccountCurrency;
        mDirection = direction;
    }

    public void setDetail(TransactionDetail detail) {
        mDetail = detail;
    }

    public TransactionDetail getDetail() {
        return mDetail;
    }
}
