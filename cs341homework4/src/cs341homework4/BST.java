package cs341homework4;

public class BST {
	
	/**
	 * Binary Search Tree to act as a dictionary
	 * 
	 * Node root = root of the tree
	 * int size = size/number of nodes in the tree
	 */
	
	private Node root;
	private int size;
	
	
	/**
	 * Constructor for the BST
	 */
	public BST() {
		this.root = null;
		this.size = 0;
	}
	
	/**
	 * Insert a new word into the BST
	 * @param word
	 */
	public void insertWordNode(String word) {
		Node newNode = new Node(word);
		if (root == null) {
			root = newNode;
			size++;
			return;
		}
		Node current = root;
		Node parent = null;
		while (true) {
			parent = current;
			if (word.compareTo(current.val) < 0) {
				current = current.left;
				if (current == null) {
					parent.left = newNode;
					parent.parent = parent;
					size++;
					System.out.println("Success");
					return;
				}
			} else if (word.compareTo(current.val) > 0) {
				current = current.right;
				if (current == null) {
					parent.right = newNode;
					parent.parent = parent;
					size++;
					System.out.println("Success");
					return;
				}
			} else {
				// Word already exists, do not insert duplicates
				System.out.println("Word already exists");
				return;
			}
		}
	}
	
	/**
	 * Check if a word exists and remove it from the BST
	 * If it exists, return the Node containing the word
	 * @param word
	 * @return Node containing the word, or null if it doesn't exist
	 */
	
	public Node checkWord(String word) {
		Node current = root;
		while (current != null) {
			if (word.compareTo(current.val) == 0) {
				// word found need to remove it and handle children
				removeNode(current);
				return current;
			} else if (word.compareTo(current.val) < 0) {
				current = current.left;
			} else {
				current = current.right;
			}
			
			if (current == null) {
				System.out.println("Word not found");
				return null;
			}
		}
		return null;
	}
	
	/**
	 * Remove a node from the BST and handle its children
	 * @param node
	 * @return
	 */
	private Node removeNode(Node node) {
		// Case 1: Node has no children (leaf node)
		if (node.left == null && node.right == null) {
			if (node == root) {
				root = null;
			} else if (node == node.parent.left) {
				node.parent.left = null;
			} else {
				node.parent.right = null;
			}
		}
		// Case 2: Node has one child
		else if (node.left == null || node.right == null) {
			Node child = (node.left != null) ? node.left : node.right;
			if (node == root) {
				root = child;
			} else if (node == node.parent.left) {
				node.parent.left = child;
			} else {
				node.parent.right = child;
			}
			child.parent = node.parent;
		}
		// Case 3: Node has two children
		else {
			Node successor = getMin(node.right);
			node.val = successor.val; // Copy the successor's value to the node
			removeNode(successor); // Recursively remove the successor
		}
		size--;
		System.out.println("Success");
		return node;
	}
	
	/**
	 * Gets the minimum node in a subtree, used to get successor when removing a node with children
	 * @param node
	 * @return
	 */
	private Node getMin(Node node) {
		while (node.left != null) {
			node = node.left;
		}
		return node;
	}
	
	
	public int getSize() {
		return size;
	}
	
	public Node getNode(String word) {
		Node current = root;
		while (current != null) {
			if (word.compareTo(current.val) == 0) {
				return current;
			}else if (word.compareTo(current.val) < 0) {
				current = current.left;
			}else {
				current = current.right;
			}
		}
		System.out.println("--------------------------------------\nERROR: Node not found");
		return null;
	}
	
	public void inOrder() {
		inOrderHelper(root);
	}
	
	/**
	 * Helper method for in-order traversal
	 * Prints elements in sorted order (left -> root -> right)
	 * @param node current node to process
	 */
	private void inOrderHelper(Node node) {
		if (node != null) {
			inOrderHelper(node.left);   // Visit left subtree
			System.out.print(node.val + " ");  // Print current node
			inOrderHelper(node.right);  // Visit right subtree
		}
	}
}
