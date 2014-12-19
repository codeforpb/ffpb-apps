package net.freifunk.paderborn.dashboard.sync.api;

import org.androidannotations.annotations.*;
import org.androidannotations.annotations.rest.*;
import org.slf4j.*;

/**
 * Downloader for krombel stats. Simple in memory caching.
 * FIXME: no error handling implemented...
 */
@EBean(scope = EBean.Scope.Singleton)
public class KrombelDownloader {
    public static final Logger LOGGER = LoggerFactory.getLogger(KrombelDownloader.class);
    @RestService
    KrombelDashApi mKrombelDashApi;

    public synchronized KrombelStat download(KrombelStatType type) {
        LOGGER.debug("Starting download of {}", type);
        KrombelStat stat = new KrombelStat();
        switch (type) {
            case CURRENT_CLIENTS:
                stat = mKrombelDashApi.currentClientCount();
                break;
            case CURRENT_NODES:
                stat = mKrombelDashApi.currentNodeCount();
                break;
            case MAX_CLIENTS:
                stat = mKrombelDashApi.maximumClientCount();
                break;
            case MAX_NODES:
                stat = mKrombelDashApi.maximumNodeCount();
                break;
        }
        stat.setType(type);
        LOGGER.debug("Downloaded {}", stat);
        return stat;
    }
}
