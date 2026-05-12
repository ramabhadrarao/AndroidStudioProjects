package com.example.sharedpreferenceapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    EditText editText;
    Button btnSave,btnLoad;
    TextView textView;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText=findViewById(R.id.editText);
        btnSave=findViewById(R.id.btnSave);
        btnLoad=findViewById(R.id.btnLoad);
        textView=findViewById(R.id.textView);

        sharedPreferences=getSharedPreferences("mydata",MODE_PRIVATE);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString("name",editText.getText().toString());
                editor.apply();
            }
        });
        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String myname=sharedPreferences.getString("name","No Data");
                textView.setText(myname);
            }
        });
    }
}