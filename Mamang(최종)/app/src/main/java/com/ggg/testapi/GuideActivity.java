package com.ggg.testapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by seoheepark on 2017-02-04.
 */

public class GuideActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
    }
    public void onClick(View v){
        switch(v.getId()){
            case R.id.start_game:

                Intent intent = new Intent(GuideActivity.this, GameActivity.class);
                startActivity(intent);
                break;
        }
    }
}
