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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
    TextView textViewStage, textViewFirst, textViewStar;
    LinearLayout linearLayoutReady, linearLayoutResult;
    EnemyPreView enemyPreView;
    FadeView fadeView;
    StarCollectionView starCollectionView;

    Handler fadingHandler, enemyInfoHandler, gameResultHandler;

    SharedPreferences sharedPreferences;

    int ability1 = -1, ability2 = -1, ability3 = -1, ability4 = -1;
    int stage, world;
    int finalStar = -1;
    int showingTotalStar, finalTotalStar;
    boolean firstClear;
    int delay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameready);

        overridePendingTransition(R.anim.gameready_activitystart, 0);

        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        delay = getResources().getInteger(R.integer.gameReadyStarShowDelay);

        buttonStart = (Button) findViewById(R.id.gameReady_button_start);
        buttonAbility1 = (ImageButton) findViewById(R.id.gameReady_button_ability1);
        buttonAbility2 = (ImageButton) findViewById(R.id.gameReady_button_ability2);
        buttonAbility3 = (ImageButton) findViewById(R.id.gameReady_button_ability3);
        buttonAbility4 = (ImageButton) findViewById(R.id.gameReady_button_ability4);
        buttonReplay = (ImageButton) findViewById(R.id.gameReady_button_replay);
        buttonStage = (ImageButton) findViewById(R.id.gameReady_button_stage);
        buttonNext = (ImageButton) findViewById(R.id.gameReady_button_next);
        textViewStage = (TextView) findViewById(R.id.gameReady_textView_stage);
        textViewFirst = (TextView) findViewById(R.id.gameReady_textView_firstClear);
        textViewStar = (TextView) findViewById(R.id.gameReady_textView_star);
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
        world = intent.getIntExtra("world", 0);

        textViewStage.setText(textViewStage.getText().toString() + " " + stage);

        enemyPreView.setStage(stage);
        enemyPreView.setEnemyInfoHandler(enemyInfoHandler);

        starCollectionView.setVisible(true);

        final byte[] abilityLevel = getAbilityLevel();
        if (abilityLevel[1] != 0) {
            buttonAbility1.setBackgroundDrawable(getResources().getDrawable(R.drawable.ability_icon_dash));
            ability1 = 0;
        }
        if (abilityLevel[3] != 0) {
            buttonAbility2.setBackgroundDrawable(getResources().getDrawable(R.drawable.ability_icon_hiding));
            ability2 = 0;
        }
        if (abilityLevel[5] != 0) {
            buttonAbility3.setBackgroundDrawable(getResources().getDrawable(R.drawable.ability_icon_shadow));
            ability3 = 0;
        }
        if (abilityLevel[7] != 0) {
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
                if (abilityLevel[1] * abilityLevel[2] != 0) {
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
                if (abilityLevel[3] * abilityLevel[4] != 0) {
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
                if (abilityLevel[5] * abilityLevel[6] != 0) {
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
                if (abilityLevel[7] * abilityLevel[9] != 0) {
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
                finalStarView();
                linearLayoutReady.setVisibility(View.VISIBLE);
                linearLayoutResult.setVisibility(View.INVISIBLE);
            }
        });

        buttonStage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalStarView();
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
                finalStarView();
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
                    textViewFirst.setVisibility(View.INVISIBLE);

                    int star = sharedPreferences.getInt("star", 0);
                    textViewStar.setText(star + "");
                    showingTotalStar = star;

                    int previousStage = sharedPreferences.getInt("stage" + world, 1);
                    firstClear = previousStage == stage;
                    if (firstClear) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt("stage" + world, stage + 1);
                        editor.apply();

                        int[] open = getResources().getIntArray(R.array.abilityOpenStage);
                        for (int i = 0; i < open.length; i++) {
                            if (open[i] == world * 25 + stage) {
                                byte[] abilityLevel = null;
                                try {
                                    FileInputStream fileInputStream = openFileInput("abilityLevel");
                                    abilityLevel = new byte[fileInputStream.available()];
                                    fileInputStream.read(abilityLevel);
                                    fileInputStream.close();
                                } catch (IOException e) {
                                    abilityLevel = new byte[9];
                                    abilityLevel[0] = 1;
                                }

                                abilityLevel[i] = 1;
                                try {
                                    FileOutputStream fileOutputStream = openFileOutput("abilityLevel", MODE_PRIVATE);
                                    fileOutputStream.write(abilityLevel);
                                    fileOutputStream.flush();
                                    fileOutputStream.close();
                                } catch (IOException e1) {
                                }

                                switch (i) {
                                    case 1:
                                        buttonAbility1.setBackgroundDrawable(getResources().getDrawable(R.drawable.ability_icon_dash));
                                        ability1 = 0;
                                        break;
                                    case 3:
                                        buttonAbility2.setBackgroundDrawable(getResources().getDrawable(R.drawable.ability_icon_hiding));
                                        ability2 = 0;
                                        break;
                                    case 5:
                                        buttonAbility3.setBackgroundDrawable(getResources().getDrawable(R.drawable.ability_icon_shadow));
                                        ability3 = 0;
                                        break;
                                    case 7:
                                        buttonAbility4.setBackgroundDrawable(getResources().getDrawable(R.drawable.ability_icon_shockwave));
                                        ability4 = 0;
                                        break;
                                }

                                Toast.makeText(getApplicationContext(), getResources().getStringArray(R.array.abilityName)[i], Toast.LENGTH_LONG).show();
                                break;
                            }
                        }
                        star += 2;
                    }

                    int starInitial = data.getIntExtra("starCollectionInitial", 0);
                    for (int i = 0; i < starCollectionView.size(); i++)
                        starCollectionView.setStarCollect(i, false);
                    for (int i = 0; i < starInitial; i++)
                        starCollectionView.setStarCollect(i, true);
                    starCollectionView.invalidate();

                    finalStar = data.getIntExtra("starCollectionFinal", 0);
                    if (starInitial < finalStar) {
                        star += finalStar - starInitial;
                        gameResultHandler.sendEmptyMessageDelayed(starInitial, delay);
                    } else {
                        finalStar = -1;
                        if (firstClear)
                            gameResultHandler.sendEmptyMessageDelayed(-1, delay);
                    }

                    finalTotalStar = star;
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("star", star);
                    editor.apply();
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
            arr = new byte[11];
            arr[0] = 1;
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
        intent.putExtra("world", world);
        startActivityForResult(intent, REQUEST_CODE_GAME);
    }

    void showEnemyInfo(char type) {
        Intent intent = new Intent(getApplicationContext(), EnemyInformationActivity.class);
        intent.putExtra("type", type);
        startActivity(intent);
    }

    void showStar(int current) {
        if (current == -1) {
            textViewFirst.setVisibility(View.VISIBLE);
            showingTotalStar += 2;
            textViewStar.setText(showingTotalStar + "");
            return;
        }

        starCollectionView.setStarCollect(current, true);
        starCollectionView.invalidate();
        current++;
        showingTotalStar++;
        textViewStar.setText(showingTotalStar + "");

        if (current < finalStar)
            gameResultHandler.sendEmptyMessageDelayed(current, delay);
        else if (current == finalStar && firstClear)
            gameResultHandler.sendEmptyMessageDelayed(-1, delay);
    }

    void finalStarView() {
        for (int i = 0; i < finalStar; i++)
            starCollectionView.setStarCollect(i, true);
        starCollectionView.invalidate();
        if (firstClear)
            textViewFirst.setVisibility(View.VISIBLE);
        textViewStar.setText(finalTotalStar + "");
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
        gameReadyActivity.showStar(msg.what);
    }
}