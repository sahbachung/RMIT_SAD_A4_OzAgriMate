package com.ozagrimate.ozagrimate;

import org.springframework.boot.builder.SpringApplicationBuilder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//Custom Imports
import com.ozagrimate.deviceservice.DeviceServiceApplication;

public class OzAgriMateApplication {
    private static final int NUMBEROFSERVICES = 1;
    private static final String DEVICESERVICE_PORT = "8080";
    private static final String DEVICESERVICE_NAME = "deviceservice";


    public static void main(String[] args) {
        System.out.println("Starting with " + NUMBEROFSERVICES + " services...");
        ExecutorService executor = Executors.newFixedThreadPool(NUMBEROFSERVICES);

        // Start Device Service
        System.out.println("Starting " + DEVICESERVICE_NAME + " service on port " + DEVICESERVICE_PORT);
        executor.submit(() -> {
            new SpringApplicationBuilder(DeviceServiceApplication.class)
                    .properties("server.port=" + DEVICESERVICE_PORT, "spring.application.name=" + DEVICESERVICE_NAME)
                    .run(args);
        });
        System.out.println("Service " + DEVICESERVICE_NAME + " listening on port " + DEVICESERVICE_PORT);

        Runtime.getRuntime().addShutdownHook(new Thread(executor::shutdown));

    }

}
