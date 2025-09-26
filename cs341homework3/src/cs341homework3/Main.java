package cs341homework3;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Main {

	private JFrame frame;
	private JTextField itemNameField;
	private JTextField costField;
	private JTextField quantityField;
	private JTextArea salesListArea;
	private JLabel totalLabel;
	private SalesSlip salesSlip;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main() {
		salesSlip = new SalesSlip();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("SalesIt - Sales List Application");
		frame.setBounds(100, 100, 600, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		// Title
		JLabel titleLabel = new JLabel("Sales List");
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
		titleLabel.setBounds(127, 10, 300, 25);
		frame.getContentPane().add(titleLabel);
		
		// Item Name
		JLabel itemNameLabel = new JLabel("Item Name:");
		itemNameLabel.setBounds(20, 50, 80, 25);
		frame.getContentPane().add(itemNameLabel);
		
		itemNameField = new JTextField();
		itemNameField.setBounds(110, 50, 150, 25);
		frame.getContentPane().add(itemNameField);
		
		// Cost
		JLabel costLabel = new JLabel("Cost ($):");
		costLabel.setBounds(20, 80, 60, 25);
		frame.getContentPane().add(costLabel);
		
		costField = new JTextField();
		costField.setBounds(110, 80, 80, 25);
		frame.getContentPane().add(costField);
		
		// Quantity
		JLabel quantityLabel = new JLabel("Quantity:");
		quantityLabel.setBounds(20, 115, 70, 25);
		frame.getContentPane().add(quantityLabel);
		
		quantityField = new JTextField();
		quantityField.setBounds(110, 115, 50, 25);
		frame.getContentPane().add(quantityField);
		
		// Add Item Button
		JButton addItemButton = new JButton("Add Item");
		addItemButton.setBounds(180, 137, 214, 30);
		addItemButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addItem();
			}
		});
		frame.getContentPane().add(addItemButton);
		
		// Sales List Display
		JLabel salesListLabel = new JLabel("Sales List:");
		salesListLabel.setFont(new Font("Arial", Font.BOLD, 12));
		salesListLabel.setBounds(20, 140, 100, 25);
		frame.getContentPane().add(salesListLabel);
		
		salesListArea = new JTextArea();
		salesListArea.setEditable(false);
		salesListArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
		JScrollPane scrollPane = new JScrollPane(salesListArea);
		scrollPane.setBounds(20, 170, 550, 200);
		frame.getContentPane().add(scrollPane);
		
		// Total Display
		totalLabel = new JLabel("Total Sales: $0.00");
		totalLabel.setFont(new Font("Arial", Font.BOLD, 14));
		totalLabel.setBounds(20, 414, 200, 25);
		frame.getContentPane().add(totalLabel);
		
		// Clear List Button
		JButton clearButton = new JButton("Clear List");
		clearButton.setBounds(470, 412, 100, 30);
		clearButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clearList();
			}
		});
		frame.getContentPane().add(clearButton);
		
		updateDisplay();
	}
	
	/**
	 * Add an item to the sales slip
	 */
	private void addItem() {
		try {
			String itemName = itemNameField.getText().trim();
			String costText = costField.getText().trim();
			String quantityText = quantityField.getText().trim();
			
			// Validate inputs
			if (itemName.isEmpty()) {
				JOptionPane.showMessageDialog(frame, "Please enter an item name.", "Input Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			double cost = Double.parseDouble(costText);
			if (cost < 0 || cost >= 100) {
				JOptionPane.showMessageDialog(frame, "Cost must be between $0.00 and $99.99.", "Input Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			int quantity = Integer.parseInt(quantityText);
			if (quantity <= 0) {
				JOptionPane.showMessageDialog(frame, "Quantity must be greater than 0.", "Input Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			// Add the item to the sales slip
			salesSlip.addItem(itemName, cost, quantity);
			
			// Clear input fields
			itemNameField.setText("");
			costField.setText("");
			quantityField.setText("");
			
			// Update the display
			updateDisplay();
			
			// Focus back to item name field for next entry
			itemNameField.requestFocus();
			
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(frame, "Please enter valid numbers for cost and quantity.", "Input Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * Clear all items from the sales list
	 */
	private void clearList() {
		salesSlip.clear();
		updateDisplay();
	}
	
	/**
	 * Update the display with current sales list and total
	 */
	private void updateDisplay() {
		// Update sales list display
		StringBuilder displayText = new StringBuilder();
		if (salesSlip.getItemCount() == 0) {
			displayText.append("No items in the list yet.\n\nEnter item details above and click 'Add Item' to get started.");
		} else {
			for (SalesItem item : salesSlip.getItems()) {
				displayText.append(item.toString()).append("\n");
			}
		}
		
		salesListArea.setText(displayText.toString());
		
		// Update total
		totalLabel.setText(String.format("Total Sales: $%.2f", salesSlip.getTotalSales()));
	}
}
