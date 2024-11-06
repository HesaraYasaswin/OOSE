package edu.curtin.wbs;

import java.io.*;
import java.util.*;
import java.util.logging.Logger;
import java.util.logging.Level;

// This Java program serves as the entry point for managing a Work Breakdown Structure (WBS) system. It provides a command-line interface for users to interact with tasks stored in the WBS. The program initializes necessary components such as the WBS instance, handles user input through a menu system.

public class Main {
    // Define a logger instance for logging purposes
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) 
    {
        
        // Check if the correct number of command-line arguments is provided
        if (args.length != 1) {
            System.out.println("Usage: java Main <filename>");  
            System.exit(1);  // Exit the program with status 1 
        }

        // Extract filename from command-line arguments
        String filename = args[0];
        WBS wbs = new WBS(filename);  // Create a new instance of WBS using the provided filename

        try {
            // Load tasks from file
            wbs.loadFromFile();

            // User interaction loop
            boolean quit = false;
            Scanner scanner = new Scanner(System.in);
            
            // Create instances of EstimateEffort and Configure classes
            EstimateEffort estimateEffort = new EstimateEffort(wbs, new Configure());
            Configure configure = new Configure(); // Instantiate Configure

            while (!quit) 
            {
                wbs.showMenu();  // Display menu and get user choice
                try {
                    int choice = scanner.nextInt();  // Read user input for choice
                    scanner.nextLine(); 

                    // Process user choice
                    switch (choice) {
                        case 1:
                            estimateEffort.estimateEffort();  // Call estimateEffort method from EstimateEffort
                            break;
                        case 2:
                            configure.configure();  // Call configure method from Configure
                            break;
                        case 3:
                            wbs.saveToFile();   // Save tasks to file and exit
                            quit = true;
                            break;
                        default:
                            System.out.println("Invalid choice.");  // Invalid input
                            break;
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a valid integer choice.");  // exception
                    scanner.nextLine(); 
                }
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "An IO exception occurred", e); // Handle IO exception with logger
        }
    }
}



