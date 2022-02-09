package edu.frostburg.cosc310.RyanBrisbane.AVLTreeProject;

import edu.frostburg.cosc310.projects.avlnames.AVLTreeProject;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Driver class for the AVL Tree Project
 * Ryan Brisbane - COSC 310
 * October 29, 2021 - 12:22 PM
 */
public class RBAVLTreeProject extends AVLTree implements AVLTreeProject {

    /**
     * Method for inserting new node into tree
     * @param name The key value (person's short name)
     * @param interests associated with that key
     */
    public void insert(String name, String... interests) {
        add(name, interests);
    }

    /**
     * Method that calls the AVL tree's dedicated search function to find a given key/value pair in the tree
     * @param name The search key
     * @return true if key/value is found (as well as the values), false if not
     */
    public boolean search(String name) {
        Node query = Find(name);
        if (query == null) {
            return false;
        } else {
            System.out.println(toString(query.key));
            return true;
        }
    }

    /**
     * Method that prints an in order traversal of the values stored in the tree
     */
    public void printInorder() {
        inOrderTraversal();
    }

    /**
     * method that prints a preorder traversal of the values stored in the tree
     */
    public void printPreorder() {
        preOrderTraversal();
    }

    /**
     * method that prints a post order traversal of the values stored in the tree
     */
    public void printPostorder() {
        postOrderTraversal();
    }

    /**
     * method that prints the project name and author (me! mwahahahaha)
     */
    public void who() {
        System.out.println("AVL Tree Project - Ryan Brisbane");
    }

    /**
     * method that prints a list of possible commands the program will accept
     */
    public void menu() {
        System.out.println("search XYZ (XYZ is the name to find)");
        System.out.println("add XYZ INTERESTA INTERESTB...(XYZ is name, followed by however many interests");
        System.out.println("inorder, preorder, postorder (prints the associated traversal)");
        System.out.println("who (prints the project name and author)");
        System.out.println("? (prints this list of commands)");
        System.out.println("exit (terminates the program)\n");
    }

    /**
     * method that exits the program (after printing a lovely goodbye)
     */
    public void exit() {
        System.out.println("Thank you and have a nice day!");
        System.exit(0);
    }

    /**
     * the main method. contains all the stuff that creates the actual tree (with data read from the provided .csv file),
     * initiates the do/while loop that accepts user input to access and alter the tree.
     * @param args
     */
    public static void main(String[] args) {
        RBAVLTreeProject interests = new RBAVLTreeProject(); //the tree

        List<List<String>> inFile = new ArrayList<>();
        try {
            //reading the file and accessing the data - each row is stored as a sublist
            //for some reason it gets angry when I just give it the file name instead of the true pathway. not sure how to fix that
            Scanner read = new Scanner(new File("/Users/rpbris11/IdeaProjects/RBAVLTreeProject/src/edu/frostburg/cosc310/RyanBrisbane/AVLTreeProject/class_interests.csv"));
            while (read.hasNextLine()) {
                inFile.add(interests.getLines(read.nextLine()));
            }
        } catch (IOException e) {
            System.err.println(e);
        }
        //store the column headers so we can appropriately store the interests (as written in the csv) in the tree
        List<String> columnHeaders = inFile.get(0);
        inFile.remove(0);

        List<String> temp = new ArrayList<>();
        int i = 2;
        while (i < 22) {
            if (inFile.get(0).get(i).equals("1")) {
                temp.add(columnHeaders.get(i)); //if the value is 1, that interest is stored in the node
            }
            i++;
            //I created the root separately just for safety. iteration has messed me up before
            interests.root = new Node(inFile.get(0).get(1), temp.toArray(new String[temp.size()])); //creates the actual node
            inFile.remove(0);

            for (List<String> l : inFile) {
                List<String> tempInterests = new ArrayList<>();
                i = 2;
                while (i < 22) {
                    if (l.get(i).equals("1")) {
                        tempInterests.add(columnHeaders.get(i));
                    }
                    i++;
                }
                //here was the error I found - I dropped the .root so it wasn't working it's way through the tree. The height
                //was never over 1 because it never updated it for the higher nodes, so it never saw a need to balance it.
                //the debugger was my BEST friend here. looping through was a huge help
                interests.root = interests.add(l.get(1), tempInterests.toArray(new String[22])); //java dark magic
                //it's not actually dark magic. if I don't initialize that array it throws a null pointer exception
            }
            //user input stage begins here
            Scanner in = new Scanner(System.in);
            String[] tokens; //because it can take input like XYZ INTERESTA INTERESTB, I had to find a way to read values separated by
            //a space. using the .split method and storing each value in a String array does this nicely
            System.out.print("Hello! ");
            do {
                System.out.print("Please enter a command (? to see the list): ");
                String input = in.nextLine();
                tokens = input.split(" "); //java voodoo
                //searches for provided key value
                if (tokens[0].equals("search")) {
                    interests.search(tokens[1]);
                }
                //inserts provided key/value pair as new node into tree
                else if (tokens[0].equals("add")) {
                    String k = tokens[1];
                    String[] ints = new String[tokens.length - 2];
                    int z;
                    for (z = 0; z < ints.length; z++) {
                        ints[z] = tokens[z + 2];
                    }
                    interests.insert(k, ints);
                }
                //prints in order traversal
                else if (tokens[0].equals("inorder")) {
                    interests.printInorder();
                }
                //prints pre order traversal
                else if (tokens[0].equals("preorder")) {
                    interests.printPreorder();
                }
                //prints post order traversal
                else if (tokens[0].equals("postorder")) {
                    interests.printPostorder();
                }
                //prints name of project and author (yours truly)
                else if (tokens[0].equals("who")) {
                    interests.who();
                }
                //prints list of acceptable commands
                else if (tokens[0].equals("?")) {
                    interests.menu();
                }
                //prompts user to reenter if it doesn't recognize the command as one of the above
                else if (!tokens[0].equals("search") && !tokens[0].equals("add") &&
                        !tokens[0].equals("inorder") && !tokens[0].equals("preorder") && !tokens[0].equals("postorder")
                        && !tokens[0].equals("who") && !tokens[0].equals("?") && !tokens[0].equals("exit")) {
                    System.out.print("Input not recognized. Try again: ");
                }
            } while (!tokens[0].equals("exit")); //exit condition
            interests.exit();
        }
    }
}
