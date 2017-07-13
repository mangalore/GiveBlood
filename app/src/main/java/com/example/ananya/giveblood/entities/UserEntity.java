package com.example.ananya.giveblood.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by akokala on 5/23/2017.
 */

public class UserEntity extends BaseEntity {
    @SerializedName("_id")
    private String objectId;

    private String bloodGroup;
    private String userName;
    private String phoneNumber;
    private String password;
    private String location;
    private String email;
    private String isDonar;

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIsDonar() {
        return isDonar;
    }

    public void setIsDonar(String isDonar) {
        this.isDonar = isDonar;
    }
}
