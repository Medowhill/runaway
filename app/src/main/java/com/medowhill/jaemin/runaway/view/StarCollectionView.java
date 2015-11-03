package com.medowhill.jaemin.runaway.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

    private float ratio;
    private boolean[] stars;

    private boolean visible = false;

    private Bitmap bitmapCollect, bitmapNotCollect;

    public StarCollectionView(Context context, AttributeSet attrs) {
        super(context, attrs);

        SIZE = getResources().getInteger(R.integer.baseSize) * getResources().getInteger(R.integer.starSize) * 2 / SIZE_RATIO;

        stars = new boolean[3];
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        ratio = w / (SIZE * stars.length);


        Bitmap temp = BitmapFactory.decodeResource(context.getResources(), R.drawable.star_collect);
        bitmapCollect = Bitmap.createScaledBitmap(temp, (int) (RADIUS * 2), (int) (RADIUS * 2), false);
        temp.recycle();

        temp = BitmapFactory.decodeResource(context.getResources(), R.drawable.star_uncollect);
        bitmapNotCollect = Bitmap.createScaledBitmap(temp, (int) (RADIUS * 2), (int) (RADIUS * 2), false);
        temp.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (visible && stars != null) {
            canvas.scale(ratio, ratio);

            for (Star star : stars)
                star.draw(canvas);
        }
    }

    public int size() {
        return stars.length;
    }

    public void setStarCollect(int index, boolean collect) {
        if (0 <= index && index < stars.length)
            stars[index] = collect;
    }

    public void initialize(boolean[] starCollection) {
        for (int i = 0; i < stars.length; i++)
            stars[i] = starCollection[i];
        invalidate();
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
        invalidate();
    }
}
