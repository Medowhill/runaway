package com.medowhill.jaemin.runaway.ability;

import com.medowhill.jaemin.runaway.R;
import com.medowhill.jaemin.runaway.buff.Buff;
import com.medowhill.jaemin.runaway.buff.IllusionBuff;
import com.medowhill.jaemin.runaway.buff.ReverseDirectionBuff;
import com.medowhill.jaemin.runaway.object.GameObject;
import com.medowhill.jaemin.runaway.object.Player;

/**
 * Created by Jaemin on 2015-09-03.
 */
public class Shadow extends Ability {

    int frame = 60;

    public Shadow(int level) {
        super(level, 360, R.drawable.ability_icon_shadow);
    }

    @Override
    public void use(GameObject gameObject) {
        super.use(gameObject);

        if (gameObject instanceof Player) {
            Player player = (Player) gameObject;
            Player illusion = player.getIllusion();

            Buff buff = new IllusionBuff(gameObject, frame);
            gameObject.addBuff(buff);
            buff = new ReverseDirectionBuff(illusion, frame);
            illusion.addBuff(buff);
        }
    }
}
