package com.example.uahclasswizard;

import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;


public class UAHClass {

    private String name;
    private String crn;
    private String department;
    private String number;
    private String section;
    private String term;
    private String credits;
    private String startTime;
    private String endTime;
    private String days;
    private String startDate;
    private String endDate;
    private String building;
    private String roomNumber;
    private String instructor;

    private Date startDateObj;
    private Date endDateObj;

    // name,crn,department,number,section,term,credits,start time,end time,days,start date,end date,building,room number,instructor
    UAHClass(String csvLine) {
        String[] things = csvLine.split(",");
        this.name = things[0];
        this.crn = things[1];
        this.department = things[2];
        this.number = things[3];
        this.section = things[4];
        this.term = things[5];
        this.credits = things[6];
        this.startTime = things[7];
        this.endTime = things[8];
        this.days = things[9];
        this.startDate = things[10];
        this.endDate = things[11];
        this.building = things[12];
        this.roomNumber = things[13];
        this.instructor = things[14];

        try {
            this.startDateObj = new SimpleDateFormat("MMM dd yyyy").parse(this.startDate);
            this.endDateObj = new SimpleDateFormat("MMM dd yyyy").parse(this.endDate);
        } catch(ParseException e) {
            this.startDateObj = new Date(0);
            this.endDateObj = new Date(0);
        }
    }

    public String toString() {
        return this.name + " - " + this.department + " " + this.number + " - " + this.section
                + "\n  " + this.days + " " + this.startTime + " - " + this.endTime
                + "\n  " + this.building + " " + this.roomNumber
                + "\n  " + this.instructor + "\n";
    }

    public Boolean isAt(Date dateTime) {
        if (dateTime.before(this.startDateObj))
            return false;
        if (dateTime.after(this.endDateObj))
            return false;
        // check day of week TODO
        // check time TODO
        return true;
    }

    public Boolean isCurrentSemester() {
        Date dateTime = new Date();
        if (dateTime.before(this.startDateObj))
            return false;
        if (dateTime.after(this.endDateObj))
            return false;
        return true;
    }

    public Boolean isInstructor(String instructor) {
        return instructor.equals(this.instructor);
    }

    public Boolean isBuilding(String building) {
        return building.equals(this.building);
    }

    public Boolean isRoom(String building, String roomNumber) {
        return building.equals(this.building) && roomNumber.equals(this.roomNumber);
    }

    public Boolean isCourse(String department, String number) {
        return department.equals(this.department) && number.equals(this.number);
    }

    public String getDepartment() {
        return this.department;
    }

    public String getBuilding() {
        return this.building;
    }

    public String getInstructor() {
        return this.instructor;
    }
}
