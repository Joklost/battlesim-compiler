package com.company.AST;

/**
 * Created by joklost on 01-04-16.
 */
public class List extends TypeIdentifier {
    public TypeIdentifier typeIdentifier;

    public List(TypeIdentifier tid, int ln) {
        super(ln);
        this.typeIdentifier = tid;
    }
}
