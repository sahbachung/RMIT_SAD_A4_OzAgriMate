package com.ozagrimate.alertservice;

import java.util.ArrayList;

public class AlertUpdateRequest {

    private String alertMessage;
    private String alertSeverity;
    private ArrayList<String> usersToAdd;
    private ArrayList<String> usersToRemove;
    private boolean active;
    public String getAlertMessage() {
        return alertMessage;
    }

    public void setAlertMessage(String alertMessage) {
        this.alertMessage = alertMessage;
    }

    public String getAlertSeverity() {
        return alertSeverity;
    }

    public void setAlertSeverity(String alertSeverity) {
        this.alertSeverity = alertSeverity;
    }

    public ArrayList<String> getUsersToAdd() {
        return usersToAdd;
    }

    public void setUsersToAdd(ArrayList<String> usersToAdd) {
        this.usersToAdd = usersToAdd;
    }

    public ArrayList<String> getUsersToRemove() {
        return usersToRemove;
    }

    public void setUsersToRemove(ArrayList<String> usersToRemove) {
        this.usersToRemove = usersToRemove;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        StringBuilder addUsers = new StringBuilder();
        for (String user : this.getUsersToAdd()) {
            addUsers.append(user);
            addUsers.append(", ");
        }
        if (!addUsers.isEmpty()) {
            addUsers.delete(addUsers.length() - 2, addUsers.length() - 1);
        }
        StringBuilder removeUsers = new StringBuilder();
        for (String user : this.getUsersToRemove()) {
            removeUsers.append(user);
            removeUsers.append(", ");
        }
        if(!removeUsers.isEmpty()) {
            removeUsers.delete(removeUsers.length() - 2, removeUsers.length() - 1);
        }
        return "AlertUpdateRequest:{" +
                ", alertMessage:" + this.alertMessage +
                ", alertSeverity" + this.alertSeverity +
                ", usersToAdd:" + addUsers +
                ", usersToRemove:" + removeUsers +
                "}";
    }
}
