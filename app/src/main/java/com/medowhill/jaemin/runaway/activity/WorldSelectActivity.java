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
public class WorldSelectActivity extends Activity {

    public static final int SELECT_STAGE = 0, LAST_STAGE = 1;

    final int REQUEST_CODE = 0;

    Button button1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worldselect);

        button1 = (Button) findViewById(R.id.worldSelect_button1);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StageSelectActivity.class);
                intent.putExtra("type", SELECT_STAGE);
                intent.putExtra("world", 1);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        Intent intent = getIntent();
        int type = intent.getIntExtra("type", MainActivity.SELECT_WORLD);

        if (type == MainActivity.LAST_STAGE) {
            intent.putExtra("type", LAST_STAGE);
            intent.putExtra("world", 1);
            startActivityForResult(intent, REQUEST_CODE);
        }
    }
}
