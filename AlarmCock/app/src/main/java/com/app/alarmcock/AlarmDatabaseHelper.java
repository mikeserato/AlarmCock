package com.app.alarmcock;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.text.SimpleDateFormat;

import java.util.Locale;

public class AlarmDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "alarm";
    private static final int DB_VERSION = 1;

    AlarmDatabaseHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE ALARM("
                + "_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "TIME TEXT,"
                + "STATUS INTEGER,"
                + "VIBRATE INTEGER,"
                + "TYPE TEXT);");

        insertAlarm(db, "2:41");
        insertAlarm(db, "2:42");
        insertAlarm(db, "2:43");
        insertAlarm(db, "2:44");
        insertAlarm(db, "2:45");
        insertAlarm(db, "2:46");
    }

    private static void insertAlarm(SQLiteDatabase db, String time){
//        int time=(picker.getCurrentMinute() * 60 + picker.getCurrentHour() * 60 * 60) * 1000;
//        SimpleDateFormat format = new SimpleDateFormat();
//        String formatted = format.format(time);

        ContentValues alarmValues = new ContentValues();
        alarmValues.put("TIME", time);
        alarmValues.put("STATUS", "1");
        alarmValues.put("VIBRATE", "1");
        alarmValues.put("TYPE", "SHAKE");

        db.insert("ALARM", null, alarmValues);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }
}
