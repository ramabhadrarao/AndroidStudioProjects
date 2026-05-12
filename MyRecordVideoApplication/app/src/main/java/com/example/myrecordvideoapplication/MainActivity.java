package com.example.myrecordvideoapplication;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Button btnRecord;
    VideoView videoView;
    static final int REQUEST_VIDEO=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnRecord=findViewById(R.id.btnRecord);
        videoView=findViewById(R.id.videoView);

        btnRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                startActivity(intent,REQUEST_VIDEO);
            }

            private void startActivity(Intent intent, int requestVideo) {
            }
        });

    }
}