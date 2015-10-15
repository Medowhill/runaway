package com.medowhill.jaemin.runaway.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.medowhill.jaemin.runaway.R;
import com.medowhill.jaemin.runaway.view.ToWorldSelectButton;

/**
 * Created by Jaemin on 2015-09-16.
 */
public class MainActivity extends Activity {

    ToWorldSelectButton toWorldSelectButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toWorldSelectButton = (ToWorldSelectButton) findViewById(R.id.main_toWorldSelectButton);

        toWorldSelectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StageSelectActivity.class);
                startActivity(intent);
            }
        });
    }
}
