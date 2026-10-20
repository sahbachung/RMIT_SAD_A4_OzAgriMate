package com.ozagrimate.deviceservice;


import com.ozagrimate.util.LoadServiceConfiguration;
import com.ozagrimate.util.ServiceConfig;
import com.ozagrimate.util.ServiceNotFoundException;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap; // Thread Safe HashMap for ID value retrieval

public class DeviceServiceBusinessLogic {
    // Map<deviceId, Device>
    // Use ConcurrentHashMap to be thread safe
    private Map<Integer, Device> devices = new ConcurrentHashMap<>();
    //Map<deviceId, Map<maintenanceEventId, MaintenanceEvent>>
    // Use ConcurrentHashMap to be thread safe
    private Map<Integer, Map<Integer, MaintenanceEvent>> maintenaceEvents = new ConcurrentHashMap<>();
    private final RestTemplate restTemplate;

    public DeviceServiceBusinessLogic() {
        this.restTemplate = new RestTemplate();
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
        this.devices.put(device.getDeviceId(), device);
        //Create a MaintenanceEvent with user=sysuser and fieldComments=Device Installed
        MaintenanceEventRequest installEvent = new MaintenanceEventRequest();
        installEvent.setUsername("sysuser");
        installEvent.setDeviceId(device.getDeviceId());
        installEvent.setEventDttm(device.getInstallDt());
        installEvent.setFieldComments("Device Installed");
        try {
            this.logMaintenanceEvent(installEvent);
        }
        catch (DeviceNotFoundException e) {
            // This should not happen because we have just installed the Device
            System.out.println(e);
        }
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

    private Device removeDevice(int deviceId) throws DeviceNotFoundException {
        Device device = this.devices.remove(deviceId);
        if (device == null) {
            throw new DeviceNotFoundException("deviceId=" + deviceId);
        }
        return device;
    }

    public Map<Integer, MaintenanceEvent> getDeviceMaintenanceLog(int deviceId) throws DeviceNotFoundException {
        if (this.getDeviceById(deviceId)==null) {
            throw new DeviceNotFoundException("deviceId=" + deviceId);
        }
        try {
            return this.maintenaceEvents.get(deviceId);
        }
        catch (NullPointerException e) {
            throw new DeviceNotFoundException("deviceId=" + deviceId);
        }
    }

    public MaintenanceEvent logMaintenanceEvent(MaintenanceEventRequest maintenanceEventRequest) throws DeviceNotFoundException, ResponseStatusException, MaintenanceEventException {
        // First, check if Username exists and is active
        // This template modified from one of the workshop solutions for PatientMonitoringSystem
        ServiceConfig userService = null;
        for (ServiceConfig service : LoadServiceConfiguration.loadServiceConfig()) {
            if (service.getServiceName().equals("user-service")) {
                userService = service;
            }
        }
        if (userService==null) {
            throw new ServiceNotFoundException("user-service");
        }
        String userEndpoint = "http://localhost:" + userService.getPort() + "/user-service/user/" + maintenanceEventRequest.getUsername();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Void> request = new HttpEntity<>(null, headers);
        ResponseEntity<Map> response;
        try {
            response = restTemplate.exchange(userEndpoint, HttpMethod.GET, request, Map.class);
            Map responseBody = response.getBody();
            if (response.getStatusCode().is4xxClientError() || responseBody==null) {
                throw new ResponseStatusException(response.getStatusCode());
            }
            else if (responseBody.get("userActiveSW")==null || responseBody.get("userActiveSW").equals("false")) {
                throw new MaintenanceEventException("Username not active: " +  maintenanceEventRequest.getUsername());
            }
        }
        catch (ResourceAccessException e) {
            System.out.println("WARNING! User Service could not be reached, could not validate username and status");
        }
        // Username exists and is active
        // Now call internal service to retrieve device to check for existence
        Device device = this.getDeviceById(maintenanceEventRequest.getDeviceId());
        device.setLastMaintenanceDt(maintenanceEventRequest.getEventDttm());
        device.updateNextMaintenanceDt();
        Map<Integer, MaintenanceEvent> events = this.maintenaceEvents.get(maintenanceEventRequest.getDeviceId());
        //check if no events exist
        if (events == null || events.isEmpty()) {
            this.maintenaceEvents.put(maintenanceEventRequest.getDeviceId(), new ConcurrentHashMap<>());
            events = this.maintenaceEvents.get(maintenanceEventRequest.getDeviceId());
        }
        MaintenanceEvent event = new MaintenanceEvent(maintenanceEventRequest.getDeviceId());
        event.setEventDttm(maintenanceEventRequest.getEventDttm());
        event.setUsername(maintenanceEventRequest.getUsername());
        event.setFieldComments(maintenanceEventRequest.getFieldComments());
        events.put(event.getMaintenanceEventId(), event);
        return event;
    }

    public void deleteMaintenanceEvent(int deviceId, int maintenanceEventId) throws DeviceNotFoundException, MaintenanceEventException{
        // Check for device existence, which will throw DeviceNotFoundException if it doesn't exist
        this.getDeviceById(deviceId);
        Map<Integer, MaintenanceEvent> events = this.maintenaceEvents.get(deviceId);
        // Check if the Maintenance Device is null or empty and throw MaintenanceEventException
        if (events == null || events.isEmpty()) {
            throw new MaintenanceEventException("deviceId=" + deviceId + " has no MaintenanceEvents");
        }
        // Check if the MaintenanceEventId exists for this device, if not throw MaintenanceEventException
        if(!events.containsKey(maintenanceEventId)){
            throw new MaintenanceEventException("deviceId=" + deviceId + " does not contain maintenanceEventId=" + maintenanceEventId);
        }
        events.remove(maintenanceEventId);
    }

    public MaintenanceEvent updateMaintenanceEventComment(MaintenanceEventFieldCommentRequest commentRequest) throws DeviceNotFoundException, MaintenanceEventException {
        // Check for device existence, which will throw DeviceNotFoundException if it doesn't exist
        this.getDeviceById(commentRequest.getDeviceId());
        Map<Integer, MaintenanceEvent> events = this.maintenaceEvents.get(commentRequest.getDeviceId());
        // Check if the Maintenance Device is null or empty and throw MaintenanceEventException
        if(events==null || !events.containsKey(commentRequest.getMaintenanceEventId())){
            throw new MaintenanceEventException("deviceId=" + commentRequest.getDeviceId() + " does not contain maintenanceEventId=" + commentRequest.getMaintenanceEventId());
        }
        MaintenanceEvent event = events.get(commentRequest.getMaintenanceEventId());
        event.setFieldComments(commentRequest.getFieldComments());
        return event;
    }

}
