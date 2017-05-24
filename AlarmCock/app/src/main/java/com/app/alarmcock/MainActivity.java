package com.app.alarmcock;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements AlarmListFragment.OnAlarmSelected {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.root_layout, AlarmListFragment.newInstance(), "AlarmList")
                    .commit();
        }
    }

    @Override
    public void onAlarmSelected(int imageResId, String name) {
        final AlarmOptionsFragment optionsFragment =
                AlarmOptionsFragment.newInstance(imageResId, name);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.root_layout, optionsFragment, "alarmDetails")
                .addToBackStack(null)
                .commit();
    }

    public void onAddAlarmClick(View view) {
        Intent intent = new Intent(this, AddAlarm.class);
        startActivity(intent);
    }

    public void enableAlarm(View view) {

        Switch s = (Switch)findViewById(R.id.switch1);

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.SECOND, 5);

        Intent intent = new Intent(this, AlarmActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                12345, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager am =
                (AlarmManager)getSystemService(Activity.ALARM_SERVICE);

        if(s.isChecked()){
            am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
        }
        else{
            am.cancel(pendingIntent);
        }

    }
}