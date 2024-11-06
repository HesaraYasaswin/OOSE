package edu.curtin.oose2024s1.assignment2.observer;

import edu.curtin.oose2024s1.assignment2.shop.BikeShop;

public class CashObserver implements SimulationObserver {

    private static final int LW_THRESHOLD = 5000; 

    private boolean alerted = false; // To prevent repeated alerts

    @Override
    public void update(BikeShop bikeShop, int daysElapsed) {
        int cash = bikeShop.getCash();
        if (!alerted && cash < LW_THRESHOLD) {
            System.out.println("ALERT: Low cash! Cash balance: $" + cash);
            alerted = true;
        } else if (cash >= LW_THRESHOLD) {
            alerted = false; // Reset alert flag if cash balance is back to normal
        }
    }
}
