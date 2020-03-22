package com.example.uahclasswizard;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<UAHClass> uahClasses = new ArrayList<UAHClass>();

    private List<String> departments = new ArrayList<String>();
    ArrayAdapter<String> departmentAdapter;

    private List<String> buildings = new ArrayList<String>();
    ArrayAdapter<String> buildingAdapter;

    private List<String> instructors = new ArrayList<String>();
    ArrayAdapter<String> instructorAdapter;

    Spinner buildingSpinner;
    Button listByBuildingButton;

    Spinner roomBuildingSpinner;
    EditText roomNumberEdit;
    Button listSemesterByRoomButton;
    Button listByRoomButton;

    TextView searchOutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeUAHClasses();
        initializeUI();
    }

    private void initializeUAHClasses() {
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
                UAHClass uahClass = new UAHClass(line);

                uahClasses.add(uahClass);

                if(!departments.contains(uahClass.getDepartment())) {
                    departments.add(uahClass.getDepartment());
                }

                if(!buildings.contains(uahClass.getBuilding())) {
                    buildings.add(uahClass.getBuilding());
                }

                if(!instructors.contains(uahClass.getInstructor())) {
                    instructors.add(uahClass.getInstructor());
                }
            } else {
                break;
            }
        }
    }

    private void initializeUI() {
        buildingAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, buildings);
        buildingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        buildingSpinner = findViewById(R.id.buildingSpinner);
        buildingSpinner.setAdapter(buildingAdapter);

        listByBuildingButton = findViewById(R.id.listByBuildingButton);
        listByBuildingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String building = buildingSpinner.getSelectedItem().toString();
                String results = new String();

                for(UAHClass uahClass : uahClasses) {
                    if(uahClass.isBuilding(building)) {
                        results += "\n\n" + uahClass.toString();
                    }
                }

                searchOutput.setText(results);
            }
        });

        roomBuildingSpinner = findViewById(R.id.roomBuildingSpinner);
        roomBuildingSpinner.setAdapter(buildingAdapter);

        roomNumberEdit = findViewById(R.id.roomNumberEdit);

        listSemesterByRoomButton = findViewById(R.id.listSemesterByRoomButton);

        listByRoomButton = findViewById(R.id.listByRoomButton);
        listByRoomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String building = roomBuildingSpinner.getSelectedItem().toString();
                String results = new String();

                for(UAHClass uahClass : uahClasses) {
                    if(uahClass.isRoom(building,roomNumberEdit.getText().toString())) {
                        results += "\n\n" + uahClass.toString();
                    }
                }

                searchOutput.setText(results);
            }
        });

        searchOutput = findViewById(R.id.searchOutput);
    }
}
