package com.medowhill.jaemin.runaway;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.medowhill.jaemin.runaway.ability.Dash;
import com.medowhill.jaemin.runaway.object.Stage;
import com.medowhill.jaemin.runaway.view.AbilityButton;
import com.medowhill.jaemin.runaway.view.DirectionControl;
import com.medowhill.jaemin.runaway.view.GameView;

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

        abilityButtons[0].setAbility(new Dash(1));
        abilityButtons[1].setVisibility(View.GONE);
        abilityButtons[2].setVisibility(View.GONE);
        abilityButtons[3].setVisibility(View.GONE);

        gameView.setDirectionControl(directionControl);
        gameView.setAbilityButtons(abilityButtons);

        gameView.setStage(new Stage(this, 1));
        gameView.startGame();
    }

}
