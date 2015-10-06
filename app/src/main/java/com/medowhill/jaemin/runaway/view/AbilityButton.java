package com.medowhill.jaemin.runaway.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

/**
 * Copyright 2015. Hong Jaemin
 * All rights reserved.
 */

public class AbilityButton extends View {

    private final float OUTER_SIZE = 0.4f, INNER_SIZE = 0.35f, ROUND_RECT = 0.05f;

    private boolean touched = false, clicked = false;

    private Bitmap icon;

    private Path[] coolPaths;

    private RectF innerRect, outerRect;

    private float ratio = 0;

    private int iconResourceID;

    private Paint paintBorder, paintTouched, paintCool;

    private boolean visible = false;

    private Handler drawHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            ratio = 1.f * msg.arg1 / msg.arg2;
            invalidate();
        }
    };

    public AbilityButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        paintBorder = new Paint();
        paintBorder.setColor(getResources().getColor(R.color.abilityButtonBorder));

        paintTouched = new Paint();
        paintTouched.setColor(getResources().getColor(R.color.abilityButtonTouched));

        paintCool = new Paint();
        paintCool.setColor(getResources().getColor(R.color.abilityButtonCool));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        innerRect = new RectF(w * (0.5f - INNER_SIZE), h * (0.5f - INNER_SIZE), w * (0.5f + INNER_SIZE), h * (0.5f + INNER_SIZE));
        outerRect = new RectF(w * (0.5f - OUTER_SIZE), h * (0.5f - OUTER_SIZE), w * (0.5f + OUTER_SIZE), h * (0.5f + OUTER_SIZE));

        coolPaths = new Path[4];
        int[][] x = new int[][]{{-1, -1, 0}, {0, 1, 1}, {1, 1, 0}, {0, -1, -1}};
        int[][] y = new int[][]{{0, -1, -1}, {-1, -1, 0}, {0, 1, 1}, {1, 1, 0}};
        for (int i = 0; i < coolPaths.length; i++) {
            Path path;
            path = new Path();
            path.moveTo(w * (0.5f + INNER_SIZE * x[i][0]), h * (0.5f + INNER_SIZE * y[i][0]));
            path.lineTo(w * (0.5f + INNER_SIZE * x[i][1]), h * (0.5f + INNER_SIZE * y[i][1]));
            path.lineTo(w * (0.5f + INNER_SIZE * x[i][2]), h * (0.5f + INNER_SIZE * y[i][2]));
            path.arcTo(innerRect, -90 + i * 90, -90);
            coolPaths[i] = path;
        }

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), iconResourceID);
        if (bitmap != null) {
            icon = Bitmap.createScaledBitmap(bitmap, (int) (getWidth() * 2 * INNER_SIZE), (int) (getHeight() * 2 * INNER_SIZE), false);
            bitmap.recycle();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (clicked)
            return true;

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touched = true;
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                if (touched) {
                    float x = event.getX(), y = event.getY();
                    if (x < 0 || x > getWidth() || y < 0 || y > getHeight()) {
                        touched = false;
                        invalidate();
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if (touched) {
                    touched = false;
                    clicked = true;
                    invalidate();
                }
                break;
        }

        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (visible) {
            int width = getWidth(), height = getHeight();

            canvas.drawRoundRect(outerRect, width * ROUND_RECT, height * ROUND_RECT, paintBorder);
            if (icon != null)
                canvas.drawBitmap(icon, width * (0.5f - INNER_SIZE), height * (0.5f - INNER_SIZE), null);

            if (touched)
                canvas.drawRect(innerRect, paintTouched);

            if (ratio != 0) {
                for (Path path : coolPaths)
                    canvas.drawPath(path, paintCool);
                canvas.drawArc(innerRect, -90 - 360 * ratio, 360 * ratio, true, paintCool);
            }
        }
    }

    public void clearCool() {
        ratio = 0;
        invalidate();
    }

    public boolean isClicked() {
        return clicked;
    }

    public void clearClick() {
        clicked = false;
    }

    public void setIconResourceID(int iconResourceID) {
        this.iconResourceID = iconResourceID;
    }

    public Handler getDrawHandler() {
        return drawHandler;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
        invalidate();
    }
}
