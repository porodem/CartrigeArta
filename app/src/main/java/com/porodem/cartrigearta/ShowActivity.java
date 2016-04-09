package com.porodem.cartrigearta;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by porod on 09.04.2016.
 */
public class ShowActivity extends AppCompatActivity {

    TextView tvModel;
    TextView tvMark;
    TextView tvProblem;
    TextView tvFix;
    TextView tvCost;
    TextView tvUser;
    TextView tvDate;

    String etID;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        tvModel = (TextView)findViewById(R.id.tvModel);
        tvMark = (TextView)findViewById(R.id.tvMark);
        tvProblem = (TextView)findViewById(R.id.tvProblem);
        tvFix = (TextView)findViewById(R.id.tvFix);
        tvCost = (TextView)findViewById(R.id.tvCost);
        tvUser = (TextView)findViewById(R.id.tvUser);
        tvDate = (TextView)findViewById(R.id.tvDate);

        Intent intentShow = getIntent();
        String intModel = intentShow.getStringExtra("tvModel");
        String intMark = intentShow.getStringExtra("tvMark");
        String intProblem = intentShow.getStringExtra("tvProblem");
        String intFix = intentShow.getStringExtra("tvFix");
        String intCost = intentShow.getStringExtra("tvCost");
        String intUser = intentShow.getStringExtra("tvUser");
        String intDate = intentShow.getStringExtra("tvDate");

        tvModel.setText(intModel);
        tvMark.setText(intMark);
        tvProblem.setText(intProblem);
        tvFix.setText(intFix);
        tvCost.setText(intCost);
        tvUser.setText(intUser);
        tvDate.setText(intDate);


    }
}
