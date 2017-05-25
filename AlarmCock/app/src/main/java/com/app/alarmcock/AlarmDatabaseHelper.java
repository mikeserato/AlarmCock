package com.app.alarmcock;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
                + "TIME TEXT;");

        insertAlarm(db, "2:41");
        insertAlarm(db, "2:42");
    }

    protected static void insertAlarm(SQLiteDatabase db, String time){
        ContentValues alarmValues = new ContentValues();
        alarmValues.put("TIME", time);

        db.insert("ALARM", null, alarmValues);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }
}
