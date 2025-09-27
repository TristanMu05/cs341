package tester;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import cs341homework4.Dictionary;
import cs341homework4.BST;
import cs341homework4.Node;

import java.util.HashSet;
import java.util.Set;

class TestDictionary {

	@Test
	void testDictionaryBST() {
		String sampleText = "Here is the perfect system for cleaning your room\n"
				+ "First move all of the items that do not have a proper place to the center of the\n"
				+ "room\n"
				+ "Get rid of at least five things that you have not used within the last year\n"
				+ "Take out all of the trash and place all of the dirty dishes in the kitchen sink\n"
				+ "Now find a location for each of the items you had placed in the center of the room\n"
				+ "For any remaining items see if you can squeeze them in under your bed or stuff\n"
				+ "them into the back of your closet\n"
				+ "See that was easy\n";

		Dictionary dict = new Dictionary();
		dict.insert(sampleText);

		// 1. Unique words count
		String[] words = sampleText.replace("\n", " ").split(" ");
		Set<String> uniqueWords = new HashSet<>();
		for (String w : words) {
			if (!w.isBlank()) uniqueWords.add(w);
		}
		assertEquals(uniqueWords.size(), dict.size(), "BST size should match unique word count");

		// 2. All words are found
		for (String w : uniqueWords) {
			assertEquals("Found", dict.contains(w), "Word should be found after insertion: " + w);
		}

		// 3. Word not in text
		String notInText = "unicorn";
		assertEquals("Not found, adding unicorn to dictionary.", dict.contains(notInText));
		assertEquals("Found", dict.contains(notInText));

		// 4. Delete a word
		String toDelete = "perfect";
		assertEquals("Deleted perfect", dict.delete(toDelete));
		assertEquals("Word not found.", dict.delete(toDelete));
		assertEquals(uniqueWords.size(), dict.size() - 1, "BST size should decrease after deletion");

		// 5. BST properties: no cycles, child pointers consistent
		BST bst = getBST(dict);
		assertTrue(checkNoCycles(bst), "BST should not have cycles");
		assertTrue(checkChildPointers(bst), "BST child pointers should be consistent");

		System.out.println("Successful");
	}

	// Helper to access BST (reflection, since bst is private)
	private BST getBST(Dictionary dict) {
		try {
			java.lang.reflect.Field bstField = dict.getClass().getDeclaredField("bst");
			bstField.setAccessible(true);
			return (BST) bstField.get(dict);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	// Check for cycles using DFS
	private boolean checkNoCycles(BST bst) {
		Set<Node> visited = new HashSet<>();
		return dfsNoCycle(getRoot(bst), visited, null);
	}
	private boolean dfsNoCycle(Node node, Set<Node> visited, Node parent) {
		if (node == null) return true;
		if (visited.contains(node)) return false;
		visited.add(node);
		if (node.parent != parent) return false;
		return dfsNoCycle(node.left, visited, node) && dfsNoCycle(node.right, visited, node);
	}

	// Check child pointers
	private boolean checkChildPointers(BST bst) {
		return dfsChildPointers(getRoot(bst));
	}
	private boolean dfsChildPointers(Node node) {
		if (node == null) return true;
		if (node.left != null && node.left.val.compareTo(node.val) >= 0) return false;
		if (node.right != null && node.right.val.compareTo(node.val) <= 0) return false;
		return dfsChildPointers(node.left) && dfsChildPointers(node.right);
	}

	// Helper to access root (reflection)
	private Node getRoot(BST bst) {
		try {
			java.lang.reflect.Field rootField = bst.getClass().getDeclaredField("root");
			rootField.setAccessible(true);
			return (Node) rootField.get(bst);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}