package com.medowhill.jaemin.runaway.object;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.medowhill.jaemin.runaway.Direction;
import com.medowhill.jaemin.runaway.ability.Ability;
import com.medowhill.jaemin.runaway.buff.Buff;

import java.util.ArrayList;

/**
 * Created by Jaemin on 2015-09-01.
 */
public abstract class GameObject {

    final float speed;
    float x, y;
    int direction = Direction.NONE;
    float speedMultiplier = 1;

    float width, height;

    Paint paint;

    ArrayList<Ability> abilities;

    ArrayList<Buff> buffs;

    GameObject(float x, float y, float speed, int color) {
        this.x = x;
        this.y = y;
        this.speed = speed;

        this.paint = new Paint();
        this.paint.setColor(color);

        abilities = new ArrayList<>();
        buffs = new ArrayList<>();
    }

    public void move() {
        x += getXSpeed();
        y += getYSpeed();
    }

    public void draw(Canvas canvas) {
        canvas.drawRect(x - width / 2, y - height / 2, x + width / 2, y + height / 2, paint);
    }

    float getXSpeed() {
        if (direction == Direction.LEFT)
            return -1 * speed * speedMultiplier;
        else if (direction == Direction.RIGHT)
            return speed * speedMultiplier;
        else
            return 0;
    }

    float getYSpeed() {
        if (direction == Direction.UP)
            return -1 * speed * speedMultiplier;
        else if (direction == Direction.DOWN)
            return speed * speedMultiplier;
        else
            return 0;
    }
}
