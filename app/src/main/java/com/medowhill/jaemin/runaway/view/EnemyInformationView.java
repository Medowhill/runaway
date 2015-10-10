package com.medowhill.jaemin.runaway.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.medowhill.jaemin.runaway.R;
import com.medowhill.jaemin.runaway.buff.Buff;
import com.medowhill.jaemin.runaway.object.Bullet;
import com.medowhill.jaemin.runaway.object.Enemy;
import com.medowhill.jaemin.runaway.object.Field;
import com.medowhill.jaemin.runaway.object.Player;
import com.medowhill.jaemin.runaway.object.Stage;

import java.util.ArrayList;

/**
 * Created by Jaemin on 2015-10-08.
 */
public class EnemyInformationView extends SurfaceView implements SurfaceHolder.Callback {

    private final int FRAME, FRAME_LENGTH, FADING_FRAME;

    private int WIDTH, HEIGHT;

    private float ratio = 0;

    private Paint paint;

    private Stage stage;

    private Enemy enemy;

    private char enemyType = ' ';

    private SurfaceThread surfaceThread;

    public EnemyInformationView(Context context, AttributeSet attrs) {
        super(context, attrs);

        WIDTH = getResources().getInteger(R.integer.gameWidth);

        FRAME = context.getResources().getInteger(R.integer.frame);
        FRAME_LENGTH = 1000 / FRAME;
        FADING_FRAME = context.getResources().getInteger(R.integer.gameOverFrame);

        paint = new Paint();
        paint.setColor(getResources().getColor(R.color.gameViewArea));

        getHolder().addCallback(this);

        stage = new Stage(getContext());
        surfaceThread = new SurfaceThread();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        ratio = 1.f * w / WIDTH;
        HEIGHT = (int) (h / ratio);

        start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        surfaceThread.start();
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

    public Enemy getEnemy() {
        return enemy;
    }

    public void setEnemyType(char enemyType) {
        this.enemyType = enemyType;
    }

    public void start() {
        if (enemyType != ' ') {
            enemy = Enemy.makeEnemy(stage, WIDTH / 2 + getResources().getInteger(R.integer.baseSight) * 5, HEIGHT / 2, enemyType);
            stage.player = new Player(stage, WIDTH / 2 - getResources().getInteger(R.integer.baseSight) * 5, HEIGHT / 2, false);
            surfaceThread.gameStart = true;
        }
    }

    // Surface Thread
    private class SurfaceThread extends Thread {

        SurfaceHolder surfaceHolder;
        boolean run = true, gameStart = false;
        long lastTime;

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

                                canvas.scale(ratio, ratio);

                                canvas.drawRect(0, 0, WIDTH, HEIGHT, paint);

                                stage.player.controlBuff();
                                stage.player.move();

                                enemy.setDirection();
                                enemy.useAbility();
                                enemy.decreaseAbilityWaiting();
                                enemy.controlBuff();
                                enemy.move();

                                ArrayList<Bullet> bullets = stage.bullets;
                                for (int i = 0; i < bullets.size(); i++) {
                                    Bullet bullet = bullets.get(i);
                                    bullet.move();
                                    if (stage.player.touch(bullet)) {
                                        for (Buff buff : bullets.get(i).getBuff())
                                            stage.player.addBuff(buff);
                                        bullets.remove(i);
                                        i--;
                                    } else if (bullet.isTouchWall()) {
                                        bullets.remove(i);
                                        i--;
                                    }
                                }

                                ArrayList<Field> fields = stage.fields;
                                for (int i = 0; i < fields.size(); i++) {
                                    Field field = fields.get(i);
                                    field.resize();
                                    if (field.isFinish()) {
                                        fields.remove(i);
                                        i--;
                                    }
                                }

                                stage.player.draw(canvas);
                                enemy.draw(canvas);
                                for (Bullet bullet : bullets)
                                    bullet.draw(canvas);
                                for (Field field : fields)
                                    field.draw(canvas);

                                if (stage.player.touch(enemy)) {
                                    enemy = Enemy.makeEnemy(stage, WIDTH / 2 + getResources().getInteger(R.integer.baseSight) * 5, HEIGHT / 2, enemy.typeCharacter);
                                    stage.player = new Player(stage, WIDTH / 2 - getResources().getInteger(R.integer.baseSight) * 5, HEIGHT / 2, false);
                                }
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
