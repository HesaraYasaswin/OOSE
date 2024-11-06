package edu.curtin.oose2024s1.assignment2.shop;

import java.util.HashSet;
import java.util.Set;

// purpose - Manages customer email addresses for tracking bikes being serviced or awaiting pick-up in shop.

public class CustomerEmailManager {
    private Set<String> customerEmailsInShop;

    public CustomerEmailManager() {
        this.customerEmailsInShop = new HashSet<>();
    }

    public void addCustomerEmail(String email) {
        customerEmailsInShop.add(email);
    }

    public void removeCustomerEmail(String email) {
        customerEmailsInShop.remove(email);
    }

    public boolean isCustomerEmailInShop(String email) {
        return customerEmailsInShop.contains(email);
    }
}
