package com.medowhill.jaemin.runaway.ability;

import com.medowhill.jaemin.runaway.R;
import com.medowhill.jaemin.runaway.buff.Buff;
import com.medowhill.jaemin.runaway.buff.SpeedChangeBuff;
import com.medowhill.jaemin.runaway.object.GameObject;

/**
 * Created by Jaemin on 2015-09-02.
 */
public class Dash extends Ability {

    public Dash(int level) {
        super(level, 80, R.mipmap.skill_icon_dash);
    }

    @Override
    public void use(final GameObject gameObject) {
        super.use(gameObject);

        Buff buff = new SpeedChangeBuff(gameObject, 20, 1);
        gameObject.addBuff(buff);
    }
}
