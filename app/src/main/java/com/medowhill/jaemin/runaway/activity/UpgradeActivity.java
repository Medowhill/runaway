package com.medowhill.jaemin.runaway.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TextView;

import com.medowhill.jaemin.runaway.R;
import com.medowhill.jaemin.runaway.view.UpgradeView;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by Jaemin on 2015-10-16.
 */
public class UpgradeActivity extends Activity {

    final int[] ID = new int[]{R.id.upgrade_upgrade0, R.id.upgrade_upgrade1, R.id.upgrade_upgrade2,
            R.id.upgrade_upgrade3, R.id.upgrade_upgrade4, R.id.upgrade_upgrade5, R.id.upgrade_upgrade6,
            R.id.upgrade_upgrade7, R.id.upgrade_upgrade8};

    UpgradeView[] upgradeViews;
    TextView textViewStar;

    SharedPreferences sharedPreferences;

    private int star;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upgrade);

        overridePendingTransition(R.anim.upgrade_activitystart, 0);

        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        byte[] abilityLevel;
        try {
            FileInputStream fileInputStream = openFileInput("abilityLevel");
            abilityLevel = new byte[fileInputStream.available()];
            fileInputStream.read(abilityLevel);
            fileInputStream.close();
        } catch (IOException e) {
            abilityLevel = new byte[9];
            abilityLevel[0] = 1;
        }

        upgradeViews = new UpgradeView[ID.length];
        for (int i = 0; i < ID.length; i++) {
            upgradeViews[i] = (UpgradeView) findViewById(ID[i]);
            upgradeViews[i].setUpgrade(i, abilityLevel[i]);
        }
        textViewStar = (TextView) findViewById(R.id.upgrade_textView_Star);

        sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
    }

    @Override
    public void finish() {
        super.finish();

        overridePendingTransition(0, R.anim.upgrade_activityfinish);
    }

    @Override
    protected void onResume() {
        super.onResume();

        star = sharedPreferences.getInt("star", 0);
        textViewStar.setText(star + "");
    }
}
