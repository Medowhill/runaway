package com.medowhill.jaemin.runaway.ability;

import com.medowhill.jaemin.runaway.R;
import com.medowhill.jaemin.runaway.buff.Buff;
import com.medowhill.jaemin.runaway.buff.CannotMoveBuff;
import com.medowhill.jaemin.runaway.buff.CannotUseAbilityBuff;
import com.medowhill.jaemin.runaway.buff.RemoveChannelingBuff;
import com.medowhill.jaemin.runaway.object.Bullet;
import com.medowhill.jaemin.runaway.object.GameObject;
import com.medowhill.jaemin.runaway.object.Player;

/**
 * Created by Jaemin on 2015-10-01.
 */
public class StunBulletFire extends Ability {

    private final int frame;
    private final float speed;

    public StunBulletFire(int level) {
        super(0, context.getResources().getString(R.string.abilityStunBulletFireName));

        WAITING_FRAME = context.getResources().getInteger(R.integer.stunBulletFireEnemyCool);
        frame = context.getResources().getIntArray(R.array.stunBulletFireEnemyFrame)[level - 1];
        speed = context.getResources().getIntArray(R.array.stunBulletFireEnemySpeed)[level - 1];
    }

    @Override
    public void use(GameObject gameObject) {
        super.use(gameObject);

        Player player = gameObject.getStage().player;

        Buff[] buffs = new Buff[3];
        buffs[0] = new CannotMoveBuff(player, frame, false);
        buffs[1] = new CannotUseAbilityBuff(player, frame, false);
        buffs[2] = new RemoveChannelingBuff(player, 1, false);

        Bullet bullet = new Bullet(gameObject.getStage(), context.getResources().getColor(R.color.stunBulletFireBullet),
                gameObject.getX(), gameObject.getY(), speed, gameObject.getDirection(), buffs);
        gameObject.getStage().bullets.add(bullet);
    }
}
