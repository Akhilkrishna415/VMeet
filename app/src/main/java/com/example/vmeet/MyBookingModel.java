package com.example.vmeet;

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


    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getHardwares() {
        return Hardwares;
    }

    public void setHardwares(String hardwares) {
        Hardwares = hardwares;
    }

    public String getSoftwares() {
        return Softwares;
    }

    public void setSoftwares(String softwares) {
        Softwares = softwares;
    }

    public String getAddComments() {
        return AddComments;
    }

    public void setAddComments(String addComments) {
        AddComments = addComments;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }


    public String getRoom() {
        return Room;
    }

    public void setRoom(String room) {
        Room = room;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getDocumentID() {
        return documentID;
    }

    public void setDocumentID(String documentID) {
        this.documentID = documentID;
    }

    @Override
    public String toString() {
        return Title + "," +  start_time + "," + end_time + ","  + Hardwares + ","  + Softwares + ","  + AddComments + ","  + Username + "," + Room + "," + date + "," + documentID;
    }
}
