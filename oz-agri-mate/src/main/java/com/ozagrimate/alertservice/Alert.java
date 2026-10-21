package com.ozagrimate.alertservice;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Alert {

    private static final AtomicInteger idCounter = new AtomicInteger(0); // Thread safe id counter
    private final int alertId;
    private String alertMessage;
    private String alertType;
    private String alertSeverity;
    private ArrayList<String> users;
    private String source;
    private boolean active;
    private String startDttm;
    private String updateDttm;

    public Alert() {
        this.alertId = idCounter.incrementAndGet();
        this.users = new ArrayList<>();
        this.active = true;
    }


    public int getAlertId() {
        return alertId;
    }

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

    public void addUser(String user) {
        if (this.users.contains(user)) {
            return;
        }
        this.users.add(user);
    }

    public void removeUser(String user) {
        this.users.remove(user);
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getStartDttm() {
        return startDttm;
    }

    public void setStartDttm(String startDttm) {
        this.startDttm = startDttm;
    }

    public String getUpdateDttm() {
        return updateDttm;
    }

    public void setUpdateDttm(String updateDttm) {
        this.updateDttm = updateDttm;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public String toString() {
        return "Alert: {" +
                "alertId:" + this.alertId +
                ", active:" + this.active +
                ", alertMessage:" + this.alertMessage +
                ", alertType:" + this.alertType +
                ", alertSeverity:" + this.alertSeverity +
                ", source:" + this.source +
                ", startDttm:" + this.startDttm +
                ", updateDttm:" + this.updateDttm +
                "}";
    }
}
