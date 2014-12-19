package net.freifunk.paderborn.dashboard.sync;

import android.accounts.*;

import org.androidannotations.annotations.*;
import org.androidannotations.annotations.res.*;
import org.slf4j.*;

/**
 * Created by ljan on 13.12.14.
 */
@EBean(scope = EBean.Scope.Singleton)
public class AccountCreator {
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountCreator.class);

    @StringRes
    String accountName, accountType, accountAuthority;

    @SystemService
    AccountManager mAccountManager;
    private Account mAccount;

    public Account createAccount() {
        mAccount = new Account(
                accountName, accountType);

        /*
         * Add the account and account type, no password or user data
         * If successful, return the Account object, otherwise report an error.
         */
        if (mAccountManager.addAccountExplicitly(mAccount, null, null)) {
            LOGGER.info("Added account {}.", mAccount);
        } else {
            LOGGER.info("Could not add account {}.", mAccount);
        }
        return mAccount;
    }

    public Account getAccount() {
        if (mAccount == null) {
            createAccount();
        }
        return mAccount;
    }

    public String getAuthority() {
        return accountAuthority;
    }
}
