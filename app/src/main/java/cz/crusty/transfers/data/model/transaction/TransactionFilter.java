package cz.crusty.transfers.data.model.transaction;

import java.util.ArrayList;
import java.util.List; /**
 * Created by Crusty. 03.09.2018
 */
public class TransactionFilter  {

    private String mQuery = null;
    private Type mType = Type.ALL;

    public void setQuery(String query) {
        mQuery = query;
    }

    public void setType(Type type) {
        mType = type;
    }

    public List<Transaction> filter(List<Transaction> transactions) {
        if(mType == Type.ALL && (mQuery == null || mQuery.length() == 0))
            return transactions;

        boolean filterByAmount = true;
        long amount = 0;
        try {
            amount = Long.parseLong(mQuery);
        } catch (NumberFormatException ex) {
            filterByAmount = false;
        }

        ArrayList<Transaction> filtered = new ArrayList<>();

        for (Transaction t : transactions) {
            if(mType != Type.ALL && t.mDirection != mType)
                continue;
            if(filterByAmount) {
                if(t.mAmountInAccountCurrency != amount)
                    continue;
            }

            filtered.add(t);
        }

        return filtered;
    }
}
