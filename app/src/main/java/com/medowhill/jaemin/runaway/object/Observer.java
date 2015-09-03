package com.medowhill.jaemin.runaway.object;

import com.medowhill.jaemin.runaway.R;

/**
 * Created by Jaemin on 2015-09-03.
 */
public class Observer extends Enemy {

    public Observer(float x, float y) {
        super(context.getResources().getInteger(R.integer.baseSize) * context.getResources().getInteger(R.integer.observerSize),
                context.getResources().getColor(R.color.observerNormal), context.getResources().getColor(R.color.observerDetecting), x, y,
                context.getResources().getInteger(R.integer.observerSpeed), context.getResources().getInteger(R.integer.observerSight));
    }

}
