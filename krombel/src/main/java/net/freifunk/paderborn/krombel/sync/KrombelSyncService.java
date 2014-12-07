package net.freifunk.paderborn.krombel.sync;

import android.app.*;
import android.content.*;
import android.os.*;

import org.androidannotations.annotations.*;

/**
 * KrombelSyncService
 * todo further doc?
 * FIXME add krombel account
 */
@EService
public class KrombelSyncService extends Service{
    private static final Object sSyncAdapterLock = new Object();
    private static KrombelSyncAdapter sAdapter;

    @Override
    public void onCreate() {
        synchronized (sSyncAdapterLock) {
            if (sAdapter == null) {
                sAdapter = new KrombelSyncAdapter(getApplicationContext(), true);
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return sAdapter.getSyncAdapterBinder();
    }
}
