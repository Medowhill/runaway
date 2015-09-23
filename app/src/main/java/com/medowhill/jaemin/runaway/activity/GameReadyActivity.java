package com.medowhill.jaemin.runaway.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.medowhill.jaemin.runaway.R;

/**
 * Copyright 2015. Hong Jaemin
 * All rights reserved.
 */

public class GameReadyActivity extends Activity {

    public static final int RESULT_RESELECT = 0, RESULT_NEXT = 1, RESULT_STAGE = 3, RESULT_MAIN = 2;

    final int REQUEST_CODE = 0;

    Button button;

    Button button1, button2, button3, button4;

    int ability1 = 0, ability2 = 0, ability3 = 0, ability4 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameready);

        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        button = (Button) findViewById(R.id.gameReady_button);
        button1 = (Button) findViewById(R.id.gameReady_button_ability1);
        button2 = (Button) findViewById(R.id.gameReady_button_ability2);
        button3 = (Button) findViewById(R.id.gameReady_button_ability3);
        button4 = (Button) findViewById(R.id.gameReady_button_ability4);

        Intent intent = getIntent();
        final int stage = intent.getIntExtra("stage", 1);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), GameActivity.class);
                intent.putExtra("Ability", new int[]{ability1, ability2, ability3, ability4});
                intent.putExtra("stage", stage);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ability1++;
                ability1 %= 2;
                if (ability1 % 2 == 0)
                    button1.setText(R.string.abilityDashName);
                else
                    button1.setText(R.string.abilityTeleportationName);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ability2++;
                ability2 %= 2;
                if (ability2 % 2 == 0)
                    button2.setText(R.string.abilityHidingName);
                else
                    button2.setText(R.string.abilityProtectionName);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ability3++;
                ability3 %= 2;
                if (ability3 % 2 == 0)
                    button3.setText(R.string.abilityShadowName);
                else
                    button3.setText(R.string.abilityIllusionName);
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ability4++;
                ability4 %= 2;
                if (ability4 % 2 == 0)
                    button4.setText(R.string.abilityShockWaveName);
                else
                    button4.setText(R.string.abilityDistortionFieldName);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            Intent intent = new Intent();
            switch (data.getIntExtra("result", RESULT_RESELECT)) {
                case RESULT_NEXT:
                    intent.putExtra("result", StageSelectActivity.RESULT_NEXT);
                    setResult(RESULT_OK, intent);
                    finish();
                    break;
                case RESULT_STAGE:
                    intent.putExtra("result", StageSelectActivity.RESULT_FIN);
                    setResult(RESULT_OK, intent);
                    finish();
                    break;
                case RESULT_MAIN:
                    intent.putExtra("result", StageSelectActivity.RESULT_MAIN);
                    setResult(RESULT_OK, intent);
                    finish();
                    break;
            }
        }
    }
}
