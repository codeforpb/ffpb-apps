package net.freifunk.paderborn.nodes.sync;

import android.app.*;
import android.content.*;
import android.os.*;

import org.androidannotations.annotations.*;

/**
 * Authenticator Service for Sync Adapter
 */
@EService
public class FfpbAuthenticatorService extends Service {
    @Bean
    StubAuthenticator mAuthenticator;

    @Override
    public IBinder onBind(Intent intent) {
        return mAuthenticator.getIBinder();
    }
}
