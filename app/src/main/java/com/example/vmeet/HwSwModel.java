package com.example.vmeet;

public class HwSwModel {
    private String title, version;
    private boolean isActive;

    public HwSwModel(String title, String version, boolean isActive) {
        this.title = title;
        this.version = version;
        this.isActive = isActive;
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