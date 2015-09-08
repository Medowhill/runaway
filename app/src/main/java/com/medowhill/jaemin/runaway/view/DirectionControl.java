package com.medowhill.jaemin.runaway.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.medowhill.jaemin.runaway.Direction;
import com.medowhill.jaemin.runaway.R;

/**
 * Copyright 2015. Hong Jaemin
 * All rights reserved.
 */

public class DirectionControl extends View {

    private final float PAD_SIZE = 0.4f, PAD_DEPTH = 0.05f, BALL_RADIUS = 0.075f, MOVING = 0.4f, NONE_LIMIT = 0.2f;

    private Paint paintPad, paintBall;

    private Path padPath;

    private int direction = Direction.NONE;

    public DirectionControl(Context context, AttributeSet attrs) {
        super(context, attrs);

        paintPad = new Paint();
        paintPad.setColor(context.getResources().getColor(R.color.directionControlPad));

        paintBall = new Paint();
        paintBall.setColor(context.getResources().getColor(R.color.directionControlBall));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        padPath = new Path();
        padPath.addRect(w * (0.5f - PAD_DEPTH), h * (0.5f - PAD_DEPTH), w * (0.5f + PAD_DEPTH), h * (0.5f + PAD_DEPTH), Path.Direction.CW);
        padPath.addRect(w * (0.5f - PAD_SIZE), h * (0.5f - PAD_DEPTH), w * (0.5f - PAD_DEPTH), h * (0.5f + PAD_DEPTH), Path.Direction.CW);
        padPath.addRect(w * (0.5f + PAD_DEPTH), h * (0.5f - PAD_DEPTH), w * (0.5f + PAD_SIZE), h * (0.5f + PAD_DEPTH), Path.Direction.CW);
        padPath.addRect(w * (0.5f - PAD_DEPTH), h * (0.5f - PAD_SIZE), w * (0.5f + PAD_DEPTH), h * (0.5f - PAD_DEPTH), Path.Direction.CW);
        padPath.addRect(w * (0.5f - PAD_DEPTH), h * (0.5f + PAD_DEPTH), w * (0.5f + PAD_DEPTH), h * (0.5f + PAD_SIZE), Path.Direction.CW);
        padPath.addCircle(w * (0.5f - PAD_SIZE), h * 0.5f, w * PAD_DEPTH, Path.Direction.CW);
        padPath.addCircle(w * (0.5f + PAD_SIZE), h * 0.5f, w * PAD_DEPTH, Path.Direction.CW);
        padPath.addCircle(w * 0.5f, h * (0.5f - PAD_SIZE), w * PAD_DEPTH, Path.Direction.CW);
        padPath.addCircle(w * 0.5f, h * (0.5f + PAD_SIZE), w * PAD_DEPTH, Path.Direction.CW);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float dx = 0, dy = 0;
        switch (direction) {
            case Direction.UP:
                dy = -MOVING;
                break;
            case Direction.RIGHT:
                dx = MOVING;
                break;
            case Direction.DOWN:
                dy = MOVING;
                break;
            case Direction.LEFT:
                dx = -MOVING;
                break;
        }

        int width = getWidth(), height = getHeight();
        canvas.drawPath(padPath, paintPad);
        canvas.drawCircle(width * (0.5f + dx), height * (0.5f + dy), width * BALL_RADIUS, paintBall);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            direction = Direction.NONE;
            invalidate();
        } else {
            int width = getWidth(), height = getHeight();

            float x = event.getX() - width / 2, y = event.getY() - height / 2;

            int newDirection;
            if (Math.abs(x) < width * NONE_LIMIT && Math.abs(y) < height * NONE_LIMIT)
                newDirection = Direction.NONE;
            else {
                if (Math.abs(x) < Math.abs(y)) {
                    if (y > 0)
                        newDirection = Direction.DOWN;
                    else
                        newDirection = Direction.UP;
                } else {
                    if (x > 0)
                        newDirection = Direction.RIGHT;
                    else
                        newDirection = Direction.LEFT;
                }
            }

            if (newDirection != direction) {
                direction = newDirection;
                invalidate();
            }
        }

        return true;
    }

    public int getDirection() {
        return direction;
    }
}
