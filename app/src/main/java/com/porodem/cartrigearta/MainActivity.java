package com.porodem.cartrigearta;

import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final String LOG_TAG = "My Logs";

    final Context context = this;

    ListView lvData;
    DB db;
    SimpleCursorAdapter scAdapter;
    Cursor c;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //open DB connections
        db = new DB(this);
        db.open();

        //get cursor
        //cursor = db.getAllData();

        //columns
        String[] from = new String[] {DB.COLUMN_MODEL, DB.COLUMN_MARK};
        int[] to = new int[] {R.id.tvModel, R.id.tvMark};

        //create adapter and config list
        scAdapter = new SimpleCursorAdapter(this, R.layout.item, null, from, to, 0);
        lvData = (ListView)findViewById(R.id.lvData);
        lvData.setAdapter(scAdapter);

        //create loader for data reading
        getLoaderManager().initLoader(0, null, this);

        c = db.getAllData();
        logCursor(c);
        c.close();
    }

    void logCursor(Cursor c) {
        if (c != null) {
            if (c.moveToFirst()) {
                String str;
                do {
                    str = "";
                    for (String cn : c.getColumnNames()) {
                        str = str.concat(cn + " = " + c.getString(c.getColumnIndex(cn)) + "; ");
                    }
                    Log.d(LOG_TAG, str);
                } while (c.moveToNext());
            }
        } else
            Log.d(LOG_TAG, "Cursor is null");
    }



/*
    @Override
    public LoaderManager getLoaderManager() {
        return super.getLoaderManager();
    }
*/
    protected void onDestroy() {
        super.onDestroy();
        //close connect when exit
        db.close();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bndl) {
        return new MyCursorLoader(this, db);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        scAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    static class MyCursorLoader extends CursorLoader {
        DB db;
        public MyCursorLoader(Context context, DB db) {
            super(context);
            this.db = db;
        }
        @Override
        public Cursor loadInBackground() {
            Cursor cursor = db.getAllData();
            try {
                TimeUnit.SECONDS.sleep(1);
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
            return cursor;
        }
    }

    /*
    static class MyCursorLoader extends CursorLoader {
            DB db;
        public MyCursorLoader(Context context, DB db) {
            super(context);
            this.db = db;
        }
        @Override
        public Cursor loadInBackGround() {
            Cursor cursor = db.getAllData();
            try {
                TimeUnit.SECONDS.sleep(1);
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
            return cursor;
        }
    }

*/
}
