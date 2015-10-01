package com.medowhill.jaemin.runaway.ability;

import com.medowhill.jaemin.runaway.R;
import com.medowhill.jaemin.runaway.buff.Buff;
import com.medowhill.jaemin.runaway.buff.InvisibleBuff;
import com.medowhill.jaemin.runaway.object.GameObject;

/**
 * Copyright 2015. Hong Jaemin
 * All rights reserved.
 */

public class Hiding extends Ability {

    private final int frame;

    public Hiding(int level, boolean player) {
        super(R.drawable.ability_icon_hiding);

        if (player) {
            WAITING_FRAME = context.getResources().getInteger(R.integer.hidingPlayerCool);
            frame = context.getResources().getIntArray(R.array.hidingPlayerFrame)[level - 1];
        } else {
            WAITING_FRAME = context.getResources().getInteger(R.integer.hidingEnemyCool);
            frame = context.getResources().getIntArray(R.array.hidingEnemyFrame)[level - 1];
        }
    }

    @Override
    public void use(GameObject gameObject) {
        super.use(gameObject);

        Buff buff = new InvisibleBuff(gameObject, frame, false);
        gameObject.addBuff(buff);
    }
}
