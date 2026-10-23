package com.ozagrimate.reportingservice;

import jakarta.websocket.server.PathParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("reporting-service")
public class ReportingServiceController {
    private ReportingServiceBusinessLogic reportingServiceBusinessLogic;

    public ReportingServiceController() {
        this.reportingServiceBusinessLogic = new ReportingServiceBusinessLogic();
    }

    @GetMapping("/reports")
    public ResponseEntity<Map<Integer, Report>> getAllReports(@RequestParam(required=false) Report.Status status) {
        return ResponseEntity.ok(this.reportingServiceBusinessLogic.getAllReports(status));
    }

    @GetMapping("/reports/{reportId}")
    public ResponseEntity<Report> getReportById(@PathVariable int reportId) {
        try {
            return ResponseEntity.ok(this.reportingServiceBusinessLogic.getReportById(reportId));
        }
        catch (ReportNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/reports")
    public ResponseEntity<Report> createReport(@RequestBody ReportRequest reportRequest) {
        try {
            return ResponseEntity.ok(this.reportingServiceBusinessLogic.createReport(reportRequest));
        }
        catch(NullPointerException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/reports/{reportId}")
    public ResponseEntity<Report> updateReport(@PathVariable int reportId, @RequestBody ReportRequest reportRequest) {
        try {
            return ResponseEntity.ok(this.reportingServiceBusinessLogic.updateReport(reportId, reportRequest));
        }
        catch (ReportNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/reports/{reportId}")
    public ResponseEntity<Void> deleteReport(@PathVariable int reportId) {
        this.reportingServiceBusinessLogic.deleteReport(reportId);
        return ResponseEntity.noContent().build();
    }
}
