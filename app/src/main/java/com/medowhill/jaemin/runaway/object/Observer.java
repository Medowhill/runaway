package com.medowhill.jaemin.runaway.object;

import com.medowhill.jaemin.runaway.R;

/**
 * Copyright 2015. Hong Jaemin
 * All rights reserved.
 */

public class Observer extends Enemy {

    public Observer(Stage stage, float x, float y) {
        super(stage, context.getResources().getInteger(R.integer.baseSize) * context.getResources().getInteger(R.integer.observerSize),
                context.getResources().getColor(R.color.observerNormal), context.getResources().getColor(R.color.observerDetecting),
                x, y, context.getResources().getInteger(R.integer.observerSpeed), context.getResources().getInteger(R.integer.observerSight));
    }

}
