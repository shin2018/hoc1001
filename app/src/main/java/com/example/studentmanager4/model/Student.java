package com.example.studentmanager4.model;


public class Student {
    private String id;
    private String name;
    private String className;
    private String phone;

    public Student(String id, String name, String className, String phone) {
        this.id = id;
        this.name = name;
        this.className = className;
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getClassName() {
        return className;
    }

    public String getPhone() {
        return phone;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
