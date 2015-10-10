package com.medowhill.jaemin.runaway.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.medowhill.jaemin.runaway.R;
import com.medowhill.jaemin.runaway.view.EnemyPreView;
import com.medowhill.jaemin.runaway.view.FadeView;
import com.medowhill.jaemin.runaway.view.StarCollectionView;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Copyright 2015. Hong Jaemin
 * All rights reserved.
 */

public class GameReadyActivity extends Activity {

    public static final int RESULT_RESELECT = 0, RESULT_NEXT = 1, RESULT_STAGE = 3, RESULT_MAIN = 2;

    final int REQUEST_CODE_GAME = 0;

    Button buttonStart;
    ImageButton buttonAbility1, buttonAbility2, buttonAbility3, buttonAbility4;
    ImageButton buttonReplay, buttonStage, buttonNext;
    TextView textView;
    LinearLayout linearLayoutReady, linearLayoutResult;
    EnemyPreView enemyPreView;
    FadeView fadeView;
    StarCollectionView starCollectionView;

    Handler fadingHandler, enemyInfoHandler, gameResultHandler;

    SharedPreferences sharedPreferences;

    int ability1 = -1, ability2 = -1, ability3 = -1, ability4 = -1;
    int stage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameready);

        overridePendingTransition(R.anim.gameready_activitystart, 0);

        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

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
        fadeView = (FadeView) findViewById(R.id.gameReady_fade);
        starCollectionView = (StarCollectionView) findViewById(R.id.gameReady_starCollection);

        sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);

        fadingHandler = new FadingHandler(this);
        enemyInfoHandler = new EnemyInfoHandler(this);
        gameResultHandler = new GameResultHandler(this);

        Intent intent = getIntent();
        stage = intent.getIntExtra("stage", 1);

        textView.setText(textView.getText().toString() + " " + stage);

        enemyPreView.setStage(stage);
        enemyPreView.setEnemyInfoHandler(enemyInfoHandler);

        starCollectionView.setVisible(true);

        final byte[] abilityLevel = getAbilityLevel();
        if (abilityLevel[0] != 0) {
            buttonAbility1.setBackgroundDrawable(getResources().getDrawable(R.drawable.ability_icon_dash));
            ability1 = 0;
        }
        if (abilityLevel[2] != 0) {
            buttonAbility2.setBackgroundDrawable(getResources().getDrawable(R.drawable.ability_icon_hiding));
            ability2 = 0;
        }
        if (abilityLevel[4] != 0) {
            buttonAbility3.setBackgroundDrawable(getResources().getDrawable(R.drawable.ability_icon_shadow));
            ability3 = 0;
        }
        if (abilityLevel[6] != 0) {
            buttonAbility4.setBackgroundDrawable(getResources().getDrawable(R.drawable.ability_icon_shockwave));
            ability4 = 0;
        }

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int duration = getResources().getInteger(R.integer.gameReadyFadeOutDuration);
                fadeView.fadeOut(duration);
                fadingHandler.sendEmptyMessageDelayed(0, duration);
            }
        });

        buttonAbility1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (abilityLevel[0] * abilityLevel[1] != 0) {
                    ability1++;
                    ability1 %= 2;
                    if (ability1 % 2 == 0)
                        buttonAbility1.setBackgroundDrawable(getResources().getDrawable(R.drawable.ability_icon_dash));
                    else
                        buttonAbility1.setBackgroundDrawable(getResources().getDrawable(R.drawable.ability_icon_teleportation));
                }
            }
        });

        buttonAbility2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (abilityLevel[2] * abilityLevel[3] != 0) {
                    ability2++;
                    ability2 %= 2;
                    if (ability2 % 2 == 0)
                        buttonAbility2.setBackgroundDrawable(getResources().getDrawable(R.drawable.ability_icon_hiding));
                    else
                        buttonAbility2.setBackgroundDrawable(getResources().getDrawable(R.drawable.ability_icon_protection));
                }
            }
        });

        buttonAbility3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (abilityLevel[4] * abilityLevel[5] != 0) {
                    ability3++;
                    ability3 %= 2;
                    if (ability3 % 2 == 0)
                        buttonAbility3.setBackgroundDrawable(getResources().getDrawable(R.drawable.ability_icon_shadow));
                    else
                        buttonAbility3.setBackgroundDrawable(getResources().getDrawable(R.drawable.ability_icon_illusion));
                }
            }
        });

        buttonAbility4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (abilityLevel[6] * abilityLevel[8] != 0) {
                    ability4++;
                    ability4 %= 2;
                    if (ability4 % 2 == 0)
                        buttonAbility4.setBackgroundDrawable(getResources().getDrawable(R.drawable.ability_icon_shockwave));
                    else
                        buttonAbility4.setBackgroundDrawable(getResources().getDrawable(R.drawable.ability_icon_distortionfield));
                }
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
                intent.putExtra("next", true);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        fadeView.initialize();
        if (requestCode == REQUEST_CODE_GAME && resultCode == RESULT_OK) {
            Intent intent = new Intent();
            switch (data.getIntExtra("result", RESULT_RESELECT)) {
                case RESULT_NEXT:
                    linearLayoutReady.setVisibility(View.INVISIBLE);
                    linearLayoutResult.setVisibility(View.VISIBLE);

                    int previousStage = sharedPreferences.getInt("stage", 1);
                    if (previousStage < stage + 1) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt("stage", stage + 1);
                        editor.apply();
                    }

                    int starInitial = data.getIntExtra("starCollectionInitial", 0);
                    for (int i = 0; i < starInitial; i++)
                        starCollectionView.setStarCollect(i, true);
                    starCollectionView.invalidate();

                    int starFinal = data.getIntExtra("starCollectionFinal", 0);
                    if (starInitial < starFinal) {
                        Message message = new Message();
                        message.arg1 = starFinal;
                        message.arg2 = starInitial;
                        gameResultHandler.sendMessageDelayed(message, getResources().getInteger(R.integer.gameReadyStarShowDelay));
                    }
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

    byte[] getAbilityLevel() {
        byte[] arr;
        try {
            FileInputStream fileInputStream = openFileInput("abilityLevel");
            arr = new byte[fileInputStream.available()];
            fileInputStream.read(arr);
            fileInputStream.close();
        } catch (IOException e) {
            arr = new byte[10];
            try {
                FileOutputStream fileOutputStream = openFileOutput("abilityLevel", MODE_PRIVATE);
                fileOutputStream.write(arr);
                fileOutputStream.flush();
                fileOutputStream.close();
            } catch (IOException e1) {
            }
        }
        return arr;
    }

    void startGame() {
        Intent intent = new Intent(getApplicationContext(), GameActivity.class);
        intent.putExtra("Ability", new int[]{ability1, ability2, ability3, ability4});
        intent.putExtra("stage", stage);
        startActivityForResult(intent, REQUEST_CODE_GAME);
    }

    void showEnemyInfo(char type) {
        Intent intent = new Intent(getApplicationContext(), EnemyInformationActivity.class);
        intent.putExtra("type", type);
        startActivity(intent);
    }

    void showStar(int finalStar, int current) {
        starCollectionView.setStarCollect(current, true);
        starCollectionView.invalidate();
        current++;
        if (current < finalStar) {
            Message message = new Message();
            message.arg1 = finalStar;
            message.arg2 = current;
            gameResultHandler.sendMessageDelayed(message, getResources().getInteger(R.integer.gameReadyStarShowDelay));
        }
    }
}

class FadingHandler extends Handler {

    GameReadyActivity gameReadyActivity;

    public FadingHandler(GameReadyActivity gameReadyActivity) {
        this.gameReadyActivity = gameReadyActivity;
    }

    @Override
    public void handleMessage(Message msg) {
        gameReadyActivity.startGame();
    }

}

class EnemyInfoHandler extends Handler {
    GameReadyActivity gameReadyActivity;

    public EnemyInfoHandler(GameReadyActivity gameReadyActivity) {
        this.gameReadyActivity = gameReadyActivity;
    }

    @Override
    public void handleMessage(Message msg) {
        gameReadyActivity.showEnemyInfo((char) msg.what);
    }
}

class GameResultHandler extends Handler {
    GameReadyActivity gameReadyActivity;

    public GameResultHandler(GameReadyActivity gameReadyActivity) {
        this.gameReadyActivity = gameReadyActivity;
    }

    @Override
    public void handleMessage(Message msg) {
        gameReadyActivity.showStar(msg.arg1, msg.arg2);
    }
}