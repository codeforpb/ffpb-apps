package net.freifunk.paderborn.nodes.persistence;

import android.accounts.*;
import android.content.*;
import android.database.sqlite.*;
import android.os.*;

import com.j256.ormlite.android.apptools.*;
import com.j256.ormlite.dao.*;
import com.j256.ormlite.support.*;
import com.j256.ormlite.table.*;

import net.freifunk.paderborn.nodes.api.*;
import net.freifunk.paderborn.nodes.sync.*;

import org.slf4j.*;

import java.sql.*;

/**
 * Database Helper class to handle db and daos.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    public static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "nodes.db";
    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseHelper.class);
    private final AccountCreator_ mAccountCreator;
    private final ContentResolver mContentResolver;
    private Dao<Node, Long> nodeDao;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mAccountCreator = AccountCreator_.getInstance_(context);
        mContentResolver = context.getContentResolver();
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTableIfNotExists(connectionSource, Node.class);
        } catch (SQLException e) {
            LOGGER.error("Could not create database", e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, Node.class, true);
            database.execSQL("DROP TABLE IF EXISTS links;");
            onCreate(database, connectionSource);
            requestSync();
            LOGGER.info("Updated db.");
        } catch (SQLException e) {
            LOGGER.error("Could not upgrade db from {} to {}", oldVersion, newVersion);
        }
    }

    private void requestSync() {
        Account account = mAccountCreator.createAccount();

        String authority = mAccountCreator.getAuthority();

        Bundle forceBundle = new Bundle();
        forceBundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        forceBundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        mContentResolver.requestSync(account, authority, forceBundle);
        LOGGER.debug("Forced sync after db upgrade.");
    }

    public Dao<Node, Long> getNodeDao() throws SQLException {
        if (nodeDao == null) {
            nodeDao = getDao(Node.class);
        }
        return nodeDao;
    }


}
