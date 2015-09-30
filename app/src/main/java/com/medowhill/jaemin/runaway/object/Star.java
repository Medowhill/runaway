package com.medowhill.jaemin.runaway.object;

import android.graphics.Canvas;
import android.graphics.Path;

import com.medowhill.jaemin.runaway.R;

/**
 * Created by Jaemin on 2015-09-24.
 */
public class Star extends GameObject {

    private final int N;
    private final float INNER_RATIO = 0.35f;

    private Path path;

    public Star(Stage stage, float x, float y) {
        super(stage, context.getResources().getInteger(R.integer.starSize) * context.getResources().getInteger(R.integer.baseSize),
                context.getResources().getColor(R.color.starColor), x, y, 0);

        N = context.getResources().getInteger(R.integer.starVertices);

        path = new Path();
        path.moveTo(x, y - RADIUS);
        for (int i = 1; i <= N; i++) {
            double degree = Math.PI * i / N * 2;
            if (i % 2 == 0)
                path.lineTo(x + RADIUS * (float) Math.sin(degree), y - RADIUS * (float) Math.cos(degree));
            else
                path.lineTo(x + RADIUS * INNER_RATIO * (float) Math.sin(degree),
                        y - RADIUS * INNER_RATIO * (float) Math.cos(degree));
        }
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawPath(path, paintNormal);
    }

    @Override
    void modifyMove(Wall wall) {
    }
}
