package com.medowhill.jaemin.runaway.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.medowhill.jaemin.runaway.R;
import com.medowhill.jaemin.runaway.object.Player;
import com.medowhill.jaemin.runaway.object.Stage;

/**
 * Created by Jaemin on 2015-09-01.
 */
public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private final int WIDTH, HEIGHT;

    private final int FRAME, FRAME_LENGTH;

    private float ratio = 0;

    private Stage stage;

    private Paint paintNonArea, paintArea;

    private DirectionControl directionControl;

    private SurfaceThread surfaceThread;

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);

        WIDTH = getResources().getInteger(R.integer.gameWidth);
        HEIGHT = getResources().getInteger(R.integer.gameHeight);

        FRAME = context.getResources().getInteger(R.integer.frame);
        FRAME_LENGTH = 1000 / FRAME;

        paintNonArea = new Paint();
        paintNonArea.setColor(getResources().getColor(R.color.gameViewNonArea));

        paintArea = new Paint();
        paintArea.setColor(getResources().getColor(R.color.gameViewArea));

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

    public void setDirectionControl(DirectionControl directionControl) {
        this.directionControl = directionControl;
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

                                canvas.drawColor(Color.BLACK);

                                canvas.scale(ratio, ratio);

                                canvas.drawRect(0, 0, WIDTH, HEIGHT, paintNonArea);

                                canvas.drawPath(stage.area, paintArea);

                                Player player = stage.getPlayer();

                                player.setDirection(directionControl.getDirection());
                                player.move();
                                player.draw(canvas);
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
