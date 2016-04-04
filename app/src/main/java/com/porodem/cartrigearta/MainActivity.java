package com.porodem.cartrigearta;

import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Loader;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

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

        //columns
        String[] from = new String[] {DB.COLUMN_MODEL, DB.COLUMN_MARK};
        int[] to = new int[] {R.id.tvModel, R.id.tvMark};

        //create adapter and config list
        scAdapter = new SimpleCursorAdapter(this, R.layout.item, null, from, to, 0);
        lvData = (ListView)findViewById(R.id.lvData);
        lvData.setAdapter(scAdapter);

        //create loader for data reading
        getLoaderManager().initLoader(0, null, this);
    }

    //Button "ADD"
    public void onButtonAdd(View view) {
        //Toast.makeText(this, "ADD Button: ", Toast.LENGTH_SHORT).show();
        LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.prompt, null);
        //create alert Dialog
        AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(context);
        //config prompt.xml for our AlertDialog
        mDialogBuilder.setView(promptsView);
        //
        final EditText userInput = (EditText)promptsView.findViewById(R.id.input_cartridge);
        //config message in dialog
        mDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                String cartridgeMark = userInput.getText().toString();
                                db.addRec(cartridgeMark);
                                //get new cursor with data
                                getLoaderManager().getLoader(0).forceLoad();
                                //Toast.makeText(getBaseContext(), "message: " , Toast.LENGTH_LONG).show();
                            }
                        })
                .setNegativeButton("Cancle",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        //create dialog
        AlertDialog alertDialog = mDialogBuilder.create();
        //show it
        alertDialog.show();
    }


    //cursor logging
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

    //close connect when exit
    protected void onDestroy() {
        super.onDestroy();
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

}
