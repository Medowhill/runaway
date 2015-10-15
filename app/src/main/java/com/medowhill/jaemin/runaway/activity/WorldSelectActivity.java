package com.medowhill.jaemin.runaway.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.ViewGroup;

import com.medowhill.jaemin.runaway.R;
import com.medowhill.jaemin.runaway.view.WorldSelectView;

/**
 * Created by Jaemin on 2015-10-15.
 */
public class WorldSelectActivity extends Activity {

    final static int REQUEST_CODE = 0, RESULT_FIN = 0, RESULT_MAIN = 1;

    WorldSelectView worldSelectView;

    Handler worldHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worldselect);

        overridePendingTransition(R.anim.worldselect_activitystart, 0);

        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        worldSelectView = (WorldSelectView) findViewById(R.id.worldSelect_worldSelect);

        worldHandler = new WorldHandler(this);
        worldSelectView.setWorldHandler(worldHandler);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            int result = data.getIntExtra("result", RESULT_FIN);
            if (result == RESULT_MAIN)
                finish();
        }
    }

    @Override
    public void finish() {
        super.finish();

        overridePendingTransition(0, R.anim.worldselect_activityfinish);
    }

    void toStageSelect(int world) {
        Intent intent = new Intent(getApplicationContext(), StageSelectActivity.class);
        intent.putExtra("world", world);
        startActivityForResult(intent, REQUEST_CODE);
    }
}

class WorldHandler extends Handler {

    WorldSelectActivity worldSelectActivity;

    public WorldHandler(WorldSelectActivity worldSelectActivity) {
        this.worldSelectActivity = worldSelectActivity;
    }

    @Override
    public void handleMessage(Message msg) {
        worldSelectActivity.toStageSelect(msg.what);
    }
}