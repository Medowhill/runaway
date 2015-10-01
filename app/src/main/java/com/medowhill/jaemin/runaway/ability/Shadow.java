package com.medowhill.jaemin.runaway.ability;

import com.medowhill.jaemin.runaway.R;
import com.medowhill.jaemin.runaway.buff.Buff;
import com.medowhill.jaemin.runaway.buff.IllusionBuff;
import com.medowhill.jaemin.runaway.buff.ReverseDirectionBuff;
import com.medowhill.jaemin.runaway.object.GameObject;
import com.medowhill.jaemin.runaway.object.Player;

/**
 * Copyright 2015. Hong Jaemin
 * All rights reserved.
 */

public class Shadow extends Ability {

    private final int frame;

    public Shadow(int level) {
        super(R.drawable.ability_icon_shadow);

        WAITING_FRAME = context.getResources().getInteger(R.integer.shadowPlayerCool);
        frame = context.getResources().getIntArray(R.array.shadowFrame)[level - 1];
    }

    @Override
    public void use(GameObject gameObject) {
        super.use(gameObject);

        if (gameObject instanceof Player) {
            Player player = (Player) gameObject;
            Player illusion = player.getIllusion();

            Buff buff = new IllusionBuff(gameObject, frame, false);
            gameObject.addBuff(buff);
            buff = new ReverseDirectionBuff(illusion, frame, false);
            illusion.addBuff(buff);
        }
    }
}
