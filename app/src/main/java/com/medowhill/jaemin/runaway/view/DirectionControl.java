package com.medowhill.jaemin.runaway.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
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

    private final float PAD_CENTER_SIZE = 0.1f, PAD_LENGTH = 0.3f, ARROW_WIDTH = 0.075f, ARROW_HEIGHT = 0.075f, ARROW_DISTANCE = 0.275f;
    private final float NONE_LIMIT = 0.2f;

    private Paint paintRect, paintArrow, paintDirectionArrow;

    private RectF rectCenter;
    private RectF[] rectFs;
    private float[][] lines;
    private LinearGradient[] gradients;
    private int[] gradientColors;
    private float[] gradientPostions;

    private int direction = Direction.NONE;

    private boolean initialized = false;

    public DirectionControl(Context context, AttributeSet attrs) {
        super(context, attrs);

        paintRect = new Paint();
        paintRect.setColor(getResources().getColor(R.color.directionControlPad));

        float stroke = getResources().getDimension(R.dimen.directionControlArrowStrokeWidth);

        paintArrow = new Paint();
        paintArrow.setColor(getResources().getColor(R.color.directionControlArrow));
        paintArrow.setStrokeCap(Paint.Cap.ROUND);
        paintArrow.setStrokeWidth(stroke);

        paintDirectionArrow = new Paint();
        paintDirectionArrow.setColor(getResources().getColor(R.color.directionControlDirectionArrow));
        paintArrow.setStrokeCap(Paint.Cap.ROUND);
        paintDirectionArrow.setStrokeWidth(stroke);

        gradientColors = new int[]{getResources().getColor(R.color.directionControlDirectionPad), getResources().getColor(R.color.directionControlPad)};
        gradientPostions = new float[]{0, 1};
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        initialized = true;

        rectCenter = new RectF(w * (0.5f - PAD_CENTER_SIZE), h * (0.5f - PAD_CENTER_SIZE), w * (0.5f + PAD_CENTER_SIZE), h * (0.5f + PAD_CENTER_SIZE));
        rectFs = new RectF[4];
        rectFs[0] = new RectF(w * (0.5f - PAD_CENTER_SIZE), h * (0.5f - PAD_CENTER_SIZE - PAD_LENGTH), w * (0.5f + PAD_CENTER_SIZE), h * (0.5f - PAD_CENTER_SIZE));
        rectFs[1] = new RectF(w * (0.5f + PAD_CENTER_SIZE), h * (0.5f - PAD_CENTER_SIZE), w * (0.5f + PAD_CENTER_SIZE + PAD_LENGTH), h * (0.5f + PAD_CENTER_SIZE));
        rectFs[2] = new RectF(w * (0.5f - PAD_CENTER_SIZE), h * (0.5f + PAD_CENTER_SIZE), w * (0.5f + PAD_CENTER_SIZE), h * (0.5f + PAD_CENTER_SIZE + PAD_LENGTH));
        rectFs[3] = new RectF(w * (0.5f - PAD_CENTER_SIZE - PAD_LENGTH), h * (0.5f - PAD_CENTER_SIZE), w * (0.5f - PAD_CENTER_SIZE), h * (0.5f + PAD_CENTER_SIZE));

        lines = new float[4][];
        lines[0] = new float[]{w * (0.5f - ARROW_WIDTH), h * (0.5f - ARROW_DISTANCE), w * 0.5f, h * (0.5f - ARROW_DISTANCE - ARROW_HEIGHT),
                w * 0.5f, h * (0.5f - ARROW_DISTANCE - ARROW_HEIGHT), w * (0.5f + ARROW_WIDTH), h * (0.5f - ARROW_DISTANCE)};
        lines[1] = new float[]{w * (0.5f + ARROW_DISTANCE), h * (0.5f - ARROW_WIDTH), w * (0.5f + ARROW_DISTANCE + ARROW_HEIGHT), h * 0.5f,
                w * (0.5f + ARROW_DISTANCE + ARROW_HEIGHT), h * 0.5f, w * (0.5f + ARROW_DISTANCE), h * (0.5f + ARROW_WIDTH)};
        lines[2] = new float[]{w * (0.5f - ARROW_WIDTH), h * (0.5f + ARROW_DISTANCE), w * 0.5f, h * (0.5f + ARROW_DISTANCE + ARROW_HEIGHT),
                w * 0.5f, h * (0.5f + ARROW_DISTANCE + ARROW_HEIGHT), w * (0.5f + ARROW_WIDTH), h * (0.5f + ARROW_DISTANCE)};
        lines[3] = new float[]{w * (0.5f - ARROW_DISTANCE), h * (0.5f - ARROW_WIDTH), w * (0.5f - ARROW_DISTANCE - ARROW_HEIGHT), h * 0.5f,
                w * (0.5f - ARROW_DISTANCE - ARROW_HEIGHT), h * 0.5f, w * (0.5f - ARROW_DISTANCE), h * (0.5f + ARROW_WIDTH)};

        gradients = new LinearGradient[4];
        gradients[0] = new LinearGradient(w * 0.5f, h * (0.5f - PAD_CENTER_SIZE - PAD_LENGTH), w * 0.5f, h * (0.5f - PAD_CENTER_SIZE),
                gradientColors, gradientPostions, Shader.TileMode.CLAMP);
        gradients[1] = new LinearGradient(w * (0.5f + PAD_CENTER_SIZE + PAD_LENGTH), h * 0.5f, w * (0.5f + PAD_CENTER_SIZE), h * 0.5f,
                gradientColors, gradientPostions, Shader.TileMode.CLAMP);
        gradients[2] = new LinearGradient(w * 0.5f, h * (0.5f + PAD_CENTER_SIZE + PAD_LENGTH), w * 0.5f, h * (0.5f + PAD_CENTER_SIZE),
                gradientColors, gradientPostions, Shader.TileMode.CLAMP);
        gradients[3] = new LinearGradient(w * (0.5f - PAD_CENTER_SIZE - PAD_LENGTH), h * 0.5f, w * (0.5f - PAD_CENTER_SIZE), h * 0.5f,
                gradientColors, gradientPostions, Shader.TileMode.CLAMP);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (initialized) {
            canvas.drawRect(rectCenter, paintRect);
            for (int i = 0; i < rectFs.length; i++) {
                if (i == direction) {
                    paintRect.setShader(gradients[i]);
                    canvas.drawRect(rectFs[i], paintRect);
                    paintRect.setShader(null);
                    canvas.drawLines(lines[i], paintDirectionArrow);
                } else {
                    canvas.drawRect(rectFs[i], paintRect);
                    canvas.drawLines(lines[i], paintArrow);
                }
            }
        }
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
