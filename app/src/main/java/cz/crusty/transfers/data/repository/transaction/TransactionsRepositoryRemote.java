package cz.crusty.transfers.data.repository.transaction;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.crusty.transfers.BuildConfig;
import cz.crusty.transfers.data.model.transaction.Transaction;
import cz.crusty.transfers.data.model.transaction.TransactionDetail;
import cz.crusty.transfers.data.model.transaction.Type;
import cz.crusty.transfers.network.API;
import cz.crusty.transfers.network.Network;

/**
 * Created by Crusty. 03.09.2018
 */
class TransactionsRepositoryRemote implements TransactionsSource {

    private static final String TAG = TransactionsRepositoryRemote.class.getSimpleName();

    @Override
    public void getAllTransactions(final LoadTransactionsCallback callback) {
        Network.get().addToRequestQueue(
                new JsonObjectRequest(Request.Method.GET, API.allTransactions(), null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (BuildConfig.DEBUG)
                            Log.d(TAG, response.toString());

                        ArrayList<Transaction> transactions = new ArrayList<>();

                        try {
                            JSONArray array = response.getJSONArray("items");
                            for (int x = 0; x < array.length(); x++) {
                                JSONObject jsonItem = array.getJSONObject(x);
                                long id = jsonItem.getLong("id");
                                long amountInAccountCurrency = jsonItem.getLong("amountInAccountCurrency");
                                String direction = jsonItem.getString("direction");
                                Type type = Type.ALL;

                                if(direction.compareToIgnoreCase("incoming") == 0)
                                    type = Type.INCOMING;
                                if(direction.compareToIgnoreCase("outgoing") == 0)
                                    type = Type.OUTGOING;

                                Transaction item = new Transaction(id, amountInAccountCurrency, type);

                                transactions.add(item);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            // TODO error log
                        }

                        callback.onTransactionsLoaded(transactions);

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onDataNotAvailable();
                    }
                }));
    }

    @Override
    public void saveTransactions(List<Transaction> transactions) {

    }

    @Override
    public void getTransactionWithDetail(long id, GetTransactionWithDetailCallback callback) {

    }

    @Override
    public void getTransactionDetail(long id, final GetTransactionDetailCallback callback) {

        Network.get().addToRequestQueue(new JsonObjectRequest(Request.Method.GET, API.transaction(id), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (BuildConfig.DEBUG)
                            Log.d(TAG, response.toString());

                        try {
                            JSONObject account = response.getJSONObject("contraAccount");

                            long accountNumber = account.getLong("accountNumber");
                            String accountName = account.getString("accountName");
                            long bankCode = account.getLong("bankCode");

                            TransactionDetail detail = new TransactionDetail(accountNumber, accountName, bankCode);
                            callback.onDetailLoaded(detail);
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                            // TODO error log
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onDataNotAvailable();
                    }
        }));

    }

    @Override
    public Transaction saveTransactionDetail(long id, TransactionDetail detail) {

        return null;
    }
}
