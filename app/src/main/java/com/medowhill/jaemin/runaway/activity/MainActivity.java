package com.medowhill.jaemin.runaway.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.medowhill.jaemin.runaway.R;

/**
 * Created by Jaemin on 2015-09-16.
 */
public class MainActivity extends Activity {

    public static final int SELECT_WORLD = 0, LAST_STAGE = 1;

    Button buttonWorld, buttonLast, buttonPlayer, buttonEnemy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonWorld = (Button) findViewById(R.id.main_button_selectWorld);
        buttonLast = (Button) findViewById(R.id.main_button_toLastStage);
        buttonPlayer = (Button) findViewById(R.id.main_button_player);
        buttonEnemy = (Button) findViewById(R.id.main_button_enemies);

        buttonWorld.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), WorldSelectActivity.class);
                intent.putExtra("type", SELECT_WORLD);
                startActivity(intent);
            }
        });

        buttonLast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), WorldSelectActivity.class);
                intent.putExtra("type", LAST_STAGE);
                startActivity(intent);
            }
        });

        buttonPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        buttonEnemy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
