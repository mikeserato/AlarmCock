package com.app.alarmcock;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TimePicker;

import java.util.Calendar;

public class AddAlarm extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alarm);
    }

    public void onCancelClick(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void onOKClick(View view) {
//        Intent intent = new Intent(this, MainActivity.class);
//        startActivity(intent);

        TimePicker alarmTimePicker;
        Calendar calendar = Calendar.getInstance();

        int currentMinute = calendar.get(Calendar.MINUTE);

        alarmTimePicker = (TimePicker) findViewById(R.id.timePicker);
        int alarmMinute = alarmTimePicker.getMinute();

        int difference = Math.abs(alarmMinute - currentMinute);
        calendar.add(Calendar.MINUTE, difference);

        Intent intent = new Intent(this, AlarmActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                12345, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager am =
                (AlarmManager)getSystemService(Activity.ALARM_SERVICE);

        am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }
}
