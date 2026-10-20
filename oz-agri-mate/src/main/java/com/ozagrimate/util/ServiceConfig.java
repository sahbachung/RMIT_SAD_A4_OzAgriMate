package com.ozagrimate.util;

public class ServiceConfig {
    private String serviceName;
    private String port;
    private Class<?> program;

    public ServiceConfig(String serviceName, String port, Class<?> program) {
        this.serviceName = serviceName;
        this.port = port;
        this.program = program;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getPort() {
        return port;
    }

    public Class<?> getProgram() {
        return program;
    }

    @Override
    public String toString() {
        return "Service: {" +
                "serviceName: " + this.serviceName +
                ", port: " + this.port +
                ", program: " + this.program.toString() +
                "}";
    }
}
