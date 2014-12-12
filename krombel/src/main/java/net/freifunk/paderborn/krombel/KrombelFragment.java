package net.freifunk.paderborn.krombel;

import android.support.v4.app.*;

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

    @AfterViews
    @Background
    void loadStats() {
        KrombelStat currentNodesStat = mKrombelDownloader.getCurrentNodesStat();
        bind(currentNodesStat, vgCurrentNodes);

        KrombelStat currentClientsStat = mKrombelDownloader.getCurrentClientsStat();
        bind(currentClientsStat, vgCurrentClients);

        KrombelStat maxNodesStat = mKrombelDownloader.getMaxNodesStat();
        bind(maxNodesStat, vgMaxNodes);

        KrombelStat maxClientsStat = mKrombelDownloader.getMaxClientsStat();
        bind(maxClientsStat, vgMaxClients);
    }

    @UiThread
    void bind(KrombelStat stat, KrombelStatViewGroup view) {
        view.bind(stat);
    }
}
