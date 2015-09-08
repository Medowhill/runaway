package com.medowhill.jaemin.runaway.buff;

import com.medowhill.jaemin.runaway.object.GameObject;
import com.medowhill.jaemin.runaway.object.Player;

/**
 * Copyright 2015. Hong Jaemin
 * All rights reserved.
 */

public class IllusionBuff extends Buff {

    Player player;

    public IllusionBuff(GameObject gameObject, int remainingTime) {
        super(gameObject, remainingTime);

        if (gameObject instanceof Player)
            player = (Player) gameObject;
    }

    @Override
    public void duringBuff() {
        if (player != null) {
            int direction = player.getDirection();
            Player illusion = player.getIllusion();
        }
    }

    @Override
    public void endBuff() {
        if (player != null)
            player.setUsingIllusion(false);
    }

    @Override
    public void startBuff() {
        if (player != null) {
            player.setIllusionLocation();
            player.setUsingIllusion(true);
        }
    }
}
