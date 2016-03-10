package com.company.ast;

import com.sun.org.apache.xpath.internal.SourceTree;
import com.sun.xml.internal.bind.annotation.OverrideAnnotationOf;

/**
 * Created by joklost on 3/10/16.
 */
public abstract class Node {
    public abstract void printPreorderDfs();
    public abstract void printInorderDfs();
    public abstract void printPostorderDfs();

    protected void print() {
        print(this.toString());
    }

    protected void print(String value) {
        System.out.println(value + " ");
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
