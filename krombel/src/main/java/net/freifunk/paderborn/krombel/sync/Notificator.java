package net.freifunk.paderborn.krombel.sync;


import android.app.*;
import android.content.*;
import android.support.v4.app.*;
import android.support.v4.app.TaskStackBuilder;

import net.freifunk.paderborn.krombel.*;
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

    public void notificate(List<KrombelStat> newRecords) {
        if (newRecords.size() == 0) {
            return;
        }
        LOGGER.debug("Building notification about new highscores");

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntent(Main_.intent(context).get());
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        LOGGER.debug("Built stack.");
        if (newRecords.size() == 1) {
            LOGGER.debug("Building simple notification.");
            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(context)
                            .setSmallIcon(R.drawable.ic_launcher)
                            .setContentTitle(notificationTitle)
                            .setContentText(statToText(newRecords.get(0)));

            mBuilder.setContentIntent(resultPendingIntent);
            mNotificationManager.notify(mId, mBuilder.build());
        } else {
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                    .setSmallIcon(R.drawable.ic_launcher)
                    .setContentTitle(notificationTitle)
                    .setContentText("");
            NotificationCompat.InboxStyle inboxStyle =
                    new NotificationCompat.InboxStyle();
            inboxStyle.setBigContentTitle(notificationTitle);
            for (KrombelStat stat : newRecords) {
                String line = statToText(stat);
                inboxStyle.addLine(line);
            }
            mBuilder.setStyle(inboxStyle);
            mBuilder.setContentIntent(resultPendingIntent);
            mNotificationManager.notify(mId, mBuilder.build());
        }
    }

    private String statToText(KrombelStat stat) {
        String type = context.getString(stat.getType().stringResId);
        return String.format(notificationStatLine, type, stat.getCount());
    }
}
