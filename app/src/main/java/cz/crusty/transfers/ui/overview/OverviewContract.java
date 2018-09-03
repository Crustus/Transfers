package cz.crusty.transfers.ui.overview;

import java.util.List;

import cz.crusty.transfers.base.BasePresenter;
import cz.crusty.transfers.base.BaseView;
import cz.crusty.transfers.data.model.transaction.Transaction;

/**
 * Created by Crusty. 03.09.2018
 */
public class OverviewContract {

    public interface Presenter extends BasePresenter {

        void onFilterTextChanged(String query);

        void onFilterTypeChanged(int checkedId);

    }

    public interface View extends BaseView {

        void refreshData(List<Transaction> data);

    }

}
