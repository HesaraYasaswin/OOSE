package edu.curtin.wbs;

import java.util.*;

// this method calculates the highest estimate feom the given estimates.

public class HighestEstimate {

    private int numEstimators;

    public HighestEstimate() {
        this.numEstimators = 3; // Default value
    }

    public int getNumEstimators() {
        return numEstimators;
    }

    public void setNumEstimators(int numEstimators) {
        this.numEstimators = numEstimators;
    }

    public int getHighestEstimate(List<Integer> estimates) {
        int max = Integer.MIN_VALUE;  // Initializing a variable max with the minimum possible integer value
        for (int estimate : estimates) { 
            if (estimate > max) {  // Checking if the current estimate is greater than the current max value
                max = estimate;  // Updating the max value if the current estimate is greater
            }
        }
        return max;  // Returning the highest estimate found in the list
    }
}

