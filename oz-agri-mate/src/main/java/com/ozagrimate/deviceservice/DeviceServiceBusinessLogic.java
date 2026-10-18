package com.ozagrimate.deviceservice;


import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap; // Thread Safe HashMap for ID value retrieval

public class DeviceServiceBusinessLogic {

    private Map<Integer, Device> devices = new ConcurrentHashMap<>();

    public DeviceServiceBusinessLogic() {

    }

    public Map<Integer, Device> getDevices () {
        return this.devices;
    }

    public Device getDeviceById (int deviceId) throws DeviceNotFoundException {
        Device device =  this.devices.get(deviceId); // Nullable
        if (device == null) {
            throw new DeviceNotFoundException("deviceId=" + deviceId);
        }
        return device;
    }

    public Device registerDevice (DeviceRequest deviceRequest) {
        Device device = new Device();
        device.setDeviceType(deviceRequest.getDeviceType());
        device.setManufacturer(deviceRequest.getManufacturer());
        device.setMaintenanceFrequencyDays(deviceRequest.getMaintenanceFrequencyDays());
        device.setInstallDt(deviceRequest.getInstallDt());
        device.setIpAddress(deviceRequest.getIpAddress());
        this.addDevice(device);
        return device;
    }

    public boolean deregisterDevice (int deviceId) {
        try {
            this.removeDevice(deviceId);
        }
        catch (DeviceNotFoundException e) {
            return false;
        }
        return true;
    }

    public Device updateDeviceById (int deviceId, DeviceRequest deviceRequest) throws DeviceNotFoundException{
        Device device = this.getDeviceById(deviceId);
        this.getDeviceById(deviceId).setManufacturer(deviceRequest.getManufacturer());
        device.setDeviceType(deviceRequest.getDeviceType());
        device.setMaintenanceFrequencyDays(deviceRequest.getMaintenanceFrequencyDays());
        device.setInstallDt(deviceRequest.getInstallDt());
        device.setIpAddress(deviceRequest.getIpAddress());
        return device;
    }


    private void addDevice(Device device) {
        this.devices.put(device.getDeviceId(), device);
    }

    private Device removeDevice(int deviceId) throws DeviceNotFoundException {
        Device device = this.devices.remove(deviceId);
        if (device == null) {
            throw new DeviceNotFoundException("deviceId=" + deviceId);
        }
        return device;
    }

}
