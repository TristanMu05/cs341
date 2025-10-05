package cs341homework5;

/**
 * Custom LinkedList implementation for storing double values.
 * Provides methods to add values and calculate the mean and standard deviation efficiently.
 * Uses running sum and sum of squares for O(1) statistical calculations.
 */
public class LinkedList {
    private Node head;
    private int size;
    private double sum; // Track sum for efficient mean calculation
    private double sumOfSquares; // Track sum of squares for efficient standard deviation calculation
    
    /**
     * Constructor to create an empty linked list.
     */
    public LinkedList() {
        this.head = null;
        this.size = 0;
        this.sum = 0.0;
        this.sumOfSquares = 0.0;
    }
    
    /**
     * Adds a double value to the end of the list.
     * Time complexity: O(1) by maintaining sum and sumOfSquares variables.
     * @param value The double value to add
     */
    public void add(double value) {
        Node newNode = new Node(value);
        
        if (head == null) {
            head = newNode;
        } else {
            // Find the last node
            Node current = head;
            while (current.getNext() != null) {
                current = current.getNext();
            }
            current.setNext(newNode);
        }
        
        size++;
        sum += value;
        sumOfSquares += (value * value);
    }
    
    /**
     * Calculates the mean (average) of all values in the list.
     * Time complexity: O(1) due to maintaining running sum.
     * @return The mean value, or 0.0 if the list is empty
     */
    public double calculateMean() {
        if (size == 0) {
            return 0.0;
        }
        return sum / size;
    }
    
    /**
     * Gets the number of elements in the list.
     * @return The size of the list
     */
    public int getSize() {
        return size;
    }
    
    /**
     * Gets the sum of all elements in the list.
     * @return The sum of all values
     */
    public double getSum() {
        return sum;
    }
    
    /**
     * Checks if the list is empty.
     * @return true if the list is empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }
    
    /**
     * Calculates the sample standard deviation of all values in the list.
     * Uses the formula: stddev = sqrt((sumOfSquares/n) - mean^2)
     * Time complexity: O(1) due to maintaining running sum and sumOfSquares.
     * @return The standard deviation, or 0.0 if the list has fewer than 2 elements
     */
    public double calculateStandardDeviation() {
        if (size < 2) {
            return 0.0;
        }
        
        double mean = calculateMean();
        // Sample standard deviation formula: sqrt(sum((x - mean)^2) / (n - 1))
        // Which equals: sqrt((sumOfSquares - n*mean^2) / (n - 1))
        double variance = (sumOfSquares - size * mean * mean) / (size - 1);
        return Math.sqrt(variance);
    }
    
    /**
     * Gets the sum of squares of all elements in the list.
     * @return The sum of squares
     */
    public double getSumOfSquares() {
        return sumOfSquares;
    }
    
    /**
     * Gets the head node of the list (for testing purposes).
     * @return The head node, or null if list is empty
     */
    public Node getHead() {
        return head;
    }
}
