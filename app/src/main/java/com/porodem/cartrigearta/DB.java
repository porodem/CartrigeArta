package com.porodem.cartrigearta;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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
    public static final String COLUMN_STATUS = "status";

    private static final String DB_CREATE =
            "create table " + DB_TABLE + "(" +
                    COLUMN_ID + " integer primary key autoincrement, " +
                    COLUMN_MODEL + " text, " +
                    COLUMN_MARK + " text, " +
                    COLUMN_PROBLEM + " text, " +
                    COLUMN_FIX + " text, " +
                    COLUMN_COST + " text, " +
                    COLUMN_USER + " text, " +
                    COLUMN_DATE + " text, " +
                    COLUMN_STATUS + " text" +
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

    //put info to DB_TABLE
    public void addRec(String model) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_MODEL, model);
        long rowID = mDB.insert(DB_TABLE, null, cv);
        Log.d(LOG_TAG, "Added record with id: " + rowID);

    }

    // reading current rec
    public Cartridge getThisData(long id) {
        Log.d(LOG_TAG, "-- getThisData --");
        String selection = "_id = ?";
        String xid = String.valueOf(id);
        String [] selectionAgrs = new String[]{xid};

        Cursor c = mDB.query(DB_TABLE, null, selection, selectionAgrs, null, null, null);
        c.moveToLast();
        int thisData = c.getColumnIndex(COLUMN_ID);
        int thisDataModel = c.getColumnIndex(COLUMN_MODEL);
        int thisDataMark = c.getColumnIndex(COLUMN_MARK);
        int thisDataProblem = c.getColumnIndex(COLUMN_PROBLEM);
        int thisDataFix = c.getColumnIndex(COLUMN_FIX);
        int thisDataCost = c.getColumnIndex(COLUMN_COST);
        int thisDataUser = c.getColumnIndex(COLUMN_USER);
        int thisDataDate = c.getColumnIndex(COLUMN_DATE);
        int thisDataStatus = c.getColumnIndex(COLUMN_STATUS);

        String readyID = c.getString(thisData);
        String readyModel = c.getString(thisDataModel);
        String readyMark = c.getString(thisDataMark);
        String readyProblem = c.getString(thisDataProblem);
        String readyFix = c.getString(thisDataFix);
        String readyCost = c.getString(thisDataCost);
        String readyUser = c.getString(thisDataUser);
        String readyDate = c.getString(thisDataDate);
        String readyStatus = c.getString(thisDataStatus);

        Log.d(LOG_TAG, "--- String readyMark: " + readyMark);
        Cartridge cartridge = new Cartridge();
        cartridge.cID = readyID;
        cartridge.cModel = readyModel;
        cartridge.cMark = readyMark;
        cartridge.cProblem = readyProblem;
        cartridge.cFix = readyFix;
        cartridge.cCost = readyCost;
        cartridge.cUser = readyUser;
        cartridge.cDate = readyDate;
        cartridge.cStatus = readyStatus;

        return cartridge;
    }

    //edit cartridge record
    public void editRec(String model, String mark, String problem, String fix, String cost,
                        String user, String date, String status, String id){
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_MODEL, model);
        cv.put(COLUMN_MARK, mark);
        cv.put(COLUMN_PROBLEM, problem);
        cv.put(COLUMN_FIX, fix);
        cv.put(COLUMN_COST, cost);
        cv.put(COLUMN_USER, user);
        cv.put(COLUMN_DATE, date);
        cv.put(COLUMN_STATUS, status);
        int updCartridge = mDB.update(DB_TABLE, cv, "_id = ?", new String[]{id});
        Log.d(LOG_TAG, "Edited record with id: " + updCartridge);

    }

    //del info from DB_TABLE
    public void delRec(long id) {
        mDB.delete(DB_TABLE, COLUMN_ID + " = " + id, null);
        Log.d(LOG_TAG, "--- Deleted ID : " + id);
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
