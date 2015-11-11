package com.medowhill.jaemin.runaway.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.medowhill.jaemin.runaway.R;
import com.medowhill.jaemin.runaway.ability.Ability;
import com.medowhill.jaemin.runaway.object.GameObject;
import com.medowhill.jaemin.runaway.view.StageSelectView;

/**
 * Copyright 2015. Hong Jaemin
 * All rights reserved.
 */

public class StageSelectActivity extends Activity {

    public static final int RESULT_FIN = 0, RESULT_NEXT = 1, RESULT_MAIN = 2;
    private static final int REQUEST_GAME_READY = 0;
    private final int WAIT = 250;

    StageSelectView stageSelectView;

    SharedPreferences sharedPreferences;

    Handler stageSelectHandler, resumeActivityHandler;

    int world;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stageselect);

        Intent intent = getIntent();
        world = intent.getIntExtra("world", 0);

        Ability.setContext(this);
        GameObject.setContext(this);

        stageSelectHandler = new StageSelectHandler(this);
        resumeActivityHandler = new ResumeActivityHandler(this);

        sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        int lastStage = sharedPreferences.getInt("stage" + world, 1);

        stageSelectView = (StageSelectView) findViewById(R.id.stageSelectView);
        stageSelectView.setWorld(world);
        stageSelectView.setStageSelectHandler(stageSelectHandler);
        stageSelectView.setLastStage(lastStage);
        stageSelectView.setInitialStage(lastStage);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_GAME_READY) {
                switch (data.getIntExtra("result", RESULT_FIN)) {
                    case RESULT_FIN:
                        resumeActivityHandler.sendEmptyMessageDelayed(0, WAIT);
                        break;
                    case RESULT_NEXT:
                        if (data.getBooleanExtra("next", false))
                            resumeActivityHandler.sendEmptyMessageDelayed(1, WAIT);
                        else
                            resumeActivityHandler.sendEmptyMessageDelayed(0, WAIT);
                        break;
                    case RESULT_MAIN:
                        Intent intent = new Intent();
                        intent.putExtra("result", WorldSelectActivity.RESULT_MAIN);
                        setResult(RESULT_OK, intent);
                        finish();
                        break;
                }
            }
        } else if (resultCode == RESULT_CANCELED) {
            stageSelectView.defaultScale(false);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        int stage = sharedPreferences.getInt("stage" + world, 1);
        stageSelectView.openNewStage(stage);
    }

    void startStage(int stage) {
        Intent intent = new Intent(getApplicationContext(), GameReadyActivity.class);
        intent.putExtra("stage", stage);
        intent.putExtra("world", world);
        startActivityForResult(intent, REQUEST_GAME_READY);
    }

    void resume(int i) {
        if (i == 0)
            stageSelectView.defaultScale(false);
        else
            stageSelectView.defaultScale(true);
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

class ResumeActivityHandler extends Handler {
    StageSelectActivity stageSelectActivity;

    public ResumeActivityHandler(StageSelectActivity stageSelectActivity) {
        this.stageSelectActivity = stageSelectActivity;
    }

    @Override
    public void handleMessage(Message msg) {
        stageSelectActivity.resume(msg.what);
    }
}