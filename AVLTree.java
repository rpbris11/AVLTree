package edu.frostburg.cosc310.RyanBrisbane.AVLTreeProject;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * AVLTree class - all the associated methods for accessing, inserting, and balancing nodes
 */
public class AVLTree {
    Node root;
    int count;

    /**
     * default constructor - sets number of nodes (at start) to zero
     */
    public AVLTree(){
        count = 0;
    }

    /**
     * method for calculating height of tree starting at root
     * @return height of the whole tree from the root
     */
    public int getHeight() {
        return getHeight(root);
    }

    /**
     * method returns height of subtree starting at given node
     * @param n - node to find height of
     * @return height of given node
     */
    int getHeight(Node n){
        if (n == null) return 0;
        return n.height;
    }

    /**
     * calculates balance of subtree starting at node n
     * @param n - provided node
     * @return balance factor of given node
     */
    int getBalance(Node n){
        if(n == null) return 0;
        return getHeight(n.left) - getHeight(n.right);
    }

    /**
     * @return number of nodes in the tree
     */
    int getCount(){
        return count;
    }

    /**
     * method takes key/value pair and prints it in a pretty format
     * @param key - provided name to access node
     * @return String - nicely formatted string containing name and interests
     */
    public String toString(String key){
        Node n = Find(key);
        String r = "";
        r += key + " likes ";
        for(int i = 0; i < n.interests.length; i++){
            if(n.interests[i] == null){
                break;
            }
            r += n.interests[i];
            if(i!=n.interests.length-1 && n.interests[i+1] != null){
                r += " and "; //super backwards way to format it nicely :) I love java dark magic
            }
        }
        r += ".";
        return r;
        }

    /**
     * Performs a right rotation on the provided node
     * @param y - provided node to rotate
     * @return node that takes y's place
     */
    Node rotateRight(Node y) {
        Node x = y.left;
        Node z = x.right;
        x.right = y;
        y.left = z;
        //update the heights appropriately
        y.height = Math.max(getHeight(y.left), getHeight(y.right)) + 1;
        x.height = Math.max(getHeight(x.left), getHeight(x.right)) + 1;
        return x;
    }

    /**
     * Performs a left rotation on the given node
     * @param y - provided node
     * @return node that takes y's place
     */
    Node rotateLeft(Node y) {
        Node x = y.right;
        Node z = x.left;
        x.left = y;
        y.right = z;
        //update the heights of each node
        y.height = Math.max(getHeight(y.left), getHeight(y.right)) + 1;
        x.height = Math.max(getHeight(x.left), getHeight(x.right)) + 1;
        return x;
    }
        /* an old attempt at a rebalancing method - not necessary but thought it might be
         * worth looking at. maybe. who knows
    Node rebalance(Node node) {
        int balanceFactor = getBalance(node);
        if (balanceFactor > 1) {
            if (node.key.compareTo(node.left.key) < 0) {
                return rotateRight(node);
            } else if (node.key.compareTo(node.left.key) > 0) {
                node.left = rotateLeft(node.left);
                return rotateRight(node);
            }
        }
        if (balanceFactor < -1) {
            if (node.key.compareTo(node.right.key) > 0) {
                return rotateLeft(node);
            } else if (node.key.compareTo(node.right.key) < 0) {
                node.right = rotateRight(node.right);
                return rotateLeft(node);
            }
        }
        return node;
    }
         */

    /**
     * overloaded method - calls the subsequent method to insert a new node into the tree
     * @param key - name to store/access new node
     * @param interests - values to store within the new node
     * @return new node with key/interests pair
     */
     public Node add(String key, String...interests){
        count++;
        return add(root, key, interests);
    }

    /**
     * The real insertion method - I changed it from insert() because it doesn't like that with the interface in
     * the main class. Method inserts new node with provided key/interests
     * @param node - subtree to iterate through and add new node into
     * @param key - key to access/store new node
     * @param interests - values to store within node
     * @return new node with key/interest pairing
     */
    Node add(Node node, String key, String...interests) {
        if (node == null) {
            return new Node(key, interests);
        } if (key.compareTo(node.key) < 0) {
            node.left = add(node.left, key, interests); //recursion time!
        } else if (key.compareTo(node.key) > 0) {
            node.right = add(node.right, key, interests);
        } else {
            node.interests = interests;
            return node;
        }
        //node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));
        //return rebalance(node);

        //rebalancing occurs here instead of within its own method - since there's no other
        //method that calls for rebalancing I figured it was fine to leave it here
        node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));
        int balanceFactor = getBalance(node);
        if (balanceFactor > 1) {
            if (key.compareTo(node.left.key) < 0) {
                return rotateRight(node);
            } else if (key.compareTo(node.left.key) > 0) {
                node.left = rotateLeft(node.left);
                return rotateRight(node);
            }
        }
        if (balanceFactor < -1) {
            if (key.compareTo(node.right.key) > 0) {
                return rotateLeft(node);
            } else if (key.compareTo(node.right.key) < 0) {
                node.right = rotateRight(node.right);
                return rotateLeft(node);
            }
        }
        return node;
    }

    /**
     * Another overloaded method - searches for a node based on the provided key
     * @param key - provided value to look for within the tree
     * @return node in question if found, null if not
     */
    public Node Find(String key){
        if (key == null) throw new IllegalArgumentException("argument to Find() is null");
        Node n = find(root, key);
        if (n == null) return null;
        return n;
    }

    /**
     * The true search method - finds a node based on the provided key value within the subtree n
     * @param n - subtree to search through
     * @param key - value to search for
     * @return node in question if found, null if not
     */
    private Node find(Node n, String key) {
        if (n == null) return null;
        int cmp = key.compareTo(n.key);
        if (cmp < 0) return find(n.left, key);
        else if (cmp > 0) return find(n.right, key);
        else return n;
    }

    /**
     * Overloaded method - performs in order traversal starting at the root
     */
    void inOrderTraversal(){
        inOrder(root);
    }

    /**
     * performs in order traversal starting at the given node
     * @param node - node to start from in the traversal
     */
    void inOrder(Node node){
        if(node == null){
            return;
        }
        inOrder(node.left);
        System.out.println(toString(node.key));
        inOrder(node.right);
    }

    /**
     * calls for preorder traversal starting at the root
     */
    public void preOrderTraversal(){
        preOrder(root);
    }

    /**
     * pre order traversal starting at the given node
     * @param node - the beginning of the traversal
     */
    void preOrder(Node node){
        if(node == null) {
            return;
        }
        System.out.println(toString(node.key));
        preOrder(node.left);
        preOrder(node.right);
    }

    /**
     * post order traversal starting at the root
     */
    public void postOrderTraversal(){
        postOrder(root);
    }

    /**
     * post order traversal beginning at the provided node
     * @param node - beginning of subtree to traverse
     */
    void postOrder(Node node){
        if(node == null) {
            return;
        }
        postOrder(node.left);
        postOrder(node.right);
        System.out.println(toString(node.key));
    }

    /**
     * this method solely exists for the purpose of reading values from the csv file. pay it no mind
     * @param line
     * @return List<String> of the entries for each row in the csv file. these Lists are store
     * within another List
     */
    public List<String> getLines(String line){
        List<String> values = new ArrayList<>();
        try(Scanner rowReader = new Scanner(line)){
            rowReader.useDelimiter(",");
            while(rowReader.hasNext()){
                values.add(rowReader.next());
            }
        }
        return values;
    }

}
