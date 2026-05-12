package com.example.storereadfiledatainternalstoragelist;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
        Button btnSave,btnLoad;
        ListView listView;
        String fileName="data.txt";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnSave=findViewById(R.id.btnSave);
        btnLoad=findViewById(R.id.btnLoad);
        listView=findViewById(R.id.listView);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    FileOutputStream fos=openFileOutput(fileName,MODE_PRIVATE);
                    String data="BCA \n MCA \n B.Tech";
                    fos.write(data.getBytes());
                    fos.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        btnLoad.setOnClickListener(new View.OnClickListener() {
            ArrayList<String> list=new ArrayList<>();
            @Override
            public void onClick(View view) {
                try{
                    FileInputStream fis=openFileInput(fileName);
                    BufferedReader br=new BufferedReader(new InputStreamReader(fis));
                    String line;
                    while((line=br.readLine())!=null){
                       list.add(line);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                ArrayAdapter<String> adapter=new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1,list);
                listView.setAdapter(adapter);
            }


        });

    }
}