package com.ozagrimate.schedulingservice;

import com.ozagrimate.reportingservice.Report;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Schedule {
    public enum ScheduleType {
        ONCE_AFTER, EVERY;
    }
    public enum ScheduleTime {
        MIN, HOUR, DAY, WEEK, MONTH, YEAR;
    }
    public enum Status {
        PENDING, RUNNING, COMPLETE, CANCELLED;
    }
    private static final AtomicInteger idCounter = new AtomicInteger(0);
    private final int scheduleId;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private ArrayList<Integer> deviceIds = new ArrayList<>();
    private String updateDttm;
    private Status status;
    private ScheduleType scheduleType;
    private ScheduleTime scheduleTime;
    private long delay;
    private ScheduleTime  durationTime;
    private long duration;

    public Schedule() {
        this.scheduleId = idCounter.incrementAndGet();
        this.updateDttm = LocalDateTime.now().format(formatter);
        this.status = Schedule.Status.PENDING;
    }

    public Schedule(ArrayList<Integer> initialDeviceIds) throws InvalidScheduleDefinitionException {
        this();
        for(int deviceId : initialDeviceIds) {
            this.addDeviceId(deviceId);
        }
    }

    public int getScheduleId() {
        return this.scheduleId;
    }

    public void addDeviceId(int deviceId) throws InvalidScheduleDefinitionException{
        if(this.deviceIds.contains(deviceId)) {
            throw new InvalidScheduleDefinitionException("deviceID=" + deviceId + " already is assigned to this schedule");
        }
        this.deviceIds.add(deviceId);
    }

    public void removeDeviceId(int deviceId) {
        this.deviceIds.remove(Integer.valueOf(deviceId));
    }

    public ArrayList<Integer> getDeviceIds () {
        return this.deviceIds;
    }
    public String getUpdateDttm() {
        return this.updateDttm;
    }

    public void setUpdateDttm(String updateDttm) {
        LocalDateTime.parse(updateDttm, formatter);
        this.updateDttm = updateDttm;
    }

    public Schedule.Status getStatus() {
        return this.status;
    }

    public void setStatus(Schedule.Status status) {
        this.status = status;
    }

    public ScheduleType getScheduleType() {
        return this.scheduleType;
    }

    public ScheduleTime getScheduleTime() {
        return this.scheduleTime;
    }

    public long getDelay() {
        return this.delay;
    }

    public void setSchedule(Schedule.ScheduleType scheduleType, Schedule.ScheduleTime scheduleTime, long delay) {
        this.scheduleType = scheduleType;
        this.scheduleTime = scheduleTime;
        this.delay = delay;
    }

    public Schedule.ScheduleTime getDurationTime() {
        return this.durationTime;
    }

    public long getDuration() {
        return this.duration;
    }

    public void setDuration(Schedule.ScheduleTime durationTime, long duration) {
        this.durationTime =  durationTime;
        this.duration = duration;
    }

    public void setScheduleAndDuration(Schedule.ScheduleType scheduleType, Schedule.ScheduleTime scheduleTime, long delay, Schedule.ScheduleTime durationTime, long duration) {
        this.setSchedule(scheduleType, scheduleTime, delay);
        this.setDuration(scheduleTime, duration);
    }

    public boolean isTimeToRun() {
        if(this.status!=Schedule.Status.PENDING) {
            return false;
        }
        LocalDateTime last = LocalDateTime.parse(this.updateDttm, formatter);
        LocalDateTime now = LocalDateTime.now();
        return switch (this.scheduleTime) {
            case MIN -> now.isAfter(last.plusMinutes(this.delay));
            case HOUR -> now.isAfter(last.plusHours(this.delay));
            case DAY -> now.isAfter(last.plusDays(this.delay));
            case WEEK -> now.isAfter(last.plusWeeks(this.delay));
            case MONTH -> now.isAfter(last.plusMonths(this.delay));
            case YEAR -> now.isAfter(last.plusYears(this.delay));
        };
    }

    public boolean isTimeToStop() {
        if(this.status!=Schedule.Status.RUNNING) {
            return false;
        }
        LocalDateTime last = LocalDateTime.parse(this.updateDttm, formatter);
        LocalDateTime now = LocalDateTime.now();
        return switch (this.durationTime) {
            case MIN -> now.isAfter(last.plusMinutes(this.duration));
            case HOUR -> now.isAfter(last.plusHours(this.duration));
            case DAY -> now.isAfter(last.plusDays(this.duration));
            case WEEK -> now.isAfter(last.plusWeeks(this.duration));
            case MONTH -> now.isAfter(last.plusMonths(this.duration));
            case YEAR -> now.isAfter(last.plusYears(this.duration));
        };
    }
}
