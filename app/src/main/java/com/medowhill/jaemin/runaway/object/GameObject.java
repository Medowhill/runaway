package com.medowhill.jaemin.runaway.object;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.medowhill.jaemin.runaway.Direction;
import com.medowhill.jaemin.runaway.ability.Ability;
import com.medowhill.jaemin.runaway.buff.Buff;

import java.util.ArrayList;

/**
 * Copyright 2015. Hong Jaemin
 * All rights reserved.
 */

public abstract class GameObject {

    static Context context;

    // Size
    final float radius;

    // Color
    Paint paintNormal;

    // Location
    float x, y;

    // Direction
    int direction = Direction.NONE;

    // Speed
    float speed;
    float speedMultiplier = 1;

    // State
    boolean movable = true, directionModifiable = true, abilityUsable = true, visible = true, mortal = true;

    // Ability
    ArrayList<Ability> abilities;

    // Buff
    ArrayList<Buff> buffs;

    // Stage
    Stage stage;

    // Constructor
    GameObject(Stage stage, float radius, int color, float x, float y, float speed) {
        this.stage = stage;

        this.radius = radius;

        paintNormal = new Paint();
        paintNormal.setColor(color);

        this.x = x;
        this.y = y;
        this.speed = speed;

        abilities = new ArrayList<>();
        buffs = new ArrayList<>();
    }

    // Static Setter

    public static void setContext(Context context) {
        GameObject.context = context;
    }

    // Setter & Getter

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public float getSpeed() {
        return speed;
    }

    void setSpeed(float speed) {
        this.speed = speed;
    }

    public Stage getStage() {
        return stage;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public ArrayList<Buff> getBuffs() {
        return buffs;
    }

    public void addBuff(Buff buff) {
        buffs.add(buff);
    }

    public ArrayList<Ability> getAbilities() {
        return abilities;
    }

    public boolean isAbilityUsable() {
        return abilityUsable;
    }

    public boolean isVisible() {
        return visible;
    }

    public boolean isMortal() {
        return mortal;
    }

    // State Setter

    public void setAbilityUsable() {
        abilityUsable = true;
        for (Buff buff : buffs) {
            if (!buff.isAbilityUsable()) {
                abilityUsable = false;
                return;
            }
        }
    }

    public void setMovable() {
        movable = true;
        for (Buff buff : buffs) {
            if (!buff.isMovable()) {
                movable = false;
                return;
            }
        }
    }

    public void setDirectionModifiable() {
        directionModifiable = true;
        for (Buff buff : buffs) {
            if (!buff.isDirectionModifiable()) {
                directionModifiable = false;
                return;
            }
        }
    }

    public void setVisible() {
        visible = true;
        for (Buff buff : buffs) {
            if (!buff.isVisible()) {
                visible = false;
                return;
            }
        }
    }

    public void setMortal() {
        mortal = true;
        for (Buff buff : buffs) {
            if (!buff.isMortal()) {
                mortal = false;
                return;
            }
        }
    }

    // Speed Setter

    public void modifySpeedMultiplier(float dSpeed) {
        speedMultiplier += dSpeed;
    }

    // Check Touch

    public boolean touch(GameObject gameObject) {
        float x1 = gameObject.x, y1 = gameObject.y, radius1 = gameObject.radius;
        return (x - x1) * (x - x1) + (y - y1) * (y - y1) < (radius + radius1) * (radius + radius1);
    }

    // Moving Method

    public void move() {
        if (movable) {
            float dx = 0, dy = 0;

            switch (direction) {
                case Direction.LEFT:
                    dx = -1 * speed * speedMultiplier;
                    break;
                case Direction.RIGHT:
                    dx = speed * speedMultiplier;
                    break;
                case Direction.UP:
                    dy = -1 * speed * speedMultiplier;
                    break;
                case Direction.DOWN:
                    dy = speed * speedMultiplier;
                    break;
            }

            move(dx, dy);
        }
    }

    public void move(float dx, float dy) {
        if (dx != 0) {
            boolean modify = false;
            for (Wall wall : stage.walls) {
                if (willTouchAfterMove(wall, dx, true)) {
                    moveToWall(wall);
                    modifyMove(wall);
                    modify = true;
                    break;
                }
            }
            if (!modify)
                x += dx;
        }

        if (dy != 0) {
            boolean modify = false;
            for (Wall wall : stage.walls) {
                if (willTouchAfterMove(wall, dy, false)) {
                    moveToWall(wall);
                    modifyMove(wall);
                    modify = true;
                    break;
                }
            }
            if (!modify)
                y += dy;
        }
    }

    boolean willTouchAfterMove(Wall wall, float distance, boolean horizontal) {
        float start = wall.START, end = wall.END, location = wall.LOCATION;

        if (horizontal) {
            if (wall.HORIZONTAL)
                return false;

            if (start - radius < y && y < end + radius) {
                float x_ = x + distance, dx;
                if (start - radius < y && y < start)
                    dx = (float) Math.sqrt(radius * radius - (start - y) * (start - y));
                else if (y < end)
                    dx = radius;
                else
                    dx = (float) Math.sqrt(radius * radius - (end - y) * (end - y));
                return Math.min(x, x_) - dx < location && location < Math.max(x, x_) + dx;
            } else
                return false;
        } else {
            if (!wall.HORIZONTAL)
                return false;

            if (start - radius < x && x < end + radius) {
                float y_ = y + distance, dy;
                if (start - radius < x && x < start)
                    dy = (float) Math.sqrt(radius * radius - (start - x) * (start - x));
                else if (x < end)
                    dy = radius;
                else
                    dy = (float) Math.sqrt(radius * radius - (end - x) * (end - x));
                return Math.min(y, y_) - dy < location && location < Math.max(y, y_) + dy;
            } else
                return false;
        }
    }

    void moveToWall(Wall wall) {
        float start = wall.START, end = wall.END, location = wall.LOCATION;

        if (wall.HORIZONTAL) {
            float dy = 0;
            if (start - radius < x && x < start)
                dy = (float) Math.sqrt(radius * radius - (start - x) * (start - x));
            else if (x < end)
                dy = radius;
            else if (x < end + radius)
                dy = (float) Math.sqrt(radius * radius - (end - x) * (end - x));

            if (location < y)
                y = location + dy;
            else
                y = location - dy;
        } else {
            float dx = 0;
            if (start - radius < y && y < start)
                dx = (float) Math.sqrt(radius * radius - (start - y) * (start - y));
            else if (y < end)
                dx = radius;
            else if (y < end + radius)
                dx = (float) Math.sqrt(radius * radius - (end - y) * (end - y));

            if (location < x)
                x = location + dx;
            else
                x = location - dx;
        }
    }

    abstract void modifyMove(Wall wall);

    // Buff Method

    public void controlBuff() {
        for (int i = 0; i < buffs.size(); i++) {
            Buff buff = buffs.get(i);

            if (!buff.isStart()) {
                buff.startBuff();
                buff.setStart(true);
            }
            if (buff.isEnd()) {
                buff.endBuff();
                buffs.remove(i);
                i--;
            } else {
                buff.duringBuff();
            }

            buff.framePass();
        }
    }

    // Drawing Method

    public abstract void draw(Canvas canvas);
}
