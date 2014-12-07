package net.freifunk.paderborn.krombel.apis.krombel_dash;

import org.androidannotations.annotations.*;
import org.androidannotations.annotations.rest.*;

/**
 * Downloader for krombel stats. Simple in memory caching.
 * FIXME: no error handling implemented...
 */
@EBean(scope = EBean.Scope.Singleton)
public class KrombelDownloader {
    @RestService
    KrombelDashApi mKrombelDashApi;

    private KrombelStat currentNodesStat;
    private KrombelStat currentClientsStat;
    private KrombelStat maxNodesStat;
    private KrombelStat maxClientsStat;

    public synchronized KrombelStat getCurrentNodesStat() {
        currentNodesStat = mKrombelDashApi.currentNodeCount();
        currentNodesStat.setType(KrombelStatType.CURRENT_NODES);
        return currentNodesStat;
    }

    public synchronized KrombelStat getCurrentClientsStat() {
        currentClientsStat = mKrombelDashApi.currentNodeCount();
        currentClientsStat.setType(KrombelStatType.CURRENT_CLIENTS);
        return currentClientsStat;
    }

    public synchronized KrombelStat getMaxNodesStat() {
        maxNodesStat = mKrombelDashApi.maximumNodeCount();
        maxNodesStat.setType(KrombelStatType.MAX_NODES);
        return maxNodesStat;
    }

    public synchronized KrombelStat getMaxClientsStat() {
        maxClientsStat = mKrombelDashApi.maximumClientCount();
        maxClientsStat.setType(KrombelStatType.MAX_CLIENTS);
        return maxClientsStat;
    }

    public KrombelStat[] getAllStats() {
        getCurrentNodesStat();
        getMaxClientsStat();
        getMaxNodesStat();
        getMaxClientsStat();
        return new KrombelStat[]{currentNodesStat, currentClientsStat, maxNodesStat, maxClientsStat};
    }
}
