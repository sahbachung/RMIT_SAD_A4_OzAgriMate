package com.ozagrimate.deviceservice;

import java.util.concurrent.atomic.AtomicInteger; // Thread-safe ID Counter

public class MaintenanceEvent {

    private static final AtomicInteger idCounter = new AtomicInteger(0);
    private final int maintenanceEventId;
    private final int deviceId;
    private String eventDttm;
    private String username;
    private String fieldComments = "";


    public MaintenanceEvent (int deviceId) {
        this.maintenanceEventId = idCounter.incrementAndGet();
        this.deviceId = deviceId;

    }


    public int getDeviceId() {
        return this.deviceId;
    }

    public int getMaintenanceEventId() {
        return this.maintenanceEventId;
    }

    public String getFieldComments() {
        return this.fieldComments;
    }

    public void setFieldComments(String fieldComments) {
        this.fieldComments = fieldComments;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEventDttm() {
        return eventDttm;
    }

    public void setEventDttm(String eventDttm) {
        this.eventDttm = eventDttm;
    }

    @Override
    public String toString() {
        return "MaintenanceEvent: {" +
                "maintenanceEventId:" + this.maintenanceEventId +
                ", deviceId:" + this.deviceId +
                ", eventDttm:" + this.eventDttm +
                ", username:" + this.username +
                ", fieldComments:" + this.fieldComments +
                "}";
    }
}
