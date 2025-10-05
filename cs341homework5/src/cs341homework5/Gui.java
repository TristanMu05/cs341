package cs341homework5;

import java.awt.EventQueue;
import java.awt.BorderLayout;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 * GUI class for the Mean and Standard Deviation Calculator application.
 * Provides a user interface to load a file of numbers and display statistics
 * including mean and sample standard deviation.
 */
public class Gui {

	private JFrame frame;
	private JTextArea resultsTextArea;
	private LinkedList numberList;
	private int invalidCount;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Gui window = new Gui();
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
	public Gui() {
		numberList = new LinkedList();
		invalidCount = 0;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Mean & Standard Deviation Calculator - CS341 Homework 5");
		frame.setBounds(100, 100, 600, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(10, 10));
		
		// Title label
		JLabel titleLabel = new JLabel("Mean & Standard Deviation Calculator");
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
		titleLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
		frame.getContentPane().add(titleLabel, BorderLayout.NORTH);
		
		// Results text area with scroll pane
		resultsTextArea = new JTextArea();
		resultsTextArea.setEditable(false);
		resultsTextArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
		resultsTextArea.setText("Click 'Load File' to select a text file containing numbers (one per line).");
		JScrollPane scrollPane = new JScrollPane(resultsTextArea);
		scrollPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		// Button panel
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		
		// Load file button
		JButton loadFileButton = new JButton("Load File");
		loadFileButton.setFont(new Font("Arial", Font.PLAIN, 14));
		loadFileButton.addActionListener(e -> loadFile());
		buttonPanel.add(loadFileButton);
		
		// Clear button
		JButton clearButton = new JButton("Clear");
		clearButton.setFont(new Font("Arial", Font.PLAIN, 14));
		clearButton.addActionListener(e -> clearResults());
		buttonPanel.add(clearButton);
	}

	/**
	 * Opens a file chooser dialog and loads numbers from the selected file.
	 */
	private void loadFile() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Select a text file with numbers");
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
		
		int result = fileChooser.showOpenDialog(frame);
		
		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			processFile(selectedFile);
		}
	}

	/**
	 * Processes the selected file and calculates statistics.
	 * @param file The file to process
	 */
	private void processFile(File file) {
		// Reset the list and counters
		numberList = new LinkedList();
		invalidCount = 0;
		int lineNumber = 0;
		
		StringBuilder output = new StringBuilder();
		output.append("File: ").append(file.getName()).append("\n");
		output.append("=" .repeat(60)).append("\n\n");
		
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			String line;
			
			while ((line = reader.readLine()) != null) {
				lineNumber++;
				line = line.trim();
				
				// Skip empty lines
				if (line.isEmpty()) {
					continue;
				}
				
				try {
					double number = Double.parseDouble(line);
					numberList.add(number);
				} catch (NumberFormatException e) {
					invalidCount++;
					output.append("Warning: Invalid number on line ").append(lineNumber)
					      .append(": \"").append(line).append("\"\n");
				}
			}
			
			// Display results
			output.append("\n").append("-".repeat(60)).append("\n");
			output.append("RESULTS\n");
			output.append("-".repeat(60)).append("\n\n");
			
			if (numberList.isEmpty()) {
				output.append("No valid numbers found in the file.\n");
			} else {
				output.append(String.format("Valid numbers: %d\n", numberList.getSize()));
				output.append(String.format("Invalid entries: %d\n", invalidCount));
				output.append(String.format("Sum: %.4f\n", numberList.getSum()));
				output.append(String.format("Mean: %.4f\n", numberList.calculateMean()));
				output.append(String.format("Sample Standard Deviation: %.4f\n", numberList.calculateStandardDeviation()));
			}
			
			if (invalidCount == 0 && !numberList.isEmpty()) {
				output.append("\n✓ All entries were valid!\n");
			} else if (invalidCount > 0) {
				output.append(String.format("\n⚠ %d invalid entries were skipped.\n", invalidCount));
			}
			
			resultsTextArea.setText(output.toString());
			
		} catch (IOException e) {
			resultsTextArea.setText("Error reading file: " + e.getMessage());
		}
	}

	/**
	 * Clears the results display and resets the data.
	 */
	private void clearResults() {
		numberList = new LinkedList();
		invalidCount = 0;
		resultsTextArea.setText("Click 'Load File' to select a text file containing numbers (one per line).");
	}
}
