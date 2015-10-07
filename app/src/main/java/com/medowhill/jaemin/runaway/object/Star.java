package com.medowhill.jaemin.runaway.object;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

import com.medowhill.jaemin.runaway.R;

/**
 * Created by Jaemin on 2015-09-24.
 */
public class Star extends GameObject {

    private final float RATIO1 = 0.7f, RATIO2 = 0.4f;
    private final float DEGREE1 = (float) Math.PI / 15, DEGREE2 = (float) Math.PI / 4;

    private Paint paintNotCollect;

    private boolean collect = false, forGameView = false;

    private Path path;

    public Star(Stage stage, float x, float y, boolean forGameView) {
        super(stage, context.getResources().getInteger(R.integer.starSize) * context.getResources().getInteger(R.integer.baseSize),
                context.getResources().getColor(R.color.starNormal), x, y, 0);

        this.forGameView = forGameView;

        paintNotCollect = new Paint();
        paintNotCollect.setColor(context.getResources().getColor(R.color.starNotCollect));

        path = new Path();
        path.moveTo(x, y - RADIUS);
        path.lineTo(x - RADIUS * RATIO1 * (float) Math.cos(DEGREE1), y - RADIUS * RATIO1 * (float) Math.sin(DEGREE1));
        path.lineTo(x + RADIUS * RATIO2 * (float) Math.cos(DEGREE2), y + RADIUS * RATIO2 * (float) Math.sin(DEGREE2));
        path.lineTo(x, y + RADIUS);
        path.lineTo(x + RADIUS * RATIO1 * (float) Math.cos(DEGREE1), y + RADIUS * RATIO1 * (float) Math.sin(DEGREE1));
        path.lineTo(x - RADIUS * RATIO2 * (float) Math.cos(DEGREE2), y - RADIUS * RATIO2 * (float) Math.sin(DEGREE2));
        path.lineTo(x, y - RADIUS);
    }

    public Star(Stage stage, float x, float y) {
        this(stage, x, y, false);
    }

    @Override
    public void draw(Canvas canvas) {
        if (forGameView) {
            if (!collect)
                canvas.drawPath(path, paintNormal);
        } else {
            if (collect)
                canvas.drawPath(path, paintNormal);
            else
                canvas.drawPath(path, paintNotCollect);
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
