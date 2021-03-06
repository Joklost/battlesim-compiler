package com.company.AST.Nodes;

import com.company.ContextualAnalysis.HelperClasses.TypeDescriptor;

/**
 * Created by joklost on 13-04-16.
 */
public abstract class IdentifierReferencing extends ASTNode {
    public String name;
    public IdentifierReferencing(int ln) {
        super(ln);
    }

    public TypeDescriptor typeDescriptor;

}
