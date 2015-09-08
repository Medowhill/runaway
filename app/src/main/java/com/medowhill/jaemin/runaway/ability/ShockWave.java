package com.medowhill.jaemin.runaway.ability;

import com.medowhill.jaemin.runaway.R;
import com.medowhill.jaemin.runaway.object.GameObject;

/**
 * Created by Jaemin on 2015-09-08.
 */
public class ShockWave extends Ability {

    public ShockWave(int level) {
        super(level, 480, R.drawable.ability_icon_shockwave);
    }

    @Override
    public void use(GameObject gameObject) {
        super.use(gameObject);


    }
}
