package edu.frostburg.cosc310.projects.avlnames;

/**
 * The interface for the AVL Tree project.  DON'T ALTER THIS INTERFACE.
 * 
 * This interface declares required methods for this project.  When your project begins
 * 		execution, it should create a new instance of your version of this class.  This class
 * 		will refer to your AVL Tree to complete its associated methods.  You must implement
 * 		all of these methods, but you may also implement any other methods that you find useful.
 * 
 * @author Steve K
 *
 */
public interface AVLTreeProject {

	/* AVL Tree stuff ----------------- */
	
	/**
	 * Insert a new item into your AVL Tree.  Since the data is all Strings, we can stick
	 * 		to that type.
	 * @param name The key value
	 * @param interets A list of 0 or more interests.
	 */
	public void insert(String name, String... interets);
	
	/**
	 * Find a name and print out their associated interests.
	 * @param name The search key
	 * @return true if something was found
	 */
	public boolean search(String name);
	
	/* The following methods demonstrate a type of BST traversal */
	
	/**
	 * Inorder traversal, printing all the names and interests.
	 */
	public void printInorder();
	
	/**
	 * Preorder traversal, printing all the names and interests.
	 */
	public void printPreorder();
	
	/**
	 * Postorder traversal, printing all the names and interests.
	 */
	public void printPostorder();
	
	/* General project stuff ---------- */
	
	/* Prints out your name */
	public void who();
	
	/* Prints out a list of the commands */
	public void menu();
	
	/* 
	 * Exits the program.  Your program should run in a loop until
	 * the user requests to exit the program.  Provide a good-bye message
	 * and exit.
	 */
	public void exit();
	
}
