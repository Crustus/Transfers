package cz.crusty.transfers.ui.overview;

import java.util.List;

import cz.crusty.transfers.R;
import cz.crusty.transfers.data.model.transaction.Transaction;
import cz.crusty.transfers.data.model.transaction.TransactionFilter;
import cz.crusty.transfers.data.model.transaction.Type;
import cz.crusty.transfers.data.repository.transaction.TransactionsRepository;
import cz.crusty.transfers.data.repository.transaction.TransactionsSource;

/**
 * Created by Crusty. 03.09.2018
 */
public class OverviewPresenter implements OverviewContract.Presenter {

    private final OverviewContract.View mView;

    private final TransactionFilter mFilter = new TransactionFilter();

    private final TransactionsRepository mTransactions = TransactionsRepository.get();

    public OverviewPresenter(OverviewContract.View view) {
        mView = view;
    }

    @Override
    public void onFilterTextChanged(String query) {
        mFilter.setQuery(query);
        refreshDataAndShow();
    }

    @Override
    public void onFilterTypeChanged(int checkedId) {
        switch (checkedId) {
            case R.id.filter_all:
                mFilter.setType(Type.ALL);
                break;

            case R.id.filter_income:
                mFilter.setType(Type.INCOMING);
                break;

            case R.id.filter_outcome:
                mFilter.setType(Type.OUTGOING);
                break;
        }
        refreshDataAndShow();
    }

    @Override
    public void onStart() {
        refreshDataAndShow();
    }

    private void refreshDataAndShow() {
        mView.showError(false);
        mView.showContent(false);
        mView.showLoading(true);

        mTransactions.getAllTransactions(new TransactionsSource.LoadTransactionsCallback() {
            @Override
            public void onTransactionsLoaded(List<Transaction> transactions) {
                List<Transaction> filtered = mFilter.filter(transactions);
                mView.refreshData(filtered);

                mView.showError(false);
                mView.showLoading(false);
                mView.showContent(true);
            }

            @Override
            public void onDataNotAvailable() {
                mView.showContent(false);
                mView.showLoading(false);
                mView.showError(true);
            }
        });
    }
}
