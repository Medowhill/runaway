package com.medowhill.jaemin.runaway.ability;

import com.medowhill.jaemin.runaway.R;
import com.medowhill.jaemin.runaway.object.GameObject;

/**
 * Created by Jaemin on 2015-09-08.
 */
public class Illusion extends Ability {

    int frame = 40;

    public Illusion(int level) {
        super(level, 360, R.drawable.ability_icon_illusion);
    }

    @Override
    public void use(GameObject gameObject) {
        super.use(gameObject);
    }
}
