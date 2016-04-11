package com.company.AST.SymbolTable;

/**
 * Created by joklost on 11-04-16.
 *
 * http://web.cecs.pdx.edu/~harry/compilers/p6/SymbolTable.java
 *
 */
public class Scope {
    private Bucket slink;
    private Scope next;

    public Bucket getSlink() {
        return slink;
    }

    public void setSlink(Bucket slink) {
        this.slink = slink;
    }

    public Scope getNext() {
        return next;
    }

    public void setNext(Scope next) {
        this.next = next;
    }
}
