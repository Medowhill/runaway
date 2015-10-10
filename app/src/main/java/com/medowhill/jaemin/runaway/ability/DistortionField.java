package com.medowhill.jaemin.runaway.ability;

import com.medowhill.jaemin.runaway.R;
import com.medowhill.jaemin.runaway.buff.DistortionFieldBuff;
import com.medowhill.jaemin.runaway.object.DistortionFieldBackground;
import com.medowhill.jaemin.runaway.object.GameObject;

/**
 * Copyright 2015. Hong Jaemin
 * All rights reserved.
 */

public class DistortionField extends Ability {

    private final float range;

    private final int frame;
    private final float dspeed;

    public DistortionField(int levelRange, int levelDSpeed, boolean player) {
        super(R.drawable.ability_icon_distortionfield, context.getResources().getString(R.string.abilityDistortionFieldName));

        if (player) {
            WAITING_FRAME = context.getResources().getInteger(R.integer.distortionFieldPlayerCool);
            range = context.getResources().getIntArray(R.array.distortionFieldPlayerRange)[levelRange - 1];
            dspeed = context.getResources().getIntArray(R.array.distortionFieldPlayerDSpeed)[levelDSpeed - 1] / 100f;
            frame = context.getResources().getInteger(R.integer.distortionFieldPlayerFrame);
        } else {
            WAITING_FRAME = context.getResources().getInteger(R.integer.distortionFieldEnemyCool);
            range = context.getResources().getIntArray(R.array.distortionFieldEnemyRange)[levelRange - 1];
            dspeed = context.getResources().getIntArray(R.array.distortionFieldEnemyDSpeed)[levelDSpeed - 1] / 100f;
            frame = context.getResources().getInteger(R.integer.distortionFieldEnemyFrame);
        }
    }

    @Override
    public void use(GameObject gameObject) {
        super.use(gameObject);

        gameObject.addBuff(new DistortionFieldBuff(gameObject, frame, range, dspeed, false));

        gameObject.getStage().fields.add(new DistortionFieldBackground(gameObject.getStage(), range, frame, gameObject));
    }

    public float getRange() {
        return range;
    }
}
