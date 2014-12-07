package net.freifunk.paderborn.krombel.sync;

import android.accounts.*;
import android.content.*;
import android.os.*;

import com.j256.ormlite.android.apptools.*;

import net.freifunk.paderborn.krombel.apis.krombel_dash.*;

/**
 * SyncAdapter for Krombel Stats
 */
public class KrombelSyncAdapter extends AbstractThreadedSyncAdapter {
    KrombelDownloader mDownloader;

    public KrombelSyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        init(context);
    }

    public KrombelSyncAdapter(Context context, boolean autoInitialize, boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
        init(context);
    }

    private void init(Context context) {
        mDownloader = KrombelDownloader_.getInstance_(context);
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
        KrombelStat[] stats = mDownloader.getAllStats();
        persist(stats);
    }

    private void persist(KrombelStat[] stats) {
        // FIXME implement
    }
}
