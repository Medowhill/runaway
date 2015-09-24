package com.medowhill.jaemin.runaway.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.medowhill.jaemin.runaway.R;
import com.medowhill.jaemin.runaway.view.EnemyPreView;

/**
 * Copyright 2015. Hong Jaemin
 * All rights reserved.
 */

public class GameReadyActivity extends Activity {

    public static final int RESULT_RESELECT = 0, RESULT_NEXT = 1, RESULT_STAGE = 3, RESULT_MAIN = 2;

    final int REQUEST_CODE = 0;

    Button buttonStart;
    ImageButton buttonAbility1, buttonAbility2, buttonAbility3, buttonAbility4;
    ImageButton buttonReplay, buttonStage, buttonNext;
    TextView textView;
    LinearLayout linearLayoutReady, linearLayoutResult;
    EnemyPreView enemyPreView;

    int ability1 = 0, ability2 = 0, ability3 = 0, ability4 = 0;
    int stage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameready);

        overridePendingTransition(R.anim.gameready_activitystart, 0);

        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        buttonStart = (Button) findViewById(R.id.gameReady_button_start);
        buttonAbility1 = (ImageButton) findViewById(R.id.gameReady_button_ability1);
        buttonAbility2 = (ImageButton) findViewById(R.id.gameReady_button_ability2);
        buttonAbility3 = (ImageButton) findViewById(R.id.gameReady_button_ability3);
        buttonAbility4 = (ImageButton) findViewById(R.id.gameReady_button_ability4);
        buttonReplay = (ImageButton) findViewById(R.id.gameReady_button_replay);
        buttonStage = (ImageButton) findViewById(R.id.gameReady_button_stage);
        buttonNext = (ImageButton) findViewById(R.id.gameReady_button_next);
        textView = (TextView) findViewById(R.id.gameReady_textView_stage);
        linearLayoutReady = (LinearLayout) findViewById(R.id.gameReady_linearLayout_ready);
        linearLayoutResult = (LinearLayout) findViewById(R.id.gameReady_linearLayout_result);
        enemyPreView = (EnemyPreView) findViewById(R.id.gameReady_enemyPreview);

        Intent intent = getIntent();
        stage = intent.getIntExtra("stage", 1);

        textView.setText(textView.getText().toString() + " " + stage);

        enemyPreView.setStage(stage);

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), GameActivity.class);
                intent.putExtra("Ability", new int[]{ability1, ability2, ability3, ability4});
                intent.putExtra("stage", stage);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        buttonAbility1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ability1++;
                ability1 %= 2;
                if (ability1 % 2 == 0)
                    buttonAbility1.setBackgroundDrawable(getResources().getDrawable(R.drawable.ability_icon_dash));
                else
                    buttonAbility1.setBackgroundDrawable(getResources().getDrawable(R.drawable.ability_icon_teleportation));
            }
        });

        buttonAbility2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ability2++;
                ability2 %= 2;
                if (ability2 % 2 == 0)
                    buttonAbility2.setBackgroundDrawable(getResources().getDrawable(R.drawable.ability_icon_hiding));
                else
                    buttonAbility2.setBackgroundDrawable(getResources().getDrawable(R.drawable.ability_icon_protection));
            }
        });

        buttonAbility3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ability3++;
                ability3 %= 2;
                if (ability3 % 2 == 0)
                    buttonAbility3.setBackgroundDrawable(getResources().getDrawable(R.drawable.ability_icon_shadow));
                else
                    buttonAbility3.setBackgroundDrawable(getResources().getDrawable(R.drawable.ability_icon_illusion));
            }
        });

        buttonAbility4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ability4++;
                ability4 %= 2;
                if (ability4 % 2 == 0)
                    buttonAbility4.setBackgroundDrawable(getResources().getDrawable(R.drawable.ability_icon_shockwave));
                else
                    buttonAbility4.setBackgroundDrawable(getResources().getDrawable(R.drawable.ability_icon_distortionfield));
            }
        });

        buttonReplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayoutReady.setVisibility(View.VISIBLE);
                linearLayoutResult.setVisibility(View.INVISIBLE);
            }
        });

        buttonStage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("result", StageSelectActivity.RESULT_NEXT);
                intent.putExtra("stage", stage);
                intent.putExtra("next", false);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("result", StageSelectActivity.RESULT_NEXT);
                intent.putExtra("stage", stage);
                intent.putExtra("next", true);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            Intent intent = new Intent();
            switch (data.getIntExtra("result", RESULT_RESELECT)) {
                case RESULT_NEXT:
                    linearLayoutReady.setVisibility(View.INVISIBLE);
                    linearLayoutResult.setVisibility(View.VISIBLE);
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

    @Override
    public void finish() {
        super.finish();

        overridePendingTransition(0, R.anim.gameready_activityfinish);
    }
}
