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
import java.util.*;

/**
 * SyncAdapter for Krombel Stats
 */
public class KrombelSyncAdapter extends AbstractThreadedSyncAdapter {
    public static final Logger LOGGER = LoggerFactory.getLogger(KrombelSyncAdapter.class);
    KrombelDownloader mDownloader;
    private DatabaseHelper databaseHelper;
    private Dao<KrombelStat, Long> statDao;
    private Notificator mNotificator;


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
        mNotificator = Notificator_.getInstance_(context);
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
            List<KrombelStat> newRecords = new ArrayList<>(4);
            for (KrombelStatType type : KrombelStatType.values()) {
                KrombelStat downloaded = mDownloader.download(type);
                KrombelStat currentMax = getCurrentMax(type);
                LOGGER.debug("Current highscore: {}", currentMax);
                if (downloaded.getCount() > currentMax.getCount()) {
                    LOGGER.debug("New highscore");
                    newRecords.add(downloaded);
                    LOGGER.debug("Added to new highscore list");
                    getStatDao().create(downloaded);
                    LOGGER.info("Persisted {}", downloaded);
                }
                LOGGER.trace("Downloading next.");
            }
            LOGGER.debug("Downloads finished.");
            mNotificator.notificate(newRecords);
        } catch (SQLException e) {
            LOGGER.error("Could not sync because of SQLException", e);
        } finally {
            LOGGER.debug("Finished sync");
        }
    }

    private KrombelStat getCurrentMax(KrombelStatType type) throws SQLException {
        KrombelStat krombelStat = getStatDao().queryBuilder()
                .orderBy(KrombelStat.COUNT, false)
                .where().eq(KrombelStat.TYPE, type)
                .queryForFirst();
        if (krombelStat == null) {
            krombelStat = new KrombelStat();
            krombelStat.setCount(-1);
        }
        return krombelStat;
    }

}
