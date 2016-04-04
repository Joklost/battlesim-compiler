package com.company.ast;

/**
 * Created by joklost on 3/10/16.
 */
public abstract class BinaryNode extends Node {
    Node left, right;
    protected BinaryNode(Node left, Node right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public void printPreorderDfs() {
        print();
        left.printPreorderDfs();
        right.printPreorderDfs();
    }

    @Override
    public void printInorderDfs() {
        left.printInorderDfs();
        print();
        right.printInorderDfs();
    }

    @Override
    public void printPostorderDfs() {
        left.printPreorderDfs();
        right.printPostorderDfs();
        print();
    }
}
