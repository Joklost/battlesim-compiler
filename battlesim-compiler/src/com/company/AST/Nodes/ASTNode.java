package com.company.AST.Nodes;

import com.company.AST.Visitor.Visitable;
import com.company.AST.Visitor.Visitor;

import java.util.HashMap;

/**
 * Created by joklost on 01-04-16.
 */
public abstract class ASTNode implements Visitable {
    protected int lineNumber;
    public ASTNode(int ln) {
        this.lineNumber = ln;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void accept(Visitor v) { v.dispatch(this); }

    public static HashMap<String, Integer> symTable = new HashMap<>();
}
