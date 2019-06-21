package com.pa1.picaday.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.pa1.picaday.CustomUI.Dateinfo;

import java.util.ArrayList;

public class DBManager {

    // DB관련 상수 선언
    private static final String dbName = "Dateinfo.db";
    //private static final String tableName = "Dateinfo";
    public int id = 1;
    public static final String TABLE_NAME = "Dateinfo";
    public static final String COLUMN_NAME_ID = "id";
    public static final String COLUMN_NAME_TITLE = "name";
    public static final String COLUMN_NAME_START_TIME = "startTime";
    public static final String COLUMN_NAME_END_TIME = "endTime";
    public static final String COLUMN_NAME_CHECKBOX_FIRST = "checkboxFirst";
    public static final String COLUMN_NAME_LOCATION = "location";
    public static final String COLUMN_NAME_WHO = "who";
    public static final String COLUMN_NAME_PRIORITY = "priority";
    public static final String COLUMN_NAME_PARTICIPATION = "participation";
    public static final String COLUMN_NAME_CYCLE = "cycle";
    public static final String COLUMN_NAME_MEMO = "memo";
    public static final int dbVersion = 1;

    // DB관련 객체 선언
    private OpenHelper opener; // DB opener
    private SQLiteDatabase db; // DB controller

    // 부가적인 객체들
    private Context context;

    // 생성자
    public DBManager(Context context) {
        this.context = context;
        this.opener = new OpenHelper(context, dbName, null, dbVersion);
        db = opener.getWritableDatabase();
    }

    // Opener of DB and Table
    private class OpenHelper extends SQLiteOpenHelper {

        public OpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                          int version) {
            super(context, name, null, version);
            // TODO Auto-generated constructor stub
        }

        // 생성된 DB가 없을 경우에 한번만 호출됨
        @Override
        public void onCreate(SQLiteDatabase arg0) {
            // String dropSql = "drop table if exists " + tableName;
            // db.execSQL(dropSql);

            String createSql = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            "%s TEXT, %s TEXT, %s TEXT, " +
                            "%s INTEGER, %s TEXT, %s TEXT, %s INTEGER, %s INTEGER, %s INTEGER, %s TEXT)",
                    TABLE_NAME,
                    COLUMN_NAME_ID,
                    COLUMN_NAME_TITLE,
                    COLUMN_NAME_START_TIME,
                    COLUMN_NAME_END_TIME,
                    COLUMN_NAME_CHECKBOX_FIRST,
                    COLUMN_NAME_LOCATION,
                    COLUMN_NAME_WHO,
                    COLUMN_NAME_PRIORITY,
                    COLUMN_NAME_PARTICIPATION,
                    COLUMN_NAME_CYCLE,
                    COLUMN_NAME_MEMO);
            arg0.execSQL(createSql);
            ///Toast.makeText(context, "DB is opened", 0).show();
        }

        @Override
        public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
            // TODO Auto-generated method stub
        }
    }

    // 데이터 추가
    public void insertData(Dateinfo info) {
        String insertsql = "INSERT INTO "+ TABLE_NAME
                + "(" + COLUMN_NAME_TITLE + ", " + COLUMN_NAME_START_TIME + ", "
                + COLUMN_NAME_END_TIME + ", " + COLUMN_NAME_CHECKBOX_FIRST + ", "
                + COLUMN_NAME_LOCATION + ", " + COLUMN_NAME_WHO + ", "
                + COLUMN_NAME_PRIORITY + ", " + COLUMN_NAME_PARTICIPATION + ", "
                + COLUMN_NAME_CYCLE + ", " + COLUMN_NAME_MEMO + ")"
                +" values ('"
                + info.getTitle() + "', '"
                + info.getStart_time() + "', '"
                + info.getEnd_time() + "', "
                + info.getType_checked() + ", '"
                + info.getLocation() + "', '"
                + info.getWithwhom() + "', "
                + info.getPriority() + ", "
                + info.getParticipation() + ", "
                + 0 + ", '"
                + info.getMemo() + "');";
        db.execSQL(insertsql);

    }

    // 데이터 갱신
    public void updateData(Dateinfo info, int index) {
        String setsql = String.format("UPDATE %s SET %s = %s, %s = %s, %s = %s, %s = %s, %s = %s, %s = %s, %s = %s, %s = %s, %s = %s, %s = %s",
                TABLE_NAME,
                COLUMN_NAME_TITLE, info.getTitle(),
                COLUMN_NAME_START_TIME, info.getStart_time(),
                COLUMN_NAME_END_TIME, info.getEnd_time(),
                COLUMN_NAME_CHECKBOX_FIRST, info.getType_checked(),
                COLUMN_NAME_LOCATION, info.getLocation(),
                COLUMN_NAME_WHO, info.getWithwhom(),
                COLUMN_NAME_PRIORITY, info.getPriority(),
                COLUMN_NAME_CYCLE, 0,
                COLUMN_NAME_MEMO, info.getMemo());
        db.execSQL(setsql);
    }

    // 데이터 삭제
    public void removeData(int index) {
        String sql = "delete from " + TABLE_NAME + " where id = " + index + ";";
        db.execSQL(sql);
    }

    // 데이터 전체 검색_today
    public ArrayList<Dateinfo> selectAll_today(String date) {
        String selectsql = "SELECT * FROM " + TABLE_NAME
                + " WHERE " + COLUMN_NAME_START_TIME
                + " LIKE '" + date + "%';";
        Cursor results = db.rawQuery(selectsql, null);
        Log.e("selectsql", selectsql);
        results.moveToFirst();
        ArrayList<Dateinfo> infos = new ArrayList<>();
        //Log.e("test", results.getString(1));
        while (!results.isAfterLast()) {
            Dateinfo info = new Dateinfo(results.getString(1),
                    results.getString(2),
                    results.getString(3),
                    results.getInt(4),
                    results.getString(5),
                    results.getString(6),
                    results.getInt(7),
                    results.getInt(8),
                    results.getInt(9),
                    results.getString(10));
            infos.add(info);
            results.moveToNext();
        }
        results.close();
        return infos;
    }
}