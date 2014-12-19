package net.freifunk.paderborn.dashboard;

import android.support.v4.app.*;
import android.widget.*;

import com.j256.ormlite.android.apptools.*;
import com.j256.ormlite.dao.*;

import net.freifunk.paderborn.dashboard.persistence.*;
import net.freifunk.paderborn.dashboard.sync.api.*;
import net.freifunk.paderborn.dashboard.views.*;

import org.androidannotations.annotations.*;

import java.sql.*;
import java.util.*;

/**
 * Fragment to load and show KrombelStats
 */
@EFragment(R.layout.krombel_stats)
public class KrombelFragment extends Fragment {
    @ViewById
    KrombelStatViewGroup vgCurrentNodes, vgMaxNodes, vgCurrentClients, vgMaxClients;

    @Bean
    KrombelDownloader mKrombelDownloader;

    private DatabaseHelper databaseHelper = null;
    private Dao<KrombelStat, Long> statDao;

    @AfterViews
    @Background
    void loadStats() {
        try {
            KrombelStat currentNodesStat = getKrombelStat(KrombelStatType.CURRENT_NODES);
            bind(currentNodesStat, vgCurrentNodes);

            KrombelStat currentClientsStat = getKrombelStat(KrombelStatType.CURRENT_CLIENTS);
            bind(currentClientsStat, vgCurrentClients);

            KrombelStat maxNodesStat = getKrombelStat(KrombelStatType.MAX_NODES);
            bind(maxNodesStat, vgMaxNodes);

            KrombelStat maxClientsStat = getKrombelStat(KrombelStatType.MAX_CLIENTS);
            bind(maxClientsStat, vgMaxClients);
        } catch (SQLException e) {
            // fixme string res"
            Toast.makeText(getActivity(), "Konnte Daten nicht laden!", Toast.LENGTH_LONG).show();
        }
    }

    private KrombelStat getKrombelStat(KrombelStatType type) throws SQLException {
        return getStatDao().queryBuilder().orderBy(KrombelStat.TIMESTAMP, false).where().eq(KrombelStat.TYPE, type).queryForFirst();
    }

    @UiThread
    void bind(KrombelStat stat, KrombelStatViewGroup view) {
        view.bind(stat);
    }

    @Click
    void btnClearDBClicked() {
        try {
            List<KrombelStat> datas = getStatDao().queryForAll();
            int delete = getStatDao().delete(datas);
            Toast.makeText(getActivity(), "Cleared " + delete + " entries", Toast.LENGTH_LONG).show();
            loadStats();
        } catch (SQLException e) {
            // fixme string res
            Toast.makeText(getActivity(), "DB could not be cleared", Toast.LENGTH_LONG).show();
        }
    }

    private Dao<KrombelStat, Long> getStatDao() throws SQLException {
        if (statDao == null) {
            statDao = getHelper().getStatDao();
        }
        return statDao;
    }

    private DatabaseHelper getHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(getActivity(),
                    DatabaseHelper.class);
        }
        return databaseHelper;
    }

}
