package com.medowhill.jaemin.runaway.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.medowhill.jaemin.runaway.ability.Ability;

/**
 * Created by Jaemin on 2015-09-01.
 */
public class AbilityButton extends SurfaceView implements SurfaceHolder.Callback {

    private boolean touched = false, clicked = false;

    private Ability ability;

    private boolean invalidate;

    private SurfaceThread surfaceThread;

    public AbilityButton(Context context, AttributeSet attrs) {
        super(context, attrs);
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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touched = true;
                invalidate = true;
                break;
            case MotionEvent.ACTION_MOVE:
                if (touched) {
                    float x = event.getX(), y = event.getY();
                    if (x < 0 || x > getWidth() || y < 0 || y > getHeight()) {
                        touched = false;
                        invalidate = true;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if (touched) {
                    touched = false;
                    clicked = true;
                    invalidate = true;
                }
                break;
        }

        return true;
    }

    public boolean isClicked() {
        return clicked;
    }

    public void clearClick() {
        clicked = false;
        surfaceThread.coolDown = true;
    }

    private class SurfaceThread extends Thread {

        boolean coolDown = false;

        private SurfaceHolder surfaceHolder;
        private boolean run = true;
        private int lastRemaining;

        public SurfaceThread() {
            surfaceHolder = getHolder();
        }

        public void run() {
            while (run) {
                if (invalidate || coolDown && lastRemaining != ability.getRemainWaitingFrame()) {

                    if (coolDown) {
                        lastRemaining = ability.getRemainWaitingFrame();
                        if (lastRemaining == 0)
                            coolDown = false;
                    }

                    Canvas canvas = null;
                    try {
                        canvas = surfaceHolder.lockCanvas(null);
                        if (canvas != null) {

                        }
                    } finally {
                        if (canvas != null)
                            surfaceHolder.unlockCanvasAndPost(canvas);
                        invalidate = false;
                    }
                }
            }
        }
    }
}
