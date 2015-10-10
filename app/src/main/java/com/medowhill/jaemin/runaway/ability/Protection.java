package com.medowhill.jaemin.runaway.ability;

import com.medowhill.jaemin.runaway.R;
import com.medowhill.jaemin.runaway.buff.Buff;
import com.medowhill.jaemin.runaway.buff.ImmortalBuff;
import com.medowhill.jaemin.runaway.object.GameObject;

/**
 * Copyright 2015. Hong Jaemin
 * All rights reserved.
 */

public class Protection extends Ability {

    private final int frame;

    public Protection(int level) {
        super(R.drawable.ability_icon_protection, context.getResources().getString(R.string.abilityProtectionName));

        WAITING_FRAME = context.getResources().getInteger(R.integer.protectionPlayerCool);
        frame = context.getResources().getIntArray(R.array.protectionFrame)[level - 1];
    }

    @Override
    public void use(GameObject gameObject) {
        super.use(gameObject);

        Buff buff = new ImmortalBuff(gameObject, frame, false);
        gameObject.addBuff(buff);
    }
}
