package com.medowhill.jaemin.runaway.object;

import com.medowhill.jaemin.runaway.R;

/**
 * Created by Jaemin on 2015-10-07.
 */
public class Tau extends Enemy {

    public Tau(Stage stage, float x, float y) {
        super(stage, context.getResources().getInteger(R.integer.baseSize) * context.getResources().getInteger(R.integer.tauSize),
                context.getResources().getColor(R.color.tauNormal), context.getResources().getColor(R.color.tauDetecting),
                x, y, context.getResources().getInteger(R.integer.tauSpeed),
                context.getResources().getInteger(R.integer.baseSight) * context.getResources().getInteger(R.integer.tauSight));
    }

    @Override
    public void useAbility() {
    }

}
