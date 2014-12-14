package net.freifunk.paderborn.krombel;

import android.content.*;
import android.os.*;
import android.support.v4.widget.*;
import android.support.v7.app.*;

import net.freifunk.paderborn.krombel.sync.*;

import org.androidannotations.annotations.*;
import org.androidannotations.annotations.res.*;
import org.slf4j.*;

/**
 * Created by ljan on 30.11.14.
 */
@EActivity(R.layout.activity_main)
public class Main extends ActionBarActivity implements SwipeRefreshLayout.OnRefreshListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
    @Bean
    AccountCreator mAccountCreator;

    @ViewById
    SwipeRefreshLayout refreshLayout;

    @StringRes
    String titleKrombelStats;

    @FragmentById
    KrombelFragment fragmentDash;

    @AfterViews
    void afterViews() {
        refreshLayout.setOnRefreshListener(this);
        setTitle(titleKrombelStats);
        registerSyncObserver();
    }

    void registerSyncObserver() {
        int mask = ContentResolver.SYNC_OBSERVER_TYPE_ACTIVE | ContentResolver.SYNC_OBSERVER_TYPE_PENDING;
        final ContentResolver contentResolver = getContentResolver();
        contentResolver.addStatusChangeListener(mask, new SyncStatusObserver() {
            @Override
            public void onStatusChanged(int which) {
                boolean syncActive = ContentResolver.isSyncActive(mAccountCreator.getAccount(), mAccountCreator.getAuthority());
                updateUi(syncActive);
            }
        });
    }

    @UiThread
    void updateUi(boolean syncActive) {
        refreshLayout.setRefreshing(syncActive);
        fragmentDash.loadStats();
    }

    @Override
    public void onRefresh() {
        Bundle bundle = new Bundle();
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        ContentResolver.requestSync(mAccountCreator.getAccount(), mAccountCreator.getAuthority(), bundle);
        LOGGER.debug("Requested sync");
    }
}
