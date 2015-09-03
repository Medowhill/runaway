package com.medowhill.jaemin.runaway.buff;

import com.medowhill.jaemin.runaway.object.GameObject;

/**
 * Created by Jaemin on 2015-09-03.
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
