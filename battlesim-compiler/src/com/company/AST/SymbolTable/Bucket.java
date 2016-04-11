package com.company.AST.SymbolTable;

import com.company.AST.Nodes.ASTNode;

/**
 * Created by joklost on 11-04-16.
 *
 * http://web.cecs.pdx.edu/~harry/compilers/p6/SymbolTable.java
 *
 */
public class Bucket {
    private String id;
    private ASTNode def;
    private int hashVal;
    private int scope;
    private Bucket next;
    private Bucket slink;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ASTNode getDef() {
        return def;
    }

    public void setDef(ASTNode def) {
        this.def = def;
    }

    public int getHashVal() {
        return hashVal;
    }

    public void setHashVal(int hashVal) {
        this.hashVal = hashVal;
    }

    public int getScope() {
        return scope;
    }

    public void setScope(int scope) {
        this.scope = scope;
    }

    public Bucket getNext() {
        return next;
    }

    public void setNext(Bucket next) {
        this.next = next;
    }

    public Bucket getSlink() {
        return slink;
    }

    public void setSlink(Bucket slink) {
        this.slink = slink;
    }
}
