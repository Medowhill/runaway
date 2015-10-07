package com.medowhill.jaemin.runaway.object;

import com.medowhill.jaemin.runaway.R;

/**
 * Created by Jaemin on 2015-10-07.
 */
public class Photon extends Enemy {

    public Photon(Stage stage, float x, float y) {
        super(stage, context.getResources().getInteger(R.integer.baseSize) * context.getResources().getInteger(R.integer.photonSize),
                context.getResources().getColor(R.color.photonNormal), context.getResources().getColor(R.color.photonDetecting),
                x, y, context.getResources().getInteger(R.integer.photonSpeed),
                context.getResources().getInteger(R.integer.baseSight) * context.getResources().getInteger(R.integer.photonSight));
    }

    @Override
    public void useAbility() {
    }

}
