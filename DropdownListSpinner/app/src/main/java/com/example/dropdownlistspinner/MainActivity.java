package com.example.dropdownlistspinner;

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
    TextView viewSelected;

    String[] courses={
      "BCA",
      "MCA",
      "BBA",
      "MBA",
      "B.Tech"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spinnerCourse=findViewById(R.id.spinnerCourse);
        viewSelected=findViewById(R.id.viewSeleted);

        ArrayAdapter<String> adapter=new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,courses);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCourse.setAdapter(adapter);

        spinnerCourse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                String selected=parent.getItemAtPosition(position).toString();

                Toast.makeText(MainActivity.this,"Selecetd:"+selected,Toast.LENGTH_LONG).show();
                viewSelected.setText(selected);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }
}