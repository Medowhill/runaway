package com.medowhill.jaemin.runaway.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.medowhill.jaemin.runaway.R;
import com.medowhill.jaemin.runaway.ability.Ability;
import com.medowhill.jaemin.runaway.ability.Dash;
import com.medowhill.jaemin.runaway.ability.Hiding;
import com.medowhill.jaemin.runaway.ability.Protection;
import com.medowhill.jaemin.runaway.ability.Teleportation;
import com.medowhill.jaemin.runaway.object.GameObject;
import com.medowhill.jaemin.runaway.object.Stage;
import com.medowhill.jaemin.runaway.view.AbilityButton;
import com.medowhill.jaemin.runaway.view.DirectionControl;
import com.medowhill.jaemin.runaway.view.GameView;

import java.util.ArrayList;

public class GameActivity extends Activity {

    final int[] ABILITY_BUTTON_ID = new int[]{R.id.abilityButton1, R.id.abilityButton2, R.id.abilityButton3, R.id.abilityButton4};

    GameView gameView;
    DirectionControl directionControl;
    AbilityButton[] abilityButtons;

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
        gameView.setDirectionControl(directionControl);
        gameView.setAbilityButtons(abilityButtons);

        Stage stage = new Stage(this, 1);
        ArrayList<Ability> playerAbilities = stage.getPlayer().getAbilities();
        playerAbilities.add(new Dash(1));
        playerAbilities.add(new Teleportation(1));
        playerAbilities.add(new Hiding(1));
        playerAbilities.add(new Protection(1));

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
