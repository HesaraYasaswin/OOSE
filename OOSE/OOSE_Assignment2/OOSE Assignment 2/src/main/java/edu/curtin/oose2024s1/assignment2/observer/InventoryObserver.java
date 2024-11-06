package edu.curtin.oose2024s1.assignment2.observer;


import edu.curtin.oose2024s1.assignment2.shop.BikeShop;

public class InventoryObserver implements SimulationObserver {

    private static final int LOW_INVENTORY_THRESHOLD = 10; 
    private boolean alerted = false; // To prevent repeated alerts

    @Override
    public void update(BikeShop bikeShop, int daysElapsed) {
        int availableBikes = bikeShop.getAvailableBikes();
        int totalBikes = bikeShop.getTotalBikes();
        if (!alerted && ((double) availableBikes / totalBikes * 100 < LOW_INVENTORY_THRESHOLD)) {
            System.out.println("ALERT: Low inventory! Available bikes: " + availableBikes);
            alerted = true;
        } else if ((double) availableBikes / totalBikes * 100 >= LOW_INVENTORY_THRESHOLD) {
            alerted = false; // Reset alert flag if inventory is back to normal
        }
    }
}
