package com.ozagrimate.deviceservice;

import java.util.concurrent.atomic.AtomicInteger; //Thread safe ID counter
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Device {
    private static final AtomicInteger idCounter = new AtomicInteger(0);
    private  static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final int deviceId;

    private String manufacturer;
    private String deviceType;
    private String installDt;
    private String nextMaintenanceDt;
    private String lastMaintenanceDt;
    private int maintenanceFrequencyDays;
    private String ipAddress;

    public Device () {
        this.deviceId = idCounter.incrementAndGet(); //Thread safe ID increment once during construction
    }

    public int getDeviceId() {
        return this.deviceId;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }
    public String getManufacturer() {
        return this.manufacturer;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }
    public String getDeviceType() {
        return this.deviceType;
    }

    public void setInstallDt (String dt) {
        LocalDateTime parseDt = LocalDateTime.parse(dt, dateTimeFormatter);
        this.installDt = parseDt.format(dateTimeFormatter);
        // If lastMaintenanceDt is null or empty, or is less than the new install date, set it to be the install date.
        if (this.lastMaintenanceDt == null || this.lastMaintenanceDt.isEmpty() || parseDt.isAfter(LocalDateTime.parse(this.lastMaintenanceDt, dateTimeFormatter))) {
            this.lastMaintenanceDt = parseDt.format(dateTimeFormatter);
        }
        if (this.maintenanceFrequencyDays > 0) {
            LocalDateTime nextDt = parseDt.plusDays(this.maintenanceFrequencyDays);
            this.nextMaintenanceDt = nextDt.format(dateTimeFormatter);
        }

    }
    public String getInstallDt () {
        return this.installDt;
    }

    public void setMaintenanceFrequencyDays(int days) {
        this.maintenanceFrequencyDays = days;
    }
    public int getMaintenanceFrequencyDays () {
        return this.maintenanceFrequencyDays;
    }

    public void setIpAddress (String ipAddress) {
        this.ipAddress = ipAddress;
    }
    public String getIpAddress () {
        return this.ipAddress;
    }

    public String getNextMaintenanceDt () {
        return this.nextMaintenanceDt;
    }

    public String getLastMaintenanceDt () {
        return this.lastMaintenanceDt;
    }

    public void performMaintenance (String maintDt) {
        LocalDateTime parseDt = LocalDateTime.parse(maintDt, dateTimeFormatter);
        this.lastMaintenanceDt = parseDt.format(dateTimeFormatter);
        if (this.maintenanceFrequencyDays > 0 ) {
            LocalDateTime nextDt = parseDt.plusDays(this.maintenanceFrequencyDays);
            this.nextMaintenanceDt = nextDt.format(dateTimeFormatter);
        }
    }

    public String toString() {
        return "Device {" +
                "deviceId=" + this.getDeviceId() +
                ", ipAddress=" + this.getIpAddress() +
                ", manufacturer=" + this.getManufacturer() +
                ", deviceType=" + this.getDeviceType() +
                ", installDt=" + this.getInstallDt() +
                ", lastMaintenanceDt=" + this.getLastMaintenanceDt() +
                ", nextMaintenanceDt=" + this.getNextMaintenanceDt() +
                ", maintenanceFrequencyDays=" + this.getMaintenanceFrequencyDays() +
                "}";
    }

}
