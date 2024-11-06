package edu.curtin.wbs;

import java.util.*;
import java.util.Scanner;

// The aim of this code is to use several methods defined in a Configure settings method to estimate the work for tasks in a Work Breakdown Structure (WBS). The code helps the user's ability to enter estimates of effort for activities and subsequently reconcile them according to the selected method (highest estimate, median estimate, or discussion among estimators).

public class EstimateEffort {

    // Private instance variables 
    private WBS wbs;
    private Configure configure;

    // Constructor 
    public EstimateEffort(WBS wbs, Configure configure) {
        this.wbs = wbs;
        this.configure = configure;
    }

    // to start the estimation process
    public void estimateEffort() 
    {
        Scanner scanner = new Scanner(System.in); 
        System.out.print("Enter task ID: ");
        String taskId = scanner.nextLine();
        Task task = wbs.getTasks().get(taskId);  // Getting the task object corresponding to the entered task ID
        if (task == null) {  // Checking if the task exists
            System.out.println("Task not found.");
            return;
        }
        
        // Estimating effort for the given task and displaying all estimates
        estimateEffortForTask(task);
        displayAllEstimates();
    }

    // to estimate effort for a specific task
    private void estimateEffortForTask(Task task) 
    {
        // Checking if the task has subtasks
        if (task.hasSubtasks()) {
            for (Task subtask : task.getSubtasks()) { // recursively estimating effort for each subtask
                estimateEffortForTask(subtask);
            }
        } else {
            List<Integer> estimates = new ArrayList<>(); // if the task does not have subtasks, prompt the user for effort estimates

            Scanner scanner = new Scanner(System.in);
            for (int i = 1; i <= configure.getNumEstimators(); i++) {
             while (true) {
                try {
                    System.out.print("Enter estimate " + i + " for task " + task.getId() + ": ");
                    int estimate = scanner.nextInt();
                    estimates.add(estimate);
                    break; 
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a valid integer value.");
                    scanner.nextLine(); // Consume newline and clear the buffer
                }
             }
            }
            // Displaying the effort estimates for the task
            System.out.println("Effort estimates for task " + task.getId() + ": " + estimates);
            // Reconciling the estimates using the selected approach and setting the final estimate for the task
            int finalEstimate = reconcileEstimates(estimates);
            task.setEffortEstimate(finalEstimate);
            // Displaying the final effort estimate for the task
            System.out.println("Final effort estimate for task " + task.getId() + ": " + finalEstimate);
        }
    }

    // to reconcile effort estimates based on the chosen approach
    private int reconcileEstimates(List<Integer> estimates) 
    {
        switch (configure.getChoice()) {
            case 1:
                return configure.getHighestEstimate().getHighestEstimate(estimates);
            case 2:
                return configure.getMedianEstimate().getMedianEstimate(estimates);
            case 3:
            default:
                return configure.getDiscussEstimate().discussEstimate(estimates);
        }
    }

    // to display all task effort estimates
    private void displayAllEstimates() {
        System.out.println("All task effort estimates:");
        for (Task task : wbs.getTasks().values()) {
            System.out.println("Task " + task.getId() + ": " + task.getEffortEstimate());
        }
    }
}





