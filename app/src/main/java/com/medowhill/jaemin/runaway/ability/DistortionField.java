package com.medowhill.jaemin.runaway.ability;

import com.medowhill.jaemin.runaway.R;
import com.medowhill.jaemin.runaway.buff.DistortionFieldBuff;
import com.medowhill.jaemin.runaway.object.GameObject;

/**
 * Copyright 2015. Hong Jaemin
 * All rights reserved.
 */

public class DistortionField extends Ability {

    private final float range;

    private final int frame;
    private final float dspeed;

    public DistortionField(int level) {
        super(level, 480, R.drawable.ability_icon_distortionfield);

        range = 500;
        frame = 60;
        dspeed = -0.5f;
    }

    @Override
    public void use(GameObject gameObject) {
        super.use(gameObject);

        gameObject.addBuff(new DistortionFieldBuff(gameObject, frame, range, dspeed));
    }
}
