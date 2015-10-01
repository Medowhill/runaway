package com.medowhill.jaemin.runaway.object;

import com.medowhill.jaemin.runaway.R;
import com.medowhill.jaemin.runaway.ability.Ability;
import com.medowhill.jaemin.runaway.ability.SlowBulletFire;

/**
 * Created by Jaemin on 2015-10-01.
 */
public class Artillery extends Enemy {

    public Artillery(Stage stage, float x, float y) {
        super(stage, context.getResources().getInteger(R.integer.baseSize) * context.getResources().getInteger(R.integer.artillerySize),
                context.getResources().getColor(R.color.artilleryNormal), context.getResources().getColor(R.color.artilleryDetecting),
                x, y, context.getResources().getInteger(R.integer.artillerySpeed), context.getResources().getInteger(R.integer.artillerySight));

        SlowBulletFire slowBulletFire = new SlowBulletFire(1);
        abilities.add(slowBulletFire);
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
