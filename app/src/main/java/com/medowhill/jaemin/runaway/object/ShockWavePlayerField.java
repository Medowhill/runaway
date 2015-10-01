package com.medowhill.jaemin.runaway.object;

import android.graphics.Paint;

import com.medowhill.jaemin.runaway.R;

/**
 * Created by Jaemin on 2015-10-01.
 */
public class ShockWavePlayerField extends Field {

    public ShockWavePlayerField(Stage stage, float radius, int remainingFrame, GameObject owner) {
        super(stage, context.getResources().getInteger(R.integer.baseSize) * context.getResources().getInteger(R.integer.playerSize),
                radius, remainingFrame, context.getResources().getColor(R.color.shockWavePlayerStroke), owner);

        paintNormal.setStyle(Paint.Style.STROKE);
        paintNormal.setStrokeWidth(context.getResources().getInteger(R.integer.shockWavePlayerStrokeWidth));
    }
}
