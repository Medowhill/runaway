package com.medowhill.jaemin.runaway.activity;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.TextView;

import com.medowhill.jaemin.runaway.R;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Jaemin on 2015-10-12.
 */
public class NewAbilityActivity extends Activity {

    final int[] abilityNameId = new int[]{0, R.string.abilityDashName, R.string.abilityTeleportationName, R.string.abilityHidingName, R.string.abilityProtectionName,
            R.string.abilityIllusionName, R.string.abilityShadowName, R.string.abilityShockWaveName, R.string.abilityDistortionFieldName};

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newability);

        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        textView = (TextView) findViewById(R.id.newAbility_textView_abilityName);

        int ability = getIntent().getIntExtra("ability", 1);

        byte[] abilityLevel;
        try {
            FileInputStream fileInputStream = openFileInput("abilityLevel");
            abilityLevel = new byte[fileInputStream.available()];
            fileInputStream.read(abilityLevel);
            fileInputStream.close();
        } catch (IOException e) {
            abilityLevel = new byte[10];
        }

        if (ability < 6)
            abilityLevel[ability] = 1;
        else if (ability == 6)
            abilityLevel[6] = abilityLevel[7] = 1;
        else if (ability == 7)
            abilityLevel[8] = abilityLevel[9] = 1;

        try {
            FileOutputStream fileOutputStream = openFileOutput("abilityLevel", MODE_PRIVATE);
            fileOutputStream.write(abilityLevel);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (IOException e1) {
        }

        textView.setText(getResources().getString(abilityNameId[ability]));
    }
}