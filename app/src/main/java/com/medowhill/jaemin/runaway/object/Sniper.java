package com.medowhill.jaemin.runaway.object;

import com.medowhill.jaemin.runaway.R;
import com.medowhill.jaemin.runaway.ability.Ability;
import com.medowhill.jaemin.runaway.ability.StunBulletFire;

/**
 * Created by Jaemin on 2015-10-01.
 */
public class Sniper extends Enemy {

    public Sniper(Stage stage, float x, float y) {
        super(stage, context.getResources().getInteger(R.integer.baseSize) * context.getResources().getInteger(R.integer.sniperSize),
                context.getResources().getColor(R.color.sniperNormal), context.getResources().getColor(R.color.sniperDetecting),
                x, y, context.getResources().getInteger(R.integer.sniperSpeed), context.getResources().getInteger(R.integer.sniperSight));

        StunBulletFire stunBulletFire = new StunBulletFire(1);
        abilities.add(stunBulletFire);
    }

    @Override
    public void useAbility() {
        Ability ability = abilities.get(0);

        if (!ability.isWaiting() && detect) {
            if (detect)
                ability.use(this);
        }
    }
}
