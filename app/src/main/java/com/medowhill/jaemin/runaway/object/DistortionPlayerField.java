package com.medowhill.jaemin.runaway.object;

import com.medowhill.jaemin.runaway.R;

/**
 * Created by Jaemin on 2015-10-01.
 */
public class DistortionPlayerField extends Field {

    public DistortionPlayerField(Stage stage, float initialRadius, float radius, int remainingFrame, GameObject owner) {
        super(stage, initialRadius, radius, remainingFrame, context.getResources().getColor(R.color.distortionFieldPlayerBackground), owner);
    }
}
