package net.freifunk.paderborn.nodes.sync;

import android.accounts.*;
import android.content.*;
import android.os.*;

import org.slf4j.*;

/**
 * SyncAdapter for Krombel Stats
 */
public class FfpbSyncAdapter extends AbstractThreadedSyncAdapter {
    public static final Logger LOGGER = LoggerFactory.getLogger(FfpbSyncAdapter.class);


    public FfpbSyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
    }

    public FfpbSyncAdapter(Context context, boolean autoInitialize, boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
    }


    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
        LOGGER.warn("Stub!");

    }

}
