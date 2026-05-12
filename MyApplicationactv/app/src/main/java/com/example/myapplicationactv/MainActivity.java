package com.example.myapplicationactv;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    AutoCompleteTextView autoCity;
    String[] cities={
            "Vijayawada",
            "Vishakapattanam",
            "Rajuhmandry",
            "Tadepalligudem",
            "Narsapuram"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        autoCity=findViewById(R.id.autoCity);

        ArrayAdapter<String> adapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,cities);
        autoCity.setThreshold(1);
        autoCity.setAdapter(adapter);

    }
}