package com.medowhill.jaemin.runaway.object;

import com.medowhill.jaemin.runaway.R;

/**
 * Copyright 2015. Hong Jaemin
 * All rights reserved.
 */

public class Electron extends Enemy {

    public Electron(Stage stage, float x, float y) {
        super(stage, context.getResources().getInteger(R.integer.baseSize) * context.getResources().getInteger(R.integer.electronSize),
                context.getResources().getColor(R.color.electronNormal), context.getResources().getColor(R.color.electronDetecting),
                x, y, context.getResources().getInteger(R.integer.electronSpeed),
                context.getResources().getInteger(R.integer.baseSight) * context.getResources().getInteger(R.integer.electronSight),
                context.getResources().getString(R.string.enemyElectronName), 'e');
    }

    @Override
    public void useAbility() {
    }
}
