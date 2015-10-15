package com.medowhill.jaemin.runaway.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.medowhill.jaemin.runaway.R;

/**
 * Created by Jaemin on 2015-10-15.
 */
public class ToWorldSelectButton extends SurfaceView implements SurfaceHolder.Callback {

    private final int FRAME, FRAME_LENGTH;

    private final int SIZE, INNER_SIZE, SPEED, MOVE, RADIUS;

    private float ratio;

    private int[] colors = new int[]{Color.RED, Color.GREEN, Color.BLUE, Color.MAGENTA, Color.CYAN, Color.YELLOW};
    private Paint[] paints;

    private SurfaceThread surfaceThread;

    private Bitmap bitmap;

    public ToWorldSelectButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        FRAME = getResources().getInteger(R.integer.frame);
        FRAME_LENGTH = 1000 / FRAME;

        SIZE = getResources().getInteger(R.integer.toWorldSelectButtonSize);
        INNER_SIZE = getResources().getInteger(R.integer.toWorldSelectButtonInnerSize);
        SPEED = getResources().getInteger(R.integer.toWorldSelectButtonSpeed);
        MOVE = getResources().getInteger(R.integer.toWorldSelectButtonMove);
        RADIUS = getResources().getInteger(R.integer.toWorldSelectButtonCircleRadius);

        paints = new Paint[colors.length];
        for (int i = 0; i < colors.length; i++) {
            paints[i] = new Paint();
            paints[i].setColor(colors[i]);
        }

        getHolder().addCallback(this);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if (bitmap != null) {
            bitmap.recycle();
            bitmap = null;
        }
        Bitmap temp = BitmapFactory.decodeResource(getResources(), R.drawable.main_button_background);
        bitmap = Bitmap.createScaledBitmap(temp, width, height, false);
        temp.recycle();

        ratio = 1.f * width / SIZE;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        surfaceThread = new SurfaceThread();
        surfaceThread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (bitmap != null) {
            bitmap.recycle();
            bitmap = null;
        }

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

    // Surface Thread
    private class SurfaceThread extends Thread {

        SurfaceHolder surfaceHolder;
        boolean run = true;
        long lastTime;

        Ball[] balls = new Ball[colors.length];

        public SurfaceThread() {
            surfaceHolder = getHolder();

            for (int i = 0; i < balls.length; i++)
                balls[i] = new Ball();
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
                            if (bitmap != null)
                                canvas.drawBitmap(bitmap, 0, 0, null);

                            canvas.translate(getWidth() / 2, getHeight() / 2);
                            canvas.scale(ratio, ratio);

                            for (int i = 0; i < balls.length; i++) {
                                balls[i].move();
                                balls[i].draw(canvas, paints[i]);
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

    private class Ball {

        boolean finish = true, moveX = true;
        int move, x, y, px1, py1, px2, py2, dx, dy;

        void move() {
            if (finish) {
                finish = false;
                move = MOVE;

                int rand = (int) (Math.random() * 4);
                switch (rand) {
                    case 0:
                        dx = 1;
                        dy = -1;
                        break;
                    case 1:
                        dx = 1;
                        dy = -1;
                        break;
                    case 2:
                        dx = -1;
                        dy = 1;
                        break;
                    case 3:
                        dx = -1;
                        dy = -1;
                        break;
                }

                x = px1 = px2 = -dx * INNER_SIZE;
                y = py1 = py2 = -dy * INNER_SIZE;
            }

            if (move == MOVE) {
                move = 0;
                moveX = (int) (Math.random() * 2) == 0;
            }

            px2 = px1;
            py2 = py1;
            px1 = x;
            py1 = y;
            if (moveX) {
                x += dx * SPEED;
            } else {
                y += dy * SPEED;
            }
            move++;


            if (Math.abs(x) > INNER_SIZE || Math.abs(y) > INNER_SIZE)
                finish = true;
        }

        void draw(Canvas canvas, Paint paint) {
            canvas.drawLine(px2, py2, px1, py1, paint);
            canvas.drawLine(px1, py1, x, y, paint);
            canvas.drawCircle(x, y, RADIUS, paint);
        }
    }

}
