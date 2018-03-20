package id.co.imastudio.driver;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import id.co.imastudio.driver.View.DetailRequestActivity;



public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);


        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {

           String pesanfirbase =  remoteMessage.getNotification().getBody();
            //Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());

            notif(pesanfirbase);
        }

    }

    private void notif(String pesanfirbase) {

        NotificationCompat.Builder mBuilder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                        .setSmallIcon(android.R.drawable.ic_btn_speak_now)
                        .setAutoCancel(true)

                        .setContentTitle("testing firebase console")
                        .setContentText("Hello World!");


        //action klik dari notif
        Intent resultIntent = new Intent(this, DetailRequestActivity.class);

        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        this,
                        0,
                        resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        mBuilder.setContentIntent(resultPendingIntent);

        // Sets an ID for the notification
        int mNotificationId = 001;
// Gets an instance of the NotificationManager service
        NotificationManager mNotifyMgr =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
// Builds the notification and issues it.
        mNotifyMgr.notify(mNotificationId, mBuilder.build());

    }
}
