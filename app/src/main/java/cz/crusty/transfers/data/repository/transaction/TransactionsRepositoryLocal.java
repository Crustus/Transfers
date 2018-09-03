package cz.crusty.transfers.data.repository.transaction;

import java.util.ArrayList;
import java.util.List;

import cz.crusty.transfers.data.model.transaction.Transaction;
import cz.crusty.transfers.data.model.transaction.TransactionDetail;

/**
 * Created by Crusty. 03.09.2018
 */
class TransactionsRepositoryLocal implements TransactionsSource {

    /** in-memory representation only now */
    private final ArrayList<Transaction> mTransactions = new ArrayList<>();

    @Override
    public void getAllTransactions(LoadTransactionsCallback callback) {
        if(mTransactions.size() > 0)
            callback.onTransactionsLoaded(mTransactions);
        else
            callback.onDataNotAvailable();
    }

    @Override
    public void saveTransactions(List<Transaction> transactions) {
        mTransactions.clear();
        mTransactions.addAll(transactions);
    }

    @Override
    public void getTransactionWithDetail(long id, GetTransactionWithDetailCallback callback) {
        Transaction transaction = null;
        for(Transaction t : mTransactions) {
            if(t.mId == id && t.getDetail() != null) {
                transaction = t;
                break;
            }
        }

        if(transaction != null)
            callback.onTransactionLoaded(transaction);
        else
            callback.onDataNotAvailable();
    }

    @Override
    public void getTransactionDetail(long id, GetTransactionDetailCallback callback) {
        // TODO hashed ID, HashMap, ...
        TransactionDetail detail = null;
        for(Transaction t : mTransactions) {
            if(t.mId == id) {
                detail = t.getDetail();
                break;
            }
        }

        if(detail != null)
            callback.onDetailLoaded(detail);
        else
            callback.onDataNotAvailable();
    }

    @Override
    public Transaction saveTransactionDetail(long id, TransactionDetail detail) {
        for(Transaction t : mTransactions) {
            if(t.mId == id) {
                t.setDetail(detail);
                return t;
            }
        }
        return null;
    }

}
