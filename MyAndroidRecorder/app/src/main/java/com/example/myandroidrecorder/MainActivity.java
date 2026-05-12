package com.example.myandroidrecorder;

import android.Manifest;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Button btnStart,btnStop,btnPlay;
    MediaRecorder recorder;
    MediaPlayer player;
    String fileName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStart=findViewById(R.id.btnStart);
        btnStop=findViewById(R.id.btnStop);
        btnPlay=findViewById(R.id.btnPlay);
        fileName=getExternalCacheDir().getAbsolutePath()+"/audio.3gp";
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.RECORD_AUDIO,Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recorder=new MediaRecorder();
                recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                recorder.setOutputFile(fileName);

                try {

                    recorder.prepare();
                    recorder.start();

                } catch (Exception e) {
                    e.printStackTrace();
                }



            }
        });


        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(recorder !=null){
                    recorder.stop();
                    recorder.release();
                    recorder=null;
                }
            }
        });

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player=new MediaPlayer();
                try {
                    player.setDataSource(fileName);
                    player.prepare();
                    player.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }
}