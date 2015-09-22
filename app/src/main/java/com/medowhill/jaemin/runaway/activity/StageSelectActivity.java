package com.medowhill.jaemin.runaway.activity;

import android.app.Activity;
import android.os.Bundle;

import com.medowhill.jaemin.runaway.R;
import com.medowhill.jaemin.runaway.ability.Ability;
import com.medowhill.jaemin.runaway.object.GameObject;
import com.medowhill.jaemin.runaway.view.StageSelectView;

/**
 * Created by Jaemin on 2015-09-10.
 */
public class StageSelectActivity extends Activity {

    StageSelectView stageSelectView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stageselect);

        Ability.setContext(this);
        GameObject.setContext(this);

        stageSelectView = (StageSelectView) findViewById(R.id.stageSelectView);
    }
}
