package com.ozagrimate.schedulingservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SchedulingServiceBusinessLogic {

    private Map<Integer, Schedule> schedules = new ConcurrentHashMap<>();

    public SchedulingServiceBusinessLogic() {
        // Create some dummy schedules in varying statuses for testing
        Schedule s1 = new Schedule(); //PENDING (Default) Schedule
        s1.setScheduleAndDuration(Schedule.ScheduleType.ONCE_AFTER, Schedule.ScheduleTime.HOUR, 6L, Schedule.ScheduleTime.MIN, 15L);
        s1.addDeviceId(1);
        s1.addDeviceId(2);
        Schedule s2 = new Schedule(); //RUNNING Schedule
        s2.setScheduleAndDuration(Schedule.ScheduleType.EVERY, Schedule.ScheduleTime.MONTH, 6L, Schedule.ScheduleTime.WEEK, 1L);
        s2.addDeviceId(3);
        s2.setStatus(Schedule.Status.RUNNING);
        Schedule s3 = new Schedule(); //CANCELLED Schedule
        s3.setScheduleAndDuration(Schedule.ScheduleType.EVERY, Schedule.ScheduleTime.MIN, 5L, Schedule.ScheduleTime.MIN, 1L);
        s3.addDeviceId(4);
        s3.setStatus(Schedule.Status.CANCELLED);
        this.schedules.put(s1.getScheduleId(), s1);
        this.schedules.put(s2.getScheduleId(), s2);
        this.schedules.put(s3.getScheduleId(), s3);
    }

    public Map<Integer, Schedule> getAllSchedules(Schedule.Status status) {
        System.out.println(status);;
        if(status==null || this.schedules.isEmpty()) {
            return this.schedules;
        }
        Map<Integer, Schedule> filtered_schedules = new HashMap<>();
        for (Schedule schedule : this.schedules.values()) {
            if (schedule.getStatus()==status) {
                filtered_schedules.put(schedule.getScheduleId(), schedule);
            }
        }
        return filtered_schedules;
    }

    public Schedule getScheduleById(int scheduleId) {
        Schedule schedule = this.schedules.get(scheduleId);
        if(schedule==null) {
            throw new ScheduleNotFoundException("scheduleId=" + scheduleId);
        }
        return schedule;
    }

    public Schedule createSchedule(ScheduleRequest scheduleRequest) throws InvalidScheduleDefinitionException {
        if(!this.checkRequestValidForCreate(scheduleRequest)) {
            throw new InvalidScheduleDefinitionException(scheduleRequest.toString());
        }
        Schedule schedule = new Schedule(scheduleRequest.getDeviceIdsToAdd());
        schedule.setScheduleAndDuration(scheduleRequest.getScheduleType(), scheduleRequest.getScheduleTime(), scheduleRequest.getDelay(), scheduleRequest.getDurationTime(), scheduleRequest.getDuration());
        this.schedules.put(schedule.getScheduleId(), schedule);
        return schedule;
    }

    public Schedule updateSchedule(int scheduleId, ScheduleRequest scheduleRequest) throws InvalidScheduleDefinitionException, ScheduleNotFoundException, ScheduleStatusException{
        if(!this.checkRequestValidForUpdate(scheduleRequest)) {
            throw new InvalidScheduleDefinitionException(scheduleRequest.toString());
        }
        Schedule schedule = this.schedules.get(scheduleId);
        if(schedule==null) {
            throw new ScheduleNotFoundException("scheduleId=" + scheduleId);
        }
        if(schedule.getStatus()==Schedule.Status.RUNNING) {
            throw new ScheduleStatusException("scheduleId:" + scheduleId + " is currently running and cannot be modified");
        }
        for(int deviceId : scheduleRequest.getDeviceIdsToRemove()) {
            schedule.removeDeviceId(deviceId);
        }
        for(int deviceId : scheduleRequest.getDeviceIdsToAdd()) {
            try {
                schedule.addDeviceId(deviceId);
            }
            catch (InvalidScheduleDefinitionException e) {} // skip deviceId if exists so PUT request is idempotent
        }
        Schedule.ScheduleType newScheduleType = schedule.getScheduleType();
        Schedule.ScheduleTime newScheduleTime = schedule.getScheduleTime();
        long newDelay = schedule.getDelay();
        Schedule.ScheduleTime newDurationTime = schedule.getDurationTime();
        long newDuration = schedule.getDuration();
        if(scheduleRequest.getScheduleType()!=null) {
            newScheduleType = scheduleRequest.getScheduleType();
        }
        if(scheduleRequest.getScheduleTime()!=null) {
            newScheduleTime = scheduleRequest.getScheduleTime();
        }
        if(scheduleRequest.getDelay()!=null) {
            newDelay = scheduleRequest.getDelay();
        }
        if(scheduleRequest.getDurationTime()!=null) {
            newDurationTime = scheduleRequest.getDurationTime();
        }
        if(scheduleRequest.getDuration()!=null) {
            newDuration = scheduleRequest.getDuration();
        }
        schedule.setScheduleAndDuration(newScheduleType, newScheduleTime, newDelay, newDurationTime, newDuration);
        return schedule;
    }

    public void deleteSchedule(int scheduleId) {
        this.schedules.remove(scheduleId);
    }

    private boolean checkRequestValidForCreate(ScheduleRequest scheduleRequest) {
        if (scheduleRequest.getDeviceIdsToAdd()==null || scheduleRequest.getDeviceIdsToAdd().isEmpty()) {
            return false;
        }
        if (scheduleRequest.getDeviceIdsToRemove()!=null) {
            return false;
        }
        if (scheduleRequest.getScheduleType()==null || scheduleRequest.getScheduleTime()==null || scheduleRequest.getDelay()==null || scheduleRequest.getDelay().longValue()<0) {
            return false;
        }
        if (scheduleRequest.getDurationTime()==null || scheduleRequest.getDuration()==null || scheduleRequest.getDuration().longValue()<0) {
            return false;
        }
        return true;
    }

    private boolean checkRequestValidForUpdate(ScheduleRequest scheduleRequest) {
        if(scheduleRequest.getDelay()!=null && scheduleRequest.getDelay().longValue()<0) {
            return false;
        }
        if(scheduleRequest.getDuration()!=null && scheduleRequest.getDuration().longValue()<0) {
            return false;
        }
        return true;
    }


}
