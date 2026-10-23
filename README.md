# About

OzAgriMate is a digital service for Australian Farmers to help manage their farms by integrating multiple IoT devices, external weather and mapping data, and to manage inventory stock.

*This Project was created for RMIT Solution Architecture & Design, Assessment 4 (Semester 2 2026).*

# Components / Usage

## OzAgriMate Application
Program Name: `com.ozagrimate.ozagrimate.OzAgriMateApplication.java`

Run this program to initialise all microservices on their configured ports.
Configuration is located in `/resources/serviceConfig.json`

See `OzAgriMate.postman_collection.json` for sample API Usage for each service.
#### Default Settings provided:
```
[
  {
    "serviceName" : "device-service",
    "port" : "8080",
    "program" : "com.ozagrimate.deviceservice.DeviceServiceApplication"
  },
  {
    "serviceName" : "user-service",
    "port" : "8081",
    "program" : "com.ozagrimate.userservice.UserServiceApplication"
  },
  {
    "serviceName" : "inventory-service",
    "port" : "8082",
    "program" : "com.ozagrimate.inventoryservice.InventoryServiceApplication"
  },
  {
    "serviceName" : "alert-service",
    "port" : "8083",
    "program" : "com.ozagrimate.alertservice.AlertServiceApplication"
  },
  {
    "serviceName" : "reporting-service",
    "port" : "8084",
    "program" : "com.ozagrimate.reportingservice.ReportingServiceApplication"
  },
  {
    "serviceName" : "scheduling-service",
    "port" : "8085",
    "program" : "com.ozagrimate.schedulingservice.SchedulingServiceApplication"
  }
]
```

## Device Service
Program Name: `com.ozagrimate.deviceservice.DeviceServiceApplication.java`

Run this program to start the device-service on the default port (8080)

#### Endpoints:
`/device-service/device`

`/device-service/maintenance`

## User Service
Program Name: `com.ozagrimate.userservice.UserServiceApplication.java`

Run this program to start the user-service on the default port (8080)

#### Endpoints:
`/user-service/user`

## Inventory Service
Program Name: `com.ozagrimate.inventoryservice.InventoryServiceApplication.java`

Run this program to start the inventory-service on the default port (8080)

#### Endpoints:
`/inventory-service/inventory`

`/inventory-service/order`

## Alert Service
Program Name: `com.ozagrimate.alertservice.AlertServiceApplication.java`

Run this program to start the alert-service on the default port (8080)

#### Endpoints:
`/alert-service/alerts`

## Reporting Service
Program Name: `com.ozagrimate.reportingservice.ReportingServiceApplication.java`

Run this program to start the reporting-service on the default port (8080)

#### Endpoints:
`/reporting-service/reports`

## Scheduling Service
Program Name: `com.ozagrimate.schedulingservice.SchedulingServiceApplication.java`

Run this program to start the scheduling-service on the default port (8080)

#### Endpoints:
`/scheduling-service/schedules`