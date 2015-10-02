package com.medowhill.jaemin.runaway.object;

import com.medowhill.jaemin.runaway.R;

/**
 * Created by Jaemin on 2015-10-01.
 */
public class DistortionFieldBackground extends Field {

    public DistortionFieldBackground(Stage stage, float radius, int remainingFrame, GameObject owner) {
        super(stage, radius, radius, remainingFrame,
                (owner instanceof Player) ? context.getResources().getColor(R.color.distortionFieldPlayerBackground) :
                        context.getResources().getColor(R.color.distortionFieldEnemyBakcground), owner);
    }
}
