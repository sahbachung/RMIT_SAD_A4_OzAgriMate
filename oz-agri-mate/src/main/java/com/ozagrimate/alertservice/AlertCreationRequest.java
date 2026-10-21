package com.ozagrimate.alertservice;

import java.util.ArrayList;

public class AlertCreationRequest {

    private String alertMessage;
    private String alertType;
    private String alertSeverity;
    private ArrayList<String> users;
    private String source;
    private String startDttm;

    public String getAlertMessage() {
        return alertMessage;
    }

    public void setAlertMessage(String alertMessage) {
        this.alertMessage = alertMessage;
    }

    public String getAlertType() {
        return alertType;
    }

    public void setAlertType(String alertType) {
        this.alertType = alertType;
    }

    public String getAlertSeverity() {
        return alertSeverity;
    }

    public void setAlertSeverity(String alertSeverity) {
        this.alertSeverity = alertSeverity;
    }

    public ArrayList<String> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<String> users) {
        this.users = users;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getStartDttm() {
        return startDttm;
    }

    public void setStartDttm(String startDttm) {
        this.startDttm = startDttm;
    }

    @Override
    public String toString() {
        return "AlertCreationRequest:{" +
                "alertMessage:" + this.alertMessage +
                ", alertType:" + this.alertType +
                ", alertSeverity:" + this.alertSeverity +
                ", source:" + this.source +
                ", startDttm:" + this.startDttm +
                "}";
    }

}
