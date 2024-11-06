package edu.curtin.oose2024s1.assignment2.observer;

import edu.curtin.oose2024s1.assignment2.shop.BikeShop;

// observes the bike shop's state and writes daily statistics to "sim_results.txt"

import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;



public class FileObserver implements SimulationObserver {

    private static final Logger LOGGER = Logger.getLogger(FileObserver.class.getName());

    @Override
    public void update(BikeShop bikeShop, int daysElapsed) {
        try (FileWriter writer = new FileWriter("sim_results.txt", true)) {
            writer.write("Day " + daysElapsed + " Statistics:\n");
            writer.write("Cash: $" + bikeShop.getCash() + "\n");
            writer.write("Total Bikes: " + bikeShop.getTotalBikes() + "\n");
            writer.write("Available Bikes: " + bikeShop.getAvailableBikes() + "\n");
            writer.write("Bikes Being Serviced: " + bikeShop.getBikesBeingServiced().size() + "\n");
            writer.write("Bikes Awaiting Pick-up: " + bikeShop.getBikesAwaitingPickUp().size() + "\n\n");
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, () -> "Error writing to file: " + e.getMessage()); 
        }
    }
}

