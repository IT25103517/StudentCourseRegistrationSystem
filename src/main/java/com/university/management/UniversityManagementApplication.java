package com.university.management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * =====================================================
 * MAIN APPLICATION CLASS
 * =====================================================
 * This is the ENTRY POINT of our Spring Boot application.
 * When you click "Run", Java starts here.
 *
 * We extend SpringBootServletInitializer because we are using
 * WAR packaging with JSP pages. This allows the app to run
 * both as embedded Tomcat AND as a standalone WAR file.
 * =====================================================
 */
@SpringBootApplication  // Tells Spring Boot: "Scan everything in this package"
public class UniversityManagementApplication extends SpringBootServletInitializer {

    /**
     * Required when using WAR packaging with JSP.
     * This method tells Spring how to start the app when deployed to an external server.
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(UniversityManagementApplication.class);
    }

    /**
     * The main method - this is where Java starts execution.
     * SpringApplication.run() boots up the entire Spring context.
     */
    public static void main(String[] args) {
        SpringApplication.run(UniversityManagementApplication.class, args);
        System.out.println("==============================================");
        System.out.println("  University Management System is Running!   ");
        System.out.println("  Open: http://localhost:8080/university      ");
        System.out.println("==============================================");
    }
}