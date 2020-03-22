package com.example.uahclasswizard;

public class UAHClass {

    // name,crn,department,number,section,term,credits,start time,end time,days,start date,end date,building,room number,instructor

    String name;
    String crn;
    String department;
    String number;
    String section;
    String term;
    String credits;
    String startTime;
    String endTime;
    String days;
    String startDate;
    String endDate;
    String building;
    String roomNumber;
    String instructor;

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
    }

    public String toString() {
        return this.name + " - " + this.department + " " + this.number + " - " + this.section
                + "\n  " + this.days + " " + this.startTime + " - " + this.endTime
                + "\n  " + this.building + " " + this.roomNumber
                + "\n  " + this.instructor + "\n";
    }
}
