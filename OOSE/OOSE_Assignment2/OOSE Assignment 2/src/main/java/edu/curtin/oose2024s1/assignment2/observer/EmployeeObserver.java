package edu.curtin.oose2024s1.assignment2.observer;

import edu.curtin.oose2024s1.assignment2.shop.BikeShop;

public class EmployeeObserver implements SimulationObserver {

    private static final int PAY_INTERVAL = 7; // Simulated days

    @Override
    public void update(BikeShop bikeShop, int daysElapsed) {
        if (daysElapsed > 0 && daysElapsed % PAY_INTERVAL == 0) {
            System.out.println("Employee should be paid today.");
            bikeShop.subtractCash(1000); // Deduct $1000 for employee payment
        }
    }
}
