package com.medowhill.jaemin.runaway;

import android.app.Activity;
import android.os.Bundle;

import com.medowhill.jaemin.runaway.object.Player;
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

        Player player = new Player(0, 0, 10, 50, getResources().getColor(R.color.player));

        gameView.setPlayer(player);

        gameView.startGame();
    }

}
