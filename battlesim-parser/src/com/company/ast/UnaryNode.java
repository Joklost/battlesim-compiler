package com.company.ast;

/**
 * Created by joklost on 3/10/16.
 */
public abstract class UnaryNode extends Node {
    Node child;
    protected UnaryNode(Node child) {
        this.child = child;
    }

    @Override
    public void printPreorderDfs() {
        print();
        child.printPreorderDfs();
    }

    @Override
    public void printInorderDfs() {
        child.printInorderDfs();
        print();
    }

    @Override
    public void printPostorderDfs() {
        child.printPostorderDfs();
        print();
    }
}
