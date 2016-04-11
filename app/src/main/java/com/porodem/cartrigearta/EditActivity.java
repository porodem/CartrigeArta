package com.porodem.cartrigearta;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

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

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        etModel = (EditText)findViewById(R.id.etModel);
        etMark = (EditText)findViewById(R.id.etMark);
        etProblem = (EditText)findViewById(R.id.etProblem);
        etFix = (EditText)findViewById(R.id.etFix);
        etCost = (EditText)findViewById(R.id.etCost);
        etUser = (EditText)findViewById(R.id.etUser);
        etDate = (EditText)findViewById(R.id.etUser);
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
