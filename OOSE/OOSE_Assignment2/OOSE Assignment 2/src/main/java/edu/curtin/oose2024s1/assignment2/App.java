package edu.curtin.oose2024s1.assignment2;

import edu.curtin.oose2024s1.assignment2.factory.*;
import edu.curtin.oose2024s1.assignment2.entities.*;
import edu.curtin.oose2024s1.assignment2.observer.*;
import edu.curtin.oose2024s1.assignment2.processor.*;
import edu.curtin.oose2024s1.assignment2.shop.*;
import edu.curtin.oose2024s1.assignment2.state.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App {

    private static final int MAX_SIMULATION_DAYS = 365;
    private static final List<SimulationObserver> OBSERVERS = new ArrayList<>();
    public static int daysElapsed = 0; // to Keep track of days 
    private static int messageCount = 0;
    private static int failureCount = 0;
    private static final Logger LOGGER = Logger.getLogger(App.class.getName());

    public static void main(String[] args) 
    {
        BikeFactory bikeFactory = new BikeFactory();
        BikeShop bikeShop = new BikeShop(15000, 50, 100, bikeFactory);
        MessageProcessor<String> processor = new MessageProcessor<>(bikeShop);
        BikeShopInput bikeShopInput = new BikeShopInput();

        // observers
        OBSERVERS.add(new ConsoleObserver());
        OBSERVERS.add(new FileObserver());
        OBSERVERS.add(new CashObserver());
        OBSERVERS.add(new EmployeeObserver());
        OBSERVERS.add(new InventoryObserver());
        OBSERVERS.add(new ServicingObserver());

        // Simulation loop
        boolean running = true;
        try (FileWriter writer = new FileWriter("sim_results.txt", true)) 
        {
            while (running && daysElapsed < MAX_SIMULATION_DAYS) 
            {
                String message;
                while ((message = bikeShopInput.nextMessage()) != null) {
                    processor.processMessage(message, writer);
                    messageCount++; // Increment the message count
                }

                processor.updateSimulation(); // Update simulation
                notifyObservers(bikeShop, daysElapsed, writer); // Notify the observers
                daysElapsed++; // Increment days elapsed

                Thread.sleep(1000); // Simulate 1 second real time per day

                // Check if user pressed Enter to stop the simulation
                if (System.in.available() > 0) {
                    running = false;
                }
            }

            // Print overall stats at the end of the simulation
            writer.write("Total messages processed: " + messageCount + "\n");
            writer.write("Total failures: " + failureCount + "\n");
        } catch (IOException | InterruptedException e) {
            LOGGER.log(Level.SEVERE, () -> "Error during simulation: " + e.getMessage());
        }

        // Print overall stats at the end of the file
        System.out.println("Total messages processed: " + messageCount);
        System.out.println("Total failures: " + failureCount);
    }

    

    public static int getDaysElapsed() {
        return daysElapsed;
    }
    
    public static void incrementFailureCount() {
        failureCount++;
    }
    
    public static int getFailureCount() {
        return failureCount;
    }

    private static void notifyObservers(BikeShop bikeShop, int daysElapsed, FileWriter writer) throws IOException {
        for (SimulationObserver observer : OBSERVERS) {
            observer.update(bikeShop, daysElapsed);
        }
        writer.flush(); // Flush the writer to ensure data is written to file immediately
    }
}
