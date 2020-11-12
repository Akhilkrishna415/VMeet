package com.example.vmeet;

public class RoomsModel {
    private String floor;
    private String room_type;
    private String room_number;
    private String documentID;

    /**
     * @param floor
     * @param room_type
     * @param room_number
     * @param documentID
     */

    public RoomsModel(String floor, String room_type, String room_number, String documentID) {
        this.floor = floor;
        this.room_type = room_type;
        this.room_number = room_number;
        this.documentID = documentID;
    }

    public String getDocumentID() {
        return documentID;
    }

    public void setDocumentID(String documentID) {
        this.documentID = documentID;
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
