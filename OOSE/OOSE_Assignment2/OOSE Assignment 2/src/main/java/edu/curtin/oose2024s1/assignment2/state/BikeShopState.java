package edu.curtin.oose2024s1.assignment2.state;

import edu.curtin.oose2024s1.assignment2.shop.BikeShop;

// purpose - to manage the bike shop's behavior in various operational states.
public abstract class BikeShopState {
    protected BikeShop bikeShop;

    public BikeShopState(BikeShop bikeShop) {
        this.bikeShop = bikeShop;
    }

    // Abstract methods
    public abstract void acceptDelivery();
    public abstract void acceptDropOff(String email);
    public abstract void purchaseOnline(String email);
    public abstract void purchaseInStore();
    public abstract void pickUp(String email);
}


