package com.company.AST;

/**
 * Created by joklost on 01-04-16.
 */
public class TypeList extends TypeIdentifier {
    public TypeIdentifier typeIdentifier;

    public TypeList(TypeIdentifier tid, int ln) {
        super(ln);
        this.typeIdentifier = tid;
    }
}
