package com.medowhill.jaemin.runaway.object;

import com.medowhill.jaemin.runaway.R;
import com.medowhill.jaemin.runaway.ability.Ability;
import com.medowhill.jaemin.runaway.ability.Teleportation;

/**
 * Created by Jaemin on 2015-09-10.
 */
public class StrangeQuark extends Enemy {

    private final float distance;

    public StrangeQuark(Stage stage, float x, float y) {
        super(stage, context.getResources().getInteger(R.integer.baseSize) * context.getResources().getInteger(R.integer.strangeQuarkSize),
                context.getResources().getColor(R.color.strangeQuarkNormal), context.getResources().getColor(R.color.strangeQuarkDetecting),
                x, y, context.getResources().getInteger(R.integer.strangeQuarkSpeed),
                context.getResources().getInteger(R.integer.baseSight) * context.getResources().getInteger(R.integer.strangeQuarkSight));

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
            if (min < RADIUS * 2 && distance * 0.75f < max && max < distance * 1.25f)
                ability.use(this);
        }
    }
}
