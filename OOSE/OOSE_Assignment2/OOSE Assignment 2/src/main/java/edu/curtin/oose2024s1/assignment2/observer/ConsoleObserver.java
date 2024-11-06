package edu.curtin.oose2024s1.assignment2.observer;

import edu.curtin.oose2024s1.assignment2.shop.BikeShop;


// This code implements a console observer to display daily statistics of a bike shop simulation.

public class ConsoleObserver implements SimulationObserver {
    @Override
    public void update(BikeShop bikeShop, int daysElapsed) {
        System.out.println("Day " + daysElapsed + " Statistics:");
        System.out.println("Cash: $" + bikeShop.getCash());
        System.out.println("Total Bikes: " + bikeShop.getTotalBikes());
        System.out.println("Available Bikes: " + bikeShop.getAvailableBikes());
        System.out.println("Bikes Being Serviced: " + bikeShop.getBikesBeingServiced().size());
        System.out.println("Bikes Awaiting Pick-up: " + bikeShop.getBikesAwaitingPickUp().size());
        System.out.println();
    }
}
