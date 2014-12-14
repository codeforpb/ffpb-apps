package net.freifunk.paderborn.krombel;

import android.support.v4.app.*;
import android.widget.*;

import com.j256.ormlite.android.apptools.*;
import com.j256.ormlite.dao.*;

import net.freifunk.paderborn.krombel.persistence.*;
import net.freifunk.paderborn.krombel.sync.api.*;
import net.freifunk.paderborn.krombel.views.*;

import org.androidannotations.annotations.*;

import java.sql.*;

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

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (databaseHelper != null) {
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }
    }

    private DatabaseHelper getHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(getActivity(),
                    DatabaseHelper.class);
        }
        return databaseHelper;
    }

    private Dao<KrombelStat, Long> getStatDao() throws SQLException {
        if (statDao == null) {
            statDao = getHelper().getStatDao();
        }
        return statDao;
    }

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
            getStatDao().delete(getStatDao().queryForAll());
            loadStats();
        } catch (SQLException e) {
            // fixme string res
            Toast.makeText(getActivity(), "DB could not be cleared", Toast.LENGTH_LONG).show();
        }
    }
}
