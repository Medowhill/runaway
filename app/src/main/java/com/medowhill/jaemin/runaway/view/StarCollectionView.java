package com.medowhill.jaemin.runaway.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import com.medowhill.jaemin.runaway.R;
import com.medowhill.jaemin.runaway.object.Star;

/**
 * Created by Jaemin on 2015-10-01.
 */
public class StarCollectionView extends View {

    private final float SIZE_RATIO = 0.8f, SIZE;

    private int width, height;
    private float ratio;
    private Star[] stars;

    public StarCollectionView(Context context, AttributeSet attrs) {
        super(context, attrs);

        SIZE = getResources().getInteger(R.integer.baseSize) * getResources().getInteger(R.integer.starSize) * 2 / SIZE_RATIO;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        width = w;
        height = h;

        stars = new Star[3];
        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Star(null, SIZE * (2 * i + 1) / 2, SIZE / 2);
            stars[i].setCollect(false);
        }

        ratio = w / (SIZE * stars.length);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (stars != null) {
            canvas.scale(ratio, ratio);

            for (Star star : stars)
                star.draw(canvas);
        }
    }

    public void setStarCollect(int index, boolean collect) {
        if (0 <= index && index < stars.length) {
            stars[index].setCollect(collect);
            invalidate();
        }
    }
}
