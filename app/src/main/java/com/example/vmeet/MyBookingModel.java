package com.example.vmeet;

public class MyBookingModel {
    private String Title;
    private String Room;
    private String time;

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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public MyBookingModel (String title,String time, String room)
    {
        this.Title = title;
        this.Room = room;
        this.time = time;

    }
}
