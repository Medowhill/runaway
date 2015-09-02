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

    float speed;

    float x, y;

    int direction = Direction.NONE;

    float speedMultiplier = 1;

    float width, height;

    Paint paint;

    ArrayList<Ability> abilities;

    ArrayList<Buff> buffs;

    GameObject() {
        this.paint = new Paint();

        abilities = new ArrayList<>();
        buffs = new ArrayList<>();
    }

    void setX(float x) {
        this.x = x;
    }

    void setY(float y) {
        this.y = y;
    }

    void setSpeed(float speed) {
        this.speed = speed;
    }

    void setColor(int color) {
        paint.setColor(color);
    }

    public void move(ArrayList<Wall> walls) {
        for (Wall wall : walls) {
            if (willTouchAfterMove(wall)) {
                moveUntilWall(wall);
                return;
            }
        }
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


    void moveUntilWall(Wall wall) {
        float location = wall.LOCATION;

        if (wall.HORIZONTAL) {
            if (location < y)
                y = location + height / 2;
            else
                y = location - height / 2;
        } else {
            if (location < x)
                x = location + width / 2;
            else
                x = location - width / 2;
        }
    }

    boolean willTouchAfterMove(Wall wall) {
        float start = wall.START, end = wall.END, location = wall.LOCATION;

        switch (direction) {
            case Direction.DOWN:
            case Direction.UP:
                if (!wall.HORIZONTAL)
                    return false;
                if (start - width / 2 < x && x < end + width / 2) {
                    float y_ = y + getYSpeed();
                    return Math.min(y, y_) - height / 2 < location && location < Math.max(y, y_) + height / 2;
                } else
                    return false;

            case Direction.RIGHT:
            case Direction.LEFT:
                if (wall.HORIZONTAL)
                    return false;
                if (start - height / 2 < y && y < end + height / 2) {
                    float x_ = x + getXSpeed();
                    return Math.min(x, x_) - width / 2 < location && location < Math.max(x, x_) + width / 2;
                } else
                    return false;

            default:
                return false;
        }
    }
}
