package net.freifunk.paderborn.dashboard;

import android.accounts.*;
import android.app.*;
import android.content.*;
import android.os.*;

import net.freifunk.paderborn.dashboard.sync.*;

import org.androidannotations.annotations.*;
import org.slf4j.*;

/**
 * Created by ljan on 07.12.14.
 */
@EApplication
public class FfpbDemoApplication extends Application {
    public static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(FfpbDemoApplication.class);

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
