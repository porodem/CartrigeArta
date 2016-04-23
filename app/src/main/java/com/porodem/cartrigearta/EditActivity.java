package com.porodem.cartrigearta;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by porod on 10.04.2016.
 */
public class EditActivity extends MainActivity {

    EditText etModel;
    EditText etMark;
    EditText etProblem;
    EditText etFix;
    EditText etCost;
    EditText etUser;
    EditText etDate;
    EditText etStatus;

    String cartID;


    AlertDialog levelDialog;

    //dialog choose status variants
    final CharSequence[] items = {"Используется","Готов","Пустой","На заправке"};

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        etModel = (EditText)findViewById(R.id.etModel);
        etMark = (EditText)findViewById(R.id.etMark);
        etProblem = (EditText)findViewById(R.id.etProblem);
        etFix = (EditText)findViewById(R.id.etFix);
        etCost = (EditText)findViewById(R.id.etCost);
        etUser = (EditText)findViewById(R.id.etUser);
        etDate = (EditText)findViewById(R.id.etDate);
        etStatus = (EditText)findViewById(R.id.etStatus);

        Intent intentEdit = getIntent();
        String intModel = intentEdit.getStringExtra("tvModel");
        String intMark = intentEdit.getStringExtra("tvMark");
        String intProblem = intentEdit.getStringExtra("tvProblem");
        String intFix = intentEdit.getStringExtra("tvFix");
        String intCost = intentEdit.getStringExtra("tvCost");
        String intUser = intentEdit.getStringExtra("tvUser");
        String intDate = intentEdit.getStringExtra("tvDate");
        String intStatus = intentEdit.getStringExtra("tvStatus");
        cartID = intentEdit.getStringExtra("ID");
        etModel.setText(intModel);
        etMark.setText(intMark);
        etProblem.setText(intProblem);
        etFix.setText(intFix);
        etCost.setText(intCost);
        etUser.setText(intUser);
        etDate.setText(intDate);
        etStatus.setText(intStatus);



        // datePicker for Date editText
        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(EditActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        // Status Picker Dialog
        etStatus.setOnClickListener(new View.OnClickListener() {
            @Override
        public void onClick(View v) {
                // Creating and Building the Dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(EditActivity.this);
                builder.setTitle(R.string.choose_status);
                builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        switch(item)
                        {
                            case 0:
                                etStatus.setText("use");
                                break;
                            case 1:
                                etStatus.setText("full");

                                break;
                            case 2:
                                etStatus.setText("empty");
                                break;
                            case 3:
                                etStatus.setText("service");
                                break;

                        }
                        levelDialog.dismiss();
                    }
                });
                levelDialog = builder.create();
                levelDialog.show();
            }
        });
    }

    // datePicker for Date editText
    Calendar myCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLable();
        }
    };
    private void updateLable() {
        String myFormat = "dd/MM/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);

        etDate.setText(sdf.format(myCalendar.getTime()));
    }


    public void ButtonApplyEdit(View view) {
        String modelRec = etModel.getText().toString();
        String markRec = etMark.getText().toString();
        String problemRec = etProblem.getText().toString();
        String fixRec = etFix.getText().toString();
        String costRec = etCost.getText().toString();
        String userRec = etUser.getText().toString();
        String dateRec = etDate.getText().toString();
        String statusRec = etStatus.getText().toString();

        db.editRec(modelRec, markRec, problemRec, fixRec, costRec, userRec, dateRec, statusRec, cartID);
        getLoaderManager().getLoader(0).forceLoad();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
