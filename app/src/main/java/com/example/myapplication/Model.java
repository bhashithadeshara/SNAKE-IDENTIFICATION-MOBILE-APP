package com.example.myapplication;

public class Model {
    private int id;
    private byte[]proavatar;
    private String snakeName;

    //constructor

    public Model(int id, byte[] proavatar, String snakeName) {
        this.id = id;
        this.proavatar = proavatar;
        this.snakeName = snakeName;
    }
    //getter and setter method

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getProavatar() {
        return proavatar;
    }

    public void setProavatar(byte[] proavatar) {
        this.proavatar = proavatar;
    }

    public String getUsername() {
        return snakeName;
    }

    public void setUsername(String username) {
        this.snakeName = username;
    }
}
