package com.mysandbox.howfrequentyoucheckdevice;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by dhong on 8/3/15.
 */
public class CountDatabase extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String COUNT_TABLE_NAME = "countTable";
    private static final String KEY_ID = "_id";
    private static final String KEY_DATE = "date";
    private static final String KEY_COUNT = "count";
    private static final String CREATE_COUNT_TABLE = "CREATE TABLE " + COUNT_TABLE_NAME + " ("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_DATE + " TEXT, "
            + KEY_COUNT + " INTEGER DEFAULT 0" + ")";


    public CountDatabase(Context context) {
        super(context, COUNT_TABLE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_COUNT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + COUNT_TABLE_NAME);
        onCreate(db);
    }

    public void insertRow(int count) {
        ContentValues values = new ContentValues();
        values.put(KEY_DATE, getCurrentDate());
        values.put(KEY_COUNT, count);
        getWritableDatabase().insert(COUNT_TABLE_NAME, null, values);
    }

    public void updateRow(int count) {
        ContentValues values = new ContentValues();
        values.put(KEY_DATE, getCurrentDate());
        values.put(KEY_COUNT, count);
        String whereClause = KEY_DATE + "=?";
        String[] whereArgs = new String[] { getCurrentDate() };
        getWritableDatabase().update(COUNT_TABLE_NAME, values, whereClause, whereArgs);
    }

    public int getCountForCurrentDate() {
        int toReturn = 0;
        String[] columns = new String[] { KEY_DATE, KEY_COUNT };
        String whereClause = KEY_DATE + "=?";
        String[] whereArgs = new String[] { getCurrentDate() };
        Cursor c = getWritableDatabase().query(true, COUNT_TABLE_NAME, columns, whereClause,
                whereArgs, null, null, null, null);
        if (c != null && c.moveToNext()) {
            toReturn = c.getInt(1); // KEY_COUNT column index
        }
        c.close();
        return toReturn;
    }

    public boolean currentDateExistsInDb() {
        boolean toReturn;
        String[] dateColumn = new String[] { KEY_DATE };
        String whereClause = KEY_DATE + "=?";
        String[] whereArgs = new String[] { getCurrentDate() };
        Cursor c = getWritableDatabase().query(true, COUNT_TABLE_NAME, dateColumn, whereClause,
                whereArgs, null, null, null, null);
        if (c != null && c.getCount() > 0) {
            toReturn = true;
        } else {
            toReturn = false;
        }
        c.close();
        return toReturn;
    }

    private String getCurrentDate() {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }


}
