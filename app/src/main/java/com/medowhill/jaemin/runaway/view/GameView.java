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
import com.medowhill.jaemin.runaway.buff.Buff;
import com.medowhill.jaemin.runaway.object.Enemy;
import com.medowhill.jaemin.runaway.object.Player;
import com.medowhill.jaemin.runaway.object.Stage;

import java.util.ArrayList;

/**
 * Created by Jaemin on 2015-09-01.
 */
public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private final int WIDTH, HEIGHT, BASE_SIZE;

    private final int FRAME, FRAME_LENGTH, GAME_OVER_FRAME;

    private float ratio = 0;

    private Stage stage;

    private Paint paintNonArea, paintArea, paintFinish, paintGameOver;

    private DirectionControl directionControl;
    private AbilityButton[] abilityButtons;
    private Handler gameOverHandler;

    private SurfaceThread surfaceThread;

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);

        WIDTH = getResources().getInteger(R.integer.gameWidth);
        HEIGHT = getResources().getInteger(R.integer.gameHeight);
        BASE_SIZE = getResources().getInteger(R.integer.baseSize);

        FRAME = context.getResources().getInteger(R.integer.frame);
        FRAME_LENGTH = 1000 / FRAME;
        GAME_OVER_FRAME = context.getResources().getInteger(R.integer.gameOverFrame);

        paintNonArea = new Paint();
        paintNonArea.setColor(getResources().getColor(R.color.gameViewNonArea));

        paintArea = new Paint();
        paintArea.setColor(getResources().getColor(R.color.gameViewArea));

        paintFinish = new Paint();
        paintFinish.setColor(getResources().getColor(R.color.gameViewFinish));

        paintGameOver = new Paint();

        getHolder().addCallback(this);

        surfaceThread = new SurfaceThread();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        float widthRatio = 1.f * w / WIDTH;
        float heightRatio = 1.f * h / HEIGHT;
        ratio = Math.min(widthRatio, heightRatio);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        surfaceThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
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

    public void setGameOverHandler(Handler gameOverHandler) {
        this.gameOverHandler = gameOverHandler;
    }

    public void setDirectionControl(DirectionControl directionControl) {
        this.directionControl = directionControl;
    }

    public void setAbilityButtons(AbilityButton[] abilityButtons) {
        this.abilityButtons = abilityButtons;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void startGame() {
        surfaceThread.gameStart = true;
        surfaceThread.lastTime = System.currentTimeMillis();
    }

    private class SurfaceThread extends Thread {

        SurfaceHolder surfaceHolder;
        boolean run = true, gameStart = false, gameOver = false, gameClear = false;
        long lastTime;
        int gameOverFrame;

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

                                Player player = stage.getPlayer();

                                if (!gameOver && !gameClear) {
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
                                            Message message = new Message();
                                            message.arg1 = ability.getRemainWaitingFrame();
                                            message.arg2 = ability.WAITING_FRAME;
                                            abilityButton.getDrawHandler().sendMessage(message);
                                            ability.decreaseRemaining(1);
                                        }
                                    }

                                    for (int i = 0; i < player.getBuffs().size(); i++) {
                                        Buff buff = player.getBuffs().get(i);

                                        if (!buff.isStart()) {
                                            buff.startBuff();
                                            buff.setStart(true);
                                        } else if (buff.isEnd()) {
                                            buff.endBuff();
                                            player.getBuffs().remove(i);
                                            i--;
                                        } else {
                                            buff.duringBuff();
                                        }
                                        buff.framePass();
                                    }

                                    player.move();
                                    if (player.isUsingIllusion())
                                        player.getIllusion().move();
                                }

                                if (!gameClear && player.getX() == stage.getxFinish() && player.getY() == stage.getyFinish()) {
                                    gameClear = true;
                                    Message message = new Message();
                                    message.what = 0;
                                    gameOverHandler.sendMessage(message);
                                }

                                ArrayList<Enemy> enemies = stage.enemies;
                                if (!gameClear) {
                                    for (Enemy enemy : enemies) {
                                        enemy.setDirection();
                                        enemy.move();
                                        if (player.isMortal() && !gameOver && player.touch(enemy)) {
                                            gameOver = true;
                                            Message message = new Message();
                                            message.what = 0;
                                            gameOverHandler.sendMessage(message);
                                        }
                                    }
                                }

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

                                canvas.drawCircle(stage.getxFinish(), stage.getyFinish(),
                                        BASE_SIZE * getResources().getInteger(R.integer.playerSize), paintFinish);

                                if (player.isUsingIllusion())
                                    player.getIllusion().draw(canvas);
                                player.draw(canvas);
                                for (Enemy enemy : enemies)
                                    enemy.draw(canvas);

                                if (gameOver || gameClear) {
                                    canvas.translate(-dx, -dy);

                                    paintGameOver.setColor(Color.argb(255 * gameOverFrame / GAME_OVER_FRAME, 0, 0, 0));
                                    canvas.drawRect(0, 0, WIDTH, HEIGHT, paintGameOver);
                                    gameOverFrame++;
                                    if (gameOverFrame == GAME_OVER_FRAME) {
                                        run = false;
                                        Message message = new Message();
                                        message.what = 1;
                                        gameOverHandler.sendMessage(message);
                                    }
                                }

                                // Log
                                long time = System.currentTimeMillis() - lastTime;
                                if (time > FRAME_LENGTH)
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
