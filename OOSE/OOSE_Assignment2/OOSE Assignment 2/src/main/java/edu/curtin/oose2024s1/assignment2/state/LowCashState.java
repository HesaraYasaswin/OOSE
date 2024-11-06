package edu.curtin.oose2024s1.assignment2.state;


import edu.curtin.oose2024s1.assignment2.shop.BikeShop;
import edu.curtin.oose2024s1.assignment2.entities.Bike;


// purpose - defines actions for handling bike shop operations in low cash state

public class LowCashState extends BikeShopState {

    public LowCashState(BikeShop bikeShop) {
        super(bikeShop);
    }

    @Override
    public void acceptDelivery() {
        if (bikeShop.getAvailableBikes() <= 90 && bikeShop.getCash() >= 10000) {
            bikeShop.addCash(-5000);
            bikeShop.addBikes(10);
            System.out.println("DELIVERY accepted: 10 bikes added in low cash state.");
        } else {
            System.out.println("FAILURE: Cannot accept delivery in low cash state - Not enough cash or space.");
        }
    }

    @Override
    public void acceptDropOff(String email) {
        bikeShop.addBikeBeingServiced(email); // Add bike to being serviced list
        System.out.println("Bike drop-off accepted in low cash state for email: " + email);
    }

    @Override
    public void purchaseOnline(String email) {
        if (bikeShop.getAvailableBikes() > 0) {
            boolean bikePurchased = false;
            for (Bike bike : bikeShop.getBikes()) {  // Iterate through available bikes
                if (bike.isAvailableForPurchase()) {
                    bike.setAvailable(false);
                    bike.setCustomerEmail(email);
                    bikeShop.getBikesAwaitingPickUp().add(bike);
                    bikeShop.subtractBikeAvailable();
                    bikeShop.addCash(1000);
                    bikePurchased = true;
                    System.out.println("Bike purchased online in low cash state.");
                    break;
                }
            }
            if (!bikePurchased) { // If no bikes available for purchase
                System.out.println("No bikes available for online purchase in low cash state.");
            }
        } else {
            System.out.println("No bikes available for online purchase in low cash state.");
        }
    }

    @Override
    public void purchaseInStore() {
        if (bikeShop.getAvailableBikes() > 0) {
            bikeShop.subtractBikeAvailable();
            bikeShop.addCash(1000);
            System.out.println("Bike purchased in store in low cash state.");
        } else {
            System.out.println("No bikes available for in-store purchase in low cash state.");
        }
    }

    @Override
    public void pickUp(String email) {
        if (bikeShop.isReadyForPickUp(email)) {
            Bike bike = bikeShop.findBikeByEmail(email);
            if (bike != null) {
                bikeShop.getBikesAwaitingPickUp().remove(bike);
                bikeShop.removeBikeBeingServiced(email);
                bikeShop.addCash(100);
                System.out.println("Bike pick-up in low cash state for email: " + email);
            } else {
                System.out.println("Bike not found for pick-up for email: " + email);
            }
        } else {
            System.out.println("Bike not ready for pick-up for email: " + email);
        }
    }
}

