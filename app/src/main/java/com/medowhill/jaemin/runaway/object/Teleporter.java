package com.medowhill.jaemin.runaway.object;

import com.medowhill.jaemin.runaway.R;
import com.medowhill.jaemin.runaway.ability.Ability;
import com.medowhill.jaemin.runaway.ability.Teleportation;

/**
 * Created by Jaemin on 2015-09-10.
 */
public class Teleporter extends Enemy {

    private final float distance = 625;

    public Teleporter(Stage stage, float x, float y) {
        super(stage, context.getResources().getInteger(R.integer.baseSize) * context.getResources().getInteger(R.integer.teleporterSize),
                context.getResources().getColor(R.color.teleporterNormal), context.getResources().getColor(R.color.teleporterDetecting),
                x, y, context.getResources().getInteger(R.integer.teleporterSpeed), context.getResources().getInteger(R.integer.teleporterSight));

        abilities.add(new Teleportation(1));
    }

    @Override
    public void useAbility() {
        Ability ability = abilities.get(0);

        if (!ability.isWaiting() && detect) {
            Player player = stage.getPlayer();
            float x1 = player.getX(), y1 = player.getY();
            float dx = Math.abs(x1 - x), dy = Math.abs(y1 - y);
            float min = Math.min(dx, dy), max = Math.max(dx, dy);
            if (min < radius * 2 && distance * 2 / 3 < max && max < distance * 4 / 3)
                ability.use(this);
        }
    }
}
