package cs341homework5;

/**
 * Node class for storing a double value in a linked list.
 * Each node contains a data value and a reference to the next node.
 */
public class Node {
    private double data;
    private Node next;
    
    /**
     * Constructor to create a new node with the given data.
     * @param data The double value to store in this node
     */
    public Node(double data) {
        this.data = data;
        this.next = null;
    }
    
    /**
     * Gets the data stored in this node.
     * @return The double value stored in this node
     */
    public double getData() {
        return data;
    }
    
    /**
     * Sets the data for this node.
     * @param data The double value to store
     */
    public void setData(double data) {
        this.data = data;
    }
    
    /**
     * Gets the next node in the linked list.
     * @return The next node, or null if this is the last node
     */
    public Node getNext() {
        return next;
    }
    
    /**
     * Sets the next node in the linked list.
     * @param next The node to set as next
     */
    public void setNext(Node next) {
        this.next = next;
    }
}
