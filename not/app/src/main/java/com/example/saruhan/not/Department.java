package com.example.saruhan.not;

/**
 * Created by yzbhr on 19.09.2017.
 */

public class Department {

    String departmentName,key,facultyKey;

    public Department(){}

    public Department(String key, String departmentName, String facultyKey){
        this.key = key;
        this.departmentName = departmentName;
        this.facultyKey = facultyKey;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public String getKey() {
        return key;
    }

    public String getFacultyKey() {
        return facultyKey;
    }
}
