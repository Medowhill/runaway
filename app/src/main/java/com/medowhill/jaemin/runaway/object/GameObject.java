package com.medowhill.jaemin.runaway.object;

import android.content.Context;
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

    static Context context;

    // Size
    final float WIDTH, HEIGHT;

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
    GameObject(Stage stage, float width, float height, int color, float x, float y, float speed) {
        this.stage = stage;

        this.WIDTH = width;
        this.HEIGHT = height;

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

    // Getter

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public Stage getStage() {
        return stage;
    }

    public int getDirection() {
        return direction;
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

    // State Getter

    public boolean isAbilityUsable() {
        return abilityUsable;
    }

    public boolean isVisible() {
        return visible;
    }

    public boolean isMortal() {
        return mortal;
    }

    // Speed Getter

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

    // Setter

    void setSpeed(float speed) {
        this.speed = speed;
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
        float x1 = gameObject.x, y1 = gameObject.y, width1 = gameObject.WIDTH, height1 = gameObject.HEIGHT;
        return Math.abs(x1 - x) < (WIDTH + width1) / 2 && Math.abs(y1 - y) < (HEIGHT + height1) / 2;
    }

    // Moving Method

    public void move() {
        if (movable) {
            for (Wall wall : stage.walls) {
                if (willTouchAfterMove(wall)) {
                    modifyMove(wall);
                    return;
                }
            }
            x += getXSpeed();
            y += getYSpeed();
        }
    }

    boolean willTouchAfterMove(Wall wall) {
        float start = wall.START, end = wall.END, location = wall.LOCATION;

        switch (direction) {
            case Direction.DOWN:
            case Direction.UP:
                if (!wall.HORIZONTAL)
                    return false;
                if (start - WIDTH / 2 < x && x < end + WIDTH / 2) {
                    float y_ = y + getYSpeed();
                    return Math.min(y, y_) - HEIGHT / 2 < location && location < Math.max(y, y_) + HEIGHT / 2;
                } else
                    return false;

            case Direction.RIGHT:
            case Direction.LEFT:
                if (wall.HORIZONTAL)
                    return false;
                if (start - HEIGHT / 2 < y && y < end + HEIGHT / 2) {
                    float x_ = x + getXSpeed();
                    return Math.min(x, x_) - WIDTH / 2 < location && location < Math.max(x, x_) + WIDTH / 2;
                } else
                    return false;

            default:
                return false;
        }
    }

    abstract void modifyMove(Wall wall);

    // Drawing Method

    public abstract void draw(Canvas canvas);
}
