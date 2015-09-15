package com.medowhill.jaemin.runaway.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.medowhill.jaemin.runaway.R;
import com.medowhill.jaemin.runaway.ability.Ability;
import com.medowhill.jaemin.runaway.object.GameObject;

/**
 * Created by Jaemin on 2015-09-10.
 */
public class StageSelectActivity extends Activity {

    Button[] buttons = new Button[5];

    int[] buttonId = new int[]{R.id.stageSelectButton1, R.id.stageSelectButton2, R.id.stageSelectButton3, R.id.stageSelectButton4, R.id.stageSelectButton5};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stageselect);

        Ability.setContext(this);
        GameObject.setContext(this);

        for (int i = 0; i < buttonId.length; i++) {
            buttons[i] = (Button) findViewById(buttonId[i]);
            buttons[i].setText(i + 1 + "");

            final int k = i;
            buttons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), GameReadyActivity.class);
                    intent.putExtra("stage", k + 1);
                    startActivity(intent);
                }
            });
        }

    }
}
