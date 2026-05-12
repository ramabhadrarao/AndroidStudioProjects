package com.example.myfirstbuttonclickviewmessageapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView messageView;
    Button myButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        messageView=findViewById(R.id.messageView);
        myButton=findViewById(R.id.myButton);

        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messageView.setText("Welcome to Swarnandhra");
            }
        });

    }
}