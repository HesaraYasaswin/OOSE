package edu.curtin.wbs;

import java.io.*;
import java.util.*;

// This code represents a Work Breakdown Structure (WBS) management system. It allows users to load tasks from a file, display the WBS structure and the menu for estimate effort and configure estimators,and save tasks to a file.

public class WBS {
    private Map<String, Task> tasks; // Store tasks in a map
    private String fileName;
    private int numEstimators = 3; // Default number of estimators

    // Constructor
    public WBS(String fileName) {
        tasks = new HashMap<>(); // Initialize tasks map
        this.fileName = fileName;
    }

    // Getter methods
    public Map<String, Task> getTasks() {
        return tasks;
    }

    public int getNumEstimators() {
        return numEstimators;
    }

    // Setter method
    public void setNumEstimators(int numEstimators) {
        this.numEstimators = numEstimators;
    }

    // Load tasks from file
    public void loadFromFile() throws IOException {
      try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
        String line;
        while ((line = reader.readLine()) != null) {
            // Split each line into parts
            String[] parts = line.split(";"); 
            if (parts.length >= 2) { // Ensure at least two parts exist
                String parentId = parts[0].trim().isEmpty() ? null : parts[0].trim();
                String taskId = parts[1].trim();
                String description = parts[2].trim();
                Integer effortEstimate = (parts.length == 4 && !parts[3].trim().isEmpty()) ? Integer.parseInt(parts[3].trim()) : null;
                Task task = new Task(parentId, taskId, description, effortEstimate);
                tasks.put(taskId, task); // Add task to map
            } else {
                System.out.println("Invalid line format: " + line); // Notify if line format is invalid
            }
        }
      } 
    }
    
    
    
    
    // Save tasks to file
    public void saveToFile() throws IOException {
      try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
        for (Task task : tasks.values()) {
            writer.write(task.toString()); // Write task information to file
            writer.newLine(); // Write newline character
        }
      } // BufferedWriter will be automatically closed here
    }

    

    // Display Work Breakdown Structure
    private void displayWBS() 
    {
     System.out.println("Work Breakdown Structure:");
     Set<String> visited = new HashSet<>(); // Keep track of visited tasks
     for (Task task : tasks.values()) {
        if (!visited.contains(task.getId())) {
            displayTask(task, 0, visited); // Display each task recursively
        }
     }
    }
    
    // Recursively display task information with proper indentation
    private void displayTask(Task task, int level, Set<String> visited) {
     StringBuilder indent = new StringBuilder();
     for (int i = 0; i < level; i++) {
        indent.append("   "); // Add indentation
     }
     String effortString = task.getEffortEstimate() != null ? ", effort = " + task.getEffortEstimate() : "";
     String description = task.getDescription().replace(";", ""); // Remove semicolon
     String taskId = task.getId() != null ? task.getId() + ": " : ""; // Add task ID if it exists
     System.out.println(indent + taskId + description + effortString);
     visited.add(task.getId()); // Mark task as visited
     for (Task subtask : tasks.values()) {
        if (Objects.equals(subtask.getParentId(), task.getId())) {
            displayTask(subtask, level + 1, visited); // Display subtasks recursively
        }
     }
    }
    
    
    // Check if a task has subtasks
    private boolean hasSubtasks(String parentId) {
        for (Task task : tasks.values()) {
            if (Objects.equals(task.getParentId(), parentId)) {
                return true; // Return true if subtask found
            }
        }
        return false; 
    }
    
    // Display menu options
    public void showMenu() 
    {
        displayWBS();
        displayEffortSummary(); 
        System.out.println("Menu:");
        System.out.println("1. Estimate effort");
        System.out.println("2. Configure");
        System.out.println("3. Quit");
        System.out.print("Select an option: ");
    }

    

    // Display effort summary
    private void displayEffortSummary() {
        int totalKnownEffort = 0;
        int unknownTasksCount = 0;
        for (Task task : tasks.values()) {
            if (task.getEffortEstimate() != null) {
                totalKnownEffort += task.getEffortEstimate(); // Accumulate known effort
            } else if (!hasSubtasks(task.getId())) {
                unknownTasksCount++; // Count tasks with unknown effort and no subtasks
            }
        }
        System.out.println("Total known effort = " + totalKnownEffort);
        System.out.println("Unknown tasks = " + unknownTasksCount);
    }

   
    
}



