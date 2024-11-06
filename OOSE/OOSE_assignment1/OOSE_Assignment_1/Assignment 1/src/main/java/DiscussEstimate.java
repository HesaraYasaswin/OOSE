package edu.curtin.wbs;

import java.util.*;

// this method allows users to input a revised estimate via the console

public class DiscussEstimate {

    private int numEstimators;

    public DiscussEstimate() {
        this.numEstimators = 3; // Default value
    }

    public int getNumEstimators() {
        return numEstimators;
    }

    public void setNumEstimators(int numEstimators) {
        this.numEstimators = numEstimators;
    }

    public int discussEstimate(List<Integer> estimates) {  // Method to discuss estimate based on the given list of estimates
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter revised estimate: ");
        return scanner.nextInt();
    }
}

