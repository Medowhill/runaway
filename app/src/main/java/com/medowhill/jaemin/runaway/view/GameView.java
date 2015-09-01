package com.medowhill.jaemin.runaway.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.medowhill.jaemin.runaway.R;

/**
 * Created by Jaemin on 2015-09-01.
 */
public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private final int WIDTH, HEIGHT;

    private final int FRAME, FRAME_LENGTH;

    private float ratio = 0;

    private SurfaceThread surfaceThread;

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);

        WIDTH = context.getResources().getInteger(R.integer.gameWidth);
        HEIGHT = context.getResources().getInteger(R.integer.gameHeight);

        FRAME = context.getResources().getInteger(R.integer.frame);
        FRAME_LENGTH = 1000 / FRAME;

        getHolder().addCallback(this);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        float widthRatio = 1.f * WIDTH / w;
        float heightRatio = 1.f * HEIGHT / h;
        ratio = Math.min(widthRatio, heightRatio);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        surfaceThread = new SurfaceThread();
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

    private class SurfaceThread extends Thread {

        private SurfaceHolder surfaceHolder;
        private boolean run = true;
        private long lastTime;

        public SurfaceThread() {
            surfaceHolder = getHolder();

            lastTime = System.currentTimeMillis();
        }

        public void run() {
            while (run) {
                long currentTime = System.currentTimeMillis();

                if (currentTime - lastTime >= FRAME_LENGTH) {
                    lastTime = currentTime;

                    Canvas canvas = null;
                    try {
                        canvas = surfaceHolder.lockCanvas(null);
                        if (canvas != null) {

                            canvas.drawColor(Color.BLACK);

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
