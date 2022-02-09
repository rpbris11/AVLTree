package edu.frostburg.cosc310.RyanBrisbane.AVLTreeProject;

/**
 * The Node class - sets the groundwork for everything else. Stores the key value, list of interests, the height of
 * this node's subtree, and the left and right children.
 */
public class Node {

    String key;
    String[] interests;
    int height;
    Node left;
    Node right;

    /**
     * default constructor - creating a new node
     * @param k - key value (name)
     * @param ints - provided interests to be stored within the node
     */
    public Node(String k, String...ints){
        key = k;
        int i = 0;
        interests = new String[ints.length];
        for(String s: ints){

            interests[i] = s;
            i++;
        }

        left = null;
        right = null;

        height = 1;
    }


}
