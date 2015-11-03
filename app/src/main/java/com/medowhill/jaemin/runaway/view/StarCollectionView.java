package com.medowhill.jaemin.runaway.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import com.medowhill.jaemin.runaway.R;

/**
 * Created by Jaemin on 2015-10-01.
 */
public class StarCollectionView extends View {

    private final float SIZE_RATIO = 0.8f;

    private boolean[] stars;

    private boolean visible = false;

    private Bitmap bitmapCollect, bitmapNotCollect;

    public StarCollectionView(Context context, AttributeSet attrs) {
        super(context, attrs);

        stars = new boolean[3];
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        int size = (int) (SIZE_RATIO * getWidth() / stars.length);

        Bitmap temp = BitmapFactory.decodeResource(getResources(), R.drawable.star_collect);
        bitmapCollect = Bitmap.createScaledBitmap(temp, size, size, false);
        temp.recycle();

        temp = BitmapFactory.decodeResource(getResources(), R.drawable.star_uncollect);
        bitmapNotCollect = Bitmap.createScaledBitmap(temp, size, size, false);
        temp.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = getWidth() / stars.length;
        int height = getHeight();
        int size = (int) (SIZE_RATIO * getWidth() / stars.length);

        if (visible && stars != null) {
            for (int i = 0; i < stars.length; i++)
                if (stars[i])
                    canvas.drawBitmap(bitmapCollect, width * i + width / 2 - size / 2, height / 2 - size / 2, null);
                else
                    canvas.drawBitmap(bitmapNotCollect, width * i + width / 2 - size / 2, height / 2 - size / 2, null);
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
