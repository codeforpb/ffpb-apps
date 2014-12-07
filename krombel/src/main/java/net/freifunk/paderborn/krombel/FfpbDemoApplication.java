package net.freifunk.paderborn.krombel;

import android.accounts.*;
import android.app.*;

import com.noveogroup.android.log.*;
import com.noveogroup.android.log.Logger;

import org.androidannotations.annotations.*;
import org.androidannotations.annotations.res.*;
import org.slf4j.*;

/**
 * Created by ljan on 07.12.14.
 */
@EApplication
public class FfpbDemoApplication extends Application {
    public static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(FfpbDemoApplication.class);
    @StringRes
    String accountName, accountType, accountAuthority;

    @SystemService
    AccountManager mAccountManager;

    @AfterInject
    void createAccount(){
        Account newAccount = new Account(
                accountName, accountType);

        /*
         * Add the account and account type, no password or user data
         * If successful, return the Account object, otherwise report an error.
         */
        if (mAccountManager.addAccountExplicitly(newAccount, null, null)){
            LOGGER.info("Added account {}.", newAccount);
        }else{
            LOGGER.info("Could not add account {}.", newAccount);
        }



    }
}
