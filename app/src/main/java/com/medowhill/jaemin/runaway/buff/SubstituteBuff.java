package com.medowhill.jaemin.runaway.buff;

import com.medowhill.jaemin.runaway.object.GameObject;
import com.medowhill.jaemin.runaway.object.Player;

/**
 * Created by Jaemin on 2015-09-04.
 */
public class SubstituteBuff extends Buff {

    Player player;

    public SubstituteBuff(GameObject gameObject, int remainingTime) {
        super(gameObject, remainingTime);

        if (gameObject instanceof Player)
            player = (Player) gameObject;
    }

    @Override
    public void duringBuff() {

    }

    @Override
    public void endBuff() {
        if (player != null) {
            player.setUsingSubstitute(false);
            player.getSubstitute().setSubstitute(false);
        }
    }

    @Override
    public void startBuff() {
        if (player != null) {
            player.setUsingSubstitute(true);
            player.getSubstitute().setSubstitute(true);
        }
    }
}
