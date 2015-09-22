package com.medowhill.jaemin.runaway.view;

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

import com.medowhill.jaemin.runaway.R;
import com.medowhill.jaemin.runaway.object.Stage;
import com.medowhill.jaemin.runaway.object.Wall;

/**
 * Created by Jaemin on 2015-09-17.
 */
public class StageSelectView extends View {

    private final int WIDTH;
    private final int FRAME, FRAME_LENGTH;
    private final int DECREASING_RATIO;
    private final int BACKGROUND_COLOR;
    private final int BOUNDARY_STROKE, BOUNDARY_RADIUS;
    private final int SHADOW_SIZE;
    private final float MAX_SCALE = 2.0f, MIN_SCALE = 0.2f;
    private final float MIN_SPEED = .5f;
    private final float ACCELERATION = -0.005f;

    private float ratio = 0;
    private float xShift = 0, yShift = 0, xPivot, yPivot, scale = 1;
    private float prevX, prevY, prevD;
    private boolean secondaryPointerUp = false;
    private boolean checkingSpeed = false;
    private long prevTime, draggingTime;
    private float draggingX, draggingY;
    private float inertialVx, inertialVy;
    private int handlerID = 0;

    private Paint paintMap;
    private Paint paintShadow;
    private Paint paintBoundary;

    private int stageCount;
    private Path[] maps;
    private RectF[] boundaries;

    private Handler accelerationHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (handlerID == msg.what) {
                float v = (float) Math.sqrt(inertialVx * inertialVx + inertialVy * inertialVy);
                float prevVx = inertialVx, prevVy = inertialVy;
                inertialVx += ACCELERATION * FRAME_LENGTH * inertialVx / v;
                inertialVy += ACCELERATION * FRAME_LENGTH * inertialVy / v;
                if (prevVx * inertialVx > 0 && prevVy * inertialVy > 0) {
                    xShift += inertialVx * FRAME_LENGTH / (scale * ratio);
                    yShift += inertialVy * FRAME_LENGTH / (scale * ratio);
                    invalidate();
                    accelerationHandler.sendEmptyMessageDelayed(handlerID, FRAME_LENGTH);
                }
            }
        }
    };

    public StageSelectView(Context context, AttributeSet attrs) {
        super(context, attrs);

        DECREASING_RATIO = getResources().getInteger(R.integer.decreasingRatio);

        WIDTH = getResources().getInteger(R.integer.gameWidth);
        BOUNDARY_STROKE = getResources().getInteger(R.integer.boundaryStroke);
        BOUNDARY_RADIUS = getResources().getInteger(R.integer.boundaryRadius);
        SHADOW_SIZE = getResources().getInteger(R.integer.shadowSize);

        FRAME = getResources().getInteger(R.integer.frame);
        FRAME_LENGTH = 1000 / FRAME;

        BACKGROUND_COLOR = getResources().getColor(R.color.stageSelectBackground);
        paintMap = new Paint();
        paintMap.setColor(getResources().getColor(R.color.stageSelectPath));
        paintShadow = new Paint();
        paintShadow.setColor(getResources().getColor(R.color.stageSelectShadow));
        paintBoundary = new Paint();
        paintBoundary.setColor(getResources().getColor(R.color.stageSelectRect));
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

            RectF rect = new RectF((int) (fx - WIDTH / 4) / DECREASING_RATIO, (int) (fy - WIDTH / 4) / DECREASING_RATIO,
                    (int) (fx + stage.getxMax() + WIDTH / 4) / DECREASING_RATIO, (int) (fy + stage.getyMax() + WIDTH / 4) / DECREASING_RATIO);
            boundaries[i] = rect;

            fx += stage.getxFinish();
            fy += stage.getyFinish();
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        ratio = 1.f * w / WIDTH;
        xPivot = w / 2;
        yPivot = h / 2;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
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
                                accelerationHandler.sendEmptyMessageDelayed(handlerID, FRAME_LENGTH);
                            }
                            checkingSpeed = false;
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
        for (Path map : maps)
            canvas.drawPath(map, paintShadow);

        canvas.translate(-SHADOW_SIZE, -SHADOW_SIZE);
        for (Path map : maps)
            canvas.drawPath(map, paintMap);

        if (scale >= 1) {
            paintBoundary.setStrokeWidth(1.f * BOUNDARY_STROKE / (scale * ratio));
            for (RectF rect : boundaries)
                canvas.drawRoundRect(rect, BOUNDARY_RADIUS, BOUNDARY_RADIUS, paintBoundary);
        }
    }
}
