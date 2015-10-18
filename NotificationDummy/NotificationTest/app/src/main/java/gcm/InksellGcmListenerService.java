package gcm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.nikhilg.notificationtest.MainActivity;
import com.example.nikhilg.notificationtest.R;
import com.google.android.gms.gcm.GcmListenerService;

/**
 * Created by nikhilg on 15/10/15.
 */
public class InksellGcmListenerService extends GcmListenerService {

    private static final String TAG = "InksellGcmListenerService";
    public static final int NOTIFICATION_ID = 1;
    private NotificationManager _notificationManeger;

    @Override
    public void onMessageReceived(String from, Bundle data) {

        String message = data.getString("message");
        String senderName = data.getString("senderName");
        String recipientNames = data.getString("recipientNames");
        createNotification(message, senderName, recipientNames);

    }

    private void createNotification(String message, String senderName, String recipientNames) {
        Intent notificationIntent = new Intent(this, MainActivity.class);
        notificationIntent.setAction(Intent.ACTION_MAIN);
        notificationIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        _notificationManeger = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setAutoCancel(true)
                        .setContentTitle(senderName + " says:")
                        .setContentText("\"" + message + "\"")
                        .setSubText("Recipients: " + recipientNames.replace("#", ","))
                        .setSmallIcon(R.drawable.common_ic_googleplayservices)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                        .setContentIntent(pendingIntent);

        _notificationManeger.notify(NOTIFICATION_ID, builder.build());
    }
}
