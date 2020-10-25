package com.example.vmeet;

public class RecycleModel {

    private String Title;
    private String Room;
    private String time;

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    private String imageURL;

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


    public RecycleModel(String title,String time, String room,String imageURL)
    {
        this.Title = title;
        this.Room = room;
        this.time = time;
        this.imageURL = imageURL;

    }

}
