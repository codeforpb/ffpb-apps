package net.freifunk.paderborn.krombel;

import android.support.v4.app.Fragment;
import android.widget.TextView;

import net.freifunk.paderborn.krombel.apis.krombel_dash.*;

import org.androidannotations.annotations.*;
import org.androidannotations.annotations.rest.RestService;

/**
 * Fragment to load and show KrombelStats
 */
@EFragment(R.layout.krombel_stats)
public class KrombelFragment extends Fragment {
    @ViewById
    TextView textCurrentNodesValue, textMaxNodesValue, textCurrentClientsValue, textMaxClientsValue;

    @Bean
    KrombelDownloader mKrombelDownloader;

    @AfterViews
    @Background
    void loadStats() {
        KrombelStat currentNodesStat = mKrombelDownloader.getCurrentNodesStat();
        bindValue(currentNodesStat, textCurrentNodesValue);

        KrombelStat currentClientsStat = mKrombelDownloader.getCurrentClientsStat();
        bindValue(currentClientsStat, textCurrentClientsValue);

        KrombelStat maxNodesStat = mKrombelDownloader.getMaxNodesStat();
        bindValue(maxNodesStat, textMaxNodesValue);

        KrombelStat maxClientsStat = mKrombelDownloader.getMaxClientsStat();
        bindValue(maxClientsStat, textMaxClientsValue);
    }

    @UiThread
    void bindValue(KrombelStat stat, TextView view) {
        view.setText("" + stat.getCount());
    }
}
