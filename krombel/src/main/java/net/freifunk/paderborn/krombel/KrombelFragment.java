package net.freifunk.paderborn.krombel;

import android.support.v4.app.Fragment;
import android.widget.TextView;

import net.freifunk.paderborn.krombel.apis.krombel_dash.KrombelDashApi;
import net.freifunk.paderborn.krombel.apis.krombel_dash.KrombelStat;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.rest.RestService;

/**
 * Fragment to load and show KrombelStats
 */
@EFragment(R.layout.krombel_stats)
public class KrombelFragment extends Fragment {
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
        view.setText("" + stat.getCount());
    }
}
