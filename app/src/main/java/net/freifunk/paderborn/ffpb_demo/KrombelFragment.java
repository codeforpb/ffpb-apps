package net.freifunk.paderborn.ffpb_demo;

import android.support.v4.app.*;
import android.widget.*;

import net.freifunk.paderborn.ffpb_demo.apis.krombel_dash.*;

import org.androidannotations.annotations.*;
import org.androidannotations.annotations.rest.*;

/**
 * Fragment to load and show KrombelStats
 */
@EFragment(R.layout.krombel_stats)
public class KrombelFragment extends Fragment{
    @ViewById
    TextView textCurrentNodesValue, textMaxNodesValue, textCurrentClientsValue, textMaxClientsValue;

    @RestService
    KrombelDashApi mKrombelDashApi;

    @AfterViews
    @Background
    void loadStats() {
        KrombelStat currentNodesStat = mKrombelDashApi.currentNodeCount();
        bindValue(currentNodesStat, textCurrentNodesValue);

        KrombelStat currentClientsStat = mKrombelDashApi.currentNodeCount();
        bindValue(currentClientsStat, textCurrentClientsValue);

        KrombelStat maxNodesStat = mKrombelDashApi.maximumNodeCount();
        bindValue(maxNodesStat, textMaxNodesValue);

        KrombelStat maxClientsStat = mKrombelDashApi.maximumClientCount();
        bindValue(maxClientsStat, textMaxClientsValue);
    }

    @UiThread
    void bindValue(KrombelStat stat, TextView view) {
        view.setText(""+stat.getCount());
    }
}
