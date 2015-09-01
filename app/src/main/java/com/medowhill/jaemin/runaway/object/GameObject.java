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

    float x, y;

    int direction = Direction.NONE;

    float speed;

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
        switch (direction) {
            case Direction.UP:
                y -= speed * speedMultiplier;
                break;
            case Direction.RIGHT:
                x += speed * speedMultiplier;
                break;
            case Direction.DOWN:
                y += speed * speedMultiplier;
                break;
            case Direction.LEFT:
                x -= speed * speedMultiplier;
                break;
        }
    }

    public void draw(Canvas canvas) {
        canvas.drawRect(x - width / 2, y - height / 2, x + width / 2, y + height / 2, paint);
    }
}
