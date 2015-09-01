package com.medowhill.jaemin.runaway.object;

import android.content.Context;

import com.medowhill.jaemin.runaway.R;

import java.util.ArrayList;

/**
 * Created by Jaemin on 2015-09-01.
 */
public class Stage {

    public final ArrayList<Enemy> enemies;

    public final ArrayList<Wall> walls;

    private Player player;

    private float xStart, yStart, xFinish, yFinish;

    public Stage(Context context, int stage) {
        String data = context.getResources().getStringArray(R.array.stageInfo)[stage - 1];

        String[] datas = data.split("/");

        enemies = new ArrayList<>();
        walls = new ArrayList<>();

        for (int i = 0; i < datas.length; i++) {
            String data_ = datas[i];
            String[] datas_ = data_.split(",");
            switch (data_.charAt(0)) {
                case 'p':
                    float x = Float.parseFloat(datas_[1]);
                    float y = Float.parseFloat(datas_[2]);
                    float speed = Float.parseFloat(datas_[3]);
                    int size = Integer.parseInt(datas_[4]);
                    player = new Player(x, y, speed, size, context.getResources().getColor(R.color.player));
                    break;
                case 'w':
                    break;
                case 'e':
                    break;
                case 's':
                    xStart = Float.parseFloat(datas_[1]);
                    yStart = Float.parseFloat(datas_[2]);
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

    public float getxStart() {
        return xStart;
    }

    public float getyFinish() {
        return yFinish;
    }

    public float getyStart() {
        return yStart;
    }
}
