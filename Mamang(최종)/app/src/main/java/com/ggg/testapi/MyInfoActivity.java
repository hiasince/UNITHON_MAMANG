package com.ggg.testapi;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.ggg.testapi.data.ItemData;
import com.ggg.testapi.utils.DBManager;
import com.ggg.testapi.utils.SharedPreferenceUtil;

import java.io.File;
import java.util.ArrayList;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by seoheepark on 2017-02-04.
 */

public class MyInfoActivity extends Activity {
    DBManager dbManager;
    int stage;

    ArrayList<ItemData> itemArrayList = new ArrayList<>();
    ImageView face;
    ProgressBar progressBar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        progressBar = (ProgressBar)findViewById(R.id.progressbar);
        dbManager = new DBManager(getApplicationContext(), "momo.db", null, 1);
        //Intent intent = new Intent(this.getIntent());
        SharedPreferenceUtil.putSharedPreference(getApplicationContext(), "stage", "1");


        File imgFile = new File("storage/sdcard0/camtest/1486234855364.jpg");//SharedPreferenceUtil.getSharedPreference(this,"mPhoto"));

        if(imgFile.exists()){

            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

            Glide.with(this).load(myBitmap)
                    .bitmapTransform(new CropCircleTransformation(this))
                    .into(face);


        }



        //dbManager.insert("item1",1,1);
        //dbManager.insert("item2",1,1);
        //dbManager.insert("item4",1,1);
        stage = Integer.parseInt(SharedPreferenceUtil.getSharedPreference(getApplicationContext(), "stage"));
        System.out.println("stage: " + stage);
        itemArrayList = dbManager.getResult(stage);
        //LinearLayout.LayoutParams layoutParams;
       // layoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);


        ImageView[] itemImage = new ImageView[itemArrayList.size()];
//        for (int i = 0; i < itemArrayList.size(); i++) {
//            final int index = i;
//
//            itemImage[i].setLayoutParams(new LinearLayout.LayoutParams(50, 50));
//            //itemImage[i].setLayoutParams(params);
//            itemImage[i] = new ImageView(getApplicationContext());
//            itemImage[i].setScaleType(ImageView.ScaleType.CENTER_INSIDE);
//            System.out.println("jjj: " + itemArrayList.get(i).getItem());
//            itemImage[i].setImageResource(matchItem(itemArrayList.get(i).getItem()));
//            if (itemArrayList.get(i).getSelected() == 0) {
//                itemImage[i].setColorFilter(Color.parseColor("#777777"));
//            }
//
//            itemLinear.addView(itemImage[i]);
//            itemImage[index].setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                    if(itemArrayList.get(index).getSelected()==1){
//                        view.setBackgroundColor(Color.parseColor("#777777"));
//                        dbManager.update(itemArrayList.get(index).getItem(),1);
//                    }else{
//                        view.setBackgroundColor(Color.TRANSPARENT);
//                        dbManager.update(itemArrayList.get(index).getItem(),0);
//                    }
//                }
//            });
//
//
//        }


    }


    private int matchItem(String itemName) {
        if (itemName.equals("item1")) return R.drawable.icon_shell;
        //else if (itemName.equals("item2")) return R.drawable.icon_octopus;
        //else if (itemName.equals("item3")) return R.drawable.icon_octopus;
        //else if (itemName.equals("item4")) return R.drawable.icon_octopus;

        else return R.drawable.icon_shell;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
