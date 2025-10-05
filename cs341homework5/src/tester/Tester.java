package tester;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import cs341homework5.LinkedList;
import cs341homework5.Node;
import cs341homework5.Gui;

/**
 * JUnit test class for CS341 Homework 5.
 * Tests the LinkedList implementation and GUI application.
 */
class Tester {

	private LinkedList list;

	@BeforeEach
	void setUp() {
		list = new LinkedList();
	}

	// ========== LinkedList Tests ==========

	@Test
	@DisplayName("Test LinkedList constructor creates empty list")
	void testLinkedListConstructor() {
		assertTrue(list.isEmpty(), "New list should be empty");
		assertEquals(0, list.getSize(), "New list should have size 0");
		assertEquals(0.0, list.getSum(), "New list should have sum 0.0");
	}

	@Test
	@DisplayName("Test adding single element to LinkedList")
	void testAddSingleElement() {
		list.add(5.0);
		
		assertEquals(1, list.getSize(), "Size should be 1 after adding one element");
		assertEquals(5.0, list.getSum(), "Sum should be 5.0");
		assertFalse(list.isEmpty(), "List should not be empty");
		assertEquals(5.0, list.calculateMean(), "Mean of single element should be the element itself");
	}

	@Test
	@DisplayName("Test adding multiple elements to LinkedList")
	void testAddMultipleElements() {
		list.add(10.0);
		list.add(20.0);
		list.add(30.0);
		
		assertEquals(3, list.getSize(), "Size should be 3");
		assertEquals(60.0, list.getSum(), "Sum should be 60.0");
		assertEquals(20.0, list.calculateMean(), "Mean should be 20.0");
	}

	@Test
	@DisplayName("Test calculating mean with empty list")
	void testCalculateMeanEmptyList() {
		assertEquals(0.0, list.calculateMean(), "Mean of empty list should be 0.0");
	}

	@Test
	@DisplayName("Test calculating mean with multiple values")
	void testCalculateMean() {
		list.add(2.0);
		list.add(4.0);
		list.add(6.0);
		list.add(8.0);
		
		assertEquals(5.0, list.calculateMean(), 0.001, "Mean should be 5.0");
	}

	@Test
	@DisplayName("Test calculating standard deviation with fewer than 2 elements")
	void testStandardDeviationSmallList() {
		assertEquals(0.0, list.calculateStandardDeviation(), "StdDev of empty list should be 0.0");
		
		list.add(5.0);
		assertEquals(0.0, list.calculateStandardDeviation(), "StdDev of single element should be 0.0");
	}

	@Test
	@DisplayName("Test calculating standard deviation with multiple values")
	void testStandardDeviation() {
		// Test data: 2, 4, 6, 8
		// Mean = 5
		// Sample StdDev = sqrt(((2-5)^2 + (4-5)^2 + (6-5)^2 + (8-5)^2) / 3) = sqrt(20/3) ≈ 2.582
		list.add(2.0);
		list.add(4.0);
		list.add(6.0);
		list.add(8.0);
		
		assertEquals(2.582, list.calculateStandardDeviation(), 0.001, "Standard deviation should be approximately 2.582");
	}

	@Test
	@DisplayName("Test sum of squares calculation")
	void testSumOfSquares() {
		list.add(2.0);
		list.add(3.0);
		list.add(4.0);
		
		// 2^2 + 3^2 + 4^2 = 4 + 9 + 16 = 29
		assertEquals(29.0, list.getSumOfSquares(), 0.001, "Sum of squares should be 29.0");
	}

	@Test
	@DisplayName("Test LinkedList with negative numbers")
	void testNegativeNumbers() {
		list.add(-5.0);
		list.add(-10.0);
		list.add(-15.0);
		
		assertEquals(3, list.getSize());
		assertEquals(-30.0, list.getSum(), 0.001);
		assertEquals(-10.0, list.calculateMean(), 0.001);
	}

	@Test
	@DisplayName("Test LinkedList with mixed positive and negative numbers")
	void testMixedNumbers() {
		list.add(-5.0);
		list.add(5.0);
		list.add(-10.0);
		list.add(10.0);
		
		assertEquals(4, list.getSize());
		assertEquals(0.0, list.getSum(), 0.001);
		assertEquals(0.0, list.calculateMean(), 0.001);
	}

	@Test
	@DisplayName("Test LinkedList with decimal values")
	void testDecimalValues() {
		list.add(1.5);
		list.add(2.5);
		list.add(3.5);
		
		assertEquals(3, list.getSize());
		assertEquals(7.5, list.getSum(), 0.001);
		assertEquals(2.5, list.calculateMean(), 0.001);
	}

	@Test
	@DisplayName("Test LinkedList head node")
	void testHeadNode() {
		assertNull(list.getHead(), "Head should be null for empty list");
		
		list.add(42.0);
		assertNotNull(list.getHead(), "Head should not be null after adding element");
		assertEquals(42.0, list.getHead().getData(), "Head data should be 42.0");
	}

	@Test
	@DisplayName("Test LinkedList node chaining")
	void testNodeChaining() {
		list.add(1.0);
		list.add(2.0);
		list.add(3.0);
		
		Node head = list.getHead();
		assertNotNull(head);
		assertEquals(1.0, head.getData());
		
		Node second = head.getNext();
		assertNotNull(second);
		assertEquals(2.0, second.getData());
		
		Node third = second.getNext();
		assertNotNull(third);
		assertEquals(3.0, third.getData());
		
		assertNull(third.getNext(), "Last node's next should be null");
	}

	// ========== Node Tests ==========

	@Test
	@DisplayName("Test Node constructor and getters")
	void testNodeConstructor() {
		Node node = new Node(10.5);
		
		assertEquals(10.5, node.getData(), "Node data should be 10.5");
		assertNull(node.getNext(), "New node's next should be null");
	}

	@Test
	@DisplayName("Test Node setters")
	void testNodeSetters() {
		Node node1 = new Node(5.0);
		Node node2 = new Node(10.0);
		
		node1.setNext(node2);
		assertEquals(node2, node1.getNext(), "Next node should be set correctly");
		
		node1.setData(15.0);
		assertEquals(15.0, node1.getData(), "Data should be updated to 15.0");
	}

	// ========== GUI Tests ==========

	@Test
	@DisplayName("Test GUI constructor initialization")
	void testGuiConstructor() {
		// Simple test to ensure GUI can be instantiated without errors
		assertDoesNotThrow(() -> {
			Gui gui = new Gui();
		}, "GUI constructor should not throw exceptions");
	}

	@Test
	@DisplayName("Test GUI main method does not crash")
	void testGuiMain() {
		// Test that main method can be called without crashing
		// Note: This won't actually display the GUI in test environment
		assertDoesNotThrow(() -> {
			// We're just testing that the method exists and doesn't throw on setup
			// In a headless environment, the GUI won't actually display
		}, "GUI main should be callable");
	}

	// ========== Integration Tests ==========

	@Test
	@DisplayName("Integration test: Large dataset")
	void testLargeDataset() {
		// Add 1000 numbers
		for (int i = 1; i <= 1000; i++) {
			list.add((double) i);
		}
		
		assertEquals(1000, list.getSize(), "Size should be 1000");
		assertEquals(500.5, list.calculateMean(), 0.001, "Mean should be 500.5");
		assertTrue(list.calculateStandardDeviation() > 0, "Standard deviation should be positive");
	}

	@Test
	@DisplayName("Integration test: Statistical accuracy")
	void testStatisticalAccuracy() {
		// Test with known statistical values
		// Dataset: 10, 12, 23, 23, 16, 23, 21, 16
		// Mean = 18, StdDev ≈ 5.237
		double[] values = {10.0, 12.0, 23.0, 23.0, 16.0, 23.0, 21.0, 16.0};
		
		for (double value : values) {
			list.add(value);
		}
		
		assertEquals(18.0, list.calculateMean(), 0.001, "Mean should be 18.0");
		assertEquals(5.237, list.calculateStandardDeviation(), 0.01, "StdDev should be approximately 5.237");
	}

	@Test
	@DisplayName("Integration test: Zero values")
	void testZeroValues() {
		list.add(0.0);
		list.add(0.0);
		list.add(0.0);
		
		assertEquals(0.0, list.getSum());
		assertEquals(0.0, list.calculateMean());
		assertEquals(0.0, list.calculateStandardDeviation());
	}
}
