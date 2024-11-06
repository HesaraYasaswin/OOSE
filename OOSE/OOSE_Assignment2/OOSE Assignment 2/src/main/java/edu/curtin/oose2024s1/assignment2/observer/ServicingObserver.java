package edu.curtin.oose2024s1.assignment2.observer;

import edu.curtin.oose2024s1.assignment2.shop.BikeShop;

import java.util.List;

public class ServicingObserver implements SimulationObserver {

    @Override
    public void update(BikeShop bikeShop, int daysElapsed) {
        List<String> bikesBeingServiced = bikeShop.getBikesBeingServiced();
        if (!bikesBeingServiced.isEmpty()) {
            System.out.println("Bikes currently being serviced:");
            for (String email : bikesBeingServiced) {
                System.out.println(" - " + email);
            }
        }
    }
}

// here the "Bikes currently being serviced:" will be displayed for example if the bike was dropped off on day 1 it will be ready for pick up on day 3. till day 3 this message will appear on the top before the day stats even though on day 3 the servicing bike is added to the awaiting for pick up list.
