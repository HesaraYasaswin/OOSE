package edu.curtin.oose2024s1.assignment2.processor;


import edu.curtin.oose2024s1.assignment2.App;
import edu.curtin.oose2024s1.assignment2.shop.BikeShop;


import java.io.FileWriter;
import java.io.IOException;

public interface MessageHandler<T> {
    void handle(String message, FileWriter writer) throws IOException;
}
