package c.maxxu.wakeup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.AlertDialog;
import android.content.DialogInterface;

public class DelayedAlert extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Are you still awake???");
        builder1.setCancelable(true);


        // disables text alarm if "Yes"
        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MainActivity.disableTextAlarm();
                        dialog.cancel();
                    }
                });

        // text alarm activates if "No" or if "Yes" is not selected before the alarm ends
        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
}