package com.ozagrimate.ozagrimate;

import org.springframework.boot.builder.SpringApplicationBuilder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//Custom Imports
import com.ozagrimate.deviceservice.DeviceServiceApplication;
import com.ozagrimate.userservice.UserServiceApplication;

/**
 * This Program intialises each of the micro-services in this project on specified Ports.
 * Ports and Service Names are configured here, if running individual services these will all default to Port 8080
 */
public class OzAgriMateApplication {
    private static final int NUMBEROFSERVICES = 2; // Increment based on Number of Microservices starting
    // com.ozagrimate.deviceservice port and name settings
    private static final String DEVICESERVICE_PORT = "8080";
    private static final String DEVICESERVICE_NAME = "device-service";

    // com.ozagrimate.usesservice port and name settings
    private static final String USERSERVICE_PORT = "8081";
    private static final String USERSERVICE_NAME = "user-service";


    public static void main(String[] args) {
        System.out.println("Starting with " + NUMBEROFSERVICES + " services...");
        ExecutorService executor = Executors.newFixedThreadPool(NUMBEROFSERVICES);

        // Start Device Service
        startService(executor, DeviceServiceApplication.class, DEVICESERVICE_NAME, DEVICESERVICE_PORT, args);
        // Start User Service
        startService(executor, UserServiceApplication.class, USERSERVICE_NAME, USERSERVICE_PORT, args);

        // Add Shutdown Hook for Cleanup
        Runtime.getRuntime().addShutdownHook(new Thread(executor::shutdown));

    }

    /**
     * Initialises a given microservice based on the class name of the SpringBoot application
     * @param executor ExecutorService instance to attach threads to
     * @param program Class of the Springboot application to start
     * @param serviceName Name of Service for logging
     * @param port Port this service will listen to
     * @param args Program args
     */
    private static void startService (ExecutorService executor, Class<?> program, String serviceName, String port, String[] args) {
        System.out.println("Starting " + serviceName + " service on Port " + port);
        executor.submit(() -> {
            new SpringApplicationBuilder(program)
                    .properties("server.port=" + port, "spring.application.name=" + serviceName)
                    .run(args);
        });
        System.out.println("Service " + serviceName + " is listening on Port " + port);
    }

}
