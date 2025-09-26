package cs341homework3;

import java.util.ArrayList;
import java.util.List;

public class SalesSlip {
    private List<SalesItem> items;
    
    // Constructor
    public SalesSlip() {
        this.items = new ArrayList<>();
    }
    
    // Add an item to the sales slip
    public void addItem(SalesItem item) {
        items.add(item);
    }
    
    // Add an item by creating a new SalesItem
    public void addItem(String itemName, double cost, int quantity) {
        SalesItem item = new SalesItem(itemName, cost, quantity);
        addItem(item);
    }
    
    // Calculate total sales for all items
    public double getTotalSales() {
        double total = 0.0;
        for (SalesItem item : items) {
            total += item.getTotalCost();
        }
        return total;
    }
    
    // Get all items
    public List<SalesItem> getItems() {
        return new ArrayList<>(items); // Return a copy to preserve encapsulation
    }
    
    // Get number of items
    public int getItemCount() {
        return items.size();
    }
    
    // Clear all items
    public void clear() {
        items.clear();
    }
    
    // String representation of the sales slip
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Sales Slip:\n");
        sb.append("-----------\n");
        
        if (items.isEmpty()) {
            sb.append("No items\n");
        } else {
            for (SalesItem item : items) {
                sb.append(item.toString()).append("\n");
            }
            sb.append("-----------\n");
            sb.append(String.format("Total: $%.2f", getTotalSales()));
        }
        
        return sb.toString();
    }
}
