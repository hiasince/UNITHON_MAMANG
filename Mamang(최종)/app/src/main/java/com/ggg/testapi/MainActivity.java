package com.ggg.testapi;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.ggg.testapi.utils.AudioWriterPCM;
import com.naver.speech.clientapi.SpeechRecognitionResult;

import java.lang.ref.WeakReference;
import java.util.List;

public class MainActivity extends Activity {

	private static final String TAG = MainActivity.class.getSimpleName();
	private static final String CLIENT_ID = "604WvRy9nWG6lynLFvaz";
    private static final String CLIENT_SECRET = "I2YCp2TMj2";

    private RecognitionHandler handler;
    private NaverRecognizer naverRecognizer;

    private AudioManager audioManager;

    private TextView txtResult;
    private Button btnStart,next;
    private SeekBar volumeControl;
    private String mResult;

    private AudioWriterPCM writer;

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
                // Extract obj property typed with String array.
                // The first element is recognition result for speech.
            	SpeechRecognitionResult speechRecognitionResult = (SpeechRecognitionResult) msg.obj;
            	List<String> results = speechRecognitionResult.getResults();
            	StringBuilder strBuf = new StringBuilder();
                /*
            	for(String result : results) {
            		strBuf.append(result);
            		strBuf.append("\n");
            	}*/
                mResult = results.get(0);//strBuf.toString();
                txtResult.setText(mResult);
                break;

            case R.id.recognitionError:
                if (writer != null) {
                    writer.close();
                }

                mResult = "Error code : " + msg.obj.toString();
                txtResult.setText(mResult);
                btnStart.setText(R.string.str_start);
                btnStart.setEnabled(true);
                break;

            case R.id.clientInactive:
                if (writer != null) {
                    writer.close();
                }
                btnStart.setText(R.string.str_start);
                btnStart.setEnabled(true);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtResult = (TextView) findViewById(R.id.txt_result);
        btnStart = (Button) findViewById(R.id.btn_start);
        next = (Button)findViewById(R.id.next);
        volumeControl = (SeekBar)findViewById(R.id.volumeControl);

        handler = new RecognitionHandler(this);
        naverRecognizer = new NaverRecognizer(this, handler, CLIENT_ID);

        btnStart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(!naverRecognizer.getSpeechRecognizer().isRunning()) {
                    // Start button is pushed when SpeechRecognizer's state is inactive.
                    // Run SpeechRecongizer by calling recognize().
                    mResult = "";
                    txtResult.setText("Connecting...");
                    btnStart.setText(R.string.str_stop);
                    naverRecognizer.recognize();
                } else {
                    Log.d(TAG, "stop and wait Final Result");
                    btnStart.setEnabled(false);

                    naverRecognizer.getSpeechRecognizer().stop();
                    txtResult.setText(mResult);
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                System.out.println("result "+ mResult);
                if(mResult.length()>0){
                    TellVoice tellVoice = new TellVoice(MainActivity.this, CLIENT_ID, CLIENT_SECRET, mResult + "  맞니?");
                    tellVoice.start();

                    //Toast.makeText(getApplicationContext(), "음성을 인식해 주세요",Toast.LENGTH_SHORT).show();
                }else {
                    TellVoice tellVoice = new TellVoice(MainActivity.this, CLIENT_ID, CLIENT_SECRET, "다시 말해줘");
                    tellVoice.start();
                }
            }
        });

        audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        int nMax = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int nCurrentVolumn = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        volumeControl.setMax(nMax);
        volumeControl.setProgress(nCurrentVolumn);
        volumeControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override public void onStopTrackingTouch(SeekBar seekBar) {

            }
            @Override public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
            }
        });



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
        btnStart.setText(R.string.str_start);
        btnStart.setEnabled(true);
    }

    @Override
    protected void onStop() {
    	super.onStop();
    	// NOTE : release() must be called on stop time.
    	naverRecognizer.getSpeechRecognizer().release();
    }

    // Declare handler for handling SpeechRecognizer thread's Messages.
    static class RecognitionHandler extends Handler {
        private final WeakReference<MainActivity> mActivity;

        RecognitionHandler(MainActivity activity) {
            mActivity = new WeakReference<MainActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            MainActivity activity = mActivity.get();
            if (activity != null) {
                activity.handleMessage(msg);
            }
        }
    }
}
