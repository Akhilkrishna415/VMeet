package com.example.vmeet;

/*
 * my bookings model class
 */

public class MyBookingModel {
    private String Title;
    private String start_time;
    private String end_time;
    private String Hardwares;
    private String Softwares;
    private String AddComments;
    private String Username;


    private String documentID;
    private String Room;
    private String date;

    /**
     * @param start_time
     * @param end_time
     * @param hardwares
     * @param softwares
     * @param addComments
     * @param username
     * @param room
     * @param date
     */

    public MyBookingModel(String title, String start_time, String end_time, String hardwares, String softwares, String addComments, String username, String room, String date, String documentID) {

        Title = title;
        this.start_time = start_time;
        this.end_time = end_time;
        Hardwares = hardwares;
        Softwares = softwares;
        AddComments = addComments;
        Username = username;
        Room = room;
        this.date = date;
        this.documentID = documentID;
    }

    /**
     * get starttime
     *
     * @return : startTime
     */
    public String getStart_time() {
        return start_time;
    }

    /**
     * set startTime
     *
     * @param start_time
     */
    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    /**
     * get endTime
     *
     * @return : end time
     */
    public String getEnd_time() {
        return end_time;
    }

    /**
     * set endTime
     *
     * @param end_time
     */

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    /**
     * get Hardwares
     *
     * @return : hardwares
     */

    public String getHardwares() {
        return Hardwares;
    }

    /**
     * set hardwares
     *
     * @param : hardwares
     */

    public void setHardwares(String hardwares) {
        Hardwares = hardwares;
    }

    /**
     * get softwares
     *
     * @return : softwares
     */

    public String getSoftwares() {
        return Softwares;
    }

    /**
     * set softwares
     *
     * @param softwares
     */

    public void setSoftwares(String softwares) {
        Softwares = softwares;
    }

    /**
     * get addcomments
     *
     * @return : addcomments
     */

    public String getAddComments() {
        return AddComments;
    }

    /**
     * set addComments
     *
     * @param addComments
     */

    public void setAddComments(String addComments) {
        AddComments = addComments;
    }

    /**
     * get Title
     *
     * @return : title
     */

    public String getTitle() {
        return Title;
    }

    /**
     * set Title
     *
     * @param title
     */

    public void setTitle(String title) {
        Title = title;
    }

    /**
     * get Room
     *
     * @return : Room
     */


    public String getRoom() {
        return Room;
    }

    /**
     * set room
     *
     * @param room
     */

    public void setRoom(String room) {
        Room = room;
    }

    /**
     * get Date
     *
     * @return : Date
     */


    public String getDate() {
        return date;
    }

    /**
     * set Date
     *
     * @param date
     */

    public void setDate(String date) {
        this.date = date;
    }

    /**
     * get Username
     *
     * @return : username
     */

    public String getUsername() {
        return Username;
    }

    /**
     * set username
     *
     * @param username
     */

    public void setUsername(String username) {
        Username = username;
    }

    /**
     * get documentID
     *
     * @return : documentID
     */

    public String getDocumentID() {
        return documentID;
    }

    /**
     * set DocumentID
     *
     * @param documentID
     */

    public void setDocumentID(String documentID) {
        this.documentID = documentID;
    }

    @Override
    public String toString() {
        return Title + "," + start_time + "," + end_time + "," + Hardwares + "," + Softwares + "," + AddComments + "," + Username + "," + Room + "," + date + "," + documentID;
    }
}
