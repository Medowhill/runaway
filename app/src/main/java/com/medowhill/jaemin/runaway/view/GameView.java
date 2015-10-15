package com.medowhill.jaemin.runaway.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.medowhill.jaemin.runaway.R;
import com.medowhill.jaemin.runaway.ability.Ability;
import com.medowhill.jaemin.runaway.activity.GameActivity;
import com.medowhill.jaemin.runaway.buff.Buff;
import com.medowhill.jaemin.runaway.object.Bullet;
import com.medowhill.jaemin.runaway.object.Enemy;
import com.medowhill.jaemin.runaway.object.Field;
import com.medowhill.jaemin.runaway.object.Player;
import com.medowhill.jaemin.runaway.object.Stage;
import com.medowhill.jaemin.runaway.object.Star;

import java.util.ArrayList;

/**
 * Copyright 2015. Hong Jaemin
 * All rights reserved.
 */

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private final int WIDTH, HEIGHT;

    private final int FRAME, FRAME_LENGTH, FADING_FRAME;

    private float ratio = 0;

    private Stage stage;

    private Paint paintNonArea, paintArea, paintGameOver;

    private DirectionControl directionControl;
    private AbilityButton[] abilityButtons;
    private StarCollectionView starCollectionView;
    private Handler gameHandler;

    private SurfaceThread surfaceThread;

    // Constructor
    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);

        WIDTH = getResources().getInteger(R.integer.gameWidth);
        HEIGHT = getResources().getInteger(R.integer.gameHeight);

        FRAME = context.getResources().getInteger(R.integer.frame);
        FRAME_LENGTH = 1000 / FRAME;
        FADING_FRAME = context.getResources().getInteger(R.integer.gameOverFrame);

        paintNonArea = new Paint();
        paintNonArea.setColor(getResources().getColor(R.color.gameViewNonArea));

        paintArea = new Paint();
        paintArea.setColor(getResources().getColor(R.color.gameViewArea));

        paintGameOver = new Paint();

        getHolder().addCallback(this);
    }

    // Override Method

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        surfaceThread = new SurfaceThread();
        surfaceThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        float widthRatio = 1.f * width / WIDTH;
        float heightRatio = 1.f * height / HEIGHT;
        ratio = Math.min(widthRatio, heightRatio);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;

        surfaceThread.run = false;
        while (retry) {
            try {
                surfaceThread.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }
    }

    // Getter & Setter

    public void setGameHandler(Handler gameHandler) {
        this.gameHandler = gameHandler;
    }

    public void setDirectionControl(DirectionControl directionControl) {
        this.directionControl = directionControl;
    }

    public void setAbilityButtons(AbilityButton[] abilityButtons) {
        this.abilityButtons = abilityButtons;
    }

    public void setStarCollectionView(StarCollectionView starCollectionView) {
        this.starCollectionView = starCollectionView;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public boolean[] getStarCollection() {
        boolean[] starCollection = new boolean[3];
        for (int i = 0; i < stage.stars.size(); i++)
            starCollection[i] = stage.stars.get(i).isCollect();
        return starCollection;
    }

    public void setStarCollection(boolean[] starCollection) {
        for (int i = 0; i < stage.stars.size(); i++)
            stage.stars.get(i).setCollect(starCollection[i]);
    }

    public boolean getPause() {
        return surfaceThread.pause;
    }

    public void setPause(boolean pause) {
        if (isPlaying())
            surfaceThread.pause = pause;
    }

    public boolean isPlaying() {
        return !surfaceThread.gameFinish && !surfaceThread.gamePrepare;
    }

    // Game Play method

    public void restartGame() {
        surfaceThread.gameFinish = true;
    }

    public void startGame() {
        surfaceThread.gameStart = true;
        surfaceThread.gamePrepare = true;
        surfaceThread.lastTime = System.currentTimeMillis();
    }

    // Surface Thread
    private class SurfaceThread extends Thread {

        SurfaceHolder surfaceHolder;
        boolean run = true, gameStart = false, pause = false;
        boolean gamePrepare = false, gameFinish = false, gameSuccess = false, gameFail = false;
        long lastTime;
        int fadingFrame;

        public SurfaceThread() {
            surfaceHolder = getHolder();
        }

        public void run() {
            while (run) {
                if (gameStart) {
                    long currentTime = System.currentTimeMillis();

                    if (currentTime - lastTime >= FRAME_LENGTH) {
                        lastTime = currentTime;

                        Canvas canvas = null;
                        try {
                            canvas = surfaceHolder.lockCanvas(null);
                            if (canvas != null) {

                                canvas.drawColor(Color.BLACK);

                                canvas.scale(ratio, ratio);

                                canvas.drawRect(0, 0, WIDTH, HEIGHT, paintNonArea);

                                Player player = stage.player;

                                if (!pause && !gamePrepare && !gameFinish) {
                                    player.setDirection(directionControl.getDirection());

                                    for (int i = 0; i < player.getAbilities().size(); i++) {
                                        AbilityButton abilityButton = abilityButtons[i];
                                        Ability ability = player.getAbilities().get(i);

                                        if (abilityButton.isClicked()) {
                                            if (player.isAbilityUsable() && !ability.isWaiting())
                                                ability.use(player);
                                            abilityButton.clearClick();
                                        }

                                        if (ability.isWaiting()) {
                                            ability.decreaseRemaining(1);
                                            Message message = new Message();
                                            message.arg1 = ability.getRemainWaitingFrame();
                                            message.arg2 = ability.WAITING_FRAME;
                                            abilityButton.getDrawHandler().sendMessage(message);
                                        }
                                    }

                                    player.controlBuff();
                                    player.getIllusion().controlBuff();

                                    player.move();
                                    player.getIllusion().move();
                                }

                                if (!pause && !gamePrepare && !gameFinish && player.touch(stage.finish)) {
                                    gameFinish = true;
                                    gameSuccess = true;
                                    gameHandler.sendEmptyMessage(GameActivity.GAME_FINISH);
                                }

                                ArrayList<Enemy> enemies = stage.enemies;
                                if (!pause && !gamePrepare && (!gameFinish || gameFail)) {
                                    for (Enemy enemy : enemies) {
                                        enemy.setDirection();
                                        enemy.useAbility();
                                        enemy.decreaseAbilityWaiting();
                                        enemy.controlBuff();
                                        enemy.move();
                                        if (player.isMortal() && !gameFinish && player.touch(enemy)) {
                                            gameFinish = true;
                                            gameFail = true;
                                            gameHandler.sendEmptyMessage(GameActivity.GAME_FINISH);
                                        }
                                    }
                                }

                                ArrayList<Star> stars = stage.stars;
                                if (!pause && !gamePrepare && !gameFinish) {
                                    for (int i = 0; i < stars.size(); i++) {
                                        Star star = stars.get(i);
                                        if (!star.isCollect() && player.touch(star)) {
                                            star.setCollect(true);
                                            starCollectionView.setStarCollect(i, true);
                                            gameHandler.sendEmptyMessage(GameActivity.COLLECT_STAR);
                                        }
                                    }
                                }

                                ArrayList<Bullet> bullets = stage.bullets;
                                if (!pause && !gamePrepare && (!gameFinish || gameFail)) {
                                    for (int i = 0; i < bullets.size(); i++) {
                                        Bullet bullet = bullets.get(i);
                                        bullet.move();
                                        if (player.touch(bullet)) {
                                            for (Buff buff : bullets.get(i).getBuff())
                                                player.addBuff(buff);
                                            bullets.remove(i);
                                            i--;
                                        } else if (bullet.isTouchWall()) {
                                            bullets.remove(i);
                                            i--;
                                        }
                                    }
                                }

                                ArrayList<Field> fields = stage.fields;
                                if (!pause && !gamePrepare) {
                                    for (int i = 0; i < fields.size(); i++) {
                                        Field field = fields.get(i);
                                        field.resize();
                                        if (field.isFinish()) {
                                            fields.remove(i);
                                            i--;
                                        }
                                    }
                                }

                                if (!pause)
                                    stage.finish.framePass();

                                float dx = WIDTH / 4, dy = HEIGHT / 4;
                                if (stage.getxMax() - WIDTH / 4 < player.getX())
                                    dx = 3 * WIDTH / 4 - stage.getxMax();
                                else if (WIDTH / 4 < player.getX())
                                    dx = WIDTH / 2 - player.getX();
                                if (stage.getyMax() - HEIGHT / 4 < player.getY())
                                    dy = 3 * HEIGHT / 4 - stage.getyMax();
                                else if (HEIGHT / 4 < player.getY())
                                    dy = HEIGHT / 2 - player.getY();

                                canvas.translate(dx, dy);

                                canvas.drawPath(stage.area, paintArea);

                                stage.finish.draw(canvas);
                                if (player.isUsingIllusion())
                                    player.getIllusion().draw(canvas);
                                player.draw(canvas);
                                for (Enemy enemy : enemies)
                                    enemy.draw(canvas);
                                for (Star star : stars)
                                    star.draw(canvas);
                                for (Bullet bullet : bullets)
                                    bullet.draw(canvas);
                                for (Field field : fields)
                                    field.draw(canvas);

                                if (gamePrepare) {
                                    canvas.translate(-dx, -dy);

                                    paintGameOver.setColor(Color.argb(255 - 255 * fadingFrame / FADING_FRAME, 0, 0, 0));
                                    canvas.drawRect(0, 0, WIDTH, HEIGHT, paintGameOver);
                                    fadingFrame++;
                                    if (fadingFrame == FADING_FRAME) {
                                        gamePrepare = false;
                                        fadingFrame = 0;
                                        gameHandler.sendEmptyMessage(GameActivity.GAME_START);
                                    }
                                } else if (gameFinish) {
                                    canvas.translate(-dx, -dy);

                                    paintGameOver.setColor(Color.argb(255 * fadingFrame / FADING_FRAME, 0, 0, 0));
                                    canvas.drawRect(0, 0, WIDTH, HEIGHT, paintGameOver);
                                    fadingFrame++;
                                    if (fadingFrame == FADING_FRAME) {
                                        Message message = new Message();
                                        if (gameSuccess) {
                                            message.what = GameActivity.ACTIVITY_FINISH;
                                            run = false;
                                        } else
                                            message.what = GameActivity.GAME_RESTART;
                                        gameFail = false;
                                        gameSuccess = false;
                                        gameFinish = false;
                                        gameStart = false;
                                        fadingFrame = 0;
                                        gameHandler.sendMessage(message);
                                    }
                                } else if (pause) {
                                    canvas.translate(-dx, -dy);
                                    paintGameOver.setColor(Color.argb(127, 0, 0, 0));
                                    canvas.drawRect(0, 0, WIDTH, HEIGHT, paintGameOver);
                                }

                                // Log
                                long time = System.currentTimeMillis() - lastTime;
                                if (time > FRAME_LENGTH / 2)
                                    Log.w("RunAway", time + "ms");
                            }
                        } finally {
                            if (canvas != null)
                                surfaceHolder.unlockCanvasAndPost(canvas);
                        }
                    }
                }
            }
        }
    }
}
