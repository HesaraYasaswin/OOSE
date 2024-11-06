package edu.curtin.oose2024s1.assignment2.observer;

import edu.curtin.oose2024s1.assignment2.shop.BikeShop;

// This code defines an interface for observers to receive updates on the bike shop simulation's progress.

public interface SimulationObserver {
    void update(BikeShop bikeShop, int daysElapsed);
}
