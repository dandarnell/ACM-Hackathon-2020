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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<UAHClass> uahClasses = new ArrayList<UAHClass>();

    private List<String> buildings = new ArrayList<String>();
    ArrayAdapter<String> buildingAdapter;

    private List<String> instructors = new ArrayList<String>();
    ArrayAdapter<String> instructorAdapter;

    private List<String> departments = new ArrayList<String>();
    ArrayAdapter<String> departmentAdapter;

    Spinner buildingSpinner;
    Button listByBuildingButton;

    Spinner roomBuildingSpinner;
    EditText roomNumberEdit;
    Button listSemesterByRoomButton;
    Button listByRoomButton;

    Spinner instructorSpinner;
    Button listSemesterByInstructorButton;
    Button listByInstructorButton;

    Spinner departmentSpinner;
    EditText courseNumberEdit;
    Button listSemesterByCourseButton;
    Button listByCourseButton;

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

                if(!buildings.contains(uahClass.getBuilding())) {
                    buildings.add(uahClass.getBuilding());
                }

                if(!instructors.contains(uahClass.getInstructor())) {
                    instructors.add(uahClass.getInstructor());
                }

                if(!departments.contains(uahClass.getDepartment())) {
                    departments.add(uahClass.getDepartment());
                }
            } else {
                break;
            }
        }

        Collections.sort(buildings);
        Collections.sort(instructors);
        Collections.sort(departments);

        buildings.add(0,"Select a building");
        instructors.add(0,"Select an instructor");
        departments.add(0,"Select a department");
    }

    private void initializeUI() {
        buildingAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, buildings);
        buildingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        instructorAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, instructors);
        instructorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        departmentAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, departments);
        departmentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        buildingSpinner = findViewById(R.id.buildingSpinner);
        buildingSpinner.setAdapter(buildingAdapter);

        listByBuildingButton = findViewById(R.id.listByBuildingButton);
        listByBuildingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String building = buildingSpinner.getSelectedItem().toString();
                StringBuilder results = new StringBuilder();

                for(UAHClass uahClass : uahClasses) {
                    if(uahClass.isBuilding(building)) {
                        results.append("\n\n");
                        results.append(uahClass.toString(true,true,true));
                    }
                }

                searchOutput.setText(results);
            }
        });

        roomBuildingSpinner = findViewById(R.id.roomBuildingSpinner);
        roomBuildingSpinner.setAdapter(buildingAdapter);

        roomNumberEdit = findViewById(R.id.roomNumberEdit);

        listSemesterByRoomButton = findViewById(R.id.listSemesterByRoomButton);
        listSemesterByRoomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String building = roomBuildingSpinner.getSelectedItem().toString();
                StringBuilder results = new StringBuilder();

                for(UAHClass uahClass : uahClasses) {
                    if(uahClass.isRoom(building,roomNumberEdit.getText().toString()) &&
                        uahClass.isCurrentSemester()) {

                        results.append("\n\n");
                        results.append(uahClass.toString(false,true,false));
                    }
                }

                searchOutput.setText(results);
            }
        });

        listByRoomButton = findViewById(R.id.listByRoomButton);
        listByRoomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String building = roomBuildingSpinner.getSelectedItem().toString();
                StringBuilder results = new StringBuilder();

                for(UAHClass uahClass : uahClasses) {
                    if(uahClass.isRoom(building,roomNumberEdit.getText().toString())) {
                        results.append("\n\n");
                        results.append(uahClass.toString(false,true,true));
                    }
                }

                searchOutput.setText(results);
            }
        });

        instructorSpinner = findViewById(R.id.instructorSpinner);
        instructorSpinner.setAdapter(instructorAdapter);

        listSemesterByInstructorButton = findViewById(R.id.listSemesterByInstructorButton);
        listSemesterByInstructorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String instructor = instructorSpinner.getSelectedItem().toString();
                StringBuilder results = new StringBuilder();

                for(UAHClass uahClass : uahClasses) {
                    if(uahClass.isInstructor(instructor) && uahClass.isCurrentSemester()) {
                        results.append("\n\n");
                        results.append(uahClass.toString(true,false,false));
                    }
                }

                searchOutput.setText(results);
            }
        });

        listByInstructorButton = findViewById(R.id.listByInstructorButton);
        listByInstructorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String instructor = instructorSpinner.getSelectedItem().toString();
                StringBuilder results = new StringBuilder();

                for(UAHClass uahClass : uahClasses) {
                    if(uahClass.isInstructor(instructor)) {
                        results.append("\n\n");
                        results.append(uahClass.toString(true,false,true));
                    }
                }

                searchOutput.setText(results);
            }
        });

        departmentSpinner = findViewById(R.id.departmentSpinner);
        departmentSpinner.setAdapter(departmentAdapter);

        courseNumberEdit = findViewById(R.id.courseNumberEdit);

        listSemesterByCourseButton = findViewById(R.id.listSemesterByCourseButton);
        listSemesterByCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String department = departmentSpinner.getSelectedItem().toString();
                StringBuilder results = new StringBuilder();

                for(UAHClass uahClass : uahClasses) {
                    if(uahClass.isCourse(department, courseNumberEdit.getText().toString()) &&
                            uahClass.isCurrentSemester()) {

                        results.append("\n\n");
                        results.append(uahClass.toString(true,true,false));
                    }
                }

                searchOutput.setText(results);
            }
        });

        listByCourseButton = findViewById(R.id.listByCourseButton);
        listByCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String department = departmentSpinner.getSelectedItem().toString();
                StringBuilder results = new StringBuilder();

                for(UAHClass uahClass : uahClasses) {
                    if(uahClass.isCourse(department, courseNumberEdit.getText().toString())) {
                        results.append("\n\n");
                        results.append(uahClass.toString(true,true,true));
                    }
                }

                searchOutput.setText(results);
            }
        });

        searchOutput = findViewById(R.id.searchOutput);
    }
}
