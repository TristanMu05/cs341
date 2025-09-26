package tester;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import cs341homework3.SalesItem;
import cs341homework3.SalesSlip;

class TestSalesApp {

	@Test
	void testSalesItem() {
		// Test creating a sales item
		SalesItem item = new SalesItem("Apple", 1.50, 3);
		
		// Check the basic properties
		assertEquals("Apple", item.getItemName());
		assertEquals(1.50, item.getCost(), 0.01);
		assertEquals(3, item.getQuantity());
		
		// Check the total cost calculation
		assertEquals(4.50, item.getTotalCost(), 0.01);
	}
	
	@Test
	void testSalesSlip() {
		// Create a new sales slip
		SalesSlip slip = new SalesSlip();
		
		// Should start empty
		assertEquals(0, slip.getItemCount());
		assertEquals(0.0, slip.getTotalSales(), 0.01);
		
		// Add some items
		slip.addItem("Banana", 2.00, 2);
		slip.addItem("Orange", 1.00, 5);
		
		// Check the counts and totals
		assertEquals(2, slip.getItemCount());
		assertEquals(9.00, slip.getTotalSales(), 0.01); // 2*2 + 1*5 = 9
	}
	
	@Test 
	void testClearSlip() {
		SalesSlip slip = new SalesSlip();
		
		// Add an item
		slip.addItem("Test", 5.00, 1);
		assertEquals(1, slip.getItemCount());
		
		// Clear it
		slip.clear();
		assertEquals(0, slip.getItemCount());
		assertEquals(0.0, slip.getTotalSales(), 0.01);
	}

}
