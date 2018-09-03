package cz.crusty.transfers.utils;

/**
 * Created by Crusty. 03.09.2018
 */

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 *
 */
public class AppExecutors {

    private static final int THREAD_COUNT = 3; // TODO calc by current cores count

    private static volatile AppExecutors mInstance;

    private final Executor mDiskIO;

    private final Executor mNetworkIO;

    private final Executor mMainThread;

    public static synchronized void newInstance() {
        if(mInstance == null) {
            mInstance = new AppExecutors();
        }
    }

    private AppExecutors() {
        mDiskIO = new DiskIOExecutor();
        mNetworkIO = Executors.newFixedThreadPool(THREAD_COUNT);
        mMainThread = new MainThreadExecutor();
    }

    public static Executor diskIO() {
        return mInstance.mDiskIO;
    }

    public static Executor networkIO() {
        return mInstance.mNetworkIO;
    }

    public static Executor mainThread() {
        return mInstance.mMainThread;
    }

    private static class MainThreadExecutor implements Executor {
        private Handler mainThreadHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable command) {
            mainThreadHandler.post(command);
        }
    }
}