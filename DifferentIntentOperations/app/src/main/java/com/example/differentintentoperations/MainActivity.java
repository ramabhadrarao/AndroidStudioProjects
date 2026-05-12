package com.example.differentintentoperations;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Button btnSMS,btnCall,btnEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        btnSMS=findViewById(R.id.btnSMS);
        btnCall=findViewById(R.id.btnCall);
        btnEmail=findViewById(R.id.btnEmail);

        btnSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("smsto:6301730454"));
                intent.putExtra("sms_body","Hai Students How are you");
                startActivity(intent);
            }
        });
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:6301730454"));
                startActivity(intent);
            }
        });

        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:bcahod@swarnandhra.ac.in"));
                intent.putExtra(Intent.EXTRA_SUBJECT,"I am testing email sent");
                intent.putExtra(Intent.EXTRA_TEXT,"Hai students i am testing this from android app to send a email");
                startActivity(intent);


            }
        });
    }
}