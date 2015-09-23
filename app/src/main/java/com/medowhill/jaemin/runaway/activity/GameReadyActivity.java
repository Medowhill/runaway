package com.medowhill.jaemin.runaway.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.medowhill.jaemin.runaway.R;

/**
 * Copyright 2015. Hong Jaemin
 * All rights reserved.
 */

public class GameReadyActivity extends Activity {

    public static final int RESULT_RESELECT = 0, RESULT_NEXT = 1, RESULT_STAGE = 3, RESULT_MAIN = 2;

    final int REQUEST_CODE = 0;

    Button button;
    ImageButton button1, button2, button3, button4;
    TextView textView;

    int ability1 = 0, ability2 = 0, ability3 = 0, ability4 = 0;
    int stage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameready);

        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        button = (Button) findViewById(R.id.gameReady_button_start);
        button1 = (ImageButton) findViewById(R.id.gameReady_button_ability1);
        button2 = (ImageButton) findViewById(R.id.gameReady_button_ability2);
        button3 = (ImageButton) findViewById(R.id.gameReady_button_ability3);
        button4 = (ImageButton) findViewById(R.id.gameReady_button_ability4);
        textView = (TextView) findViewById(R.id.gameReady_textView_stage);

        Intent intent = getIntent();
        stage = intent.getIntExtra("stage", 1);

        textView.setText(textView.getText().toString() + " " + stage);

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
                    button1.setBackgroundDrawable(getResources().getDrawable(R.drawable.ability_icon_dash));
                else
                    button1.setBackgroundDrawable(getResources().getDrawable(R.drawable.ability_icon_teleportation));
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ability2++;
                ability2 %= 2;
                if (ability2 % 2 == 0)
                    button2.setBackgroundDrawable(getResources().getDrawable(R.drawable.ability_icon_hiding));
                else
                    button2.setBackgroundDrawable(getResources().getDrawable(R.drawable.ability_icon_protection));
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ability3++;
                ability3 %= 2;
                if (ability3 % 2 == 0)
                    button3.setBackgroundDrawable(getResources().getDrawable(R.drawable.ability_icon_shadow));
                else
                    button3.setBackgroundDrawable(getResources().getDrawable(R.drawable.ability_icon_illusion));
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ability4++;
                ability4 %= 2;
                if (ability4 % 2 == 0)
                    button4.setBackgroundDrawable(getResources().getDrawable(R.drawable.ability_icon_shockwave));
                else
                    button4.setBackgroundDrawable(getResources().getDrawable(R.drawable.ability_icon_distortionfield));
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
                    intent.putExtra("stage", stage);
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
