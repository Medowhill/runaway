package com.medowhill.jaemin.runaway.object;

import android.graphics.Canvas;

import com.medowhill.jaemin.runaway.R;

/**
 * Created by Jaemin on 2015-10-01.
 */
public class Finish extends GameObject {

    private final int PERIOD;
    private int frame = 0;

    public Finish(Stage stage, float x, float y) {
        super(stage, context.getResources().getInteger(R.integer.baseSize) * context.getResources().getInteger(R.integer.finishSize),
                context.getResources().getColor(R.color.finish), x, y, 0);

        PERIOD = context.getResources().getInteger(R.integer.gameFinishPeriod);
    }

    public void framePass() {
        frame++;
        if (frame >= PERIOD)
            frame = 0;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawCircle(x, y, RADIUS * (PERIOD - frame) / PERIOD, paintNormal);
    }

    @Override
    void modifyMove(Wall wall) {
    }
}
