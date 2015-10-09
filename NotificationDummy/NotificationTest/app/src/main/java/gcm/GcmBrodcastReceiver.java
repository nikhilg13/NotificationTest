package gcm;

import android.support.v4.content.WakefulBroadcastReceiver;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

/**
 * Created by nikhilg on 4/10/15.
 */
public class GcmBrodcastReceiver extends WakefulBroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        ComponentName gcmServiceComponent = new ComponentName(context.getPackageName(),GcmIntentService.class.getName());
        startWakefulService(context,intent.setComponent(gcmServiceComponent));
        setResultCode(Activity.RESULT_OK);
    }
}
