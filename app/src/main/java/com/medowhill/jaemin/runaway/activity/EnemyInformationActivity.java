package com.medowhill.jaemin.runaway.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.medowhill.jaemin.runaway.R;
import com.medowhill.jaemin.runaway.object.Enemy;
import com.medowhill.jaemin.runaway.view.EnemyInformationView;

/**
 * Created by Jaemin on 2015-10-10.
 */
public class EnemyInformationActivity extends Activity {

    TextView textViewName, textViewSize, textViewSpeed, textViewSight, textViewAbility;
    EnemyInformationView enemyInformationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enemyinformation);

        textViewName = (TextView) findViewById(R.id.enemyInfo_textView_name);
        textViewSize = (TextView) findViewById(R.id.enemyInfo_textView_size);
        textViewSpeed = (TextView) findViewById(R.id.enemyInfo_textView_speed);
        textViewSight = (TextView) findViewById(R.id.enemyInfo_textView_sight);
        textViewAbility = (TextView) findViewById(R.id.enemyInfo_textView_ability);
        enemyInformationView = (EnemyInformationView) findViewById(R.id.enemyInfo_enemyInfoView);

        char type = getIntent().getCharExtra("type", 'e');
        enemyInformationView.setEnemyType(type);
        enemyInformationView.start();

        Enemy enemy = enemyInformationView.getEnemy();
        textViewName.setText(enemy.name);
        textViewSize.setText(enemy.getSize() + "");
        textViewSpeed.setText((int) enemy.getSpeed() + "");
        textViewSight.setText(enemy.getSight() + "");
        textViewAbility.setText(enemy.getAbilityName());
    }
}
