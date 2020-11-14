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
     * @param title       : room number
     * @param description : refers the description
     * @param url         : url of the image
     * @param status      : status of the request
     * @param userEmail   : refers the users email
     * @param docuId      :  refers the documentID
     */
    public ServiceReqModel(String title, String description, Uri url, String status, String userEmail, String docuId) {
        this.RoomNumber = title;
        this.description = description;
        this.url = url;
        this.status = status;
        this.userEmail = userEmail;
        this.docuId = docuId;
    }

    /**
     * get url
     *
     * @return : url of the image
     */
    public Uri getUrl() {
        return url;
    }

    /**
     * set the url
     *
     * @param url
     */
    public void setUrl(Uri url) {
        this.url = url;
    }

    /**
     * get room number
     *
     * @return :Room number
     */
    public String getRoomNumber() {
        return RoomNumber;
    }

    /**
     * to set the room number
     *
     * @param RoomNumber
     */
    public void setRoomNumber(String RoomNumber) {
        this.RoomNumber = RoomNumber;
    }

    /**
     * get description
     *
     * @return : it returns the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * set the description
     *
     * @param description
     */
    public void setVersion(String description) {
        this.description = description;
    }

    /**
     * get the status
     *
     * @return : it returns the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * set the status
     *
     * @param status : refers the status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * get users email
     *
     * @return : userEmail
     */
    public String getUserEmail() {
        return userEmail;
    }

    /**
     * set the user email
     *
     * @param status : it refers the status
     */
    public void setUserEmail(String status) {
        this.userEmail = userEmail;
    }

}
