package com.ggg.testapi;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;

/**
 * Created by Administrator on 2017-02-04.
 */
public class TellVoice extends Thread {
    private String clientId, clientSecret, result;
    private Context context;

    public TellVoice(Context context, String clientId, String clientSecret, String result) {
        super();
        this.context = context;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.result = result;
    }

    @Override
    public void run() {
        //String clientId = "604WvRy9nWG6lynLFvaz";//애플리케이션 클라이언트 아이디값";
        //String clientSecret = "I2YCp2TMj2";//애플리케이션 클라이언트 시크릿값";
        try {
            String text = URLEncoder.encode(result, "UTF-8"); // 13자
            String apiURL = "https://openapi.naver.com/v1/voice/tts.bin";
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("X-Naver-Client-Id", clientId);
            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
            // post request
            String postParams = "speaker=mijin&speed=1&text=" + text;
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(postParams);
            wr.flush();
            wr.close();
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if (responseCode == 200) { // 정상 호출
                InputStream is = con.getInputStream();
                int read = 0;
                byte[] bytes = new byte[1024];
                // 랜덤한 이름으로 mp3 파일 생성
                String tempname = Long.valueOf(new Date().getTime()).toString();
                File f = new File(context.getFilesDir().getPath().toString() + "/temp.mp3");
                f.createNewFile();
                Log.d("hbi", "100");
                OutputStream outputStream = new FileOutputStream(f);
                while ((read = is.read(bytes)) != -1) {
                    Log.d("hbi", "200");
                    outputStream.write(bytes, 0, read);
                }
                is.close();
                Log.d("hbi", "300");
                MediaPlayer m = new MediaPlayer();
                m.setDataSource(context.getFilesDir().getPath().toString() + "/temp.mp3");
                m.prepare();
                m.start();

                f.delete();

            } else {  // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = br.readLine()) != null) {
                    response.append(inputLine);
                }
                br.close();
                System.out.println(response.toString());
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    };
}


