package cz.crusty.transfers.ui.detail;

import java.util.List;

import cz.crusty.transfers.R;
import cz.crusty.transfers.data.model.transaction.Transaction;
import cz.crusty.transfers.data.model.transaction.TransactionDetail;
import cz.crusty.transfers.data.model.transaction.TransactionFilter;
import cz.crusty.transfers.data.model.transaction.Type;
import cz.crusty.transfers.data.repository.transaction.TransactionsRepository;
import cz.crusty.transfers.data.repository.transaction.TransactionsSource;
import cz.crusty.transfers.ui.overview.OverviewContract;

/**
 * Created by Crusty. 03.09.2018
 */
public class DetailPresenter implements DetailContract.Presenter {

    private final DetailContract.View mView;

    private final TransactionsRepository mTransactions = TransactionsRepository.get();

    private final long mTransactionId;

    public DetailPresenter(DetailContract.View view, long transactionId) {
        mView = view;
        mTransactionId = transactionId;
    }

    @Override
    public void onStart() {
        refreshDataAndShow();
    }

    private void refreshDataAndShow() {

        mView.showError(false);
        mView.showContent(false);
        mView.showLoading(true);

        mTransactions.getTransactionWithDetail(mTransactionId, new TransactionsSource.GetTransactionWithDetailCallback() {
            @Override
            public void onTransactionLoaded(Transaction transaction) {
                mView.showLoading(false);
                mView.showError(false);
                mView.showContent(true);
                mView.refreshData(transaction);
            }

            @Override
            public void onDataNotAvailable() {
                mView.showLoading(false);
                mView.showContent(false);
                mView.showError(true);
            }
        });
    }
}
