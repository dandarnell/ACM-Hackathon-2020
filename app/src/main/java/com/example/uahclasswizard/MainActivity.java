package com.example.uahclasswizard;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView searchOutput;

    private List<UAHClass> uahClasses = new ArrayList<UAHClass>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchOutput = findViewById(R.id.searchOutput);

        InputStream inputStream = this.getResources().openRawResource(R.raw.uah_classes);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));


        String line = null;

        try {
            line = reader.readLine();
        } catch(IOException ioe) {

        }

        while(true) {
            try {
                line = reader.readLine();
            } catch(IOException ioe) {

            }

            if(line != null) {
                uahClasses.add(new UAHClass(line));
            } else {
                break;
            }
        }

        searchOutput.setText(uahClasses.get(uahClasses.size()-1).toString());
    }
}
