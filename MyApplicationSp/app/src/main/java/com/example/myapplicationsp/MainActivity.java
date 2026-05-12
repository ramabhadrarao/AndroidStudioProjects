package com.example.myapplicationsp;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Spinner spinnerCourse;
    TextView textView;
    String [] course={
      "BCA",
      "BBA",
      "MCA",
      "MBA"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinnerCourse=findViewById(R.id.spinnerCourse);
        textView=findViewById(R.id.textView);
        ArrayAdapter<String> adapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,course);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCourse.setAdapter(adapter);

        spinnerCourse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selected=adapterView.getItemAtPosition(i).toString();
                Toast.makeText(MainActivity.this,"Selected:"+selected,Toast.LENGTH_SHORT).show();
                textView.setText(selected);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}