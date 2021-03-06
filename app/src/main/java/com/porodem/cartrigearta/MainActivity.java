package com.porodem.cartrigearta;

import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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

    Button showInUse;
    Button showEmpty;
    Button showService;
    Button showReady;

    String lastElementNumInUse;
    String lastElementNumEmpty;
    String lastElementNumService;
    String lastElementNumReady;



    //constants for context menu
    private static final int CM_SHOW = 1;
    private static final int CM_EDIT = 2;
    private static final int CM_DELITE = 3;

    final int DIALOG_DELETE = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //btnAdd = (Button)findViewById(R.id.btnAdd);

        //open DB connections
        db = new DB(this);
        db.open();

        //columns
        String[] from = new String[] {DB.COLUMN_MODEL, DB.COLUMN_MARK, DB.COLUMN_USER};
        int[] to = new int[] {R.id.tvModel, R.id.tvMark, R.id.tvUser};

        //create adapter and config list
        scAdapter = new SimpleCursorAdapter(this, R.layout.item, null, from, to, 0);
        lvData = (ListView)findViewById(R.id.lvData);
        lvData.setAdapter(scAdapter);

        //add context menu to list
        registerForContextMenu(lvData);

        //create loader for data reading
        getLoaderManager().initLoader(0, null, this);

        getAllCounter();

    }

    public void getAllCounter() {
        lastElementNumInUse = db.getItemsCountInUse();
        lastElementNumEmpty = db.getItemsCountEmpty();
        lastElementNumService = db.getItemsCountService();
        lastElementNumReady = db.getItemsCountReady();


        showInUse = (Button)findViewById(R.id.btnShowInUse);
        showEmpty = (Button)findViewById(R.id.btnShowEmpty);
        showService = (Button)findViewById(R.id.btnShowService);
        showReady = (Button)findViewById(R.id.btnShowFull);

        showInUse.setText(lastElementNumInUse);
        showEmpty.setText(lastElementNumEmpty);
        showService.setText(lastElementNumService);
        showReady.setText(lastElementNumReady);
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

    // Context menu     09-04-16
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        menu.add(0, CM_SHOW, 0, R.string.show_record);
        menu.add(0, CM_EDIT, 0, R.string.edit_record);
        menu.add(0, CM_DELITE, 0, R.string.del_record);
    }

    public boolean onContextItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case CM_SHOW:
                if (item.getItemId()==CM_SHOW) {
                    //get from context menu item data
                    AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) item
                            .getMenuInfo();
                    Cartridge thisCart = db.getThisData(acmi.id);

                    String CartID = thisCart.cID;
                    String CartModel = thisCart.cModel;
                    String CartMark = thisCart.cMark;
                    String CartProblem = thisCart.cProblem;
                    String CartFix = thisCart.cFix;
                    String CartCost = thisCart.cCost;
                    String CartUser = thisCart.cUser;
                    String CartDate = thisCart.cDate;
                    String CartStatus = thisCart.cStatus;

                    Intent intentShow = new Intent(this, ShowActivity.class);
                    intentShow.putExtra("ID", CartID);
                    intentShow.putExtra("tvModel", CartModel);
                    intentShow.putExtra("tvMark", CartMark);
                    intentShow.putExtra("tvProblem", CartProblem);
                    intentShow.putExtra("tvFix", CartFix);
                    intentShow.putExtra("tvCost", CartCost);
                    intentShow.putExtra("tvUser", CartUser);
                    intentShow.putExtra("tvDate", CartDate);
                    intentShow.putExtra("tvStatus", CartStatus);
                    startActivity(intentShow);

                }
                break;
            case CM_EDIT:
                if (item.getItemId()==CM_EDIT) {
                    AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) item
                            .getMenuInfo();
                    Cartridge thisCart = db.getThisData(acmi.id);

                    String CartID = thisCart.cID;
                    String CartModel = thisCart.cModel;
                    String CartMark = thisCart.cMark;
                    String CartProblem = thisCart.cProblem;
                    String CartFix = thisCart.cFix;
                    String CartCost = thisCart.cCost;
                    String CartUser = thisCart.cUser;
                    String CartDate = thisCart.cDate;
                    String CartStatus = thisCart.cStatus;

                    Intent intentEdit = new Intent(this, EditActivity.class);
                    intentEdit.putExtra("ID", CartID);
                    intentEdit.putExtra("tvModel", CartModel);
                    intentEdit.putExtra("tvMark", CartMark);
                    intentEdit.putExtra("tvProblem", CartProblem);
                    intentEdit.putExtra("tvFix", CartFix);
                    intentEdit.putExtra("tvCost", CartCost);
                    intentEdit.putExtra("tvUser", CartUser);
                    intentEdit.putExtra("tvDate", CartDate);
                    intentEdit.putExtra("tvStatus",CartStatus);
                    startActivity(intentEdit);
                }
                break;
            case CM_DELITE:
                if (item.getItemId()==CM_DELITE) {
                    showDialog(DIALOG_DELETE);
                    //get from context menu item data
                    AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) item
                            .getMenuInfo();
                    //extract id off user and delete this user from DB
                    db.delRec(acmi.id);
                    //get new cursor
                    getLoaderManager().getLoader(0).forceLoad();
                    return true;
                }
                break;
        }
        return super.onContextItemSelected(item);
    }

    //close connect when exit
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }

    // ActionBar menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_act_bar, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
//copied from ADD button
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
                                        String cartridgeModel = userInput.getText().toString();
                                        //check for exsist mark
                                        if (db.checkForExsist(cartridgeModel)) {
                                            Log.d(LOG_TAG, "-- no repeated - Mark Added --");
                                            db.addRec(cartridgeModel);
                                        } else {
                                            Log.d(LOG_TAG, "---- mark alredy exist ----");
                                            Toast.makeText(getBaseContext(), "Такой код уже есть!", Toast.LENGTH_LONG).show();
                                        }

                                        //get new cursor with data
                                        getAllCounter();
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
        return true;


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

    //Button Show Full Cartridge
    public void onButtonShowFull(View view) {
        //Temporary use for TEST
        //Intent intent = new Intent(MainActivity.this, TestActivity.class);
        //startActivity(intent);
        db.changeButton("full");
        getLoaderManager().getLoader(0).forceLoad();
    }

    //Button "ADD"
    public void onButtonAdd(View view) {
        db.changeButton("use");
        getLoaderManager().getLoader(0).forceLoad();
    }



    public void onButtonShowEmpty(View view) {
        db.changeButton("empty");
        getLoaderManager().getLoader(0).forceLoad();
    }

    public void onButtonShowService(View view) {
        db.changeButton("service");
        getLoaderManager().getLoader(0).forceLoad();
    }



    //my -----------------------------------TEST Button
    //----------------------------------------------------------------------
    public void test(View view) {
        //String exist = db.checkForExsist();
        Log.d(LOG_TAG, "MyLog: - checkForExsist SHOW: " );
    }



    static class MyCursorLoader extends CursorLoader {
        DB db;
        public MyCursorLoader(Context context, DB db) {
            super(context);
            this.db = db;
        }
        @Override
        public Cursor loadInBackground() {
            Cursor cursor = db.showStatus();
            try {
                TimeUnit.SECONDS.sleep(1);
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
            return cursor;
        }
    }

}
