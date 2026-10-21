package com.ozagrimate.alertservice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("alert-service")
public class AlertServiceController {

    private AlertServiceBusinessLogic alertServiceBusinessLogic;

    public AlertServiceController () {
        this.alertServiceBusinessLogic = new AlertServiceBusinessLogic();
    }

    @GetMapping("/alerts")
    public ResponseEntity<Map<Integer, Alert>> getAlerts(@RequestParam(required = false) Boolean active) {
        return ResponseEntity.ok(this.alertServiceBusinessLogic.getAllAlerts(active));
    }

    @GetMapping("/alerts/{alertId}")
    public ResponseEntity<Alert> getAlert(@PathVariable int alertId) {
        try {
            return ResponseEntity.ok(this.alertServiceBusinessLogic.getAlertById(alertId));
        }
        catch (AlertNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/alerts")
    public ResponseEntity<Alert> createAlert(@RequestBody AlertCreationRequest alertRequest) {
         return ResponseEntity.ok(this.alertServiceBusinessLogic.createAlert(alertRequest));
    }

    @PutMapping("/alerts/{alertId}")
    public ResponseEntity<Alert> updateAlert(@PathVariable int alertId, @RequestBody AlertUpdateRequest alertRequest) {
        try {
            return ResponseEntity.ok(this.alertServiceBusinessLogic.updateAlert(alertId, alertRequest));
        }
        catch (AlertNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/alerts/{alertId}")
    public ResponseEntity<Void> deleteAlert(@PathVariable int alertId) {
        try {
            this.alertServiceBusinessLogic.deleteAlert(alertId);
            return ResponseEntity.noContent().build();
        }
        catch (AlertNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
