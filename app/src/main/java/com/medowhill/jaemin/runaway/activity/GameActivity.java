package com.medowhill.jaemin.runaway.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.medowhill.jaemin.runaway.R;
import com.medowhill.jaemin.runaway.ability.Ability;
import com.medowhill.jaemin.runaway.ability.Dash;
import com.medowhill.jaemin.runaway.ability.DistortionField;
import com.medowhill.jaemin.runaway.ability.Hiding;
import com.medowhill.jaemin.runaway.ability.Illusion;
import com.medowhill.jaemin.runaway.ability.Protection;
import com.medowhill.jaemin.runaway.ability.Shadow;
import com.medowhill.jaemin.runaway.ability.ShockWave;
import com.medowhill.jaemin.runaway.ability.Teleportation;
import com.medowhill.jaemin.runaway.object.Stage;
import com.medowhill.jaemin.runaway.view.AbilityButton;
import com.medowhill.jaemin.runaway.view.DirectionControl;
import com.medowhill.jaemin.runaway.view.GameView;

import java.util.ArrayList;

/**
 * Copyright 2015. Hong Jaemin
 * All rights reserved.
 */

public class GameActivity extends Activity {

    public static final int GAME_OVER = 0, ACTIVITY_FIN = 1, GAME_RESTART = 2;

    final int[] ABILITY_BUTTON_ID = new int[]{R.id.game_abilityButton1, R.id.game_abilityButton2, R.id.game_abilityButton3, R.id.game_abilityButton4};
    final int[] BUTTON_ID = new int[]{R.id.game_button_restart, R.id.game_button_reselectAbilities, R.id.game_button_selectStage, R.id.game_button_goToMainMenu, R.id.game_button_options};

    final int BUTTON_DELAY = 50;

    private Ability[][] abilityArray = new Ability[][]{{new Dash(1, true), new Teleportation(1, true)},
            {new Hiding(1, true), new Protection(1)}, {new Shadow(1), new Illusion(1)}, {new ShockWave(1, 1), new DistortionField(1, 1)}};

    private GameView gameView;
    private DirectionControl directionControl;
    private AbilityButton[] abilityButtons;
    private ImageView buttonPause, buttonResume;
    private Button[] buttons;
    private LinearLayout linearLayoutAbilityButton;
    private Animation[] animationShowMenu, animationHideMenu;

    private Animation animationButtonDisappear, animationButtonAppear;
    private boolean animatingMenu = false, showing = true;

    private int stageNum;
    private int[] abilities = new int[]{};

    private Handler gameOverHandler, showButtonHandler, hideButtonHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        gameView = (GameView) findViewById(R.id.game_gameView);
        directionControl = (DirectionControl) findViewById(R.id.game_directionControl);
        buttonPause = (ImageView) findViewById(R.id.game_button_pause);
        buttonResume = (ImageView) findViewById(R.id.game_button_resume);
        linearLayoutAbilityButton = (LinearLayout) findViewById(R.id.game_linearLayout_abilityButton);
        buttons = new Button[BUTTON_ID.length];

        for (int i = 0; i < buttons.length; i++)
            buttons[i] = (Button) findViewById(BUTTON_ID[i]);
        abilityButtons = new AbilityButton[ABILITY_BUTTON_ID.length];
        for (int i = 0; i < abilityButtons.length; i++)
            abilityButtons[i] = (AbilityButton) findViewById(ABILITY_BUTTON_ID[i]);

        gameOverHandler = new GameOverHandler(this);
        showButtonHandler = new ShowButtonHandler(this);
        hideButtonHandler = new HideButtonHandler(this);

        animationShowMenu = new Animation[buttons.length];
        animationHideMenu = new Animation[buttons.length];
        for (int i = 0; i < animationShowMenu.length; i++) {
            final int k = i;
            animationShowMenu[k] = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.game_showmenubutton);
            animationShowMenu[k].setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    buttons[k].setVisibility(View.VISIBLE);
                    showButtonHandler.sendEmptyMessageDelayed(k + 1, BUTTON_DELAY);
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    if (k == buttons.length - 1)
                        animatingMenu = false;
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
            animationHideMenu[k] = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.game_hidemenubutton);
            animationHideMenu[k].setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    hideButtonHandler.sendEmptyMessageDelayed(k + 1, BUTTON_DELAY);
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    buttons[k].setVisibility(View.INVISIBLE);
                    if (k == buttons.length - 1) {
                        gameView.setPause(false);
                        directionControl.setVisibility(View.VISIBLE);
                        linearLayoutAbilityButton.setVisibility(View.VISIBLE);
                        animatingMenu = false;
                    }
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
        }

        animationButtonDisappear = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.game_buttondisappear);
        animationButtonAppear = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.game_buttonappear);
        animationButtonDisappear.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (showing) {
                    buttonPause.setVisibility(View.INVISIBLE);
                    buttonResume.setVisibility(View.VISIBLE);
                    buttonResume.startAnimation(animationButtonAppear);
                } else {
                    buttonPause.setVisibility(View.VISIBLE);
                    buttonResume.setVisibility(View.INVISIBLE);
                    buttonPause.startAnimation(animationButtonAppear);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        buttonPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pause();
            }
        });

        buttonResume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pause();
            }
        });

        buttons[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!animatingMenu) {
                    readyGame();
                    pause();
                }
            }
        });

        buttons[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("result", GameReadyActivity.RESULT_RESELECT);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        buttons[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("result", GameReadyActivity.RESULT_STAGE);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        buttons[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("result", GameReadyActivity.RESULT_MAIN);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        buttons[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        gameView.setGameOverHandler(gameOverHandler);
        gameView.setDirectionControl(directionControl);
        gameView.setAbilityButtons(abilityButtons);

        Intent intent = getIntent();
        stageNum = intent.getIntExtra("stage", 1);
        abilities = intent.getIntArrayExtra("Ability");

        readyGame();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        pause();
        return false;
    }

    @Override
    public void onBackPressed() {
        pause();
    }

    private void readyGame() {
        Stage stage = new Stage(this, stageNum);
        ArrayList<Ability> playerAbilities = stage.getPlayer().getAbilities();
        for (int i = 0; i < abilities.length; i++)
            if (abilities[i] != -1)
                playerAbilities.add(abilityArray[i][abilities[i]]);

        for (int i = 0; i < abilityButtons.length; i++) {
            if (playerAbilities.size() > i)
                abilityButtons[i].setIconResourceID(playerAbilities.get(i).iconResourceID);
            else
                abilityButtons[i].setVisibility(View.INVISIBLE);
        }

        directionControl.setVisibility(View.VISIBLE);
        buttonPause.setVisibility(View.VISIBLE);
        linearLayoutAbilityButton.setVisibility(View.VISIBLE);

        gameView.setStage(stage);
        gameView.startGame();
    }

    private void pause() {
        if (!animatingMenu && gameView.isPlaying()) {
            animatingMenu = true;
            if (gameView.getPause()) {
                showing = false;
                buttons[0].startAnimation(animationHideMenu[0]);
                buttonResume.startAnimation(animationButtonDisappear);
            } else {
                showing = true;
                gameView.setPause(true);
                directionControl.setVisibility(View.INVISIBLE);
                linearLayoutAbilityButton.setVisibility(View.INVISIBLE);
                buttons[0].startAnimation(animationShowMenu[0]);
                buttonPause.startAnimation(animationButtonDisappear);
            }
        }
    }

    void gameOver(int what) {
        switch (what) {
            case GAME_OVER:
                directionControl.setVisibility(View.INVISIBLE);
                buttonPause.setVisibility(View.INVISIBLE);
                linearLayoutAbilityButton.setVisibility(View.INVISIBLE);
                break;
            case ACTIVITY_FIN:
                Intent intent = new Intent();
                intent.putExtra("result", GameReadyActivity.RESULT_NEXT);
                setResult(RESULT_OK, intent);
                finish();
                break;
            case GAME_RESTART:
                readyGame();
                break;
        }
    }

    void showButton(int i) {
        if (i < buttons.length)
            buttons[i].startAnimation(animationShowMenu[i]);
    }

    void hideButton(int i) {
        if (i < buttons.length)
            buttons[i].startAnimation(animationHideMenu[i]);
    }
}

class GameOverHandler extends Handler {
    GameActivity gameActivity;

    public GameOverHandler(GameActivity gameActivity) {
        this.gameActivity = gameActivity;
    }

    @Override
    public void handleMessage(Message msg) {
        gameActivity.gameOver(msg.what);
    }
}

class ShowButtonHandler extends Handler {
    GameActivity gameActivity;

    public ShowButtonHandler(GameActivity gameActivity) {
        this.gameActivity = gameActivity;
    }

    @Override
    public void handleMessage(Message msg) {
        gameActivity.showButton(msg.what);
    }
}

class HideButtonHandler extends Handler {
    GameActivity gameActivity;

    public HideButtonHandler(GameActivity gameActivity) {
        this.gameActivity = gameActivity;
    }

    @Override
    public void handleMessage(Message msg) {
        gameActivity.hideButton(msg.what);
    }
}