package com.medowhill.jaemin.runaway.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.medowhill.jaemin.runaway.R;
import com.medowhill.jaemin.runaway.ability.Ability;
import com.medowhill.jaemin.runaway.ability.Dash;
import com.medowhill.jaemin.runaway.ability.Hiding;
import com.medowhill.jaemin.runaway.ability.Illusion;
import com.medowhill.jaemin.runaway.ability.Protection;
import com.medowhill.jaemin.runaway.ability.Substitute;
import com.medowhill.jaemin.runaway.ability.Teleportation;
import com.medowhill.jaemin.runaway.object.GameObject;
import com.medowhill.jaemin.runaway.object.Stage;
import com.medowhill.jaemin.runaway.view.AbilityButton;
import com.medowhill.jaemin.runaway.view.DirectionControl;
import com.medowhill.jaemin.runaway.view.GameView;

import java.util.ArrayList;

public class GameActivity extends Activity {

    final int[] ABILITY_BUTTON_ID = new int[]{R.id.abilityButton1, R.id.abilityButton2, R.id.abilityButton3, R.id.abilityButton4};

    Ability[][] abilityArray = new Ability[][]{{new Dash(1), new Teleportation(1)},
            {new Hiding(1), new Protection(1)}, {new Illusion(1), new Substitute(1)}, {}};

    GameView gameView;
    DirectionControl directionControl;
    AbilityButton[] abilityButtons;

    Handler gameOverHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    directionControl.setVisibility(View.GONE);
                    for (int i = 0; i < abilityButtons.length; i++)
                        abilityButtons[i].setVisibility(View.GONE);
                    break;
                case 1:
                    finish();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        gameView = (GameView) findViewById(R.id.game_gameView);
        directionControl = (DirectionControl) findViewById(R.id.game_directionControl);

        abilityButtons = new AbilityButton[4];
        for (int i = 0; i < abilityButtons.length; i++)
            abilityButtons[i] = (AbilityButton) findViewById(ABILITY_BUTTON_ID[i]);

        GameObject.setContext(this);
        gameView.setGameOverHandler(gameOverHandler);
        gameView.setDirectionControl(directionControl);
        gameView.setAbilityButtons(abilityButtons);

        Stage stage = new Stage(this, 1);
        ArrayList<Ability> playerAbilities = stage.getPlayer().getAbilities();
        Intent intent = getIntent();
        int[] abilities = intent.getIntArrayExtra("Ability");
        for (int i = 0; i < abilities.length; i++) {
            if (abilities[i] != -1)
                playerAbilities.add(abilityArray[i][abilities[i]]);
        }

        for (int i = 0; i < abilityButtons.length; i++) {
            if (playerAbilities.size() > i)
                abilityButtons[i].setIconResourceID(playerAbilities.get(i).iconResourceID);
            else
                abilityButtons[i].setVisibility(View.GONE);
        }

        gameView.setStage(stage);
        gameView.startGame();
    }

}
