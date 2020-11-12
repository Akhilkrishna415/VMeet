package com.example.vmeet;

public class HwSwModel {
    private String title;
    private String version;
    private String documentID;
    private boolean isActive;

    /**
     * @param title
     * @param version
     * @param documentID
     * @param isActive
     */
    public HwSwModel(String title, String version, String documentID, boolean isActive) {
        this.title = title;
        this.version = version;
        this.documentID = documentID;
        this.isActive = isActive;
    }

    public String getDocumentID() {
        return documentID;
    }

    public void setDocumentID(String documentID) {
        this.documentID = documentID;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
