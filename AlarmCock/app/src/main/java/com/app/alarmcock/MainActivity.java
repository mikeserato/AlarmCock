package com.app.alarmcock;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity
        implements AlarmListFragment.OnAlarmSelected {

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
}