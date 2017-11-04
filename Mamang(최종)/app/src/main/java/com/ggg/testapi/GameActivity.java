package com.ggg.testapi;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.ggg.testapi.utils.API;
import com.ggg.testapi.utils.AudioWriterPCM;
import com.ggg.testapi.utils.SharedPreferenceUtil;
import com.naver.speech.clientapi.SpeechRecognitionResult;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by seoheepark on 2017-02-04.
 */

public class GameActivity extends Activity {
    Dialog dlg;
    int mBackValue = 4;
    TextView mBackText;
    private RecognitionHandler handler;
    private NaverRecognizer naverRecognizer;
    private TextView txtResult;
    private String mResult;
    int i;
    ImageView card;
    int[] imgs = {R.drawable.fish_1, R.drawable.shark, R.drawable.shell, R.drawable.ship, R.drawable.jellyfish, R.drawable.octo };//카드 그림
    int[] wins = {R.drawable.fish_win, R.drawable.shark_win, R.drawable.shell_win, R.drawable.ship_win,R.drawable.jellyfish_win, R.drawable.octo_win};

    String[] korean = {"빨간 물고기","상어","조개", "배", "해파리","문어" };
    private AudioWriterPCM writer;
    ImageButton re;
    ImageButton pass;

    ArrayList<String> winList = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        re = (ImageButton)findViewById(R.id.bt_re);
        pass = (ImageButton)findViewById(R.id.bt_pass);
        re.setVisibility(View.INVISIBLE);
        pass.setVisibility(View.INVISIBLE);
        i=0;
        mBackText = (TextView)findViewById(R.id.backvalue);
        txtResult = (TextView)findViewById(R.id.mResult);
        card = (ImageView)findViewById(R.id.card);

        card.setImageResource(imgs[i]);
        //그림에 맞는 글자
        // korean = ;

        handler = new RecognitionHandler(GameActivity.this);
        naverRecognizer = new NaverRecognizer(GameActivity.this, handler, API.CLIENT_ID);
        BackThread thread = new BackThread();
        thread.setDaemon(true);
        thread.start();
        Typeface typeface = Typeface.createFromAsset( getAssets(), "NanumSquareB.otf" );
        ViewGroup root = (ViewGroup) findViewById(android.R.id.content);
        SetViewGroupFont(root, typeface);

        re.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                re.setVisibility(View.INVISIBLE);
                pass.setVisibility(View.INVISIBLE);
                BackThread thread = new BackThread();
                thread.setDaemon(true);
                thread.start();
            }
        });
        pass.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                re.setVisibility(View.INVISIBLE);
                pass.setVisibility(View.INVISIBLE);
                i++;

                if(i<6) card.setImageResource(imgs[i]);
                else {
                    SharedPreferenceUtil.setStringArrayPref(getApplicationContext(),"winwin",winList);
                    Intent intent = new Intent(GameActivity.this, GetItemActivity.class);
                    startActivity(intent);
                }

                BackThread thread = new BackThread();
                thread.setDaemon(true);
                thread.start();
            }
        });

    }

    class BackThread extends Thread{// 3, 2, 1 하고 음성인식 기능 시작
        public void run(){
            while(mBackValue !=0){
                mBackValue--;

                mHandler.sendEmptyMessage(0);
                try{
                    Thread.sleep(1000);
                }
                catch (InterruptedException e){

                }

            }

            mBackValue = 4;//5초
        }
    }


    final Handler mHandler = new Handler(Looper.getMainLooper()){
        public void handleMessage (Message msg){
            if(msg.what == 0){
                mBackText.setText(""+ mBackValue);
                //여기서 음성 인식 시작되어야함
                //if(!naverRecognizer.getSpeechRecognizer().isRunning()) {

                mResult = "";
                txtResult.setText("Connecting...");
                //btnStart.setText(R.string.str_stop);
                naverRecognizer.recognize();
            } else {
                Log.d("stop", "stop and wait Final Result");
                //btnStart.setEnabled(false);

                naverRecognizer.getSpeechRecognizer().stop();
                txtResult.setText(mResult);
                Log.i("result ", mResult);

            }
        }

    };




    // Handle speech recognition Messages.
    private void handleMessage(Message msg) {
        switch (msg.what) {
            case R.id.clientReady:
                // Now an user can speak.
                txtResult.setText("Connected");
                writer = new AudioWriterPCM(
                        Environment.getExternalStorageDirectory().getAbsolutePath() + "/NaverSpeechTest");
                writer.open("Test");
                break;

            case R.id.audioRecording:
                writer.write((short[]) msg.obj);
                break;

            case R.id.partialResult:
                // Extract obj property typed with String.
                mResult = (String) (msg.obj);
                txtResult.setText(mResult);
                break;

            case R.id.finalResult:
                SpeechRecognitionResult speechRecognitionResult = (SpeechRecognitionResult) msg.obj;
                List<String> results = speechRecognitionResult.getResults();
                StringBuilder strBuf = new StringBuilder();
                //for(int i=0;i<5;i++){
                //if(results.get(i).compareTo(korean[i])==0){
                mResult = results.get(0);
                //break;//바나나
                //}

                //}
                mResult = results.get(0);//strBuf.toString();
                txtResult.setText(mResult);
                //if(i<6){
                    if(mResult.compareTo(korean[i])==0) {
                     mBackText.setText("0");
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                if(i<6) card.setImageResource(imgs[i]);
                        }
                    }, 2000);

                //}


                    //Toast.makeText(GameActivity.this, "서희"+ korean[i] , Toast.LENGTH_LONG).show();


                    for(int i = 0 ; i<winList.size();i++){
                        System.out.println("dd:  "+ winList.get(i));
                    }
                    card.setImageResource(wins[i]);

                    i++;
                    if(i==6){
                        SharedPreferenceUtil.setStringArrayPref(getApplicationContext(),"winwin",winList);
                        Intent intent = new Intent(GameActivity.this, GetItemActivity.class);
                        startActivity(intent);
                    }
                    BackThread thread = new BackThread();
                    thread.setDaemon(true);
                    thread.start();
                    //3초후 다시 바뀐걸 321다시
                }
                else{//일치하지않으면,
                    mBackText.setText("0");
                    winList.add(korean[i]);
                    re.setVisibility(View.VISIBLE);
                    pass.setVisibility(View.VISIBLE);
                }
                break;

            case R.id.recognitionError:
                if (writer != null) {
                    writer.close();
                }

                mResult = "Error code : " + msg.obj.toString();
                txtResult.setText("다시 시도해 주세요");

                break;

            case R.id.clientInactive:
                if (writer != null) {
                    writer.close();
                }
                break;
        }
    }

    public static void SetViewGroupFont( ViewGroup root, Typeface mTypeface )
    {
        for( int i = 0; i < root.getChildCount(); i++ )
        {
            View child = root.getChildAt( i );
            if( child instanceof TextView ) ( (TextView) child ).setTypeface( mTypeface );
            else if( child instanceof ViewGroup ) SetViewGroupFont( (ViewGroup) child, mTypeface );
        }
    }

    static class RecognitionHandler extends Handler {
        private final WeakReference<GameActivity> mActivity;

        RecognitionHandler(GameActivity activity) {
            mActivity = new WeakReference<GameActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            GameActivity activity = mActivity.get();
            if (activity != null) {
                activity.handleMessage(msg);
            }
        }
    }







    @Override
    protected void onStart() {
        super.onStart();
        // NOTE : initialize() must be called on start time.
        naverRecognizer.getSpeechRecognizer().initialize();
    }

    @Override
    protected void onResume() {
        super.onResume();

        mResult = "";
        txtResult.setText("");

    }

    @Override
    protected void onStop() {
        super.onStop();
        // NOTE : release() must be called on stop time.
        naverRecognizer.getSpeechRecognizer().release();
    }
}
