package com.medowhill.jaemin.runaway.object;

import com.medowhill.jaemin.runaway.Direction;

/**
 * Created by Jaemin on 2015-09-01.
 */
public class Wall {

    public final static int HORIZONTAL = 0, VERTICAL = 1;

    private int direction;

    private float start, end;
    private float location;

    public Wall(int direction, float start, float end, float location) {
        this.direction = direction;
        this.start = start;
        this.end = end;
        this.location = location;
    }

    float distance(GameObject gameObject) {
        if (direction == HORIZONTAL) {
            float y = gameObject.y, height = gameObject.height / 2;
            if (location < y)
                return location - (y - height);
            else
                return location - (y + height);
        } else {
            float x = gameObject.x, width = gameObject.width / 2;
            if (location < x)
                return location - (x - width);
            else
                return location - (x + width);
        }
    }

    boolean willTouchAfterMove(GameObject gameObject) {
        float x = gameObject.x, y = gameObject.y, width = gameObject.width / 2, height = gameObject.height / 2;

        switch (gameObject.direction) {
            case Direction.DOWN:
            case Direction.UP:
                if (direction == VERTICAL)
                    return false;
                if (start - width < x && x < end + width) {
                    float y_ = gameObject.y + gameObject.getYSpeed();
                    return Math.min(y, y_) - height < location && location < Math.max(y, y_) + height;
                } else
                    return false;

            case Direction.RIGHT:
            case Direction.LEFT:
                if (direction == HORIZONTAL)
                    return false;
                if (start - height < y && y < end + height) {
                    float x_ = gameObject.x + gameObject.getXSpeed();
                    return Math.min(x, x_) - width < location && location < Math.max(x, x_) + width;
                } else
                    return false;

            default:
                return false;
        }
    }

}
