package com.medowhill.jaemin.runaway.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;

import com.medowhill.jaemin.runaway.R;
import com.medowhill.jaemin.runaway.object.Stage;
import com.medowhill.jaemin.runaway.object.Wall;

/**
 * Copyright 2015. Hong Jaemin
 * All rights reserved.
 */

public class StageSelectView extends View {

    static final int INERTIA = 0, SCALE = 1, MOVE = 2;

    private final int WIDTH;
    private final int FRAME, FRAME_LENGTH;
    private final int DECREASING_RATIO;
    private final int BACKGROUND_COLOR;
    private final int BOUNDARY_STROKE, BOUNDARY_RADIUS, BOUNDARY_MARGIN;
    private final int SHADOW_SIZE;
    private final int MAX_POINTER_MOVE;
    private final float MAX_SCALE = 1.5f, MIN_SCALE = .2f, SELECT_SCALE = 1.f, GAME_SCALE = 2.5f;
    private final float MIN_SPEED = .25f;
    private final float ACCELERATION = -0.005f;
    private final float SCALE_SPEED = .004f, MOVE_SPEED = 1.9f;
    private final float MOVE_TENSION = 1.25f;

    private float ratio = 0;
    private float xShift = 0, yShift = 0, xPivot, yPivot, scale = 1;
    private float prevX, prevY, prevD;
    private boolean secondaryPointerUp = false;
    private boolean checkingSpeed = false;
    private long prevTime, draggingTime;
    private float draggingX, draggingY;
    private float inertialVx, inertialVy;
    private int pointerMoveCount = 0;
    private int handlerID = 0;

    private Paint paintShadow;
    private Paint paintMap;
    private Paint paintUnableMap;
    private Paint paintBoundary;

    private int stageCount;
    private Path[] maps;
    private RectF[] boundaries;

    private int lastStage = 0;
    private int startStage;
    private boolean gameStart;

    private Handler inertiaHandler, stageSelectHandler;

    public StageSelectView(Context context, AttributeSet attrs) {
        super(context, attrs);

        DECREASING_RATIO = getResources().getInteger(R.integer.stageSelectDecreasingRatio);
        WIDTH = getResources().getInteger(R.integer.gameWidth);
        BOUNDARY_STROKE = getResources().getInteger(R.integer.stageSelectBoundaryStroke);
        BOUNDARY_RADIUS = getResources().getInteger(R.integer.stageSelectBoundaryRadius);
        BOUNDARY_MARGIN = getResources().getInteger(R.integer.stageSelectBoundaryMargin);
        SHADOW_SIZE = getResources().getInteger(R.integer.stageSelectShadowSize);
        MAX_POINTER_MOVE = getResources().getInteger(R.integer.stageSelectMaxPointerMove);

        FRAME = getResources().getInteger(R.integer.frame);
        FRAME_LENGTH = 1000 / FRAME;

        BACKGROUND_COLOR = getResources().getColor(R.color.stageSelectBackground);
        paintShadow = new Paint();
        paintShadow.setColor(getResources().getColor(R.color.stageSelectShadow));
        paintMap = new Paint();
        paintMap.setColor(getResources().getColor(R.color.stageSelectMap));
        paintUnableMap = new Paint();
        paintUnableMap.setColor(getResources().getColor(R.color.stageSelectUnableMap));
        paintBoundary = new Paint();
        paintBoundary.setColor(getResources().getColor(R.color.stageSelectBoundary));
        paintBoundary.setStyle(Paint.Style.STROKE);

        stageCount = getResources().getStringArray(R.array.stageInfo).length;
        maps = new Path[stageCount];
        boundaries = new RectF[stageCount];

        float fx = WIDTH * DECREASING_RATIO / 4, fy = WIDTH * DECREASING_RATIO / 4;
        for (int i = 0; i < stageCount; i++) {
            Stage stage = new Stage(context, i + 1, true);
            fx -= stage.getxStart();
            fy -= stage.getyStart();

            Path path = new Path();
            path.moveTo(fx / DECREASING_RATIO, fy / DECREASING_RATIO);
            for (Wall wall : stage.walls) {
                if (wall.HORIZONTAL)
                    path.lineTo((fx + wall.LAST) / DECREASING_RATIO, (fy + wall.LOCATION) / DECREASING_RATIO);
                else
                    path.lineTo((fx + wall.LOCATION) / DECREASING_RATIO, (fy + wall.LAST) / DECREASING_RATIO);
            }
            maps[i] = path;

            RectF rect = new RectF((int) (fx - BOUNDARY_MARGIN) / DECREASING_RATIO, (int) (fy - BOUNDARY_MARGIN) / DECREASING_RATIO,
                    (int) (fx + stage.getxMax() + BOUNDARY_MARGIN) / DECREASING_RATIO, (int) (fy + stage.getyMax() + BOUNDARY_MARGIN) / DECREASING_RATIO);
            boundaries[i] = rect;

            fx += stage.getxFinish();
            fy += stage.getyFinish();
        }

        inertiaHandler = new InertiaHandler(this);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        ratio = 1.f * w / WIDTH;
        xPivot = w / 2;
        yPivot = h / 2;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (gameStart)
            return true;

        int count = event.getPointerCount();
        if (count == 1) {
            float x = event.getX(), y = event.getY();
            if (secondaryPointerUp) {
                prevX = x;
                prevY = y;
                secondaryPointerUp = false;
            } else {
                float dx, dy;
                long currentTime;
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        prevX = x;
                        prevY = y;
                        handlerID++;
                        pointerMoveCount = 0;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        dx = x - prevX;
                        dy = y - prevY;
                        currentTime = System.currentTimeMillis();
                        float speed = (float) Math.sqrt(dx * dx + dy * dy) / (currentTime - prevTime);
                        if (speed > MIN_SPEED) {
                            if (!checkingSpeed) {
                                checkingSpeed = true;
                                draggingTime = currentTime;
                                draggingX = x;
                                draggingY = y;
                            }
                        } else
                            checkingSpeed = false;
                        xShift += dx / (scale * ratio);
                        yShift += dy / (scale * ratio);
                        prevX = x;
                        prevY = y;
                        pointerMoveCount++;
                        break;
                    case MotionEvent.ACTION_UP:
                        if (checkingSpeed) {
                            dx = x - draggingX;
                            dy = y - draggingY;
                            currentTime = System.currentTimeMillis();
                            long time = currentTime - draggingTime;
                            float aveSpeed = (float) Math.sqrt(dx * dx + dy * dy) / time;
                            if (aveSpeed > MIN_SPEED) {
                                inertialVx = dx / time;
                                inertialVy = dy / time;
                                Message message = new Message();
                                message.what = INERTIA;
                                message.arg1 = handlerID;
                                inertiaHandler.sendMessageDelayed(message, FRAME_LENGTH);
                            }
                            checkingSpeed = false;
                        } else if (pointerMoveCount < MAX_POINTER_MOVE) {
                            if (scale >= SELECT_SCALE) {
                                float x_ = (x - xPivot) / (scale * ratio) + xPivot - xShift;
                                float y_ = (y - yPivot) / (scale * ratio) + yPivot - yShift;
                                for (int i = lastStage - 1; i >= 0; i--) {
                                    RectF rectF = boundaries[i];
                                    if (rectF.left < x_ && x_ < rectF.right && rectF.top < y_ && y_ < rectF.bottom) {
                                        gameStart = true;
                                        startStage = i + 1;
                                        moveToStage();
                                        break;
                                    }
                                }
                            }
                        }
                        break;
                }
            }
            prevTime = System.currentTimeMillis();
        } else {
            float x1 = event.getX(0), y1 = event.getY(0), x2 = event.getX(1), y2 = event.getY(1);
            float distance = (float) Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_POINTER_DOWN:
                    if (count == 2)
                        prevD = distance;
                    break;
                case MotionEvent.ACTION_MOVE:
                    scale *= distance / prevD;
                    if (scale > MAX_SCALE)
                        scale = MAX_SCALE;
                    else if (scale < MIN_SCALE)
                        scale = MIN_SCALE;
                    prevD = distance;
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                    if (count == 2) {
                        checkingSpeed = false;
                        secondaryPointerUp = true;
                    }
                    break;
            }
            pointerMoveCount = MAX_POINTER_MOVE;
        }

        invalidate();

        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(BACKGROUND_COLOR);

        canvas.scale(scale * ratio, scale * ratio, xPivot, yPivot);
        canvas.translate(xShift, yShift);

        canvas.translate(SHADOW_SIZE, SHADOW_SIZE);
        for (int i = 0; i < maps.length; i++)
            if (i < lastStage - 1)
                canvas.drawPath(maps[i], paintShadow);

        canvas.translate(-SHADOW_SIZE, -SHADOW_SIZE);
        for (int i = maps.length - 1; i >= 0; i--) {
            if (i < lastStage) {
                if (i == lastStage - 1) {
                    canvas.translate(SHADOW_SIZE, SHADOW_SIZE);
                    canvas.drawPath(maps[i], paintShadow);
                    canvas.translate(-SHADOW_SIZE, -SHADOW_SIZE);
                }
                canvas.drawPath(maps[i], paintMap);
            } else
                canvas.drawPath(maps[i], paintUnableMap);
        }

        if (scale >= SELECT_SCALE && !gameStart) {
            paintBoundary.setStrokeWidth(1.f * BOUNDARY_STROKE / (scale * ratio));
            for (int i = 0; i < boundaries.length; i++)
                if (i < lastStage)
                    canvas.drawRoundRect(boundaries[i], BOUNDARY_RADIUS, BOUNDARY_RADIUS, paintBoundary);
        }
    }

    void inertia(int handlerID) {
        if (this.handlerID == handlerID) {
            float v = (float) Math.sqrt(inertialVx * inertialVx + inertialVy * inertialVy);
            float prevVx = inertialVx, prevVy = inertialVy;
            inertialVx += ACCELERATION * FRAME_LENGTH * inertialVx / v;
            inertialVy += ACCELERATION * FRAME_LENGTH * inertialVy / v;
            if (prevVx * inertialVx > 0 && prevVy * inertialVy > 0) {
                xShift += inertialVx * FRAME_LENGTH / (scale * ratio);
                yShift += inertialVy * FRAME_LENGTH / (scale * ratio);
                invalidate();

                Message message = new Message();
                message.what = INERTIA;
                message.arg1 = handlerID;
                inertiaHandler.sendMessageDelayed(message, FRAME_LENGTH);
            }
        }
    }

    void moveToStage() {
        RectF rectF = boundaries[startStage - 1];

        final float moveToX = -(rectF.left + rectF.right) / 2 + xPivot;
        final float moveToY = -(rectF.top + rectF.bottom) / 2 + yPivot;
        final float distance = (float) Math.sqrt((xShift - moveToX) * (xShift - moveToX) + (yShift - moveToY) * (yShift - moveToY));

        if (distance > MOVE_SPEED) {
            ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
            valueAnimator.setDuration((int) (distance / MOVE_SPEED));
            valueAnimator.setInterpolator(new AnticipateOvershootInterpolator(MOVE_TENSION));
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                final float X = xShift, Y = yShift, DX = moveToX - X, DY = moveToY - Y;

                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float ratio = (Float) (animation.getAnimatedValue());
                    xShift = ratio * DX + X;
                    yShift = ratio * DY + Y;
                    invalidate();

                    if (ratio == 1)
                        magnify();
                }
            });
            valueAnimator.start();
        } else {
            magnify();
        }
    }

    void magnify() {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(scale, GAME_SCALE);
        valueAnimator.setDuration((int) ((GAME_SCALE - scale) / SCALE_SPEED));
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                scale = (Float) animation.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.start();
        stageSelectHandler.sendEmptyMessage(startStage);
    }

    public void defaultScale(final boolean nextStage) {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(GAME_SCALE, SELECT_SCALE);
        valueAnimator.setDuration((int) ((GAME_SCALE - SELECT_SCALE) / SCALE_SPEED));
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                scale = (Float) animation.getAnimatedValue();
                invalidate();

                if (scale == SELECT_SCALE) {
                    if (nextStage) {
                        startStage++;
                        moveToStage();
                    } else
                        gameStart = false;
                }
            }
        });
        valueAnimator.start();
    }

    public void openNewStage(int stage) {
        if (stage > lastStage)
            lastStage = stage;
        invalidate();
    }

    public void setLastStage(int lastStage) {
        this.lastStage = lastStage;
        invalidate();
    }

    public void setStageSelectHandler(Handler stageSelectHandler) {
        this.stageSelectHandler = stageSelectHandler;
    }
}

class InertiaHandler extends Handler {

    StageSelectView stageSelectView;

    public InertiaHandler(StageSelectView stageSelectView) {
        this.stageSelectView = stageSelectView;
    }

    @Override
    public void handleMessage(Message msg) {
        stageSelectView.inertia(msg.arg1);
    }
}