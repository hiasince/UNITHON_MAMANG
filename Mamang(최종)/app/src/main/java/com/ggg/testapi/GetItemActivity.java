package com.ggg.testapi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ggg.testapi.utils.API;
import com.ggg.testapi.utils.SharedPreferenceUtil;

public class GetItemActivity extends AppCompatActivity {
    LinearLayout layout;
    ImageView back,circle;
    TextView fail, success;
    int what;
    int[] imgs = {R.drawable.fish_1, R.drawable.shark, R.drawable.shell, R.drawable.ship, R.drawable.jellyfish, R.drawable.octo };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_item);
        layout = (LinearLayout)findViewById(R.id.layout);
        back = (ImageView) findViewById(R.id.back);
        circle = (ImageView) findViewById(R.id.circle);
        fail = (TextView)findViewById(R.id.fail);
        success = (TextView)findViewById(R.id.success);
        //Glide.with(this).load(R.drawable.fail_background).into();
        int failCount = SharedPreferenceUtil.getStringArrayPref(getApplicationContext(),"winwin").size();

        fail.setText(String.valueOf(failCount));
        success.setText(String.valueOf(6-failCount));
        Glide.with(this).load(R.drawable.circle).into(circle);
        Glide.with(this).load(R.drawable.fail_background).into(back);

        for(int i = 0; i<failCount;i++) {
            View addCard = (View) View.inflate(getApplicationContext(), R.layout.card, null);

            ImageView itemImg = (ImageView) addCard.findViewById(R.id.fail_image);



            final TextView itemName = (TextView) addCard.findViewById(R.id.name);
            String[] korean = {"빨간 물고기","상어","조개", "배", "해파리","문어" };

            for(int j = 0;j<6;j++){
                if(itemName.equals(korean[j])){
                    what = imgs[j];
                    break;
                }
            }
            //itemImg.setImageResource(what);
            Glide.with(GetItemActivity.this).load(what).into(itemImg);

            itemName.setText(SharedPreferenceUtil.getStringArrayPref(getApplicationContext(),"winwin").get(i));
            Button listen = (Button) addCard.findViewById(R.id.sound_btn);
            listen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TellVoice tellVoice = new TellVoice(GetItemActivity.this, API.CLIENT_ID, API.CLIENT_SECRET, itemName.getText().toString());
                    tellVoice.start();
                }
            });
            layout.addView(addCard);
        }

    }


}
