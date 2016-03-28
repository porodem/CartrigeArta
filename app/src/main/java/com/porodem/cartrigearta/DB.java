package com.porodem.cartrigearta;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by porod on 28.03.2016.
 */
public class DB {
    public static final String LOG_TAG = "myLogs";

    private static final String DB_NAME ="mydb";
    private static final int DB_VERSION = 1;
    private static final String DB_TABLE = "mytab";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_MODEL = "model";
    public static final String COLUMN_MARK = "mark";
    public static final String COLUMN_PROBLEM = "problem";
    public static final String COLUMN_FIX = "fix";
    public static final String COLUMN_COST = "cost";
    public static final String COLUMN_USER = "user";
    public static final String COLUMN_DATE = "date";

    private static final String DB_CREATE =
            "create table " + DB_TABLE + "(" +
                    COLUMN_ID + " integer primary key autoincrement, " +
                    COLUMN_MODEL + " text, " +
                    COLUMN_MARK + " text, " +
                    COLUMN_PROBLEM + " text, " +
                    COLUMN_FIX + " text, " +
                    COLUMN_COST + " text, " +
                    COLUMN_USER + " text, " +
                    COLUMN_DATE + " text" +
                    ");";
    private final Context mCtx;


    public DB(Context ctx) {
        mCtx = ctx;
    }

    private DBHelper mDBHelper;
    private SQLiteDatabase mDB;

    //open connection
    public void open() {
        mDBHelper = new DBHelper(mCtx, DB_NAME, null, DB_VERSION);
        mDB = mDBHelper.getWritableDatabase();
    }

    //close connection
    public void close() {
        if (mDBHelper != null) mDBHelper.close();
    }

    //take all data from DB_TABLE
    public Cursor getAllData() {
        return mDB.query(DB_TABLE, null, null, null, null, null, null, null);
    }

    // ----- Class for create and handle DB -----

    private class DBHelper extends SQLiteOpenHelper {
        public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DB_CREATE);

            ContentValues cv = new ContentValues();
            for (int i =1; i<5; i++) {
                cv.put(COLUMN_MODEL, "H82A" + i);
                cv.put(COLUMN_MARK, "K" + i);
                db.insert(DB_TABLE, null, cv);
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
