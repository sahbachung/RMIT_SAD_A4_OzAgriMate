package com.ozagrimate.deviceservice;
import java.time.format.DateTimeParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Map;

@RestController
@RequestMapping("/device-service")
public class DeviceServiceController {

    private DeviceServiceBusinessLogic deviceServiceBusinessLogic;

    public DeviceServiceController() {
        this.deviceServiceBusinessLogic = new DeviceServiceBusinessLogic();
    }

    // /device endpoint
    @GetMapping ("/device")
    public ResponseEntity<Map<Integer, Device>> getAllDevices () {
        return ResponseEntity.ok(this.deviceServiceBusinessLogic.getDevices());
    }

    @GetMapping ("/device/{deviceId}")
    public ResponseEntity<Device> getDeviceById (@PathVariable int deviceId) {
        try {
            Device device = this.deviceServiceBusinessLogic.getDeviceById(deviceId);
            return ResponseEntity.ok(device);
        }
        catch (DeviceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping ("/device")
    public ResponseEntity<Device> registerDevice (@RequestBody DeviceRequest deviceRequest) {
    try {
        Device device = this.deviceServiceBusinessLogic.registerDevice(deviceRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(device);         // 201
    }
    catch (DateTimeParseException | NullPointerException e) {
        return ResponseEntity.badRequest().build();                            // 400: bad or missing installDt
    }
    }

    @DeleteMapping ("/device/{deviceId}")
    public ResponseEntity<Void> deregisterDevice (@PathVariable int deviceId) {
        if(this.deviceServiceBusinessLogic.deregisterDevice(deviceId)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/device/{deviceId}")
    public ResponseEntity<Device> updateDevice (@PathVariable int deviceId, @RequestBody DeviceRequest deviceRequest) {
    try {
        return ResponseEntity.ok(this.deviceServiceBusinessLogic.updateDeviceById(deviceId, deviceRequest));
    }
    catch (DeviceNotFoundException e) {
        return ResponseEntity.notFound().build();                              // 404
    }
    catch (DateTimeParseException | NullPointerException e) {
        return ResponseEntity.badRequest().build();                            // 400
    }
    }

    // /maintenance

    @GetMapping("/maintenance/{deviceId}")
    //TODO This currently returns an empty body
    public ResponseEntity<Map<Integer, MaintenanceEvent>> getDeviceHistory (@PathVariable int deviceId) {
        try {
            return ResponseEntity.ok(this.deviceServiceBusinessLogic.getDeviceMaintenanceLog(deviceId));
        }
        catch (DeviceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

   @PostMapping("/maintenance")
    public ResponseEntity<MaintenanceEvent> addMaintenanceEvent (@RequestBody MaintenanceEventRequest maintenanceEventRequest) {
    try {
        MaintenanceEvent event = this.deviceServiceBusinessLogic.logMaintenanceEvent(maintenanceEventRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(event);          // 201: saved
    }
    catch (DeviceNotFoundException | HttpClientErrorException e) {
        return ResponseEntity.notFound().build();                              // 404: no such device or user
    }
    catch (MaintenanceEventException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();            // 403: user is inactive
    }
    }

    @DeleteMapping("/maintenance/{deviceId}/{maintenanceEventId}")
    public ResponseEntity<Void> deleteMaintenanceEvent(@PathVariable int deviceId, @PathVariable int maintenanceEventId) {
        try {
            this.deviceServiceBusinessLogic.deleteMaintenanceEvent(deviceId, maintenanceEventId);
            return ResponseEntity.noContent().build();
        }
        catch (DeviceNotFoundException | MaintenanceEventException e){
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/maintenance")
    public ResponseEntity<MaintenanceEvent> updateMaintenanceEventComment(@RequestBody MaintenanceEventFieldCommentRequest commentRequest) {
        try {
            return ResponseEntity.ok(this.deviceServiceBusinessLogic.updateMaintenanceEventComment(commentRequest));
        }
        catch (DeviceNotFoundException | MaintenanceEventException e){
            return ResponseEntity.notFound().build();
        }
    }
}
