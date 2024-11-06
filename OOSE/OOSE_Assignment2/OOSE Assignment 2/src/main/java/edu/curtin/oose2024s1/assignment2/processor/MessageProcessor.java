package edu.curtin.oose2024s1.assignment2.processor;


import edu.curtin.oose2024s1.assignment2.App;
import edu.curtin.oose2024s1.assignment2.shop.BikeShop;

// purpose - Processes and handles bike shop messages, updating the shop and writing results to a file.

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MessageProcessor<T> {  // a generic class MessageProcessor with type parameter T
    private static final Logger LOGGER = Logger.getLogger(MessageProcessor.class.getName());

    private final BikeShop bikeShop;
    private final Map<String, MessageHandler<T>> handlers; // Declare a final instance variable handlers of type Map<String, MessageHandler<T>> to store message handlers

    public MessageProcessor(BikeShop bikeShop) {
        this.bikeShop = bikeShop;
        this.handlers = new HashMap<>();  // Initialize the handlers map with a new HashMap
        initializeHandlers();  // call
    }

    private void initializeHandlers() {
        handlers.put("DELIVERY", this::handleDelivery);   // Add a message handler for DELIVERY messages
        handlers.put("DROP-OFF", this::handleDropOff);
        handlers.put("PURCHASE-ONLINE", this::handlePurchaseOnline);
        handlers.put("PURCHASE-IN-STORE", this::handlePurchaseInStore);
        handlers.put("PICK-UP", this::handlePickUp);
    }

    public void processMessage(T message, FileWriter writer) {  // to process incoming messages
        try {
            if (message instanceof String) {
                String msg = (String) message;
                System.out.println(msg);
                writer.write(msg + "\n");
                // Delegate processing based on the message type
                handleBasedOnMessageType(msg, writer);
            } else {
                LOGGER.log(Level.WARNING, () -> "Invalid message type: " + message.getClass().getName());
                writeToFile("FAILURE: Invalid message type", writer);
                App.incrementFailureCount();
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, () -> "Error processing message: " + e.getMessage());
        }
    }

    private void handleBasedOnMessageType(String message, FileWriter writer) throws IOException {  // Method to handle messages based on their type
        for (Map.Entry<String, MessageHandler<T>> entry : handlers.entrySet()) { // Iterate over the handlers map
            if (message.startsWith(entry.getKey())) { // Check if the message starts with the key of the current handler
                entry.getValue().handle(message, writer);  // Call the handle method of the corresponding handler
                return;
            }
        }
        LOGGER.log(Level.WARNING, () -> "Invalid message: " + message);
        writeToFile("FAILURE: Invalid message", writer);
        App.incrementFailureCount();
    }

    private void writeToFile(String message, FileWriter writer) throws IOException {
        writer.write(message + "\n");
        writer.flush();
    }

    private void handleDelivery(String message, FileWriter writer) throws IOException {
        bikeShop.acceptDelivery();
        writeToFile(message, writer);
    }

    private void handleDropOff(String message, FileWriter writer) throws IOException {
        String[] parts = message.split(" ");
        if (parts.length == 2) {
            bikeShop.acceptDropOff(parts[1]);
            writeToFile(message, writer);
        } else {
            writeToFile("FAILURE: Invalid drop-off message format", writer);
            App.incrementFailureCount();
        }
    }

    private void handlePurchaseOnline(String message, FileWriter writer) throws IOException {
        bikeShop.purchaseOnline(message.substring("PURCHASE-ONLINE".length()).trim());
        writeToFile(message, writer);
    }

    private void handlePurchaseInStore(String message, FileWriter writer) throws IOException {
        bikeShop.purchaseInStore();
        writeToFile(message, writer);
    }

    private void handlePickUp(String message, FileWriter writer) throws IOException {
        String[] parts = message.split(" ");  // Split the message into parts using space as delimiter
        if (parts.length == 2) {
            bikeShop.pickUp(parts[1]);
            writeToFile(message, writer);
        } else {
            writeToFile("FAILURE: Invalid pick-up message format", writer);
            App.incrementFailureCount();
        }
    }

    // to update the simulation by incrementing the day count
    public void updateSimulation() 
    {
        bikeShop.incrementDay();
    }
}



