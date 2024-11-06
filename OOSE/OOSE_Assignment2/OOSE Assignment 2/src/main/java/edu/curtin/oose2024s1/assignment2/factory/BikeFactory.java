package edu.curtin.oose2024s1.assignment2.factory;

import edu.curtin.oose2024s1.assignment2.entities.Bike;


public class BikeFactory {
    public Bike createBike(boolean available) 
    {
        return new Bike(available);
    }
}


