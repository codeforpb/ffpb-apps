package net.freifunk.paderborn.krombel.sync;

import android.app.*;
import android.content.*;
import android.os.*;

import org.androidannotations.annotations.*;

/**
 * FIXME: doc
 */
@EService
public class KrombelAuthenticatorService extends Service {
    @Bean
    StubAuthenticator mAuthenticator;


    @Override
    public IBinder onBind(Intent intent) {
        // FIXME is this working with bean?
        return mAuthenticator.getIBinder();
    }
}
