package cz.crusty.transfers.data.repository.transaction;

import java.util.List;

import cz.crusty.transfers.data.model.transaction.Transaction;
import cz.crusty.transfers.data.model.transaction.TransactionDetail;

/**
 * Created by Crusty. 02.09.2018
 */
public interface TransactionsSource {

    interface LoadTransactionsCallback {

        void onTransactionsLoaded(List<Transaction> transactions);

        void onDataNotAvailable();
    }

    interface GetTransactionWithDetailCallback {

        void onTransactionLoaded(Transaction transaction);

        void onDataNotAvailable();
    }

    interface GetTransactionDetailCallback {

        void onDetailLoaded(TransactionDetail detail);

        void onDataNotAvailable();
    }

    void getAllTransactions(LoadTransactionsCallback callback);

    void saveTransactions(List<Transaction> transactions);

    void getTransactionWithDetail(long id, GetTransactionWithDetailCallback callback);

    void getTransactionDetail(long id, GetTransactionDetailCallback callback);

    Transaction saveTransactionDetail(long id, TransactionDetail detail);

}
