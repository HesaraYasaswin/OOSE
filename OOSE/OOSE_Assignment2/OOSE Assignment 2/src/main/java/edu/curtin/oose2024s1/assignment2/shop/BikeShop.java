package edu.curtin.oose2024s1.assignment2.shop;

import edu.curtin.oose2024s1.assignment2.entities.Bike;
import edu.curtin.oose2024s1.assignment2.state.BikeShopState;
import edu.curtin.oose2024s1.assignment2.factory.BikeFactory;
import edu.curtin.oose2024s1.assignment2.observer.CashObserver;
import edu.curtin.oose2024s1.assignment2.observer.EmployeeObserver;
import edu.curtin.oose2024s1.assignment2.observer.InventoryObserver;
import edu.curtin.oose2024s1.assignment2.observer.ServicingObserver;
import edu.curtin.oose2024s1.assignment2.observer.SimulationObserver;
import edu.curtin.oose2024s1.assignment2.state.NormalState;
import edu.curtin.oose2024s1.assignment2.state.LowCashState;
import edu.curtin.oose2024s1.assignment2.state.LowInventoryState;



import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.*;

public class BikeShop {

    private int cash;
    private int availableBikes;
    private final int maxCapacity;
    private List<Bike> bikes; 
    private List<String> bikesBeingServiced;
    private List<Bike> bikesAwaitingPickUp;
    private Map<String, Integer> servicingDaysMap; 
    private BikeShopState state;
    private BikeFactory bikeFactory;
    

    private int daysElapsed = 0;
    private CustomerEmailManager customerEmailManager;
    private FinancialManager financialManager;

    // states
    private final BikeShopState normalState = new NormalState(this);
    private final BikeShopState lowCashState = new LowCashState(this);
    private final BikeShopState lowInventoryState = new LowInventoryState(this);


    public BikeShop(int cash, int totalBikes, int maxCapacity, BikeFactory bikeFactory) 
    {
        this.cash = cash; 
        this.bikeFactory = bikeFactory;
        this.availableBikes = totalBikes;
        this.maxCapacity = maxCapacity;
        this.bikes = new ArrayList<>();
        this.bikesBeingServiced = new ArrayList<>();
        this.bikesAwaitingPickUp = new ArrayList<>();
        this.servicingDaysMap = new HashMap<>();
        this.customerEmailManager = new CustomerEmailManager(); 
        this.financialManager = new FinancialManager(cash);
        this.state = cash >= 10000 ? normalState : lowCashState;

        for (int i = 0; i < totalBikes; i++) {
            bikes.add(new Bike(true)); // Add new bike to the list
        }
    }
    
    

    // State transitions
    
    // Method to set the current state of the bike shop    
    public void setState(BikeShopState state) {
        this.state = state;
    }

    public BikeShopState getNormalState() {
        return normalState;
    }

    public BikeShopState getLowCashState() {
        return lowCashState;
    }

    public BikeShopState getLowInventoryState() {
        return lowInventoryState;
    }

    public List<String> getBikesBeingServiced() {
        return bikesBeingServiced;
    }

    public List<Bike> getBikesAwaitingPickUp() {
        return bikesAwaitingPickUp;
    }
    

    
    // DELIVERY Methods
    public void acceptDelivery() {
        state.acceptDelivery();  // Delegate to current state
    }

    // DROP-OFF Methods 
    public void acceptDropOff(String email) {
        state.acceptDropOff(email);
        addCustomerEmail(email);
    }

    
    public void addBikeBeingServiced(String email) {  // Method to add a bike for servicing
        if (getTotalBikes() < maxCapacity) {  // Check space
            boolean bikeAdded = false;
            for (Bike bike : bikes) {
                if (bike.isAvailable()) {
                    bike.setBeingServiced(true);  // Set bike as being serviced
                    bike.setCustomerEmail(email);  // Assign customer email
                    bikesBeingServiced.add(email);  // Add to being serviced list
                    servicingDaysMap.put(email, 2);   // Set servicing time             
                    bikeAdded = true;
                    break;
                }
            }
            if (bikeAdded) {
                updateState(); // Update state after adding a bike
            }
        }
    }
    
    
    private void decreaseServicingTime() {  // Method to decrease servicing time
        Iterator<String> iterator = bikesBeingServiced.iterator();
        while (iterator.hasNext()) {
            String email = iterator.next();
            int remainingDays = servicingDaysMap.get(email);
            if (remainingDays > 0) {
                servicingDaysMap.put(email, remainingDays - 1); // Decrement servicing time
            } else {
                iterator.remove(); // Move bike to awaiting pick-up list if servicing is complete
                bikesAwaitingPickUp.add(findBikeByEmail(email)); // Move to awaiting pick-up list
                updateState();
            }
        }
    }

    
    public Bike findBikeByEmail(String email) {  // Method to find a bike by customer email
        for (Bike bike : bikes) {
            if (email.equals(bike.getCustomerEmail())) {
                return bike; // if email matches
            }
        }
        return null;
    }

    // PURCHASE-ONLINE Methods
    public void purchaseOnline(String email) 
    {
        state.purchaseOnline(email);
    }

    // PURCHASE-IN-STORE Methods
    public void purchaseInStore() 
    {
        state.purchaseInStore();
    }

    // PICK-UP Methods
    public void pickUp(String email) 
    {
        state.pickUp(email);

        
        Bike bike = findBikeByEmail(email); // Check if the bike was dropped off for servicing
        if (bike != null && bike.getServicingTime() > 0) {
            System.out.println("FAILURE: Bike cannot be picked up yet. It is still being serviced.");
            return;
        }
    
        bikesAwaitingPickUp.remove(bike); // Remove the bike from the list of bikes awaiting pick-up after the pick up was done
        removeCustomerEmail(email);
    }

    
    public boolean isReadyForPickUp(String email) { // Method to check if a bike is ready for pick-up
     for (Bike bike : bikes) {
        if (bike.getCustomerEmail() != null && bike.getCustomerEmail().equals(email)) {
            if (bike.getServicingTime() <= 0 || bikesAwaitingPickUp.contains(bike)) {
                return true; // ready for pick-up
            }
        }
     }
     return false; // not ready
    }

    
    public void removeBikeBeingServiced(String email) { // Method to remove a bike being serviced
        Iterator<Bike> iterator = bikes.iterator();
        while (iterator.hasNext()) {
            Bike bike = iterator.next();
            if (bike.getCustomerEmail() != null && bike.getCustomerEmail().equals(email)) {
                iterator.remove(); // Remove bike from list
                bikesBeingServiced.remove(email); // Remove from being serviced list
                updateState();
                return;
            }
        }
    }

    
    public void addBikesAwaitingPickUp(Bike bike) {  // Method to add a bike to the awaiting pick-up list
        bikesAwaitingPickUp.add(bike);  
        updateState();
    }

    // Financial management part
    public void addCash(int amount) {
     financialManager.addCash(amount); // Update the cash in FinancialManager
     cash = financialManager.getCash(); // Update the cash in BikeShop
     updateState();
    }


    public void subtractCash(int amount) {
     financialManager.subtractCash(amount); // Update the cash in FinancialManager
     cash = financialManager.getCash(); // Update the cash in BikeShop
     updateState();
    }


    public int getCash() {
        return financialManager.getCash();
    }


    public int getAvailableBikes() {
        return availableBikes;
    }

    public List<Bike> getBikes() {
        return bikes;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }
    
    // customer email management
    public void addCustomerEmail(String email) 
    {
        customerEmailManager.addCustomerEmail(email);
    }


    public void removeCustomerEmail(String email) 
    {
        customerEmailManager.removeCustomerEmail(email);
    }


    public boolean isCustomerEmailInShop(String email) 
    {
        return customerEmailManager.isCustomerEmailInShop(email);
    }
    
    // shop inventory management 
        
    public void addBikes(int quantity) {  // Method to add bikes to the shop
        if (getTotalBikes() + quantity <= maxCapacity) {
            for (int i = 0; i < quantity; i++) {
                bikes.add(bikeFactory.createBike(true)); // Create and add bike
                availableBikes++;
            }
            updateState();
        } else {
            System.out.println("FAILURE: Cannot accept delivery - Not enough space.");
        }
    }

    
    public void subtractBikeAvailable() {  // Method to decrement available bikes
        if (availableBikes > 0) {
            availableBikes--;
        } else {
            System.out.println("FAILURE: No bikes available to subtract.");
        }
        updateState();
    }

    
    public void addBikeAvailable() { // Method to increment available bikes
        if (availableBikes < getTotalBikes()) {
            availableBikes++;
        }
        updateState();
    }


    // Method to update bike availability status
    public void updateBikeAvailability() {
        List<Bike> bikesReadyForPickUp = getBikesAwaitingPickUp();
        for (Bike bike : bikesReadyForPickUp) {
            if (bike != null && bike.isAvailableForPurchase()) {
                addBikeAvailable();
            }
        }
    }
    
    // Method to update the shop's state based on cash and inventory levels
    private void updateState() {
    // Check for low cash state transition
     if (cash < 5000) {
        setState(lowCashState); // Set to low cash state if cash is low
        return; 
     }

    // Check for low inventory state transition
     if (availableBikes < 10) {
        setState(lowInventoryState); // Set to low inventory state if inventory is low
        return; 
     }

    // Check for normal state transition
     if (cash >= 5000 && availableBikes >= 10 && !(state instanceof NormalState)) {
        setState(normalState); // Set to normal state if cash is sufficient and available bikes are 10 or more
     }
    }

    
    // Method to simulate the passage of days and update servicing and cash(employee -1000)
    public void incrementDay() { 
     decreaseServicingTime();  // Decrease servicing time
     updateBikeAvailability();  // Update bike availability

     if ((daysElapsed + 1) % 7 == 0) { // Deduct $1000 every 7 days
        cash -= 1000;
        System.out.println("Employee should be paid today.");
     }

     daysElapsed++;
     updateState();
    }

    // Method to get total number of bikes (available + being serviced + awaiting pick-up)
    public int getTotalBikes() 
    {
        return availableBikes + bikesBeingServiced.size() + bikesAwaitingPickUp.size();
    }

}

