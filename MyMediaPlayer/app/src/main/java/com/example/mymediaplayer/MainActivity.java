package com.example.mymediaplayer;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Button btnPlay,btnPause,btnStop;
    MediaPlayer mediaplayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnPlay=findViewById(R.id.btnPlay);
        btnPause=findViewById(R.id.btnPause);
        btnStop=findViewById(R.id.btnStop);

        mediaplayer=MediaPlayer.create(this,R.raw.song);

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaplayer.start();  //play song
            }
        });
        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaplayer.pause();
            }
        });
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaplayer.stop();
            }
        });


    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaplayer.release();
    }
}