package com.ozagrimate.deviceservice;

public class MaintenanceEventRequest {
    private  int deviceId;
    private String eventDttm;
    private String username;
    private String fieldComments;


    public int getDeviceId() {
        return this.deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public String getEventDttm() {
        return this.eventDttm;
    }

    public void setEventDttm(String eventDttm) {
        this.eventDttm = eventDttm;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFieldComments() {
        return this.fieldComments;
    }

    public void setFieldComments(String fieldComments) {
        this.fieldComments = fieldComments;
    }

    @Override
    public String toString() {
        return "MaintenanceEventRequest:{" +
                "deviceId:" + this.deviceId +
                "eventDttm:" + this.eventDttm +
                "username:" + this.username +
                "fieldComments:" + this.fieldComments +
                "}";
    }
}
