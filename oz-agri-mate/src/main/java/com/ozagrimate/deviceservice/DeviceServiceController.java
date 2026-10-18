package com.ozagrimate.deviceservice;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@RestController
@RequestMapping("/device-service")
public class DeviceServiceController {

    private DeviceServiceBusinessLogic deviceServiceBusinessLogic;

    public DeviceServiceController() {
        this.deviceServiceBusinessLogic = new DeviceServiceBusinessLogic();
    }

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
        Device device = this.deviceServiceBusinessLogic.registerDevice(deviceRequest);
        return ResponseEntity.ok(device);
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
            Device device = this.deviceServiceBusinessLogic.updateDeviceById(deviceId, deviceRequest);
            return ResponseEntity.ok(device);
        }
        catch (DeviceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
