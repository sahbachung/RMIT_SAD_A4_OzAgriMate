package com.ozagrimate.deviceservice;

public class DeviceRequest {

    private String manufacturer;
    private String deviceType;
    private String installDt;
    private int maintenanceFrequencyDays;
    private String ipAddress;

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }
    public String getManufacturer() {
        return this.manufacturer;
    }

    public void setDeviceType (String deviceType) {
        this.deviceType = deviceType;
    }
    public String getDeviceType() {
        return this.deviceType;
    }

    public void setInstallDt(String installDt) {
        this.installDt = installDt;
    }
    public String getInstallDt () {
        return this.installDt;
    }

    public void setMaintenanceFrequencyDays(int days) {
        this.maintenanceFrequencyDays = days;
    }
    public int getMaintenanceFrequencyDays() {
        return this.maintenanceFrequencyDays;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
    public String getIpAddress() {
        return this.ipAddress;
    }
}
