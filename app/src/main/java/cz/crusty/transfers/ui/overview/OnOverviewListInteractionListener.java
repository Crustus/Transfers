package cz.crusty.transfers.ui.overview;

import cz.crusty.transfers.data.model.transaction.Transaction;

/**
 * Created by Crusty. 03.09.2018
 */
public interface OnOverviewListInteractionListener {
    void onListFragmentInteraction(Transaction item);
}
