package c.maxxu.wakeup;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.widget.Toast;
import android.app.Activity;

public class AlarmReceiver extends BroadcastReceiver
{

    static Ringtone ringtone;

    @Override
    public void onReceive(Context context, Intent intent) {
        // first PendingIntent
        if (MainActivity.alarm == 1) {
            Toast.makeText(context, "Time to wake the h*** up!", Toast.LENGTH_LONG).show();

            Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            if (alarmUri == null) {
                alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            }
            ringtone = RingtoneManager.getRingtone(context, alarmUri);
            ringtone.play();
            MainActivity.setRingtone();
            MainActivity.alarm++;
        }

        // second PendingIntent
        else if (MainActivity.alarm == 2) {
            // sends out alert dialog to disable text alarm
            context.startActivity(new Intent(context, DelayedAlert.class));
            MainActivity.alarm++;
        }

        // third PendingIntent
        else {
            // initiates text messages
            context.startActivity(new Intent(context, TextAlert.class));
            MainActivity.alarm = 1;
        }
    }
}