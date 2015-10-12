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
import com.medowhill.jaemin.runaway.view.StarCollectionView;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Copyright 2015. Hong Jaemin
 * All rights reserved.
 */

public class GameActivity extends Activity {

    public static final int GAME_FINISH = 0, ACTIVITY_FINISH = 1, GAME_START = 2, GAME_RESTART = 4, COLLECT_STAR = 5;

    final int[] ABILITY_BUTTON_ID = new int[]{R.id.game_abilityButton1, R.id.game_abilityButton2, R.id.game_abilityButton3, R.id.game_abilityButton4};
    final int[] BUTTON_ID = new int[]{R.id.game_button_restart, R.id.game_button_reselectAbilities, R.id.game_button_selectStage, R.id.game_button_goToMainMenu, R.id.game_button_options};

    final int BUTTON_DELAY = 50;

    private GameView gameView;
    private DirectionControl directionControl;
    private AbilityButton[] abilityButtons;
    private ImageView buttonPause, buttonResume;
    private Button[] buttons;
    private StarCollectionView starCollectionView;

    private Animation[] animationShowMenu, animationHideMenu;
    private Animation animationButtonDisappear, animationButtonAppear;
    private boolean animatingMenu = false, showing = true, replay = false;

    private int stageNum;
    private int abilityNum;
    private int[] abilities;
    private byte[] abilityLevel;
    private boolean[] starCollectionInitial;

    private Handler gameHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        overridePendingTransition(0, 0);

        gameView = (GameView) findViewById(R.id.game_gameView);
        directionControl = (DirectionControl) findViewById(R.id.game_directionControl);
        buttonPause = (ImageView) findViewById(R.id.game_button_pause);
        buttonResume = (ImageView) findViewById(R.id.game_button_resume);
        starCollectionView = (StarCollectionView) findViewById(R.id.game_starCollection);
        buttons = new Button[BUTTON_ID.length];

        for (int i = 0; i < buttons.length; i++)
            buttons[i] = (Button) findViewById(BUTTON_ID[i]);
        abilityButtons = new AbilityButton[ABILITY_BUTTON_ID.length];
        for (int i = 0; i < abilityButtons.length; i++)
            abilityButtons[i] = (AbilityButton) findViewById(ABILITY_BUTTON_ID[i]);

        gameHandler = new GameHandler(this);

        animationShowMenu = new Animation[buttons.length];
        animationHideMenu = new Animation[buttons.length];
        for (int i = 0; i < animationShowMenu.length; i++) {
            final int k = i;
            animationShowMenu[k] = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.game_showmenubutton);
            animationShowMenu[k].setStartOffset(BUTTON_DELAY * k);
            animationShowMenu[k].setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    buttons[k].setVisibility(View.VISIBLE);
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
            animationHideMenu[k].setStartOffset(BUTTON_DELAY * k);
            animationHideMenu[k].setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    buttons[k].setVisibility(View.INVISIBLE);
                    if (k == buttons.length - 1) {
                        if (replay) {
                            replay = false;
                        } else {
                            gameView.setPause(false);
                            setGameControlVisibility(true);
                        }
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
                    pause();
                    replay = true;
                    gameView.setPause(false);
                    gameView.restartGame();
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

        gameView.setGameHandler(gameHandler);
        gameView.setDirectionControl(directionControl);
        gameView.setAbilityButtons(abilityButtons);
        gameView.setStarCollectionView(starCollectionView);

        Intent intent = getIntent();
        stageNum = intent.getIntExtra("stage", 1);
        abilities = intent.getIntArrayExtra("Ability");
        starCollectionInitial = getStarCollection();

        readyGame();

        setGameControlVisibility(false);
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

    private Ability getAbility(int n) {
        if (abilityLevel == null) {
            try {
                FileInputStream fileInputStream = openFileInput("abilityLevel");
                abilityLevel = new byte[fileInputStream.available()];
                fileInputStream.read(abilityLevel);
                fileInputStream.close();
            } catch (IOException e) {
                abilityLevel = new byte[10];
                try {
                    FileOutputStream fileOutputStream = openFileOutput("abilityLevel", MODE_PRIVATE);
                    fileOutputStream.write(abilityLevel);
                    fileOutputStream.flush();
                    fileOutputStream.close();
                } catch (IOException e1) {
                }
            }
        }

        switch (n) {
            case 0:
                return new Dash(abilityLevel[0]);
            case 1:
                return new Teleportation(abilityLevel[1]);
            case 2:
                return new Hiding(abilityLevel[2], true);
            case 3:
                return new Protection(abilityLevel[3]);
            case 4:
                return new Shadow(abilityLevel[4]);
            case 5:
                return new Illusion(abilityLevel[5]);
            case 6:
                return new ShockWave(abilityLevel[6], abilityLevel[7], true);
            default:
                return new DistortionField(abilityLevel[8], abilityLevel[9]);
        }
    }

    private void readyGame() {
        Stage stage = new Stage(this, stageNum);
        ArrayList<Ability> playerAbilities = stage.player.getAbilities();
        for (int i = 0; i < abilities.length; i++)
            if (abilities[i] != -1)
                playerAbilities.add(getAbility(i * 2 + abilities[i]));
        abilityNum = playerAbilities.size();

        for (int i = 0; i < abilityButtons.length; i++) {
            if (playerAbilities.size() > i)
                abilityButtons[i].setIconResourceID(playerAbilities.get(i).iconResourceID);
            abilityButtons[i].clearCool();
            abilityButtons[i].clearClick();
        }

        starCollectionView.initialize(starCollectionInitial);

        gameView.setStage(stage);
        gameView.setStarCollection(starCollectionInitial);
        gameView.startGame();
    }

    private void pause() {
        if (!animatingMenu && gameView.isPlaying()) {
            animatingMenu = true;
            if (gameView.getPause()) {
                showing = false;
                buttonResume.startAnimation(animationButtonDisappear);
                for (int i = 0; i < buttons.length; i++)
                    buttons[i].startAnimation(animationHideMenu[i]);
            } else {
                showing = true;
                gameView.setPause(true);
                buttonPause.startAnimation(animationButtonDisappear);
                setGameControlVisibility(false);
                for (int i = 0; i < buttons.length; i++)
                    buttons[i].startAnimation(animationShowMenu[i]);
            }
        }
    }

    void gameHandle(int what) {
        switch (what) {
            case GAME_FINISH:
                setGameControlVisibility(false);
                break;
            case ACTIVITY_FINISH:
                boolean[] starCollection = gameView.getStarCollection();
                setStarCollection(starCollection);

                int initialCount = 0, finalCount = 0;
                for (int i = 0; i < starCollection.length; i++) {
                    if (starCollectionInitial[i])
                        initialCount++;
                    if (starCollection[i])
                        finalCount++;
                }

                Intent intent = new Intent();
                intent.putExtra("result", GameReadyActivity.RESULT_NEXT);
                intent.putExtra("starCollectionInitial", initialCount);
                intent.putExtra("starCollectionFinal", finalCount);
                setResult(RESULT_OK, intent);
                finish();
                break;
            case GAME_RESTART:
                readyGame();
                break;
            case GAME_START:
                setGameControlVisibility(true);
                break;
            case COLLECT_STAR:
                starCollectionView.invalidate();
                break;
        }
    }

    void setGameControlVisibility(boolean visible) {
        if (visible) {
            directionControl.setVisibility(View.VISIBLE);
            for (int i = 0; i < abilityNum; i++)
                abilityButtons[i].setVisible(true);
            starCollectionView.setVisible(true);
            buttonPause.setVisibility(View.VISIBLE);
        } else {
            directionControl.setVisibility(View.INVISIBLE);
            for (int i = 0; i < abilityNum; i++)
                abilityButtons[i].setVisible(false);
            starCollectionView.setVisible(false);
            buttonPause.setVisibility(View.INVISIBLE);
        }
    }

    boolean[] getStarCollection() {
        byte[] arr;
        try {
            FileInputStream fileInputStream = openFileInput("starCollection" + stageNum);
            arr = new byte[fileInputStream.available()];
            fileInputStream.read(arr);
            fileInputStream.close();
        } catch (IOException e) {
            arr = new byte[1];
            try {
                FileOutputStream fileOutputStream = openFileOutput("starCollection" + stageNum, MODE_PRIVATE);
                fileOutputStream.write(arr);
                fileOutputStream.flush();
                fileOutputStream.close();
            } catch (IOException e1) {
            }
        }

        int star = arr[0];
        boolean[] stars = new boolean[3];
        for (int i = 0; i < stars.length; i++) {
            stars[i] = star % 2 != 0;
            star /= 2;
        }
        return stars;
    }

    void setStarCollection(boolean[] starCollection) {
        byte sum = 0;
        for (int i = starCollection.length - 1; i >= 0; i--) {
            sum *= 2;
            sum += (starCollection[i]) ? 1 : 0;
        }
        byte[] arr = new byte[]{sum};
        try {
            FileOutputStream fileOutputStream = openFileOutput("starCollection" + stageNum, MODE_PRIVATE);
            fileOutputStream.write(arr);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (IOException e1) {
        }
    }
}

class GameHandler extends Handler {
    GameActivity gameActivity;

    public GameHandler(GameActivity gameActivity) {
        this.gameActivity = gameActivity;
    }

    @Override
    public void handleMessage(Message msg) {
        gameActivity.gameHandle(msg.what);
    }
}