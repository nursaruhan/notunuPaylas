package com.example.saruhan.not;

import android.app.Application;

import com.firebase.client.Firebase;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by saruhan on 23.08.2017.
 */

//public class Faculty extends Application{
    public class Faculty {

        String facultyName,key;

        public Faculty(){}

        public Faculty(String facultyName,String key){
            this.facultyName = facultyName;
            this.key = key;
        }

        public String getFacultyName() {
            return facultyName;
        }

        public String getKey() {
            return key;
        }

    }



    /*    String facultyID, facultyName;

    public Faculty() {
    }

    public Faculty(String facultyID, String facultyName) {
        this.facultyID = facultyID;
        this.facultyName = facultyName;
    }

    public String getFacultyID() {
        return facultyID;
    }

    public void setFacultyID(String facultyID) {
        this.facultyID = facultyID;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }*/
/*
    @Override
    public void onCreate() {
        super.onCreate();

        if (!FirebaseApp.getApps(this).isEmpty()) {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        }
    }*/

