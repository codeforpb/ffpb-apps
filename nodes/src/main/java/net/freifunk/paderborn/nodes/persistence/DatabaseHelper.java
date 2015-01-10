package net.freifunk.paderborn.nodes.persistence;

import android.content.*;
import android.database.sqlite.*;

import com.j256.ormlite.android.apptools.*;
import com.j256.ormlite.dao.*;
import com.j256.ormlite.support.*;
import com.j256.ormlite.table.*;

import net.freifunk.paderborn.nodes.api.*;

import org.slf4j.*;

import java.sql.*;

/**
 * Database Helper class to handle db and daos.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    public static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "nodes.db";
    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseHelper.class);
    private Dao<Node, Long> nodeDao;
    private Dao<Link, Long> linkDao;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTableIfNotExists(connectionSource, Node.class);
            TableUtils.createTableIfNotExists(connectionSource, Link.class);
        } catch (SQLException e) {
            LOGGER.error("Could not create database", e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, Node.class, true);
            TableUtils.dropTable(connectionSource, Link.class, true);
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            LOGGER.error("Could not upgrade db from {} to {}", oldVersion, newVersion);
        }
    }

    public Dao<Node, Long> getNodeDao() throws SQLException {
        if (nodeDao == null) {
            nodeDao = getDao(Node.class);
        }
        return nodeDao;
    }

    public Dao<Link, Long> getLinkDao() throws SQLException {
        if (linkDao == null) {
            linkDao = getDao(Link.class);
        }
        return linkDao;
    }


}
