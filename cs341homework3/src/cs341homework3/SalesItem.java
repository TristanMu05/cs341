package cs341homework3;

public class SalesItem {
    private String itemName;
    private double cost;
    private int quantity;
    
    // Constructor
    public SalesItem(String itemName, double cost, int quantity) {
        this.itemName = itemName;
        this.cost = cost;
        this.quantity = quantity;
    }
    
    // Getters
    public String getItemName() {
        return itemName;
    }
    
    public double getCost() {
        return cost;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    // Calculate total cost for this item (cost * quantity)
    public double getTotalCost() {
        return cost * quantity;
    }
    
    // String representation for display
    @Override
    public String toString() {
        return String.format("%s - $%.2f x %d = $%.2f", 
                           itemName, cost, quantity, getTotalCost());
    }
}
