package com.example.intentdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button btnImplicit,btnExplicit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnExplicit=findViewById(R.id.btnExplicit);
        btnImplicit=findViewById(R.id.btnImplicit);
        //Explicit Intent
        btnExplicit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,MainActivity2.class);
                startActivity(intent);
            }
        });

        //Implicit Intent
        btnImplicit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://www.swarnandhra.ac.in"));
                startActivity(intent);
            }
        });
    }
}