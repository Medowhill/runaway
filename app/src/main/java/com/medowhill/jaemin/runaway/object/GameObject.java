package com.medowhill.jaemin.runaway.object;

import com.medowhill.jaemin.runaway.ability.Ability;
import com.medowhill.jaemin.runaway.buff.Buff;

import java.util.ArrayList;

/**
 * Created by Jaemin on 2015-09-01.
 */
public abstract class GameObject {

    private float x, y;

    private int direction;

    private float speed;

    private ArrayList<Ability> abilities;

    private ArrayList<Buff> buffs;

}
