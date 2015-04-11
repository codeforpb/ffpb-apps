package net.freifunk.paderborn.krombel.sync;


import android.app.*;
import android.content.*;
import android.support.v4.app.*;
import android.support.v4.app.TaskStackBuilder;

import net.freifunk.paderborn.krombel.Main_;
import net.freifunk.paderborn.krombel.R;
import net.freifunk.paderborn.krombel.sync.api.*;

import org.androidannotations.annotations.*;
import org.androidannotations.annotations.res.*;
import org.slf4j.*;

import java.util.*;

/**
 * Created by ljan on 14.12.14.
 */
@EBean(scope = EBean.Scope.Singleton)
public class Notificator {
    @SystemService
    NotificationManager mNotificationManager;
    Logger LOGGER = LoggerFactory.getLogger(Notificator.class);
    @RootContext
    Context context;
    @StringRes
    String notificationStatLine, notificationTitle;
    private int mId = 0;

    /**
     * @param newRecords
     */
    public void notificate(List<KrombelStat> newRecords) {
        if (newRecords.size() == 0) {
            return;
        }

        LOGGER.debug("Building notification about new highscores");

        PendingIntent resultPendingIntent = buildStack();

        LOGGER.debug("Built stack.");

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle(notificationTitle);

        if (newRecords.size() == 1) {
            mBuilder = buildSimpleNotification(newRecords, mBuilder);
        } else {
            mBuilder = buildInboxNotification(newRecords, mBuilder);
        }

        mBuilder.setContentIntent(resultPendingIntent);
        mNotificationManager.notify(mId, mBuilder.build());
    }

    private NotificationCompat.Builder buildInboxNotification(List<KrombelStat> newRecords, NotificationCompat.Builder mBuilder) {
        mBuilder = mBuilder.setContentText("Neue Highscores!");
        NotificationCompat.InboxStyle inboxStyle =
                new NotificationCompat.InboxStyle();
        inboxStyle.setBigContentTitle(notificationTitle);
        for (KrombelStat stat : newRecords) {
            String line = statToText(stat);
            inboxStyle.addLine(line);
        }
        mBuilder.setStyle(inboxStyle);
        return mBuilder;
    }

    private NotificationCompat.Builder buildSimpleNotification(List<KrombelStat> newRecords, NotificationCompat.Builder mBuilder) {
        LOGGER.debug("Building simple notification.");
        mBuilder = mBuilder.setContentText(statToText(newRecords.get(0)));
        return mBuilder;
    }

    private PendingIntent buildStack() {
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntent(Main_.intent(context).get());
        return stackBuilder.getPendingIntent(
                0,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
    }


    private String statToText(KrombelStat stat) {
        if (stat.getCount() == -1) {
            return "";
        }
        String type = context.getString(stat.getType().stringResId);
        return String.format(notificationStatLine, type, stat.getCount());
    }
}
