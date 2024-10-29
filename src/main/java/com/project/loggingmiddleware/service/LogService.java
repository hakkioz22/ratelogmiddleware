package com.project.loggingmiddleware.service;

import org.springframework.stereotype.Service;
import java.io.FileWriter;
import java.io.IOException;

@Service
public class LogService {

    private static final String LOG_FILE_PATH = "application.log";

    // Log to console
    public void logToConsole(String message) {
        System.out.println(message);
    }

    // Log to file
    public void logToFile(String message) {
        try (FileWriter writer = new FileWriter(LOG_FILE_PATH, true)) {
            writer.write(message + "\n");
        } catch (IOException e) {
            System.err.println("Error writing log to file: " + e.getMessage());
        }
    }

    // Log to database (placeholder)
    public void logToDatabase(String message) {
        // Database logging logic
        System.out.println("Log to database: " + message);
    }

    // Generalized log method
    public void log(String message, String destination) {
        switch (destination.toLowerCase()) {
            case "console":
                logToConsole(message);
                break;
            case "file":
                logToFile(message);
                break;
            case "database":
                logToDatabase(message);
                break;
            default:
                logToConsole("Invalid log destination: " + destination);
        }
    }
}
