package com.ozagrimate.alertservice;

import com.ozagrimate.util.LoadServiceConfiguration;
import com.ozagrimate.util.ServiceConfig;
import com.ozagrimate.util.ServiceNotFoundException;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AlertServiceBusinessLogic {
    // Map<alertId, Alert>
    // Use ConcurrentHashMap to be thread safe
    private Map<Integer, Alert> alerts = new ConcurrentHashMap<>();
    private int alertUpdateIntervalMins; // TODO not currently in scope for this assessment

    private final RestTemplate restTemplate;
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public AlertServiceBusinessLogic  () {
        this.alertUpdateIntervalMins = 30;
        this.restTemplate = new RestTemplate();
    }

    public AlertServiceBusinessLogic (int alertUpdateIntervalMins) {
        this();
        this.alertUpdateIntervalMins = alertUpdateIntervalMins;
    }

    public Map<Integer, Alert> getAllAlerts(Boolean active) {
        // active is option, can be true, false or null
        // if active==null, return all Alerts
        // otherwise only return Alerts with matching active attributes
        if (active==null) {
            return this.alerts;
        }
        Map<Integer, Alert> filtered_alerts = new HashMap<Integer, Alert>();
        for (int alertId : this.alerts.keySet()) {
            if (this.alerts.get(alertId).isActive()==active) {
                filtered_alerts.put(alertId, this.alerts.get(alertId));
            }
        }
        return filtered_alerts;
    }

    public Alert getAlertById(int alertId) throws AlertNotFoundException{
        Alert alert = this.alerts.get(alertId);
        if (alert==null) {
            throw new AlertNotFoundException("alertId=" + alertId);
        }
        return alert;
    }

    public Alert createAlert(AlertCreationRequest alertRequest) {
        Alert alert = new Alert();
        alert.setAlertMessage(alertRequest.getAlertMessage());
        alert.setAlertType(alertRequest.getAlertType());
        alert.setAlertSeverity(alertRequest.getAlertSeverity());
        for (String username : alertRequest.getUsers()) {
            if (this.checkUserExistsAndActive(username)) {
                alert.addUser(username);
            }
            else {
                System.out.println("Username=" + username + " does not exist or is inactive!");
            }
        }
        alert.setSource(alertRequest.getSource());
        alert.setStartDttm(alertRequest.getStartDttm());
        alert.setUpdateDttm(alertRequest.getStartDttm()); // Initial updateDttm is same as startDttm
        this.alerts.put(alert.getAlertId(), alert);
        return alert;
    }

    public Alert updateAlert(int alertId, AlertUpdateRequest alertRequest) throws AlertNotFoundException {
        Alert alert = this.getAlertById(alertId);
        alert.setAlertMessage(alertRequest.getAlertMessage());
        alert.setAlertSeverity(alertRequest.getAlertSeverity());
        alert.setUpdateDttm(LocalDateTime.now().format(this.dateTimeFormatter));
        alert.setActive(alertRequest.isActive());
        if(alertRequest.getUsersToRemove()!=null) {
            for (String username : alertRequest.getUsersToRemove()) {
                alert.removeUser(username);
            }
        }
        if(alertRequest.getUsersToAdd()!=null) {
            for (String username : alertRequest.getUsersToAdd()) {
                if (this.checkUserExistsAndActive(username)) {
                    alert.addUser(username);
                } else {
                    System.out.println("Username=" + username + " does not exist or is inactive!");
                }
            }
        }
        return alert;
    }

    public void deleteAlert(int alertId) throws AlertNotFoundException {
        //check for alert existence or throw AlertNotFoundException
        this.getAlertById(alertId);
        this.alerts.remove(alertId);
    }

    private boolean checkUserExistsAndActive(String username) {
        ServiceConfig userService = null;
        for (ServiceConfig service : LoadServiceConfiguration.loadServiceConfig()) {
            if (service.getServiceName().equals("user-service")) {
                userService = service;
            }
        }
        if (userService==null) {
            throw new ServiceNotFoundException("user-service");
        }
        String userEndpoint = "http://localhost:" + userService.getPort() + "/user-service/user/" + username;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Void> request = new HttpEntity<>(null, headers);
        ResponseEntity<Map> response;
        try {
            response = restTemplate.exchange(userEndpoint, HttpMethod.GET, request, Map.class);
            Map responseBody = response.getBody();
            if (response.getStatusCode().is4xxClientError() || responseBody==null) {
                return false;
            }
            else if (responseBody.get("userActiveSW")==null || responseBody.get("userActiveSW").equals("false")) {
                return false;
            }
        }
        catch (HttpClientErrorException e) {
            return false;
        }
        catch (ResourceAccessException e) {
            System.out.println("WARNING! User Service could not be reached, could not validate username and status");
            return true; // For now, assume that the username can be accepted even if it isn't validated, to maintain service continuity
        }
        return true;
    }

    private void sendAlerts () {
        //TODO implement a separate thread process to iterate
        // Not currently in scope for this assessment
        Map<Integer, Alert> alerts = this.getAllAlerts(true);
        for (int alertId : alerts.keySet()) {
            Alert alert = alerts.get(alertId);
            StringBuilder users = new StringBuilder();
            for (String username : alert.getUsers()) {
                users.append(username);
                users.append(", ");
            }
            if(!users.isEmpty()) {
                users.delete(users.length() - 2, users.length() - 1); // Remove last ', ' from user list
            }
            System.out.println(alert + " has been sent to the following users: " + users);
            alert.setUpdateDttm(LocalDateTime.now().format(this.dateTimeFormatter));
        }
    }
}
