package com.example.saruhan.not;

/**
 * Created by saruhan on 21.09.2017.
 */

public class User {
    String name;
    String surname;
    String email;
    String facultyName;
    String departmentName;

    public User(){}

    public User(String name, String surname, String email, String facultyName, String departmentName){
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.facultyName = facultyName;
        this.departmentName = departmentName;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

}
