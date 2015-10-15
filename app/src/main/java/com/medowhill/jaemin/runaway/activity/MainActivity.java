package com.medowhill.jaemin.runaway.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.medowhill.jaemin.runaway.R;
import com.medowhill.jaemin.runaway.view.ToWorldSelectButton;

/**
 * Created by Jaemin on 2015-09-16.
 */
public class MainActivity extends Activity {

    FrameLayout frameLayout;
    ToWorldSelectButton toWorldSelectButton;
    ImageView imageView;
    ImageButton buttonUpgrade, buttonSetting, buttonCredit;

    Animation animationToWorldSelect, animationFromWorldSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        frameLayout = (FrameLayout) findViewById(R.id.main_frameLayout);
        toWorldSelectButton = (ToWorldSelectButton) findViewById(R.id.main_toWorldSelectButton);
        imageView = (ImageView) findViewById(R.id.main_imageView);
        buttonUpgrade = (ImageButton) findViewById(R.id.main_button_upgrade);
        buttonSetting = (ImageButton) findViewById(R.id.main_button_setting);
        buttonCredit = (ImageButton) findViewById(R.id.main_button_credit);

        animationToWorldSelect = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.main_toworldselect);
        animationFromWorldSelect = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.main_fromworldselect);
        animationToWorldSelect.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        animationFromWorldSelect.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                toWorldSelectButton.setVisibility(View.VISIBLE);
                imageView.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        toWorldSelectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.setVisibility(View.VISIBLE);
                toWorldSelectButton.setVisibility(View.INVISIBLE);
                frameLayout.startAnimation(animationToWorldSelect);

                Intent intent = new Intent(getApplicationContext(), WorldSelectActivity.class);
                startActivityForResult(intent, 0);
            }
        });

//        buttonUpgrade.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), .class);
//                startActivity(intent);
//            }
//        });

//        buttonSetting.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), .class);
//                startActivity(intent);
//            }
//        });

//        buttonCredit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), .class);
//                startActivity(intent);
//            }
//        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        frameLayout.startAnimation(animationFromWorldSelect);
    }
}
