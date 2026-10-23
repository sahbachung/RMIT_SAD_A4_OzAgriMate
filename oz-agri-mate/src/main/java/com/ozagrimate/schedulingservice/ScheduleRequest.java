package com.ozagrimate.schedulingservice;

import java.util.ArrayList;

public class ScheduleRequest {
    private ArrayList<Integer> deviceIdsToAdd;
    private ArrayList<Integer> deviceIdsToRemove;
    private Schedule.ScheduleType scheduleType;
    private Schedule.ScheduleTime scheduleTime;
    private Long delay;
    private Schedule.ScheduleTime durationTime;
    private Long duration;

    public ArrayList<Integer> getDeviceIdsToAdd() {
        return this.deviceIdsToAdd;
    }

    public void setDeviceIdsToAdd(ArrayList<Integer> deviceIdsToAdd) {
        this.deviceIdsToAdd = deviceIdsToAdd;
    }

    public ArrayList<Integer> getDeviceIdsToRemove() {
        return this.deviceIdsToRemove;
    }

    public void setDeviceIdsToRemove(ArrayList<Integer> deviceIdsToRemove) {
        this.deviceIdsToRemove = deviceIdsToRemove;
    }

    public Schedule.ScheduleType getScheduleType() {
        return this.scheduleType;
    }

    public void setScheduleType(Schedule.ScheduleType scheduleType) {
        this.scheduleType = scheduleType;
    }

    public Schedule.ScheduleTime getScheduleTime() {
        return this.scheduleTime;
    }

    public void setScheduleTime(Schedule.ScheduleTime scheduleTime) {
        this.scheduleTime = scheduleTime;
    }

    public Long getDelay() {
        return this.delay;
    }

    public void setDelay(Long delay) {
        this.delay = delay;
    }

    public Schedule.ScheduleTime getDurationTime() {
        return this.durationTime;
    }

    public void setDurationTime(Schedule.ScheduleTime durationTime) {
        this.durationTime = durationTime;
    }

    public Long getDuration() {
        return this.duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "ScheduleRequest:{" +
                "deviceIdsToAdd:" + this.deviceIdsToAdd+
                ", deviceIdsToRemove:" + this.deviceIdsToRemove +
                ", scheduleType:" + this.scheduleType +
                ", scheduleTime:" + this.scheduleTime +
                ", delay:" + this.delay +
                ", durationTime:" + this.durationTime +
                ", duration:" + this.duration +
                "}";
    }
}
