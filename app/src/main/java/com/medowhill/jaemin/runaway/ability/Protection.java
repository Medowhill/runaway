package com.medowhill.jaemin.runaway.ability;

import com.medowhill.jaemin.runaway.R;
import com.medowhill.jaemin.runaway.buff.Buff;
import com.medowhill.jaemin.runaway.buff.ImmortalBuff;
import com.medowhill.jaemin.runaway.object.GameObject;

/**
 * Created by Jaemin on 2015-09-03.
 */
public class Protection extends Ability {

    int frame = 40;

    public Protection(int level) {
        super(level, 240, R.drawable.ability_icon_protection);
    }

    @Override
    public void use(GameObject gameObject) {
        super.use(gameObject);

        Buff buff = new ImmortalBuff(gameObject, frame);
        gameObject.addBuff(buff);
    }
}
