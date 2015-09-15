package com.medowhill.jaemin.runaway.ability;

import com.medowhill.jaemin.runaway.R;
import com.medowhill.jaemin.runaway.buff.Buff;
import com.medowhill.jaemin.runaway.buff.CannotMoveBuff;
import com.medowhill.jaemin.runaway.buff.IllusionBuff;
import com.medowhill.jaemin.runaway.object.GameObject;
import com.medowhill.jaemin.runaway.object.Player;

/**
 * Copyright 2015. Hong Jaemin
 * All rights reserved.
 */

public class Illusion extends Ability {

    private final int frame;

    public Illusion(int level) {
        super(R.drawable.ability_icon_illusion);

        WAITING_FRAME = context.getResources().getInteger(R.integer.illusionPlayerCool);
        frame = context.getResources().getIntArray(R.array.illusionFrame)[level - 1];
    }

    @Override
    public void use(GameObject gameObject) {
        super.use(gameObject);

        if (gameObject instanceof Player) {
            Player player = (Player) gameObject;
            Player illusion = player.getIllusion();

            illusion.setDirection(player.getDirection());

            Buff buff = new IllusionBuff(gameObject, frame);
            gameObject.addBuff(buff);
            buff = new CannotMoveBuff(illusion, frame);
            illusion.addBuff(buff);
        }
    }
}
