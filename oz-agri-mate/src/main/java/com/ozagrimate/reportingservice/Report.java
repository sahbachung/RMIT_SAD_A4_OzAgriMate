package com.ozagrimate.reportingservice;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicInteger;

public class Report {

    public enum ScheduleType {
        ONCE_AFTER, EVERY;
    }
    public enum ScheduleTime {
        MIN, HOUR, DAY, WEEK, MONTH, YEAR;
    }
    public enum Status {
        PENDING, RUNNING, COMPLETE, CANCELLED;
        public Status next() throws NoSuchElementException {
            return switch (this) {
                case Status.PENDING -> Status.RUNNING;
                case Status.RUNNING -> Status.COMPLETE;
                case Status.COMPLETE, Status.CANCELLED -> Status.PENDING;
            };
        }
    }
    private static final AtomicInteger idCounter = new AtomicInteger(0); // Thread Safe ID Counter
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final int reportId;
    private Status status;
    private String reportName;
    private String targetEndpoint;
    private final String createDttm;
    private String lastRunDttm;
    private long delay;
    private ScheduleTime scheduleTime;
    private ScheduleType scheduleType;

    public Report(String reportName) {
        this.reportId = idCounter.incrementAndGet();
        this.status = Status.PENDING;
        this.reportName = reportName;
        this.createDttm = LocalDateTime.now().format(formatter);
    }

    public int getReportId() { return this.reportId; }

    public Status getStatus() { return this.status; }


    public void setStatus(Report.Status status) {
        if (status==Report.Status.COMPLETE) {
            throw new ReportStatusException("reportId=" + this.reportId + "cannot be set to COMPLETE manually");
        }
        this.status = status;
    }

    public void setSchedule(ScheduleType scheduleType, ScheduleTime scheduleTime, long delay) {
        this.scheduleType = scheduleType;
        this.scheduleTime = scheduleTime;
        this.delay = delay;
    }

    public ScheduleType getScheduleType() { return this.scheduleType; }

    public ScheduleTime getScheduleTime() { return this.scheduleTime; }

    public long getDelay() { return this.delay; }

    public String getReportName() {
        return this.reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public String getTargetEndpoint() {
        return targetEndpoint;
    }

    public void setTargetEndpoint(String targetEndpoint) {
        this.targetEndpoint = targetEndpoint;
    }

    public String getLastRunDttm() { return this.lastRunDttm; }

    public void setLastRunDttm(String lastRunDttm) {
        this.lastRunDttm = lastRunDttm;
    }


    public boolean isTimeToRun() {
        if (this.status != Report.Status.PENDING) {
            return false;
        }
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime last;
        if (this.lastRunDttm == null) {
            last = LocalDateTime.parse(this.createDttm, formatter);
        }
        else {
            last = LocalDateTime.parse(this.lastRunDttm, formatter);
        }
        return switch (this.scheduleTime) {
            case MIN -> now.isAfter(last.plusMinutes(this.delay));
            case HOUR -> now.isAfter(last.plusHours(this.delay));
            case DAY -> now.isAfter(last.plusDays(this.delay));
            case WEEK -> now.isAfter(last.plusWeeks(this.delay));
            case MONTH -> now.isAfter(last.plusMonths(this.delay));
            case YEAR -> now.isAfter(last.plusYears(this.delay));
        };
    }

}
