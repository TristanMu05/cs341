#!/usr/bin/env python3
"""
BST Dictionary Visualizer
A Python application that visually displays a Binary Search Tree dictionary
with the ability to add and delete nodes.
"""

import tkinter as tk
from tkinter import messagebox, simpledialog
import math


class Node:
    """Node class for the Binary Search Tree"""
    def __init__(self, value):
        self.value = value
        self.left = None
        self.right = None
        self.parent = None


class BST:
    """Binary Search Tree implementation similar to the Java version"""
    
    def __init__(self):
        self.root = None
        self.size = 0
    
    def insert(self, word):
        """Insert a new word into the BST"""
        if not word:
            return False
            
        new_node = Node(word)
        if self.root is None:
            self.root = new_node
            self.size += 1
            return True
        
        current = self.root
        while True:
            if word < current.value:
                if current.left is None:
                    current.left = new_node
                    new_node.parent = current
                    self.size += 1
                    return True
                current = current.left
            elif word > current.value:
                if current.right is None:
                    current.right = new_node
                    new_node.parent = current
                    self.size += 1
                    return True
                current = current.right
            else:
                # Word already exists
                return False
    
    def search(self, word):
        """Search for a word in the BST"""
        current = self.root
        while current:
            if word == current.value:
                return current
            elif word < current.value:
                current = current.left
            else:
                current = current.right
        return None
    
    def delete(self, word):
        """Delete a word from the BST"""
        node = self.search(word)
        if node is None:
            return False
        
        self._remove_node(node)
        self.size -= 1
        return True
    
    def _remove_node(self, node):
        """Remove a node from the BST and handle its children"""
        # Case 1: Node has no children (leaf node)
        if node.left is None and node.right is None:
            if node == self.root:
                self.root = None
            elif node == node.parent.left:
                node.parent.left = None
            else:
                node.parent.right = None
        
        # Case 2: Node has one child
        elif node.left is None or node.right is None:
            child = node.left if node.left else node.right
            if node == self.root:
                self.root = child
                child.parent = None
            elif node == node.parent.left:
                node.parent.left = child
                child.parent = node.parent
            else:
                node.parent.right = child
                child.parent = node.parent
        
        # Case 3: Node has two children
        else:
            successor = self._get_min(node.right)
            node.value = successor.value
            self._remove_node(successor)
    
    def _get_min(self, node):
        """Get the minimum node in a subtree"""
        while node.left:
            node = node.left
        return node
    
    def in_order(self):
        """Return list of values in in-order traversal"""
        result = []
        self._in_order_helper(self.root, result)
        return result
    
    def _in_order_helper(self, node, result):
        """Helper method for in-order traversal"""
        if node:
            self._in_order_helper(node.left, result)
            result.append(node.value)
            self._in_order_helper(node.right, result)


class BSTVisualizer:
    """GUI application to visualize the BST"""
    
    def __init__(self):
        self.root = tk.Tk()
        self.root.title("BST Dictionary Visualizer")
        self.root.geometry("1000x700")
        self.root.configure(bg='white')
        
        self.bst = BST()
        self.node_positions = {}
        
        self.setup_ui()
        
        # Add some initial words for demonstration
        initial_words = ["perfect", "system", "cleaning", "room", "center", "trash", "items"]
        for word in initial_words:
            self.bst.insert(word)
        
        self.draw_tree()
    
    def setup_ui(self):
        """Set up the user interface"""
        # Control frame
        control_frame = tk.Frame(self.root, bg='lightgray', height=100)
        control_frame.pack(fill=tk.X, padx=10, pady=5)
        control_frame.pack_propagate(False)
        
        # Add word section
        tk.Label(control_frame, text="Add Word:", bg='lightgray', font=('Arial', 12)).pack(side=tk.LEFT, padx=5)
        self.add_entry = tk.Entry(control_frame, font=('Arial', 12), width=20)
        self.add_entry.pack(side=tk.LEFT, padx=5)
        self.add_entry.bind('<Return>', lambda e: self.add_word())
        
        tk.Button(control_frame, text="Add", command=self.add_word, bg='lightgreen', 
                 font=('Arial', 12)).pack(side=tk.LEFT, padx=5)
        
        # Delete word section
        tk.Label(control_frame, text="Delete Word:", bg='lightgray', font=('Arial', 12)).pack(side=tk.LEFT, padx=20)
        self.delete_entry = tk.Entry(control_frame, font=('Arial', 12), width=20)
        self.delete_entry.pack(side=tk.LEFT, padx=5)
        self.delete_entry.bind('<Return>', lambda e: self.delete_word())
        
        tk.Button(control_frame, text="Delete", command=self.delete_word, bg='lightcoral', 
                 font=('Arial', 12)).pack(side=tk.LEFT, padx=5)
        
        # Clear tree button
        tk.Button(control_frame, text="Clear Tree", command=self.clear_tree, bg='orange', 
                 font=('Arial', 12)).pack(side=tk.LEFT, padx=20)
        
        # Canvas for drawing the tree
        self.canvas = tk.Canvas(self.root, bg='white', width=980, height=550)
        self.canvas.pack(padx=10, pady=5)
        
        # Status frame
        status_frame = tk.Frame(self.root, bg='lightgray', height=50)
        status_frame.pack(fill=tk.X, padx=10, pady=5)
        status_frame.pack_propagate(False)
        
        self.status_label = tk.Label(status_frame, text="BST Dictionary Visualizer - Ready", 
                                   bg='lightgray', font=('Arial', 10))
        self.status_label.pack(side=tk.LEFT, padx=10, pady=10)
        
        self.size_label = tk.Label(status_frame, text="Nodes: 0", 
                                 bg='lightgray', font=('Arial', 10))
        self.size_label.pack(side=tk.RIGHT, padx=10, pady=10)
    
    def add_word(self):
        """Add a word to the BST"""
        word = self.add_entry.get().strip().lower()
        if not word:
            messagebox.showwarning("Warning", "Please enter a word to add.")
            return
        
        if self.bst.insert(word):
            self.status_label.config(text=f"Successfully added '{word}'")
            self.add_entry.delete(0, tk.END)
            self.draw_tree()
        else:
            self.status_label.config(text=f"Word '{word}' already exists in the tree")
            messagebox.showinfo("Info", f"Word '{word}' already exists in the tree.")
    
    def delete_word(self):
        """Delete a word from the BST"""
        word = self.delete_entry.get().strip().lower()
        if not word:
            messagebox.showwarning("Warning", "Please enter a word to delete.")
            return
        
        if self.bst.delete(word):
            self.status_label.config(text=f"Successfully deleted '{word}'")
            self.delete_entry.delete(0, tk.END)
            self.draw_tree()
        else:
            self.status_label.config(text=f"Word '{word}' not found in the tree")
            messagebox.showinfo("Info", f"Word '{word}' not found in the tree.")
    
    def clear_tree(self):
        """Clear all nodes from the tree"""
        if messagebox.askyesno("Confirm", "Are you sure you want to clear the entire tree?"):
            self.bst = BST()
            self.draw_tree()
            self.status_label.config(text="Tree cleared")
    
    def calculate_positions(self, node, x, y, x_offset):
        """Calculate positions for all nodes in the tree"""
        if node is None:
            return
        
        self.node_positions[node] = (x, y)
        
        if node.left:
            self.calculate_positions(node.left, x - x_offset, y + 80, x_offset // 2)
        
        if node.right:
            self.calculate_positions(node.right, x + x_offset, y + 80, x_offset // 2)
    
    def draw_tree(self):
        """Draw the entire BST on the canvas"""
        self.canvas.delete("all")
        self.node_positions = {}
        
        if self.bst.root is None:
            self.canvas.create_text(490, 275, text="Empty Tree", 
                                  font=('Arial', 16), fill='gray')
            self.size_label.config(text="Nodes: 0")
            return
        
        # Calculate positions for all nodes
        self.calculate_positions(self.bst.root, 490, 50, 200)
        
        # Draw edges first
        self._draw_edges(self.bst.root)
        
        # Draw nodes
        self._draw_nodes()
        
        # Update size label
        self.size_label.config(text=f"Nodes: {self.bst.size}")
        
        # Display in-order traversal
        in_order_result = self.bst.in_order()
        traversal_text = "In-order: " + " -> ".join(in_order_result)
        self.canvas.create_text(490, 520, text=traversal_text, 
                              font=('Arial', 12), fill='blue')
    
    def _draw_edges(self, node):
        """Draw edges between nodes"""
        if node is None:
            return
        
        x, y = self.node_positions[node]
        
        if node.left:
            left_x, left_y = self.node_positions[node.left]
            self.canvas.create_line(x, y, left_x, left_y, width=2, fill='black')
            self._draw_edges(node.left)
        
        if node.right:
            right_x, right_y = self.node_positions[node.right]
            self.canvas.create_line(x, y, right_x, right_y, width=2, fill='black')
            self._draw_edges(node.right)
    
    def _draw_nodes(self):
        """Draw all nodes as circles with text"""
        for node, (x, y) in self.node_positions.items():
            # Draw circle
            radius = 25
            self.canvas.create_oval(x-radius, y-radius, x+radius, y+radius, 
                                  fill='lightblue', outline='black', width=2)
            
            # Draw text
            self.canvas.create_text(x, y, text=node.value, 
                                  font=('Arial', 10, 'bold'), fill='black')
    
    def run(self):
        """Start the GUI application"""
        self.root.mainloop()


if __name__ == "__main__":
    # Create and run the BST visualizer
    app = BSTVisualizer()
    app.run()