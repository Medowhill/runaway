package com.medowhill.jaemin.runaway.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
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

    private final float OUTER_SIZE = 0.375f, INNER_SIZE = 0.325f, MOVING = 0.1f, NONE_LIMIT = 0.2f;

    private Paint paint;

    private int direction = Direction.NONE;

    public DirectionControl(Context context, AttributeSet attrs) {
        super(context, attrs);

        paint = new Paint();
        paint.setColor(context.getResources().getColor(R.color.directionControl));
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
        canvas.drawRect(width * (0.5f - OUTER_SIZE), height * (0.5f - OUTER_SIZE), width * (0.5f + OUTER_SIZE), height * (0.5f + OUTER_SIZE), paint);
        canvas.drawRect(width * (0.5f - INNER_SIZE + dx), height * (0.5f - INNER_SIZE + dy),
                width * (0.5f + INNER_SIZE + dx), height * (0.5f + INNER_SIZE + dy), paint);
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
