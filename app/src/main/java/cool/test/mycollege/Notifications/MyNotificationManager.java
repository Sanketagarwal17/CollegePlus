package cool.test.mycollege.Notifications;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;


import cool.test.mycollege.R;
import cool.test.mycollege.StudyNotes.StudyNotes;

import static android.content.Context.NOTIFICATION_SERVICE;


class MyNotificationManager {
    public static MyNotificationManager getInstance;
    private Context mCtx;
    private static MyNotificationManager mInstance;

    private MyNotificationManager(Context context) {
        mCtx = context;
    }

    public static synchronized MyNotificationManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new MyNotificationManager(context);
        }
        return mInstance;
    }

    public void displayNotification(String title, String body) {

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(mCtx, constants.CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_alarm_black_24dp)
                        .setContentTitle(title)
                        .setContentText(body);


        /*
        *  Clicking on the notification will take us to this intent*/

        Intent resultIntent = new Intent(mCtx, StudyNotes.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(mCtx, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);



        mBuilder.setContentIntent(pendingIntent);

        NotificationManager mNotifyMgr =
                (NotificationManager) mCtx.getSystemService(NOTIFICATION_SERVICE);


        if (mNotifyMgr != null) {
            mNotifyMgr.notify(1, mBuilder.build());
        }
    }
}
