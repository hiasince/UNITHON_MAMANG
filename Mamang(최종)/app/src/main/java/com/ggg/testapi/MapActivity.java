package com.ggg.testapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

/**
 * Created by seoheepark on 2017-02-04.
 */

public class MapActivity extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        final Animation anim = AnimationUtils.loadAnimation(this, R.anim.anim_size);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.stage:
                Intent intent = new Intent(MapActivity.this, PrepareActivity.class);
                startActivity(intent);
                break;
            case R.id.myinfo:
                intent = new Intent(MapActivity.this, MyInfoActivity.class);
                startActivity(intent);
                break;
        }
    }
}
