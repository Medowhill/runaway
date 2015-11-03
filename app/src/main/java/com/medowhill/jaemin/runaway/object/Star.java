package com.medowhill.jaemin.runaway.object;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.medowhill.jaemin.runaway.R;

/**
 * Created by Jaemin on 2015-09-24.
 */
public class Star extends GameObject {

    private boolean collect = false, forGameView = false;

    private Bitmap bitmapCollect, bitmapNotCollect;

    public Star(Stage stage, float x, float y, boolean forGameView) {
        super(stage, context.getResources().getInteger(R.integer.starSize) * context.getResources().getInteger(R.integer.baseSize), 0, x, y, 0);

        this.forGameView = forGameView;

        Bitmap temp = BitmapFactory.decodeResource(context.getResources(), R.drawable.star_collect);
        bitmapCollect = Bitmap.createScaledBitmap(temp, (int) (RADIUS * 2), (int) (RADIUS * 2), false);
        temp.recycle();

        temp = BitmapFactory.decodeResource(context.getResources(), R.drawable.star_uncollect);
        bitmapNotCollect = Bitmap.createScaledBitmap(temp, (int) (RADIUS * 2), (int) (RADIUS * 2), false);
        temp.recycle();
    }

    public Star(Stage stage, float x, float y) {
        this(stage, x, y, false);
    }

    @Override
    public void draw(Canvas canvas) {
        if (forGameView) {
            if (!collect)
                canvas.drawBitmap(bitmapCollect, x - RADIUS, y - RADIUS, null);
        } else {
            if (collect)
                canvas.drawBitmap(bitmapCollect, x - RADIUS, y - RADIUS, null);
            else
                canvas.drawBitmap(bitmapNotCollect, x - RADIUS, y - RADIUS, null);
        }
    }

    @Override
    void modifyMove(Wall wall) {
    }

    public boolean isCollect() {
        return collect;
    }

    public void setCollect(boolean collect) {
        this.collect = collect;
    }
}
