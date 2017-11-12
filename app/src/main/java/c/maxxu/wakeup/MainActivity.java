package c.maxxu.wakeup;

    import android.annotation.TargetApi;
    import android.app.AlarmManager;
    import android.app.PendingIntent;
    import android.content.Intent;
    import android.os.Bundle;
    import android.support.v7.app.AppCompatActivity;
    import android.view.View;
    import android.widget.TimePicker;
    import android.widget.Toast;
    import android.widget.ToggleButton;
    import java.util.Calendar;

public class MainActivity extends AppCompatActivity
{
    TimePicker alarmTimePicker;
    static PendingIntent pendingIntent, pendingIntent2, pendingIntent3;
    static AlarmManager alarmManager;
    private static boolean ringtoneOn = false; // whether a ringtone is currently playing
    static int alarm = 1; // desired response to PendingIntent

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        alarmTimePicker = findViewById(R.id.timePicker);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
    }

    @TargetApi(19)
    public void OnToggleClicked(View view)
    {
    long time1, time2, time3;
        if (((ToggleButton) view).isChecked()) {
            Toast.makeText(MainActivity.this, "ALARM ON", Toast.LENGTH_SHORT).show();

            // initialize calendar
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, alarmTimePicker.getCurrentHour());
            calendar.set(Calendar.MINUTE, alarmTimePicker.getCurrentMinute());

            // set up 3 pendingIntents
            Intent intent1 = new Intent(this, AlarmReceiver.class);
            Intent intent2 = new Intent(this, AlarmReceiver.class);
            Intent intent3 = new Intent(this, AlarmReceiver.class);
            pendingIntent = PendingIntent.getBroadcast(this, 0, intent1, 0);
            pendingIntent2 = PendingIntent.getBroadcast(this, 1, intent2, 0);
            pendingIntent3 = PendingIntent.getBroadcast(this, 2, intent3, 0);

            // set up time delay
            time1 = (calendar.getTimeInMillis() - (calendar.getTimeInMillis() % 60000));
            if (System.currentTimeMillis() > time1) {
                time1 = time1 + (1000 * 60 * 60 * 24);
            }
            time2 = time1 + 10000; // 10 second time delay
            time3 = time1 + 20000; // 20 second time delay

            // set up alarms
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, time1, pendingIntent);
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, time2, pendingIntent2);
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, time3, pendingIntent3);
        }

        else
        {
            Toast.makeText(MainActivity.this, "ALARM OFF", Toast.LENGTH_SHORT).show();

            if (ringtoneOn) {
                // stops playing ringtone
                AlarmReceiver.ringtone.stop();
                setRingtone();
            }
            else {
                // cancels alarms if first alarm has not yet sounded
                alarmManager.cancel(pendingIntent);
                alarmManager.cancel(pendingIntent2);
                alarmManager.cancel(pendingIntent3);
            }
        }
    }

    // changes status of whether ringtone is on
    public static void setRingtone() {
        ringtoneOn = !ringtoneOn;
    }

    // cancels alarm to send out text messages
    public static void disableTextAlarm() {
        alarmManager.cancel(pendingIntent3);
    }
}