package edu.curtin.oose2024s1.assignment2.entities;

public class Bike {
    private boolean available;
    private boolean beingServiced;
    private String customerEmail;
    private int servicingTime; // Time left 

    public Bike(boolean available) {
        this.available = available;
        this.beingServiced = false;
        this.customerEmail = null;
        this.servicingTime = 0; 
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public boolean isBeingServiced() {
        return beingServiced;
    }

    public void setBeingServiced(boolean beingServiced) {
        this.beingServiced = beingServiced;
        if (beingServiced) {
            this.available = false;
        }
    }
    
    public int getServicingTime() {
        return servicingTime;
    }

    public void setServicingTime(int servicingTime) {
        this.servicingTime = servicingTime;
    }

    public void decreaseServicingTime() {
     if (this.beingServiced && this.servicingTime > 0) 
     {
        this.servicingTime--;
     }
    }

    public boolean isAvailableForPurchase() {
        return !beingServiced && available;
    }
    
    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }
}

