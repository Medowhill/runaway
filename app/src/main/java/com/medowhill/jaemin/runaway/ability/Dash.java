package com.medowhill.jaemin.runaway.ability;

import com.medowhill.jaemin.runaway.Direction;
import com.medowhill.jaemin.runaway.R;
import com.medowhill.jaemin.runaway.buff.Buff;
import com.medowhill.jaemin.runaway.buff.CannotModifyDirectionBuff;
import com.medowhill.jaemin.runaway.buff.CannotUseAbilityBuff;
import com.medowhill.jaemin.runaway.buff.SpeedChangeBuff;
import com.medowhill.jaemin.runaway.object.GameObject;

/**
 * Created by Jaemin on 2015-09-02.
 */
public class Dash extends Ability {

    int frame;
    float speed;

    public Dash(int level) {
        super(level, 120, R.drawable.skill_icon_dash);
        frame = 10;
        speed = 1.5f;
    }

    @Override
    public void use(final GameObject gameObject) {
        super.use(gameObject);

        if (gameObject.getDirection() == Direction.NONE) {
            remainWaitingFrame = 0;
            return;
        }

        Buff buff = new SpeedChangeBuff(gameObject, frame, speed);
        gameObject.addBuff(buff);
        buff = new CannotModifyDirectionBuff(gameObject, frame);
        gameObject.addBuff(buff);
        buff = new CannotUseAbilityBuff(gameObject, frame);
        gameObject.addBuff(buff);
    }
}
