package com.medowhill.jaemin.runaway.object;

import com.medowhill.jaemin.runaway.R;

/**
 * Created by Jaemin on 2015-10-07.
 */
public class Muon extends Enemy {

    public Muon(Stage stage, float x, float y) {
        super(stage, context.getResources().getInteger(R.integer.baseSize) * context.getResources().getInteger(R.integer.muonSize),
                context.getResources().getColor(R.color.muonNormal), context.getResources().getColor(R.color.muonDetecting),
                x, y, context.getResources().getInteger(R.integer.muonSpeed),
                context.getResources().getInteger(R.integer.baseSight) * context.getResources().getInteger(R.integer.muonSight));
    }

    @Override
    public void useAbility() {
    }
}
