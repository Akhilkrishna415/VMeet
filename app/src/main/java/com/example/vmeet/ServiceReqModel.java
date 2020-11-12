package com.example.vmeet;

import android.net.Uri;

public class ServiceReqModel {

    private String RoomNumber;
    private String description;
    private String status;
    private String userEmail;

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDocuId() {
        return docuId;
    }

    public void setDocuId(String docuId) {
        this.docuId = docuId;
    }

    private String docuId;
    private Uri url;

    /**
     * @param title
     * @param description
     * @param url
     * @param status
     * @param userEmail
     * @param docuId
     */
    public ServiceReqModel(String title, String description, Uri url, String status, String userEmail, String docuId) {
        this.RoomNumber = title;
        this.description = description;
        this.url = url;
        this.status = status;
        this.userEmail = userEmail;
        this.docuId = docuId;
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
