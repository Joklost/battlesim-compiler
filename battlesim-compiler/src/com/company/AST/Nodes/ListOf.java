package com.company.AST.Nodes;

/**
 * Created by joklost on 12-04-16.
 */
public class ListOf extends TypeIdentifier {
    public TypeName typeName;
    
    public ListOf(TypeName tn, int ln) {
        super(ln);
        this.typeName = tn;
    }
}
