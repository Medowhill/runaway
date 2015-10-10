package com.medowhill.jaemin.runaway.ability;

import com.medowhill.jaemin.runaway.R;
import com.medowhill.jaemin.runaway.buff.Buff;
import com.medowhill.jaemin.runaway.buff.SpeedChangeBuff;
import com.medowhill.jaemin.runaway.object.Bullet;
import com.medowhill.jaemin.runaway.object.GameObject;
import com.medowhill.jaemin.runaway.object.Player;

/**
 * Created by Jaemin on 2015-10-01.
 */
public class SlowBulletFire extends Ability {

    private final int frame;
    private final float speed, dSpeed;

    public SlowBulletFire(int level) {
        super(0);

        WAITING_FRAME = context.getResources().getInteger(R.integer.slowBulletFireEnemyCool);
        frame = context.getResources().getInteger(R.integer.slowBulletFireEnemyFrame);
        speed = context.getResources().getIntArray(R.array.slowBulletFireEnemySpeed)[level - 1];
        dSpeed = context.getResources().getIntArray(R.array.slowBulletFireEnemyDSpeed)[level - 1] / 100.f;
    }

    @Override
    public void use(GameObject gameObject) {
        super.use(gameObject);

        Player player = gameObject.getStage().player;

        Buff[] buffs = new Buff[1];
        buffs[0] = new SpeedChangeBuff(player, frame, dSpeed, false);

        Bullet bullet = new Bullet(gameObject.getStage(), context.getResources().getColor(R.color.slowBulletFireBullet),
                gameObject.getX(), gameObject.getY(), speed, gameObject.getDirection(), buffs);
        gameObject.getStage().bullets.add(bullet);
    }

}
