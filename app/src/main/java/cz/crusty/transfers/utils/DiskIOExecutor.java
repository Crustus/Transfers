package cz.crusty.transfers.utils;

import android.support.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by Crusty. 03.09.2018
 */
public class DiskIOExecutor implements Executor {

    private final Executor mDiskIO;

    public DiskIOExecutor() {
        mDiskIO = Executors.newSingleThreadExecutor();
    }

    @Override
    public void execute(@NonNull Runnable command) {
        mDiskIO.execute(command);
    }
}
