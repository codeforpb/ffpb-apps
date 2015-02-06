package net.freifunk.paderborn.nodes.sync;

import android.accounts.*;
import android.content.*;
import android.os.*;
import android.util.*;

import com.j256.ormlite.android.apptools.*;
import com.j256.ormlite.dao.*;

import net.freifunk.paderborn.nodes.api.*;
import net.freifunk.paderborn.nodes.persistence.*;

import org.slf4j.*;
import org.springframework.web.client.*;

import java.sql.*;

/**
 * SyncAdapter for Krombel Stats
 */
public class FfpbSyncAdapter extends AbstractThreadedSyncAdapter {
    public static final Logger LOGGER = LoggerFactory.getLogger(FfpbSyncAdapter.class);
    private NodesJsonApi mNodesJsonApi;
    private DatabaseHelper mDatabaseHelper;


    public FfpbSyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        init(context);
    }

    public FfpbSyncAdapter(Context context, boolean autoInitialize, boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
        init(context);
    }

    private void init(Context context) {
        mNodesJsonApi = new NodesJsonApi_(context);
        mDatabaseHelper = OpenHelperManager.getHelper(context,
                DatabaseHelper.class);
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
        LOGGER.info("onPerformSync(...)");
        StrippedDownNodesJson nodesJson = downloadData();
        persistData(nodesJson);
        LOGGER.info("onPerformSync(...) done");
    }

    private void persistData(StrippedDownNodesJson nodesJson) {
        if (nodesJson == null) {
            return;
        }

        try {
            persist(nodesJson.getNodes(), nodesJson.getMeta());
        } catch (SQLException e) {
            // FIXME handle
            Log.e(FfpbSyncAdapter.class.getName(), "Error", e);
        }
    }

    private void persist(Node[] nodes, MetaInformation meta) throws SQLException {
        LOGGER.debug("persist()");
        Dao<Node, Long> nodeDao = mDatabaseHelper.getNodeDao();
        int created = 0, updated = 0;
        for (Node node : nodes) {
            node.setTimeStamp(meta.getTimestamp());
            Dao.CreateOrUpdateStatus status = nodeDao.createOrUpdate(node);
            if (status.isCreated()) {
                created++;
            } else {
                updated++;
            }
        }

        LOGGER.info("Created {} and updated {} nodes", created, updated);
    }

    private StrippedDownNodesJson downloadData() {
        LOGGER.debug("downloadData()");
        try {
            StrippedDownNodesJson nodesJson = mNodesJsonApi.getNodesJson();
            LOGGER.debug("downloadData() - done");
            return nodesJson;
        } catch (RestClientException e) {
            LOGGER.warn("RestClientException: {}", e);
        }
        LOGGER.debug("downloadData() - errored");
        return null;
    }

}
