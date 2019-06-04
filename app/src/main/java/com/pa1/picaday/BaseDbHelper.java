package com.pa1.picaday;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BaseDbHelper extends SQLiteOpenHelper {
    private static BaseDbHelper sInstance;

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "BaseDb.db";
    //DB 생성 시 basic query문, 포맷 null 조건 등은 향후 지정
    private static final String SQL_CREATE_ENTRIES =
            String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT, %s TEXT, %d INTEGER, %s TEXT, %s TEXT, %d INTEGER, %d INTEGER, %s TEXT, %s TEXT)",
                    BaseDbContract.baseDbEntry.TABLE_NAME,
                    BaseDbContract.baseDbEntry._ID,
                    BaseDbContract.baseDbEntry.COLUMN_NAME_TITLE,
                    BaseDbContract.baseDbEntry.COLUMN_NAME_START_TIME,
                    BaseDbContract.baseDbEntry.COLUMN_NAME_END_TIME,
                    BaseDbContract.baseDbEntry.COLUMN_NAME_CHECKBOX_FIRST,
                    BaseDbContract.baseDbEntry.COLUMN_NAME_LOCATION,
                    BaseDbContract.baseDbEntry.COLUMN_NAME_WHO,
                    BaseDbContract.baseDbEntry.COLUMN_NAME_PRIORITY,
                    BaseDbContract.baseDbEntry.COLUMN_NAME_PARTICIPATION,
                    BaseDbContract.baseDbEntry.COLUMN_NAME_CYCLE,
                    BaseDbContract.baseDbEntry.COLUMN_NAME_MEMO);

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + BaseDbContract.baseDbEntry.TABLE_NAME;


    //외부에서 DB 접근, DB 생성 여부 확인에 따라 sInstance 주소값 반환
    public static BaseDbHelper getInstance(Context context) {
        if(sInstance == null){
            sInstance = new BaseDbHelper(context);
        }
        return sInstance;
    }

    //외부에서 DB생성 불가, getInstant를 통해 접근
    private BaseDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
