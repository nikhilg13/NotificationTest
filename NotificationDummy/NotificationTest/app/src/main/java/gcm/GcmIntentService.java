package gcm;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.nikhilg.notificationtest.MainActivity;
import com.example.nikhilg.notificationtest.R;
import com.google.android.gms.gcm.GoogleCloudMessaging;

/**
 * Created by nikhilg on 4/10/15.
 */
public class GcmIntentService extends IntentService {

    public static final String TAG = "GCM Test";
    public static final int NOTIFICATION_ID = 1;
    private NotificationManager _notificationManeger;

    public GcmIntentService()
    {
        super(GcmIntentService.class.getName());
    }
    @Override
    protected void onHandleIntent(Intent intent) {

        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        String messageType = gcm.getMessageType(intent);
        Bundle extras = intent.getExtras();
        Log.i(TAG, "Received extras: " + extras.toString());
        if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
            createNotification("Send error occured!", "GCM", "NONE");
        } else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED.equals(messageType)) {
            createNotification("Messages deleted on the server!", "GCM", "NONE");
        } else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
            String message = extras.getString("message");
            String senderName = extras.getString("senderName");
            String recipientNames = extras.getString("recipientNames");
            createNotification(message, senderName, recipientNames);
        }
        GcmBrodcastReceiver.completeWakefulIntent(intent);
    }

    private void createNotification(String message, String senderName, String recipientNames) {
        Intent notificationIntent = new Intent(this, MainActivity.class);
        notificationIntent.setAction(Intent.ACTION_MAIN);
        notificationIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        _notificationManeger = (NotificationManager)this.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setAutoCancel(true)
                        .setContentTitle(senderName + " says:")
                        .setContentText( "\"" + message + "\"")
                        .setSubText("Recipients: " + recipientNames.replace("#", ","))
                        .setSmallIcon(R.drawable.common_ic_googleplayservices)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                        .setContentIntent(pendingIntent);

        _notificationManeger.notify(NOTIFICATION_ID, builder.build());
    }
}
