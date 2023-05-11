package com.example.uploadfile;

import java.util.UUID;

public class HiddenData {

    private String id; //the unique UUID
    private String sheetName;
    private String location;
    private boolean hiddenSheet;

    public HiddenData() {

    }

    public HiddenData(String sheetName, String location) {
        super();
        this.setId(UUID.randomUUID().toString());
        this.sheetName = sheetName;
        this.location = location;
    }

    public HiddenData(String sheetName, String location, boolean hiddenSheet) {
        super();
        this.setId(UUID.randomUUID().toString());
        this.sheetName = sheetName;
        this.location = location;
        this.hiddenSheet = hiddenSheet;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isHiddenSheet() {
        return hiddenSheet;
    }

    public void setHiddenSheet(boolean hiddenSheet) {
        this.hiddenSheet = hiddenSheet;
    }

    @Override
    public String toString() {
        return "HiddenData{" +
                "id='" + id + '\'' +
                ", sheetName='" + sheetName + '\'' +
                ", location='" + location + '\'' +
                ", hiddenSheet=" + hiddenSheet +
                '}';
    }

}
