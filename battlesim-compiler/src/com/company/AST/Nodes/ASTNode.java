package com.company.AST.Nodes;

import com.company.AST.Visitor.Visitable;
import com.company.AST.Visitor.Visitor;
import com.company.SyntaxAnalysis.Preprocessor;

import static com.company.ContextualAnalysis.HelperClasses.TypeConsts.noType;

/**
 * Created by joklost on 01-04-16.
 */
public abstract class ASTNode implements Visitable {

    private int lineNumber;
    public int type = noType;

    protected ASTNode(int ln) {
        this.lineNumber = ln + 1;
    }

    public int getLineNumber() {
        return lineNumber - Preprocessor.STDLIB_LINES;
    }

    public void accept(Visitor v) { v.dispatch(this); }

}
