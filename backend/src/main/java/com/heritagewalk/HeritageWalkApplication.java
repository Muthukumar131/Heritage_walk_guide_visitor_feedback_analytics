package com.heritagewalk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HeritageWalkApplication {

    public static void main(String[] args) {
        SpringApplication.run(HeritageWalkApplication.class, args);
        System.out.println("==================================================================");
        System.out.println("  Heritage Walk Guide Backend is RUNNING on http://localhost:8080");
        System.out.println("  API Base URL: http://localhost:8080/api");
        System.out.println("==================================================================");
    }
}
