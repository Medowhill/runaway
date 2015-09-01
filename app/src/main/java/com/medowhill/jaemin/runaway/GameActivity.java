package com.medowhill.jaemin.runaway;

import android.app.Activity;
import android.os.Bundle;

import com.medowhill.jaemin.runaway.object.Stage;
import com.medowhill.jaemin.runaway.view.DirectionControl;
import com.medowhill.jaemin.runaway.view.GameView;

public class GameActivity extends Activity {

    GameView gameView;
    DirectionControl directionControl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        gameView = (GameView) findViewById(R.id.game_gameView);
        directionControl = (DirectionControl) findViewById(R.id.game_directionControl);

        gameView.setDirectionControl(directionControl);
        gameView.setStage(new Stage(this, 1));
        gameView.startGame();
    }

}
