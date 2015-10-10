package com.medowhill.jaemin.runaway.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.medowhill.jaemin.runaway.R;
import com.medowhill.jaemin.runaway.view.EnemyInformationView;

/**
 * Created by Jaemin on 2015-10-10.
 */
public class EnemyInformationActivity extends Activity {

    TextView textViewName;
    EnemyInformationView enemyInformationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enemyinformation);

        textViewName = (TextView) findViewById(R.id.enemyInfo_textView_name);
        enemyInformationView = (EnemyInformationView) findViewById(R.id.enemyInfo_enemyInfoView);

        char type = getIntent().getCharExtra("type", 'e');
        enemyInformationView.setEnemyType(type);
        enemyInformationView.start();
    }
}
