package com.medowhill.jaemin.runaway.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.medowhill.jaemin.runaway.R;
import com.medowhill.jaemin.runaway.ability.Ability;
import com.medowhill.jaemin.runaway.object.GameObject;
import com.medowhill.jaemin.runaway.view.StageSelectView;

/**
 * Created by Jaemin on 2015-09-10.
 */
public class StageSelectActivity extends Activity {

    StageSelectView stageSelectView;

    Handler stageSelectHandler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stageselect);

        Ability.setContext(this);
        GameObject.setContext(this);

        stageSelectHandler = new StageSelectHandler(this);

        stageSelectView = (StageSelectView) findViewById(R.id.stageSelectView);
        stageSelectView.setStageSelectHandler(stageSelectHandler);
        stageSelectView.setLastStage(3);
    }

    void startStage(int stage) {
        Intent intent = new Intent(getApplicationContext(), GameReadyActivity.class);
        intent.putExtra("stage", stage);
        startActivity(intent);
    }
}

class StageSelectHandler extends Handler {

    StageSelectActivity stageSelectActivity;

    public StageSelectHandler(StageSelectActivity stageSelectActivity) {
        this.stageSelectActivity = stageSelectActivity;
    }

    @Override
    public void handleMessage(Message msg) {
        stageSelectActivity.startStage(msg.what);
    }
}
