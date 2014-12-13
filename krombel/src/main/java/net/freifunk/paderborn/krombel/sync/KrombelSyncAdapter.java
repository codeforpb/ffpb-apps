package net.freifunk.paderborn.krombel.sync;

import android.accounts.*;
import android.content.*;
import android.os.*;

import com.j256.ormlite.android.apptools.*;
import com.j256.ormlite.dao.*;

import net.freifunk.paderborn.krombel.persistence.*;
import net.freifunk.paderborn.krombel.sync.api.*;

import org.slf4j.*;

import java.sql.*;

/**
 * SyncAdapter for Krombel Stats
 */
public class KrombelSyncAdapter extends AbstractThreadedSyncAdapter {
    public static final Logger LOGGER = LoggerFactory.getLogger(KrombelSyncAdapter.class);
    KrombelDownloader mDownloader;
    private DatabaseHelper databaseHelper;
    private Dao<KrombelStat, Long> statDao;

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
        databaseHelper = OpenHelperManager.getHelper(context,
                DatabaseHelper.class);

    }

    private Dao<KrombelStat, Long> getStatDao() throws SQLException {
        if (statDao == null) {
            statDao = databaseHelper.getStatDao();
        }
        return statDao;
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
        try {
            LOGGER.debug("Starting sync");
            for (KrombelStatType type : KrombelStatType.values()) {
                persist(mDownloader.download(type));
            }
        } catch (SQLException e) {
            LOGGER.error("Could not sync because of SQLException", e);
        } finally {
            LOGGER.debug("Finished sync");
        }
    }

    private void persist(KrombelStat stat) throws SQLException {
        getStatDao().create(stat);
        LOGGER.info("Created {}", stat);
    }

}
