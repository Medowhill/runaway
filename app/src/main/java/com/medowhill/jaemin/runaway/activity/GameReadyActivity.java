package com.medowhill.jaemin.runaway.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.medowhill.jaemin.runaway.R;

/**
 * Copyright 2015. Hong Jaemin
 * All rights reserved.
 */

public class GameReadyActivity extends Activity {

    Button button;

    Button button1, button2, button3, button4;

    int ability1 = 0, ability2 = 0, ability3 = 0, ability4 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameready);

        button = (Button) findViewById(R.id.gameReady_button);
        button1 = (Button) findViewById(R.id.gameReady_button_ability1);
        button2 = (Button) findViewById(R.id.gameReady_button_ability2);
        button3 = (Button) findViewById(R.id.gameReady_button_ability3);
        button4 = (Button) findViewById(R.id.gameReady_button_ability4);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), GameActivity.class);
                intent.putExtra("Ability", new int[]{ability1, ability2, ability3, ability4});
                startActivity(intent);
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
}
