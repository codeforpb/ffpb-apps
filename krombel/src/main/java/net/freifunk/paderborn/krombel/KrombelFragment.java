package net.freifunk.paderborn.krombel;

import android.support.v4.app.*;

import com.j256.ormlite.android.apptools.*;

import net.freifunk.paderborn.krombel.persistence.*;
import net.freifunk.paderborn.krombel.sync.api.*;
import net.freifunk.paderborn.krombel.views.*;

import org.androidannotations.annotations.*;

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

    @AfterViews
    @Background
    void loadStats() {
        KrombelStat currentNodesStat = mKrombelDownloader.download(KrombelStatType.CURRENT_NODES);
        bind(currentNodesStat, vgCurrentNodes);

        KrombelStat currentClientsStat = mKrombelDownloader.download(KrombelStatType.CURRENT_CLIENTS);
        bind(currentClientsStat, vgCurrentClients);

        KrombelStat maxNodesStat = mKrombelDownloader.download(KrombelStatType.MAX_NODES);
        bind(maxNodesStat, vgMaxNodes);

        KrombelStat maxClientsStat = mKrombelDownloader.download(KrombelStatType.MAX_CLIENTS);
        bind(maxClientsStat, vgMaxClients);
    }

    @UiThread
    void bind(KrombelStat stat, KrombelStatViewGroup view) {
        view.bind(stat);
    }
}
