package com.medowhill.jaemin.runaway.ability;

import com.medowhill.jaemin.runaway.Direction;
import com.medowhill.jaemin.runaway.R;
import com.medowhill.jaemin.runaway.buff.Buff;
import com.medowhill.jaemin.runaway.buff.CannotModifyDirectionBuff;
import com.medowhill.jaemin.runaway.buff.CannotMoveBuff;
import com.medowhill.jaemin.runaway.buff.CannotUseAbilityBuff;
import com.medowhill.jaemin.runaway.buff.DelayBuff;
import com.medowhill.jaemin.runaway.buff.SpeedChangeBuff;
import com.medowhill.jaemin.runaway.object.GameObject;

/**
 * Created by Jaemin on 2015-09-03.
 */
public class Teleportation extends Ability {

    int frame = 10;
    float speed = 1.5f;

    public Teleportation(int level) {
        super(level, 120, R.drawable.skill_icon_teleportation);
    }

    @Override
    public void use(GameObject gameObject) {
        super.use(gameObject);

        if (gameObject.getDirection() == Direction.NONE) {
            remainWaitingFrame = 0;
            return;
        }

        Buff buff = new SpeedChangeBuff(gameObject, 1, (speed + 1) * frame - 1);
        gameObject.addBuff(new DelayBuff(gameObject, frame, buff));
        buff = new CannotModifyDirectionBuff(gameObject, frame);
        gameObject.addBuff(buff);
        buff = new CannotMoveBuff(gameObject, frame);
        gameObject.addBuff(buff);
        buff = new CannotUseAbilityBuff(gameObject, frame);
        gameObject.addBuff(buff);
    }
}
