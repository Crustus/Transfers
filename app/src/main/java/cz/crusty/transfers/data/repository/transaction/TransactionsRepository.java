package cz.crusty.transfers.data.repository.transaction;

import java.util.List;

import cz.crusty.transfers.data.model.transaction.Transaction;
import cz.crusty.transfers.data.model.transaction.TransactionDetail;

/**
 * Created by Crusty. 02.09.2018
 */
public class TransactionsRepository implements TransactionsSource {

    private static volatile TransactionsRepository mInstance;

    private final TransactionsSource mRepositoryLocal;
    private final TransactionsSource mRepositoryRemote;

    private TransactionsRepository(TransactionsSource local, TransactionsSource remote) {
        mRepositoryLocal = local;
        mRepositoryRemote = remote;
    }

    public static TransactionsRepository get() {
        if (mInstance == null) {
            newInstance();
        }
        return mInstance;
    }

    private static synchronized void newInstance() {
        if (mInstance == null) {
            mInstance = new TransactionsRepository(new TransactionsRepositoryLocal(), new TransactionsRepositoryRemote());
        }
    }

    @Override
    public void getAllTransactions(final LoadTransactionsCallback callback) {

        mRepositoryLocal.getAllTransactions(new LoadTransactionsCallback() {
            @Override
            public void onTransactionsLoaded(List<Transaction> transactions) {
                callback.onTransactionsLoaded(transactions);
            }

            @Override
            public void onDataNotAvailable() {
                mRepositoryRemote.getAllTransactions(new LoadTransactionsCallback() {
                    @Override
                    public void onTransactionsLoaded(List<Transaction> transactions) {
                        mRepositoryLocal.saveTransactions(transactions);
                        callback.onTransactionsLoaded(transactions);
                    }

                    @Override
                    public void onDataNotAvailable() {
                        callback.onDataNotAvailable();
                    }
                });
            }
        });

    }

    @Override
    public void saveTransactions(List<Transaction> transactions) {

    }

    @Override
    public void getTransactionWithDetail(final long id, final GetTransactionWithDetailCallback callback) {
        mRepositoryLocal.getTransactionWithDetail(id, new GetTransactionWithDetailCallback() {
            @Override
            public void onTransactionLoaded(Transaction transaction) {
                callback.onTransactionLoaded(transaction);
            }

            @Override
            public void onDataNotAvailable() {
                mRepositoryRemote.getTransactionDetail(id, new GetTransactionDetailCallback() {
                    @Override
                    public void onDetailLoaded(TransactionDetail detail) {
                        Transaction transaction = mRepositoryLocal.saveTransactionDetail(id, detail);
                        callback.onTransactionLoaded(transaction);
                    }

                    @Override
                    public void onDataNotAvailable() {
                        callback.onDataNotAvailable();
                    }
                });
            }
        });
    }

    @Override
    public void getTransactionDetail(final long id, final GetTransactionDetailCallback callback) {

        mRepositoryLocal.getTransactionDetail(id, new GetTransactionDetailCallback() {
            @Override
            public void onDetailLoaded(TransactionDetail detail) {
                callback.onDetailLoaded(detail);
            }

            @Override
            public void onDataNotAvailable() {
                mRepositoryRemote.getTransactionDetail(id, new GetTransactionDetailCallback() {
                    @Override
                    public void onDetailLoaded(TransactionDetail detail) {
                        mRepositoryLocal.saveTransactionDetail(id, detail);
                        callback.onDetailLoaded(detail);
                    }

                    @Override
                    public void onDataNotAvailable() {
                        callback.onDataNotAvailable();
                    }
                });
            }
        });

    }

    @Override
    public Transaction saveTransactionDetail(long id, TransactionDetail detail) {

        return null;
    }
}
