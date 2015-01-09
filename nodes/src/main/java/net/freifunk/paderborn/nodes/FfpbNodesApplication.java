package net.freifunk.paderborn.nodes;

import android.accounts.*;
import android.app.*;
import android.content.*;
import android.os.*;

import net.freifunk.paderborn.nodes.sync.*;

import org.androidannotations.annotations.*;
import org.slf4j.*;

/**
 * Application class. Sets up synchronization
 */
@EApplication
public class FfpbNodesApplication extends Application {
    public static final Logger LOGGER = LoggerFactory.getLogger(FfpbNodesApplication.class);

    private static final long SYNC_INTERVAL = 4 * 60 * 60 * 1000;

    @Bean
    AccountCreator mAccountCreator;

    @AfterInject
    void createAccount() {
        Account account = mAccountCreator.createAccount();

        String authority = mAccountCreator.getAuthority();
        getContentResolver().addPeriodicSync(account, authority, Bundle.EMPTY, SYNC_INTERVAL);

        Bundle forceBundle = new Bundle();
        forceBundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        getContentResolver().requestSync(account, authority, forceBundle);
        LOGGER.debug("Setup synchronization and forced sync.");
    }
}
