package cz.crusty.transfers.ui.detail;

import java.util.List;

import cz.crusty.transfers.base.BasePresenter;
import cz.crusty.transfers.base.BaseView;
import cz.crusty.transfers.data.model.transaction.Transaction;
import cz.crusty.transfers.data.model.transaction.TransactionDetail;

/**
 * Created by Crusty. 03.09.2018
 */
public class DetailContract {

    public interface Presenter extends BasePresenter{

    }

    public interface View extends BaseView {

        void refreshData(Transaction transaction);

    }

}
