package com.medowhill.jaemin.runaway.object;

import com.medowhill.jaemin.runaway.R;
import com.medowhill.jaemin.runaway.ability.Ability;
import com.medowhill.jaemin.runaway.ability.Dash;

/**
 * Created by Jaemin on 2015-09-10.
 */
public class Chaser extends Enemy {

    private float distance = 625;

    public Chaser(Stage stage, float x, float y) {
        super(stage, context.getResources().getInteger(R.integer.baseSize) * context.getResources().getInteger(R.integer.chaserSize),
                context.getResources().getColor(R.color.chaserNormal), context.getResources().getColor(R.color.chaserDetecting),
                x, y, context.getResources().getInteger(R.integer.chaserSpeed), context.getResources().getInteger(R.integer.chaserSight));

        abilities.add(new Dash(1));
    }

    @Override
    public void useAbility() {
        Ability ability = abilities.get(0);

        if (!ability.isWaiting() && detect) {
            Player player = stage.getPlayer();
            float x1 = player.getX(), y1 = player.getY();
            float dx = Math.abs(x1 - x), dy = Math.abs(y1 - y);
            float min = Math.min(dx, dy), max = Math.max(dx, dy);
            if (min < radius * 2 && max < distance)
                ability.use(this);
        }
    }
}
