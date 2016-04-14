package com.company.AST.Nodes;

import com.company.AST.Visitor.Visitable;
import com.company.AST.Visitor.Visitor;

import java.util.HashMap;

import static com.company.ContextualAnalysis.TypeConsts.noType;

/**
 * Created by joklost on 01-04-16.
 */
public abstract class ASTNode implements Visitable {

    //public List<ASTNode> children;

    protected int lineNumber;
    public int type = noType;

    public ASTNode(int ln) {
        this.lineNumber = ln + 1;
        //this.children = new ArrayList<>();
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void accept(Visitor v) { v.dispatch(this); }

    public static HashMap<String, Integer> symTable = new HashMap<>();
}
