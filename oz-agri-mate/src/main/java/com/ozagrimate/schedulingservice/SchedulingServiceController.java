package com.ozagrimate.schedulingservice;

import jakarta.websocket.server.PathParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/scheduling-service")
public class SchedulingServiceController {
    private SchedulingServiceBusinessLogic schedulingServiceBusinessLogic;
    public SchedulingServiceController() {
        this.schedulingServiceBusinessLogic = new SchedulingServiceBusinessLogic();
    }

    @GetMapping("/schedules")
    public ResponseEntity<Map<Integer, Schedule>> getAllSchedules(@RequestParam(required=false) Schedule.Status status) {
        return ResponseEntity.ok(this.schedulingServiceBusinessLogic.getAllSchedules(status));
    }

    @GetMapping("/schedules/{scheduleId}")
    public ResponseEntity<Schedule> getScheduleById(@PathVariable int scheduleId) {
        try {
            return ResponseEntity.ok(this.schedulingServiceBusinessLogic.getScheduleById(scheduleId));
        }
        catch (ScheduleNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/schedules")
    public ResponseEntity<Schedule> createSchedule(@RequestBody ScheduleRequest scheduleRequest) {
        try {
            return ResponseEntity.ok(this.schedulingServiceBusinessLogic.createSchedule(scheduleRequest));
        } catch (InvalidScheduleDefinitionException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_CONTENT).build();
        }
    }

    @PutMapping("/schedules/{scheduleId}")
    public ResponseEntity<Schedule> updateSchedule(@PathVariable int scheduleId, @RequestBody ScheduleRequest scheduleRequest) {
        // Each field in ScheduleRequest is nullable for PUT requests to remain idempotent
        try  {
            return ResponseEntity.ok(this.schedulingServiceBusinessLogic.updateSchedule(scheduleId, scheduleRequest));
        } catch (InvalidScheduleDefinitionException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_CONTENT).build();
        } catch (ScheduleNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (ScheduleStatusException e) {
            return ResponseEntity.status(HttpStatus.LOCKED).build();
        }
    }

    @DeleteMapping("/schedules/{scheduleId}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable int scheduleId) {
        this.schedulingServiceBusinessLogic.deleteSchedule(scheduleId);
        return ResponseEntity.noContent().build();
    }
}
