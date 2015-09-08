package com.medowhill.jaemin.runaway.buff;

import com.medowhill.jaemin.runaway.object.GameObject;

/**
 * Copyright 2015. Hong Jaemin
 * All rights reserved.
 */

public class CannotUseAbilityBuff extends Buff {

    public CannotUseAbilityBuff(GameObject gameObject, int remainingTime) {
        super(gameObject, remainingTime);
        abilityUsable = false;
    }

    @Override
    public void duringBuff() {

    }

    @Override
    public void endBuff() {
        abilityUsable = true;
        gameObject.setAbilityUsable();
    }

    @Override
    public void startBuff() {
        gameObject.setAbilityUsable();
    }
}
