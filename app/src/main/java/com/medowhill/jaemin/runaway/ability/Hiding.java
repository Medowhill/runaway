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

    int frame = 80;

    public Hiding(int level) {
        super(level, 240, R.drawable.ability_icon_hiding);
    }

    @Override
    public void use(GameObject gameObject) {
        super.use(gameObject);

        Buff buff = new InvisibleBuff(gameObject, frame);
        gameObject.addBuff(buff);
    }
}
