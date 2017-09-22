package com.example.saruhan.not;

/**
 * Created by saruhan on 21.09.2017.
 */

public class Lesson {
    String lessonName, key ,departmentName;

    public Lesson(){}

    public Lesson(String key, String lessonName, String departmentName){
        this.key = key;
        this.lessonName = lessonName;
        this.departmentName = departmentName;

    }

    public String getLessonName() {
        return lessonName;
    }

    public String getKey() {
        return key;
    }

    public String getDepartmentName() {
        return departmentName;
    }
}
