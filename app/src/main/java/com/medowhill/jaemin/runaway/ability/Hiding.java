package com.medowhill.jaemin.runaway.ability;

import com.medowhill.jaemin.runaway.R;
import com.medowhill.jaemin.runaway.buff.Buff;
import com.medowhill.jaemin.runaway.buff.DelayBuff;
import com.medowhill.jaemin.runaway.buff.InvisibleBuff;
import com.medowhill.jaemin.runaway.object.GameObject;

/**
 * Created by Jaemin on 2015-09-03.
 */
public class Hiding extends Ability {

    int waitingFrame = 20;
    int frame = 40;

    public Hiding(int level) {
        super(level, 240, R.drawable.ability_icon_hiding);
    }

    @Override
    public void use(GameObject gameObject) {
        super.use(gameObject);

        Buff buff = new InvisibleBuff(gameObject, frame);
        gameObject.addBuff(new DelayBuff(gameObject, waitingFrame, buff));
    }
}
