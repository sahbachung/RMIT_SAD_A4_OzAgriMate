package com.ozagrimate.deviceservice;

public class MaintenanceEventFieldCommentRequest {
    private int deviceId;
    private int maintenanceEventId;
    private String fieldComments;

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public int getMaintenanceEventId() {
        return maintenanceEventId;
    }

    public void setMaintenanceEventId(int maintenanceEventId) {
        this.maintenanceEventId = maintenanceEventId;
    }

    public String getFieldComments() {
        return fieldComments;
    }

    public void setFieldComments(String fieldComments) {
        this.fieldComments = fieldComments;
    }
}
