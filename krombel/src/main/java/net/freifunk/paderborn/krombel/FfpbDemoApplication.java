package net.freifunk.paderborn.krombel;

import android.accounts.*;
import android.app.*;
import android.content.*;
import android.os.*;

import org.androidannotations.annotations.*;
import org.androidannotations.annotations.res.*;
import org.slf4j.*;

/**
 * Created by ljan on 07.12.14.
 */
@EApplication
public class FfpbDemoApplication extends Application {
    public static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(FfpbDemoApplication.class);
    private static final long SYNC_INTERVAL = 4 * 60 * 60 * 1000;

    @StringRes
    String accountName, accountType, accountAuthority;

    @SystemService
    AccountManager mAccountManager;

    @AfterInject
    void createAccount() {
        Account account = new Account(
                accountName, accountType);

        /*
         * Add the account and account type, no password or user data
         * If successful, return the Account object, otherwise report an error.
         */
        if (mAccountManager.addAccountExplicitly(account, null, null)) {
            LOGGER.info("Added account {}.", account);
        } else {
            LOGGER.info("Could not add account {}.", account);
        }

        getContentResolver().addPeriodicSync(account, accountAuthority, Bundle.EMPTY, SYNC_INTERVAL);

        Bundle forceBundle = new Bundle();
        forceBundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        getContentResolver().requestSync(account, accountAuthority, forceBundle);
        LOGGER.debug("Setup synchronization and forced sync.");
    }
}
