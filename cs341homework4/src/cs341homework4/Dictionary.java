package cs341homework4;

public class Dictionary {
	
	private BST bst;
	
	public Dictionary() {
		this.bst = new BST();
	}
	
	public void insert(String word) {
		// break words by spaces and insert each word
		String[] words = word.split(" ");
		for (String w : words) {
			bst.insertWordNode(w);
		}
	}
	
	public String contains(String word) {
		if (bst.getNode(word) != null) {
			return "Found";
		}else {
			bst.insertWordNode(word);
			return "Not found, adding " + word + " to dictionary.";
		}
	}
	
	public String delete(String word) {
		if (bst.checkWord(word) != null) {
			return "Deleted " + word;
		}else {
			return "Word not found.";
		}
	}
	
	public int size() {
		return bst.getSize();
	}
	
	public void print() {
		bst.inOrder();
	}

}
