package cz.crusty.transfers.data.model.transaction;

/**
 * Created by Crusty. 03.09.2018
 */
public class TransactionDetail {

    public final long mAccountNumber;
    public final String mAccountName;
    public final long mBankCode;

    public TransactionDetail(long accountNumber, String accountName, long bankCode) {
        mAccountNumber = accountNumber;
        mAccountName = accountName;
        mBankCode = bankCode;
    }
}
