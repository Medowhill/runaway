package com.medowhill.jaemin.runaway.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Jaemin on 2015-10-07.
 */
public class FadeView extends View {

    private int color;

    public FadeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(color);
    }

    public void fadeOut(int duration) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, 255);
        valueAnimator.setDuration(duration);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                color = Color.argb((Integer) animation.getAnimatedValue(), 0, 0, 0);
                invalidate();
            }
        });
        valueAnimator.start();
    }

    public void initialize() {
        color = Color.argb(0, 0, 0, 0);
        invalidate();
    }
}
