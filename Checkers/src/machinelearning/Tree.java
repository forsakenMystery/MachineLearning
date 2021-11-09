/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package machinelearning;

import utility.Entity;

/**
 *
 * @author Hamed Khashehchi
 */
public class Tree {
    private Node root;

    public Tree() {
        this.root = new Node();
    }

    public Node getRoot() {
        return root;
    }
    
    
}
