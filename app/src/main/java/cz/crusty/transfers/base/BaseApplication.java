package cz.crusty.transfers.base;

import android.app.Application;

import cz.crusty.transfers.network.Network;
import cz.crusty.transfers.utils.AppExecutors;

/**
 * Created by Crusty. 03.09.2018
 */
public class BaseApplication extends Application {

    private static final String TAG = BaseApplication.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();

        Network.newInstance(this);

        AppExecutors.newInstance();

    }
}
