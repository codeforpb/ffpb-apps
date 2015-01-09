package net.freifunk.paderborn.nodes.sync;

import android.app.*;
import android.content.*;
import android.os.*;

import org.androidannotations.annotations.*;

/**
 * Sync Service for Sync Adapter
 */
@EService
public class FfpbSyncService extends Service {
    private static final Object sSyncAdapterLock = new Object();
    private static FfpbSyncAdapter sAdapter;

    @Override
    public void onCreate() {
        synchronized (sSyncAdapterLock) {
            if (sAdapter == null) {
                sAdapter = new FfpbSyncAdapter(getApplicationContext(), true);
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return sAdapter.getSyncAdapterBinder();
    }
}
