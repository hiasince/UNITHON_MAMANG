package com.ggg.testapi;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by seoheepark on 2017-02-04.
 */

public class PrepareActivity extends Activity {
    private static final String CLIENT_ID = "604WvRy9nWG6lynLFvaz";
    private static final String CLIENT_SECRET = "I2YCp2TMj2";
    private ImageButton ship;
    private ImageButton fish_blue;
    private ImageButton fish_orange;
    private ImageButton shark;
    private ImageButton octopus;
    private ImageButton jellyfish;
    private ImageButton shell;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prepare);

        ship = (ImageButton) findViewById(R.id.ship);
        fish_blue = (ImageButton) findViewById(R.id.fish_blue);
        fish_orange = (ImageButton) findViewById(R.id.fish_orange);
        shark = (ImageButton) findViewById(R.id.shark);
        octopus = (ImageButton) findViewById(R.id.octopus);
        jellyfish = (ImageButton) findViewById(R.id.jellyfish);
        shell = (ImageButton) findViewById(R.id.shell);
        final Animation anim = AnimationUtils.loadAnimation(this, R.anim.anim_size);

        ship.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ship.startAnimation(anim);
                TellVoice tellVoice = new TellVoice(PrepareActivity.this, CLIENT_ID, CLIENT_SECRET, "배");
                tellVoice.start();
            }
        });
        fish_blue.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                fish_blue.startAnimation(anim);
                TellVoice tellVoice = new TellVoice(PrepareActivity.this, CLIENT_ID, CLIENT_SECRET, "파란 물고기");
                tellVoice.start();
            }
        });
        fish_orange.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                fish_orange.startAnimation(anim);
                TellVoice tellVoice = new TellVoice(PrepareActivity.this, CLIENT_ID, CLIENT_SECRET, "빨간 물고기");
                tellVoice.start();
            }
        });
        shark.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                shark.startAnimation(anim);
                TellVoice tellVoice = new TellVoice(PrepareActivity.this, CLIENT_ID, CLIENT_SECRET, "상 어");
                tellVoice.start();
            }
        });
        octopus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                octopus.startAnimation(anim);
                TellVoice tellVoice = new TellVoice(PrepareActivity.this, CLIENT_ID, CLIENT_SECRET, "문어");
                tellVoice.start();
            }
        });
        jellyfish.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                jellyfish.startAnimation(anim);
                TellVoice tellVoice = new TellVoice(PrepareActivity.this, CLIENT_ID, CLIENT_SECRET, "해 파리");
                tellVoice.start();
            }
        });
        shell.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                shell.startAnimation(anim);
                TellVoice tellVoice = new TellVoice(PrepareActivity.this, CLIENT_ID, CLIENT_SECRET, "조개");
                tellVoice.start();
            }
        });
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.study_start:
                Intent intent = new Intent(PrepareActivity.this,  GameActivity.class);
                startActivity(intent);
                break;
            case R.id.exit:
                finish();
                break;
        }
    }

    public static void SetViewGroupFont(ViewGroup root, Typeface mTypeface )
    {
        for( int i = 0; i < root.getChildCount(); i++ )
        {
            View child = root.getChildAt( i );
            if( child instanceof TextView) ( (TextView) child ).setTypeface( mTypeface );
            else if( child instanceof ViewGroup ) SetViewGroupFont( (ViewGroup) child, mTypeface );
        }
    }
}
