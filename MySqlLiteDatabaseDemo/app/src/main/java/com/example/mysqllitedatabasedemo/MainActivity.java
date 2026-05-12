package com.example.mysqllitedatabasedemo;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    EditText editName;
    Button btnInsert,btnView;
    TextView textView;

    DBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editName=findViewById(R.id.editName);
        btnInsert=findViewById(R.id.btnInsert);
        btnView=findViewById(R.id.btnView);
        textView=findViewById(R.id.textView);
        db=new DBHelper(this);
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.insertData(editName.getText().toString());
                Toast.makeText(MainActivity.this,"Data Inserted",Toast.LENGTH_SHORT).show();
            }
        });
        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor c= db.getData();
                StringBuilder sb=new StringBuilder();
                while (c.moveToNext()){
                    sb.append("ID:").append(c.getInt(0)).append("Name:").append(c.getString(1)).append("\n");
                }
                textView.setText(sb.toString());
            }
        });
    }
}