package edu.curtin.oose2024s1.assignment2.shop;

// purpsoe - Manages the bike shop's cash flow, tracking and adjusting the cash balance as needed.

public class FinancialManager {
    private int cash;

    public FinancialManager(int initialCash) {
        this.cash = initialCash;
    }

    public void addCash(int amount) {
        cash += amount;
    }

    public void subtractCash(int amount) {
        cash -= amount;
    }

    public int getCash() {
        return cash;
    }
}
