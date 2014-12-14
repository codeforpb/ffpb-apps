package net.freifunk.paderborn.krombel.sync;


import android.app.*;
import android.content.*;
import android.support.v4.app.*;
import android.support.v4.app.TaskStackBuilder;

import net.freifunk.paderborn.krombel.*;
import net.freifunk.paderborn.krombel.sync.api.*;

import org.androidannotations.annotations.*;
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
            KrombelStat stat = newRecords.get(0);
            String text = stat.getType() + ": " + stat.getCount();
            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(context)
                            .setSmallIcon(R.drawable.ic_launcher)
                            .setContentTitle("Neuer ffpb Highscore")
                            .setContentText(text);

            mBuilder.setContentIntent(resultPendingIntent);
            mNotificationManager.notify(mId, mBuilder.build());
        } else {
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                    .setSmallIcon(R.drawable.ic_launcher)
                    .setContentTitle("Neue ffpb Highscores")
                    .setContentText("contextText");
            NotificationCompat.InboxStyle inboxStyle =
                    new NotificationCompat.InboxStyle();
            inboxStyle.setBigContentTitle("Neue Highscores:");
            for (KrombelStat stat : newRecords) {
                String line = stat.getType() + ": " + stat.getCount();
                inboxStyle.addLine(line);
            }
            mBuilder.setStyle(inboxStyle);
            mBuilder.setContentIntent(resultPendingIntent);
            mNotificationManager.notify(mId, mBuilder.build());
        }
    }
}
