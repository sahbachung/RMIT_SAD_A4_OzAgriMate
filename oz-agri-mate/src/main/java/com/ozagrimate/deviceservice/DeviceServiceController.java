package com.ozagrimate.deviceservice;


import org.apache.coyote.Response;
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

    private DeviceServiceLayer deviceServiceLayer;

    public DeviceServiceController() {
        this.deviceServiceLayer = new DeviceServiceLayer();
    }

    @GetMapping ("/device")
    public ResponseEntity<Map<Integer, Device>> getAllDevices () {
        return ResponseEntity.ok(this.deviceServiceLayer.getDevices());
    }

    @GetMapping ("/device/{deviceId}")
    public ResponseEntity<Device> getDeviceById (@PathVariable int deviceId) {
        try {
            Device device = this.deviceServiceLayer.getDeviceById(deviceId);
            return ResponseEntity.ok(device);
        }
        catch (DeviceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping ("/device")
    public ResponseEntity<Device> registerDevice (@RequestBody DeviceRequest deviceRequest) {
        Device device = this.deviceServiceLayer.registerDevice(deviceRequest);
        return ResponseEntity.ok(device);
    }

    @DeleteMapping ("/device/{deviceId}")
    public ResponseEntity<Void> deregisterDevice (@PathVariable int deviceId) {
        if(this.deviceServiceLayer.deregisterDevice(deviceId)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/device/{deviceId}")
    public ResponseEntity<Device> updateDevice (@PathVariable int deviceId, @RequestBody DeviceRequest deviceRequest) {
        try {
            Device device = this.deviceServiceLayer.updateDeviceById(deviceId, deviceRequest);
            return ResponseEntity.ok(device);
        }
        catch (DeviceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
