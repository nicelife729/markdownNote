package com.nicelife729.algo.trees.test;

import com.nicelife729.algo.trees.checkbalancedtree.CheckBalancedTree;
import com.nicelife729.algo.trees.model.BinarySearchTree;
import com.nicelife729.algo.trees.model.BinarySearchTreeImpl;

/**
 * Test class for balanced tree.
 * User: rpanjrath
 * Date: 10/4/13
 * Time: 3:30 PM
 */
public class TestBalancedTree {

    public static void main(String args[]) {
        BinarySearchTree binarySearchTree = new BinarySearchTreeImpl();
        binarySearchTree.insertNode(2);
        binarySearchTree.insertNode(1);
        binarySearchTree.insertNode(3);
        binarySearchTree.insertNode(4);
        binarySearchTree.insertNode(6);
        binarySearchTree.insertNode(5);
        System.out.println(CheckBalancedTree.isTreeBalanced(binarySearchTree.getRootOfTree()));
    }
}
