package com.medowhill.jaemin.runaway.object;

import android.graphics.Paint;

import com.medowhill.jaemin.runaway.R;

/**
 * Created by Jaemin on 2015-10-01.
 */
public class ShockWaveField extends Field {

    public ShockWaveField(Stage stage, float initialRadius, float radius, int remainingFrame, GameObject owner) {
        super(stage, initialRadius, radius, remainingFrame,
                (owner instanceof Player) ? context.getResources().getColor(R.color.shockWavePlayerStroke) :
                        context.getResources().getColor(R.color.shockWaveEnemyStroke), owner);

        paintNormal.setStyle(Paint.Style.STROKE);
        paintNormal.setStrokeWidth(context.getResources().getInteger(R.integer.shockWaveStrokeWidth));
    }
}
