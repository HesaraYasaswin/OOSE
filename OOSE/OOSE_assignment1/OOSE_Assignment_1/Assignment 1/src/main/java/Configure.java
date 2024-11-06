package edu.curtin.wbs;

import java.util.*;
import java.util.Scanner;

// This code aims to construct a class called Configure that lets users set up settings for effort estimation. It contains procedures for selecting and retrieving the number of estimators, selecting a reconciliation strategy, and reconciling estimates using the selected strategy.

public class Configure {

    private static int choice;
    private HighestEstimate highestEstimate;
    private MedianEstimate medianEstimate;
    private DiscussEstimate discussEstimate;
    private Scanner scanner;

    public Configure() { 
        this.highestEstimate = new HighestEstimate();
        this.medianEstimate = new MedianEstimate();
        this.discussEstimate = new DiscussEstimate();
        this.scanner = new Scanner(System.in);
    }

    public int getNumEstimators() {
        return highestEstimate.getNumEstimators();
    }

    public void setChoice(int choice) {
        Configure.choice = choice;
    }

    public int getChoice() {
        return choice;
    }

    public HighestEstimate getHighestEstimate() {
        return highestEstimate;
    }

    public MedianEstimate getMedianEstimate() {
        return medianEstimate;
    }

    public DiscussEstimate getDiscussEstimate() {
        return discussEstimate;
    }
    
   

    public void configure() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter new value of N (the number of estimators you want): ");
        highestEstimate.setNumEstimators(scanner.nextInt()); // Setting the number of estimators that was chosen
        scanner.nextLine(); 
        System.out.println("Choose the reconciliation approach:");
        System.out.println("1. Take the highest estimate");
        System.out.println("2. Take the median estimate");
        System.out.println("3. Discuss among estimators");
        while (true) {
         System.out.print("Enter your choice: ");
         if (scanner.hasNextInt()) {
            choice = scanner.nextInt(); // Read the user's choice
            if (choice >= 1 && choice <= 3) {
                break; // Exit the loop if a valid choice is entered
            } else {
                System.out.println("Invalid choice. Please enter a number between 1 and 3.");
            }
         } else {
            System.out.println("Invalid input. Please enter a valid integer value.");
            scanner.nextLine(); // Consume newline and clear the buffer
         }
    }
        System.out.println("Final effort estimate: " + reconcileEstimates()); // Displaying the final effort estimate.
    }

    public int reconcileEstimates() 
    {
        List<Integer> estimates = new ArrayList<>();  // Creating a new ArrayList to store the estimates.
        for (int i = 1; i <= highestEstimate.getNumEstimators(); i++) {  // Looping through each estimator(here the highestestimate mean the number of estimates chosen).
          while (true) {
            try {
                System.out.print("Enter estimate " + i + ": ");  // Prompting the user to enter the estimate.
                int estimate = scanner.nextInt();  // Reading the estimate from the user.
                estimates.add(estimate);  // Adding the estimate to the list of estimates.
                break; 
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer value.");
                scanner.nextLine(); // Consume newline and clear the buffer
            }
          }
        }
        switch (choice) {  // switch case for the selected estimate approach
            case 1:
                return highestEstimate.getHighestEstimate(estimates);
            case 2:
                return medianEstimate.getMedianEstimate(estimates);
            case 3:
            default:
                return discussEstimate.discussEstimate(estimates);
        }
    }
}


