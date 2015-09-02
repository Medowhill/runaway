package com.medowhill.jaemin.runaway.object;

import android.content.Context;
import android.graphics.Path;

import com.medowhill.jaemin.runaway.R;

import java.util.ArrayList;

/**
 * Created by Jaemin on 2015-09-01.
 */
public class Stage {

    public final ArrayList<Enemy> enemies;

    public final ArrayList<Wall> walls;

    public final Path area;

    private float xMax, yMax;

    private Player player;

    private float xFinish, yFinish;

    public Stage(Context context, int stage) {
        String data = context.getResources().getStringArray(R.array.stageInfo)[stage - 1];

        String[] datas = data.split("/");

        enemies = new ArrayList<>();
        walls = new ArrayList<>();
        area = new Path();
        area.moveTo(0, 0);

        int baseSize = context.getResources().getInteger(R.integer.baseSize);

        for (int i = 0; i < datas.length; i++) {
            String data_ = datas[i];
            String[] datas_ = data_.split(",");
            float x, y;
            switch (data_.charAt(0)) {
                case 'p':
                    player = new Player();
                    player.setX(Float.parseFloat(datas_[1]));
                    player.setY(Float.parseFloat(datas_[2]));
                    player.setSpeed(Float.parseFloat(datas_[3]));
                    player.setSize(context.getResources().getInteger(R.integer.playerSize) * baseSize);
                    player.setColor(context.getResources().getColor(R.color.player));
                    break;
                case 'w':
                    x = 0;
                    y = 0;
                    for (int j = 1; j < datas_.length; j++) {
                        if (j % 2 == 1) {
                            float x_ = Float.parseFloat(datas_[j]);
                            walls.add(new Wall(true, x, x_, y));
                            x = x_;
                            if (x > xMax)
                                xMax = x;
                        } else {
                            float y_ = Float.parseFloat(datas_[j]);
                            walls.add(new Wall(false, y, y_, x));
                            y = y_;
                            if (y > yMax)
                                yMax = y;
                        }
                        area.lineTo(x, y);
                    }
                    break;
                case 'e':
                    break;
                case 'f':
                    xFinish = Float.parseFloat(datas_[1]);
                    yFinish = Float.parseFloat(datas_[2]);
                    break;
            }
        }
    }

    public Player getPlayer() {
        return player;
    }

    public float getxFinish() {
        return xFinish;
    }

    public float getyFinish() {
        return yFinish;
    }

    public float getxMax() {
        return xMax;
    }

    public float getyMax() {
        return yMax;
    }
}
