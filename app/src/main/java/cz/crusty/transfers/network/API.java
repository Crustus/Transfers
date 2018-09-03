package cz.crusty.transfers.network;

/**
 * Created by Crusty. 28.08.2018
 */
public class API {

    private static final String BASE_URL = "http://demo0569565.mockable.io";

    public static String allTransactions() {
         return BASE_URL + "/transactions";
    }

    public static String transaction(long id) {
        return BASE_URL + "/transactions/"+id;
    }

}
