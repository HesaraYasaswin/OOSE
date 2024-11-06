package edu.curtin.wbs;

import java.util.*;
// This code is responsible for calculating the median estimate from a list of estimates

public class MedianEstimate {

    private int numEstimators;

    public MedianEstimate() {
        this.numEstimators = 3; // Default value
    }

    public int getNumEstimators() {
        return numEstimators;
    }

    public void setNumEstimators(int numEstimators) {
        this.numEstimators = numEstimators;
    }

    public int getMedianEstimate(List<Integer> estimates) {
        Collections.sort(estimates); // Sorting the list of estimates in ascending order
        return estimates.get(estimates.size() / 2); // Returning the estimate at the middle index (median)
    }
}

