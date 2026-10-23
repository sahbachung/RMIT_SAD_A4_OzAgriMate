package com.ozagrimate.reportingservice;

public class ReportRequest {
    private String reportName;
    private String targetEndpoint;
    private Report.ScheduleTime scheduleTime;
    private Report.ScheduleType scheduleType;
    private Long delay;

    public String getReportName() {
        return reportName;
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

    public Report.ScheduleTime getScheduleTime() {
        return scheduleTime;
    }

    public void setScheduleTime(Report.ScheduleTime newScheduleTime) {
        this.scheduleTime = newScheduleTime;
    }

    public Report.ScheduleType getScheduleType() {
        return scheduleType;
    }

    public void setScheduleType(Report.ScheduleType scheduleType) {
        this.scheduleType = scheduleType;
    }

    public Long getDelay() {
        return delay;
    }

    public void setDelay(Long delay) {
        this.delay = delay;
    }

    @Override
    public String toString() {
        return "ReportRequest:{" +
                "reportName:" + this.reportName +
                ", targetEndpoint:" + this.targetEndpoint +
                ", scheduleType:" + this.scheduleType +
                ", scheduleTime:" + this.scheduleTime +
                ", delay:" + this.delay +
                "}";
    }
}
