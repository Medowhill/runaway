package com.medowhill.jaemin.runaway.ability;

import com.medowhill.jaemin.runaway.R;
import com.medowhill.jaemin.runaway.buff.Buff;
import com.medowhill.jaemin.runaway.buff.SubstituteBuff;
import com.medowhill.jaemin.runaway.object.Enemy;
import com.medowhill.jaemin.runaway.object.GameObject;
import com.medowhill.jaemin.runaway.object.Player;

/**
 * Created by Jaemin on 2015-09-03.
 */
public class Substitute extends Ability {

    private float range = 1000;

    private int frame = 60;

    public Substitute(int level) {
        super(level, 360, R.drawable.ability_icon_substitute);
    }

    @Override
    public void use(GameObject gameObject) {
        super.use(gameObject);

        if (gameObject instanceof Player) {
            Player player = (Player) gameObject;
            float x1 = player.getX(), y1 = player.getY();

            float minDistance = range * range;
            Enemy minEnemy = null;

            for (Enemy enemy : gameObject.getStage().enemies) {
                float x2 = enemy.getX(), y2 = enemy.getY();
                float distance = (x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2);
                if (distance < minDistance) {
                    minDistance = distance;
                    minEnemy = enemy;
                }
            }

            if (minEnemy == null) {
                remainWaitingFrame = 0;
                return;
            }

            player.setSubstitute(minEnemy);

            Buff buff = new SubstituteBuff(gameObject, frame);
            gameObject.addBuff(buff);
        }
    }
}
