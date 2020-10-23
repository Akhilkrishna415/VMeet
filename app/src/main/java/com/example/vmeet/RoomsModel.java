package com.example.vmeet;

public class RoomsModel {
    private  String floor, room_type, room_number;

    public RoomsModel(String floor, String room_type, String room_number) {
        this.floor = floor;
        this.room_type = room_type;
        this.room_number = room_number;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getRoom_type() {
        return room_type;
    }

    public void setRoom_type(String room_type) {
        this.room_type = room_type;
    }

    public String getRoom_number() {
        return room_number;
    }

    public void setRoom_number(String room_number) {
        this.room_number = room_number;
    }
}
