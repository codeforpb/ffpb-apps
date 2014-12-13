package net.freifunk.paderborn.krombel.persistence;

import android.content.*;
import android.database.sqlite.*;

import com.j256.ormlite.android.apptools.*;
import com.j256.ormlite.dao.*;
import com.j256.ormlite.support.*;
import com.j256.ormlite.table.*;

import net.freifunk.paderborn.krombel.sync.api.*;

import org.slf4j.*;

import java.sql.*;

/**
 * Created by ljan on 13.12.14.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "krombel.db";
private static final  Logger LOGGER = LoggerFactory.getLogger(DatabaseHelper.class);
    private Dao<KrombelStat, Long> statDao;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTableIfNotExists(connectionSource, KrombelStat.class);
        } catch (SQLException e) {
            LOGGER.error("Could not create database", e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }

    public Dao<KrombelStat, Long> getStatDao() throws SQLException {
        if (statDao == null) {
            statDao = getDao(KrombelStat.class);
        }
        return  statDao;
    }

}
