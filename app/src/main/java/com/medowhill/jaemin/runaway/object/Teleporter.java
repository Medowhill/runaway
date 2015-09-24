package com.medowhill.jaemin.runaway.object;

import com.medowhill.jaemin.runaway.R;
import com.medowhill.jaemin.runaway.ability.Ability;
import com.medowhill.jaemin.runaway.ability.Teleportation;

/**
 * Created by Jaemin on 2015-09-10.
 */
public class Teleporter extends Enemy {

    private final float distance;

    public Teleporter(Stage stage, float x, float y) {
        super(stage, context.getResources().getInteger(R.integer.baseSize) * context.getResources().getInteger(R.integer.teleporterSize),
                context.getResources().getColor(R.color.teleporterNormal), context.getResources().getColor(R.color.teleporterDetecting),
                x, y, context.getResources().getInteger(R.integer.teleporterSpeed), context.getResources().getInteger(R.integer.teleporterSight));

        Teleportation teleportation = new Teleportation(1, false);
        abilities.add(teleportation);
        distance = teleportation.getDistance();
    }

    @Override
    public void useAbility() {
        Ability ability = abilities.get(0);

        if (!ability.isWaiting() && (detect || detectIllusion)) {
            Player player = stage.getPlayer();
            if (detectIllusion)
                player = player.getIllusion();
            float x1 = player.getX(), y1 = player.getY();
            float dx = Math.abs(x1 - x), dy = Math.abs(y1 - y);
            float min = Math.min(dx, dy), max = Math.max(dx, dy);
            if (min < RADIUS * 3 && distance * 0.5f < max && max < distance * 1.5f)
                ability.use(this);
        }
    }
}
