package com.ozagrimate.reportingservice;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ReportingServiceBusinessLogic {

    private Map<Integer, Report> reports = new ConcurrentHashMap<>();

    public ReportingServiceBusinessLogic() {
        // Create some dummy reports
        Report r1 = new Report("Device Maintenance Daily");
        r1.setSchedule(Report.ScheduleType.EVERY, Report.ScheduleTime.DAY, 1L);
        r1.setTargetEndpoint("/device-service/maintenance");

        Report r2 = new Report("Device Monthly");
        r2.setSchedule(Report.ScheduleType.EVERY, Report.ScheduleTime.MONTH, 1L);
        r2.setTargetEndpoint("/device-service/device");

        this.reports.put(r1.getReportId(), r1);
        this.reports.put(r2.getReportId(), r2);
    }

    public Map<Integer, Report> getAllReports(Report.Status status) {
        if(this.reports==null || status==null) {
            return this.reports;
        }
        Map<Integer, Report> filtered_reports = new HashMap<>();
        for (Report report : this.reports.values()) {
            if(report.getStatus()==status) {
                filtered_reports.put(report.getReportId(), report);
            }
        }
        return filtered_reports;
    }

    public Report getReportById(int reportId) throws ReportNotFoundException{
        Report report = this.reports.get(reportId);
        if(report==null) {
            throw new ReportNotFoundException("reportId=" + reportId + " not found");
        }
        return report;
    }

    public Report createReport(ReportRequest reportRequest) throws NullPointerException {
        if (
                reportRequest.getReportName()==null ||
                reportRequest.getScheduleType()==null ||
                reportRequest.getScheduleTime()==null ||
                reportRequest.getDelay()==null ||
                reportRequest.getTargetEndpoint()==null
        ) {
            throw new NullPointerException(reportRequest.toString());
        }
        Report report = new Report(reportRequest.getReportName());
        report.setSchedule(reportRequest.getScheduleType(), reportRequest.getScheduleTime(), reportRequest.getDelay());
        report.setTargetEndpoint(reportRequest.getTargetEndpoint());
        this.reports.put(report.getReportId(), report);
        return report;
    }

    public Report updateReport(int reportId, ReportRequest reportRequest) throws ReportNotFoundException {
        Report report = this.getReportById(reportId);
        if(reportRequest.getReportName()!= null) {
            report.setReportName(reportRequest.getReportName());
        }
        if(reportRequest.getTargetEndpoint()!=null) {
            report.setTargetEndpoint(reportRequest.getTargetEndpoint());
        }
        Report.ScheduleType newScheduleType = report.getScheduleType();
        Report.ScheduleTime newScheduleTime = report.getScheduleTime();
        long newDelay = report.getDelay();
        if(reportRequest.getScheduleType()!=null) {
            newScheduleType = reportRequest.getScheduleType();
        }
        if(reportRequest.getScheduleTime()!=null) {
            newScheduleTime = reportRequest.getScheduleTime();
        }
        if(reportRequest.getDelay()!=null) {
            newDelay = reportRequest.getDelay();
        }
        report.setSchedule(newScheduleType, newScheduleTime, newDelay);
        return report;
    }

    public void deleteReport(int reportId) {
        this.reports.remove(reportId);
    }

}
