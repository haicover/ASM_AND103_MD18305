package com.example.and103_assignmentht.model;

public class StudentModel {
    private String id;
    private String name;
    private String mssv;
    private String image;


    public StudentModel(String id, String name, String mssv,  String image) {
        this.id = id;
        this.name = name;
        this.mssv = mssv;
        this.image = image;
    }

    public StudentModel() {
    }

    public String getid() {
        return id;
    }

    public void setid(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMssv() {
        return mssv;
    }

    public void setMssv(String mssv) {
        this.mssv = mssv;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
