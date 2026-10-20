package com.ozagrimate.ozagrimate;

import com.ozagrimate.util.LoadServiceConfiguration;
import com.ozagrimate.util.ServiceConfig;
import org.springframework.boot.builder.SpringApplicationBuilder;

import com.ozagrimate.userservice.UserServiceApplication;
import com.ozagrimate.deviceservice.DeviceServiceApplication;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//Custom Imports

/**
 * This Program initialises each of the microservices in this project on specified Ports.
 * Ports and Service Names are configured here, if running individual services these will all default to Port 8080
 */
public class OzAgriMateApplication {

    // Taken from https://jenkov.com/tutorials/java-concurrency/synchronized.html
    private static final Object startLock = new Object();

    public static void main(String[] args) {

        ServiceConfig[] serviceConfig = LoadServiceConfiguration.loadServiceConfig();
        System.out.println("Starting with " + serviceConfig.length + " services...");
        ExecutorService executor = Executors.newFixedThreadPool(serviceConfig.length);

        for (ServiceConfig service : serviceConfig) {
            System.out.println(service);
            startService(executor, service.getProgram(), service.getServiceName(), service.getPort(), args);
        }

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
            // Solution to thread race condition from https://jenkov.com/tutorials/java-concurrency/synchronized.html
            synchronized (startLock) {
                new SpringApplicationBuilder(program)
                        .properties("server.port=" + port, "spring.application.name=" + serviceName)
                        .run(args);
            }
        });
        System.out.println("Service " + serviceName + " is listening on Port " + port);
    }
}
