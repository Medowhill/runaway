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

    int frame = 80;

    public Illusion(int level) {
        super(level, 360, R.drawable.ability_icon_illusion);
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
