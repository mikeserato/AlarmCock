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

    private AlarmDatabaseHelper alarmDatabaseHelper;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alarm);

        alarmDatabaseHelper = new AlarmDatabaseHelper(this.getApplicationContext());
    }

    public void onCancelClick(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void onOKClick(View view) {
        TimePicker alarmTimePicker;
        alarmTimePicker = (TimePicker) findViewById(R.id.timePicker);

        int alarmHour = alarmTimePicker.getHour();
        int alarmMinute = alarmTimePicker.getMinute();

        String time;
        if(alarmMinute < 10) {
            time = alarmHour + ":0" + alarmMinute;
        } else {
            time = alarmHour + ":" + alarmMinute;
        }

        alarmDatabaseHelper.insertAlarm(alarmDatabaseHelper.getReadableDatabase(), time);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
