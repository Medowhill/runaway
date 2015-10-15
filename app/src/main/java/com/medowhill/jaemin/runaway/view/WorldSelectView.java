package com.medowhill.jaemin.runaway.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.medowhill.jaemin.runaway.R;

/**
 * Created by Jaemin on 2015-10-15.
 */
public class WorldSelectView extends View {

    private final int SIZE, SHADOW_SIZE, CLICKED_DISTANCE;

    private float ratio;

    private Paint paintWorld, paintClicked, paintUnable, paintShadow;

    private RectF[][] worlds;

    private int pointer = -1;

    private Handler worldHandler;

    public WorldSelectView(Context context, AttributeSet attrs) {
        super(context, attrs);

        SIZE = getResources().getInteger(R.integer.worldSelectViewSize);
        SHADOW_SIZE = getResources().getInteger(R.integer.worldSelectShadowSize);
        CLICKED_DISTANCE = getResources().getInteger(R.integer.worldSelectClickedDistance);

        paintWorld = new Paint();
        paintWorld.setColor(getResources().getColor(R.color.worldSelectWorld));

        paintClicked = new Paint();
        paintClicked.setColor(getResources().getColor(R.color.worldSelectClickedWorld));

        paintUnable = new Paint();
        paintUnable.setColor(getResources().getColor(R.color.worldSelectUnableWorld));

        paintShadow = new Paint();
        paintShadow.setColor(getResources().getColor(R.color.worldSelectShadow));

        String[] worldData = getResources().getStringArray(R.array.worldShape);
        worlds = new RectF[worldData.length][];
        for (int i = 0; i < worlds.length; i++) {
            String[] data = worldData[i].split("/");
            worlds[i] = new RectF[data.length];
            for (int j = 0; j < data.length; j++) {
                String[] values = data[j].split(",");
                int left = Integer.parseInt(values[0]);
                int top = Integer.parseInt(values[1]);
                int right = Integer.parseInt(values[2]);
                int bottom = Integer.parseInt(values[3]);
                worlds[i][j] = new RectF(left, top, right, bottom);
            }
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        ratio = 1.f * w / SIZE;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.scale(ratio, ratio);

        canvas.translate(SHADOW_SIZE, SHADOW_SIZE);
        for (RectF[] rects : worlds)
            for (RectF rect : rects)
                canvas.drawRect(rect, paintShadow);

        canvas.translate(-SHADOW_SIZE, -SHADOW_SIZE);
        for (int i = 0; i < worlds.length; i++) {
            RectF[] rects = worlds[i];
            if (i == pointer) {
                canvas.translate(CLICKED_DISTANCE, CLICKED_DISTANCE);
                for (RectF rect : rects)
                    canvas.drawRect(rect, paintClicked);
                canvas.translate(-CLICKED_DISTANCE, -CLICKED_DISTANCE);
            } else
                for (RectF rect : rects)
                    canvas.drawRect(rect, paintWorld);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX() / ratio, y = event.getY() / ratio;

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                for (int i = 0; i < worlds.length; i++) {
                    boolean b = false;
                    for (int j = 0; j < worlds[i].length; j++) {
                        if (worlds[i][j].left < x && x < worlds[i][j].right && worlds[i][j].top < y && y < worlds[i][j].bottom) {
                            b = true;
                            break;
                        }
                    }
                    if (b) {
                        pointer = i;
                        invalidate();
                        break;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if (pointer != -1) {
                    worldHandler.sendEmptyMessage(pointer);
                    pointer = -1;
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (pointer == -1)
                    break;

                boolean b = true;
                for (int i = 0; i < worlds[pointer].length; i++) {
                    if (worlds[pointer][i].left < x && x < worlds[pointer][i].right && worlds[pointer][i].top < y && y < worlds[pointer][i].bottom) {
                        b = false;
                        break;
                    }
                }
                if (b) {
                    pointer = -1;
                    invalidate();
                }
                break;
        }

        return true;
    }

    public void setWorldHandler(Handler worldHandler) {
        this.worldHandler = worldHandler;
    }
}
