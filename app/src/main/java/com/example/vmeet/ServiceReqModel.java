package com.example.vmeet;

import android.net.Uri;

public class ServiceReqModel {

    private String RoomNumber, description, status, userEmail;
    private Uri url;

    public ServiceReqModel(String roomNumber, String description, String status, String userEmail) {
    }

    public ServiceReqModel(String title, String description, Uri url, String status, String userEmail) {
        this.RoomNumber = title;
        this.description = description;
        this.url = url;
        this.status = status;
        this.userEmail = userEmail;
    }

    public Uri getUrl() {
        return url;
    }

    public void setUrl(Uri url) {
        this.url = url;
    }

    public String getRoomNumber() {
        return RoomNumber;
    }

    public void setRoomNumber(String RoomNumber) {
        this.RoomNumber = RoomNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setVersion(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String status) {
        this.userEmail = userEmail;
    }

}
